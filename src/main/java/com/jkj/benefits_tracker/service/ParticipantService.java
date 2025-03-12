package com.jkj.benefits_tracker.service;

import com.jkj.benefits_tracker.exception.*;
import com.jkj.benefits_tracker.model.Participant;
import com.jkj.benefits_tracker.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ParticipantService {
    //     This Service class contains the Business logic, Validation and Transformation
    @Autowired
    ParticipantRepository participantRepository;
    @Autowired
    private EvidenceService evidenceService;

    //+++++++++++++++++++++ Business Logic +++++++++++++++++++++++
    //Register a participant to the system using participant input data
    public Participant registerParticipant(Participant participant)  {

        //validate NINO
        if(!isValidNino(participant.getNationalInsuranceNumber())){
            throw new InvalidNINOException("Invalid National Insurance Number format.");
        }

        if(!isValidPostCode(participant.getPrimaryAddress())){
            throw new InvalidPostcodeException("Invalid Postcode format. Please enter a valid UK Postcode");
        }

        if(!isValidEmail(participant.getPrimaryEmailAddress())){
            throw new InvalidEmailException("Invalid email address, Please enter a valid email address");
        }

        if(!isValidPhoneNumber(participant.getPrimaryTelephone())){
            throw new InvalidTelephoneNumberException("Invalid phone number, Please enter a valid phone number");
        }
        return participantRepository.save(participant);
    }

    //Update a particular participant details by Id and updated data
    public Participant updateParticipant(UUID id, Participant updatedParticipant){
        Participant existingParticipant = getParticipantById(id);
        //TODO: update the evidence table fields (firstName,surName,primaryAddress,primaryBankAccount)
//        evidenceService.trackChanges(existingParticipant, "firstName", existingParticipant.getFirstName(),
//                updatedParticipant.getFirstName());
//        evidenceService.trackChanges(existingParticipant,"surName",existingParticipant.getSurName(),
//                updatedParticipant.getSurName());
//        evidenceService.trackChanges(existingParticipant, "primaryAddress",
//                existingParticipant.getPrimaryAddress(), updatedParticipant.getPrimaryAddress() );
//        evidenceService.trackChanges(existingParticipant,"primaryBankAccount",
//                existingParticipant.getPrimaryBankAccount(), updatedParticipant.getPrimaryBankAccount());
//
//        //update all the fields
//        existingParticipant.setDateOfBirth(updatedParticipant.getDateOfBirth());
//        existingParticipant.setFirstName(updatedParticipant.getFirstName());
//        existingParticipant.setSurName(updatedParticipant.getSurName());
//        existingParticipant.setPrimaryAddress(updatedParticipant.getPrimaryAddress());
//        existingParticipant.setPrimaryTelephone(updatedParticipant.getPrimaryTelephone());
//        existingParticipant.setPrimaryBankAccount(updatedParticipant.getPrimaryBankAccount());
//        existingParticipant.setPrimaryEmailAddress(updatedParticipant.getPrimaryEmailAddress());

        // First Name
        if (updatedParticipant.getFirstName() != null &&
                !updatedParticipant.getFirstName().equals(existingParticipant.getFirstName())) {
            evidenceService.trackChanges(existingParticipant, "firstName",
                    existingParticipant.getFirstName(), updatedParticipant.getFirstName());
            existingParticipant.setFirstName(updatedParticipant.getFirstName());
        }

        // Surname
        if (updatedParticipant.getSurName() != null &&
                !updatedParticipant.getSurName().equals(existingParticipant.getSurName())) {
            evidenceService.trackChanges(existingParticipant, "surName",
                    existingParticipant.getSurName(), updatedParticipant.getSurName());
            existingParticipant.setSurName(updatedParticipant.getSurName());
        }

        // Primary Address
        if (updatedParticipant.getPrimaryAddress() != null &&
                !updatedParticipant.getPrimaryAddress().equals(existingParticipant.getPrimaryAddress())) {
            evidenceService.trackChanges(existingParticipant, "primaryAddress",
                    existingParticipant.getPrimaryAddress(), updatedParticipant.getPrimaryAddress());
            existingParticipant.setPrimaryAddress(updatedParticipant.getPrimaryAddress());
        }

        // Primary Bank Account
        if (updatedParticipant.getPrimaryBankAccount() != null &&
                !updatedParticipant.getPrimaryBankAccount().equals(existingParticipant.getPrimaryBankAccount())) {
            evidenceService.trackChanges(existingParticipant, "primaryBankAccount",
                    existingParticipant.getPrimaryBankAccount(), updatedParticipant.getPrimaryBankAccount());
            existingParticipant.setPrimaryBankAccount(updatedParticipant.getPrimaryBankAccount());
        }

        // Date of Birth
        if (updatedParticipant.getDateOfBirth() != null) {
            existingParticipant.setDateOfBirth(updatedParticipant.getDateOfBirth());
        }

        // Telephone (optional)
        if (updatedParticipant.getPrimaryTelephone() != null &&
                !updatedParticipant.getPrimaryTelephone().equals(existingParticipant.getPrimaryTelephone())) {
            existingParticipant.setPrimaryTelephone(updatedParticipant.getPrimaryTelephone());
        }

        // Email Address
        if (updatedParticipant.getPrimaryEmailAddress() != null &&
                !updatedParticipant.getPrimaryEmailAddress().equals(existingParticipant.getPrimaryEmailAddress())) {
            existingParticipant.setPrimaryEmailAddress(updatedParticipant.getPrimaryEmailAddress());
        }
        return participantRepository.save(existingParticipant);

    }

    //Get a specific participant data using Id
    public Participant getParticipantById(UUID id){
        return participantRepository.findById(id)
                .orElseThrow(() -> new ParticipantNotFoundException("Participant with ID " + id + " not found."));

    }


    //Get all participant registered to the system (Admin)
    public List<Participant> getAllParticipants(){
        return participantRepository.findAll();
    }

    //Get participant Age
    public Map getParticipantAgeById(UUID id){
        Participant existingParticipant = getParticipantById(id);
        Map participantAgeMap = new HashMap();
        participantAgeMap.put("age",LocalDate.now().getYear() - existingParticipant.getDateOfBirth().getYear());
        participantAgeMap.put("dob",existingParticipant.getDateOfBirth());
        return participantAgeMap;
    }

    //Get participant FirstName & SurName
    public Map getParticipantFirstAndSurNameById(UUID id){
        Participant existingParticipant = getParticipantById(id);
        Map participantNameMap = new HashMap();
        participantNameMap.put("firstName",existingParticipant.getFirstName());
        participantNameMap.put("surName",existingParticipant.getSurName());
        participantNameMap.put("fullName",existingParticipant.getFirstName() + " " +existingParticipant.getSurName());
        return participantNameMap;
    }

    //Get participant Primary Address
    public Map getParticipantPrimaryAddressById(UUID id){
        Participant existingParticipant = getParticipantById(id);
        Map participantPrimaryAddressMap = new HashMap();
        participantPrimaryAddressMap.put("primaryAddress",existingParticipant.getPrimaryAddress());
        return participantPrimaryAddressMap;
    }

    //Get participant Primary Bank Account
    public Map getParticipantPrimaryBankAccountById(UUID id){
        Participant existingParticipant = getParticipantById(id);
        Map participantPrimaryBankAccountMap = new HashMap();
        participantPrimaryBankAccountMap.put("primaryBankAccount",existingParticipant.getPrimaryBankAccount());
        return participantPrimaryBankAccountMap;
    }
    //++++++++++++++ Validation methods ++++++++++++

    //validate NINO
    private Boolean isValidNino(String nino){
        return nino == null || nino.isEmpty() || nino.matches("^[A-CEGHJ-PR-TW-Z]{2}\\d{6}[A-D]?$");
    }
    // validate postcode
    private Boolean isValidPostCode(String postcode){
        return postcode == null || postcode.isEmpty() ||
                postcode.matches("^([A-Z]{1,2}\\d[A-Z\\d]? ?\\d[A-Z]{2}|GIR ?0A{2})$") ;
    }
    //validate email
    private Boolean isValidEmail(String email){
        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email==null || email.isEmpty() || email.matches(EMAIL_REGEX);
    }

    //validate UK phone number
    private Boolean isValidPhoneNumber(String phoneNumber){

        String PHONE_REGEX = "^((\\+44)|(0)) ?\\d{4} ?\\d{6}$";
        return phoneNumber==null || phoneNumber.isEmpty() || phoneNumber.matches(PHONE_REGEX);
    }
}
