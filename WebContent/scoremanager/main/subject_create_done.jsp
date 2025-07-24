<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/common/base.jsp">
  <c:param name="title">得点管理システム</c:param>
  <c:param name="scripts"></c:param>

  <c:param name="content">
    <style>
      .section-title {
        background-color: #f0f0f0;
        padding: 8px 12px;
        font-size: 20px;
        font-weight: bold;
      }

      .completion-message {
        font-size: 16px;
        margin: 20px 0 30px 0;
        color: #333;
        background-color: #eaffea; /* 薄緑背景 */
        padding: 16px 24px;
        width: 100%;
        box-sizing: border-box;
        border-radius: 6px;
        text-align: center;
      }

      .link-button {
        font-size: 16px;
        color: #007BFF;
        text-decoration: underline;
        background: none;
        border: none;
        cursor: pointer;
        padding: 0;
        margin-right: 20px;
        letter-spacing: 2px; /* 文字の間隔を広げる */
      }

      .button-area {
        margin: 20px 0;
      }
    </style>

    <h2 class="section-title">科目情報変更</h2>
    <p class="completion-message">新規作成が完了しました</p>
    <div class="button-area">
      <a href="SubjectCreate.action" class="link-button">戻る</a>
      <a href="SubjectList.action" class="link-button">科目一覧</a>
    </div>
  </c:param>
</c:import>