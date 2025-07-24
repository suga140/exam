<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <style>
            body {
                font-family: sans-serif;
                margin: 20px;
            }

            .section-title {
                background-color: #f0f0f0;
                padding: 8px 12px;
                font-size: 20px;
            }

            .confirm-message {
                margin-top: 20px;
                font-size: 16px;
            }

            .button-area {
                margin: 20px 0;
            }

            .link-button {
                color: #007BFF;
                text-decoration: underline;
                font-size: 16px;
                border: none;
                background: none;
                cursor: pointer;
            }

            .delete-button {
                background-color: #FF4C4C;
                color: white;
                font-size: 16px;
                padding: 8px 16px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }

            .delete-button:hover {
                background-color: #CC0000;
            }
        </style>

        <h2 class="section-title">科目情報削除</h2>

        <c:if test="${not empty errorMsg}">
            <div class="error-message">${errorMsg}</div>
        </c:if>

        <form action="SubjectDeleteExecute.action" method="post">
            <div class="confirm-message">
                「<strong>${subject.name}（${subject.cd}）</strong>」を削除してもよろしいですか？
            </div>

            <input type="hidden" name="subjectCode" value="${subject.cd}" />

            <div class="button-area">
                <input type="submit" value="削除" class="delete-button" />
            </div>
        </form>

        <!-- formの外で戻る -->
        <div class="button-area">
            <a href="SubjectList.action" class="link-button">戻る</a>
        </div>
    </c:param>
</c:import>