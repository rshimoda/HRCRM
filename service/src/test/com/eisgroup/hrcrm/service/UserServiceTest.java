package com.eisgroup.hrcrm.service;

import com.eisgroup.hrcrm.dao.UserDAO;
import com.eisgroup.hrcrm.model.User;
import com.eisgroup.hrcrm.service.impl.UserServiceImpl;
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
 * Created by Margo on 04.03.2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

//    @InjectMocks
//    private UserServiceImpl service;
//
//    @Mock
//    private UserDAO dao;
//
//    private List<User> testUsers;
//
//
//    @Before
//    public void init() {
//        testUsers = new ArrayList<User>(3);
//
//        testUsers.add(new User(1L, "Adam", "Jensen", "AJensen", "12345", "ajensen@gmail.com", "rwo", "", false, null));
//        testUsers.add(new User(2L, "David", "Sariff", "DSariff", "12345", "dsariff@gmail.com", "rwo", "", false, null));
//        testUsers.add(new User(3L, "Megan", "Rid", "MRid", "12345", "mrid@gmail.com", "rwo", "", true, null));
//    }
//
//    @Test
//    public void testGetAllActiveUsers() {
//        List<User> expected = new ArrayList<User>();
//        for (User user : testUsers) {
//            if (!user.isDeleted()) {
//                expected.add(user);
//            }
//        }
//        assertNotNull(expected);
//
//        when(dao.getAllActiveUsers()).thenReturn(expected);
//        List<User> actual = service.getAllActiveUsers();
//
//        verify(dao).getAllActiveUsers();
//
//        assertNotNull(actual);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testGetNumberOfUsers() {
//        when(dao.getNumberOfUsers()).thenReturn(Long.valueOf(testUsers.size()));
//        long actual = service.getNumberOfUsers();
//
//        verify(dao).getNumberOfUsers();
//
//        assertNotNull(actual);
//        assertEquals(testUsers.size(), actual);
//    }
//
//    @Test
//    public void testFindById() {
//        long id = 1;
//        User user = null;
//
//        for (User testUser : testUsers) {
//            if (testUser.getId() == id) {
//                user = testUser;
//            }
//        }
//
//        assertNotNull(user);
//        when(dao.find(id)).thenReturn(user);
//
//        User actual = service.find(id);
//
//        verify(dao).find(id);
//
//        assertNotNull(actual);
//        assertEquals(user, actual);
//    }
//
//    @Test
//    public void testFindUserByLogin() {
//        User expected = testUsers.get(0);
//        String login = expected.getLogin();
//
//        assertNotNull(login);
//        when(dao.findUserByLogin(login)).thenReturn(expected);
//
//        User actual = service.findUserByLogin(login);
//        verify(dao).findUserByLogin(login);
//
//        assertNotNull(actual);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testFindUserByEmail() {
//        User expected = testUsers.get(0);
//        String email = expected.getEmail();
//
//        assertNotNull(email);
//
//        when(dao.findUserByEmail(email)).thenReturn(expected);
//
//        User actual = service.findUserByEmail(email);
//
//        verify(dao).findUserByEmail(email);
//        assertNotNull(actual);
//        assertEquals(expected, actual);
//    }
}
