<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>电话管理</title>
		<link type="text/css" rel="stylesheet" href="css/TelMessage.css" />
        <link type="text/css" rel="stylesheet" href="css/table.css" />
        <script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		<script type="text/javascript"
			src="${ctx}/app/portal/toolbar/showModel.js"></script>
        <script type="text/javascript">
            function btn_hover(dom, num)
            {
                if(num == 0)
                {
                    dom.className = dom.className + "_hover";
                }
                else
                {
                    dom.className = dom.className.replace("_hover", "");
                }
            }
            
            /**
			* 号码分配
			*/
			function telAndNumDistribute(){
				var url = "${ctx}/app/personnel/telAndNumber/telAndNumber.act";
				var data = null;
				sucFun = function (data){
					$("#main_right").replaceWith(data);
				};
				errFun = function (data){
					easyAlert('提示信息','加载页面出错!','info');
				};
				ajaxQueryHTML(url,data,sucFun,errFun);
			}
			$(function (){
				telAndNumDistribute();
			});
			
			/**
			* 导出清单
			*/
			function exportDetail() {
				alert("exportDetail");
			}
			/**
			* 申请新话机
			*/
			function applyNewTel(){
				var url = "${ctx}/app/personnel/telAndNumber/unitDistribute.act";
				windowDialog("单位新增话机申请",url,"10",'850','450');
			}
			
			function windowDialog(url,top,w,h){
				$("#companyWin").show();
				var left = (document.body.scrollWidth - w) / 2;
				$("#companyWin").window({
					title : '邮件配置',
					modal : true,
					shadow : false,
					closed : false,
					width : w,
					height : h,
					top : top,
					left : left,
					onBeforeClose:function(){
					}
				});
				$("#companyIframe").attr("src", url);
			}
        </script>
	</head>
	<body>
        <div class="main">
        	<!-- 
            <div class="header"><span style="background:white;font-weight:bold;">话机、号码、传真</span></div>
             -->
            <div class="main_left">
                <div class="main_left_btn1" onclick="javascript:applyNewTel();" onmouseover="btn_hover(this, 0)" onmouseout="btn_hover(this, 1)"></div>
                <!-- 
                <div class="main_left_btn2" onmouseover="btn_hover(this, 0)" onmouseout="btn_hover(this, 1)"></div>
                
                <div class="main_left_btn3" onclick="javascript:exportDetail();" onmouseover="btn_hover(this, 0)" onmouseout="btn_hover(this, 1)"></div>
                 -->
                
				<div class="main_left_btn3" type="submit" value=""></div>
                
                
                <div class="main_left_say"></div>
            </div>
            <div class="main_right" id= "main_right">
            </div>
        </div>
  		<div id='companyWin' class='easyui-window' collapsible='false'
			minimizable='false' maximizable='true'
			style='padding: 0px; background: #fafafa; overflow: hidden; display: none;'
			closed='true'>
			<iframe id='companyIframe' name='companyIframe'
				style='width: 100%; height: 100%;' frameborder='0'></iframe>
		</div>
	</body>
</html>