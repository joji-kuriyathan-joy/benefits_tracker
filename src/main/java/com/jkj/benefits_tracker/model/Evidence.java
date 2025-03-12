package com.jkj.benefits_tracker.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table (name = "evidences")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Evidence {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn (name = "participant_id", referencedColumnName = "id", nullable = false)
    private Participant participant;
    private String fieldName;
    private String oldValue;
    private String newValue;
    private LocalDateTime changeTime;

    public Evidence(Participant participant, String fieldName, String oldValue, String newValue) {
        this.participant = participant;
        this.fieldName = fieldName;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.changeTime = LocalDateTime.now();
    }

//    public UUID getId() {
//        return id;
//    }
//
//    public void setId(UUID id) {
//        this.id = id;
//    }
//
//    public Participant getParticipant() {
//        return participant;
//    }
//
//    public void setParticipant(Participant participant) {
//        this.participant = participant;
//    }
//
//    public String getFieldName() {
//        return fieldName;
//    }
//
//    public void setFieldName(String fieldName) {
//        this.fieldName = fieldName;
//    }
//
//    public String getOldValue() {
//        return oldValue;
//    }
//
//    public void setOldValue(String oldValue) {
//        this.oldValue = oldValue;
//    }
//
//    public String getNewValue() {
//        return newValue;
//    }
//
//    public void setNewValue(String newValue) {
//        this.newValue = newValue;
//    }
//
//    public LocalDateTime getChangeTime() {
//        return changeTime;
//    }
//
//    public void setChangeTime(LocalDateTime changeTime) {
//        this.changeTime = changeTime;
//    }
}
