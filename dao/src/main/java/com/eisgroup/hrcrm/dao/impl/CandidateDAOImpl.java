package com.eisgroup.hrcrm.dao.impl;

import com.eisgroup.hrcrm.dao.CandidateDAO;
import com.eisgroup.hrcrm.model.Candidate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value = "candidateDAO")
@Transactional
public class CandidateDAOImpl extends BaseObjectDAOImpl<Candidate> implements CandidateDAO {
    public CandidateDAOImpl() {
        super(Candidate.class);
    }
}
