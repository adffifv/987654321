package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Doctor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DoctorMapper {

//    @Select("select* from user")
    List<Doctor> list();

    List<Doctor> listByCondition(BaseRequest baseRequest);


    void save(Doctor obj);

    Doctor getById(Integer id);

    List<Doctor> getByCategory(String category);

    void updateById(Doctor obj);

    void deleteById(Integer id);

}

