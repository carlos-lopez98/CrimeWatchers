package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.CrimeDao;
import com.kenzie.capstone.service.model.CrimeData;
import com.kenzie.capstone.service.model.CrimeDataRecord;
import com.kenzie.capstone.service.model.ExampleData;
import com.kenzie.capstone.service.dao.ExampleDao;
import com.kenzie.capstone.service.model.ExampleRecord;

import javax.inject.Inject;

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
        List<CrimeDataRecord> records = crimeDao.getClosedCases(borough);

        if(!records.isEmpty()){
            return records.stream().map(this::recordToData).collect(Collectors.toList());
        }

        return null;
    }

    //Calls CrimeDao to add a closed case
    public CrimeData addClosedCase(CrimeDataRecord crimeDataRecord) {

        String id = UUID.randomUUID().toString();

        CrimeDataRecord record = crimeDao.addClosedCase(crimeDataRecord);

        return new CrimeData(id, record.getBorough(), record.getState(), record.getCrimeType(), record.getDescription(), record.getTime());
    }



    private CrimeData recordToData(CrimeDataRecord record) {
        CrimeData data = new CrimeData(record.getId(), record.getBorough(), record.getState(), record.getCrimeType(),
                record.getDescription(), record.getTime());

        return data;
    }

}
