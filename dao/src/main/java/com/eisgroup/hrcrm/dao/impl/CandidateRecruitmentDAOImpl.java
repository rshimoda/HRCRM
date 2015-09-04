package com.eisgroup.hrcrm.dao.impl;

import com.eisgroup.hrcrm.dao.CandidateRecruitmentDAO;
import com.eisgroup.hrcrm.model.Candidate;
import com.eisgroup.hrcrm.model.CandidateRecruitment;
import com.eisgroup.hrcrm.model.Recruitment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository(value = "candidateRecruitmentDAO")
@Transactional
public class CandidateRecruitmentDAOImpl extends BaseObjectDAOImpl<CandidateRecruitment> implements CandidateRecruitmentDAO {
    public CandidateRecruitmentDAOImpl() {
        super(CandidateRecruitment.class);
    }

    @Override
    public List<Recruitment> getAllRecruitmentsByTaskId(Long id) {
        Query query = entityManager.createQuery("SELECT task FROM Task task WHERE task.id IN " +
                "(SELECT cr.recruitmentId FROM Candidate_Recruitment cr WHERE cr.candidateId = :id)");
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public List<Candidate> getAllCandidatesByTaskId(Long id) {
        Query query = entityManager.createQuery("SELECT task FROM Task task WHERE task.id IN " +
                "(SELECT cr.candidateId FROM Candidate_Recruitment cr WHERE cr.recruitmentId = :id)");
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public List<Recruitment> getAllActiveRecruitment() {
        Query query = entityManager.createQuery(
//                "SELECT recruitment FROM Recruitment recruitment WHERE recruitment.status = 'REOPENED' OR recruitment.status = 'OPEN'");
                "SELECT recruitment FROM Recruitment recruitment");
        return query.getResultList();
    }

    @Override
    public List<Candidate> getAllActiveCandidate() {
        Query query = entityManager.createQuery(
//                "SELECT candidate FROM Candidate candidate WHERE candidate.status = 'REOPENED' OR candidate.status = 'OPEN'");
                "SELECT candidate FROM Candidate candidate");
        return query.getResultList();
    }

    @Override
    public List<CandidateRecruitment> isExists(Long recruitmentID, Long candidateID) {
        Query query = entityManager.createQuery(
                "SELECT cr.recruitmentId, cr.candidateId FROM Candidate_Recruitment cr " +
                        "WHERE cr.candidateId = :candidateID AND cr.recruitmentId = :recruitmentID");
        query.setParameter("candidateID", candidateID);
        query.setParameter("recruitmentID", recruitmentID);
        return query.getResultList();
    }

    @Override
    public void deleteAllCandidatesByTaskId(Long id) {
        Query query = entityManager.createQuery(
                "DELETE FROM Candidate_Recruitment cr WHERE cr.recruitmentId = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void deleteAllRecruitmentsByTaskId(Long id) {
        Query query = entityManager.createQuery(
                "DELETE FROM Candidate_Recruitment cr WHERE cr.candidateId = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
