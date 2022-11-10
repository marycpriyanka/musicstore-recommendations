package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.AlbumRecommendation;
import com.company.musicstorerecommendations.repository.AlbumRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/albumRecommendations")
public class AlbumRecommendationController {
    @Autowired
    private AlbumRecommendationRepository albumRecommendationRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumRecommendation addAlbumRecommendation(@RequestBody @Valid AlbumRecommendation albumRecommendation) {
        return albumRecommendationRepository.save(albumRecommendation);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumRecommendation> findAllAlbumRecommendations() {
        return albumRecommendationRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AlbumRecommendation findAlbumRecommendationById(@PathVariable int id) {
        Optional<AlbumRecommendation> albumRecommendation = albumRecommendationRepository.findById(id);

        if (albumRecommendation.isPresent()) {
            return albumRecommendation.get();
        }  else {
            return null;
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbumRecommendation(@RequestBody @Valid AlbumRecommendation albumRecommendation) {
        albumRecommendationRepository.save(albumRecommendation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbumRecommendation(@PathVariable int id) {
        albumRecommendationRepository.deleteById(id);
    }
}
