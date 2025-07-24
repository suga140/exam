package scoremanager.main;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

@WebServlet("/StudentCreate.action")
public class StudentCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        if (teacher == null || teacher.getSchool() == null) {
            response.sendRedirect("Login.action");
            return;
        }

        // クラス一覧取得（所属校のみ）
        List<String> classList = new ClassNumDao().filter(teacher.getSchool());
        request.setAttribute("classList", classList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("student_create.jsp");
        dispatcher.forward(request, response);
    }
}