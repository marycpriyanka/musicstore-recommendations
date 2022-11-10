package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.TrackRecommendation;
import com.company.musicstorerecommendations.repository.TrackRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trackRecommendations")
public class TrackRecommendationController {
    @Autowired
    private TrackRecommendationRepository trackRecommendationRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrackRecommendation addTrackRecommendation(@RequestBody @Valid TrackRecommendation trackRecommendation) {
        return trackRecommendationRepository.save(trackRecommendation);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TrackRecommendation> findAllTrackRecommendations() {
        return trackRecommendationRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TrackRecommendation findTrackRecommendationById(@PathVariable int id) {
        Optional<TrackRecommendation> trackRecommendation = trackRecommendationRepository.findById(id);

        if (trackRecommendation.isPresent()) {
            return trackRecommendation.get();
        } else {
            return null;
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrackRecommendation(@RequestBody @Valid TrackRecommendation trackRecommendation) {
        trackRecommendationRepository.save(trackRecommendation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrackRecommendation(@PathVariable int id) {
        trackRecommendationRepository.deleteById(id);
    }
}
