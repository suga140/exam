package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SchoolDao;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        // パラメータの取得
        String code = request.getParameter("subjectCode");
        String name = request.getParameter("subjectName");

        // セッションから teacher → school を取得
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        String schoolCd = teacher.getSchool().getCd();
        SchoolDao schoolDao = new SchoolDao();
        School school = schoolDao.get(schoolCd);

        // 入力チェック：コードが3文字ちょうど、名前は1〜20文字
        if (code == null || code.trim().isEmpty() || code.length() != 3 ||
            name == null || name.trim().isEmpty() || name.length() > 100) {

            Subject subject = new Subject();
            subject.setCd(code);
            subject.setName(name);
            subject.setSchool(school);

            request.setAttribute("errorMsg", "科目コードは3文字で入力してください");
            request.setAttribute("subject", subject);
            request.getRequestDispatcher("../main/subject_create.jsp").forward(request, response);
            return;
        }

        SubjectDao dao = new SubjectDao();

        // 重複チェック
        Subject existing = dao.get(code, school);
        if (existing != null) {
            Subject subject = new Subject();
            subject.setCd(code);
            subject.setName(name);
            subject.setSchool(school);

            request.setAttribute("errorMsg", "指定された科目コードは既に存在します。");
            request.setAttribute("subject", subject);
            request.getRequestDispatcher("../main/subject_create.jsp").forward(request, response);
            return;
        }

        // 登録処理
        Subject subject = new Subject();
        subject.setCd(code);
        subject.setName(name);
        subject.setSchool(school);

        boolean success = false;
        try {
            success = dao.save(subject);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 成否で分岐
        if (success) {
            request.getRequestDispatcher("../main/subject_create_done.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMsg", "登録処理に失敗しました。");
            request.setAttribute("subject", subject);
            request.getRequestDispatcher("../main/subject_create.jsp").forward(request, response);
        }
    }
}