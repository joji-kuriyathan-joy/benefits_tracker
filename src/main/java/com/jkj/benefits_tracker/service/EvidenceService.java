package com.jkj.benefits_tracker.service;

import com.jkj.benefits_tracker.model.Evidence;
import com.jkj.benefits_tracker.model.Participant;
import com.jkj.benefits_tracker.repository.EvidenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EvidenceService {
    @Autowired
    EvidenceRepository evidenceRepository;

    //Get the evidence history of a participant using the ID
    public List<Evidence> getHistory(UUID id){
        return  evidenceRepository.findByParticipantId(id);
    }

    //Track the change & update the evidence table by comparing
    //participant old data with new data
    public void trackChanges(Participant participant, String fieldName, String oldVal, String newVal){
        if(oldVal != null && newVal != null && !oldVal.trim().equals(newVal.trim())){
            evidenceRepository.save(new Evidence(participant,fieldName,oldVal,newVal));
        }
    }
}
