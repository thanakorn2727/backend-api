package com.thanakornget.backendjavaapi.repository.impl;

import com.thanakornget.backendjavaapi.model.request.TripsRequest;
import com.thanakornget.backendjavaapi.model.response.TripsResponse;
import com.thanakornget.backendjavaapi.repository.TripsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
@Repository
@AllArgsConstructor
public class TripsRepositoryImpl implements TripsRepository {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate npjdbcTemplate;

    public List<TripsResponse> getListTrips(TripsRequest body) throws Exception{
        System.out.println("body eee ::>>>>" + body);
        String query = "t.id, \n" +
                "t.title, \n" +
                "t.url, \n" +
                "t.description \n" +
//                "COALESCE(jsonb_agg(distinct to_jsonb(p)) FILTER (WHERE p.id IS NOT NULL), '[]') as photoslist,\n" +
//                "COALESCE(jsonb_agg(distinct to_jsonb(ta)) FILTER (WHERE ta.id IS NOT NULL), '[]') as tagslist\n" +
                "from trips t\n" +
                "join photos p on t.id = p.trips_id \n" +
                "join tags ta on t.id = ta.trips_id \n" +
                "where t.active_flag = 'Y'\n";
        List<Object> paramList = new ArrayList<>();
        if(body.getId()!=null){
            query +="and t.id = ? \n";
            paramList.add(body.getId());
        }
        query +="group by t.id, \n" +
                "t.title, \n" +
                "t.url, \n" +
                "t.description";
        Object[] params = paramList.toArray();
        return jdbcTemplate.query(query, params, (rs, rowNum) -> {
            TripsResponse model = new TripsResponse();
            model.setId(rs.getInt("id"));
            model.setUrl(rs.getString("url"));
            model.setTitle(rs.getString("title"));
            model.setDescription(rs.getString("description"));
//            model.setPhotos(rs.getArray("photoslist"));
            return model;
        });
    }
}
