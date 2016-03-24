<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/meta.jsp" %>
    <title>菜单详情</title>
</head>
  
<body style="background: url('${ctx}/app/integrateservice/images/menus_bg.png') repeat;">
	<div id="main">
		<table>					
			<tr> <td colspan ="3" style="font-size: 25px; font:bolder; color: blue" > ${fs.fdName}&nbsp;&nbsp;</td> </tr>
			<tr>
		       <td><img  width="180px" height="120px" src="${src1}"/></td>
		       <td><img  width="180px" height="120px" src="${src2}"/></td>
		       <td><img  width="180px" height="120px" src="${src3}"/></td>
		    </tr>
			<tr>
				<td colspan="3">
					<div style="word-wrap:break-word; overflow-y: auto; overflow-x:hidden; height: 100px; width:550px; margin:0px;padding:0px;" ><pre style="margin:0px;padding:0px;">${fn:trim(fs.fdDesc)}</pre></div>	
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
