package com.kenzie.appserver.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.CreateCrimeRequest;
import com.kenzie.appserver.controller.model.CrimeResponse;
import com.kenzie.appserver.converter.ZonedDateTimeConverter;
import com.kenzie.appserver.service.CrimeService;
import com.kenzie.appserver.service.model.Crime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.BeforeAll;
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
public class CrimeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    CrimeService crimeService;

    private static final MockNeat mockNeat = MockNeat.threadLocal();

    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    public static void setup() {
        mapper.registerModule(new Jdk8Module());
    }

    @Test
    public void getAllActiveCrimes_success() throws Exception {

        crimeService.addNewActiveCrime(new Crime("123","Something", "ca","H",
                "jlkjlk ljlkj  jkljl", "jlkj kjlk"));

        ResultActions actions = mvc.perform(get("/crimes/all")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        String responseBody = actions.andReturn().getResponse().getContentAsString();

        List<CrimeResponse> responses = mapper.readValue(responseBody, new TypeReference<List<CrimeResponse>>() {});

        assertThat(responses.size()).isGreaterThan(0);
    }

    @Test
    public void createActiveCrime_success() throws Exception {

        CreateCrimeRequest createCrimeRequest = new CreateCrimeRequest();
        createCrimeRequest.setCrimeType("123");
        createCrimeRequest.setBorough("borough");
        createCrimeRequest.setDescription("....");
        createCrimeRequest.setState("lj");
        createCrimeRequest.setCaseId("1243");
        createCrimeRequest.setZonedDateTime("kjlkjlksjd");

        ResultActions actions = mvc.perform(post("/crimes")
                        .content(mapper.writeValueAsString(createCrimeRequest))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        String responseBody = actions.andReturn().getResponse().getContentAsString();

        CrimeResponse response = mapper.readValue(responseBody, CrimeResponse.class);

        assertThat(response.getCaseId()).isNotEmpty();
    }



    @Test
    public void getCaseById_success(){

        //GIVEN

        //WHEN

        //THEN
    }
}