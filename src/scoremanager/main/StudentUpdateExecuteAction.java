package scoremanager.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Student;
import dao.StudentDao;

@WebServlet("/scoremanager/main/StudentUpdateExecute.action")
public class StudentUpdateExecuteAction extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 文字化け対策
        request.setCharacterEncoding("UTF-8");

        // パラメータ取得
        String entYear = request.getParameter("entyear");
        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String classNum = request.getParameter("class_num");
        boolean isAttend = request.getParameter("is_attend") != null;

        // 入力値チェック（例：空文字や長さ制限など必要に応じて追加）
        if (name == null || name.isEmpty() || name.length() > 30) {
            request.setAttribute("error", "氏名が不正です。");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        // Studentインスタンス作成
        Student student = new Student();
        student.setEntYear(Integer.parseInt(entYear));
        student.setNo(no);
        student.setName(name);
        student.setClassNum(classNum);
        student.setAttend(isAttend);

        // DB更新
        try {
            StudentDao dao = new StudentDao();
            dao.update(student);  // updateメソッドは実装済みである前提
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "学生情報の更新に失敗しました。");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        // 完了画面へ
        request.setAttribute("student", student);
        request.getRequestDispatcher("student_update_done.jsp").forward(request, response);
    }
}
