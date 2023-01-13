package com.kenzie.appserver.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.CreateCrimeRequest;
import com.kenzie.appserver.controller.model.CrimeResponse;
import com.kenzie.appserver.converter.ZonedDateTimeConverter;
import com.kenzie.appserver.service.CrimeService;
import com.kenzie.appserver.service.model.Crime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.ZonedDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;


@IntegrationTest
class CrimeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    CrimeService crimeService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getAllActiveCrimes_success() throws Exception {

        Crime crimeToAdd = new Crime(mockNeat.uuids().get(), mockNeat.names().get(), mockNeat.names().get(),
                mockNeat.names().get(), mockNeat.names().get(), mockNeat.names().get());

        crimeService.addNewActiveCrime(crimeToAdd);

        ResultActions actions = mvc.perform(get("/crimes/all")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        String responseBody = actions.andReturn().getResponse().getContentAsString();

        List<CrimeResponse> responses = mapper.readValue(responseBody, new TypeReference<List<CrimeResponse>>() {});

        assertThat(responses.size()).isGreaterThan(0);
    }

    @Test
    public void createExample_CreateSuccessful() throws Exception {
//        String name = mockNeat.strings().valStr();
//
//        ExampleCreateRequest exampleCreateRequest = new ExampleCreateRequest();
//        exampleCreateRequest.setName(name);
//
//        mapper.registerModule(new JavaTimeModule());
//
//        mvc.perform(post("/crimes")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(mapper.writeValueAsString(exampleCreateRequest)))
//                .andExpect(jsonPath("caseId")
//                        .exists())
//                .andExpect(jsonPath("crimeType")
//                        .value(is(name)))
//                .andExpect(status().is2xxSuccessful());
    }



    @Test
    public void getCaseById_success(){

        //GIVEN

        //WHEN

        //THEN
    }
}