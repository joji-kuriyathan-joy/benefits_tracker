package com.jkj.benefits_tracker.repository;

import com.jkj.benefits_tracker.model.Evidence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EvidenceRepository extends JpaRepository<Evidence, UUID> {

    //Retrieves historical changes based on participantId.
    List<Evidence> findByParticipantId(UUID participantId);
}
