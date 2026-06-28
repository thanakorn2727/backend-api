package com.thanakornget.backendjavaapi.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
@Getter
@Setter
public class UsersResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String idCard;
    private String firstName;
    private String lastName;
    private LocalDateTime birthDate;
    private String address;
    private Long subDistrictId;
    private Long districtId;
    private Long provinceId;
    private Long postalCode;
    private String email;
    private String phoneNumber;
    private LocalDateTime createDate;
    private String createBy;
    private LocalDateTime updateDate;
    private String updateBy;
    private String active;

    public UsersResponse(
            Long id,
            String idCard,
            String firstName,
            String lastName,
            LocalDateTime birthDate,
            String address,
            Long subDistrictId,
            Long districtId,
            Long provinceId,
            Long postalCode,
            String email,
            String phoneNumber,
            LocalDateTime createDate,
            String createBy,
            LocalDateTime updateDate,
            String updateBy,
            String active) {
        this.id = id;
        this.idCard = idCard;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.subDistrictId = subDistrictId;
        this.districtId = districtId;
        this.provinceId = provinceId;
        this.postalCode = postalCode;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.createBy = createBy;
        this.updateDate = updateDate;
        this.updateBy = updateBy;
        this.active = active;
    }

}
