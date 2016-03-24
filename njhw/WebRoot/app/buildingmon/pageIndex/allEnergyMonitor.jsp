﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
	<body>
	
	SUCCESS!!
		<!--<div class="main_index">
			<jsp:include page="/app/integrateservice/header.jsp" >
				<jsp:param value="/app/integrateservice/init.act" name="gotoParentPath"/>
			</jsp:include>
			<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
				<div class="bgsgl_border_left">
					此处写页面的标题 
					<div class="bgsgl_border_right">
						三维定位
					</div>
				</div>
				<div class="bgsgl_conter" style="min-height: 550px">
					此处写页面的内容 
					<div class="fla_left">
						请输入房间号
						<div class="fla_top">
							<input name="" class="fla_input" id="room_num" type="text" />
							<span>注意:由字母和数字组成，例如：D410</span>
							<a class="fla_botton" id="search_room" href="javascript:void(0);">定位房间</a>
						</div>
						<div class="fla_top">
							请选择单位
							<select id="org" name="org" class="fla_input">
								<c:forEach items="${orgList}" var="org">
									<option value="${org.orgId }">
										${org.name }
									</option>
								</c:forEach>
							</select>
							<a class="fla_botton" id="search_unit" href="javascript:void(0);">定位单位</a>
						</div>
						<div class="fla_top">
							<strong>三维操作提示：</strong>
							<br />
							<span>1、三维地图对显卡要求较高，建议使用1G以上独 立显卡<br />
								2、首次使用三维地图请务必按照页面提示安装所 需插件<br /> 3、三维地图首次加载可能较慢，请务必在地图加
								载完毕后再进行定位操作，否则可能导致浏览 器崩溃</span>
						</div>
					</div>
					<div class="swfk_dj_right" style="float: right; width: 1000px; height: 550px;">
						
					</div>
					<div class="bgsgl_clear"></div>
				</div>
			</div>
			
		</div>
		<jsp:include page="/app/integrateservice/footer.jsp" />
	--></body>

</html>