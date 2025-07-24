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

        .form-table {
            width: 100%;
            margin-top: 20px;
            border-collapse: collapse;
        }

        th, td {
            padding: 10px;
            text-align: left;
        }

        .form-label {
            width: 150px;
            font-weight: bold;
        }

        .error-message {
            color: red;
            margin-bottom: 10px;
        }

        .button-area {
            margin-top: 20px;
        }

        .link-button {
            color: #007BFF;
            text-decoration: underline;
            font-size: 16px;
            border: none;
            background: none;
            cursor: pointer;
        }
    </style>

    <h2 class="section-title">科目名変更</h2>

    <form action="SubjectUpdateExecute.action" method="post">
	  <table class="form-table">
	    <tr>
	      <td class="form-label">科目コード</td>
	      <td>
	        <span style="font-weight: bold;">${subject.cd}</span>
	        <input type="hidden" name="subjectCode" value="${subject.cd}" />
	      </td>
	    </tr>

		<c:if test="${not empty errorMsg}">
		  <tr>
		    <td colspan="2" style="
		      background-color: #fff3cd;
		      color: #856404;
		      text-align: left;
		      padding: 12px 16px;
		      border: 1px solid #ffeeba;
		      border-radius: 4px;
		    ">
		      ${errorMsg}
		    </td>
		  </tr>
		</c:if>

	    <tr>
	      <td class="form-label">科目名</td>
	      <td>
	        <input type="text" name="subjectName" value="${subject.name}" maxlength="20" required />
	      </td>
	    </tr>
	  </table>

	  <div class="button-area" style="display: flex; flex-direction: column; align-items: flex-start; gap: 10px;">
	    <input type="submit" value="変更" />
	    <a href="SubjectList.action" class="link-button">戻る</a>
	  </div>
	</form>
    </c:param>
</c:import>