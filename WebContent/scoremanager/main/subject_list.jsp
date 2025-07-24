<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/common/base.jsp">
    <c:param name="title">
        得点管理システム
    </c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
    <style>
        body {
            font-family: sans-serif;
            margin: 20px;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .section-title {
            background-color: #f0f0f0;
            padding: 8px 12px;
            margin: 0;
            font-size: 20px;
            width: 100%;
            box-sizing: border-box;
        }

        .register-link {
            display: flex;
            justify-content: flex-end;
            margin: 8px 0 12px 0;
        }

        .link-button {
            color: #007BFF;
            text-decoration: underline;
            text-underline-offset: 2px;
            font-size: 16px;
            background: none;
            border: none;
            cursor: pointer;
            padding: 0;
        }

        table {
            width: 100%;
            margin-top: 10px;
            border-collapse: collapse;
        }

        th, td {
		    border-bottom: 1px solid #999;
		    padding: 10px 200px 10px 2px;
		    text-align: left;
		}

        .action-links {
            float: right;
        }

        .action-links a {
            margin-left: 15px;
            color: #007BFF;
            text-decoration: underline;
            text-underline-offset: 2px;
            font-size: 14px;
        }

        .action-links a:hover {
            opacity: 0.7;
        }
    </style>
    <div class="header">
        <h2 class="section-title">科目管理</h2>
    </div>

    <div class="register-link">
        <a href="subject_create.jsp" class="link-button">新規登録</a>
    </div>

    <table>
	  <tr>
	    <th>科目コード</th>
	    <th>科目名</th>
	  </tr>
	  <c:forEach var="subject" items="${subjectlist}">
	    <tr>
	      <td>${subject.cd}</td>
	      <td>
	        ${subject.name}
	        <span class="action-links">
	          <a href="SubjectUpdate.action?cd=${subject.cd}">変更</a>
	          <a href="SubjectDelete.action?cd=${subject.cd}">削除</a>
	        </span>
	      </td>
	    </tr>
	  </c:forEach>
	</table>
</c:param>
</c:import>