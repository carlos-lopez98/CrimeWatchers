package com.kenzie.appserver.service;


import com.kenzie.appserver.repositories.CrimeRepository;
//import com.kenzie.appserver.repositories.model.CrimeId;
import com.kenzie.appserver.repositories.model.CrimeRecord;
import com.kenzie.appserver.service.model.Crime;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.model.CrimeData;
import com.kenzie.capstone.service.model.CrimeDataRequest;
import com.kenzie.capstone.service.model.CrimeDataResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;

public class CrimeServiceTest {
    private CrimeRepository crimeRepository;
    private CrimeService crimeService;
    private LambdaServiceClient lambdaServiceClient;

    @BeforeEach
    void setup() {
        crimeRepository = mock(CrimeRepository.class);
        lambdaServiceClient = mock(LambdaServiceClient.class);
        crimeService = new CrimeService(crimeRepository, lambdaServiceClient); //May have to delete if it breaks
        //crimeService = mock(CrimeService.class);
    }
    /** ------------------------------------------------------------------------
     *  crimeService.findByCaseIdActive
     *  ------------------------------------------------------------------------ **/

    @Test
    void findByCaseIdActive() {
        // GIVEN
        String id = randomUUID().toString();
        String borough = "Manhattan";
        String state = "New York";
        String crimeType = "Theft";
        String description = "some body stealing something";
        String zonedDateTime = "some date";

        Crime crime = new Crime(id, borough, state, crimeType, description, zonedDateTime);


//        CrimeId crimeId = new CrimeId();
//        crimeId.setId(id);
//        crimeId.setBorough("Anywhere");

        CrimeRecord record = new CrimeRecord();
        record.setId(crime.getCaseId());
        record.setBorough(crime.getBorough());
        record.setState(crime.getState());
        record.setCrimeType(crime.getCrimeType());
        record.setDescription(crime.getDescription());
        record.setZonedDateTime(crime.getDateAndTime());



        // WHEN

        when(crimeRepository.findById(id)).thenReturn(record);
        Crime getCrime = crimeService.findByCaseIdActive(id);

        // THEN
        Assertions.assertNotNull(getCrime, "The object is returned");
        Assertions.assertEquals(record.getId(), crime.getCaseId(), "The id matches");
        Assertions.assertEquals(record.getCrimeType(), crime.getCrimeType(), "The type matches");
    }
    /** ------------------------------------------------------------------------
     *  crimeService.findByCaseIdActive
     *  ------------------------------------------------------------------------ **/
    @Test
    void findByCaseIdActive_invalid() {
        // GIVEN
        String id = "12345anjdskiwje44";

        when(crimeRepository.findById(id)).thenReturn(null);

        // WHEN THEN
        Assertions.assertNull(crimeService.findByCaseIdActive(id), "The example is null when not found");
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
        List<CrimeRecord> crimeRecords = new ArrayList<>();

        CrimeRecord record1 = new CrimeRecord();
        record1.setId("1029389laksjd");
        record1.setBorough("thisBorough");
        record1.setState("New York");
        record1.setCrimeType("Murder");
        record1.setDescription("some one was killed");
        record1.setZonedDateTime("yesterday");


        CrimeRecord record2 = new CrimeRecord();
        record2.setId("293847qpwoeir");
        record2.setBorough("thatBorough");
        record2.setState("New York");
        record2.setCrimeType("Arson");
        record2.setDescription("a house was set on fire");
        record2.setZonedDateTime("today");

        crimeRecords.add(record1);
        crimeRecords.add(record2);

        when(crimeRepository.findAll()).thenReturn(crimeRecords);
        //WHEN
        List<Crime> crimesList =  crimeService.findAllActiveCrimes();
        //THEN
        Assertions.assertNotNull(crimesList, "List of crimes not returned");
        Assertions.assertTrue(crimesList.size() > 1, "Crimes List is not populated with enough crimes");
        Assertions.assertEquals(crimesList.get(0).getCaseId(), record1.getId());
        Assertions.assertEquals(crimesList.get(1).getCaseId(), record2.getId());
    }
    /** ------------------------------------------------------------------------
     *
     *  ------------------------------------------------------------------------ **/
    @Test
    void findAllClosedCrimes(){
        //GIVEN
        String borough = "thisBorough";
        List<CrimeData> crimeDataList = new ArrayList<>();

        CrimeData crimeData1 = new CrimeData("1029389laksjd",borough,"New York"
                ,"Murder","some one was killed","yesterday");

        CrimeData crimeData2 = new CrimeData("293847qpwoeir",borough,"New York"
                ,"Arson","a house was set on fire","today");

        crimeDataList.add(crimeData1);
        crimeDataList.add(crimeData2);

        when(lambdaServiceClient.getClosedCases(borough)).thenReturn(crimeDataList);
        //WHEN
        List<CrimeData> actualDataList = crimeService.getClosedCases(borough);
        //THEN
        Assertions.assertNotNull(actualDataList, "List of crimes not returned");
        Assertions.assertTrue(actualDataList.size() > 1, "Crimes List is not populated with enough crimes");
        Assertions.assertEquals(crimeDataList.get(0).getId(), crimeData1.getId());
        Assertions.assertEquals(crimeDataList.get(0).getBorough(), crimeData1.getBorough());
        Assertions.assertEquals(crimeDataList.get(1).getId(), crimeData2.getId());
        Assertions.assertEquals(crimeDataList.get(1).getBorough(), crimeData2.getBorough());
    }
    /** ------------------------------------------------------------------------
     *  crimeService.getClosedCases
     *  ------------------------------------------------------------------------ **/
    @Test
    void getClosedCases_throws_RunTimeException(){
        //GIVEN
        String borough = "No Crime Borough";
        List<CrimeData> emptyCrimeList =  new ArrayList<>();

        when(lambdaServiceClient.getClosedCases(borough)).thenReturn(emptyCrimeList);
        //WHEN

        //THEN
        Assertions.assertThrows(RuntimeException.class,()-> this.crimeService.getClosedCases(borough));
    }
    /** ------------------------------------------------------------------------
     *  crimeService.getClosedCases
     *  ------------------------------------------------------------------------ **/
    @Test
    void getClosedCases(){
        //GIVEN
        String borough = "thisBorough";
        List<CrimeData> crimeDataList = new ArrayList<>();

        CrimeData crimeData1 = new CrimeData("1029389laksjd",borough,"New York"
                ,"Murder","some one was killed","yesterday");

        CrimeData crimeData2 = new CrimeData("293847qpwoeir",borough,"New York"
                ,"Arson","a house was set on fire","today");

        crimeDataList.add(crimeData1);
        crimeDataList.add(crimeData2);

        when(lambdaServiceClient.getClosedCases(borough)).thenReturn(crimeDataList);
        //WHEN

        //THEN
        Assertions.assertNotNull(this.crimeService.getClosedCases(borough), "CrimeDataList Should not be null");
    }
    /** ------------------------------------------------------------------------
     *  crimeService.addNewActiveCrime
     *  ------------------------------------------------------------------------ **/
    @Test
    void addNewActiveCrime(){

        String id = "5678hell0abc";
        String borough = "Manhattan";
        String state = "New York";
        String crimeType = "Theft";
        String description = "some body stealing something";
        String zonedDateTime = "some date";

        Crime crime = new Crime(id, borough, state, crimeType, description, zonedDateTime);

        CrimeRecord record1 = new CrimeRecord();
        record1.setId(crime.getCaseId());
        record1.setBorough(crime.getBorough());
        record1.setState(crime.getState());
        record1.setCrimeType(crime.getCrimeType());
        record1.setDescription(crime.getDescription());
        record1.setZonedDateTime(crime.getDateAndTime());

        Assertions.assertEquals(this.crimeService.addNewActiveCrime(crime).getCaseId(), crime.getCaseId());
        verify(crimeRepository).save(record1);
        Assertions.assertEquals(this.crimeService.addNewActiveCrime(crime).getBorough(), crime.getBorough());
        Assertions.assertEquals(this.crimeService.addNewActiveCrime(crime).getDescription(), crime.getDescription());

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
     *  crimeService.crimeRecordToCrime
     *  ------------------------------------------------------------------------ **/
    @Test
    void crimeRecordToCrime(){
        //GIVEN
        CrimeRecord record = new CrimeRecord();
        record.setId("293847qpwoeir");
        record.setBorough("thatBorough");
        record.setState("New York");
        record.setCrimeType("Arson");
        record.setDescription("a house was set on fire");
        record.setZonedDateTime("today");

        when(crimeRepository.findById(record.getId())).thenReturn(record);
        //WHEN

        Crime testCrime = new Crime(record.getId(), record.getBorough(), record.getState()
                ,record.getCrimeType(), record.getDescription(), record.getZonedDateTime());
        //THEN

        Assertions.assertEquals(this.crimeService.findByCaseIdActive(record.getId()).getCaseId(), testCrime.getCaseId());
    }

    @Test
    void crimeRecordToCrime_returnsNull(){
        CrimeRecord record = new CrimeRecord();
        record.setId("293847qpwoeir");
        record.setBorough("thatBorough");
        record.setState("New York");
        record.setCrimeType("Arson");
        record.setDescription("a house was set on fire");
        record.setZonedDateTime("today");

        when(crimeRepository.findById(record.getId())).thenReturn(null);
        //WHEN


        //THEN

        Assertions.assertNull(this.crimeService.findByCaseIdActive(record.getId()));
    }

    @Test
    void findCrimeByBorough(){

        List<CrimeRecord> records =  new ArrayList<>();
        CrimeRecord record = new CrimeRecord();
        record.setId("293847qpwoeir");
        record.setBorough("thatBorough");
        record.setState("New York");
        record.setCrimeType("Arson");
        record.setDescription("a house was set on fire");
        record.setZonedDateTime("today");

        records.add(record);

        when(crimeRepository.findByBorough(record.getId())).thenReturn(records);
        //WHEN

        List<Crime> crimes = this.crimeService.findCrimeByBorough(record.getId());
        //THEN

        Assertions.assertTrue(crimes.size() > 0);
        Assertions.assertEquals(crimes.get(0).getCaseId(),record.getId());
    }


//    @Test
//    void addClosedCase(){
//        //GIVEN
//
//        String id = "5678hell0abc";
//        String borough = "Manhattan";
//        String state = "New York";
//        String crimeType = "Theft";
//        String description = "some body stealing something";
//        String zonedDateTime = "some date";
//
//        Crime crime = new Crime(id, borough, state, crimeType, description, zonedDateTime);
//        CrimeData data = new CrimeData(id, borough, state, crimeType, description, zonedDateTime);
//
//        CrimeDataRequest request = new CrimeDataRequest(data.getId(), data.getBorough(),
//                data.getState(), data.getCrimeType(), data.getDescription());
//
//        CrimeDataResponse response =  new CrimeDataResponse();
//        response.setCaseId(data.getId());
//        response.setBorough(data.getBorough());
//        response.setState(data.getState());
//        response.setCrimeType(data.getCrimeType());
//        response.setDescription(data.getDescription());
//
//
//        when(lambdaServiceClient.addClosedCase(request)).thenReturn(response);
//
//        //WHEN
//
//        CrimeData actualCrimeData = this.crimeService.addClosedCase(data);
//
//        //THEN
//        Assertions.assertNotNull(actualCrimeData, "crime data should not be null");
//
//    }

    //Methods are private, dont need to test
    /** ------------------------------------------------------------------------
     *  crimeService.crimeResponseToData
     *  ------------------------------------------------------------------------ **/
//    @Test
//    void crimeResponseToData(){
//        //GIVEN
//
//        //WHEN
//
//        //THEN
//    }
    /** ------------------------------------------------------------------------
     *  crimeService.dataToRequest
     *  ------------------------------------------------------------------------ **/
//    @Test
//    void dataToRequest(){
//        //GIVEN
//
//        //WHEN
//
//        //THEN
//    }

}
