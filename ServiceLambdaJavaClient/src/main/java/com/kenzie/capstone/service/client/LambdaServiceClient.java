package com.kenzie.capstone.service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.model.CrimeData;
import com.kenzie.capstone.service.model.CrimeDataResponse;
import com.kenzie.capstone.service.model.ExampleData;


public class LambdaServiceClient {

    private static final String GET_CLOSED_CASE = "crimes/closed/{id}";
    private static final String SET_CLOSED_CASE = "crimes/closed";

    private ObjectMapper mapper;

    public LambdaServiceClient() {
        this.mapper = new ObjectMapper();
    }

    public CrimeData getClosedCase(String caseId) {
        EndpointUtility endpointUtility = new EndpointUtility();

        String response = endpointUtility.getEndpoint(GET_CLOSED_CASE.replace("{caseId}", caseId));

        CrimeData crimeData;
        try {
            crimeData = mapper.readValue(response, CrimeData.class);
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

        return crimeData;
    }

    public CrimeData addClosedCase(CrimeData crimeData) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.postEndpoint(SET_CLOSED_CASE, crimeData);

        CrimeData newData;


        try {
            newData = mapper.readValue(response, CrimeData.class);
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

        return newData;
    }
}
