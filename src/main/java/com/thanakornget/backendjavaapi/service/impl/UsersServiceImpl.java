package com.thanakornget.backendjavaapi.service.impl;

import com.sun.jdi.request.DuplicateRequestException;
import com.thanakornget.backendjavaapi.dao.UsersDao;
import com.thanakornget.backendjavaapi.entity.UsersEntity;
import com.thanakornget.backendjavaapi.exception.ResourceNotFoundException;
import com.thanakornget.backendjavaapi.model.request.UsersRequest;
import com.thanakornget.backendjavaapi.model.response.UsersResponse;
import com.thanakornget.backendjavaapi.repository.UsersRepository;
import com.thanakornget.backendjavaapi.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {
    private static final DateTimeFormatter BIRTH_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

    private final UsersRepository usersRepository;
    private final UsersDao usersDao;

    public UsersServiceImpl(UsersRepository usersRepository, UsersDao usersDao) {
        this.usersRepository = usersRepository;
        this.usersDao = usersDao;
    }

    @Override
    @Transactional
    public UsersResponse createUser(UsersRequest request) {
        System.out.print("request ::::" + request);
        if (usersRepository.countByUsername(request.getFirstName()) > 0) {
            throw new DuplicateRequestException("Username already exists");
        }

        UsersEntity entity = new UsersEntity();
        applyRequest(entity, request);

        return mapToResponse(usersRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public UsersResponse getUserById(Long id) {
        System.out.print("id >>>>" + id);
        UsersResponse userNotFound = usersRepository.findUserById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userNotFound;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsersResponse> getUsers() {
        return usersRepository.findAllUsers().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsersResponse> searchUsers(String keyword, String status) {
        return usersDao.searchUsers(keyword, status);
    }

    @Override
    @Transactional
    public UsersResponse updateUser(Long id, UsersRequest request) {
        UsersEntity current = usersRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found: " + id));

        applyRequest(current, request);

        return mapToResponse(usersRepository.save(current));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        int deleted = usersRepository.deleteUserById(id);
        if (deleted == 0) {
            throw new ResourceNotFoundException("User not found");
        }
    }

    private void applyRequest(UsersEntity entity, UsersRequest request) {
        entity.setIdCard(request.getIdCard());
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setBirthDate(parseBirthDate(request.getBirthDate()));
        entity.setAddress(request.getAddress());
        entity.setSubDistrictId(request.getSubDistrictId());
        entity.setDistrictId(request.getDistrictId());
        entity.setProvinceId(request.getProvinceId());
        entity.setPostalCode(request.getPostalCode());
        entity.setEmail(request.getEmail());
        entity.setPhoneNumber(request.getPhoneNumber());
    }

    private LocalDateTime parseBirthDate(String birthDate) {
        try {
            return LocalDate.parse(birthDate, BIRTH_DATE_FORMATTER).atStartOfDay();
        } catch (DateTimeParseException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "birthDate must use dd/MM/yyyy format and be a valid date"
            );
        }
    }

    private String defaultStatus(String status) {
        return status == null || status.isBlank() ? "Y" : status;
    }

    private UsersResponse mapToResponse(UsersEntity entity) {
        return new UsersResponse(
                entity.getId(),
                entity.getIdCard(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getBirthDate(),
                entity.getAddress(),
                entity.getSubDistrictId(),
                entity.getDistrictId(),
                entity.getProvinceId(),
                entity.getPostalCode(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getCreateDate(),
                entity.getCreateBy(),
                entity.getUpdateDate(),
                entity.getUpdateBy(),
                entity.getActive()
        );
    }
}
