package scoremanager.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SchoolDao;
import dao.StudentDao;
import tool.Action;

public class StudentListAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Teacher teacher = (Teacher) request.getSession(false).getAttribute("user");
            if (teacher == null || teacher.getSchool() == null) {
                response.sendRedirect(request.getContextPath() + "/Login.action");
                return;
            }

            String schoolCd = teacher.getSchool().getCd();
            School school = new SchoolDao().get(schoolCd);
            if (school == null) {
                response.sendRedirect(request.getContextPath() + "/Login.action");
                return;
            }

            // パラメータ取得
            String entYearStr = request.getParameter("entYear");
            String classNum = request.getParameter("classNum");
            String isAttendStr = request.getParameter("isAttend");

            Integer entYear = (entYearStr != null && !entYearStr.isEmpty()) ? Integer.parseInt(entYearStr) : null;
            Boolean isAttend = (isAttendStr != null && isAttendStr.equals("1")) ? true : null;

            StudentDao studentDao = new StudentDao();
            List<Student> studentList = new ArrayList<>();

            // ✅ 年度一覧をセッションに保持（初回のみ抽出）
            List<Integer> yearList = (List<Integer>) request.getSession().getAttribute("yearList");
            if (yearList == null) {
                yearList = new ArrayList<>();
                List<Student> allStudents = new ArrayList<>();
                allStudents.addAll(studentDao.filter(school, true));
                allStudents.addAll(studentDao.filter(school, false));
                for (Student s : allStudents) {
                    if (!yearList.contains(s.getEntYear())) {
                        yearList.add(s.getEntYear());
                    }
                }
                Collections.sort(yearList, Collections.reverseOrder()); // 降順
                request.getSession().setAttribute("yearList", yearList);
            }

            // ✅ 条件分岐ごとの絞り込み処理
            if (isAttend != null) {
                if (entYear != null && classNum != null && !classNum.isEmpty()) {
                    studentList = studentDao.filter(school, entYear, classNum, true);
                } else if (entYear != null) {
                    studentList = studentDao.filter(school, entYear, true);
                } else if (classNum != null && !classNum.isEmpty()) {
                    // 年度未指定＋クラス指定 → 全年度ループ
                    for (Integer y : yearList) {
                        studentList.addAll(studentDao.filter(school, y, classNum, true));
                    }
                } else {
                    studentList = studentDao.filter(school, true);
                }
            } else {
                if (entYear != null && classNum != null && !classNum.isEmpty()) {
                    studentList.addAll(studentDao.filter(school, entYear, classNum, true));
                    studentList.addAll(studentDao.filter(school, entYear, classNum, false));
                } else if (entYear != null) {
                    studentList.addAll(studentDao.filter(school, entYear, true));
                    studentList.addAll(studentDao.filter(school, entYear, false));
                } else if (classNum != null && !classNum.isEmpty()) {
                    for (Integer y : yearList) {
                        studentList.addAll(studentDao.filter(school, y, classNum, true));
                        studentList.addAll(studentDao.filter(school, y, classNum, false));
                    }
                } else {
                    studentList.addAll(studentDao.filter(school, true));
                    studentList.addAll(studentDao.filter(school, false));
                }
            }

            List<String> classList = new ClassNumDao().filter(school);

            request.setAttribute("studentList", studentList);
            request.setAttribute("classList", classList);
            request.setAttribute("yearList", yearList);
            request.setAttribute("entYear", entYear);
            request.setAttribute("classNum", classNum);
            request.setAttribute("isAttend", isAttendStr != null ? isAttendStr : "");

            RequestDispatcher dispatcher = request.getRequestDispatcher("student_list.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            throw new ServletException("学生一覧表示時にエラーが発生しました", e);
        }
    }
}