package com.example.springboot.service.impl;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Doctor;
import com.example.springboot.entity.Record;
import com.example.springboot.entity.Retur;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.DoctorMapper;
import com.example.springboot.mapper.RecordMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.IRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class RecordService implements IRecordService {
    
    @Resource
    RecordMapper recordMapper;
    @Autowired
    UserMapper userMapper;

    @Autowired
    DoctorMapper doctorMapper;
    @Override
    public List<Record> list() {
        return recordMapper.list();
    }

    @Override
    public PageInfo<Record> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(),baseRequest.getPageSize());
        return new PageInfo<>(recordMapper.listByCondition(baseRequest));

    }

    @Override
    @Transactional
    public void save(Record obj) {

        String userId = obj.getUserId();
        User user = userMapper.getByuserId(userId);
        Doctor doctor= doctorMapper.getById(obj.getDoctorId());
        Integer score=doctor.getAuthor();
        Integer account= user.getAccount();
        if (score>account){
            throw new ServiceException("病人余额不足");
        }

        if (Objects.isNull(user)){
            throw new  ServiceException("病人不存在");
        }
        user.setAccount(user.getAccount()- score);
        userMapper.updateById(user);
        recordMapper.save(obj);
    }

    @Override
    public PageInfo<Retur> pageRetur(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(),baseRequest.getPageSize());
        return new PageInfo<>(recordMapper.listReturByCondition(baseRequest));

    }

    @Override
    public void saveRetur(Retur obj) {
        obj.setStatus("已就诊");
        recordMapper.updateStatus("已就诊",obj.getId());

//        obj.setId(null);
        recordMapper.saveRetur(obj);
    }


    @Override
    public Record getById(Integer id) {
        return recordMapper.getById(id);
    }

    @Override
    public void update(Record obj) {

//        obj.setUpdatetime(new Date());
        recordMapper.updateById(obj);
    }

    @Override
    public void deleteById(Integer id) {

        recordMapper.deleteById(id);
    }

    @Override
    public void deleteReturById(Integer id) {
        recordMapper.deleteReturById(id);
    }


}
