package com.jkj.benefits_tracker.controller;

import com.jkj.benefits_tracker.model.Participant;
import com.jkj.benefits_tracker.service.ParticipantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/participant")
public class ParticipantController {
    @Autowired
    ParticipantService participantService;

    //[POST] register
    @PostMapping("/register")
    public ResponseEntity<Participant> register(@Valid @RequestBody Participant participant){
        return ResponseEntity.ok(participantService.registerParticipant(participant));
    }

    //[GET] getOneParticipant
    @GetMapping("/{id}")
    public ResponseEntity<Participant> getOneParticipant(@PathVariable UUID id){
        return ResponseEntity.ok(participantService.getParticipantById(id));
    }

    //[GET] getAlParticipant
    @GetMapping
    public ResponseEntity<List<Participant>> getAlParticipant(){
        return ResponseEntity.ok(participantService.getAllParticipants());
    }

    //[GET] getParticipantAge
    @GetMapping("/{id}/age")
    public ResponseEntity<Map> getParticipantAge(@PathVariable UUID id){
        return  ResponseEntity.ok(participantService.getParticipantAgeById(id));
    }

    //[GET] getParticipantActiveFNameAndSName
    @GetMapping("/{id}/name")
    public ResponseEntity<Map> getParticipantActiveFNameAndSName(@PathVariable UUID id){
        return  ResponseEntity.ok(participantService.getParticipantFirstAndSurNameById(id));
    }
    //[GET] getParticipantActiveAddress
    @GetMapping("/{id}/address")
    public ResponseEntity<Map> getParticipantActiveAddress(@PathVariable UUID id){
        return  ResponseEntity.ok(participantService.getParticipantPrimaryAddressById(id));
    }
    //[GET] getParticipantActiveBankAccount
    @GetMapping("/{id}/bank-account")
    public ResponseEntity<Map> getParticipantActiveBankAccount(@PathVariable UUID id){
        return  ResponseEntity.ok(participantService.getParticipantPrimaryBankAccountById(id));
    }

    //[PUT] updateParticipantData
    @PutMapping("/{id}/update")
    public ResponseEntity<Participant> updateParticipantDetails(@PathVariable UUID id,
                                                                @RequestBody Participant updatedParticipant){
        return ResponseEntity.ok(participantService.updateParticipant(id,updatedParticipant));
    }
}
