<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title></title>
		<OBJECT id="WebRiaReader" codeBase=""
			classid="clsid:13C57329-846A-4C82-AAB1-1AA0EC3CA0BB"></OBJECT>
		<link type="text/css" rel="stylesheet" href="css/tabMian.css" />
		<script type="text/javascript" src="js/tabMain.js"></script>
		<script type="text/javascript" src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script type="text/javascript">
			/*加载房间分配页面*/
			function loadRoomDistribute(pageNo){
				var url = "${ctx}/app/personnel/unit/roomDistribute.act?pageNo="+pageNo;
				//var url = "${ctx}/app/personnel/unit/light.act?pageNo="+pageNo;
				$("#unit_nav_content").attr("src",url);
			}
			
			$(document).ready(function(){
			//$("#playCard").attr("backgroundPosition","-250px -400px");
				loadRoomDistribute(1);
				initPhotoActiveX();
			});
			
			/**
			* 导航方法
			*/
			function navBtnClick(type){
				//alert(type);
				//alert(type=="light");
				var linkUrl = "";
				var title = "";
				var id = "";
				if(type=="orgUser"){
					id = '10111';
					title = "机构及人员管理";
					//linkUrl = "http://WangXJ/mxwo/mxe/permissions/org_user.htm";
					linkUrl = "${ctx}/app/per/orgTreeUserCheckinFrame.act";
				}
				
				else if(type=="phoneList"){
					id = '10261';
					title = "电话和号码";
					linkUrl = "${ctx}/app/personnel/telAndNumber/index.act";
				} else if(type=="park"){
					id = 'cpcw';
					title = "车牌和车位";
					linkUrl = "${ctx}/app/personnel/queryCarPorts.act?orgId=${_orgid}";
				} else if(type=="userPermitList"){
					id = 'wbjgly05';
					title = "人员权限清单";
					linkUrl = "${ctx}/app/personnel/orguser/usersLimitInit.act";
				} else if(type=="readCardPermit"){
					var str;
					str = WebRiaReader.RiaReadCardEngravedNumber;
					
					/*if (str==undefined || "" == str || $.trim(str) == "FoundCard error!"){
				 		easyAlert('提示信息','读取市民卡异常!请检查市民驱动是否正确安装.','info');
				 		return;
				 	}*/
				 	
					id = 'dqkqx';
					title = "读取卡权限";
					linkUrl = "${ctx}/app/personnel/unit/readCardPrivileges.act?cardId="+str;
					//showShelter('600','500',linkUrl)
					openEasyWin("dqkqx","读取卡权限",linkUrl,"800","400",true);
					return;
				} else if (type=="playCard"){
					linkUrl = "${ctx}/app/personnel/unit/playCardInit2.act";
					openEasyWin("zztk","制作通卡",linkUrl,"800","500",true);
				} else if (type=="playCardIndex"){
					id = 'tkqd'
					linkUrl = "${ctx}/app/personnel/unit/playCardIndex.act";
					title = "通卡清单";
				}
				if (id != "" && title != "" && linkUrl != "") {
					parent.open_main3(id,title,linkUrl);
				}
			}
			
			/**
			* init photo activeX
			*/
			function initPhotoActiveX(){
				WebRiaReader.LinkReader();
			}
			
			/**
			* window close 关闭市民卡
			*/
			function close()
			{
				//关闭市民卡扫描插件
				WebRiaReader.RiaCloseReader();
			}
			
			function showLight()
			{
			
				//alert(1);
				var url = "${ctx}/app/personnel/unit/light.act?pageNo=1";
				$("#unit_nav_content").attr("src",url);
				//alert(2);
				//alert(111);
			}
		</script>
	</head>
	<body>
    <div class="main">
        <div class="main_left">
       
            <div class="btn1" onclick = "navBtnClick('orgUser');" onmouseover="btn_hover(this, 0, 0)" onmouseout="btn_hover(this, 0, 1)"></div>
            
<!--            <div class="btn2" onclick = "navBtnClick('phoneList');" onmouseover="btn_hover(this, 1, 0)" onmouseout="btn_hover(this, 1, 1)"></div>-->
<!--            <div class="btn3" onclick = "navBtnClick('park');" onmouseover="btn_hover(this, 2, 0)" onmouseout="btn_hover(this, 2, 1)"></div>-->
            <div class="btn2" onclick = "navBtnClick('userPermitList');" onmouseover="btn_hover(this, 1, 0)" onmouseout="btn_hover(this, 1, 1)"></div>
            <div class="btn3" onclick = "navBtnClick('playCardIndex');" onmouseover="btn_hover(this, 2, 0)" onmouseout="btn_hover(this, 2, 1)"></div>
            <div class="btn4" onclick = "navBtnClick('playCard');" onmouseover="btn_hover(this, 3, 0)" onmouseout="btn_hover(this, 3, 1)"></div>
        </div>
        <div class="main_right">
            <iframe id= "unit_nav_content" frameborder="0" src="" width="100%" height="100%"></iframe>
        </div>
    </div>
	</body>
</html>