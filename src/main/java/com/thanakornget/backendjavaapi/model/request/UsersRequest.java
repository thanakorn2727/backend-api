package com.thanakornget.backendjavaapi.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class UsersRequest {
    @Id
    private Long id;
    @NotNull
    private String idCard;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String birthDate;
    @NotBlank
    private String address;
    private Long subDistrictId;
    private Long districtId;
    private Long provinceId;
    private Long postalCode;
    @Email
    private String email;
    @NotNull
    private String phoneNumber;
}
