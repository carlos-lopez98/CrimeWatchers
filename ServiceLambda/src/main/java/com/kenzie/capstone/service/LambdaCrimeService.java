package com.kenzie.capstone.service;

import com.kenzie.capstone.service.model.ExampleData;
import com.kenzie.capstone.service.dao.ExampleDao;
import com.kenzie.capstone.service.model.ExampleRecord;

import javax.inject.Inject;

import java.util.List;
import java.util.UUID;


//Need to implement CrimeDao
public class LambdaCrimeService {

    private CrimeDao crimeDao;

    @Inject
    public LambdaService(CrimeDao crimeDao) {
        this.crimeDao = crimeDao;
    }

    public CrimeData getExampleData(String id) {
        List<CrimeDataRecord> records = crimeDao.getExampleData(id);

        if (records.size() > 0) {
            return new CrimeData(records.get(0).getId(), records.get(0).getData());
        }
        return null;
    }

    public CrimeData setExampleData(String data) {

        String id = UUID.randomUUID().toString();

        CrimeDataRecord record = crimeDao.setExampleData(id, data);
        return new CrimeData(id, data);
    }
}
