package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {
    private String baseSql = "SELECT s.name, s.cd, ts.no, ts.point " +
                             "FROM test ts " +
                             "JOIN subject s ON ts.subject_cd = s.cd " +
                             "WHERE ts.student_no = ?";

    public List<TestListStudent> postFilter(ResultSet rs) throws Exception {
        List<TestListStudent> list = new ArrayList<>();
        while (rs.next()) {
            TestListStudent tls = new TestListStudent();
            tls.setSubjectName(rs.getString("name"));
            tls.setSubjectCd(rs.getString("cd"));
            tls.setNo(rs.getString("no"));
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
