package scoremanager.main;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

@WebServlet("/StudentCreateExecute.action")
public class StudentCreateExecuteAction extends Action {
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // ログイン教員から所属校取得
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        if (teacher == null || teacher.getSchool() == null) {
            response.sendRedirect("Login.action");
            return;
        }

        School school = teacher.getSchool();

        // クラス一覧取得（常に JSP に渡す）
        List<String> classList = new ClassNumDao().filter(school);
        request.setAttribute("classList", classList);

        // パラメータ取得
        String entYear = request.getParameter("entYear");
        String stuId = request.getParameter("stuId");
        String name = request.getParameter("name");
        String classNum = request.getParameter("class_Num");

        // 入学年度のチェック
        int error = 0;
        if (entYear == null || entYear.isEmpty()) {
            request.setAttribute("error", "入学年度を選択してください。");
            error ++;
        }

        StudentDao studentdao = new StudentDao();
        if (studentdao.get(stuId) != null){
        	request.setAttribute("errorstuId", "学生番号が重複しています。");
            error ++;
        }
        if (error > 0){
        	request.setAttribute("entYear", entYear);
        	request.setAttribute("stuId", stuId);
        	request.setAttribute("name", name);
        	request.setAttribute("classNum", classNum);
        	request.getRequestDispatcher("student_create.jsp").forward(request, response);
        }

        int entYearValue;
        try {
            entYearValue = Integer.parseInt(entYear);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "入学年度が正しくありません。");
            request.getRequestDispatcher("student_create.jsp").forward(request, response);
            return;
        }

        // 学生生成
        Student student = new Student();
        student.setNo(stuId);
        student.setName(name);
        student.setEntYear(entYearValue);
        student.setClassNum(classNum);
        student.setAttend(true);
        student.setSchool(school);

        boolean success = new StudentDao().save(student);

        if (success) {
            request.getRequestDispatcher("student_create_done.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "学生登録に失敗しました。");
            request.getRequestDispatcher("student_create.jsp").forward(request, response);
        }
    }
}