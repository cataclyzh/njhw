<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% 
	String month = request.getParameter("month");
	request.setAttribute("month",month);
	String format_month = request.getParameter("format_month");
	request.setAttribute("format_month",format_month);
%>
<c:if test="${format_month != null}">
<s:textfield name="%{#request.month}" value="%{#request.format_month}" theme="simple" maxlength="10" size="10" 
    onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})" cssClass="Wdate"/>
</c:if>
<c:if test="${format_month == null}">
<s:textfield name="%{#request.month}" theme="simple" maxlength="10" size="10" 
    onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})" cssClass="Wdate"/>
</c:if>