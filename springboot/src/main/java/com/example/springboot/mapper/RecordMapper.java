package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Record;
import com.example.springboot.entity.Retur;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecordMapper {

//    @Select("select* from user")
    List<Record> list();

    List<Record> listByCondition(BaseRequest baseRequest);

    List<Retur> listReturByCondition(BaseRequest baseRequest);


    void save(Record obj);
    void saveRetur(Retur obj);

    Record getById(Integer id);

    void updateById(Record obj);

    void deleteById(Integer id);

    void deleteReturById(Integer id);

    void updateStatus(String status,Integer id);
}

