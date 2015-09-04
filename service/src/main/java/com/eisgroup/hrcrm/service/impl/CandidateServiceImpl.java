package com.eisgroup.hrcrm.service.impl;

import com.eisgroup.hrcrm.dao.CandidateDAO;
import com.eisgroup.hrcrm.dao.CandidateLevelDAO;
import com.eisgroup.hrcrm.dao.CandidateRecruitmentDAO;
import com.eisgroup.hrcrm.dao.PositionDAO;
import com.eisgroup.hrcrm.model.Candidate;
import com.eisgroup.hrcrm.model.CandidateRecruitment;
import com.eisgroup.hrcrm.model.Recruitment;
import com.eisgroup.hrcrm.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service("candidateService")
public class CandidateServiceImpl extends TaskServiceImpl implements CandidateService, Serializable {

    @Autowired
    CandidateDAO candidateDAO;

    @Autowired
    CandidateRecruitmentDAO candidateRecruitmentDAO;

    @Autowired
    PositionDAO positionDAO;

    @Autowired
    CandidateLevelDAO candidateLevelDAO;

    @Override
    public Candidate find(long id) {
        return candidateDAO.find(id);
    }

    @Override
    public List<Recruitment> getAllRecruitmentsByCandidateId(Long id) {
        return candidateRecruitmentDAO.getAllRecruitmentsByTaskId(id);
    }

    @Override
    public List<Recruitment> getAllActiveRecruiment() {
        return candidateRecruitmentDAO.getAllActiveRecruitment();
    }

    @Override
    public void linkActiveRecruitments(Long candidateID, List<Recruitment> selectedRecruitments) {
        if (!selectedRecruitments.isEmpty()) {
            candidateRecruitmentDAO.deleteAllRecruitmentsByTaskId(candidateID);
            for (Recruitment item : selectedRecruitments) {
                if (!isExists(item.getId(), candidateID)) {
                    candidateRecruitmentDAO.create(new CandidateRecruitment(candidateID, item.getId()));
                }
            }
        } else candidateRecruitmentDAO.deleteAllRecruitmentsByTaskId(candidateID);
    }

    @Override
    public boolean isExists(Long recruitmentID, Long candidateID) {
        return !candidateRecruitmentDAO.isExists(recruitmentID, candidateID).isEmpty();
    }

    @Override
    public List<String> getAllRecruitmentPositions() {
        return positionDAO.getAllUsedRecruitmentPosition();
    }

    @Override
    public List<String> getAllRecruitmentLevels() {
        return candidateLevelDAO.getAllUsedRecruitmentLevel();
    }

}
