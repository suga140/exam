<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="dao.ClassNumDao, bean.Teacher, bean.School, java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="/common/base.jsp">
  <c:param name="title">学生情報登録</c:param>
  <c:param name="scripts"></c:param>

  <c:param name="content">
    <style>
      .section-title {
        background-color: #f0f0f0;
        padding: 10px 20px;
        font-size: 24px;
        font-weight: bold;
        margin-bottom: 20px;
      }

      .form-group {
        margin-bottom: 15px;
      }

      .form-label {
        font-weight: bold;
        display: block;
        margin-bottom: 5px;
      }

      .form-input {
        width: 100%;
        max-width: 700px;
        padding: 6px;
        box-sizing: border-box;
      }

      .btn-area {
        margin-top: 20px;
      }

      .link-button {
        margin-top: 10px;
        display: inline-block;
        color: #007bff;
        text-decoration: none;
        font-weight: bold;
      }

      .link-button:hover {
        text-decoration: underline;
      }

      .error-message {
        background-color: #fff8dc;
        border: 1px solid #f0ad4e;
        padding: 10px 15px;
        color: #a46922;
        font-weight: bold;
        border-radius: 6px;
        margin-top: 8px;
        margin-bottom: 12px;
      }
    </style>

    <div class="section-title">学生情報登録</div>

    <form action="StudentCreateExecute.action" method="post">
      <div class="form-group">
        <label class="form-label">入学年度</label>
        <select name="entYear" class="form-input">
          <option value="">--------</option>
          <%
            int currentYear = java.time.Year.now().getValue();
            for (int y = currentYear; y >= currentYear - 10; y--) {
          %>
          <option value="<%= y %>"><%= y %></option>
          <%
            }
          %>
        </select>

        <!-- 入学年度に関するエラーメッセージをここに表示 -->
        <c:if test="${not empty error}">
          <div class="error-message">${error}</div>
        </c:if>
      </div>

      <div class="form-group">
       <label>学生番号</label><br>
<input type="text" name="stuId" class = "form-input" value="${stuId}" required>
<c:if test="${not empty errorstuId}">
<div class="error-message">${errorstuId}</div>
</c:if>
      </div>

      <div class="form-group">
        <label>氏名</label><br>
			<input type="text" name="name" class = "form-input" value="${name}" required>
			<c:if test="${not empty nameError}">
			<div class="error-message">${nameError}</div>
</c:if>
      </div>

      <div class="form-group">
        <label class="form-label">クラス</label>
        <select name="class_Num" class="form-input">
          <c:forEach var="c" items="${classList}">
            <option value="${c}" <c:if test="${param.class_Num == c}">selected</c:if>>${c}</option>
          </c:forEach>
        </select>
      </div>

      <div class="btn-area">
        <input type="submit" value="登録して終了">
      </div>
      <div class="btn-area">
        <a href="StudentList.action" class="link-button">戻る</a>
      </div>
    </form>
  </c:param>
</c:import>