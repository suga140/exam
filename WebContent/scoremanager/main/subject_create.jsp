<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="/common/base.jsp">
  <c:param name="title">科目情報登録</c:param>
  <c:param name="scripts"></c:param>

  <c:param name="content">
    <style>
      .section-title {
        background-color: #f1f1f1;
        padding: 10px 15px;
        font-size: 24px;
        font-weight: bold;
        border-radius: 4px;
      }

      .form-group {
        margin-top: 20px;
      }

      .form-group label {
        font-weight: bold;
        display: block;
        margin-bottom: 5px;
      }

      .form-group input[type="text"] {
        width: 100%;
        padding: 8px;
        font-size: 16px;
      }

      .error-message {
        color: #d78c00;
        background-color: #fff9e6;
        border: 1px solid #f5c36a;
        padding: 6px 12px;
        margin-top: 6px;
        font-size: 14px;
        border-radius: 4px;
      }

      .button-area {
        margin-top: 30px;
        text-align: left;
      }

      .submit-button {
        background-color: #007BFF;
        color: white;
        padding: 8px 16px;
        font-size: 16px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
      }

      .submit-button:hover {
        background-color: #0056b3;
      }

      .button-area a {
        margin-left: 20px;
        color: #007BFF;
        text-decoration: underline;
        font-size: 16px;
      }
    </style>

    <div class="section-title">科目情報登録</div>

    <form action="SubjectCreateExecute.action" method="post">
      <div class="form-group">
        <label for="subjectCode">科目コード</label>
        <input type="text" name="subjectCode" id="subjectCode"
               value="${subject.cd}" maxlength="10"
               placeholder="科目コードを入力してください" required />

        <c:if test="${not empty errorMsg}">
          <div class="error-message">${errorMsg}</div>
        </c:if>
      </div>

      <div class="form-group">
        <label for="subjectName">科目名</label>
        <input type="text" name="subjectName" id="subjectName"
               value="${subject.name}" maxlength="20"
               placeholder="科目名を入力してください" required />
      </div>

      <div class="button-area">
        <input type="submit" value="登録" class="submit-button" />
      </div>

      <div class="button-area">
        <a href="SubjectList.action">戻る</a>
      </div>
    </form>
  </c:param>
</c:import>