package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.ArtistRecommendation;
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
public class ArtistRecommendationRepositoryTest {
    @Autowired
    private ArtistRecommendationRepository artistRecommendationRepository;

    private ArtistRecommendation artistRecommendation;

    @Before
    public void setUp() throws Exception {
        artistRecommendationRepository.deleteAll();

        artistRecommendation = new ArtistRecommendation();
        artistRecommendation.setArtistId(1);
        artistRecommendation.setUserId(1);
        artistRecommendation.setLiked(true);
    }

    @Test
    public void shouldCreateReadDeleteArtistRecommendation() {
        artistRecommendation = artistRecommendationRepository.save(artistRecommendation);

        int id = artistRecommendation.getArtistRecommendationId();

        Optional<ArtistRecommendation> artistRecommendation1 = artistRecommendationRepository.findById(id);
        assertEquals(artistRecommendation, artistRecommendation1.get());

        artistRecommendationRepository.deleteById(id);
        assertEquals(false, artistRecommendationRepository.existsById(id));
    }


    @Test
    public void shouldReadAllArtistRecommendations() {
        artistRecommendation = artistRecommendationRepository.save(artistRecommendation);

        artistRecommendation = new ArtistRecommendation();
        artistRecommendation.setArtistId(2);
        artistRecommendation.setUserId(2);
        artistRecommendation.setLiked(true);
        artistRecommendation = artistRecommendationRepository.save(artistRecommendation);

        List<ArtistRecommendation> artistRecommendationList = artistRecommendationRepository.findAll();
        assertEquals(2, artistRecommendationList.size());
    }

    @Test
    public void shouldUpdateArtistRecommendation() {
        artistRecommendation = artistRecommendationRepository.save(artistRecommendation);

        artistRecommendation.setLiked(false);
        artistRecommendation = artistRecommendationRepository.save(artistRecommendation);

        Optional<ArtistRecommendation> artistRecommendation1 = artistRecommendationRepository.findById(artistRecommendation.getArtistRecommendationId());
        assertEquals(artistRecommendation, artistRecommendation1.get());
        assertEquals(false, artistRecommendation.isLiked());
    }
}