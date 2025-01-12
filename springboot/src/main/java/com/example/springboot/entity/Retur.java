package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data

public class Retur {

    /**
     * id
     */
    private Integer id;

    /**
     * 医生姓名
     */
    private String doctorName;
    private String status;

    private String category;
    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户联系方式
     */
    private String userPhone;
    private String descripe;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GNT+8")
    private LocalDate createtime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GNT+8")
    private LocalDate updatetime;

    /**
     * 余额
     */
    private Integer score;
    private Integer account;
    private Integer doctorId;

}
