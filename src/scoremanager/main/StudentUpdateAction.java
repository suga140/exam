package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Student;
import dao.StudentDao;

public class StudentUpdateAction {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

//        String no = request.getParameter("no");
        String no = "2225001";

        StudentDao dao = new StudentDao();
        Student student = dao.get(no);
        System.out.println("取得した学生: " + student);

        if (student == null) {
            throw new Exception("該当する学生が見つかりませんでした");
        }

        request.setAttribute("student", student);
        return "scoremanager/main/student_update.jsp";
    }
}
