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
    public List<CrimeData> getClosedCases(String borough) {

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
        CrimeData record = crimeDao.addClosedCase(requestToRecord(crimeDataRecord));


        return dataToResponse(record);
    }

    public List<CrimeData> getAllClosedCases() {

        List<CrimeDataRecord> records = crimeDao.getAllClosedCases();

        if(!records.isEmpty()){
            return records.stream().map(this::recordToData).collect(Collectors.toList());
        }

        return null;
    }

    private CrimeData recordToData(CrimeDataRecord record) {
        CrimeData data = new CrimeData(record.getId(), record.getBorough(), record.getState(), record.getCrimeType(),
                record.getDescription(), record.getTime());

        return data;
    }

    private CrimeDataResponse dataToResponse(CrimeData record) {
        CrimeDataResponse response = new CrimeDataResponse();
        response.setBorough(record.getBorough());
        response.setState(record.getState());
        response.setDescription(record.getDescription());
        response.setCrimeType(record.getCrimeType());
        response.setCaseId(record.getId());
        response.setZonedDateTime(record.getTime());

        return response;
    }

    private CrimeDataRecord requestToRecord(CrimeDataRequest request){
        CrimeDataRecord record = new CrimeDataRecord();
        record.setId(request.getId());
        record.setTime(new ZonedDateTimeConverter().convert(ZonedDateTime.now()));
        record.setState(request.getState());
        record.setDescription(request.getDescription());
        record.setBorough(request.getBorough());
        record.setCrimeType(request.getCrimeType());

        return record;
    }


}
