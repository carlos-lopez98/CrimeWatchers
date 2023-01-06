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

public class CrimeServiceTest {
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
     *  crimeService.findByCaseIdActive
     *  ------------------------------------------------------------------------ **/

    @Test
    void findByCaseIdActive() {
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
    /** ------------------------------------------------------------------------
     *  crimeService.findByCaseIdActive
     *  ------------------------------------------------------------------------ **/
    @Test
    void findByCaseIdActive_invalid() {
        // GIVEN
        String id = randomUUID().toString();

        when(crimeRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN
        Crime crime = crimeService.findByCaseIdActive(id);

        // THEN
        Assertions.assertNull(crime, "The example is null when not found");
    }

//    @Test
//    void CrimeService_findByCaseId_notNullReturnsCachedItem(){
//        //Given
//        String id = randomUUID().toString();
//        CrimeRecord record = new CrimeRecord();
//        record.setCaseId(id);
//        record.setCrimeType("Theft");
//
//        when(crimeRepository.findById(id)).thenReturn(Optional.of(record));
//
//        //When
//        Crime crime = crimeService.findByCaseIdActive(id);
//
//        //Then
//
//    }
    /** ------------------------------------------------------------------------
     *  crimeService.findAllActiveCrimes
     *  ------------------------------------------------------------------------ **/
    @Test
    void findAllActiveCrimes(){
        //GIVEN

        //WHEN

        //THEN
    }
    /** ------------------------------------------------------------------------
     *
     *  ------------------------------------------------------------------------ **/
    @Test
    void findAllClosedCrimes(){
        //GIVEN

        //WHEN

        //THEN
    }
    /** ------------------------------------------------------------------------
     *  crimeService.findByCaseIdClosed
     *  ------------------------------------------------------------------------ **/
    @Test
    void findByCaseIdClosed(){
        //GIVEN

        //WHEN

        //THEN
    }
    /** ------------------------------------------------------------------------
     *  crimeService.findByCaseIdClosed
     *  ------------------------------------------------------------------------ **/
    @Test
    void findByCaseIdClosed_doesNotExist(){
        //GIVEN

        //WHEN

        //THEN
    }
    /** ------------------------------------------------------------------------
     *  crimeService.addNewActiveCrime
     *  ------------------------------------------------------------------------ **/
    @Test
    void addNewCase(){
        String id = randomUUID().toString();

        CrimeRecord record = new CrimeRecord();
        record.setCaseId(id);
        record.setCrimeType("Theft");
    }
//    /** ------------------------------------------------------------------------
//     *  crimeService.findByCaseIdClosed
//     *  ------------------------------------------------------------------------ **/
//    @Test
//    void CrimeService_updateCase_updatesCaseAndCache(){
//        //GIVEN
//
//        //WHEN
//
//        //THEN
//    }
//
//    @Test
//    void updateCase_doesNotExist(){
//        //GIVEN
//
//        //WHEN
//
//        //THEN
//    }

//    @Test
//    void CrimeService_deleteCase_deletesCaseAndEvictsFromCache(){
//        //GIVEN
//
//        //WHEN
//
//        //THEN
//    }
    /** ------------------------------------------------------------------------
     *  crimeService.findByCrimeType
     *  ------------------------------------------------------------------------ **/
    @Test
    void findByCrimeType(){
        //GIVEN

        //WHEN

        //THEN
    }
    /** ------------------------------------------------------------------------
     *  crimeService.findByCrimeType
     *  ------------------------------------------------------------------------ **/
    @Test
    void findByCrimeType_doesNotExist(){
        //GIVEN

        //WHEN

        //THEN
    }
    /** ------------------------------------------------------------------------
     *  crimeService.findCrimeByBorough
     *  ------------------------------------------------------------------------ **/
    @Test
    void findCrimeByBorough(){
        //GIVEN

        //WHEN

        //THEN
    }

}
