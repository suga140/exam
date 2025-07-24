package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SchoolDao;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        // パラメータの取得
        String code = request.getParameter("subjectCode");

        // セッションから teacher → schoolCd → School を取得
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        String schoolCd = teacher.getSchool().getCd();
        SchoolDao schooldao = new SchoolDao();
        School school = schooldao.get(schoolCd);

        SubjectDao dao = new SubjectDao();

        // 指定された科目を取得
        Subject subject = dao.get(code, school);
        if (subject == null) {
            request.setAttribute("errorMsg", "指定された科目は存在しません。");
            request.getRequestDispatcher("../main/subject_delete.jsp").forward(request, response);
            return;
        }

        boolean success = false;
        try {
            success = dao.delete(subject);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 削除結果の分岐
        if (success) {
            request.getRequestDispatcher("../main/subject_delete_done.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMsg", "削除処理に失敗しました。");
            request.setAttribute("subject", subject);
            request.getRequestDispatcher("../main/subjectDelete.jsp").forward(request, response);
        }
    }
}