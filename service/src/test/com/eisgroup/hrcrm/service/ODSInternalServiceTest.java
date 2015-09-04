package com.eisgroup.hrcrm.service;

import com.eisgroup.hrcrm.dao.ODSInternalDAO;
import com.eisgroup.hrcrm.model.*;
import com.eisgroup.hrcrm.service.impl.ODSInternalServiceImpl;
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
public class ODSInternalServiceTest {

    @InjectMocks
    private ODSInternalServiceImpl service;

    @Mock
    private ODSInternalDAO dao;

    private List<ODSInternal> list;

    private static final String SUMMARY = "Summary";
    private static final String DESCRIPTION = "Description";
    private Complexity complexity = new Complexity("1", "ODS", false, false);
    private Resolution resolution = new Resolution("Resolution");
    private User user = new User("Adam", "Jensen", "ajensen","pass","jensen@email.com","sa","desc",false,null);

    @Before
    public void init() {
        list = new ArrayList<ODSInternal>(1);

        list.add(new ODSInternal(1L, Status.OPEN, Priority.HIGH, complexity, resolution, SUMMARY, DESCRIPTION, null, null, null, null, user, user));
    }

    @Test
    public void testFindById() {
        long id = 1;
        ODSInternal expexted = new ODSInternal();

        for (ODSInternal task : list) {
            if (task.getId() == id) {
                expexted = task;
            }
        }
        assertNotNull(expexted);

        when(dao.find(id)).thenReturn(expexted);

        ODSInternal actual = service.find(id);

        verify(dao).find(id);

        assertNotNull(actual);
        assertEquals(expexted, actual);
    }
}
