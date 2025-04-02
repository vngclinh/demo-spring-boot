package com.example.demo.controller;

import com.example.demo.repository.BenhNhanDAO;
import com.example.demo.service.BenhNhanService;
import com.example.demo.model.BenhNhan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")  // Đổi lại endpoint chính
public class BenhNhanController {

    @Autowired
    private BenhNhanDAO benhNhanDAO;  // Inject DAO vào đây

    @GetMapping("/benh-nhan")  // API sẽ là: http://localhost:8080/api/benh-nhan
    public List<BenhNhan> getAllBenhNhan() {
        return benhNhanDAO.getAllBenhNhan();
    }

        @Autowired
    private BenhNhanService benhNhanService;

    @PostMapping("/benh-nhan")
    public BenhNhan themBenhNhan(@RequestBody BenhNhan benhNhan) {
        return benhNhanService.themBenhNhan(benhNhan);
    }
    @DeleteMapping("/benh-nhan/{id}")
public ResponseEntity<?> xoaBenhNhan(@PathVariable String id) {
    try {
        benhNhanService.xoaBenhNhan(id);
        return ResponseEntity.ok().build();
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
// BenhNhanController.java
@PutMapping("/benh-nhan/{id}")
public ResponseEntity<BenhNhan> suaBenhNhan(
        @PathVariable String id,
        @RequestBody BenhNhan benhNhan
) {
    benhNhan.setId(id); // Đảm bảo ID không bị thay đổi
    BenhNhan updatedBenhNhan = benhNhanService.suaBenhNhan(benhNhan);
    return ResponseEntity.ok(updatedBenhNhan);
}
}
