package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.CreateCrimeRequest;
import com.kenzie.appserver.controller.model.CreateCrimeRequestClosed;
import com.kenzie.appserver.controller.model.CrimeResponse;
import com.kenzie.appserver.converter.ZonedDateTimeConverter;
//import com.kenzie.appserver.repositories.model.CrimeId;
import com.kenzie.appserver.service.CrimeService;


import com.kenzie.appserver.service.model.Crime;
import com.kenzie.capstone.service.model.CrimeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/crimes")
public class CrimeController {

    @Autowired
    private CrimeService crimeService;

    @GetMapping("/all")
    public ResponseEntity<List<CrimeResponse>> getAllCrimes() {

        List<Crime> allCrimes = crimeService.findAllActiveCrimes();

        List<CrimeResponse> allCrimeResponses = allCrimes.stream().map(this::crimeToResponse).collect(Collectors.toList());
        if (allCrimes == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(allCrimeResponses);
    }

    @PostMapping
    public ResponseEntity<CrimeResponse> addCrime(@RequestBody CreateCrimeRequest createCrimeRequest) {
        Crime crime = crimeService.addNewActiveCrime(requestToCrime(createCrimeRequest));

        CrimeResponse crimeResponse = new CrimeResponse();
        crimeResponse.setCaseId(crime.getCaseId());
        crimeResponse.setBorough(crime.getBorough());
        crimeResponse.setState(crime.getState());
        crimeResponse.setCrimeType(crime.getCrimeType());
        crimeResponse.setZonedDateTime(crime.getDateAndTime());
        crimeResponse.setDescription(crime.getDescription());

        return ResponseEntity.ok(crimeResponse);
    }

//    @GetMapping("/active/{crimeType}")
//    public ResponseEntity<List<CrimeResponse>> getCrimeByType(@PathVariable("crimeType") String crimeType){
//        //TODO return all crimes of crimeType
//        List<Crime> crimes = crimeService.findByCrimeType(crimeType);
//        if (crimes == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        List<CrimeResponse> crimeResponses = getCrimeResponseList(crimes);
//        return ResponseEntity.ok(crimeResponses);
//    }

    @GetMapping("/active/{id}/{borough}")
    public ResponseEntity<CrimeResponse> getCrimeById(@PathVariable("id") String id, @PathVariable("borough") String borough) {
        //TODO return all borough crimes

//        CrimeId crimeId = new CrimeId();
//        crimeId.setBorough(borough);
//        crimeId.setId(id);

        Crime crime = crimeService.findByCaseIdActive(id);

        if (crime == null) {
            return ResponseEntity.notFound().build();
        }

        CrimeResponse response = crimeToResponse(crime);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/active/borough/{borough}")
    public ResponseEntity<List<CrimeResponse>> getCrimeByBorough(@PathVariable("borough") String borough) {
        //TODO return all borough crimes
        List<Crime> crimes = crimeService.findCrimeByBorough(borough);

        if (crimes == null) {
            return ResponseEntity.notFound().build();
        }

        List<CrimeResponse> crimeResponses = getCrimeResponseList(crimes);

        return ResponseEntity.ok(crimeResponses);
    }

    @GetMapping("/closed/{borough}")
    public ResponseEntity<List<CrimeResponse>> getClosedCaseByBorough(@PathVariable("borough") String borough){

        List<CrimeData> closedCases = crimeService.getClosedCases(borough);

        List<CrimeResponse> crimeResponseList = closedCases.stream().map(this::crimeDatatoResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(crimeResponseList);
    }

    @PostMapping("/closed")
    public ResponseEntity<CrimeResponse> addClosedCrime(@RequestBody CreateCrimeRequestClosed createCrimeRequest) {
        CrimeData crimeData = crimeService.addClosedCase(createClosedRequestToCrimeData(createCrimeRequest));

        CrimeResponse crimeResponse = new CrimeResponse();
        crimeResponse.setCaseId(crimeData.getId());
        crimeResponse.setBorough(crimeData.getBorough());
        crimeResponse.setState(crimeData.getState());

        return ResponseEntity.ok(crimeResponse);
    }

    @GetMapping("/closed/all")
    public ResponseEntity<List<CrimeResponse>> getAllClosedCases(){

        List<CrimeData> closedCases = crimeService.getAllClosedCases();

        List<CrimeResponse> crimeResponseList = closedCases.stream().map(this::crimeDatatoResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(crimeResponseList);
    }

    private CrimeData requestToCrimeData(CreateCrimeRequest request){
        return new CrimeData(request.getCaseId(), request.getBorough(), request.getState(), request.getCrimeType(), request.getDescription(),
                request.getZonedDateTime());
    }

    private CrimeResponse crimeDatatoResponse(CrimeData data){
        CrimeResponse response = new CrimeResponse();
        response.setZonedDateTime(data.getTime());
        response.setState(data.getState());
        response.setDescription(data.getDescription());
        response.setCrimeType(data.getCrimeType());
        response.setBorough(data.getBorough());
        response.setCaseId(data.getId());

        return response;
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
            crimeResponse.setZonedDateTime(crime.getDateAndTime());
            responseList.add(crimeResponse);
        }

        return responseList;
    }

    private Crime requestToCrime (CreateCrimeRequest request){
        return new Crime(request.getCaseId(), request.getBorough(), request.getState()
                , request.getCrimeType(), request.getDescription(), request.getZonedDateTime());
    }

    private CrimeResponse crimeToResponse(Crime crime){
        CrimeResponse newResponse = new CrimeResponse();
        newResponse.setCaseId(crime.getCaseId());
        newResponse.setCrimeType(crime.getCrimeType());
        newResponse.setBorough(crime.getBorough());
        newResponse.setState(crime.getState());
        newResponse.setDescription(crime.getDescription());
        newResponse.setZonedDateTime(crime.getDateAndTime());
     return newResponse;
    }

    private CrimeData createClosedRequestToCrimeData(CreateCrimeRequestClosed closedRequest){
        CrimeData data = new CrimeData(closedRequest.getId(), closedRequest.getBorough(), closedRequest.getState(),
                closedRequest.getCrimeType(), closedRequest.getDescription(), new ZonedDateTimeConverter().convert(ZonedDateTime.now()));
        return data;
    }
}
