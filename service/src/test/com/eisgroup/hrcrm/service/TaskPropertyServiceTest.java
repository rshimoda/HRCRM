package com.eisgroup.hrcrm.service;

import com.eisgroup.hrcrm.dao.*;
import com.eisgroup.hrcrm.model.*;
import com.eisgroup.hrcrm.service.impl.TaskPropertyServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Margo on 15.03.2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class TaskPropertyServiceTest {

//    @InjectMocks
//    TaskPropertyServiceImpl service;
//
//    @Mock
//    private ResolutionDAO resolutionDAO;
//    @Mock
//    private ComplexityDAO complexityDAO;
//    @Mock
//    private CandidateLevelDAO candidateLevelDAO;
//    @Mock
//    private ProjectDAO projectDAO;
//    @Mock
//    private PositionDAO positionDAO;
//
//    private List<Resolution> resolutions;
//    private List<Complexity> complexities;
//    private List<CandidateLevel> candidateLevels;
//    private List<Project> projects;
//    private List<Position> positions;
//
//   @Before
//   public void init() {
//       resolutions = new ArrayList<Resolution>(2);
//       complexities = new ArrayList<Complexity>(4);
//       candidateLevels = new ArrayList<CandidateLevel>(4);
//       projects = new ArrayList<Project>(4);
//       positions = new ArrayList<Position>(2);
//
//       resolutions.add(new Resolution(1L, "Resolution"));
//       resolutions.add(new Resolution(2L, "Resolutions"));
//
//       complexities.add(new Complexity(1L, "2", ODSInternal.class.getSimpleName(), false, false));
//       complexities.add(new Complexity(2L, "2", Recruitment.class.getSimpleName(), false, false));
//       complexities.add(new Complexity(3L, "2", ODSInternal.class.getSimpleName(), true, false));
//       complexities.add(new Complexity(4L, "1", ODSInternal.class.getSimpleName(), false, false));
//
//       candidateLevels.add(new CandidateLevel(1L, "1", false, false, ODSInternal.class.getSimpleName()));
//       candidateLevels.add(new CandidateLevel(2L, "2", false, false, ODSInternal.class.getSimpleName()));
//       candidateLevels.add(new CandidateLevel(3L, "3", true, false, Recruitment.class.getSimpleName()));
//       candidateLevels.add(new CandidateLevel(4L, "3", false, false, Recruitment.class.getSimpleName()));
//
//       projects.add(new Project(1L, "HRCRM", false, false, ODSInternal.class.getSimpleName(), null));
//       projects.add(new Project(2L, "CM", false, false, ODSInternal.class.getSimpleName(), null));
//       projects.add(new Project(3L, "GoogleAs", true, false, Recruitment.class.getSimpleName(), null));
//       projects.add(new Project(4L, "Gravity", false, false, Recruitment.class.getSimpleName(), null));
//
//       positions.add(new Position(1L, "BA", false, false, ODSInternal.class.getSimpleName()));
//       positions.add(new Position(2L, "PM", false, false, ODSInternal.class.getSimpleName()));
//       positions.add(new Position(3L, "CM", true, false, Recruitment.class.getSimpleName()));
//       positions.add(new Position(4L, "Lead", false, false, Recruitment.class.getSimpleName()));
//
//   }
//
//    @Test
//    public void testGetAllResolutions() {
//        List<Resolution> expected = resolutions;
//        assertNotNull(expected);
//
//        when(resolutionDAO.getAll()).thenReturn(resolutions);
//
//        List<Resolution> actual = service.getAllResolutions();
//        verify(resolutionDAO).getAll();
//
//        assertNotNull(actual);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testGetActiveComplexitiesByTaskType() {
//        String name = complexities.get(0).getTaskType();
//        assertNotNull(name);
//
//        List<Complexity> expected = new ArrayList<Complexity>();
//        for (Complexity complexity : complexities) {
//            if (!complexity.isDeleted() && complexity.getTaskType().equals(name)) {
//                expected.add(complexity);
//            }
//        }
//
//        when(complexityDAO.getActiveComplexitiesByTaskType(name)).thenReturn(expected);
//
//        List<Complexity> actual = service.getActiveComplexitiesByTaskType(name);
//        verify(complexityDAO).getActiveComplexitiesByTaskType(name);
//
//        assertNotNull(actual);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testGetResolutionById() {
//        long id = 1L;
//
//        Resolution expected = new Resolution();
//        for (Resolution resolution : resolutions) {
//            if (resolution.getId() == id) {
//                expected = resolution;
//            }
//        }
//        assertNotNull(expected);
//
//        when(resolutionDAO.find(id)).thenReturn(expected);
//
//        Resolution actual = service.getResolutionById(id);
//        verify(resolutionDAO).find(id);
//
//        assertNotNull(actual);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testGetComplexityById() {
//        long id = 1L;
//
//        Complexity expexted = complexities.get(0);
//        assertNotNull(expexted);
//
//        when(complexityDAO.find(id)).thenReturn(expexted);
//
//        Complexity actual = service.getComplexityById(id);
//        verify(complexityDAO).find(id);
//
//        assertNotNull(actual);
//        assertEquals(expexted, actual);
//    }
//
//    @Test
//    public void testGetActiveCandidateLevelsByTaskType() {
//        String taskType = candidateLevels.get(0).getTaskType(); //ODS
//        assertNotNull(taskType);
//        List<CandidateLevel> expected = new ArrayList<CandidateLevel>();
//
//        for (CandidateLevel level : candidateLevels) {
//            if (!level.isDeleted()) {
//                expected.add(level);
//            }
//        }
//        assertNotNull(expected);
//
//        when(candidateLevelDAO.getActiveCandidateLevelsByTaskType(taskType)).thenReturn(expected);
//
//        List<CandidateLevel> actual = service.getActiveCandidateLevelsByTaskType(taskType);
//
//        verify(candidateLevelDAO).getActiveCandidateLevelsByTaskType(taskType);
//
//        assertNotNull(actual);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testGetActiveProjectsByTaskType() {
//        String taskType = projects.get(0).getTaskType(); //ODS
//        assertNotNull(taskType);
//
//        List<Project> expected = new ArrayList<Project>();
//
//        for (Project project : projects) {
//            if (!project.isDeleted()) {
//                expected.add(project);
//            }
//        }
//        assertNotNull(expected);
//
//        when(projectDAO.getActiveProjectsByTaskType(taskType)).thenReturn(expected);
//
//        List<Project> actual = service.getActiveProjectsByTaskType(taskType);
//
//        verify(projectDAO).getActiveProjectsByTaskType(taskType);
//
//        assertNotNull(actual);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testGetActivePositionsByTaskType() {
//        String taskType = positions.get(0).getTaskType(); //ODS
//        assertNotNull(taskType);
//        List<Position> expected = new ArrayList<Position>();
//
//        for (Position position : positions) {
//            if (!position.isDeleted()) {
//                expected.add(position);
//            }
//        }
//        assertNotNull(expected);
//
//        when(positionDAO.getActivePositionsByTaskType(taskType)).thenReturn(expected);
//
//        List<Position> actual = service.getActivePositionsByTaskType(taskType);
//        verify(positionDAO).getActivePositionsByTaskType(taskType);
//
//        assertNotNull(actual);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testGetAllActiveProjects() {
//        List<Project> expected = new ArrayList<Project>();
//        for (Project project : projects) {
//            if (!project.isDeleted()) {
//                expected.add(project);
//            }
//        }
//        assertNotNull(expected);
//
//        when(projectDAO.getAllActiveProjects()).thenReturn(expected);
//
//        List<Project> actual = service.getAllActiveProjects();
//        verify(projectDAO).getAllActiveProjects();
//
//        assertNotNull(actual);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testGetProjectById() {
//        long id = 1L;
//
//        Project expected = projects.get(0);
//        assertNotNull(expected);
//
//        when(projectDAO.find(id)).thenReturn(expected);
//
//        Project actual = service.getProjectById(id);
//        verify(projectDAO).find(id);
//
//        assertNotNull(actual);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testGetCandidateLevelById() {
//        long id = 1L;
//
//        CandidateLevel expected = new CandidateLevel();
//        for(CandidateLevel candidateLevel : candidateLevels) {
//            if (candidateLevel.getId() == id) {
//                expected = candidateLevel;
//            }
//        }
//        assertNotNull(expected);
//
//        when(candidateLevelDAO.find(id)).thenReturn(expected);
//
//        CandidateLevel actual = service.getCandidateLevelById(id);
//        verify(candidateLevelDAO).find(id);
//
//        assertNotNull(actual);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testGetPositionById() {
//        long id = 1L;
//
//        Position expected = new Position();
//        for (Position position : positions) {
//            if (position.getId() == id) {
//                expected = position;
//            }
//        }
//        assertNotNull(expected);
//
//        when(positionDAO.find(id)).thenReturn(expected);
//
//        Position actual = service.getPositionById(id);
//        verify(positionDAO).find(id);
//
//        assertNotNull(actual);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testGetComplexitiesInString() {
//        String taskType = complexities.get(0).getTaskType(); //ODS
//        assertNotNull(taskType);
//
//        String expected = "1, 2";
//
//        List<Complexity> activeComplexities = new ArrayList<Complexity>();
//        for (Complexity complexity : complexities) {
//            if (!complexity.isDeleted() && complexity.getTaskType().equals(taskType)) {
//                activeComplexities.add(complexity);
//            }
//        }
//
//        when(complexityDAO.getActiveComplexitiesByTaskType(taskType)).thenReturn(activeComplexities);
//
//        String actual = service.getComplexitiesInString(taskType);
//
//        assertNotNull(actual);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testGetCandidateLevelsInString() {
//        String taskType = candidateLevels.get(0).getTaskType(); //ODS
//        assertNotNull(taskType);
//
//        String expected = "1, 2";
//
//        List<CandidateLevel> activeCandidateLevels = new ArrayList<CandidateLevel>();
//        for (CandidateLevel candidateLevel : candidateLevels) {
//            if (!candidateLevel.isDeleted() && candidateLevel.getTaskType().equals(taskType)) {
//                activeCandidateLevels.add(candidateLevel);
//            }
//        }
//        assertNotNull(activeCandidateLevels);
//
//        when(candidateLevelDAO.getActiveCandidateLevelsByTaskType(taskType)).thenReturn(activeCandidateLevels);
//
//        String actual = service.getCandidateLevelsInString(taskType);
//        verify(candidateLevelDAO).getActiveCandidateLevelsByTaskType(taskType);
//
//        assertNotNull(actual);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testGetProjectsInString() {
//        String taskType = projects.get(0).getTaskType(); //ODS
//        assertNotNull(taskType);
//
//        String expected = "HRCRM, CM";
//
//        List<Project> activeProjects = new ArrayList<Project>();
//        for (Project project : projects) {
//            if (!project.isDeleted() && project.getTaskType().equals(taskType)) {
//                activeProjects.add(project);
//            }
//        }
//        assertNotNull(activeProjects);
//
//        when(projectDAO.getActiveProjectsByTaskType(taskType)).thenReturn(activeProjects);
//
//        String actual = service.getProjectsInString(taskType);
//        verify(projectDAO).getActiveProjectsByTaskType(taskType);
//
//        assertNotNull(actual);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testGetPositionsInString() {
//        String taskType = positions.get(0).getTaskType(); // ODS
//        assertNotNull(taskType);
//
//        String expected = "BA, PM";
//
//        List<Position> activePositions = new ArrayList<Position>();
//        for (Position position : positions) {
//            if (!position.isDeleted() && position.getTaskType().equals(taskType)) {
//                activePositions.add(position);
//            }
//        }
//        assertNotNull(activePositions);
//
//        when(positionDAO.getActivePositionsByTaskType(taskType)).thenReturn(activePositions);
//
//        String actual = service.getPositionsInString(taskType);
//        verify(positionDAO).getActivePositionsByTaskType(taskType);
//
//        assertNotNull(actual);
//        assertEquals(expected, actual);
//    }
}
