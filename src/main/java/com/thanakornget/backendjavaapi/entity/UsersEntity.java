package com.thanakornget.backendjavaapi.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_card", length = 13)
    private String idCard;
    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "birth_date", nullable = false, updatable = false)
    private LocalDateTime birthDate;
    @Column(name = "address", length = 500)
    private String address;
    @Column(name = "sub_district_id", length = 6)
    private Long subDistrictId;
    @Column(name = "district_id", length = 6)
    private Long districtId;
    @Column(name = "province_id", length = 6)
    private Long provinceId;
    @Column(name = "postal_code", length = 6)
    private Long postalCode;
    @Column(name = "email", length = 150)
    private String email;
    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;
    @Column(name = "create_by", length = 50)
    private String createBy;
    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;
    @Column(name = "update_by", length = 50)
    private String updateBy;
    @Column(name = "active_flag", length = 30)
    private String active;
    @PrePersist
    void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createDate = now;
        updateDate = now;
        if (active == null || active.isBlank()) {
            active = "Y";
        }
    }

    @PreUpdate
    void preUpdate() {
        updateDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public Long getSubDistrictId() {
        return subDistrictId;
    }

    public void setSubDistrictId(Long subDistrictId) {
        this.subDistrictId = subDistrictId;
    }
    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }
    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }
    public Long getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Long postalCode) {
        this.postalCode = postalCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
    public String getCreateBy() {
        return createBy;
    }
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
    public String getUpdateBy() {
        return updateBy;
    }
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
