package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SchoolDao;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateAction extends Action {
	@Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String no = request.getParameter("no");


        StudentDao dao = new StudentDao();
        Student student = dao.get(no);


        if (student == null) {
            throw new Exception("該当する学生が見つかりませんでした");
        }

        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        String schoolCd = teacher.getSchool().getCd();
        SchoolDao schooldao = new SchoolDao();
        School school = schooldao.get(schoolCd);

        ClassNumDao classDao = new ClassNumDao();
        List<String> classList = classDao.filter(school);
        request.setAttribute("classList", classList);


        request.setAttribute("student", student);
        request.setAttribute("classList", classList);
        request.getRequestDispatcher("student_update.jsp").forward(request, response);
    }
}