package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.CrimeRecord;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface CrimeRepository extends CrudRepository<CrimeRecord, String> {
}
