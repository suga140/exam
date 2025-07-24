<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="/common/base.jsp">
  <c:param name="title">学生登録完了</c:param>
  <c:param name="scripts"></c:param>

  <c:param name="content">
    <style>
      .message-area {
        padding: 30px;
        text-align: center;
        font-size: 20px;
        background-color: #f0fff0;
        border: 1px solid #c0eac0;
        border-radius: 10px;
        margin: 50px auto;
        width: 600px;
      }
      .button-area {
        text-align: center;
        margin-top: 30px;
      }
      .link-button {
        display: inline-block;
        margin: 0 15px;
        color: #007bff;
        text-decoration: none;
        font-weight: bold;
      }
      .link-button:hover {
        text-decoration: underline;
      }
    </style>

    <div class="message-area">
      学生情報の登録が完了しました！
    </div>
    <div class="button-area">
      <a href="StudentCreate.action" class="link-button">戻る</a>
      <a href="StudentList.action" class="link-button">学生一覧へ</a>
    </div>
  </c:param>
</c:import>