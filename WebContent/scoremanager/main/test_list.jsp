<%-- 成績参照画面 JSP --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">成績参照</c:param>
	<c:param name="scripts">
		<script>
			// 追加スクリプトがあればここに書く
		</script>
	</c:param>
	<c:param name="content">
		<h2>成績参照</h2>

		<form action="TestList.action" method="get" class="p-3 bg-light rounded shadow-sm">
			<!-- 科目情報 -->
			<p>科目情報</p>
			<div class="mb-3 d-flex flex-wrap gap-4">
				<div>
					<label class="form-label">入学年度</label>
					<select name="f1" class="form-select form-select-sm">
						<option value="">--------</option>
						<c:forEach var="year" items="${yearList}">
							<option value="${year}">${year}</option>
						</c:forEach>
					</select>
				</div>

				<div>
					<label class="form-label">クラス</label>
					<select name="f2" class="form-select form-select-sm">
						<option value="">--------</option>
						<c:forEach var="cls" items="${classList}">
							<option value="${cls}">${cls}</option>
						</c:forEach>
					</select>
				</div>

				<div>
					<label class="form-label">科目</label>
					<select name="f3" class="form-select form-select-sm">
						<option value="">--------</option>
						<c:forEach var="subj" items="${subjectList}">
							<option value="${subj.cd}">${subj.name}</option>
						</c:forEach>
					</select>
				</div>

				<div class="align-self-end">
					<button type="submit" name="action" value="subjectSearch" class="btn btn-sm btn-secondary">検索</button>
				</div>
			</div>

			<!-- 学生情報 -->
			<p>学生情報</p>
			<div class="mb-3 d-flex flex-wrap gap-4">
				<div>
					<label class="form-label">学生番号</label>
					<input type="text" name="f4" class="form-control form-control-sm" value="${f4}" maxlength="10"
						placeholder="学生番号を入力してください" />
				</div>
				<div class="align-self-end">
					<button type="submit" name="action" value="studentSearch" class="btn btn-sm btn-secondary">検索</button>
				</div>
			</div>

			<p class="text-info">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</p>

			<!-- 隠しフィールド -->
			<input type="hidden" name="f" value="sj" />
			<input type="hidden" name="f" value="st" />
		</form>
				<c:if test="${f == 'sj'}">
		  <!-- 科目名 -->
		  <div class="fw-bold mb-2">
		    科目：<c:forEach var="subject" items="${subjectList}">
		      <c:if test="${subject.cd == f3}">
		        ${subject.name}
		      </c:if>
		    </c:forEach>
		  </div>

		  <!-- 成績一覧テーブル -->
		  <table class="table table-bordered table-striped">
		    <thead class="table-secondary">
		      <tr>
		        <th>入学年度</th>  <!-- ③ -->
		        <th>クラス</th>    <!-- ④ -->
		        <th>学生番号</th>  <!-- ⑤ -->
		        <th>氏名</th>      <!-- ⑥ -->
		        <th>1回</th>       <!-- ⑦ -->
		        <th>2回</th>       <!-- ⑧ -->
		      </tr>
		    </thead>
		    <tbody>
		      <c:forEach var="row" items="${subjectScoreList}">
		        <tr>
		          <td>${row.entYear}</td>                <!-- ⑨ -->
		          <td>${row.classNum}</td>               <!-- ⑩ -->
		          <td>${row.studentNo}</td>              <!-- ⑪ -->
		          <td>${row.studentName}</td>            <!-- ⑫ -->
		          <td>
		            <c:choose>
		              <c:when test="${row.points[1] != null}">${row.points[1]}</c:when>
		              <c:otherwise>-</c:otherwise>
		            </c:choose>
		          </td>                                  <!-- ⑬ -->
		          <td>
		            <c:choose>
		              <c:when test="${row.points[2] != null}">${row.points[2]}</c:when>
		              <c:otherwise>-</c:otherwise>
		            </c:choose>
		          </td>                                  <!-- ⑭ -->
		        </tr>
		      </c:forEach>
		    </tbody>
		  </table>
		</c:if>
	</c:param>
</c:import>

