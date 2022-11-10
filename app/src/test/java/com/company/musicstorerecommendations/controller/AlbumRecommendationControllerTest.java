package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.AlbumRecommendation;
import com.company.musicstorerecommendations.repository.AlbumRecommendationRepository;
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
@WebMvcTest(AlbumRecommendationController.class)
public class AlbumRecommendationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlbumRecommendationRepository albumRecommendationRepository;

    private ObjectMapper mapper = new ObjectMapper();

    private AlbumRecommendation inputAlbumRecommendation1;
    private AlbumRecommendation inputAlbumRecommendation2;
    private String inputAlbumRecommendation1Json;

    private AlbumRecommendation outputAlbumRecommendation1;
    private AlbumRecommendation outputAlbumRecommendation2;
    private String outputAlbumRecommendation1Json;

    private List<AlbumRecommendation> outputAlbumRecommendationList = new ArrayList<>();
    private String outputAlbumRecommendationListJson;

    @Before
    public void setUp() throws Exception {
        inputAlbumRecommendation1 = new AlbumRecommendation();
        inputAlbumRecommendation1.setAlbumId(1);
        inputAlbumRecommendation1.setUserId(1);
        inputAlbumRecommendation1.setLiked(true);

        inputAlbumRecommendation2 = new AlbumRecommendation();
        inputAlbumRecommendation2.setAlbumId(2);
        inputAlbumRecommendation2.setUserId(2);
        inputAlbumRecommendation2.setLiked(true);

        inputAlbumRecommendation1Json = mapper.writeValueAsString(inputAlbumRecommendation1);

        outputAlbumRecommendation1 = new AlbumRecommendation();
        outputAlbumRecommendation1.setAlbumRecommendationId(1);
        outputAlbumRecommendation1.setAlbumId(1);
        outputAlbumRecommendation1.setUserId(1);
        outputAlbumRecommendation1.setLiked(true);

        outputAlbumRecommendation2 = new AlbumRecommendation();
        outputAlbumRecommendation2.setAlbumRecommendationId(2);
        outputAlbumRecommendation2.setAlbumId(2);
        outputAlbumRecommendation2.setUserId(2);
        outputAlbumRecommendation2.setLiked(true);

        outputAlbumRecommendation1Json = mapper.writeValueAsString(outputAlbumRecommendation1);

        outputAlbumRecommendationList.add(outputAlbumRecommendation1);
        outputAlbumRecommendationList.add(outputAlbumRecommendation2);

        outputAlbumRecommendationListJson = mapper.writeValueAsString(outputAlbumRecommendationList);
    }

    @Test
    public void shouldCreateAlbumRecommendation() throws Exception {
        doReturn(outputAlbumRecommendation1).when(albumRecommendationRepository).save(inputAlbumRecommendation1);

        mockMvc.perform(
                post("/albumRecommendations")
                        .content(inputAlbumRecommendation1Json)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputAlbumRecommendation1Json));
    }

    @Test
    public void shouldUpdateAlbumById() throws Exception {
        inputAlbumRecommendation1.setAlbumRecommendationId(1);
        inputAlbumRecommendation1.setLiked(false);

        String inputJson = mapper.writeValueAsString(inputAlbumRecommendation1);

        mockMvc.perform(
                put("/albumRecommendations")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnAllAlbumRecommendations() throws Exception {
        doReturn(outputAlbumRecommendationList).when(albumRecommendationRepository).findAll();

        mockMvc.perform(get("/albumRecommendations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputAlbumRecommendationListJson));
    }

    @Test
    public void shouldReturnAlbumRecommendationById() throws Exception {
        doReturn(Optional.of(outputAlbumRecommendation1)).when(albumRecommendationRepository).findById(1);

        mockMvc.perform(get("/albumRecommendations/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputAlbumRecommendation1Json));
    }

    @Test
    public void shouldDeleteAlbumRecommendationBYId() throws Exception {
        mockMvc.perform(delete("/albumRecommendations/2"))
                .andExpect(status().isNoContent());

        verify(albumRecommendationRepository).deleteById(2);
    }
}