package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.ArtistRecommendation;
import com.company.musicstorerecommendations.repository.ArtistRecommendationRepository;
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
@WebMvcTest(ArtistRecommendationController.class)
public class ArtistRecommendationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArtistRecommendationRepository artistRecommendationRepository;

    private ObjectMapper mapper = new ObjectMapper();

    private ArtistRecommendation inputArtistRecommendation1;
    private ArtistRecommendation inputArtistRecommendation2;
    private String inputArtistRecommendation1Json;

    private ArtistRecommendation outputArtistRecommendation1;
    private ArtistRecommendation outputArtistRecommendation2;
    private String outputArtistRecommendation1Json;

    private List<ArtistRecommendation> outputArtistRecommendationList = new ArrayList<>();
    private String outputArtistRecommendationListJson;

    @Before
    public void setUp() throws Exception {
        inputArtistRecommendation1 = new ArtistRecommendation();
        inputArtistRecommendation1.setArtistId(1);
        inputArtistRecommendation1.setUserId(1);
        inputArtistRecommendation1.setLiked(true);

        inputArtistRecommendation2 = new ArtistRecommendation();
        inputArtistRecommendation2.setArtistId(2);
        inputArtistRecommendation2.setUserId(2);
        inputArtistRecommendation2.setLiked(true);

        inputArtistRecommendation1Json = mapper.writeValueAsString(inputArtistRecommendation1);

        outputArtistRecommendation1 = new ArtistRecommendation();
        outputArtistRecommendation1.setArtistRecommendationId(1);
        outputArtistRecommendation1.setArtistId(1);
        outputArtistRecommendation1.setUserId(1);
        outputArtistRecommendation1.setLiked(true);

        outputArtistRecommendation2 = new ArtistRecommendation();
        outputArtistRecommendation2.setArtistRecommendationId(2);
        outputArtistRecommendation2.setArtistId(2);
        outputArtistRecommendation2.setUserId(2);
        outputArtistRecommendation2.setLiked(true);

        outputArtistRecommendation1Json = mapper.writeValueAsString(outputArtistRecommendation1);

        outputArtistRecommendationList.add(outputArtistRecommendation1);
        outputArtistRecommendationList.add(outputArtistRecommendation2);

        outputArtistRecommendationListJson = mapper.writeValueAsString(outputArtistRecommendationList);
    }

    @Test
    public void shouldCreateArtistRecommendation() throws Exception {
        doReturn(outputArtistRecommendation1).when(artistRecommendationRepository).save(inputArtistRecommendation1);

        mockMvc.perform(
                post("/artistRecommendations")
                        .content(inputArtistRecommendation1Json)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputArtistRecommendation1Json));
    }

    @Test
    public void shouldReturnAllArtistRecommendations() throws Exception {
        doReturn(outputArtistRecommendationList).when(artistRecommendationRepository).findAll();

        mockMvc.perform(get("/artistRecommendations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputArtistRecommendationListJson));
    }

    @Test
    public void shouldReturnArtistRecommendationById() throws Exception {
        doReturn(Optional.of(outputArtistRecommendation1)).when(artistRecommendationRepository).findById(1);

        mockMvc.perform(get("/artistRecommendations/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputArtistRecommendation1Json));
    }

    @Test
    public void shouldUpdateArtistRecommendationById() throws Exception {
        inputArtistRecommendation1.setArtistRecommendationId(1);
        inputArtistRecommendation1.setLiked(false);

        String inputJson = mapper.writeValueAsString(inputArtistRecommendation1);

        mockMvc.perform(
                put("/artistRecommendations")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteArtistRecommendationById() throws Exception {
        mockMvc.perform(delete("/artistRecommendations/2"))
                .andExpect(status().isNoContent());

        verify(artistRecommendationRepository).deleteById(2);
    }
}