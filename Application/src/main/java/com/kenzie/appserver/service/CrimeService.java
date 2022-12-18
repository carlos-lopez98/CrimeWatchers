package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.model.CrimeRecord;
import com.kenzie.appserver.repositories.ExampleRepository;
import com.kenzie.appserver.service.model.Crime;

import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.model.ExampleData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CrimeService {
    private ExampleRepository exampleRepository;
    private LambdaServiceClient lambdaServiceClient;

    public CrimeService(ExampleRepository exampleRepository, LambdaServiceClient lambdaServiceClient) {
        this.exampleRepository = exampleRepository;
        this.lambdaServiceClient = lambdaServiceClient;
    }


    public List<Crime> findAllCrimes(){

        // Example getting data from the local repository
        List<CrimeRecord> dataFromDynamo = (List<CrimeRecord>) exampleRepository.findAll();
        List<Crime> crimesList = new ArrayList<>();

        for(CrimeRecord record : dataFromDynamo){
            crimesList.add(new Crime(record.getCaseId(), record.getBorough(),
                    record.getState(), record.getCrimeType(), record.getDescription(), record.getZonedDateTime()));
        }

        return crimesList;
    }

    public Crime findByCaseId(String caseId) {

        // Example getting data from the lambda
        ExampleData dataFromLambda = lambdaServiceClient.getExampleData(caseId);

        // Example getting data from the local repository
        Crime dataFromDynamo = exampleRepository
                .findById(caseId)
                .map(crime -> new Crime(crime.getCaseId(), crime.getBorough(),
                        crime.getState(), crime.getCrimeType(), crime.getDescription(), crime.getZonedDateTime()))
                .orElse(null);

        return dataFromDynamo;
    }

    public Crime addNewCrime(String crimeType) {
        // Example sending data to the lambda
        ExampleData dataFromLambda = lambdaServiceClient.setExampleData(crimeType);

        // Example sending data to the local repository
        CrimeRecord crimeRecord = new CrimeRecord();
        crimeRecord.setCaseId(dataFromLambda.getCaseId());
        crimeRecord.setBorough(dataFromLambda.getBorough());
        crimeRecord.setState(dataFromLambda.getState());
        crimeRecord.setDescription(dataFromLambda.getDescription());
        crimeRecord.setZonedDateTime(dataFromLambda.getDateAndTime());
        crimeRecord.setCrimeType(dataFromLambda.getCrimeType());
        exampleRepository.save(crimeRecord);

        return new Crime(dataFromLambda.getCaseId(), dataFromLambda.getBorough(),
                dataFromLambda.getState(), crimeType, dataFromLambda.getDescription(), dataFromLambda.getDateAndTime());
    }

    public Crime findByCrimeType(String crimeType) {
        //TODO implement findByCrimeType through CrudRepository
        // Example getting data from the lambda
        ExampleData dataFromLambda = lambdaServiceClient.getExampleData(crimeType);

        // Example getting data from the local repository
        Crime dataFromDynamo = exampleRepository
                .findById(crimeType)
                .map(crime -> new Crime(crime.getCaseId(), crime.getBorough(),
                        crime.getState(), crime.getCrimeType(), crime.getDescription(), crime.getZonedDateTime()))
                .orElse(null);

        return dataFromDynamo;
    }

    public Crime findCrimeByBorough(String borough) {
        //TODO implement findCrimeByBorough through CrudRepository
        // Example getting data from the lambda
        ExampleData dataFromLambda = lambdaServiceClient.getExampleData(borough);

        // Example getting data from the local repository
        Crime dataFromDynamo = exampleRepository
                .findById(borough)
                .map(crime -> new Crime(crime.getCaseId(), crime.getBorough(),
                        crime.getState(), crime.getCrimeType(), crime.getDescription(), crime.getZonedDateTime()))
                .orElse(null);

        return dataFromDynamo;
    }
}
