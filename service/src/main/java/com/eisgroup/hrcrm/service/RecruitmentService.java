package com.eisgroup.hrcrm.service;

import com.eisgroup.hrcrm.model.Candidate;
import com.eisgroup.hrcrm.model.Recruitment;

import java.util.List;

public interface RecruitmentService extends TaskService {
    Recruitment find(long id);

    List<Candidate> getAllCandidatesByRecruitmentId(Long id);

    List<Candidate> getAllActiveCandidate();

    void linkActiveCandidates(Long recruitmentID, List<Candidate> selectedCandidates);

    boolean isExists(Long recruitmentID, Long candidateID);

    List<String> getAllUsedCandidatePositions();

    List<String> getAllUsedCandidateLevels();
}