<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">得点管理システム</c:param>
  <c:param name="scripts"></c:param>

  <c:param name="content">
    <section class="container my-4">
      <h2 class="mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>

      <form action="StudentUpdateExecute.action" method="post" class="mx-auto">
        <!-- 入学年度 -->
        <div class="mb-3">
          <label for="entYear" class="form-label">入学年度</label>
          <input type="text" id="entYear" name="entYear" value="${student.entYear}" readonly class="form-control-plaintext">
        </div>

        <!-- 学生番号 -->
        <div class="mb-3">
          <label for="no" class="form-label">学生番号</label>
          <input type="text" id="no" name="no" value="${student.no}" readonly class="form-control-plaintext">
        </div>

        <!-- 氏名 -->
        <div class="mb-3">
          <label for="name" class="form-label">氏名</label>
          <input type="text" id="name" name="name" value="${student.name}" maxlength="30" required class="form-control">
        </div>

        <!-- クラス -->
        <div class="mb-3">
          <label for="classNum" class="form-label">クラス</label>
          <select name="classNum" id="classNum" class="form-select">
            <c:forEach var="c" items="${classList}">
              <option value="${c}" <c:if test="${c eq student.classNum}">selected</c:if>>${c}</option>
            </c:forEach>
          </select>
        </div>

        <!-- 在学中 -->
        <div class="form-check mb-4">
          <input type="checkbox" id="isAttend" name="isAttend" value="true" class="form-check-input"
            <c:if test="${student.attend}">checked</c:if>>
          <label for="isAttend" class="form-check-label">在学中</label>
        </div>

        <!-- ボタン -->
        <div class="text-center">
          <input type="submit" name="login" value="変更" class="btn btn-primary px-4 me-3">
          <a href="StudentList.action" class="btn btn-link">戻る</a>
        </div>
      </form>
    </section>
  </c:param>
</c:import>
