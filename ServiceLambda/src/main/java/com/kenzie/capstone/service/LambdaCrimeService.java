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


public class LambdaCrimeService {

    private CrimeDao crimeDao;

    @Inject
    public LambdaCrimeService(CrimeDao crimeDao) {
        this.crimeDao = crimeDao;
    }

    //Calls CrimeDao to get a closed case
    public CrimeData getClosedCase(String id) {
        List<CrimeDataRecord> records = crimeDao.getClosedCase(id);

        if (records.size() > 0) {
            return new CrimeData(records.get(0).getId(), records.get(0).getBorough(), records.get(0).getState(), records.get(0).getCrimeType(),
                    records.get(0).getDescription(), records.get(0).getTime());
        }

        return null;
    }

    //Calls CrimeDao to add a closed case
    public CrimeData addClosedCase(CrimeDataRecord crimeDataRecord) {

        String id = UUID.randomUUID().toString();

        CrimeDataRecord record = crimeDao.addClosedCase(crimeDataRecord);

        return new CrimeData(id, record.getBorough(), record.getState(), record.getCrimeType(), record.getDescription(), record.getTime());
    }
}