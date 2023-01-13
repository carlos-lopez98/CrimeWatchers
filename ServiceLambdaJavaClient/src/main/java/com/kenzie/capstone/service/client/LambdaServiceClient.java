package com.kenzie.capstone.service.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.model.CrimeData;
import com.kenzie.capstone.service.model.CrimeDataRequest;
import com.kenzie.capstone.service.model.CrimeDataResponse;
import com.kenzie.capstone.service.model.ExampleData;

import java.util.List;

public class LambdaServiceClient {

    private static final String GET_CLOSED_CASE = "crimeByBorough/{borough}";
    private static final String SET_CLOSED_CASE = "crimeById/add";
    private static final String GETALL_CLOSED_CASE = "crimes/all";

    private ObjectMapper mapper;

    public LambdaServiceClient() {
        this.mapper = new ObjectMapper();
    }

    public List<CrimeData> getClosedCases(String borough) {
        EndpointUtility endpointUtility = new EndpointUtility();

        String response = endpointUtility.getEndpoint(GET_CLOSED_CASE.replace("{borough}", borough));

        List<CrimeData> crimeDataList;

        try {

            crimeDataList = mapper.readValue(response, new TypeReference<>(){});

        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }

//        CrimeDataResponse crimeDataResponse = new CrimeDataResponse();
//        crimeDataResponse.setCaseId(crimeData.getId());
//        crimeDataResponse.setBorough(crimeData.getBorough());
//        crimeDataResponse.setState(crimeData.getState());
//        crimeDataResponse.setCrimeType(crimeData.getCrimeType());
//        crimeDataResponse.setDescription(crimeData.getDescription());
//        crimeDataResponse.setZonedDateTime(crimeData.getTime().toString());

        return crimeDataList;
    }

    public CrimeDataResponse addClosedCase(CrimeDataRequest request) {
        EndpointUtility endpointUtility = new EndpointUtility();


        String newData;

        try {
            newData = mapper.writeValueAsString(request);
        } catch(JsonProcessingException e) {
            throw new ApiGatewayException("Unable to serialize request: " + e);
        }

        String response = endpointUtility.postEndpoint(SET_CLOSED_CASE,newData);
        CrimeDataResponse crimeDataResponse;

        try {
            crimeDataResponse = mapper.readValue(response, CrimeDataResponse.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }

//        CrimeDataResponse crimeDataResponse = new CrimeDataResponse();
//        crimeDataResponse.setCaseId(crimeData.getId());
//        crimeDataResponse.setBorough(crimeData.getBorough());
//        crimeDataResponse.setState(crimeData.getState());
//        crimeDataResponse.setCrimeType(crimeData.getCrimeType());
//        crimeDataResponse.setDescription(crimeData.getDescription());
//        crimeDataResponse.setZonedDateTime(crimeData.getTime().toString());

        return crimeDataResponse;
    }

    public List<CrimeData> getAllClosedCases() {

        EndpointUtility endpointUtility = new EndpointUtility();


        String response = endpointUtility.getEndpoint(GETALL_CLOSED_CASE);
        List<CrimeData> crimeDataResponse;


        try {
            crimeDataResponse = mapper.readValue(response, new TypeReference<>() {});
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }

        return crimeDataResponse;
    }
}
