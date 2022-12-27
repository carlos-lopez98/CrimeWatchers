package com.kenzie.capstone.service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.model.CrimeData;
import com.kenzie.capstone.service.model.CrimeDataResponse;
import com.kenzie.capstone.service.model.ExampleData;


public class LambdaServiceClient {

    private static final String GET_EXAMPLE_ENDPOINT = "crimes/{id}";
    private static final String SET_EXAMPLE_ENDPOINT = "crimes";

    private ObjectMapper mapper;

    public LambdaServiceClient() {
        this.mapper = new ObjectMapper();
    }

    public CrimeData getClosedCase(String caseId) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.getEndpoint(GET_EXAMPLE_ENDPOINT.replace("{caseId}", caseId));

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

    public CrimeData addClosedCase(String caseData) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.postEndpoint(SET_EXAMPLE_ENDPOINT, caseData);
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
}
