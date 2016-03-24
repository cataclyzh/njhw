<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/meta.jsp"%>

<html>
	<head>
	<title>摄像头测试</title>
	<script type="text/javascript" src="${ctx}/app/portal/toolbar/showModel.js"></script>
	<script type="text/javascript"
			src="${ctx}/app/portal/toolbar/showModel.js"></script>
	
	<script type="text/javascript">
	
	
	/**
	 * 提供给FLASH的接口 ： 没有摄像头时的回调方法
	 */
	function noCamera() {
		alert("没有摄像头");
	}

	/**
	 * 提供给FLASH的接口：编辑头像保存成功后的回调方法
	 */
	function avatarSaved() {
		//这里需跳转到别的页面去
		parent.closeIframe();
		parent.frameDialogClose();

	
		
	


	}

	/**
	 * 提供给FLASH的接口：编辑头像保存失败的回调方法, msg 是失败信息，
	 * 可以不返回给用户, 仅作调试使用.
	 */
	function avatarError(msg) {
		alert(msg);
	}
	
	
	function photoOnload(){
		parent.setPhotoName('${photoName}');
		
	}
	
</script>

	</head>

	<body onload="photoOnload()">

		<EMBED  height="464" type="application/x-shockwave-flash"
			pluginspage="http://www.macromedia.com/go/getflashplayer" width="514"
			src="${ctx}/app/portal/photo/AvatarEditor.swf" quality="high" allowscriptaccess="always"
			flashvars="type=camera&amp;postUrl=${ctx}/servlet/PhotoServlet?photoName=${photoName}&amp;radom=1&amp;saveUrl=${ctx}/servlet/PhotoServlet?photoName=${photoName}radom=1">
		</EMBED>

		<br />
		<br />
		<EMBED height="464" type="application/x-shockwave-flash"
			pluginspage="http://www.macromedia.com/go/getflashplayer" width="514"
			src="${ctx}/app/portal/photo/AvatarEditor.swf" quality="high" allowscriptaccess="always"
			flashvars="type=photo&amp;photoUrl=0.jpg&amp;postUrl=${ctx}/servlet/PhotoServlet?photoName=${photoName}&amp;radom=1&amp;saveUrl=${ctx}/servlet/PhotoServlet?photoName=${photoName}radom=1">
		</EMBED>
		
	</body>
</html>
