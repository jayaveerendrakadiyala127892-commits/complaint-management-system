package com.complaintmanagement.dao;

import com.complaintmanagement.model.Complaint;
import com.complaintmanagement.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDAO {

    public boolean submitComplaint(Complaint c) {
        String sql = "INSERT INTO complaints (user_id, subject, message, status, date) VALUES (?, ?, ?, ?, NOW())";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, c.getUserId());
            ps.setString(2, c.getSubject());
            ps.setString(3, c.getMessage());
            ps.setString(4, "Pending");
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Complaint> getComplaintsByUserId(int userId) {
        List<Complaint> list = new ArrayList<>();
        String sql = "SELECT * FROM complaints WHERE user_id = ? ORDER BY date DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Complaint c = new Complaint();
                c.setId(rs.getInt("id"));
                c.setUserId(rs.getInt("user_id"));
                c.setSubject(rs.getString("subject"));
                c.setMessage(rs.getString("message"));
                c.setStatus(rs.getString("status"));
                c.setDate(rs.getString("date"));
                list.add(c);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public List<Complaint> getAllComplaints() {
        List<Complaint> list = new ArrayList<>();
        String sql = "SELECT * FROM complaints ORDER BY date DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Complaint c = new Complaint();
                c.setId(rs.getInt("id"));
                c.setUserId(rs.getInt("user_id"));
                c.setSubject(rs.getString("subject"));
                c.setMessage(rs.getString("message"));
                c.setStatus(rs.getString("status"));
                c.setDate(rs.getString("date"));
                list.add(c);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public boolean updateStatus(int complaintId, String status) {
        String sql = "UPDATE complaints SET status = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, complaintId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // NEW METHOD: Counts complaints by status (Pending, Resolved, etc.)
    public int getComplaintCountByStatus(String status) {
        String sql = "SELECT COUNT(id) AS count FROM complaints WHERE status = ?";
        int count = 0;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    
    // NEW METHOD: Counts new complaints submitted today
    public int getNewComplaintCount() {
        // Assumes a column named 'date' in your complaints table.
        String sql = "SELECT COUNT(id) AS count FROM complaints WHERE DATE(date) = CURDATE()";
        int count = 0;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}