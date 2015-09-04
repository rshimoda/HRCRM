package com.eisgroup.hrcrm.dao;

import com.eisgroup.hrcrm.model.Complexity;

import java.util.List;

public interface ComplexityDAO extends BaseObjectDAO<Complexity> {

    List<Complexity> getActiveComplexitiesByTaskType(String className);

    List<Complexity> getAllComplexitiesByTaskType(String className);

    void resetToDefault(String className);

    void setDeletedAll(String className);

    Complexity getByName(String name, String className);
}
