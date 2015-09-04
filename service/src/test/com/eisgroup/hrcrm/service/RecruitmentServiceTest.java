package com.eisgroup.hrcrm.service;

import com.eisgroup.hrcrm.dao.RecruitmentDAO;
import com.eisgroup.hrcrm.model.*;
import com.eisgroup.hrcrm.service.impl.RecruitmentServiceImpl;
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
public class RecruitmentServiceTest {

    @InjectMocks
    private RecruitmentServiceImpl service;

    @Mock
    private RecruitmentDAO dao;

    private List<Recruitment> list;

    private static final String SUMMARY = "Summary";
    private static final String DESCRIPTION = "Description";
    private Complexity complexity = new Complexity("1", "ODS", false, false);
    private Resolution resolution = new Resolution("Resolution");
    private User user = new User("Adam", "Jensen", "adamjensen","pass","ajensen@email.com","sa","desc",false,null);
    private Project project = new Project("Project", false, false, null);
    private Position position = new Position("BA", false, false, "ODS");
    private CandidateLevel candidateLevel = new CandidateLevel("2", false, false, "ODS");

    @Before
    public void init() {
        list = new ArrayList<Recruitment>(1);
        list.add(new Recruitment(1L, Status.OPEN, Priority.HIGH, complexity, resolution, SUMMARY, DESCRIPTION, null, null, null, null,
                user, user, project, position, candidateLevel));
    }

    @Test
    public void testFindById() {
        long id = 1L;
        Recruitment expected = new Recruitment();

        for (Recruitment task : list) {
            if (task.getId() == id) {
                expected = task;
            }
        }

        assertNotNull(expected);

        when(dao.find(id)).thenReturn(expected);

        Recruitment actual = service.find(id);

        verify(dao).find(id);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}
