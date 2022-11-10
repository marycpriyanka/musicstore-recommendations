package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.TrackRecommendation;
import com.company.musicstorerecommendations.repository.TrackRecommendationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TrackRecommendationController.class)
public class TrackRecommendationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrackRecommendationRepository trackRecommendationRepository;

    private ObjectMapper mapper = new ObjectMapper();

    private TrackRecommendation inputTrackRecommendation1;
    private TrackRecommendation inputTrackRecommendation2;
    private String inputTrackRecommendation1Json;

    private TrackRecommendation outputTrackRecommendation1;
    private TrackRecommendation outputTrackRecommendation2;
    private String outputTrackRecommendation1Json;

    private List<TrackRecommendation> outputTrackRecommendationList = new ArrayList<>();
    private String outputTrackRecommendationListJson;

    @Before
    public void setUp() throws Exception {
        inputTrackRecommendation1 = new TrackRecommendation();
        inputTrackRecommendation1.setTrackId(1);
        inputTrackRecommendation1.setUserId(1);
        inputTrackRecommendation1.setLiked(true);

        inputTrackRecommendation2 = new TrackRecommendation();
        inputTrackRecommendation2.setTrackId(2);
        inputTrackRecommendation2.setUserId(2);
        inputTrackRecommendation2.setLiked(true);

        inputTrackRecommendation1Json = mapper.writeValueAsString(inputTrackRecommendation1);

        outputTrackRecommendation1 = new TrackRecommendation();
        outputTrackRecommendation1.setTrackRecommendationId(1);
        outputTrackRecommendation1.setTrackId(1);
        outputTrackRecommendation1.setUserId(1);
        outputTrackRecommendation1.setLiked(true);

        outputTrackRecommendation2 = new TrackRecommendation();
        outputTrackRecommendation2.setTrackRecommendationId(2);
        outputTrackRecommendation2.setTrackId(2);
        outputTrackRecommendation2.setUserId(2);
        outputTrackRecommendation2.setLiked(true);

        outputTrackRecommendation1Json = mapper.writeValueAsString(outputTrackRecommendation1);

        outputTrackRecommendationList.add(outputTrackRecommendation1);
        outputTrackRecommendationList.add(outputTrackRecommendation2);

        outputTrackRecommendationListJson = mapper.writeValueAsString(outputTrackRecommendationList);
    }

    @Test
    public void shouldCreateTrackRecommendation() throws Exception {
        doReturn(outputTrackRecommendation1).when(trackRecommendationRepository).save(inputTrackRecommendation1);

        mockMvc.perform(
                post("/trackRecommendations")
                        .content(inputTrackRecommendation1Json)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputTrackRecommendation1Json));
    }

    @Test
    public void shouldReturnAllTrackRecommendations() throws Exception {
        doReturn(outputTrackRecommendationList).when(trackRecommendationRepository).findAll();

        mockMvc.perform(get("/trackRecommendations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputTrackRecommendationListJson));
    }

    @Test
    public void shouldReturnTrackRecommendationById() throws Exception {
        doReturn(Optional.of(outputTrackRecommendation1)).when(trackRecommendationRepository).findById(1);

        mockMvc.perform(get("/trackRecommendations/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputTrackRecommendation1Json));
    }

    @Test
    public void shouldUpdateTrackRecommendation() throws Exception {
        inputTrackRecommendation1.setTrackRecommendationId(1);
        inputTrackRecommendation1.setLiked(false);
        String inputJson = mapper.writeValueAsString(inputTrackRecommendation1);

        mockMvc.perform(
                put("/trackRecommendations")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteTrackRecommendationById() throws Exception {
        mockMvc.perform(delete("/trackRecommendations/2"))
                .andExpect(status().isNoContent());

        verify(trackRecommendationRepository).deleteById(2);
    }
}