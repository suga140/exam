package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SchoolDao;
import tool.Action;

public class TestRegistAction extends Action {
	@Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Teacher teacher = (Teacher) request.getSession().getAttribute("user");
    String schoolCd = teacher.getSchool().getCd();
    SchoolDao schooldao = new SchoolDao();
    School school = schooldao.get(schoolCd);

    ClassNumDao classDao = new ClassNumDao();
    List<String> classList = classDao.filter(school);
    request.setAttribute("classList", classList);


    request.setAttribute("classList", classList);
    request.getRequestDispatcher("test_regist.jsp").forward(request, response);
	}
}