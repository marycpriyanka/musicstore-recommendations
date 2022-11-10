package com.company.musicstorerecommendations.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "track_recommendation")
public class TrackRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_recommendation_id")
    private Integer trackRecommendationId;

    @NotNull
    @Column(name = "track_id")
    private int trackId;

    @NotNull
    @Column(name = "user_id")
    private int userId;

    @NotNull
    private boolean liked;

    public TrackRecommendation() {}

    public TrackRecommendation(int trackId, int userId, boolean liked) {
        this.trackId = trackId;
        this.userId = userId;
        this.liked = liked;
    }

    public TrackRecommendation(Integer trackRecommendationId, int trackId, int userId, boolean liked) {
        this.trackRecommendationId = trackRecommendationId;
        this.trackId = trackId;
        this.userId = userId;
        this.liked = liked;
    }

    public Integer getTrackRecommendationId() {
        return trackRecommendationId;
    }

    public void setTrackRecommendationId(Integer trackRecommendationId) {
        this.trackRecommendationId = trackRecommendationId;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackRecommendation that = (TrackRecommendation) o;
        return trackId == that.trackId && userId == that.userId && liked == that.liked && Objects.equals(trackRecommendationId, that.trackRecommendationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackRecommendationId, trackId, userId, liked);
    }

    @Override
    public String toString() {
        return "TrackRecommendation{" +
                "trackRecommendationId=" + trackRecommendationId +
                ", trackId=" + trackId +
                ", userId=" + userId +
                ", liked=" + liked +
                '}';
    }
}
