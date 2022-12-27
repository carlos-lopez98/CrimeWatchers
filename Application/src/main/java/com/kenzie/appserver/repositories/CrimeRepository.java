package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.CrimeRecord;

import com.kenzie.appserver.service.model.Crime;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface CrimeRepository extends CrudRepository<CrimeRecord, String> {
}
