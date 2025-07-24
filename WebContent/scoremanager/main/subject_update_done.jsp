<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/common/base.jsp">
  <c:param name="title">得点管理システム</c:param>
  <c:param name="scripts"></c:param>

  <c:param name="content">
    <style>
      .completion-message {
        text-align: center;
        background-color: #eaffea;
        color: #155724;
        font-size: 18px;
        font-weight: bold;
        padding: 14px 0;
        border: 1px solid #c3e6cb;
        border-radius: 4px;
        width: 100%;
        margin: 30px 0;
        box-sizing: border-box;
      }
    </style>

    <h2 class="section-title">科目情報変更</h2>

    <p class="completion-message">変更が完了しました</p>

    <a href="SubjectList.action" class="link-button">科目一覧</a>
  </c:param>
</c:import>