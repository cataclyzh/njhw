<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<html>
	<head>
		<title>TopMenu</title>
		<meta name="GENERATOR" Content="Microsoft Visual Studio .NET 7.1">
		<meta name="CODE_LANGUAGE" Content="C#">
		<meta name="vs_defaultClientScript" content="JavaScript">
		<meta name="vs_targetSchema" content="http://schemas.microsoft.com/intellisense/ie5">
		<LINK href="css/style.css" type="text/css" rel="stylesheet">
		<link href="css/TopMenu.css" rel="stylesheet" type="text/css">
		<style>	td{white-space:nowrap}
		</style>
		<script src="js/TopMenu.js" type="text/javascript" charset="gb2312"></script>
		<script language="javascript">
		document.title = "<%=strTitle%>";
		
		</script>
	</head>
	<body scroll="no" onload="Init()">
		<form id="Form1" method="post" runat="server">
			<table border="0" cellpadding="0" cellspacing="0" width="100%" background="images/home_ln_dd.gif">
				<tr>
					<td><asp:label id="LabelShowMenu" runat="server"></asp:label></td>
				</tr>
			</table>
			<iframe id="ShowSecondFrame" runat="server" src="" frameBorder="0" width="100%" scrolling="no"
				height="95%"></iframe>
		</form>
	</body>
</html>

