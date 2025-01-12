package com.example.springboot.service;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Record;
import com.example.springboot.entity.Retur;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IRecordService {
    List<Record> list();

    PageInfo<Record> page(BaseRequest baseRequest);

    void save(Record obj);

    PageInfo<Retur> pageRetur(BaseRequest baseRequest);

    void saveRetur(Retur obj);

    Record getById(Integer id);

    void update(Record obj);

    void deleteById(Integer id);
    void deleteReturById(Integer id);

}
