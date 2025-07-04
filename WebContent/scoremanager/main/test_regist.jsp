<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- ベーステンプレートを利用 --%>
<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">

		<div class="content">

		    <h2>成績管理</h2>

		    <form action="TestRegist.action" method="get" class="search-form">

		        <div>
		            <label>入学年度</label>
		            <label>クラス</label>
		            <label>科目</label>
		            <label>回数</label>
		        </div>

		        <div>
		            <select name="f1">
		                <option value="">--------</option>
		                <c:forEach var="year" items="${yearList}">
		                    <option value="${year}">${year}</option>
		                </c:forEach>
		            </select>

		            <select name="f2">
		                <option value="">--------</option>
		                <c:forEach var="class" items="${classList}">
		                    <option value="${class.classNum}">${class.className}</option>
		                </c:forEach>
		            </select>

		            <select name="f3">
		                <option value="">--------</option>
		                <c:forEach var="subject" items="${subjectList}">
		                    <option value="${subject.cd}">${subject.name}</option>
		                </c:forEach>
		            </select>

		            <select name="f4">
		                <option value="">--------</option>
		                <c:forEach var="count" items="${countList}">
		                    <option value="${count}">${count}</option>
		                </c:forEach>
		            </select>

		            <button type="submit">検索</button>
		        </div>
		    </form>
		</div>
	</c:param>
</c:import>
