package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.ArtistRecommendation;
import com.company.musicstorerecommendations.repository.ArtistRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artistRecommendations")
public class ArtistRecommendationController {
    @Autowired
    private ArtistRecommendationRepository artistRecommendationRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistRecommendation addArstistRecommendation(@RequestBody @Valid ArtistRecommendation artistRecommendation) {
        return artistRecommendationRepository.save(artistRecommendation);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistRecommendation> findAllArtistRecommendations() {
        return artistRecommendationRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArtistRecommendation findArtistRecommendationById(@PathVariable int id) {
        Optional<ArtistRecommendation> artistRecommendation = artistRecommendationRepository.findById(id);

        if (artistRecommendation.isPresent()) {
            return artistRecommendation.get();
        } else {
            return null;
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtistRecommendation(@RequestBody @Valid ArtistRecommendation artistRecommendation) {
        artistRecommendationRepository.save(artistRecommendation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtistRecommendation(@PathVariable int id) {
        artistRecommendationRepository.deleteById(id);
    }
}
