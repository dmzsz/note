<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>


<c:set var="title">${article.title} | <spring:message code="project.title" /></c:set>

<t:template title="${title}">
	<jsp:body>
		<c:url value="/user" var="profileUrl" />
		<c:url value="/signin" var="signinUrl" />
		<c:url value="/signup" var="signupUrl" />
		<c:url value="/article/${article.id}/edit" var="editArticleUrl" />
		<c:url value="${contextPath}/article/${article.id}/delete" var="deleteArticleUrl" />
		<c:url value="/article/${article.id}/addcomment" var="addCommentUrl" />

		<!-- Logged user is admin -->
		<c:set var="isAdmin" value="false" />
		<sec:authorize access="hasRole('ROLE_ADMIN')">
		    <c:set var="isAdmin" value="true" />
		</sec:authorize>
		
		<!-- Logged user is author of this article -->
		<c:set var="isThisArticleAuthor" value="false" />
		<sec:authorize access="hasRole('ROLE_AUTHOR')">
			<c:if test="${loggedUserId == article.author.id}">
				<c:set var="isThisArticleAuthor" value="true" />
			</c:if>
		</sec:authorize>
		
		<!-- Article title -->
		<h2><c:out value="${article.title}" /></h2>
		
		<!-- Show Edit/Delete links -->
		<sec:authorize access="hasRole('ROLE_AUTHOR')">
			<c:if test="${isThisArticleAuthor || isAdmin}">
				<a href="${editArticleUrl}" class="btn btn-info btn-xs" type="button">
					<i class="glyphicon glyphicon-edit"></i>
					<spring:message code="article.editArticleUrl" />
				</a>
				<a href="${deleteArticleUrl}" class="btn btn-danger btn-xs" type="button"
						id="delete-article">
					<i class="glyphicon glyphicon-exclamation-sign"></i>
					<spring:message code="article.deleteArticleUrl" />
				</a>
				<br>
			</c:if>
		</sec:authorize>
		<br>
		
		<!-- Article category and tags -->
		<t:category-tags article="${article}" />
		
		<!-- View count, last modified -->
		<small class="text-muted">
			<i class="glyphicon glyphicon-check"></i>
			<spring:message code="article.views" />:
			<c:out value="${article.viewCount}" />
			<c:if test="${article.lastModified != null}">
				&nbsp;|&nbsp;
				<spring:message code="article.lastModified" />:
				<fmt:formatDate value="${article.lastModified}" type="both" />
				<br>
			</c:if>
		</small>
		
		<!-- Article content -->
		<div class="article-content">
			<c:out value="${article.content}" escapeXml="false" />
		</div>
		<hr>
		
		<!-- Comments-message -->
		<h3 id="comments" class="comment-message">
			<c:choose>
				<c:when test="${article.commentCount > 0}">
					<spring:message code="article-view.commentsToArticle" />
					<span id="comment-count">${article.commentCount}</span>
				</c:when>
				<c:otherwise>
					<spring:message code="article-view.noComments" />
				</c:otherwise>
			</c:choose>
		</h3>
		
		<!-- Message for not registered users -->
		<sec:authorize access="isAnonymous()">
			<c:set var="signin">
				<a href="${signinUrl}">
					<spring:message code="article-view.loginComments.signin" />
				</a>
			</c:set>
			<c:set var="signup">
				<a href="${signupUrl}">
					<spring:message code="article-view.loginComments.signup" />
				</a>
			</c:set>
			<spring:message code="article-view.loginComments" arguments="${signin},${signup}" />
		</sec:authorize>
		
		<!-- Add comment form -->
		<sec:authorize access="hasRole('ROLE_USER')">
			<form data-toggle="validator" onsubmit="return false">
				<h4><span id="comment-validation" class="label label-danger"></span></h4>
				<textarea id="comment-content" class="form-control" name="content"
					maxlength="500" rows="2" required
					placeholder="<spring:message code="article-view.leaveComment" />"></textarea>

				<button id="commentSubmit" class="btn btn-default" type="submit">
					<spring:message code="article-view.addCommentButton" />
				</button>
		  		<button id="reset" class="btn btn-default" type="reset">
					<spring:message code="article-view.resetCommentButton" />
				</button>
			</form>
		</sec:authorize>
		
		<!-- List of comments -->
		<div id="comment-list">
		<c:forEach var="comment" items="${article.comments}">
			<div class="comment" id="comments_${comment.id}">
				<a href="${profileUrl}/${comment.author.id}">
					<strong>${comment.author.login}</strong>
				</a>
				&nbsp;-&nbsp;
				<small>
					<i class="glyphicon glyphicon-time"></i>
					<fmt:formatDate value="${comment.created}" type="both" />
				</small>
				<c:if test="${loggedUserId == article.author.id|| isAdmin}">
					<small>
						<i class="glyphicon glyphicon-remove pull-right" id="comment_${comment.id}"></i>
					</small>
				</c:if>
				<br>
				<c:out value="${comment.content}" escapeXml="false" />
			</div>
		</c:forEach>
		</div>
		
		<script type="text/javascript" src="<c:url value="/resources/js/app/inputs.js"/>"></script>
		<script>
			function deleteComment() {
				var message = "${pageContext.response.locale.language}" === "zh"
					? "你确定删除该评论?"
					: "Are you sure?";
				if (confirm(message)){
					var id=$(this).attr('id').split("comment_")[1];
					$.post("${pageContext.request.contextPath}/article/comment/"+id+"/deletecomment",
							{"commentId": id}, function(response) {
							if (response.status === "ok") {
								$("#comment-content").val("");
								$("#comments_"+response.commentId).slideUp(300, function() {
					                $("#comments_"+response.commentId).remove();
					            });
								var count = $("#comment-count").text();
								if (count === "1") {
									$("h3#comments").html("<spring:message code="article-view.noComments" />");
								} else {
									$("#comment-count").text(--count);
								}
							}else{
								$("#comment-validation").text(response.error).show().delay(1500).fadeOut(1500);
							}
					});
					return false;
				}
			};
			// add comment ajax-request
			$("#commentSubmit").click(function() {
				var content = $("#comment-content").val();
				$.post('${pageContext.request.contextPath}/article/${article.id}/addcomment',
						{"articleId": "${article.id}", "content": content}, 
						function(response) {
							if (response.status === "ok") {
								$("#comment-content").val("");
						
								$("#comment-list").prepend(
									"<div class='comment' id='comments_"+response.commentId
									+"'><a href='${profileUrl}/${loggedUserId}'>"
									+ "<strong>${pageContext.request.userPrincipal.name}</strong></a>"
									+ "&nbsp;&nbsp;-&nbsp;&nbsp;<small><i class='glyphicon glyphicon-time'></i>"
									+ "&nbsp;<fmt:formatDate value='<%=new java.util.Date()%>' type='both'/></small>"
									+ "<c:if test='${loggedUserId == article.author.id || isAdmin}'>"
									+ "<small><i class='glyphicon glyphicon-remove pull-right' id='comment_"+response.commentId+"'></i></small>"
									+"</c:if>"
									+ "</br>"+content+"</div>"
								).children(':first').hide().fadeIn(1500)
								.find(".glyphicon-remove").click(deleteComment);//关键！！
								var count = $("#comment-count").text();
								if (count === "") {
									$("h3#comments").html("<spring:message code='article-view.commentsToArticle' />"
										+ "<span id='comment-count'>1</span>");
								} else {
									$("#comment-count").text(++count);
								}
							} else {
								$("#comment-validation").text(response.error).show().delay(1500).fadeOut(1500);
							}
						}
				);
				return false;
			});
			
			$(".comment .glyphicon-remove").click(deleteComment);
			$("#delete-article").click(function() {
				var message = "${pageContext.response.locale.language}" === "zh"
					? "你确定删除?"
					: "Are you sure?";
				if (confirm(message)){
					location.href="${deleteArticleUrl}";
				};
				return false;
			});
		</script>
	</jsp:body>
</t:template>