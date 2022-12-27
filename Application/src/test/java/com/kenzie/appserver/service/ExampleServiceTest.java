package com.kenzie.appserver.service;


import com.kenzie.appserver.repositories.CrimeRepository;
import com.kenzie.appserver.repositories.model.CrimeRecord;
import com.kenzie.appserver.service.model.Crime;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExampleServiceTest {
    private CrimeRepository crimeRepository;
    private CrimeService crimeService;
    private LambdaServiceClient lambdaServiceClient;

    @BeforeEach
    void setup() {
        crimeRepository = mock(CrimeRepository.class);
        lambdaServiceClient = mock(LambdaServiceClient.class);
        crimeService = new CrimeService(crimeRepository, lambdaServiceClient);
    }
    /** ------------------------------------------------------------------------
     *  exampleService.findById
     *  ------------------------------------------------------------------------ **/

    @Test
    void findByCaseId() {
        // GIVEN
        String id = randomUUID().toString();

        CrimeRecord record = new CrimeRecord();
        record.setCaseId(id);
        record.setCrimeType("Theft");

        // WHEN
        when(crimeRepository.findById(id)).thenReturn(Optional.of(record));
        Crime crime = crimeService.findByCaseIdActive(id);

        // THEN
        Assertions.assertNotNull(crime, "The object is returned");
        Assertions.assertEquals(record.getCaseId(), crime.getCaseId(), "The id matches");
        Assertions.assertEquals(record.getCrimeType(), crime.getCrimeType(), "The type matches");
    }

    @Test
    void findByCaseId_invalid() {
        // GIVEN
        String id = randomUUID().toString();

        when(crimeRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN
        Crime crime = crimeService.findByCaseIdActive(id);

        // THEN
        Assertions.assertNull(crime, "The example is null when not found");
    }

}
