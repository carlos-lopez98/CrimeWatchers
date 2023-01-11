package com.kenzie.capstone.service.converter;

import com.kenzie.capstone.service.model.CrimeData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kenzie.capstone.service.model.CrimeDataRequest;


public class JsonStringToCrimeRecordConverter {

    public CrimeDataRequest convert(String body) {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            CrimeDataRequest crimeDataFromJson = gson.fromJson(body, CrimeDataRequest.class);
            return crimeDataFromJson;
        } catch (Exception e) {
            throw new IllegalArgumentException("Referral could not be deserialized");
        }
    }
}
