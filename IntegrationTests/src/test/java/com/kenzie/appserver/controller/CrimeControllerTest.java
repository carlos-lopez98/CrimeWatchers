package com.kenzie.appserver.controller;

import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.CreateCrimeRequest;
import com.kenzie.appserver.service.CrimeService;
import com.kenzie.appserver.service.model.Crime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IntegrationTest
class CrimeControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    CrimeService crimeService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getById_Exists() throws Exception {

        String crimeType = mockNeat.strings().valStr();
        String description = mockNeat.strings().valStr();
        Crime crime = new Crime("12345", "That Borough", "NewState"
                , crimeType,description, "2022/12/25");

        Crime persistedCrime = crimeService.addNewActiveCrime(crime);
        mvc.perform(get("/crimes/{caseId}", persistedCrime.getCaseId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("caseId")
                        .isString())
                .andExpect(jsonPath("crimeType")
                        .value(is(crimeType)))
                .andExpect(jsonPath("description")
                        .value(is(description)))
                .andExpect(status().is2xxSuccessful());
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
    public void updateCase_success() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    public void updateCase_invalidCase(){

        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    public void getAllCases_success() throws Exception {
        //GIVEN
        CreateCrimeRequest crimeRequest = new CreateCrimeRequest();
//        crimeRequest.setCaseId(mockNeat.names();
        //WHEN

        //THEN
    }

    @Test
    public void getCaseById_success(){

        //GIVEN

        //WHEN

        //THEN
    }
}