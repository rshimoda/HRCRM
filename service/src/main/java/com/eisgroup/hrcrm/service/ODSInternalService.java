package com.eisgroup.hrcrm.service;

import com.eisgroup.hrcrm.model.ODSInternal;

public interface ODSInternalService extends TaskService {
    ODSInternal find(long id);
}