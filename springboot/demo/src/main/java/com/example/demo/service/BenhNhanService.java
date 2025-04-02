package com.example.demo.service;

import com.example.demo.model.BenhNhan;
import com.example.demo.repository.BenhNhanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BenhNhanService {

    @Autowired
    private BenhNhanRepository benhNhanRepository;

    public BenhNhan themBenhNhan(BenhNhan benhNhan) {
        return benhNhanRepository.save(benhNhan);
    }
    public void xoaBenhNhan(String id) {
        benhNhanRepository.deleteById(id); // JPA tự động DELETE
    }
    // BenhNhanService.java
public BenhNhan suaBenhNhan(BenhNhan benhNhan) {
    return benhNhanRepository.save(benhNhan); // JPA tự động UPDATE nếu ID đã tồn tại
}
}