<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>JCosmo</title>
	<%@ include file="/common/include/header-meta.jsp" %>
	<link href="${ctx}/styles/layout.css" rel="stylesheet" type="text/css"/>
	<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
	<script src="${ctx}/common/pages/main/layout.js" type="text/javascript"></script>
	<%--<script type="text/javascript" src="${ctx}/common/pages/main/jquery.blockUI.js"></script> --%>
	<script type="text/javascript">
	if (top != self) top.location.href = location.href;
	$(function() {
		using('messager');	
		<c:if test="${msgMb!=null}">
		using('messager', function () {
			$.messager.show({
				title:'消息提示',
				msg:"${msgMb}",
				timeout:5000,
				width:300,
				height:150,
				showType:'show'
			});
		});	
		</c:if>
		$('#sysname').css("font-size","26px");
		$("#accordion-div").show();$("#center-div").show();
	});

	function initTabs(){
		<c:if test="${_company=='tescoadmin' or _company=='hisapadmin' or _company=='lotusadmin'}">
		$("#initFrame").attr("src", "${ctx}/${_company_min}/home/init.act"); 
		</c:if>
    }
	</script>
</head>
<body class="easyui-layout" style="width:100%; overflow:hidden " onload="initTabs()">
	<div region="north" border="false" style="height:97px; width:100%; " align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td height="57" background="${ctx}/images/main/main_03.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td width="300" height="57" background="${ctx}/images/main/main_01.gif">&nbsp;</td>
		        <c:if test="${_company!=null&&_company!='Cosmosource'}">
		        <td  width="100" align="left" valign="middle"><img border="0" align="left" src="${ctx}/images/logo/${_company}.gif" width="100"/></td>
		        </c:if>
		        <td style="FONT-SIZE:20px;" class=drop0 align="left" valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;
					<font face=黑体>JCosmo</font>
		        </td>
		        <td width="281" valign="bottom">
		        <table width="100%" border="0" cellspacing="0" cellpadding="0">
		          <tr>
		            <td width="33" height="27"><img src="${ctx}/images/main/main_05.gif" width="33" height="27"/></td>
		            <td width="248" background="${ctx}/images/main/main_06.gif">
		            <table width="225" border="0" align="center" cellpadding="0" cellspacing="0">
		              <tr>
		                <td height="17"><div align="right"><a href="javascript:void(0);" onclick="openPwdDiv()"><img src="${ctx}/images/main/pass.gif" width="69" height="17"/></a></div></td>
		                <td><div align="right"><a href="javascript:void(0);" onclick="openEditDiv()"><img src="${ctx}/images/main/user.gif" width="69" height="17"/></a></div></td>
		                <td><div align="right"><a href="${ctx}/j_spring_security_logout"><img src="${ctx}/images/main/quit.gif" alt=" " width="69" height="17"/></a></div></td>
		              </tr>
		            </table>
		            </td>
		          </tr>
		        </table>
		        </td>
		      </tr>
		    </table></td>
		  </tr>
		  <tr>
		    <td height="40" background="${ctx}/images/main/main_10.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td width="194" height="40" background="${ctx}/images/main/main_07.gif">&nbsp;</td>
		        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
		          <tr>
		          <td  align="left"><h:funcMenu menuType="menu"/></td>
		               <td align="left">
		               	<span class="STYLE7">欢迎您：</span>
		               	<span class="STYLE7"><s:property value="#request.username"/></span>
		               	<span id="bwAlertMsgId" style="COLOR: #e60012;FONT-SIZE: 16px;"></span>
		               </td>
		          </tr>
		        </table>
		        </td>
		        <td width="248" background="${ctx}/images/main/main_11.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		          <tr>
		            <td width="16%"><span class="STYLE5"></span></td>
		            <td width="75%"><div align="center"><span class="STYLE7" id="systime"></span></div></td>
		            <td width="9%">&nbsp;</td>
		          </tr>
		        </table></td>
		      </tr>
		    </table></td>
		  </tr>		 
		</table>		
	</div>
	<%--<div region="west" split="true" icon="icon-guid" title="导航菜单" id="accordion-div" style="width:200px;padding:0px;color:#15428b;display:none;">
		<div class="easyui-accordion" fit="true" style="width:180px;height:200px;background:#f1f8fd;">
			<h:funcMenu menuType="accordion"/>     
		</div>	
	</div>--%>
	<div region="center" id="center-div" style="display:none;">
		<div id="main-center" class="easyui-tabs" fit="true" border="false">
			<div title="首页" style="padding:20px;">
			<c:if test="${_company=='tescoadmin' or _company=='hisapadmin' or _company=='lotusadmin'}">
			<iframe id="initFrame" scrolling="auto" frameborder="0"  src="" style="width:100%;height:100%;"></iframe>		
			</c:if>
			<c:if test="${_company!='tescoadmin' and _company!='hisapadmin' and _company!='lotusadmin'}">
			<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
			  <tr>
			    <td align="center"><img src="${ctx}/images/main/wel.jpg" width="660" height="377"></td>
			  </tr>
			</table>
			</c:if>
			</div> 
		</div>
	</div>
<div id="mm" class="easyui-menu" style="width:150px;display:none;" >
	<div id="mm-tabclose">关闭</div>
	<div id="mm-tabcloseall">全部关闭</div>
	<div id="mm-tabcloseother">除此之外全部关闭</div>
	<div class="menu-sep"></div>
	<div id="mm-tabcloseright">当前页右侧全部关闭</div>
	<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
	<div class="menu-sep"></div>
	<div id="mm-exit">退出</div>
</div>
</body>
</html>

