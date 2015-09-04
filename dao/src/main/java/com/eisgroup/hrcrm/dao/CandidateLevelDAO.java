package com.eisgroup.hrcrm.dao;

import com.eisgroup.hrcrm.model.CandidateLevel;

import java.util.List;

public interface CandidateLevelDAO extends BaseObjectDAO<CandidateLevel> {
    List<CandidateLevel> getActiveCandidateLevelsByTaskType(String taskType);

    List<CandidateLevel> getAllCandidateLevelsByTaskType(String taskType);

    void resetToDefault(String taskType);

    void setDeletedAll(String taskType);

    CandidateLevel getByName(String name, String taskType);

    List<String> getAllUsedCandidateLevel();

    List<String> getAllUsedRecruitmentLevel();
}
