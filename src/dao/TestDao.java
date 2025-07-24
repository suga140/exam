package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

    // 基本SQL
    private final String baseSql =
        "SELECT " +
        " test.no AS test_no, test.point, test.class_num AS test_class_num, test.subject_cd, " +
        " subject.name AS subject_name, " +
        " student.no AS student_no, student.name, student.ent_year, " +
        " student.class_num AS student_class_num, student.is_attend " +
        "FROM test " +
        "INNER JOIN student ON test.student_no = student.no " +
        "INNER JOIN subject ON test.subject_cd = subject.cd AND test.school_cd = subject.school_cd " +
        "WHERE student.school_cd = ?";

    // フィルター検索
    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
        List<Test> list;
        Connection con = getConnection();

        String sql = baseSql +
            " AND student.ent_year = ?" +
            " AND test.class_num = ?" +
            " AND test.subject_cd = ?" +
            " AND test.no = ?" +
            " ORDER BY test.student_no";

        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, school.getCd());
        st.setInt(2, entYear);
        st.setString(3, classNum);
        st.setString(4, subject.getCd());
        st.setInt(5, num);

        ResultSet rs = st.executeQuery();
        list = postfilter(rs, school);

        rs.close();
        st.close();
        con.close();

        return list;
    }

    // 特定のテスト1件取得
    public Test get(Student student, Subject subject, School school, int no) throws Exception {
        Test test = null;
        Connection con = getConnection();

        String sql = baseSql +
            " AND student.no = ?" +
            " AND test.subject_cd = ?" +
            " AND test.no = ?";

        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, school.getCd());
        st.setString(2, student.getNo());
        st.setString(3, subject.getCd());
        st.setInt(4, no);

        ResultSet rs = st.executeQuery();
        List<Test> list = postfilter(rs, school);
        if (!list.isEmpty()) {
            test = list.get(0);
        }

        rs.close();
        st.close();
        con.close();

        return test;
    }

    // テスト一覧保存
    public boolean save(List<Test> list) throws Exception {
        boolean success = true;
        Connection con = getConnection();
        try {
            con.setAutoCommit(false);
            for (Test test : list) {
                if (!save(test, con)) {
                    success = false;
                    break;
                }
            }
            if (success) {
                con.commit();
            } else {
                con.rollback();
            }
        } catch (Exception e) {
            con.rollback();
            success = false;
            throw e;
        } finally {
            con.setAutoCommit(true);
            con.close();
        }
        return success;
    }

    // ResultSet → Testリスト生成
    private List<Test> postfilter(ResultSet rs, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        while (rs.next()) {
            Test test = new Test();

            // Studentの設定
            Student student = new Student();
            student.setNo(rs.getString("student_no"));
            student.setName(rs.getString("name"));
            student.setEntYear(rs.getInt("ent_year"));
            student.setClassNum(rs.getString("student_class_num"));
            student.setAttend(rs.getBoolean("is_attend"));
            student.setSchool(school);
            test.setStudent(student);

            // Subjectの設定
            Subject subject = new Subject();
            subject.setCd(rs.getString("subject_cd"));
            subject.setName(rs.getString("subject_name"));
            subject.setSchool(school);
            test.setSubject(subject);

            // Testの設定
            test.setNo(rs.getInt("test_no"));
            test.setPoint(rs.getInt("point"));
            test.setClassNum(rs.getString("test_class_num"));
            test.setSchool(school);

            list.add(test);
        }
        return list;
    }

    // 単一テストの更新用 save
    private boolean save(Test test, Connection con) throws Exception {
        String sql =
        		"UPDATE test SET point = ? "
        		+ "WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ? AND class_num = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setInt(1, test.getPoint());
        st.setString(2, test.getStudent().getNo());
        st.setString(3, test.getSubject().getCd());
        st.setString(4, test.getSchool().getCd());
        st.setInt(5, test.getNo());
        st.setString(6, test.getClassNum());

        int rows = st.executeUpdate();
        st.close();
        return rows > 0;
    }
}