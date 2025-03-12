package com.jkj.benefits_tracker.service;

import com.jkj.benefits_tracker.model.Evidence;
import com.jkj.benefits_tracker.model.Participant;
import com.jkj.benefits_tracker.repository.EvidenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EvidenceServiceTest {

    @Mock
    private EvidenceRepository evidenceRepository;

    @InjectMocks
    private EvidenceService evidenceService;

    private Participant testParticipant;
    private UUID testParticipantId;

    @BeforeEach
    void setUp() {
        testParticipantId = UUID.randomUUID();
        testParticipant = new Participant();
        testParticipant.setId(testParticipantId);
        testParticipant.setFirstName("Joji");
        testParticipant.setSurName("Kuriyathan Joy");
    }

    // Test creating evidence when values change
    @Test
    void trackChanges_ShouldSaveEvidenceWhenValuesDiffer() {
        // Act
        evidenceService.trackChanges(testParticipant, "firstName", "OldName", "NewName");

        // Assert
        verify(evidenceRepository, times(1)).save(any(Evidence.class));
    }

    //  Test when no change occurs (should not save evidence)
    @Test
    void trackChanges_ShouldNotSaveEvidenceWhenValuesAreSame() {
        // Act
        evidenceService.trackChanges(testParticipant, "firstName", "SameName", "SameName");

        // Assert
        verify(evidenceRepository, never()).save(any(Evidence.class));
    }

    //  Test retrieving evidence history for a participant
    @Test
    void getEvidenceByParticipantId_ShouldReturnEvidenceList() {
        // Arrange
        Evidence evidence1 = new Evidence(testParticipant, "firstName", "OldName", "NewName");
        Evidence evidence2 = new Evidence(testParticipant, "primaryAddress", "OldAddress", "NewAddress");

        when(evidenceRepository.findByParticipantId(testParticipantId)).thenReturn(Arrays.asList(evidence1, evidence2));

        // Act
        List<Evidence> evidenceList = evidenceService.getHistory(testParticipantId);

        // Assert
        assertNotNull(evidenceList);
        assertEquals(2, evidenceList.size());
        verify(evidenceRepository, times(1)).findByParticipantId(testParticipantId);
    }

    //  Test when no evidence exists for a participant
    @Test
    void getEvidenceByParticipantId_ShouldReturnEmptyListIfNoEvidence() {
        // Arrange
        when(evidenceRepository.findByParticipantId(testParticipantId)).thenReturn(Arrays.asList());

        // Act
        List<Evidence> evidenceList = evidenceService.getHistory(testParticipantId);

        // Assert
        assertNotNull(evidenceList);
        assertEquals(0, evidenceList.size());
        verify(evidenceRepository, times(1)).findByParticipantId(testParticipantId);
    }
}