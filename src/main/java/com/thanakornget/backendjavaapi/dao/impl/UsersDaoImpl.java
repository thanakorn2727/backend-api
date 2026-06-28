package com.thanakornget.backendjavaapi.dao.impl;

import com.thanakornget.backendjavaapi.dao.UsersDao;
import com.thanakornget.backendjavaapi.model.response.UsersResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UsersDaoImpl implements UsersDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UsersResponse> searchUsers(String keyword, String status) {
        StringBuilder sql = new StringBuilder("""
                SELECT id, username, first_name, last_name, email, phone, status, created_at, updated_at
                FROM users
                WHERE 1 = 1
                """);

        Map<String, Object> params = new HashMap<>();

        if (keyword != null && !keyword.isBlank()) {
            sql.append("""
                     AND (
                        LOWER(username) LIKE :keyword
                        OR LOWER(first_name) LIKE :keyword
                        OR LOWER(last_name) LIKE :keyword
                        OR LOWER(email) LIKE :keyword
                        OR LOWER(phone) LIKE :keyword
                     )
                    """);
            params.put("keyword", "%" + keyword.toLowerCase() + "%");
        }

        if (status != null && !status.isBlank()) {
            sql.append(" AND status = :status");
            params.put("status", status);
        }

        sql.append(" ORDER BY id DESC");

        Query query = entityManager.createNativeQuery(sql.toString());
        params.forEach(query::setParameter);

        @SuppressWarnings("unchecked")
        List<Object[]> rows = query.getResultList();

        return rows.stream()
                .map(this::mapRowToResponse)
                .toList();
    }

    private UsersResponse mapRowToResponse(Object[] row) {
        return new UsersResponse(
                toLong(row[0]),
                toString(row[1]),
                toString(row[2]),
                toString(row[3]),
                toLocalDateTime(row[4]),
                toString(row[5]),
                toLong(row[6]),
                toLong(row[7]),
                toLong(row[8]),
                toLong(row[9]),
                toString(row[10]),
                toString(row[11]),
                toLocalDateTime(row[12]),
                toString(row[13]),
                toLocalDateTime(row[14]),
                toString(row[15]),
                toString(row[16])
        );
    }

    private Long toLong(Object value) {
        return value == null ? null : ((Number) value).longValue();
    }

    private String toString(Object value) {
        return value == null ? null : value.toString();
    }

    private LocalDateTime toLocalDateTime(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof LocalDateTime localDateTime) {
            return localDateTime;
        }
        if (value instanceof Timestamp timestamp) {
            return timestamp.toLocalDateTime();
        }
        return LocalDateTime.parse(value.toString().replace(" ", "T"));
    }
}
