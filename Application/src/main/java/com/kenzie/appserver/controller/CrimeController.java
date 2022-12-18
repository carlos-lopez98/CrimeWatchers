package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.ExampleCreateRequest;
import com.kenzie.appserver.controller.model.ExampleResponse;
import com.kenzie.appserver.service.CrimeService;


import com.kenzie.appserver.service.model.Crime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/crimes")
public class CrimeController {

    private CrimeService crimeService;

    CrimeController(CrimeService crimeService) {
        this.crimeService = crimeService;
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
    public ResponseEntity<ExampleResponse> addCrime(@RequestBody ExampleCreateRequest exampleCreateRequest) {
        Crime crime = crimeService.addNewCrime(exampleCreateRequest.getName());

        ExampleResponse exampleResponse = new ExampleResponse();
        exampleResponse.setId(crime.getCaseId());
        exampleResponse.setName(crime.getCrimeType());

        return ResponseEntity.ok(exampleResponse);
    }

    @GetMapping("/{crimeType}")
    public ResponseEntity<ExampleResponse> getCrimeByType(@PathVariable("crimeType") String crimeType){
        Crime crime = crimeService.findByCrimeType(crimeType);
        if (crime == null) {
            return ResponseEntity.notFound().build();
        }

        ExampleResponse exampleResponse = new ExampleResponse();
        exampleResponse.setId(crime.getCaseId());
        exampleResponse.setName(crime.getCrimeType());
        return ResponseEntity.ok(exampleResponse);
    }

    @GetMapping("/{borough}")
    public ResponseEntity<ExampleResponse> getCrimeByBorough(@PathVariable("borough") String borough){
        Crime crime = crimeService.findCrimeByBorough(borough);
        if (crime == null) {
            return ResponseEntity.notFound().build();
        }

        ExampleResponse exampleResponse = new ExampleResponse();
        exampleResponse.setId(crime.getCaseId());
        exampleResponse.setName(crime.getCrimeType());
        return ResponseEntity.ok(exampleResponse);
    }
}
