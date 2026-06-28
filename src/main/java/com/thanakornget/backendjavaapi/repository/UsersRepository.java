package com.thanakornget.backendjavaapi.repository;

import com.thanakornget.backendjavaapi.entity.UsersEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
    @Query(value = "SELECT * FROM users WHERE id = :id", nativeQuery = true)
    Optional<UsersEntity> findUserById(@Param("id") Long id);

    @Query(value = "SELECT * FROM users WHERE active_flag = 'Y' ORDER BY id DESC", nativeQuery = true)
    List<UsersEntity> findAllUsers();

    @Query(value = "SELECT COUNT(1) FROM users WHERE first_name = :firstName", nativeQuery = true)
    long countByUsername(@Param("firstName") String firstName);

    @Query(value = "SELECT COUNT(1) FROM users WHERE first_name = :firstName AND id <> :id", nativeQuery = true)
    long countByUsernameAndIdNot(@Param("firstName") String firstName, @Param("id") Long id);

    @Modifying
    @org.springframework.data.jpa.repository.Query(value = """
            UPDATE users
            SET id_card = :idCard,
                first_name = :firstName,
                last_name = :lastName,
                birth_date = :birthDate,
                address = :address,
                sub_district_id = :subDistrictId,
                district_id = :districtId,
                province_id = :provinceId,
                postal_code = :postalCode,
                email = :email,
                phone_number = :phoneNumber,
                update_date = CURRENT_TIMESTAMP
            WHERE id = :id
            """, nativeQuery = true)
    int updateUser(@Param("id") Long id,
                   @Param("idCard") String idCard,
                   @Param("firstName") String firstName,
                   @Param("lastName") String lastName,
                   @Param("birthDate") String birthDate,
                   @Param("address") String address,
                   @Param("subDistrictId") String subDistrictId,
                   @Param("districtId") String districtId,
                   @Param("provinceId") String provinceId,
                   @Param("postalCode") String postalCode,
                   @Param("email") String email,
                   @Param("phoneNumber") String phoneNumber);

    @Modifying
    @org.springframework.data.jpa.repository.Query(value = "UPDATE users SET active_flag = 'N' WHERE id = :id", nativeQuery = true)
    int deleteUserById(@Param("id") Long id);
}
