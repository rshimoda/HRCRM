package com.eisgroup.hrcrm.service;

import com.eisgroup.hrcrm.model.Candidate;
import com.eisgroup.hrcrm.model.Recruitment;

import java.util.List;

public interface CandidateService extends TaskService {
    Candidate find(long id);

    List<Recruitment> getAllRecruitmentsByCandidateId(Long id);

    List<Recruitment> getAllActiveRecruiment();

    void linkActiveRecruitments(Long candidateID, List<Recruitment> selectedRecruitments);

    boolean isExists(Long recruitmentID, Long candidateID);

    List<String> getAllRecruitmentPositions();

    List<String> getAllRecruitmentLevels();
}
