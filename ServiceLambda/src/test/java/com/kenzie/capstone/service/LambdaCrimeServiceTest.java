package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.CrimeDao;
import com.kenzie.capstone.service.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LambdaCrimeServiceTest {

    private CrimeDao crimeDao;
    private LambdaCrimeService lambdaCrimeService;

    @BeforeAll
    void setup() {
        this.crimeDao = mock(CrimeDao.class);
        this.lambdaCrimeService = new LambdaCrimeService(crimeDao);
    }

    @Test
    void setDataTest() {
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> dataCaptor = ArgumentCaptor.forClass(String.class);

        // GIVEN
        String id = "fakeid";
        String borough = "someBorough";
        String crimeType = "someCrime";
        String description = "This crime occurred";

        CrimeDataRecord record = new CrimeDataRecord();
        record.setId(id);
        record.setBorough(borough);
        record.setCrimeType(crimeType);
        record.setDescription(description);

        // WHEN
        CrimeData response = this.lambdaCrimeService.addClosedCase(record);

        // THEN
        verify(crimeDao, times(1)).addClosedCase(record);

        assertNotNull(idCaptor.getValue(), "An ID is generated");
        assertEquals(record.getId(), dataCaptor.getValue(), "The data is saved");

        assertNotNull(response, "A response is returned");
        assertEquals(idCaptor.getValue(), response.getId(), "The response id should match");
        assertEquals(dataCaptor.getValue(), response.getBorough(), "The response data should match");
    }

    @Test
    void getDataTest() {
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);

        // GIVEN
        String id = "fakeid";
        String borough = "someBorough";
        String crimeType = "someCrime";
        String description = "This crime occurred";

        CrimeDataRecord record = new CrimeDataRecord();
        record.setId(id);
        record.setBorough(borough);
        record.setCrimeType(crimeType);
        record.setDescription(description);


        when(crimeDao.getClosedCase(id)).thenReturn(Arrays.asList(record));

        // WHEN
        CrimeData response = this.lambdaCrimeService.getClosedCase(id);

        // THEN
        verify(crimeDao, times(1)).getClosedCase(idCaptor.capture());

        assertEquals(id, idCaptor.getValue(), "The correct id is used");

        assertNotNull(response, "A response is returned");
        assertEquals(id, response.getId(), "The response id should match");
        assertEquals(borough, response.getBorough(), "The borough response data should match");
        assertEquals(crimeType, response.getCrimeType(), "The crime type response data should match");
        assertEquals(description, response.getDescription(), "The description response data should match");
    }

    @Test
    void getClosedDataTest_throws_IllegalArgumentException() {

        String id = "";

        assertThrows(IllegalArgumentException.class, ()-> this.lambdaCrimeService.getClosedCase(id));
    }

    @Test
    void addClosedDataTest_throws_NullPointerException() {

        // GIVEN
        String id = "anotherFakeId";
        CrimeDataRecord record = new CrimeDataRecord();
        record.setId(id);

        when(crimeDao.addClosedCase(record)).thenReturn(null);

        //  WHEN THEN
        assertThrows(NullPointerException.class,()-> this.lambdaCrimeService.addClosedCase(record));

    }
}
