package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.TrackRecommendation;
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
public class TrackRecommendationRepositoryTest {
    @Autowired
    private TrackRecommendationRepository trackRecommendationRepository;

    private TrackRecommendation trackRecommendation;

    @Before
    public void setUp() {
        trackRecommendationRepository.deleteAll();

        trackRecommendation = new TrackRecommendation();
        trackRecommendation.setTrackId(1);
        trackRecommendation.setUserId(1);
        trackRecommendation.setLiked(true);
    }

    @Test
    public void shouldCreateReadDeleteTrackRecommendation() {
        trackRecommendation = trackRecommendationRepository.save(trackRecommendation);

        int id = trackRecommendation.getTrackRecommendationId();

        Optional<TrackRecommendation> trackRecommendation1 = trackRecommendationRepository.findById(id);
        assertEquals(trackRecommendation, trackRecommendation1.get());

        trackRecommendationRepository.deleteById(id);
        assertEquals(false, trackRecommendationRepository.existsById(id));
    }

    @Test
    public void shouldReadAllTrackRecommendations() {
        trackRecommendationRepository.save(trackRecommendation);

        trackRecommendation = new TrackRecommendation();
        trackRecommendation.setTrackId(2);
        trackRecommendation.setUserId(2);
        trackRecommendation.setLiked(true);
        trackRecommendationRepository.save(trackRecommendation);

        List<TrackRecommendation> trackRecommendationList = trackRecommendationRepository.findAll();
        assertEquals(2, trackRecommendationList.size());
    }

    @Test
    public void shouldUpdateTrackRecommendation() {
        trackRecommendation = trackRecommendationRepository.save(trackRecommendation);

        trackRecommendation.setLiked(false);
        trackRecommendation = trackRecommendationRepository.save(trackRecommendation);

        Optional<TrackRecommendation> trackRecommendation1 = trackRecommendationRepository.findById(trackRecommendation.getTrackRecommendationId());
        assertEquals(trackRecommendation, trackRecommendation1.get());
        assertEquals(false, trackRecommendation.isLiked());
    }
}