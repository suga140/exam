package scoremanager.main;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectListAction extends Action {
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            Teacher teacher = (Teacher) request.getSession().getAttribute("user");
            String schoolCd = teacher.getSchool().getCd();

            SubjectDao dao = new SubjectDao();
            List<Subject> subjects = dao.filter(schoolCd);

            request.setAttribute("subjectList", subjects);
            RequestDispatcher dispatcher = request.getRequestDispatcher("subjectList.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}