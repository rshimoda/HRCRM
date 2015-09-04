//package com.eisgroup.hrcrm.service;
//
//import com.eisgroup.hrcrm.dao.*;
//import com.eisgroup.hrcrm.dao.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.eisgroup.hrcrm.service.impl.TaskPropertyServiceImpl;
//
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//import org.testng.Assert;
//import org.testng.internal.thread.ThreadTimeoutException;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.lang.Exception;
//
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.testng.Assert.assertEquals;
//import static org.testng.Assert.assertNotNull;
//
///**
// * Created by Yan Nikonorov on 3/12/15.
// *
// *  testng-mockito
// */
//public class TaskPropertyServiceTestNG {
//
//    private static final Logger LOG = LoggerFactory.getLogger(TaskPropertyServiceTestNG.class);
//
//    @InjectMocks
//    private TaskPropertyService taskPropertyService = new TaskPropertyServiceImpl();
//
//    @Mock
//    private ProjectDAO projectDAO;
//
//    @Mock
//    private ResolutionDAO resolutionDAO;
//    @Mock
//    private ComplexityDAO complexityDAO;
//    @Mock
//    private CandidateLevelDAO candidateLevelDAO;
//
//    @Mock
//    private PositionDAO positionDAO;
//
//    private List<Project> testProjects;
//
//    private List<Complexity> testComplexities;
//
//    @BeforeMethod
//    public void setUp() {
//        throw new SetUpException("test SetUp @BeforeMethod failed.");
//        MockitoAnnotations.initMocks(this);
//
//        testProjects = new ArrayList<Project>(5);
//
//        when(projectDAO.getAllActiveProjects()).thenReturn(new ArrayList<Project>() {{
//            add(new Project());
//            add(new Project());
//            add(new Project(3L, "HRCRM", false, false, ODSInternal.class.getSimpleName(), null));
//            add(new Project(4L, "What is love?", true, false, PerformanceAppraisal.class.getSimpleName(), null));
//            add(new Project(5L, "I like Java", true, false, Recruitment.class.getSimpleName(), null));
//
//        }});
//
//
//
//        testComplexities = new ArrayList<Complexity>(3);
//        testComplexities.add(new Complexity(1L, "2", ODSInternal.class.getSimpleName(), true, false));
//        testComplexities.add(new Complexity(2L, "3", Recruitment.class.getSimpleName(), false, false));
//        testComplexities.add(new Complexity(3L, "4", Recruitment.class.getSimpleName(), false, false));
//
//
//    }
//       @Test(enabled=true)
//        public void testgetActiveProjects() {
//        testProjects = taskPropertyService.getAllActiveProjects();
//
//            assertEquals(testProjects.size(), 5);
//            verify(projectDAO, times(1)).getAllActiveProjects();
//        }
//
//        @Test(enabled=true)
//        public void testcreateProject() {
//            Project testProject = new Project();
//            testProject.setName("Testtttt project1");
//            testProject.setDefault(true);
//
//            taskPropertyService.createProject(testProject);
//            verify(projectDAO, times(1)).create(testProject);
//        }
//
//
//    public class testngTimeTest {
//        @Test(timeOut = 500, expectedExceptions =ThreadTimeoutException.class)
//        public void waitLongTime() {
//            throw new WaitLongTimeException("TestngTimeTest method waitLongTime failed.");
//            LOG.info("HRCRM long long test ");
//            Thread.sleep(501);
//        }
//    }
//
//
//
//    @Test(enabled=true)
//    public void testgetComplexitiesInString() {
//        String taskType = testComplexities.get(1).getTaskType();
//        assertEquals(taskType.isEmpty(), false);
//        assertEquals(taskType, Recruitment.class.getSimpleName());
//
//        String expected = "3, 4";
//
//        List<Complexity> workComplexities = new ArrayList<Complexity>();
//        for (Complexity complexity : testComplexities) {
//            if (!complexity.isDeleted() && complexity.getTaskType().equals(taskType)) {
//                workComplexities.add(complexity);
//            }
//        }
//
//        try {
//        when(complexityDAO.getActiveComplexitiesByTaskType(taskType)).thenReturn(workComplexities);
//            } catch (Exception e) {
//            LOG.info("testgetComplexitiesInString exception: ", e);
//            }
//
//        String current = taskPropertyService.getComplexitiesInString(taskType);
//
//        assertNotNull(current);
//        assertEquals(expected, current);
//
//    }
//
//
//    public class TestExpectedExceptionComplexityTest  {
//
//        @Test(expectedExceptions = NullPointerException.class)
//        public void testNullPointerException() {
//            testComplexities = null;
//            int size = testComplexities.size();
//        }
//
//
//        public class TestngIgnoreTest {
//
//            @Test(enabled = false)
//            public void testsetProperty() {
//                LOG.info("Ignore this bad test");
//            }
//        }
//
//    }
//}