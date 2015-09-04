package com.eisgroup.hrcrm.dao.impl;

import com.eisgroup.hrcrm.dao.ResolutionDAO;
import com.eisgroup.hrcrm.model.Resolution;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class ResolutionDAOImpl extends BaseObjectDAOImpl<Resolution> implements ResolutionDAO {

    public ResolutionDAOImpl() {
        super(Resolution.class);
    }

    @Override
    public List<Resolution> getResolutionValue() {

        Query query = entityManager.createQuery("select p from Resolution p where p.id > 1 order by p.id");
        return query.getResultList();
    }
}
