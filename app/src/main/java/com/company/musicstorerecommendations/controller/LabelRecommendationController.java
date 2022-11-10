package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.LabelRecommendation;
import com.company.musicstorerecommendations.repository.LabelRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/labelRecommendations")
public class LabelRecommendationController {
    @Autowired
    private LabelRecommendationRepository labelRecommendationRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LabelRecommendation addLabelRecommendation(@RequestBody @Valid LabelRecommendation labelRecommendation) {
        return labelRecommendationRepository.save(labelRecommendation);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LabelRecommendation> findAllLabelRecommendations() {
        return labelRecommendationRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelRecommendation findLabelRecommendationById(@PathVariable int id) {
        Optional<LabelRecommendation> labelRecommendation = labelRecommendationRepository.findById(id);

        if (labelRecommendation.isPresent()) {
            return labelRecommendation.get();
        } else {
            return null;
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabelRecommendation(@RequestBody @Valid LabelRecommendation labelRecommendation) {
        labelRecommendationRepository.save(labelRecommendation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabelRecommendation(@PathVariable int id) {
        labelRecommendationRepository.deleteById(id);
    }
}
