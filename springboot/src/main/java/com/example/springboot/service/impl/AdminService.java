package com.example.springboot.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.tokenizer.TokenizerUtil;
import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.PasswordRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Admin;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.AdminMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.IAdminService;
import com.example.springboot.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AdminService implements IAdminService {

    @Autowired
    AdminMapper adminMapper;

    private static final String DEFAULT_PASS = "123";
    private static final String PASS_SALT = "hyc";

    @Override
    public List<Admin> list() {
        return adminMapper.list();
    }

    @Override
    public PageInfo<Admin> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(),baseRequest.getPageSize());
        List<Admin> users = adminMapper.listByCondition(baseRequest);
        return new PageInfo<>(users);

    }

    @Override
    public void save(Admin obj) {
       //默认密码123
        if (StrUtil.isBlank(obj.getPassword())){
            obj.setPassword(DEFAULT_PASS);
        }
        obj.setPassword(securePass(obj.getPassword())); //设置m5加密
        adminMapper.save(obj);
    }

    @Override
    public Admin getById(Integer id) {
        return adminMapper.getById(id);

    }

    @Override
    public void update(Admin obj) {
        obj.setUpdatetime(new Date());
        adminMapper.updateById(obj);
    }

    @Override
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    @Override
    public LoginDTO login(LoginRequest request) {

        Admin admin = null;
        try {
            admin = adminMapper.getByUsername(request.getUsername());
        } catch (Exception e){
            log.error("根据用户名{} 查询出错",request.getUsername());
            throw new ServiceException("用户名错误");
        }
//        Admin admin = adminMapper.getByUsername(request.getUsername());
//        Admin admin= adminMapper.getByUsernameAndPassword(request.getUsername(),request.getPassword());
        //  当你的admin存在的时候再去判断密码是否合法

        if (admin == null){
            throw new ServiceException("用户名或密码错误");
        }
        //判断密码是否合法
        String securePass =   securePass(request.getPassword());
        if (!securePass.equals(admin.getPassword())){
            throw new ServiceException("用户名或密码错误");
        }

        if (!admin.isStatus()){
            throw new ServiceException("当前用户处于禁用状态，请联系管理员");
        }
        LoginDTO loginDTO = new LoginDTO();
        BeanUtils.copyProperties(admin,loginDTO);

        //生成token
//        String token = TokenUtils.genToken
        return loginDTO;

    }

    @Override
    public void changePass(PasswordRequest request) {
        // 注意：你要对新的密码进行加密
        request.setNewPass(securePass(request.getNewPass()));
        int count = adminMapper.updatePassword(request);
        if (count <= 0){
            throw new ServiceException("修改密码失败");
        }
    }

    private String securePass(String password){

        return SecureUtil.md5(password+PASS_SALT);
//        return password+PASS_SALT;
    }

}
