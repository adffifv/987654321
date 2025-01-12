package com.example.springboot.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Doctor;
import com.example.springboot.mapper.DoctorMapper;
import com.example.springboot.service.IDoctorService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class DoctorService implements IDoctorService {
    
    @Resource
    DoctorMapper doctorMapper;
    
    @Override
    public List<Doctor> list() {
        return doctorMapper.list();
    }

    @Override
    public PageInfo<Doctor> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(),baseRequest.getPageSize());
        return new PageInfo<>(doctorMapper.listByCondition(baseRequest));

    }

    @Override
    public void save(Doctor obj) {

        obj.setCategory(category(obj.getCategories()));
        doctorMapper.save(obj);
    }


    @Override
    public Doctor getById(Integer id) {
        return doctorMapper.getById(id);
    }

    @Override
    public List<Doctor> getByCategory(String category) {
        return doctorMapper.getByCategory(category);
    }

    @Override
    public void update(Doctor obj) {
        obj.setCategory(category(obj.getCategories()));
//        obj.setUpdatetime(LocalDate.now());
        doctorMapper.updateById(obj);
    }

    @Override
    public void deleteById(Integer id) {

        doctorMapper.deleteById(id);
    }

    private String category(List<String> categories){

        StringBuilder sb = new StringBuilder();
        if (CollUtil.isNotEmpty(categories)){
            categories.forEach(v -> sb.append(v).append(">"));
            return sb.substring(0, sb.lastIndexOf(">"));
        }
        return sb.toString();

    }
}
