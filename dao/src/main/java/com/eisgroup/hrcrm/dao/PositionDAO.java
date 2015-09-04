package com.eisgroup.hrcrm.dao;

import com.eisgroup.hrcrm.model.Position;

import java.util.List;

public interface PositionDAO extends BaseObjectDAO<Position> {
    List<Position> getActivePositionsByTaskType(String taskType);

    void resetToDefaultByTaskType(String taskType);

    void setDeletedAllByTaskType(String taskType);

    Position getByNameByTaskType(String name, String taskType);

    List<String> getAllUsedCandidatePositions();

    List<String> getAllUsedRecruitmentPosition();
}
