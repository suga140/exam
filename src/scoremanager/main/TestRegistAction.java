package scoremanager.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistAction extends Action {

    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        try {
            HttpSession session = req.getSession();
            SubjectDao sdao = new SubjectDao();
            TestDao tdao = new TestDao();
            ClassNumDao cdao = new ClassNumDao();
            StudentDao stdao = new StudentDao();
            Teacher teacher = (Teacher) session.getAttribute("user");
            School school = teacher.getSchool();

            // 入学年度リスト生成
            List<Integer> yearList = (List<Integer>) req.getSession().getAttribute("yearList");
            if (yearList == null) {
                yearList = new ArrayList<>();
                List<Student> allStudents = new ArrayList<>();
                allStudents.addAll(stdao.filter(school, true));
                allStudents.addAll(stdao.filter(school, false));
                for (Student s : allStudents) {
                    if (!yearList.contains(s.getEntYear())) {
                        yearList.add(s.getEntYear());
                    }
                }
                Collections.sort(yearList, Collections.reverseOrder()); // 降順
                req.getSession().setAttribute("yearList", yearList);
            }

            // 学校に紐づく科目一覧取得
            List<Subject> sList = sdao.filter(school);
            req.setAttribute("sList", sList);

            // 学校に紐づくクラス一覧取得
            List<String> cList = cdao.filter(school);
            req.setAttribute("cList", cList);

            List<Integer> tNum = new ArrayList<>();
            for (int i = 1; i <= 2; i++) {
                tNum.add(i);
            }
            req.setAttribute("tNum", tNum);

            String method = req.getMethod();

            if ("POST".equalsIgnoreCase(method)) {
                // --- 更新処理 ---
                // パラメータで送られたテスト情報の点数を更新
                String[] studentNos = req.getParameterValues("studentNo");
                String[] points = req.getParameterValues("point");
                String entyStr = req.getParameter("f1");
                String classNum = req.getParameter("f2");
                String subCd = req.getParameter("f3");
                String numStr = req.getParameter("f4");

                if (studentNos != null && points != null &&
                    entyStr != null && !entyStr.isEmpty() &&
                    classNum != null && !classNum.isEmpty() &&
                    subCd != null && !subCd.isEmpty() &&
                    numStr != null && !numStr.isEmpty()) {

                    int enty = Integer.parseInt(entyStr);
                    int num = Integer.parseInt(numStr);
                    Subject subject = sdao.get(subCd, school);

                    List<Test> updateList = new ArrayList<>();
                    for (int i = 0; i < studentNos.length; i++) {
                        String studentNo = studentNos[i];
                        int point = 0;
                        try {
                            point = Integer.parseInt(points[i]);
                        } catch (NumberFormatException e) {
                            // 無効な値は0とみなすかスキップなどの対応
                            point = 0;
                        }

                        // Test情報組み立て
                        Test test = new Test();
                        test.setSchool(school);
                        test.setClassNum(classNum);
                        test.setNo(num);
                        test.setPoint(point);

                        // 生徒情報セット（最低限Noだけ）
                        bean.Student student = new bean.Student();
                        student.setNo(studentNo);
                        test.setStudent(student);

                        test.setSubject(subject);

                        updateList.add(test);
                    }
                    // DB更新
                    boolean updated = tdao.save(updateList);
                    if (!updated) {
                        req.setAttribute("error", "点数の更新に失敗しました。");
                    }
                }
            }

            // --- 絞り込み条件がある場合は検索して一覧取得 ---
            String entyStr = req.getParameter("f1");
            String classNum = req.getParameter("f2");
            String subCd = req.getParameter("f3");
            String numStr = req.getParameter("f4");

            List<Test> tList = null;
            if (entyStr != null && !entyStr.isEmpty() &&
                classNum != null && !classNum.isEmpty() &&
                subCd != null && !subCd.isEmpty() &&
                numStr != null && !numStr.isEmpty()) {

                int enty = Integer.parseInt(entyStr);
                int num = Integer.parseInt(numStr);
                Subject subject = sdao.get(subCd, school);
                tList = tdao.filter(enty, classNum, subject, num, school);
            }
            session.setAttribute("tList", tList);

            req.getRequestDispatcher("test_regist.jsp").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
