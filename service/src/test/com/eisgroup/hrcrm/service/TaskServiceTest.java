//package com.eisgroup.hrcrm.service;
//
//import com.eisgroup.hrcrm.dao.TaskDAO;
//import com.eisgroup.hrcrm.dao.*;
//import com.eisgroup.hrcrm.service.impl.TaskServiceImpl;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static junit.framework.Assert.assertEquals;
//import static junit.framework.TestCase.assertNotNull;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
///**
// * Created by Yan Nikonorov on 02.03.2015.
// * JUnit-mockito
// */
//
//
//@RunWith(MockitoJUnitRunner.class)
//public class TaskServiceTest {
//
//    private static final Logger LOG = LoggerFactory.getLogger(TaskServiceTest.class);
//
//    @InjectMocks
//    private TaskServiceImpl taskService;
//    @Mock
//    private TaskDAO taskDAO;
//    private List<Task> testTasks;
//    public static final String TEST_SUMMARY1 = "test Summary1";
//    public static final String TEST_DESCRIPTION1 = "test Description1";
//
//    public static final String TEST_SUMMARY2 = "test Summary2";
//    public static final String TEST_DESCRIPTION2 = "test Description2";
//
//
//    private class testTask extends  Task {
//
//        public testTask(Status open, Priority medium, Object o, Object o1, String testSummary1, String testDescription1, Date date, Date date1, Date date2, Date date3, Object o2, Object o3) {
//
//        }
//    }
//
//    @Before
//    public void init() {
//
//        testTasks=new ArrayList<Task>(2);
//
//        testTasks.add(new
//
//                testTask(
//                Status.OPEN,
//                Priority.MEDIUM,
//                null,
//                null,
//                TEST_SUMMARY1,
//                TEST_DESCRIPTION1,
//                new Date(),
//                new Date(),
//                new Date(),
//                new Date(),
//                null,
//                null));
//
//        testTasks.add(new
//                testTask(
//                Status.IN_PROGRESS,
//                Priority.LOW,
//                null,
//                null,
//                TEST_SUMMARY2,
//                TEST_DESCRIPTION2,
//                new Date(),
//                new Date(),
//                new Date(),
//                new Date(),
//                null,
//                null));
//
//    }
//
//
//
//
//    @Test
//    public void testgetAllTasks() {
//        try {
//            when(taskDAO.getAll()).thenReturn(testTasks);
//        } catch (Exception e) {
//            LOG.info("testgetAllTasks exception: ", e);
//        }
//        List<Task> current = taskService.getAllTasks();
//
//        verify(taskDAO).getAll();
//
//        assertNotNull(current);
//        assertEquals(testTasks, current);
//
//
//    }
//
//
//    @Test
//    public void testgetNumberOfTasks() {
//        try {
//            when(taskDAO.getNumberOfTasks()).thenReturn(Long.valueOf(testTasks.size()));
//        } catch (Exception e) {
//            LOG.info("testgetNumberOfTasks exception: ", e);
//        }
//
//        long current = taskService.getNumberOfTasks();
//
//        verify(taskDAO).getNumberOfTasks();
//
//        assertNotNull(current);
//        assertEquals(testTasks.size(), current);
//    }
//
//    @Test
//    public void testgetActiveTasks() {
//        try {
//            when(taskDAO.getActiveTasks()).thenReturn(testTasks);
//        } catch (Exception e) {
//             LOG.info("testgetActiveTasks exception: ", e);
//        }
//        List<Task> current = taskService.getActiveTasks();
//
//        verify(taskDAO).getActiveTasks();
//
//        assertNotNull(current);
//        assertEquals(testTasks, current);
//
//    }
//
//
//
//    @Test
//    public void testfindbyid() {
//        long id =2;
//        Task testTask = testTasks.get(0);
//
//        assertNotNull(testTask);
//        try {
//            when(taskDAO.find(id)).thenReturn(testTask);
//        } catch (Exception e) {
//            LOG.info("testfindbyid exception: ", e);
//        }
//
//        Task current = taskService.find(id);
//
//        verify(taskDAO).find(id);
//        assertNotNull(current);
//        assertEquals(testTask, current);
//    }
//
//
//
//}
