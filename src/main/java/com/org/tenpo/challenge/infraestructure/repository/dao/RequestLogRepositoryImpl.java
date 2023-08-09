package com.org.tenpo.challenge.infraestructure.repository.dao;

import com.org.tenpo.challenge.infraestructure.repository.model.RequestLogEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestLogRepositoryImpl extends R2dbcRepository<RequestLogEntity, String> {

}
