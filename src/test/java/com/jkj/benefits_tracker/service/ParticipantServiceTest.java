package com.jkj.benefits_tracker.service;

import com.jkj.benefits_tracker.exception.ParticipantNotFoundException;
import com.jkj.benefits_tracker.model.Participant;
import com.jkj.benefits_tracker.repository.EvidenceRepository;
import com.jkj.benefits_tracker.repository.ParticipantRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceTest {
    @Mock
    private ParticipantRepository participantRepository;
    @Mock
    private EvidenceService evidenceService;

    @InjectMocks
    private ParticipantService participantService;




    private Participant testParticipant;
    private UUID testParticipantId;

    @BeforeEach
    void init(){
        testParticipantId = UUID.randomUUID();
        testParticipant = new Participant();
        testParticipant.setId(testParticipantId);
        testParticipant.setDateOfBirth(LocalDate.of(1996,1,28));
        testParticipant.setFirstName("Joji");
        testParticipant.setSurName("Kuriyathan Joy");
        testParticipant.setPrimaryAddress("NE49UU");
        testParticipant.setPrimaryBankAccount("87346514");
        testParticipant.setPrimaryTelephone("07407841026");
        testParticipant.setNationalInsuranceNumber("NJ538490C"); //QQ123456A
        testParticipant.setPrimaryEmailAddress("joji@gmail.com");

    }

    @Test
    void registerParticipant_shouldSaveParticipant() {
        System.out.println("---- Testing registerParticipant_shouldSaveParticipant (Start)-----");
        when(participantRepository.save(any(Participant.class))).thenReturn(testParticipant);

        Participant savedParticipant = participantService.registerParticipant(testParticipant);

        assertNotNull(savedParticipant);
        assertEquals("Joji", savedParticipant.getFirstName());
        verify(participantRepository, times(1)).save(any(Participant.class));
        System.out.println("registerParticipant_shouldSaveParticipant : PASSED");
    }

    @Test
    void updateParticipant_ShouldUpdateOnlyProvidedFields() {
        System.out.println("---- Testing updateParticipant_ShouldUpdateOnlyProvidedFields (Start)-----");
        when(participantRepository.findById(testParticipant.getId())).thenReturn(Optional.of(testParticipant));
        when(participantRepository.save(any(Participant.class))).thenReturn(testParticipant);

        // Mocking EvidenceService behavior explicitly
        doNothing().when(evidenceService).trackChanges(any(Participant.class), anyString(), anyString(), anyString());

        Participant updated = new Participant();
        updated.setFirstName("UpdatedName");

        Participant result = participantService.updateParticipant(testParticipant.getId(), updated);

        assertEquals("UpdatedName", result.getFirstName());
        assertEquals("Kuriyathan Joy", result.getSurName()); // Should not change
        verify(participantRepository, times(1)).save(any(Participant.class));
        verify(evidenceService, times(1)).trackChanges(any(Participant.class),
                eq("firstName"), eq("Joji"), eq("UpdatedName"));

        System.out.println("updateParticipant_ShouldUpdateOnlyProvidedFields : PASSED");
    }

    @Test
    void getParticipantById_ShouldReturnParticipant() {
        System.out.println("---- Testing getParticipantById_ShouldReturnParticipant (Start)-----");
        when(participantRepository.findById(testParticipant.getId())).thenReturn(Optional.of(testParticipant));

        Participant retrieved = participantService.getParticipantById(testParticipant.getId());

        assertNotNull(retrieved);
        assertEquals("Joji", retrieved.getFirstName());
        verify(participantRepository, times(1)).findById(testParticipant.getId());
        System.out.println("getParticipantById_ShouldReturnParticipant : PASSED");

    }

    @Test
    void getParticipantById_ShouldThrowExceptionIfNotFound() {
        System.out.println("---- Testing getParticipantById_ShouldThrowExceptionIfNotFound (Given Wrong ID - " +
                "Should throw Not found exception)" +
                " (Start)-----");
        UUID randomId = UUID.randomUUID();
        when(participantRepository.findById(randomId)).thenReturn(Optional.empty());

        assertThrows(ParticipantNotFoundException.class, () -> participantService.getParticipantById(randomId));
        System.out.println("getParticipantById_ShouldThrowExceptionIfNotFound : PASSED");

    }
    // Test fetching all participants
    @Test
    void getAllParticipants_shouldGetAllRegisteredParticipants() {
        System.out.println("---- Testing getAllParticipants_shouldGetAllRegisteredParticipants (Start)-----");

        List<Participant> participants = Arrays.asList(testParticipant);

        when(participantRepository.findAll()).thenReturn(participants);

        List<Participant> result = participantService.getAllParticipants();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Joji", result.get(0).getFirstName());
        verify(participantRepository, times(1)).findAll();
        System.out.println("getAllParticipants_shouldGetAllRegisteredParticipants : PASSED");

    }

    // Test fetching participant age by ID
    @Test
    void getParticipantAgeById_shouldReturnParticipantAge() {
        System.out.println("---- Testing getParticipantAgeById_shouldReturnParticipantAge (Start)-----");

        when(participantRepository.findById(testParticipantId)).thenReturn(Optional.of(testParticipant));

        int expectedAge = Period.between(testParticipant.getDateOfBirth(), LocalDate.now()).getYears();
        int actualAge = (int)participantService.getParticipantAgeById(testParticipantId).get("age");

        assertEquals(expectedAge, actualAge);
        verify(participantRepository, times(1)).findById(testParticipantId);

        System.out.println("getParticipantAgeById_shouldReturnParticipantAge : PASSED");

    }

    //  Test when participant does not exist for age retrieval
    @Test
    void getParticipantAgeById_ShouldThrowExceptionIfNotFound() {
        System.out.println("---- Testing getParticipantAgeById_ShouldThrowExceptionIfNotFound (Start)-----");

        when(participantRepository.findById(testParticipantId)).thenReturn(Optional.empty());

        assertThrows(ParticipantNotFoundException.class, () -> participantService.getParticipantAgeById(testParticipantId));
        System.out.println("getParticipantAgeById_ShouldThrowExceptionIfNotFound : PASSED");

    }

    //  Test fetching participant's first and surname by ID
    @Test
    void getParticipantFirstAndSurNameById() {
        System.out.println("---- Testing getParticipantFirstAndSurNameById (Start)-----");

        when(participantRepository.findById(testParticipantId)).thenReturn(Optional.of(testParticipant));

        String expectedName = testParticipant.getFirstName() + " " + testParticipant.getSurName();
        String actualName = (String) participantService.getParticipantFirstAndSurNameById(testParticipantId).get("fullName");

        assertEquals(expectedName, actualName);
        verify(participantRepository, times(1)).findById(testParticipantId);
        System.out.println("getParticipantFirstAndSurNameById : PASSED");

    }

    //  Test when participant does not exist for name retrieval
    @Test
    void getParticipantFirstAndSurNameById_ShouldThrowExceptionIfNotFound() {
        System.out.println("---- Testing getParticipantFirstAndSurNameById_ShouldThrowExceptionIfNotFound (Start)-----");

        when(participantRepository.findById(testParticipantId)).thenReturn(Optional.empty());

        assertThrows(ParticipantNotFoundException.class, () -> participantService.getParticipantFirstAndSurNameById(testParticipantId));
        System.out.println("getParticipantFirstAndSurNameById_ShouldThrowExceptionIfNotFound : PASSED");

    }

    //  Test fetching participant's primary address by ID
    @Test
    void getParticipantPrimaryAddressById() {
        System.out.println("---- Testing getParticipantPrimaryAddressById (Start)-----");

        when(participantRepository.findById(testParticipantId)).thenReturn(Optional.of(testParticipant));

        String actualAddress = (String)participantService.getParticipantPrimaryAddressById(testParticipantId).get("primaryAddress");

        assertEquals("NE49UU", actualAddress);
        verify(participantRepository, times(1)).findById(testParticipantId);
        System.out.println("getParticipantPrimaryAddressById : PASSED");

    }



    //  Test when participant does not exist for address retrieval
    @Test
    void getParticipantPrimaryAddressById_ShouldThrowExceptionIfNotFound() {
        System.out.println("---- Testing getParticipantPrimaryAddressById_ShouldThrowExceptionIfNotFound (Start)-----");

        when(participantRepository.findById(testParticipantId)).thenReturn(Optional.empty());

        assertThrows(ParticipantNotFoundException.class, () -> participantService.getParticipantPrimaryAddressById(testParticipantId));
        System.out.println("getParticipantPrimaryAddressById_ShouldThrowExceptionIfNotFound : PASSED");

    }

    //  Test fetching participant's primary bank account by ID
    @Test
    void getParticipantPrimaryBankAccountById() {
        System.out.println("---- Testing getParticipantPrimaryBankAccountById (Start)-----");

        when(participantRepository.findById(testParticipantId)).thenReturn(Optional.of(testParticipant));

        String actualBankAccount =(String) participantService.getParticipantPrimaryBankAccountById(testParticipantId).get("primaryBankAccount");

        assertEquals(testParticipant.getPrimaryBankAccount(), actualBankAccount);
        verify(participantRepository, times(1)).findById(testParticipantId);
        System.out.println("getParticipantPrimaryBankAccountById : PASSED");

    }

    //  Test when participant does not exist for bank account retrieval
    @Test
    void getParticipantPrimaryBankAccountById_ShouldThrowExceptionIfNotFound() {
        System.out.println("---- Testing getParticipantPrimaryBankAccountById_ShouldThrowExceptionIfNotFound (Start)-----");

        when(participantRepository.findById(testParticipantId)).thenReturn(Optional.empty());

        assertThrows(ParticipantNotFoundException.class, () -> participantService.getParticipantPrimaryBankAccountById(testParticipantId));
        System.out.println("getParticipantPrimaryBankAccountById_ShouldThrowExceptionIfNotFound : PASSED");

    }
}