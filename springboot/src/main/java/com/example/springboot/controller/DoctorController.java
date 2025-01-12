package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.request.DoctorPageRequest;
import com.example.springboot.entity.Doctor;
import com.example.springboot.service.IDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    IDoctorService doctorService;

    @PostMapping("/save")
    public Result  save(@RequestBody Doctor obj){
        doctorService.save(obj);
        return Result.success();
    }

    @PutMapping("/update")
    public Result  update(@RequestBody Doctor obj){
        obj.setUpdatetime(LocalDate.now());
        doctorService.update(obj);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        doctorService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Doctor obj = doctorService.getById(id);
        return Result.success(obj);
    }

    @GetMapping("/doctor/{category}")
    public Result getByCategory(@PathVariable String category){
        List<Doctor> doctors = doctorService.getByCategory(category);
        return Result.success(doctors);
    }

    @GetMapping("/list")
    public Result list(){
        List<Doctor> list = doctorService.list();
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result page(DoctorPageRequest PageRequest){

        return Result.success( doctorService.page(PageRequest));
    }

}
