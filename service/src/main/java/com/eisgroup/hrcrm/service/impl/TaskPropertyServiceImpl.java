package com.eisgroup.hrcrm.service.impl;

import com.eisgroup.hrcrm.dao.*;
import com.eisgroup.hrcrm.model.*;
import com.eisgroup.hrcrm.service.TaskPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service("taskPropertyService")
public class TaskPropertyServiceImpl implements TaskPropertyService, Serializable {

    @Autowired
    private ResolutionDAO resolutionDAO;
    @Autowired
    private ComplexityDAO complexityDAO;
    @Autowired
    private CandidateLevelDAO candidateLevelDAO;
    @Autowired
    private ProjectDAO projectDAO;
    @Autowired
    private PositionDAO positionDAO;

    @Override
    public List<Resolution> getAllResolutions() {
        return resolutionDAO.getAll();
    }

    @Override
    public List<Resolution> getResolutionValue() {
        return resolutionDAO.getResolutionValue();
    }

    @Override
    public List<Complexity> getActiveComplexitiesByTaskType(String className) {
        List<Complexity> complexities = complexityDAO.getActiveComplexitiesByTaskType(className);

        Collections.sort(complexities, new Comparator<Complexity>() {
            @Override
            public int compare(Complexity o1, Complexity o2) {
                BigDecimal big1 = new BigDecimal(o1.getName());
                BigDecimal big2 = new BigDecimal(o2.getName());
                return big1.compareTo(big2);
            }
        });
        return complexities;
    }

    @Override
    public Resolution getResolutionById(Long id) {
        return resolutionDAO.find(id);
    }

    @Override
    public Complexity getComplexityById(Long id) {
        return complexityDAO.find(id);
    }


    @Override
    public List<CandidateLevel> getActiveCandidateLevelsByTaskType(String className) {
        List<CandidateLevel> candidateLevels = candidateLevelDAO.getActiveCandidateLevelsByTaskType(className);

        Collections.sort(candidateLevels, new Comparator<CandidateLevel>() {
            @Override
            public int compare(CandidateLevel o1, CandidateLevel o2) {
                BigDecimal big1 = new BigDecimal(o1.getName());
                BigDecimal big2 = new BigDecimal(o2.getName());
                return big1.compareTo(big2);
            }
        });
        return candidateLevels;
    }

    @Override
    public List<Position> getActivePositionsByTaskType(String taskType) {
        return positionDAO.getActivePositionsByTaskType(taskType);
    }

    @Override
    public List<Project> getAllActiveProjects() {
        return projectDAO.getAllActiveProjects();
    }

    @Override
    public void createPosition(Position position) {
        positionDAO.create(position);
    }

    @Override
    public void createCandidateLevel(CandidateLevel candidateLevel) {
        candidateLevelDAO.create(candidateLevel);
    }

    @Override
    public void createProject(Project project) {
        projectDAO.create(project);
    }

    @Override
    public Project getProjectById(Long id) {
        return projectDAO.find(id);
    }

    @Override
    public CandidateLevel getCandidateLevelById(Long id) {
        return candidateLevelDAO.find(id);
    }

    @Override
    public Position getPositionById(Long id) {
        return positionDAO.find(id);
    }

