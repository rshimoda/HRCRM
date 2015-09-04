package com.eisgroup.hrcrm.dao.impl;

import com.eisgroup.hrcrm.dao.ODSInternalDAO;
import com.eisgroup.hrcrm.model.ODSInternal;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value = "oDSInternalDAO")
@Transactional
public class ODSInternalDAOImpl extends BaseObjectDAOImpl<ODSInternal> implements ODSInternalDAO {
    public ODSInternalDAOImpl() {
        super(ODSInternal.class);
    }
}