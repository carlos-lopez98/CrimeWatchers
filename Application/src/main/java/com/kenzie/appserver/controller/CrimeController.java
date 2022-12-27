package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.CreateCrimeRequest;
import com.kenzie.appserver.controller.model.CrimeResponse;
import com.kenzie.appserver.service.CrimeService;


import com.kenzie.appserver.service.model.Crime;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/crimes")
public class CrimeController {

    private LambdaServiceClient lambdaServiceClient;
    private CrimeService crimeService;

    CrimeController(CrimeService crimeService, LambdaServiceClient lambdaServiceClient) {
        this.crimeService = crimeService;
        this.lambdaServiceClient = lambdaServiceClient;
    }

   @GetMapping
    public ResponseEntity<List<Crime>> getAllCrimes() {

        List<Crime> allCrimes = crimeService.findAllCrimes();

        if (allCrimes == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(allCrimes);
    }

    @PostMapping
    public ResponseEntity<CrimeResponse> addCrime(@RequestBody CreateCrimeRequest createCrimeRequest) {
        Crime crime = crimeService.addNewActiveCrime(requestToCrime(createCrimeRequest));

        CrimeResponse crimeResponse = new CrimeResponse();
        crimeResponse.setCaseId(crime.getCaseId());
        crimeResponse.setBorough(crime.getBorough());
        crimeResponse.setState(crime.getState());

        return ResponseEntity.ok(crimeResponse);
    }

    @GetMapping("/active/{crimeType}")
    public ResponseEntity<List<CrimeResponse>> getCrimeByType(@PathVariable("crimeType") String crimeType){
        //TODO return all crimes of crimeType
        List<Crime> crimes = crimeService.findByCrimeType(crimeType);
        if (crimes == null) {
            return ResponseEntity.notFound().build();
        }

        List<CrimeResponse> crimeResponses = getCrimeResponseList(crimes);


        return ResponseEntity.ok(crimeResponses);
    }

    @GetMapping("/active/{borough}")
    public ResponseEntity<List<CrimeResponse>> getCrimeByBorough(@PathVariable("borough") String borough){
        //TODO return all borough crimes
        List<Crime> crimes = crimeService.findCrimeByBorough(borough);
        if (crimes == null) {
            return ResponseEntity.notFound().build();
        }

        List<CrimeResponse> crimeResponses = getCrimeResponseList(crimes);

        return ResponseEntity.ok(crimeResponses);
    }

    private Crime requestToCrime (CreateCrimeRequest request){
        return new Crime(request.getCaseId(), request.getBorough(), request.getState()
                , request.getCrimeType(), request.getDescription(), request.getZonedDateTime());
    }

    private List<CrimeResponse> getCrimeResponseList(List<Crime> crimes){
        List<CrimeResponse> responseList = new ArrayList<>();

        for(Crime crime : crimes){
            CrimeResponse crimeResponse = new CrimeResponse();
            crimeResponse.setCaseId(crime.getCaseId());
            crimeResponse.setBorough(crime.getBorough());
            crimeResponse.setState(crime.getState());
            crimeResponse.setCrimeType(crime.getCrimeType());
            crimeResponse.setDescription(crime.getDescription());
            responseList.add(crimeResponse);
        }

        return responseList;
    }
}
