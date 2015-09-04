package com.eisgroup.hrcrm.service;

import com.eisgroup.hrcrm.model.*;

import java.util.List;

public interface TaskPropertyService {
    List<Resolution> getAllResolutions();

    List<Resolution> getResolutionValue();

    List<Complexity> getActiveComplexitiesByTaskType(String className);

    Resolution getResolutionById(Long id);

    Complexity getComplexityById(Long id);

    List<CandidateLevel> getActiveCandidateLevelsByTaskType(String className);

    List<Project> getAllActiveProjects();

    List<Position> getActivePositionsByTaskType(String taskType);

    void createPosition(Position position);

    void createCandidateLevel(CandidateLevel candidateLevel);

    void createProject(Project project);

    Project getProjectById(Long id);

    CandidateLevel getCandidateLevelById(Long id);

    Position getPositionById(Long id);

    String getComplexitiesInString(String taskType);

    String getCandidateLevelsInString(String taskType);

    String getProjectsInString();

    String getPositionsInString(String taskType);

    void updateComplexities(String complexities, String taskType);

    void updateProjects(String projects);

    void updateCandidateLevels(String candidateLevels, String taskType);

    void updatePositions(String positions, String taskType);
}
