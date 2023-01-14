package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.ClosedCrimeResponse;
import com.kenzie.appserver.controller.model.CreateCrimeRequestClosed;
import com.kenzie.appserver.converter.ZonedDateTimeConverter;
import com.kenzie.appserver.repositories.CrimeRepository;
import com.kenzie.appserver.repositories.model.CrimeRecord;
import com.kenzie.appserver.service.model.Crime;

import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.model.ClosedCrimeData;
import com.kenzie.capstone.service.model.CrimeDataRequest;
import com.kenzie.capstone.service.model.CrimeDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrimeService {

    @Autowired
    private CrimeRepository crimeRepository;
    @Autowired
    private LambdaServiceClient lambdaServiceClient;

    // May have to delete
    public CrimeService(CrimeRepository crimeRepository, LambdaServiceClient lambdaServiceClient){
        this.crimeRepository = crimeRepository;
        this.lambdaServiceClient = lambdaServiceClient;
    }
    //Find all Active Crimes
    public List<Crime> findAllActiveCrimes(){

        // Example getting data from the local repository
        List<CrimeRecord> dataFromDynamo = crimeRepository.findAll();
        List<Crime> crimesList = new ArrayList<>();

        //Returns a list of crimes from the ActiveCrimeRepository
        for(CrimeRecord record : dataFromDynamo){
            crimesList.add(new Crime(record.getId(), record.getBorough(),
                    record.getState(), record.getCrimeType(), record.getDescription(), record.getZonedDateTime()));
        }

        return crimesList;
    }

    //Returns active case from local repository
    public Crime findByCaseIdActive(String id) {

        CrimeRecord dataFromDynamo = crimeRepository
                .findById(id);

        return crimeRecordToCrime(dataFromDynamo);
    }

    public Crime addNewActiveCrime(Crime crime) {


        List<Crime> allCrimes = this.findAllActiveCrimes();

        String id = String.valueOf (allCrimes.size() + 1);

        // Example sending data to the local repository
        CrimeRecord crimeRecord = new CrimeRecord();
        crimeRecord.setId(id);
        crimeRecord.setBorough(crime.getBorough());
        crimeRecord.setState(crime.getState());
        crimeRecord.setDescription(crime.getDescription());
        crimeRecord.setZonedDateTime(crime.getDateAndTime());
        crimeRecord.setCrimeType(crime.getCrimeType());
        crimeRepository.save(crimeRecord);

        return new Crime(id, crime.getBorough(),
                crime.getState(),crime.getCrimeType(), crime.getDescription(), crime.getDateAndTime());
    }

//    public List<Crime> findByCrimeType(String crimeType) {
//        //Using CrimeRepository for now
//        List<CrimeRecord> dataFromDynamo = (List<CrimeRecord>) crimeRepository.findAll();
//        List<Crime> crimesList = new ArrayList<>();
//
//        //Returns a list of crimes from the ActiveCrimeRepository
//        for(CrimeRecord record : dataFromDynamo){
//            if(record.getCrimeType().equals(crimeType)) {
//                crimesList.add(new Crime(record.getId(), record.getBorough(),
//                        record.getState(), record.getCrimeType(), record.getDescription(), record.getZonedDateTime()));
//            }
//        }
//
//        return crimesList;
//    }

    public List<Crime> findCrimeByBorough(String borough) {

        //Using CrimeRepository for now
        List<CrimeRecord> records = crimeRepository.findByBorough(borough);

        return records.stream().map(this::crimeRecordToCrime).collect(Collectors.toList());
    }

    public List<ClosedCrimeData> getClosedCases(String borough) {

        List<ClosedCrimeData> crimeDataList = lambdaServiceClient.getClosedCases(borough);

        if(crimeDataList.isEmpty()){
            throw new RuntimeException("LambdaServiceClient is not finding any cases for that borough");
        }

        return crimeDataList;
    }

    public List<ClosedCrimeData> getAllClosedCases() {

        List<ClosedCrimeData> allClosedCrimes = lambdaServiceClient.getAllClosedCases();

        return allClosedCrimes;
    }

    public ClosedCrimeData addClosedCase(ClosedCrimeData crimeData) {
        CrimeDataResponse addedCrime = lambdaServiceClient.addClosedCase(dataToRequest(crimeData));

        ClosedCrimeData data = crimeResponseToData(addedCrime);
        return data;
    }

    private Crime crimeRecordToCrime(CrimeRecord record){
        if(record== null){
            return null;
        }
        Crime crime = new Crime(record.getId(), record.getBorough(), record.getState(), record.getCrimeType(),
                record.getDescription(), record.getZonedDateTime());

        return crime;
    }

    private ClosedCrimeData crimeResponseToData(CrimeDataResponse response){
        ClosedCrimeData data = new ClosedCrimeData(response.getCaseId(), response.getBorough(), response.getState(),
                response.getCrimeType(), response.getDescription(), response.getDateClosed(), response.getStatus());
        return data;
    }

    private CrimeDataRequest dataToRequest(ClosedCrimeData data){

        CrimeDataRequest request = new CrimeDataRequest(data.getId(), data.getBorough(),
                data.getState(), data.getCrimeType(), data.getDescription(), data.getDateClosed(), data.getStatus());


        return request;
    }

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
