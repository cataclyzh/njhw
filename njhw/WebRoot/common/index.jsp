<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
<script language="JavaScript">
	 if($.browser.msie&&parseInt($.browser.version)<=6){
		 top.location.href='${ctx}/errorBrowser.act';
	 }else{
		 top.location.href='${ctx}/mainMenu.act';
	 }
</script>
