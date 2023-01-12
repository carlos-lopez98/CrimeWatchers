package com.kenzie.capstone.service;

import com.kenzie.capstone.service.converter.ZonedDateTimeConverter;
import com.kenzie.capstone.service.dao.CrimeDao;
import com.kenzie.capstone.service.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LambdaServiceTest {

    /** ------------------------------------------------------------------------
     *  expenseService.getExpenseById
     *  ------------------------------------------------------------------------ **/

    private CrimeDao crimeDao;
    private LambdaService lambdaService;

    @BeforeAll
    void setup() {
        this.crimeDao = mock(CrimeDao.class);
        this.lambdaService = new LambdaService(crimeDao);
    }

    @Test
    void addClosedCaseTest() {
        //Keeping around for reference
        //ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        //ArgumentCaptor<String> dataCaptor = ArgumentCaptor.forClass(String.class);

        ArgumentCaptor<CrimeDataRecord> recordArgumentCaptor = ArgumentCaptor.forClass(CrimeDataRecord.class);
        // GIVEN
        String data = "somedata";

        CrimeDataRecord crimeDataRecord = new CrimeDataRecord();
        crimeDataRecord.setId(String.valueOf(UUID.randomUUID()));
        crimeDataRecord.setCrimeType("Murder");
        crimeDataRecord.setBorough("New York");
        crimeDataRecord.setDescription("Killer killed someone");
        crimeDataRecord.setState("New York");
        crimeDataRecord.setTime(new ZonedDateTimeConverter().convert(ZonedDateTime.now()));

        CrimeData crimeData = new CrimeData(crimeDataRecord.getId(), crimeDataRecord.getBorough(),
                crimeDataRecord.getState(), crimeDataRecord.getCrimeType(), crimeDataRecord.getDescription(),
                crimeDataRecord.getTime());

        when(crimeDao.addClosedCase(crimeDataRecord)).thenReturn(crimeData);

        // WHEN
        CrimeDataResponse response = this.lambdaService.addClosedCase(new CrimeDataRequest(crimeDataRecord.getId(),
                crimeDataRecord.getBorough(), crimeDataRecord.getState(), crimeDataRecord.getCrimeType(), crimeDataRecord.getDescription()));

        // THEN
        verify(crimeDao, times(1)).addClosedCase(recordArgumentCaptor.capture());

        assertNotNull(recordArgumentCaptor.getValue().getId(), "An ID is generated");
        assertEquals("Killer killed someone", recordArgumentCaptor.getValue().getDescription(), "The data is saved");

        assertNotNull(response, "A response is returned");
        assertEquals(recordArgumentCaptor.getValue().getId(), response.getCaseId(), "The response id should match");
        assertEquals("Killer killed someone", response.getDescription(), "The response description should match");
    }

    @Test
    void getClosedCasesByBoroughTest() {
        ArgumentCaptor<String> boroughCaptor = ArgumentCaptor.forClass(String.class);

        // GIVEN
        String borough = "New York";
        CrimeDataRecord record = new CrimeDataRecord();
        String randomId = String.valueOf(UUID.randomUUID());
        record.setBorough(borough);
        record.setId(randomId);
        record.setDescription("Killer Killed Someone");

        when(crimeDao.getClosedCases(borough)).thenReturn(Arrays.asList(record));

        // WHEN
        List<CrimeData> crimeDataList = this.lambdaService.getClosedCases(borough);

        // THEN
        verify(crimeDao, times(1)).getClosedCases(boroughCaptor.capture());

        assertEquals(borough, boroughCaptor.getValue(), "The correct borough is used");

        //Just asserts that we get a list of crimes back
        assertNotNull(crimeDataList, "A response is returned");

        /**
         * Keeping this around for reference
         */

        // assertEquals(borough, crimeDataList.get(0).getBorough(), "Borough should be ");
        // assertEquals(data, response.getBorough(), "The response data should match");
    }

    // Write additional tests here


    @Test
    void addClosedCaseTest_throws_NullPointerException() {
        // GIVEN
        // WHEN
        // THEN
        assertThrows(NullPointerException.class,()->this.lambdaService.addClosedCase(null));
    }

    @Test
    void getClosedCasesByBoroughTest_throws_IllegalArgumentException() {
        ArgumentCaptor<String> boroughCaptor = ArgumentCaptor.forClass(String.class);

        // GIVEN
        String borough = "";

        CrimeDataRecord record = new CrimeDataRecord();
        record.setBorough(borough);


        // WHEN
        // THEN
        assertThrows(IllegalArgumentException.class,()->this.lambdaService.getClosedCases(record.getBorough()));
        verify(crimeDao, times(0)).getClosedCases(boroughCaptor.capture());


        /**
         * Keeping this around for reference
         */

        // assertEquals(borough, crimeDataList.get(0).getBorough(), "Borough should be ");
        // assertEquals(data, response.getBorough(), "The response data should match");
    }

}