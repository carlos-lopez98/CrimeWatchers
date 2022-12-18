package com.kenzie.appserver.controller;

import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.ExampleCreateRequest;
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

        String name = mockNeat.strings().valStr();

        Crime persistedCrime = crimeService.addNewCrime(name);
        mvc.perform(get("/crimes/{caseId}", persistedCrime.getCaseId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("caseId")
                        .isString())
                .andExpect(jsonPath("crimeType")
                        .value(is(name)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void createExample_CreateSuccessful() throws Exception {
        String name = mockNeat.strings().valStr();

        ExampleCreateRequest exampleCreateRequest = new ExampleCreateRequest();
        exampleCreateRequest.setName(name);

        mapper.registerModule(new JavaTimeModule());

        mvc.perform(post("/crimes")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(exampleCreateRequest)))
                .andExpect(jsonPath("caseId")
                        .exists())
                .andExpect(jsonPath("crimeType")
                        .value(is(name)))
                .andExpect(status().is2xxSuccessful());
    }
}