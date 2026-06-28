package com.thanakornget.backendjavaapi.model.request;

import lombok.Data;

@Data
public class TripsRequest {
    private Integer id;
    private String title;
    private String url;
    private String description;
    private String[] photos;
    private String[] tags;
}
