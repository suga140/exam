package scoremanager.main;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SchoolDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 1. リクエストパラメータ取得
        String f1 = request.getParameter("f1"); // 入学年度
        String f2 = request.getParameter("f2"); // クラス
        String f3 = request.getParameter("f3"); // 科目コード
        String f4 = request.getParameter("f4"); // 学生番号

        // 2. 学校情報の取得（セッション内ユーザーから）
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        if (teacher == null || teacher.getSchool() == null) {
            request.setAttribute("error", "ログイン情報が取得できませんでした。再ログインしてください。");
            request.getRequestDispatcher("/view/error.jsp").forward(request, response);
            return;
        }
        String schoolCd = teacher.getSchool().getCd();

        SchoolDao schoolDao = new SchoolDao();
        School school = schoolDao.get(schoolCd);

        // 3. 入学年度リスト生成（現在年から過去10年）
        int currentYear = Year.now().getValue();
        List<Integer> yearList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            yearList.add(currentYear - i);
        }
        request.setAttribute("yearList", yearList);

        // 4. 検索処理の分岐
        if (f4 != null && !f4.trim().isEmpty()) {
            // ◆ 学生番号検索
            Student student = new Student();
            student.setNo(f4.trim());

            TestListStudentDao studentDao = new TestListStudentDao();
            List<TestListStudent> result = studentDao.filter(student);

            request.setAttribute("studentScoreList", result);
            request.setAttribute("f", "st");

        } else if (f1 != null && !f1.isEmpty() &&
                   f2 != null && !f2.isEmpty() &&
                   f3 != null && !f3.isEmpty()) {

            try {
                // ◆ 科目情報検索
                int entYear = Integer.parseInt(f1.trim());
                String classNum = f2.trim();

                Subject subject = new Subject();
                subject.setCd(f3.trim());

                TestListSubjectDao subjectDao = new TestListSubjectDao();
                List<TestListSubject> result = subjectDao.filter(entYear, classNum, subject, school);

                request.setAttribute("subjectScoreList", result);
                request.setAttribute("f", "sj");

            } catch (NumberFormatException e) {
                request.setAttribute("error", "入学年度の形式が不正です。");
            }

        } else {
            // 入力なし：検索しない（画面だけ表示）
        }

        // 5. プルダウン（クラス、科目）設定
        ClassNumDao cnDao = new ClassNumDao();
        SubjectDao subjDao = new SubjectDao();

        request.setAttribute("classList", cnDao.filter(school));
        request.setAttribute("subjectList", subjDao.filter(schoolCd));

        // 6. 入力値の再設定
        request.setAttribute("f1", f1);
        request.setAttribute("f2", f2);
        request.setAttribute("f3", f3);
        request.setAttribute("f4", f4);

        // 7. 表示へ遷移
        request.getRequestDispatcher("test_list.jsp").forward(request, response);
    }
}
