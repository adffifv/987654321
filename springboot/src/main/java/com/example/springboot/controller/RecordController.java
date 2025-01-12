package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.request.RecordPageRequest;
import com.example.springboot.entity.Record;
import com.example.springboot.entity.Retur;
import com.example.springboot.service.IRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    IRecordService recordService;



    @PostMapping("/save")
    public Result  save(@RequestBody Record obj){
        recordService.save(obj);
        return Result.success();
    }

    @PutMapping("/update")
    public Result  update(@RequestBody Record obj){
        obj.setUpdatetime(LocalDate.now());
        recordService.update(obj);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        recordService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Record obj = recordService.getById(id);
        return Result.success(obj);
    }

    @GetMapping("/list")
    public Result list(){
        List<Record> list = recordService.list();
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result page(RecordPageRequest PageRequest){

        return Result.success( recordService.page(PageRequest));
    }

    @GetMapping("/pageRetur")
    public Result pageRetur(RecordPageRequest PageRequest){

        return Result.success( recordService.pageRetur(PageRequest));
    }

    @PostMapping("/saveRetur")
    public Result saveRetur(@RequestBody Retur obj){
        recordService.saveRetur(obj);
        return Result.success();
    }

    @DeleteMapping("/deleteRetur/{id}")
    public Result deleteRetur(@PathVariable Integer id){
        recordService.deleteReturById(id);
        return Result.success();
    }
}
