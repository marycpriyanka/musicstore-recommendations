package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.AlbumRecommendation;
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
public class AlbumRecommendationRepositoryTest {
    @Autowired
    private AlbumRecommendationRepository albumRecommendationRepository;

    private AlbumRecommendation albumRecommendation;

    @Before
    public void setUp() throws Exception {
        albumRecommendationRepository.deleteAll();

        albumRecommendation = new AlbumRecommendation();
        albumRecommendation.setAlbumId(1);
        albumRecommendation.setUserId(1);
        albumRecommendation.setLiked(true);
    }

    @Test
    public void shouldCreateReadDeleteAlbumRecommendation() {
        albumRecommendationRepository.save(albumRecommendation);

        int id = albumRecommendation.getAlbumRecommendationId();

        Optional<AlbumRecommendation> albumRecommendation1 = albumRecommendationRepository.findById(id);
        assertEquals(albumRecommendation, albumRecommendation1.get());

        albumRecommendationRepository.deleteById(id);
        assertEquals(false, albumRecommendationRepository.existsById(id));
    }

    @Test
    public void shouldReadAllAlbumRecommendations() {
          albumRecommendationRepository.save(albumRecommendation);

          albumRecommendation = new AlbumRecommendation();
          albumRecommendation.setAlbumId(2);
          albumRecommendation.setUserId(2);
          albumRecommendation.setLiked(true);

          albumRecommendationRepository.save(albumRecommendation);

        List<AlbumRecommendation> albumRecommendationList = albumRecommendationRepository.findAll();
        assertEquals(2, albumRecommendationList.size());
    }

    @Test
    public void shouldUpdateAlbumRecommendation() {
        albumRecommendationRepository.save(albumRecommendation);

        albumRecommendation.setLiked(false);
        albumRecommendationRepository.save(albumRecommendation);

        Optional<AlbumRecommendation> albumRecommendation1 = albumRecommendationRepository.findById(albumRecommendation.getAlbumRecommendationId());
        assertEquals(albumRecommendation, albumRecommendation1.get());
        assertEquals(false, albumRecommendation.isLiked());
    }
}