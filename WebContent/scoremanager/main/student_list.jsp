<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/common/base.jsp">
  <c:param name="title">得点管理システム - 学生管理</c:param>
  <c:param name="scripts"></c:param>

  <c:param name="content">
    <style>
      h1 {
        font-size: 24px;
        margin-bottom: 10px;
        display: inline-block;
      }
		.section-title {

            background-color: #f0f0f0;

            padding: 8px 12px;

            margin: 0;

            font-size: 20px;

            width: 100%;

            box-sizing: border-box;

        }
      .top-action {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 20px;
      }

      .new-link {
        font-size: 14px;
        color: #007BFF;
        text-decoration: underline;
        margin-left: auto;
      }

      .form-container {
        margin-bottom: 20px;
        padding: 12px 16px;
        background-color: #f9f9f9;
        border: 1px solid #ccc;
        border-radius: 4px;
      }

      .form-row {
        display: grid;
        grid-template-columns: repeat(4, 1fr);
        gap: 20px;
        align-items: end;
      }

      .form-group {
        display: flex;
        flex-direction: column;
        font-size: 14px;
      }

      .form-group label {
        margin-bottom: 4px;
        font-weight: bold;
        text-align: center;
      }

      select, input[type="checkbox"] {
        padding: 6px;
        font-size: 14px;
      }

      .button-area {
        display: flex;
        flex-direction: column;
        align-items: flex-end;
        justify-content: flex-end;
      }

      input[type="submit"] {
        padding: 6px 14px;
        font-size: 14px;
        background-color: #ccc;
        color: #333;
        border: none;
        border-radius: 4px;
        cursor: pointer;
      }

      input[type="submit"]:hover {
        background-color: #999;
      }

      table {
        width: 100%;
        border-collapse: collapse;
        table-layout: fixed;
      }

      th, td {
        padding: 8px;
        text-align: center;
        word-wrap: break-word;
        border-bottom: 1px solid #ccc;
      }

      th {
        font-weight: bold;
        background-color: transparent;
        border-bottom: 1px solid #999;
      }

      .no-results {
        text-align: center;
        color: red;
        font-style: italic;
      }
    </style>
	<div class="header">
		<h2 class="section-title">学生管理</h2>
	</div>
	<div class = "top-action" >
    	<a href="StudentCreate.action" class="new-link">新規登録</a>
	</div>
    <!-- 絞り込みフォーム -->
    <form method="get" action="StudentList.action" class="form-container">
      <div class="form-row">
        <div class="form-group">
          <label for="entYear">入学年度</label>
          <select name="entYear" id="entYear">
            <option value="">--全て--</option>
            <c:forEach var="y" items="${yearList}">
              <option value="${y}" <c:if test="${param.entYear == y}">selected</c:if>>${y}</option>
            </c:forEach>
          </select>
        </div>

        <div class="form-group">
          <label for="classNum">クラス</label>
          <select name="classNum" id="classNum">
            <option value="">--全て--</option>
            <c:forEach var="c" items="${classList}">
              <option value="${c}" <c:if test="${param.classNum == c}">selected</c:if>>${c}</option>
            </c:forEach>
          </select>
        </div>

        <div class="form-group">
          <label for="isAttend">在学中</label>
          <input type="checkbox" name="isAttend" id="isAttend" value="1"
                 <c:if test="${param.isAttend == '1'}">checked</c:if> />
        </div>

        <div class="form-group button-area">
          <input type="submit" value="絞り込み">
        </div>
      </div>
    </form>

    <!-- 学生一覧テーブル -->
    <table>
      <thead>
        <tr>
          <th>入学年度</th>
          <th>学生番号</th>
          <th>名前</th>
          <th>クラス</th>
          <th>在学中</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="s" items="${studentList}">
          <tr>
            <td>${s.entYear}</td>
            <td>${s.no}</td>
            <td>${s.name}</td>
            <td>${s.classNum}</td>
            <td>
              <c:choose>
                <c:when test="${s.attend}">○</c:when>
                <c:otherwise>✕</c:otherwise>
              </c:choose>
            </td>
            <td><a href="StudentUpdate.action?no=${s.no}">変更</a></td>
          </tr>
        </c:forEach>

        <c:if test="${fn:length(studentList) == 0}">
          <tr>
            <td colspan="6" class="no-results">該当する学生はいません</td>
          </tr>
        </c:if>
      </tbody>
    </table>
  </c:param>
</c:import>