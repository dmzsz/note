<%@ tag language="java" description="Displays the list of archives"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ attribute name="archivesByPage" required="true"
	type="java.util.ArrayList" description="List of archives"%>

<!-- Show articles per page -->
<c:choose>
	<c:when test="${not empty articlesByPage}">
		<c:forEach var="curArticle" items="${articlesByPage}">
			<c:url value="/article/${curArticle.id}" var="viewArticleUrl" />
			<div class="main">
				<ul class="cbp_tmtimeline">
					<li><time class="cbp_tmtime" datetime="${curArticle.created}">
							<span>
								<fmt:formatDate
									value="${curArticle.created}"
								 	pattern="yy/MM/dd" />
							</span> <span>
								<fmt:formatDate
									value="${curArticle.created}"
								 	pattern="HH:mm" />
							</span>
						</time>
						<div class="cbp_tmicon cbp_tmicon-phone"></div>
						<div class="cbp_tmlabel">
							<h2>
								<a href="${viewArticleUrl}"><c:out
										value="${curArticle.title}" /></a>
							</h2>
						</div></li>
				</ul>
			</div>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<h4>
			<spring:message code="home.noArticles" />
		</h4>
		<hr>
	</c:otherwise>
</c:choose>

