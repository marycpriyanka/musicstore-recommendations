package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.LabelRecommendation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LabelRecommendationRepositoryTest {
    @Autowired
    private LabelRecommendationRepository labelRecommendationRepository;

    private LabelRecommendation labelRecommendation;

    @Before
    public void setUp() throws Exception {
        labelRecommendationRepository.deleteAll();

        labelRecommendation = new LabelRecommendation();
        labelRecommendation.setLabelId(1);
        labelRecommendation.setUserId(1);
        labelRecommendation.setLiked(true);
    }

    @Test
    public void shouldCreateReadDeleteLabelRecommendation() {
        labelRecommendation = labelRecommendationRepository.save(labelRecommendation);

        int id = labelRecommendation.getLabelRecommendationId();

        Optional<LabelRecommendation> labelRecommendation1 = labelRecommendationRepository.findById(id);
        assertEquals(labelRecommendation, labelRecommendation1.get());

        labelRecommendationRepository.deleteById(id);
        assertEquals(false, labelRecommendationRepository.existsById(id));
    }

    @Test
    public void shouldReadAllLabelRecommendations() {
        labelRecommendationRepository.save(labelRecommendation);

        labelRecommendation = new LabelRecommendation();
        labelRecommendation.setLabelId(2);
        labelRecommendation.setUserId(2);
        labelRecommendation.setLiked(true);
        labelRecommendationRepository.save(labelRecommendation);

        List<LabelRecommendation> labelRecommendationList = labelRecommendationRepository.findAll();
        assertEquals(2, labelRecommendationList.size());
    }

    @Test
    public void shouldUpdateLabelRecommendation() {
        labelRecommendation = labelRecommendationRepository.save(labelRecommendation);

        labelRecommendation.setLiked(false);
        labelRecommendation = labelRecommendationRepository.save(labelRecommendation);

        Optional<LabelRecommendation> labelRecommendation1 = labelRecommendationRepository.findById(labelRecommendation.getLabelRecommendationId());
        assertEquals(labelRecommendation, labelRecommendation1.get());
        assertEquals(false, labelRecommendation.isLiked());
    }
}