<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ attribute name="availibleLanguages" type="java.util.Collection"%>
<%@ include file="/WEB-INF/fragment/l10n.jspf"%>
<li class="dropdown navbar-right menu-li">
  <a href="#" class="dropdown-toggle menu-a" data-toggle="dropdown" role="button" aria-expanded="false"><fmt:message key="l10n.language"/> <span class="caret"></span></a>
  <ul class="dropdown-menu navbar-nav" role="menu">
  	<c:forEach items="${ availibleLanguages}" var="language">
    	<li><a href="" class="black " onclick="changeLocale('${language}');"><fmt:message key="l10n.${language}"/></a></li>
  	</c:forEach>
  </ul>
</li>