package com.eisgroup.hrcrm.dao;

import com.eisgroup.hrcrm.model.Candidate;
import com.eisgroup.hrcrm.model.CandidateRecruitment;
import com.eisgroup.hrcrm.model.Recruitment;

import java.util.List;

public interface CandidateRecruitmentDAO extends BaseObjectDAO<CandidateRecruitment> {

    List<Recruitment> getAllRecruitmentsByTaskId(Long id);

    List<Candidate> getAllCandidatesByTaskId(Long id);

    List<Recruitment> getAllActiveRecruitment();

    List<Candidate> getAllActiveCandidate();

    List<CandidateRecruitment> isExists(Long recruitmentID, Long candidateID);

    void deleteAllCandidatesByTaskId(Long id);

    void deleteAllRecruitmentsByTaskId(Long id);
}
