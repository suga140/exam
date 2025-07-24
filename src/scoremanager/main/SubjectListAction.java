package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SchoolDao;
import dao.SubjectDao;
import tool.Action;

public class SubjectListAction extends Action {
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // セッションからログインユーザー（Teacher）を取得
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");

        // Teacher → Schoolコード → Schoolオブジェクトを取得
        String schoolCd = teacher.getSchool().getCd();
        SchoolDao schoolDao = new SchoolDao();
        School school = schoolDao.get(schoolCd);

        // 科目一覧を取得（filter）
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectlist = subjectDao.filter(school);

        // リクエストにセットして画面へ
        request.setAttribute("subjectlist", subjectlist);
        request.getRequestDispatcher("../main/subject_list.jsp").forward(request, response);
    }
}