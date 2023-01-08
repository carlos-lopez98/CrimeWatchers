package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.CreateCrimeRequest;
import com.kenzie.appserver.controller.model.CrimeResponse;
import com.kenzie.appserver.converter.ZonedDateTimeConverter;
import com.kenzie.appserver.service.CrimeService;


import com.kenzie.appserver.service.model.Crime;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.model.CrimeData;
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

   @GetMapping("/all")
    public ResponseEntity<List<Crime>> getAllCrimes() {

        List<Crime> allCrimes = crimeService.findAllActiveCrimes();

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
    public ResponseEntity<List<CrimeResponse>> getCrimeByBorough(@PathVariable("borough") String borough) {
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


    @GetMapping("/closed/{id}")
    public ResponseEntity<CrimeResponse> getClosedCaseById(@PathVariable("id") String id){

        CrimeData closedCase = lambdaServiceClient.getClosedCase(id);

        CrimeResponse response = new CrimeResponse();
        response.setBorough(closedCase.getBorough());
        response.setCaseId(closedCase.getId());
        response.setCrimeType(closedCase.getCrimeType());
        response.setDescription(closedCase.getDescription());
        response.setState(closedCase.getState());

        response.setZonedDateTime(new ZonedDateTimeConverter().convert(closedCase.getTime()));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/closed")
    public ResponseEntity<CrimeResponse> addClosedCrime(@RequestBody CreateCrimeRequest createCrimeRequest) {
        CrimeData crimeData = lambdaServiceClient.addClosedCase(requestToCrimeData(createCrimeRequest));

        CrimeResponse crimeResponse = new CrimeResponse();
        crimeResponse.setCaseId(crimeData.getId());
        crimeResponse.setBorough(crimeData.getBorough());
        crimeResponse.setState(crimeData.getState());

        return ResponseEntity.ok(crimeResponse);
    }

    private CrimeData requestToCrimeData(CreateCrimeRequest request){
        return new CrimeData(request.getCaseId(), request.getBorough(), request.getState(), request.getCrimeType(), request.getDescription(),
                new ZonedDateTimeConverter().unconvert(request.getZonedDateTime()));
    }
}
