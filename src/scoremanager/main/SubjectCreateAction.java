package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SchoolDao;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateAction extends Action {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String code = request.getParameter("cd");

        // ✔ teacher → schoolCd → school を復元する

        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        String schoolCd = teacher.getSchool().getCd();
        SchoolDao schooldao = new SchoolDao();
        School school = schooldao.get(schoolCd);

        SubjectDao dao = new SubjectDao();
        Subject subject = dao.get(code, school);

        request.setAttribute("subject", subject);
        request.getRequestDispatcher("../main/subject_create.jsp").forward(request, response);
    }
}
