package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
public class Doctor implements Serializable {
    private static final long serialVersionUID=1L;
//
    private Integer id;
    private String name;
    private String description;
    private String publishDate;
    private String overDate;
    private Integer author;
    private String category;
    private String cover;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GNT+8")
    private LocalDate createtime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GNT+8")
    private LocalDate updatetime;
    private List<String> categories;
}
