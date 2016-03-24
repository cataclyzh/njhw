<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% 
	String monthStart = request.getParameter("monthStart");
	request.setAttribute("monthStart",monthStart);
	String monthEnd = request.getParameter("monthEnd");
	request.setAttribute("monthEnd",monthEnd);
%>

<s:textfield name="%{#request.monthStart}" theme="simple" maxlength="10" size="10" onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'})" cssClass="Wdate"/>
至 
<s:textfield name="%{#request.monthEnd}" theme="simple" maxlength="10" size="10" onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'})" cssClass="Wdate"/>