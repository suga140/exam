<%-- 学生変更JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>


  <c:param name="content">
    <section class="me-4">
      <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>

      <form action="/kadai/scoremanager/main/StudentUpdate.action" method="post">
        <!-- 入学年度 -->
        <div class="row text-center px-4 fs-3 my-2">
          <label for="ent_year">入学年度</label>
          <input type="text" id="ent_year" name="ent_year" value="${ent_year}" readonly>
        </div>

        <!-- 学生番号 -->
        <div class="row text-center px-4 fs-3 my-2">
          <label for="no">学生番号</label>
          <input type="text" id="no" name="no" value="${no}" readonly>
        </div>

        <!-- 氏名 -->
        <div class="row text-center px-4 fs-3 my-2">
          <label for="name">氏名</label>
          <input type="text" id="name" name="name" value="${name}" maxlength="30" required>
        </div>

        <!-- クラス -->
        <div class="row text-center px-4 fs-3 my-2">
          <label for="class_num">クラス</label>
          <select name="class_num" id="class_num">
            <c:forEach var="c" items="${classList}">
              <option value="${c}" <c:if test="${c == class_num}">selected</c:if>>${c}</option>
            </c:forEach>
          </select>
        </div>

        <!-- 在学中 -->
        <div class="row text-center px-4 fs-3 my-2">
          <label for="is_attend">在学中</label>
          <input type="checkbox" id="is_attend" name="is_attend" value="true"
                 <c:if test="${is_attend}">checked</c:if>>
        </div>

        <!-- ボタン -->
        <div class="text-center my-4">
          <input type="submit" name="login" value="変更" class="btn btn-primary px-4">
          <a href="/kadai/scoremanager/student/list.action" class="btn btn-link">戻る</a>
        </div>
      </form>
    </section>
  </c:param>
</c:import>