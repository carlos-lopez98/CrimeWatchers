package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.ClosedCrimeResponse;
import com.kenzie.appserver.controller.model.CreateCrimeRequest;
import com.kenzie.appserver.controller.model.CreateCrimeRequestClosed;
import com.kenzie.appserver.controller.model.CrimeResponse;
import com.kenzie.appserver.converter.ZonedDateTimeConverter;
//import com.kenzie.appserver.repositories.model.CrimeId;
import com.kenzie.appserver.service.CrimeService;


import com.kenzie.appserver.service.model.Crime;
import com.kenzie.capstone.service.model.ClosedCrimeData;
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
        crimeResponse.setZonedDateTime(new ZonedDateTimeConverter().convert(ZonedDateTime.now()));
        crimeResponse.setDescription(crime.getDescription());

        return ResponseEntity.ok(crimeResponse);
    }

    @GetMapping("/active/{id}/{borough}")
    public ResponseEntity<CrimeResponse> getCrimeById(@PathVariable("id") String id, @PathVariable("borough") String borough) {
        //TODO return all borough crimes
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



    /**
     * ---------------------------------
     * ClosedCrime Methods
     * @param
     * @return
     * ---------------------------------
     */


    @GetMapping("/closed/{borough}")
    public ResponseEntity<List<ClosedCrimeResponse>> getClosedCaseByBorough(@PathVariable("borough") String borough){

        List<ClosedCrimeData> closedCases = crimeService.getClosedCases(borough);

        List<ClosedCrimeResponse> crimeResponseList = closedCases.stream().map(this::crimeDatatoResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(crimeResponseList);
    }

    @PostMapping("/closed")
    public ResponseEntity<ClosedCrimeResponse> addClosedCrime(@RequestBody CreateCrimeRequestClosed createCrimeRequest) {
        ClosedCrimeData crimeData = crimeService.addClosedCase(createClosedRequestToCrimeData(createCrimeRequest));

        ClosedCrimeResponse crimeResponse = new ClosedCrimeResponse();
        crimeResponse.setCaseId(crimeData.getId());
        crimeResponse.setBorough(crimeData.getBorough());
        crimeResponse.setState(crimeData.getState());
        crimeResponse.setCrimeType(crimeData.getCrimeType());
        crimeResponse.setDateClosed(crimeData.getDateClosed());
        crimeResponse.setStatus(crimeData.getStatus());
        crimeResponse.setDescription(crimeData.getDescription());

        return ResponseEntity.ok(crimeResponse);
    }

    @GetMapping("/closed/all")
    public ResponseEntity<List<ClosedCrimeResponse>> getAllClosedCases(){

        List<ClosedCrimeData> closedCases = crimeService.getAllClosedCases();

        List<ClosedCrimeResponse> crimeResponseList = closedCases.stream().map(this::crimeDatatoResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(crimeResponseList);
    }


    /**
     * ---------------------------------
     * Helper Methods
     * @param data
     * @return
     * ---------------------------------
     */

    private ClosedCrimeResponse crimeDatatoResponse(ClosedCrimeData data){
        ClosedCrimeResponse response = new ClosedCrimeResponse();
        response.setDateClosed(data.getDateClosed());
        response.setState(data.getState());
        response.setDescription(data.getDescription());
        response.setCrimeType(data.getCrimeType());
        response.setBorough(data.getBorough());
        response.setCaseId(data.getId());
        response.setStatus(data.getStatus());

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
                , request.getCrimeType(), request.getDescription(), new ZonedDateTimeConverter().convert(ZonedDateTime.now()));
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

    private ClosedCrimeData createClosedRequestToCrimeData(CreateCrimeRequestClosed closedRequest){
        ClosedCrimeData data = new ClosedCrimeData(closedRequest.getId(), closedRequest.getBorough(), closedRequest.getState(),
                closedRequest.getCrimeType(), closedRequest.getDescription(),
                new ZonedDateTimeConverter().convert(ZonedDateTime.now()),closedRequest.getStatus());
        return data;
    }
}
