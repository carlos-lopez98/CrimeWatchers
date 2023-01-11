package com.kenzie.capstone.service.converter;

import com.kenzie.capstone.service.model.CrimeData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class JsonStringToCrimeRecordConverter {

    public CrimeData convert(String body) {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            CrimeData crimeDataFromJson = gson.fromJson(body, CrimeData.class);
            return crimeDataFromJson;
        } catch (Exception e) {
            throw new IllegalArgumentException("Referral could not be deserialized");
        }
    }
}
