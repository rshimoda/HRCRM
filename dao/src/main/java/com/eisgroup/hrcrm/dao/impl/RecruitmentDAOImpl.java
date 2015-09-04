package com.eisgroup.hrcrm.dao.impl;

import com.eisgroup.hrcrm.dao.RecruitmentDAO;
import com.eisgroup.hrcrm.model.Recruitment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Popov
 * Date: 22.02.2015
 * Time: 21:26
 */
@Repository(value = "recruitmentDAO")
@Transactional
public class RecruitmentDAOImpl extends BaseObjectDAOImpl<Recruitment> implements RecruitmentDAO {
    public RecruitmentDAOImpl() {
        super(Recruitment.class);
    }
}