    @Override
    public String getComplexitiesInString(String taskType) {
        if (taskType == null)
            return null;
        StringBuilder stringBuilder = new StringBuilder();
        List<Complexity> complexities = getActiveComplexitiesByTaskType(taskType);

        for (Complexity complexity : complexities) {
            stringBuilder.append(complexity.getName());
            stringBuilder.append(", ");
        }
        if (stringBuilder.length() >= 2) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }
        return stringBuilder.toString();
    }

    @Override
    public String getCandidateLevelsInString(String className) {
        StringBuilder stringBuilder = new StringBuilder();
        List<CandidateLevel> candidateLevels = getActiveCandidateLevelsByTaskType(className);

        for (CandidateLevel candidateLevel : candidateLevels) {
            stringBuilder.append(candidateLevel.getName());
            stringBuilder.append(", ");
        }
        if (stringBuilder.length() >= 2) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }
        return stringBuilder.toString();
    }


    @Override
    public String getProjectsInString() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Project> projects = getAllActiveProjects();
        Collections.sort(projects, new Comparator<Project>() {
            @Override
            public int compare(Project o1, Project o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        for (Project project : projects) {
            stringBuilder.append(project.getName());
            stringBuilder.append(", ");
        }
        if (stringBuilder.length() >= 2) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }
        return stringBuilder.toString();
    }

    @Override
    public String getPositionsInString(String taskType) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Position> positions = getActivePositionsByTaskType(taskType);

        for (Position position : positions) {
            stringBuilder.append(position.getName());
            stringBuilder.append(", ");
        }
        if (stringBuilder.length() >= 2) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }
        return stringBuilder.toString();
    }

    @Override
    public void updateProjects(String propertiesString) {
        if (propertiesString == null)
            return;
        if (propertiesString.isEmpty()) {
            projectDAO.resetToDefault();
            return;
        }
        projectDAO.setDeletedAll();

        for (String propertyName : propertiesString.split(",")) {
            propertyName = propertyName.trim();
            if (!propertyName.isEmpty()) {
                Project project = projectDAO.getByName(propertyName);
                if (project == null) {
                    project = new Project();
                    project.setName(propertyName);
                }
                project.setDeleted(false);
                project.setName(propertyName);
                projectDAO.update(project);
            }
        }
    }

    @Override
    public void updateCandidateLevels(String propertiesString, String taskType) {
        if (propertiesString == null || taskType == null)
            return;
        if (propertiesString.isEmpty()) {
            candidateLevelDAO.resetToDefault(taskType);
            return;
        }
        candidateLevelDAO.setDeletedAll(taskType);

        for (String propertyName : propertiesString.replaceAll("\\p{Z}+", "").split(",")) {
            if (!propertyName.isEmpty()) {
                CandidateLevel candidateLevel = candidateLevelDAO.getByName(propertyName, taskType);
                if (candidateLevel == null) {
                    candidateLevel = new CandidateLevel();
                    candidateLevel.setName(propertyName);
                    candidateLevel.setTaskType(taskType);
                }
                candidateLevel.setDeleted(false);
//                if (!candidateLevel.isDefault()) {
//                    candidateLevel.setName(propertyName);
//                }
                candidateLevelDAO.update(candidateLevel);
            }
        }
    }

    @Override
    public void updatePositions(String propertiesString, String taskType) {
        if (propertiesString == null)
            return;
        if (propertiesString.isEmpty()) {
            positionDAO.resetToDefaultByTaskType(taskType);
            return;
        }

        positionDAO.setDeletedAllByTaskType(taskType);

        for (String propertyName : propertiesString.split(",")) {
            propertyName = propertyName.trim();
            if (!propertyName.isEmpty()) {
                Position position = positionDAO.getByNameByTaskType(propertyName, taskType);
                if (position == null) {
                    position = new Position();
                    position.setName(propertyName);
                    position.setTaskType(taskType);
                }
                position.setDeleted(false);
                position.setName(propertyName);
                positionDAO.update(position);
            }
        }
    }

    @Override
    public void updateComplexities(String propertiesString, String taskType) {
        if (propertiesString == null || taskType == null)
            return;

        if (propertiesString.isEmpty()) {
            complexityDAO.resetToDefault(taskType);
            return;
        }
        complexityDAO.setDeletedAll(taskType);
        for (String propertyName : propertiesString.replaceAll("\\p{Z}+", "").split(",")) {
            if (!propertyName.isEmpty()) {
                Complexity complexity = complexityDAO.getByName(propertyName, taskType);
                if (complexity == null) {
                    complexity = new Complexity();
                    complexity.setName(propertyName);
                    complexity.setTaskType(taskType);
                }
                complexity.setDeleted(false);
//                if (!complexity.isDefault()) {
//                    complexity.setName(propertyName);
//                }
                complexityDAO.update(complexity);
            }
        }
    }
}