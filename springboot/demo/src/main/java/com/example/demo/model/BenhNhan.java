package com.example.demo.model;

import jakarta.persistence.*;
import java.sql.Date;


@Entity // Đánh dấu đây là một Entity
@Table(name = "benh_nhan") // Ánh xạ với bảng "benh_nhan" trong CSDL
public class BenhNhan {
    @Id // Đánh dấu đây là khóa chính
    @Column(name = "id")
    private String id;

    @Column(name = "ten") // Ánh xạ với cột "ten" trong bảng
    private String ten;

    @Column(name = "gioi") // Ánh xạ với cột "gioi"
    private String gioiTinh;

    @Column(name = "dia_chi") // Ánh xạ với cột "dia_chi"
    private String diaChi;

    @Column(name = "ngay_sinh") // Ánh xạ với cột "ngay_sinh"
    @Temporal(TemporalType.DATE) // Ánh xạ với kiểu DATE trong CSDL
    private Date ngaySinh;

    @Column(name = "bhyt") // Ánh xạ với cột "bhyt"
    private String bhyt;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getBhyt() {
        return bhyt;
    }

    public void setBhyt(String bhyt) {
        this.bhyt = bhyt;
    }
    public BenhNhan() {}

    // Constructor có tham số
    public BenhNhan(String id, String ten, String gioiTinh, String diaChi, Date ngaySinh, String bhyt) {
        this.id = id;
        this.ten = ten;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.ngaySinh = ngaySinh;
        this.bhyt = bhyt;
    }
}