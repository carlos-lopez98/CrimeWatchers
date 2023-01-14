package com.kenzie.capstone.service;

import com.kenzie.capstone.service.converter.ZonedDateTimeConverter;
import com.kenzie.capstone.service.dao.CrimeDao;
import com.kenzie.capstone.service.model.*;
import com.kenzie.capstone.service.dao.ExampleDao;

import javax.inject.Inject;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class LambdaService {

    private CrimeDao crimeDao;

    @Inject
    public LambdaService(CrimeDao crimeDao) {
        this.crimeDao = crimeDao;
    }

//    public ExampleData getExampleData(String id) {
//        List<ExampleRecord> records = exampleDao.getExampleData(id);
//
//
//        if (records.size() > 0) {
//            return new ExampleData(records.get(0).getId(), records.get(0).getData());
//        }
//        return null;
//    }
//
//    public ExampleData setExampleData(String data) {
//        String id = UUID.randomUUID().toString();
//        ExampleRecord record = exampleDao.setExampleData(id, data);
//        return new ExampleData(id, data);
//    }

    //Calls CrimeDao to get a closed case
    public List<ClosedCrimeData> getClosedCases(String borough) {

        if(borough.isEmpty()){
            throw new IllegalArgumentException("When getting closed case, borough cannot be empty");
        }

        List<CrimeDataRecord> records = crimeDao.getClosedCases(borough);

        if(!records.isEmpty()){
            return records.stream().map(this::recordToData).collect(Collectors.toList());
        }

        return null;
    }

    //Calls CrimeDao to add a closed case
    public CrimeDataResponse addClosedCase(CrimeDataRequest crimeDataRecord) {

        if(crimeDataRecord == null){
            throw new NullPointerException("Cant retrieve null record.");
        }


        //String id = UUID.randomUUID().toString();
        String id = crimeDataRecord.getId();
        ClosedCrimeData record = crimeDao.addClosedCase(requestToRecord(crimeDataRecord));


        return dataToResponse(record);
    }

    public List<ClosedCrimeData> getAllClosedCases() {

        List<CrimeDataRecord> records = crimeDao.getAllClosedCases();

        if(!records.isEmpty()){
            return records.stream().map(this::recordToData).collect(Collectors.toList());
        }

        return null;
    }

    private ClosedCrimeData recordToData(CrimeDataRecord record) {
        ClosedCrimeData data = new ClosedCrimeData();
        data.setId(record.getId());
        data.setDateClosed(record.getDateClosed());
        data.setBorough(record.getBorough());
        data.setDescription(record.getDescription());
        data.setCrimeType(record.getCrimeType());
        data.setState(record.getState());
        data.setStatus(record.getStatus());

        return data;
    }

    private CrimeDataResponse dataToResponse(ClosedCrimeData record) {
        CrimeDataResponse response = new CrimeDataResponse();
        response.setBorough(record.getBorough());
        response.setState(record.getState());
        response.setDescription(record.getDescription());
        response.setCrimeType(record.getCrimeType());
        response.setCaseId(record.getId());
        response.setDateClosed(record.getDateClosed());
        response.setStatus(record.getStatus());

        return response;
    }

    private CrimeDataRecord requestToRecord(CrimeDataRequest request){
        CrimeDataRecord record = new CrimeDataRecord();
        record.setId(request.getId());
        record.setDateClosed(new ZonedDateTimeConverter().convert(ZonedDateTime.now()));
        record.setState(request.getState());
        record.setDescription(request.getDescription());
        record.setBorough(request.getBorough());
        record.setCrimeType(request.getCrimeType());
        record.setStatus(request.getStatus());

        return record;
    }


}
