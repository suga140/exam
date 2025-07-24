package scoremanager.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Student;
import dao.Dao;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            request.setCharacterEncoding("UTF-8");

            String entYear = request.getParameter("entYear");
            String no = request.getParameter("no");
            String name = request.getParameter("name");
            String classNum = request.getParameter("classNum");
            boolean isAttend = request.getParameter("isAttend") != null;

            if (name == null || name.isEmpty() || name.length() > 30) {
                request.setAttribute("error", "氏名が不正です。");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            Student student = new Student();
            student.setEntYear(Integer.parseInt(entYear));
            student.setNo(no);
            student.setName(name);
            student.setClassNum(classNum);
            student.setAttend(isAttend);

            // Daoインスタンスを作成し、getConnection()を利用
            Dao dao = new Dao();

            try (Connection con = dao.getConnection();
                 PreparedStatement st = con.prepareStatement(
                    "UPDATE student SET name=?, class_num=?, is_attend=? WHERE ent_year=? AND no=?"
                 )) {

                st.setString(1, student.getName());
                st.setString(2, student.getClassNum());
                st.setBoolean(3, student.isAttend());
                st.setInt(4, student.getEntYear());
                st.setString(5, student.getNo());

                int updated = st.executeUpdate();
                if (updated == 0) {
                    request.setAttribute("error", "該当する学生が見つかりませんでした。");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                    return;
                }
            }

            request.setAttribute("student", student);
            request.getRequestDispatcher("student_update_done.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "学生情報の更新に失敗しました。");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
