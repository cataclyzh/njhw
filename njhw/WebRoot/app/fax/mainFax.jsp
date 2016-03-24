<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   
    <title></title>
    <link type="text/css" rel="stylesheet" href="css/FaxMachine.css" />
    <script type="text/javascript">
        function btn_hover(dom, num, num1)
        {
            if(num1 == 0)
                dom.style.backgroundPosition = "-250px -" + num*100 + "px";
            else
                dom.style.backgroundPosition = "0px -" + num*100 + "px";
        }
        function iframeShow(dom)
        {
            document.getElementById("Faxframe").src = dom.className + ".jsp";
        }
    </script>
</head>
<body>
<div class="main">
    <div class="main_left">
    	<input type="hidden" id="session_id" value="${fax_session_id}"/>
    	<input type="hidden" id="userFax" value="${userFax}"/>
        <div class="writeFax" onmouseover="btn_hover(this, 0, 0)" onmouseout="btn_hover(this, 0, 1)" onclick="iframeShow(this)"></div>
        <div class="sendFax" onmouseover="btn_hover(this, 1, 0)" onmouseout="btn_hover(this, 1, 1)" onclick="iframeShow(this)"></div>
        <div class="receiveFax" onmouseover="btn_hover(this, 2, 0)" onmouseout="btn_hover(this, 2, 1)" onclick="iframeShow(this)"></div>
    </div>
    <div class="main_right">
        <iframe id="Faxframe"  frameborder="0" src="writeFax.jsp" width="100%" height="100%"></iframe>
    </div>
</div>
</body>
</html>