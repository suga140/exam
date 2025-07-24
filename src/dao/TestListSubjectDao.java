package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

    private String baseSql =
        "SELECT st.ent_year, st.no AS student_no, st.name, st.class_num, " +
        "ts.subject_cd, ts.no AS test_num, ts.point " +
        "FROM student st " +
        "JOIN test ts ON st.no = ts.student_no " +
        "WHERE st.ent_year = ? AND st.class_num = ? AND ts.subject_cd = ? AND st.school_cd = ?";

    public List<TestListSubject> postFilter(ResultSet rs) throws Exception {
        Map<String, TestListSubject> map = new LinkedHashMap<>();
        while (rs.next()) {
            String studentNo = rs.getString("student_no");

            // すでにMapにあるか確認
            TestListSubject subject = map.get(studentNo);
            if (subject == null) {
                subject = new TestListSubject();
                subject.setEntYear(rs.getInt("ent_year"));
                subject.setStudentNo(studentNo);
                subject.setStudentName(rs.getString("name"));
                subject.setClassNum(rs.getString("class_num"));
                subject.setPoints(new java.util.HashMap<>());
                map.put(studentNo, subject);
            }

            // 点数情報をMapに追加（テスト番号をキーに）
            int testNum = rs.getInt("test_num");
            int point = rs.getInt("point");

            subject.putPoint(testNum, point);
        }
        return new ArrayList<>(map.values());
    }

    public List<TestListSubject> filter(int entYear, String classNum, bean.Subject subject, School school) {
        List<TestListSubject> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(baseSql)) {

            stmt.setInt(1, entYear);
            stmt.setString(2, classNum);
            stmt.setString(3, subject.getCd());
            stmt.setString(4, school.getCd());

            try (ResultSet rs = stmt.executeQuery()) {
                list = postFilter(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
