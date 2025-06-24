package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

public class ClassNumDao extends Dao {

    /**
     * 指定されたクラス番号と学校に一致するClassNumインスタンスを1件取得する
     */
    public ClassNum get(String class_num, School school) {
        ClassNum cn = null;

        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM class_num WHERE class_num = ? AND school_cd = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, class_num);
            st.setString(2, school.getCd());
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                cn = new ClassNum(rs.getString("class_num"), school);
            }

            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cn;
    }

    /**
     * 指定された学校に属する全てのクラス番号を取得（class_numだけの一覧）
     */
    public List<String> filter(School school) {
        List<String> list = new ArrayList<>();

        try (Connection con = getConnection()) {
            String sql = "SELECT class_num FROM class_num WHERE school_cd = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, school.getCd());
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("class_num"));
            }

            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 新しいClassNumを保存（INSERT）
     */
    public boolean save(ClassNum classNum) {
        try (Connection con = getConnection()) {
            String sql = "INSERT INTO class_num (class_num, school_cd) VALUES (?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, classNum.getClass_num());
            st.setString(2, classNum.getSchool().getCd());

            int rows = st.executeUpdate();
            st.close();

            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 既存のclass_numを更新（UPDATE）
     */
    public boolean save(ClassNum classNum, String newClassNum) {
        try (Connection con = getConnection()) {
            String sql = "UPDATE class_num SET class_num = ? WHERE class_num = ? AND school_cd = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, newClassNum);
            st.setString(2, classNum.getClass_num());
            st.setString(3, classNum.getSchool().getCd());

            int rows = st.executeUpdate();
            st.close();

            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
