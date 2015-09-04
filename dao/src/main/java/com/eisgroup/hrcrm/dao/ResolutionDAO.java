package com.eisgroup.hrcrm.dao;

import com.eisgroup.hrcrm.model.Resolution;

import java.util.List;

public interface ResolutionDAO extends BaseObjectDAO<Resolution> {

    List<Resolution> getResolutionValue();

}



