<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% 
	String dateStart = request.getParameter("dateStart");
	request.setAttribute("dateStart",dateStart);
	String dateEnd = request.getParameter("dateEnd");
	request.setAttribute("dateEnd",dateEnd);
%>
<s:textfield name="%{#request.dateStart}" theme="simple" maxlength="10" size="12" onblur="requireDate(\"%{#request.dateStart}\");" cssClass="Wdate"
             onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',isShowClear:false,errDealMode:1})"/>
至 
<s:textfield name="%{#request.dateEnd}" theme="simple" maxlength="10" size="12" onblur="requireDate(\"%{#request.dateEnd}\");" cssClass="Wdate" 
             onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',isShowClear:false,errDealMode:1})"/>

