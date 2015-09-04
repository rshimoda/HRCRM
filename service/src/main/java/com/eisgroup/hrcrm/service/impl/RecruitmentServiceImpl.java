package com.eisgroup.hrcrm.service.impl;

import com.eisgroup.hrcrm.dao.CandidateLevelDAO;
import com.eisgroup.hrcrm.dao.CandidateRecruitmentDAO;
import com.eisgroup.hrcrm.dao.PositionDAO;
import com.eisgroup.hrcrm.dao.RecruitmentDAO;
import com.eisgroup.hrcrm.model.Candidate;
import com.eisgroup.hrcrm.model.CandidateRecruitment;
import com.eisgroup.hrcrm.model.Recruitment;
import com.eisgroup.hrcrm.service.RecruitmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service("recruitmentService")
public class RecruitmentServiceImpl extends TaskServiceImpl implements RecruitmentService, Serializable {

    @Autowired
    RecruitmentDAO recruitmentDAO;

    @Autowired
    CandidateRecruitmentDAO candidateRecruitmentDAO;

    @Autowired
    PositionDAO positionDAO;

    @Autowired
    CandidateLevelDAO candidateLevelDAO;

    @Override
    public Recruitment find(long id) {
        return recruitmentDAO.find(id);
    }

    @Override
    public List<Candidate> getAllCandidatesByRecruitmentId(Long id) {
        return candidateRecruitmentDAO.getAllCandidatesByTaskId(id);
    }

    @Override
    public List<Candidate> getAllActiveCandidate() {
        return candidateRecruitmentDAO.getAllActiveCandidate();
    }

    @Override
    public void linkActiveCandidates(Long recruitmentID, List<Candidate> selectedCandidates) {
        if (!selectedCandidates.isEmpty()) {
            candidateRecruitmentDAO.deleteAllCandidatesByTaskId(recruitmentID);
            for (Candidate item : selectedCandidates) {
                if (!isExists(recruitmentID, item.getId())) {
                    candidateRecruitmentDAO.create(new CandidateRecruitment(item.getId(), recruitmentID));
                }
            }
        } else candidateRecruitmentDAO.deleteAllCandidatesByTaskId(recruitmentID);
    }

    @Override
    public boolean isExists(Long recruitmentID, Long candidateID) {
        if (candidateRecruitmentDAO.isExists(recruitmentID, candidateID).isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public List<String> getAllUsedCandidatePositions() {
        return positionDAO.getAllUsedCandidatePositions();
    }

    @Override
    public List<String> getAllUsedCandidateLevels() {
        return candidateLevelDAO.getAllUsedCandidateLevel();
    }


}
