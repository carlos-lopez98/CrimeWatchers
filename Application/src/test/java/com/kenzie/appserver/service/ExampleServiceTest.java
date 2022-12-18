package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.ExampleRepository;
import com.kenzie.appserver.repositories.model.CrimeRecord;
import com.kenzie.appserver.repositories.model.ExampleRecord;
import com.kenzie.appserver.service.model.Crime;
import com.kenzie.appserver.service.model.Example;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExampleServiceTest {
    private ExampleRepository exampleRepository;
    private CrimeService crimeService;
    private LambdaServiceClient lambdaServiceClient;

    @BeforeEach
    void setup() {
        exampleRepository = mock(ExampleRepository.class);
        lambdaServiceClient = mock(LambdaServiceClient.class);
        crimeService = new CrimeService(exampleRepository, lambdaServiceClient);
    }
    /** ------------------------------------------------------------------------
     *  exampleService.findById
     *  ------------------------------------------------------------------------ **/

    @Test
    void findById() {
        // GIVEN
        String id = randomUUID().toString();

        CrimeRecord record = new CrimeRecord();
        record.setCaseId(id);
        record.setCrimeType("Theft");

        // WHEN
        when(exampleRepository.findById(id)).thenReturn(Optional.of(record));
        Crime crime = crimeService.findByCaseId(id);

        // THEN
        Assertions.assertNotNull(crime, "The object is returned");
        Assertions.assertEquals(record.getCaseId(), crime.getCaseId(), "The id matches");
        Assertions.assertEquals(record.getCrimeType(), crime.getCrimeType(), "The type matches");
    }

    @Test
    void findByConcertId_invalid() {
        // GIVEN
        String id = randomUUID().toString();

        when(exampleRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN
        Crime crime = crimeService.findByCaseId(id);

        // THEN
        Assertions.assertNull(crime, "The example is null when not found");
    }

}
