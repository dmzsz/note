<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<c:set var="title">
	<spring:message code="about.pageTitle" /> | <spring:message
		code="project.title" />
</c:set>

<t:template title="${title}">
	<jsp:body>
	<h2>胡燕燕</h2>
	<p>
		2015年即将毕业于北京石油化工大学，现于xxx公司技术部就职。
	</p>
	</jsp:body>
</t:template>