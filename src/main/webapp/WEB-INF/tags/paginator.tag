<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="pages" type="java.lang.Integer" required="true"%>
<%@ attribute name="currentPage" type="java.lang.Integer" required="true"%>
<%@ attribute name="url" required="true"%>
<c:if test="${pages > 1}">
	<%--Form new url from current without 'page' parameter--%>
	<c:url value="${url }" var="generatedUrl">
		<c:forEach var="entry" items="${pageContext.request.parameterMap}">
			<c:if test="${entry.key ne 'page' }">
				<c:forEach items="${ entry.value}" var="value">
				    <c:param name="${entry.key }" value="${value }"></c:param>
				</c:forEach>
			</c:if>
		</c:forEach>
	</c:url>
	
	<nav>
		<ul class="pagination">
			<li>
				<a href='<c:url value="${generatedUrl}" >
						    <c:param name="page" value="${1 }"></c:param>
					</c:url>' aria-label="Previous"> 
					<span aria-hidden="true">&laquo;</span>
				</a>
			</li>
			<c:choose>
				<c:when test="${ pages > 5}">
					<c:if test="${ currentPage >= 3 and currentPage <= pages - 2}">
						<c:set var="begin" value="${currentPage - 2}"></c:set>
						<c:set var="end" value="${currentPage + 2}"></c:set>
					</c:if>
						
					<c:if test="${ currentPage < 3 }">
						<c:set var="begin" value="1"></c:set>
						<c:set var="end" value="5"></c:set>
					</c:if>
					<c:if test="${ currentPage > pages -2}">
						<c:set var="begin" value="${pages - 4 }"></c:set>
						<c:set var="end" value="${pages }"></c:set>
					</c:if>
				</c:when>
				<c:otherwise>
						<c:set var="begin" value="1"></c:set>
						<c:set var="end" value="${pages }"></c:set>
				</c:otherwise>
			</c:choose>
			<c:forEach begin="${begin }" step="1" end="${ end}" var="i">
				<li class="${(currentPage == i) ? 'active' : ''}">
					<a href='<c:url value="${generatedUrl}" >
						    <c:param name="page" value="${i }"></c:param>
					</c:url>'>${i}</a>
				</li>
			</c:forEach>
			<li>
				<a href='<c:url value="${generatedUrl}" >
						    <c:param name="page" value="${pages }"></c:param>
					</c:url>' aria-label="Next"> <span
						aria-hidden="true">&raquo;</span>
				</a>
			</li>
		</ul>
	</nav>
</c:if>