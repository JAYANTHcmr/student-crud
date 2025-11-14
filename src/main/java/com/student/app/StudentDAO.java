package com.student.app;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public void addStudent(Student s) throws Exception {
        String sql = "INSERT INTO students (name, email, course, age) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, s.getName());
            pst.setString(2, s.getEmail());
            pst.setString(3, s.getCourse());
            pst.setInt(4, s.getAge());
            pst.executeUpdate();
        }
    }

    public void updateStudent(Student s) throws Exception {
        String sql = "UPDATE students SET name=?, email=?, course=?, age=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, s.getName());
            pst.setString(2, s.getEmail());
            pst.setString(3, s.getCourse());
            pst.setInt(4, s.getAge());
            pst.setInt(5, s.getId());
            pst.executeUpdate();
        }
    }

    public void deleteStudent(int id) throws Exception {
        String sql = "DELETE FROM students WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        }
    }

    public Student getStudentById(int id) throws Exception {
        String sql = "SELECT * FROM students WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return mapRowToStudent(rs);
            }
        }
        return null;
    }

    public List<Student> getAllStudents() throws Exception {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) list.add(mapRowToStudent(rs));
        }
        return list;
    }

    private Student mapRowToStudent(ResultSet rs) throws SQLException {
        return new Student(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("course"),
            rs.getInt("age")
        );
    }
}
