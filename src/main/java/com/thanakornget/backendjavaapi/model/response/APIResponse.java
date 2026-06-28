package com.thanakornget.backendjavaapi.model.response;

import lombok.Data;

@Data
public class APIResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public APIResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;

    }

    public static <T> APIResponse<T> empty() { return new APIResponse<>(false, null, null);}
    public static <T> APIResponse<T> success(String message, T data) { return new APIResponse<>(true, message, data);}
    public static <T> APIResponse<T> error(String message) { return new APIResponse<>(false, message, null);}
}
