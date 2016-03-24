<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% 
	String date = request.getParameter("date");
	request.setAttribute("date",date);
	String format_date = request.getParameter("format_date");
	request.setAttribute("format_date",format_date);
%> 
<c:if test="${format_date != null}">	

<s:textfield name="%{#request.date}" theme="simple" maxlength="20" size="25" value="%{#request.format_date}" onblur="requireDate(\"%{#request.date}\");" cssClass="Wdate" 

             onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,errDealMode:1})"/>
</c:if>
<c:if test="${format_date == null}">	

<s:textfield name="%{#request.date}" theme="simple" maxlength="20" size="25"  onblur="requireDate(\"%{#request.date}\");" cssClass="Wdate" 

             onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false,errDealMode:1})"/>
</c:if>

