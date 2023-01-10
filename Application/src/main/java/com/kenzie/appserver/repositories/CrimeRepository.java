package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.CrimeRecord;

import com.kenzie.appserver.service.model.Crime;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableScan
@Repository
public interface CrimeRepository extends CrudRepository<CrimeRecord, String> {
}
