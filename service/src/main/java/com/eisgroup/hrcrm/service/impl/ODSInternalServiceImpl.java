package com.eisgroup.hrcrm.service.impl;

import com.eisgroup.hrcrm.model.ODSInternal;
import com.eisgroup.hrcrm.dao.ODSInternalDAO;
import com.eisgroup.hrcrm.service.ODSInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service("odsInternalService")
public class ODSInternalServiceImpl extends TaskServiceImpl implements ODSInternalService, Serializable {

    @Autowired
    ODSInternalDAO odsInternalDAO;

    @Override
    public ODSInternal find(long id) {
        return odsInternalDAO.find(id);
    }
}
