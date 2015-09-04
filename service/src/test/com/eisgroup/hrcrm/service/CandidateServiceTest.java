package com.eisgroup.hrcrm.service;

import com.eisgroup.hrcrm.dao.CandidateDAO;
import com.eisgroup.hrcrm.model.Candidate;
import com.eisgroup.hrcrm.service.impl.CandidateServiceImpl;
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
 * @author VKarpenko
 */
@RunWith(MockitoJUnitRunner.class)
public class CandidateServiceTest {

    @InjectMocks
    private CandidateServiceImpl service;

    @Mock
    private CandidateDAO dao;

    private List<Candidate> list;

    @Before
    public void init() {
        list = new ArrayList<Candidate>(1);
        Candidate candidate = new Candidate();
        candidate.setId(1L);
        list.add(candidate);
    }

    @Test
    public void testFindById() {
        long id = 1;

        Candidate expected = new Candidate();
        for (Candidate task : list) {
            if (task.getId() == id) {
                expected = task;
            }
        }

        when(dao.find(id)).thenReturn(expected);
        Candidate actual = service.find(id);
        verify(dao).find(id);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }


}
