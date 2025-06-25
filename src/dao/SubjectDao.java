package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;

/**
 * SubjectDaoクラス：subjectテーブルに対するデータアクセス処理を担当
 */
public class SubjectDao extends Dao {

    /**
     * 指定された科目コードに該当する科目情報を1件取得する
     *
     * @param cd 科目コード
     * @param school 学校コード（現在は使用していない）
     * @return 科目が見つかればSubjectインスタンス、存在しなければnull
     * @throws Exception 取得中に例外が発生した場合
     */
    public Subject get(String cd, String school) throws Exception {
        Subject subject = new Subject();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            // SQL文を準備（科目コードで検索）
            statement = connection.prepareStatement("select * from subject where cd = ?");
            statement.setString(1, cd);

            // SQL実行
            ResultSet rSet = statement.executeQuery();

            if (rSet.next()) {
                // データが存在する場合、Subjectオブジェクトに値を設定
                subject.setCd(rSet.getString("cd"));
                subject.setName(rSet.getString("name"));
            } else {
                // 存在しない場合はnullを返す
                subject = null;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            // リソースを確実に解放
            if (statement != null) try { statement.close(); } catch (SQLException sqle) { throw sqle; }
            if (connection != null) try { connection.close(); } catch (SQLException sqle) { throw sqle; }
        }
        return subject;
    }

    /**
     * 指定された学校に所属するすべての科目を取得する
     *
     * @param school 学校コード
     * @return 科目リスト
     * @throws Exception 実行中に例外が発生した場合
     */
    public List<Subject> filter(String school) throws Exception {
        List<Subject> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        try {
            String sql = "SELECT cd, name FROM subject WHERE school_cd = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, school);

            rSet = statement.executeQuery();
            while (rSet.next()) {
                Subject subject = new Subject();
                subject.setCd(rSet.getString("cd"));
                subject.setName(rSet.getString("name"));
                subject.setSchool(school); // 学校コードをセット
                list.add(subject);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rSet != null) try { rSet.close(); } catch (SQLException ignored) {}
            if (statement != null) try { statement.close(); } catch (SQLException ignored) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignored) {}
        }

        return list;
    }

    /**
     * 新しい科目をデータベースに保存する
     *
     * @param subject 追加対象の科目インスタンス
     * @return 保存成功時はtrue
     * @throws Exception 実行中に例外が発生した場合
     */
    public boolean save(Subject subject) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        boolean result = false;

        try {
            String sql = "INSERT INTO subject (cd, name, school_cd) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, subject.getCd());
            statement.setString(2, subject.getName());
            statement.setString(3, subject.getSchool());

            result = statement.executeUpdate() > 0;
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignored) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignored) {}
        }

        return result;
    }

    /**
     * 指定された科目をデータベースから削除する
     *
     * @param subject 削除対象の科目インスタンス
     * @return 削除成功時はtrue
     * @throws Exception 実行中に例外が発生した場合
     */
    public boolean delete(Subject subject) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        boolean result = false;

        try {
            String sql = "DELETE FROM subject WHERE cd = ? AND school_cd = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, subject.getCd());
            statement.setString(2, subject.getSchool());

            result = statement.executeUpdate() > 0;
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignored) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignored) {}
        }

        return result;
    }
}