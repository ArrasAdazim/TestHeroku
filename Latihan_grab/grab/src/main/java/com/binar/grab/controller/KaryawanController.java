package com.binar.grab.controller;


import com.binar.grab.model.Karyawan;
import com.binar.grab.repository.KaryawanRepository;
import com.binar.grab.service.KaryawanService;
import com.binar.grab.util.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/binar/karyawan")

public class KaryawanController {

    @Autowired
    public KaryawanService karyawanService;

    @Autowired
    public KaryawanRepository karyawanRepository;

    @Autowired
    public TemplateResponse templateResponse;

    @PostMapping("")
    public ResponseEntity<Map> save(@RequestBody Karyawan obj){
        Map save = karyawanService.insert(obj);
        return new ResponseEntity<Map>(save, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Map> update(@RequestBody Karyawan obj){
        Map update = karyawanService.update(obj);
        return new ResponseEntity<Map>(update, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Map> delete(@PathVariable(value = "id") Long id){
        Map delete = karyawanService.delete(id);
        return new ResponseEntity<Map>(delete, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Map> listByBama(
            @RequestParam() Integer page,
            @RequestParam() Integer size,
            @RequestParam(required = false) String nama) {
        Map map = new HashMap();
        Page<Karyawan> list = null;
        Pageable show_data = PageRequest.of(page, size, Sort.by("id").descending());//batasin roq

        if(nama != null && !nama.isEmpty()){
            list = karyawanRepository.findByNama("%"+nama+"%",show_data);
        }else{
            list = karyawanRepository.getAllData(show_data);
        }
        return new ResponseEntity<Map>(templateResponse.templateSukses(list), new HttpHeaders(), HttpStatus.OK);
    }





}
