package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.LabelRecommendation;
import com.company.musicstorerecommendations.repository.LabelRecommendationRepository;
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
@WebMvcTest(LabelRecommendationController.class)
public class LabelRecommendationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LabelRecommendationRepository labelRecommendationRepository;

    private ObjectMapper mapper = new ObjectMapper();

    private LabelRecommendation inputLabelRecommendation1;
    private LabelRecommendation inputLabelRecommendation2;
    private String inputLabelRecommendation1Json;

    private LabelRecommendation outputLabelRecommendation1;
    private LabelRecommendation outputLabelRecommendation2;
    private String outputLabelRecommendation1Json;

    private List<LabelRecommendation> outputLabelRecommendationList = new ArrayList<>();
    private String outputLabelRecommendationListJson;

    @Before
    public void setUp() throws Exception {
        inputLabelRecommendation1 = new LabelRecommendation();
        inputLabelRecommendation1.setLabelId(1);
        inputLabelRecommendation1.setUserId(1);
        inputLabelRecommendation1.setLiked(true);

        inputLabelRecommendation2 = new LabelRecommendation();
        inputLabelRecommendation2.setLabelId(2);
        inputLabelRecommendation2.setUserId(2);
        inputLabelRecommendation2.setLiked(true);

        inputLabelRecommendation1Json = mapper.writeValueAsString(inputLabelRecommendation1);

        outputLabelRecommendation1 = new LabelRecommendation();
        outputLabelRecommendation1.setLabelRecommendationId(1);
        outputLabelRecommendation1.setLabelId(1);
        outputLabelRecommendation1.setUserId(1);
        outputLabelRecommendation1.setLiked(true);

        outputLabelRecommendation2 = new LabelRecommendation();
        outputLabelRecommendation2.setLabelRecommendationId(2);
        outputLabelRecommendation2.setLabelId(2);
        outputLabelRecommendation2.setUserId(2);
        outputLabelRecommendation2.setLiked(true);

        outputLabelRecommendation1Json = mapper.writeValueAsString(outputLabelRecommendation1);

        outputLabelRecommendationList.add(outputLabelRecommendation1);
        outputLabelRecommendationList.add(outputLabelRecommendation2);

        outputLabelRecommendationListJson = mapper.writeValueAsString(outputLabelRecommendationList);
    }

    @Test
    public void shouldCreateLabelRecommendation() throws Exception {
        doReturn(outputLabelRecommendation1).when(labelRecommendationRepository).save(inputLabelRecommendation1);

        mockMvc.perform(
                post("/labelRecommendations")
                        .content(inputLabelRecommendation1Json)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputLabelRecommendation1Json));
    }

    @Test
    public void shouldReturnAllLabelRecommendations() throws Exception {
        doReturn(outputLabelRecommendationList).when(labelRecommendationRepository).findAll();

        mockMvc.perform(get("/labelRecommendations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputLabelRecommendationListJson));
    }

    @Test
    public void shouldReturnLabelRecommendationById() throws Exception {
        doReturn(Optional.of(outputLabelRecommendation1)).when(labelRecommendationRepository).findById(1);

        mockMvc.perform(get("/labelRecommendations/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputLabelRecommendation1Json));
    }

    @Test
    public void shouldUpdateLabelRecommendaionById() throws Exception {
        inputLabelRecommendation1.setLabelRecommendationId(1);
        inputLabelRecommendation1.setLiked(false);

        String inputJson = mapper.writeValueAsString(inputLabelRecommendation1);

        mockMvc.perform(
                put("/labelRecommendations")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteLabelRecommendationById() throws Exception {
        mockMvc.perform(delete("/labelRecommendations/2"))
                .andExpect(status().isNoContent());

        verify(labelRecommendationRepository).deleteById(2);
    }
}