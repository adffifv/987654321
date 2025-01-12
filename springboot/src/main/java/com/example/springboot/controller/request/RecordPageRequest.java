package com.example.springboot.controller.request;


import lombok.Data;

@Data
public class RecordPageRequest extends BaseRequest{
    private String doctorName;
    private String userName;
    private String userId;
}
