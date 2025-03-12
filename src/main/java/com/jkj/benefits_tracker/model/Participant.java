package com.jkj.benefits_tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "participants")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ReadOnlyProperty
    private LocalDate registrationDate;

    //Mandatory attr -> Validation
    @NotNull(message = "Date of birth is mandatory")

    private LocalDate dateOfBirth;
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Surname is mandatory")
    private String surName;
    @NotBlank(message = "Primary address is mandatory")
    private String primaryAddress;
    @NotBlank(message = "Primary bank account is mandatory")
    private String primaryBankAccount;

    private String nationalInsuranceNumber;
    private String primaryTelephone;
    private String primaryEmailAddress;
    private Boolean isProspect;
    private String temporaryReferenceNumber;

    @PrePersist
    protected void onCreate(){
        this.registrationDate = LocalDate.now();
        this.isProspect = false;
        if(this.nationalInsuranceNumber == null || this.nationalInsuranceNumber.isEmpty()){
            this.isProspect = true;
            this.temporaryReferenceNumber = "PROS_" + UUID.randomUUID().toString().substring(0, 6);
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public @NotNull(message = "Date of birth is mandatory") LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(@NotNull(message = "Date of birth is mandatory") LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public @NotBlank(message = "First name is mandatory") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank(message = "First name is mandatory") String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank(message = "Surname is mandatory") String getSurName() {
        return surName;
    }
//
//    public void setSurName(@NotBlank(message = "Surname is mandatory") String surName) {
//        this.surName = surName;
//    }
//
//    public @NotBlank(message = "Primary address is mandatory") String getPrimaryAddress() {
//        return primaryAddress;
//    }
//
//    public void setPrimaryAddress(@NotBlank(message = "Primary address is mandatory") String primaryAddress) {
//        this.primaryAddress = primaryAddress;
//    }
//
//    public @NotBlank(message = "Primary bank account is mandatory") String getPrimaryBankAccount() {
//        return primaryBankAccount;
//    }
//
//    public void setPrimaryBankAccount(@NotBlank(message = "Primary bank account is mandatory") String primaryBankAccount) {
//        this.primaryBankAccount = primaryBankAccount;
//    }
//
//    public String getNationalInsuranceNumber() {
//        return nationalInsuranceNumber;
//    }
//
//    public void setNationalInsuranceNumber(String nationalInsuranceNumber) {
//        this.nationalInsuranceNumber = nationalInsuranceNumber;
//    }
//
//    public String getPrimaryTelephone() {
//        return primaryTelephone;
//    }
//
//    public void setPrimaryTelephone(String primaryTelephone) {
//        this.primaryTelephone = primaryTelephone;
//    }
//
//    public String getPrimaryEmailAddress() {
//        return primaryEmailAddress;
//    }
//
//    public void setPrimaryEmailAddress(String primaryEmailAddress) {
//        this.primaryEmailAddress = primaryEmailAddress;
//    }
//
//    public Boolean getProspect() {
//        return isProspect;
//    }
//
//    public void setProspect(Boolean prospect) {
//        isProspect = prospect;
//    }
//
//    public String getTemporaryReferenceNumber() {
//        return temporaryReferenceNumber;
//    }
//
//    public void setTemporaryReferenceNumber(String temporaryReferenceNumber) {
//        this.temporaryReferenceNumber = temporaryReferenceNumber;
//    }
}
