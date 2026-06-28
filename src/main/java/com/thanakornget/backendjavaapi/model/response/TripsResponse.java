package com.thanakornget.backendjavaapi.model.response;

import lombok.Data;

@Data
public class TripsResponse {
    private Integer id;
    private String title;
    private String url;
    private String description;
    private String[] photos;
    private String[] tags;
}
