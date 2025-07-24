<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">成績参照</c:param>
  <c:param name="scripts">
    <script>
      // 必要に応じてスクリプト追加
    </script>
  </c:param>
  <c:param name="content">
    <h2>成績参照</h2>

    <div class="card p-4 mx-auto" style="max-width: 900px;">

      <!-- 科目情報検索フォーム -->
      <form action="TestList.action" method="post" class="mb-4">
        <div class="d-flex flex-wrap gap-4">
          <p>科目情報</p>
          <div>
            <label>入学年度</label>
            <select name="f1" class="form-select form-select-sm">
              <option value="">--------</option>
              <c:forEach var="year" items="${yearList}">
                <option value="${year}" <c:if test="${f1 == year}">selected</c:if>>${year}</option>
              </c:forEach>
            </select>
          </div>
          <div>
            <label>クラス</label>
            <select name="f2" class="form-select form-select-sm">
              <option value="">--------</option>
              <c:forEach var="cls" items="${classList}">
                <option value="${cls}" <c:if test="${f2 == cls}">selected</c:if>>${cls}</option>
              </c:forEach>
            </select>
          </div>
          <div>
            <label>科目</label>
            <select name="f3" class="form-select form-select-sm">
              <option value="">--------</option>
              <c:forEach var="subj" items="${subjectList}">
                <option value="${subj.cd}" <c:if test="${f3 == subj.cd}">selected</c:if>>${subj.name}</option>
              </c:forEach>
            </select>
          </div>
          <div class="align-self-end">
            <input type="hidden" name="f" value="sj" />
            <button type="submit" name="search_sj" class="btn btn-sm btn-secondary" value="1">検索</button>
          </div>
        </div>
		<c:if test="${not empty message_sj}">
    		<div class="d-flex flex-wrap gap-4 error">${message_sj}</div>
		</c:if>
      </form>

      <hr class="my-4" />

      <!-- 学生情報検索フォーム -->
      <form action="TestList.action" method="post" class="mb-4">
        <div class="d-flex flex-wrap gap-4">
          <p>学生情報</p>
          <div>
            <label>学生番号</label>
            <input type="text" name="f4" class="form-control form-control-sm"
                   value="${f4}" maxlength="10" placeholder="学生番号を入力してください" required />
          </div>
          <div class="align-self-end">
            <input type="hidden" name="f" value="st" />
            <button type="submit" class="btn btn-sm btn-secondary">検索</button>
          </div>
        </div>
      </form>
    </div>

    <hr class="my-4" />

    <!-- 初期表示メッセージ -->
    <c:if test="${empty f}">
      <div class="text-primary mt-3">
        <p>科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</p>
      </div>
    </c:if>

    <!-- 科目検索結果表示 -->
    <c:if test="${f == 'sj'}">
      <c:choose>
        <c:when test="${not empty message_sj_null}">
          <div class="fw-bold mt-2">${message_sj_null}</div>
        </c:when>
        <c:otherwise>
          <!-- 科目名表示 -->
          <div class="fw-bold mb-2">
            科目：
            <c:forEach var="subject" items="${subjectList}">
              <c:if test="${subject.cd == f3}">
                ${subject.name}
              </c:if>
            </c:forEach>
          </div>

          <!-- 成績テーブル -->
          <table class="table table-bordered table-striped">
            <thead class="table-secondary">
              <tr>
                <th>入学年度</th>
                <th>クラス</th>
                <th>学生番号</th>
                <th>氏名</th>
                <th>1回</th>
                <th>2回</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="row" items="${subjectScoreList}">
                <tr>
                  <td>${row.entYear}</td>
                  <td>${row.classNum}</td>
                  <td>${row.studentNo}</td>
                  <td>${row.studentName}</td>
                  <td><c:out value="${row.points['1']}" default="-" /></td>
                  <td><c:out value="${row.points['2']}" default="-" /></td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </c:otherwise>
      </c:choose>
    </c:if>

    <!-- 学生検索結果表示 -->
    <c:if test="${f == 'st'}">
      <c:choose>
        <c:when test="${studentList == null || studentList.isEmpty()}">
          <div class="fw-bold mt-2">${message_st}</div>
        </c:when>
        <c:otherwise>
          <!-- 学生名表示 -->
          <div class="fw-bold mb-2">
            学生：
            <c:if test="${studentList!= null || studentList.isNotEmpty()}">
            <c:forEach var="student" items="${studentList}">
              <c:if test="${student.no == f4}">
                ${student.name}（${student.no}）
              </c:if>
            </c:forEach>
            </c:if>
          </div>

          <!-- 成績テーブル -->
          <table class="table table-bordered table-striped">
            <thead class="table-secondary">
              <tr>
                <th>科目名</th>
                <th>科目コード</th>
                <th>回数</th>
                <th>点数</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="row" items="${studentScoreList}">
                <tr>
                  <td>${row.subjectName}</td>
                  <td>${row.subjectCd}</td>
                  <td>${row.no}</td>
                  <td>${row.point}</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </c:otherwise>
      </c:choose>
    </c:if>
  </c:param>
</c:import>
