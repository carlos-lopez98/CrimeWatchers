package com.kenzie.capstone.service.lambda;

import com.kenzie.capstone.service.dependency.ServiceComponentCrime;
import com.kenzie.capstone.service.model.CrimeData;
import com.kenzie.capstone.service.dependency.DaggerServiceComponent;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class AddClosedCase implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>  {

    static final Logger log = LogManager.getLogger();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        log.info(gson.toJson(input));

        //Added a new ServiceComponentCrime
        /**
         * CrimeServiceComponent is a spring/ Dagger dependency controller in a sense
         * Our dependency folder is instantiating and packaging up a component, from which
         * we can access different items
         *
         *-- In this case --
         * Dao Module - Instantiates a DAO
         * Service Module - Instantiates our service classes
         *
         * Then Service Component wraps them up into a component, in which we can access them from,
         * This allows us to keep reusing them instead, of having to instantiate new instances
         * in every class the we need them (they're dependencies for other classes)
         */

        ServiceComponentCrime serviceComponent = DaggerServiceComponent.create();
        LambdaCrimeService lambdaCrimeService = serviceComponent.provideLambdaCrimeService();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        String data = input.getBody();

        if (data == null || data.length() == 0) {
            return response
                    .withStatusCode(400)
                    .withBody("data is invalid");
        }

        try {
            //Changed to CrimeService & CrimeExampleData
            CrimeData crimeData = lambdaCrimeService.setExampleData(data);
            String output = gson.toJson(crimeData);

            return response
                    .withStatusCode(200)
                    .withBody(output);

        } catch (Exception e) {
            return response
                    .withStatusCode(400)
                    .withBody(gson.toJson(e.getMessage()));
        }
    }
}
