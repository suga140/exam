package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {
    private String baseSql = "SELECT s.subject_name, s.subject_cd, ts.num, ts.point " +
                             "FROM test_scores ts " +
                             "JOIN subjects s ON ts.subject_cd = s.subject_cd " +
                             "WHERE ts.student_no = ?";

    public List<TestListStudent> postFilter(ResultSet rs) throws Exception {
        List<TestListStudent> list = new ArrayList<>();
        while (rs.next()) {
            TestListStudent tls = new TestListStudent();
            tls.setSubjectName(rs.getString("subject_name"));
            tls.setSubjectCd(rs.getString("subject_cd"));
            tls.setNum(rs.getString("num"));
            tls.setPoint(rs.getInt("point"));
            list.add(tls);
        }
        return list;
    }

    public List<TestListStudent> filter(Student student) {
        List<TestListStudent> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(baseSql)) {

            stmt.setString(1, student.getNo());

            try (ResultSet rs = stmt.executeQuery()) {
                list = postFilter(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
