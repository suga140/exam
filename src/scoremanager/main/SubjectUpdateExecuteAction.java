package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SchoolDao;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        // パラメータの取得
        String code = request.getParameter("subjectCode");
        String name = request.getParameter("subjectName");

        // セッションから teacher → schoolCd → School を取得（安全設計）
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        String schoolCd = teacher.getSchool().getCd();
        SchoolDao schoolDao = new SchoolDao();
        School school = schoolDao.get(schoolCd);

        // 入力チェック（空・文字数超過）
        if (name == null || name.trim().isEmpty() || name.length() > 20) {
            Subject subject = new Subject();
            subject.setCd(code);
            subject.setName(name);
            subject.setSchool(school);

            request.setAttribute("errorMsg", "科目名は1～20文字で入力してください。");
            request.setAttribute("subject", subject);
            request.getRequestDispatcher("../main/subject_update.jsp").forward(request, response);
            return;
        }

        SubjectDao dao = new SubjectDao();

        // 既存データの取得と null チェック
        Subject existing = dao.get(code, school);
        if (existing == null) {
            Subject subject = new Subject();
            subject.setCd(code);
            subject.setName(name);
            subject.setSchool(school);

            request.setAttribute("errorMsg", "指定された科目は存在しません。");
            request.setAttribute("subject", subject);
            request.getRequestDispatcher("../main/subject_update.jsp").forward(request, response);
            return;
        }

        // 更新処理（delete → save）
        Subject subject = new Subject();
        subject.setCd(code);
        subject.setName(name);
        subject.setSchool(school);

        boolean success = false;
        try {
            if (dao.delete(existing)) {
                success = dao.save(subject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 成功・失敗の分岐処理
        if (success) {
            request.getRequestDispatcher("../main/subject_update_done.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMsg", "変更処理に失敗しました。");
            request.setAttribute("subject", subject);
            request.getRequestDispatcher("../main/subject_update.jsp").forward(request, response);
        }
    }
}