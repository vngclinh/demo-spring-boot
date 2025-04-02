package com.example.demo.repository;
import com.example.demo.model.BenhNhan;
import java.sql.Connection;
import java.sql.DriverManager;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BenhNhanDAO {
    // Thông tin kết nối SQL Server
    String url = "jdbc:sqlserver://localhost:1500;databaseName=quanlysinhvien;encrypt=false;trustServerCertificate=true;";
    String user = "sa";
    String password = "123";

    // Phương thức lấy danh sách bệnh nhân
    public List<BenhNhan> getAllBenhNhan() {
        List<BenhNhan> benhNhanList = new ArrayList<>();
        String query = "SELECT * FROM benh_nhan";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                BenhNhan benhNhan = new BenhNhan();
                benhNhan.setId(rs.getString("id"));
                benhNhan.setTen(rs.getString("ten"));
                benhNhan.setGioiTinh(rs.getString("gioi"));
                benhNhan.setDiaChi(rs.getString("dia_chi"));
                benhNhan.setNgaySinh(rs.getDate("ngay_sinh"));
                benhNhan.setBhyt(rs.getString("bhyt"));
                benhNhanList.add(benhNhan);
            }
        } catch (SQLException e) {
            System.out.println(" Lỗi kết nối SQL Server!");
            e.printStackTrace();
        }
        return benhNhanList;
    }
    public void xoaBenhNhan(String id) {
        String query = "DELETE FROM benh_nhan WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa bệnh nhân!");
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi xóa bệnh nhân từ CSDL", e);
        }
    }
    // BenhNhanDAO.java
public BenhNhan suaBenhNhan(BenhNhan benhNhan) {
    String query = "UPDATE benh_nhan SET ten=?, gioi=?, dia_chi=?, ngay_sinh=?, bhyt=? WHERE id=?";
    
    try (Connection conn = DriverManager.getConnection(url, user, password);
         PreparedStatement ps = conn.prepareStatement(query)) {
        
        ps.setString(1, benhNhan.getTen());
        ps.setString(2, benhNhan.getGioiTinh());
        ps.setString(3, benhNhan.getDiaChi());
        ps.setDate(4, new java.sql.Date(benhNhan.getNgaySinh().getTime()));
        ps.setString(5, benhNhan.getBhyt());
        ps.setString(6, benhNhan.getId());
        
        ps.executeUpdate();
        return benhNhan;
    } catch (SQLException e) {
        throw new RuntimeException("Lỗi khi cập nhật bệnh nhân", e);
    }
}
    
}