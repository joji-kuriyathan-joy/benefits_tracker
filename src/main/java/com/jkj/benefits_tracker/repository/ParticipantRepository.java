package com.jkj.benefits_tracker.repository;

import com.jkj.benefits_tracker.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

//Provides built-in CRUD operations for Participant.
public interface ParticipantRepository extends JpaRepository<Participant, UUID> {
}
