package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends Dao {
    private String baseSql = "SELECT * FROM student WHERE 1=1";

    // 学生情報を取得
    public Student get(String no) {
        Student student = null;
        Connection con = null;

        try {
            con = getConnection();

            String sql = baseSql + " AND no = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, no);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                SchoolDao schoolDao = new SchoolDao();
                School school = schoolDao.get(rs.getString("school_cd"));
                student = toStudent(rs, school);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return student;
    }

    // 検索結果をリストに変換
    public List<Student> postFilter(ResultSet rs, School school) throws Exception {
        List<Student> list = new ArrayList<>();

        while (rs.next()) {
            list.add(toStudent(rs, school));
        }

        return list;
    }

    // 全条件フィルター
    public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) {
        List<Student> list = new ArrayList<>();
        Connection con = null;

        try {
            con = getConnection();

            String sql = baseSql + " AND school_cd = ? AND ent_year = ? AND class_num = ? AND is_attend = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, school.getCd());
            st.setInt(2, entYear);
            st.setString(3, classNum);
            st.setBoolean(4, isAttend);

            ResultSet rs = st.executeQuery();
            list = postFilter(rs, school);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 学年・出席条件で絞り込み
    public List<Student> filter(School school, int entYear, boolean isAttend) {
        List<Student> list = new ArrayList<>();
        Connection con = null;

        try {
            con = getConnection();

            String sql = baseSql + " AND school_cd = ? AND ent_year = ? AND is_attend = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, school.getCd());
            st.setInt(2, entYear);
            st.setBoolean(3, isAttend);

            ResultSet rs = st.executeQuery();
            list = postFilter(rs, school);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 学校と在学中フラグで絞り込み
    public List<Student> filter(School school, boolean isAttend) {
        List<Student> list = new ArrayList<>();
        Connection con = null;

        try {
            con = getConnection();

            String sql = baseSql + " AND school_cd = ? AND is_attend = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, school.getCd());
            st.setBoolean(2, isAttend);

            ResultSet rs = st.executeQuery();
            list = postFilter(rs, school);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 学生情報を保存
    public boolean save(Student student) {
        boolean success = false;
        Connection con = null;

        try {
            con = getConnection();

            String sql = "INSERT INTO student (no, name, ent_year, class_num, is_attend, school_cd) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            String no = getNo(student.getName());

            st.setString(1, no);
            st.setString(2, student.getName());
            st.setInt(3, student.getEntYear());
            st.setString(4, student.getClassNum());
            st.setBoolean(5, student.isAttend());
            st.setString(6, student.getSchool().getCd());

            int rows = st.executeUpdate();
            success = (rows == 1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    // 学籍番号生成
    public String getNo(String name) {
        return name.substring(0, Math.min(3, name.length())).toUpperCase() + System.currentTimeMillis();
    }

    // ResultSet から Student を構築
    private Student toStudent(ResultSet rs, School school) throws Exception {
        Student s = new Student();
        s.setName(rs.getString("name"));
        s.setEntYear(rs.getInt("ent_year"));
        s.setClassNum(rs.getString("class_num"));
        s.setAttend(rs.getBoolean("is_attend"));
        s.setSchool(school);
        return s;
    }
}
