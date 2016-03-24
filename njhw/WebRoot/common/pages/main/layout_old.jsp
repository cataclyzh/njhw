<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>JCosmo</title>
	<%@ include file="/common/include/header-meta.jsp" %>
	<link href="${ctx}/styles/layout.css" rel="stylesheet" type="text/css"/>
	<style type="text/css">
	    ul,li{margin:0;padding:0}
	    #scrollDiv{width:400px;height:25px;min-height:25px;line-height:25px;overflow:hidden}
	    #scrollDiv li{height:25px;padding-left:10px;}
	    #scrollDiv a:link{cursor:hand;color:red;font-size:16px;text-decoration:none;}
	    #scrollDiv a:active{cursor:hand;color:red;font-size:16px;text-decoration:none;}
	    #scrollDiv a:hover{cursor:hand;color:red;font-size:16px;text-decoration:none;}
	    #scrollDiv a:visited{cursor:hand;color:red;font-size:16px;text-decoration:none;}
		.STYLEX {font-size: 16px; color: red; }
	</style>
	
	<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
	<script src="${ctx}/common/pages/main/layout.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/custom/roller.js" type="text/javascript"></script>
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
		$("#scrollDiv").scroll({line:1,speed:800,timer:3000});
	});
	<c:if test="${_orgtype=='8'}">
	function downloadFile(){
		var url = "${ctx}/common/filesMgr/download.act?downid=201";
		location.href=url;
	}

	</c:if>	
	</script>
</head>
<body class="easyui-layout" style="width:100%; overflow:hidden ">
	<div region="north" border="false" style="height:97px; width:100%; " align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td height="57" background="${ctx}/images/main/main_03.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td width="300" height="57" background="${ctx}/images/main/main_01.gif">&nbsp;</td>
		        <c:if test="${_company!=null&&_company!='Cosmosource'}">
		        <td  width="100" align="left" valign="middle"><img border="0" align="left" src="${ctx}/images/logo/${_company}.gif" 
width="100"/></td>
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
		                <td height="17"><div align="right"><a href="javascript:void(0);" onclick="openPwdDiv()"><img src="${ctx}/images/main/pass.gif" width="69" 
height="17"/></a></div></td>
		                <td><div align="right"><a href="javascript:void(0);" onclick="openEditDiv()"><img src="${ctx}/images/main/user.gif" width="69" 
height="17"/></a></div></td>
		                <td><div align="right"><a href="${ctx}/j_spring_security_logout"><img src="${ctx}/images/main/quit.gif" alt=" " width="69" 
height="17"/></a></div></td>
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
		        <td width="400" height="40" background="${ctx}/images/main/main_07.gif"><span class="STYLE7">欢迎您：<s:property value="#request.username"/></span></td>
		        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
		          <tr>
		               <td align="left" width="30%" nowrap>
		               
		               </td>
					   <c:if test="${_orgtype=='8'}">
		               <td align="right" width="10%" nowrap>             	
		               	<span class="STYLEX"><strong>重要通知:</strong></span>		               
		               </td>
		               <td align="left" width="60%" nowrap>
		               	<div id="scrollDiv" style="color: #fff;font-size: 12px;">
						  <ul>
						    <li><a href="javascript:void(0);" onclick="javascript:downloadFile();"><strong>卜蜂莲花郑重承诺</strong></a></li>
						    <li><a href="javascript:void(0);" onclick="javascript:downloadFile();"><strong>卜蜂莲花郑重承诺</strong></a></li>
						    <!-- add more message -->
						  </ul>
		               	</div>
		               </td>
					   </c:if>	
		          </tr>
		        </table></td>
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
	<div region="west" split="true" icon="icon-guid" title="导航菜单" style="width:200px;padding:0px;color:#15428b;">
		<div class="easyui-accordion" fit="true" style="width:180px;height:200px;background:#f1f8fd;">
			<h:funcMenu menuType="accordion"/> 
		</div>	
	</div>
	<div region="center" id="center-div">
		<div id="main-center" class="easyui-tabs" fit="true" border="false">
			<div title="首页" style="padding:20px;">
			<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
			  <tr>
			    <td align="center"><img src="${ctx}/images/main/wel.jpg" width="660" height="377"></td>
			  </tr>
			</table>			
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

