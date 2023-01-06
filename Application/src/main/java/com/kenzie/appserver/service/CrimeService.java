package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.CrimeRepository;
import com.kenzie.appserver.repositories.model.CrimeRecord;
import com.kenzie.appserver.service.model.Crime;

import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.model.CrimeData;
import com.kenzie.capstone.service.model.ExampleData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CrimeService {

    private CrimeRepository crimeRepository;
    private LambdaServiceClient lambdaServiceClient;

    public CrimeService(CrimeRepository crimeRepository, LambdaServiceClient lambdaServiceClient) {
        this.crimeRepository = crimeRepository;
        this.lambdaServiceClient = lambdaServiceClient;
    }


    //Find all Active Crimes
    public List<Crime> findAllActiveCrimes(){

        // Example getting data from the local repository
        List<CrimeRecord> dataFromDynamo = (List<CrimeRecord>) crimeRepository.findAll();
        List<Crime> crimesList = new ArrayList<>();

        //Returns a list of crimes from the ActiveCrimeRepository
        for(CrimeRecord record : dataFromDynamo){
            crimesList.add(new Crime(record.getCaseId(), record.getBorough(),
                    record.getState(), record.getCrimeType(), record.getDescription(), record.getZonedDateTime()));
        }

        return crimesList;
    }

    //Closed caseIds are stored in the lambdaTable
    public Crime findByCaseIdClosed(String caseId) {

        // Example getting data from the lambda
        CrimeData dataFromLambda = lambdaServiceClient.getClosedCase(caseId);

        // Example getting data from the local repository
        Crime dataFromDynamo = crimeRepository
                .findById(caseId)
                .map(crime -> new Crime(crime.getCaseId(), crime.getBorough(),
                        crime.getState(), crime.getCrimeType(), crime.getDescription(), crime.getZonedDateTime()))
                .orElse(null);

        return dataFromDynamo;
    }

    //Returns active case from local repository
    public Crime findByCaseIdActive(String caseId) {

        Crime dataFromDynamo = crimeRepository
                .findById(caseId)
                .map(crime -> new Crime(crime.getCaseId(), crime.getBorough(),
                        crime.getState(), crime.getCrimeType(), crime.getDescription(), crime.getZonedDateTime()))
                .orElse(null);

        return dataFromDynamo;
    }

    public Crime addNewActiveCrime(Crime crime) {

        // Example sending data to the local repository
        CrimeRecord crimeRecord = new CrimeRecord();
        crimeRecord.setCaseId(crime.getCaseId());
        crimeRecord.setBorough(crime.getBorough());
        crimeRecord.setState(crime.getState());
        crimeRecord.setDescription(crime.getDescription());
        crimeRecord.setZonedDateTime(crime.getDateAndTime());
        crimeRecord.setCrimeType(crime.getCrimeType());
        crimeRepository.save(crimeRecord);

        return new Crime(crime.getCaseId(), crime.getBorough(),
                crime.getState(),crime.getCrimeType(), crime.getDescription(), crime.getDateAndTime());
    }

    public List<Crime> findByCrimeType(String crimeType) {
        //Using CrimeRepository for now
        List<CrimeRecord> dataFromDynamo = (List<CrimeRecord>) crimeRepository.findAll();
        List<Crime> crimesList = new ArrayList<>();

        //Returns a list of crimes from the ActiveCrimeRepository
        for(CrimeRecord record : dataFromDynamo){
            if(record.getCrimeType().equals(crimeType)) {
                crimesList.add(new Crime(record.getCaseId(), record.getBorough(),
                        record.getState(), record.getCrimeType(), record.getDescription(), record.getZonedDateTime()));
            }
        }

        return crimesList;


        //TODO implement findCrimeByBorough through CrudRepository
        // Example getting data from the lambda
        //ExampleData dataFromLambda = lambdaServiceClient.getExampleData(crimeType);

        // Example getting data from the local repository
//        Crime dataFromDynamo = crimeRepository
//                .findById(crimeType)
//                .map(crime -> new Crime(crime.getCaseId(), crime.getBorough(),
//                        crime.getState(), crime.getCrimeType(), crime.getDescription(), crime.getZonedDateTime()))
//                .orElse(null);

       // return dataFromDynamo;
    }

    public List<Crime> findCrimeByBorough(String borough) {
        //Using CrimeRepository for now
        List<CrimeRecord> dataFromDynamo = (List<CrimeRecord>) crimeRepository.findAll();
        List<Crime> crimesList = new ArrayList<>();

        //Returns a list of crimes from the ActiveCrimeRepository
        for(CrimeRecord record : dataFromDynamo){
            if(record.getCrimeType().equals(borough)) {
                crimesList.add(new Crime(record.getCaseId(), record.getBorough(),
                        record.getState(), record.getCrimeType(), record.getDescription(), record.getZonedDateTime()));
            }
        }

        return crimesList;

        //TODO implement findCrimeByBorough through CrudRepository
        // Example getting data from the lambda
        //ExampleData dataFromLambda = lambdaServiceClient.getExampleData(borough);

        // Example getting data from the local repository
//        Crime dataFromDynamo = crimeRepository
//                .findById(borough)
//                .map(crime -> new Crime(crime.getCaseId(), crime.getBorough(),
//                        crime.getState(), crime.getCrimeType(), crime.getDescription(), crime.getZonedDateTime()))
//                .orElse(null);
//
//        return dataFromDynamo;
    }
}
