<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<script type="text/javascript">var _ctx = '${ctx}';</script>
<link href="${ctx}/styles/default.all.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/app/portal/toolbar/css/toolbar.css" rel="stylesheet" type="text/css"/>
<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<script src="${ctx}/app/caller/frontReg/visitorjsNew.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/common/pages/main/layout.js"></script>

<link href="${ctx}/app/integrateservice/css/fex_center.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/integrateservice/css/css_body.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/integrateservice/css/css_index.css" rel="stylesheet" type="text/css" />
<title>访客登记</title>
<OBJECT id="WebRiaReader" codeBase="" classid="clsid:13C57329-846A-4C82-AAB1-1AA0EC3CA0BB"></OBJECT>
<script src="${ctx}/scripts/validation/card.js" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
<script src="${ctx}/app/caller/frontReg/visitorjs.js" type="text/javascript"></script>		
<script type="text/javascript">
$(document).ready(function(){
	jQuery.validator.methods.compareDate1 = function(value, element, param) {
        var beginTime = jQuery(param).val();
        if(value!=""){
        return beginTime <= value;
        }else {
        return true;
        }
    };  
	$("#queryForm").validate({		// 为inputForm注册validate函数
		meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
		errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
		onsubmit: true,
		rules: {

		vsEt:{
		compareDate1: "#vsSt"
	}
		},
		messages:{
			vsEt:{
				compareDate1: "结束时间必须晚于开始时间"
			}
		}
	});
});    
</script>
<script type="text/javascript">
	
	//图片名称
	var pageTimer=null; //定义计算器全局变量
	var photoName="";
	var dbPhotoName=""
   	var str = "${ctx}";
   	var ctx = "${ctx}";
   	var DRIVER_TYPE = "";
   	$(document)
		.ready(function() {
			opeReset("init");
			//初始化市民卡接口
			initPhotoActiveX();
	});

	function refreshVMCountInfo(){
		var url = "${ctx}/app/visitor/frontReg/dailyVMCountInfo.act";
		var sucFun = function(data) {
			$("#right_bottom").empty().html(data);
		};
		var errFun = function() {
			alert("加载数据出错!");
		};
		ajaxQueryHTML(url, null, sucFun, errFun);
	}
			
	/**
	* 页面查询按钮事件
	*/
	function queryBtnClick(){
		$("#DRIVER_TYPE").val("btnQuery");
	    if($("#queryForm").valid()){
		    queryData();
	    }
	}
	/**
	* init photo activeX
	*/
	function initPhotoActiveX(){
	    try{
		WebRiaReader.LinkReader();
		}catch(e){
		   //
		}
	}
	
	/**
	* window close 关闭市民卡
	*/
	function close()
	{
		//关闭市民卡扫描插件
		WebRiaReader.RiaCloseReader();
	}
	
	/**
	* 返回按钮事件
	*/
	function opeReset(type) {
		if (null == type || type=="") {
			if(confirm("确定不做当前操作,新建登记吗!?")){ 
				opeResetDo();
				return true;
			}
			return false;
		}
		opeResetDo();
	}
	function opeResetDo(){
		var url = "${ctx}/app/visitor/frontReg/refreshVmOpeContent.act";
		var sucFun = function(data) {
			//$("#vm_ope_content").replaceWith(data);
			$("#vm_ope_content").empty().html(data);
		};
		var errFun = function() {
			alert("加载数据出错!");
		};
		
		var data = null;
		ajaxQueryHTML(url, data, sucFun, errFun);
		
		queryBtnClick();
	}
	
	
	
	/**
	* 调用拍照
	*/
	function photograph(){
	   var url="${ctx}/app/visitor/frontReg/photograph.act";
	   showShelter('600','500',url)
	}
	function setPhotoName(name){
		photoName = name;
	}
	
	function frameDialogClose(){
		var url = "${ctx}/app/visitor/frontReg/getBytesPic.act";
		var sucFun = function(data) {
			if (null!=data && data.length > 0) {
			//alert(data.length);
				document.getElementById('photoshops').src = data;
				//alert(document.getElementById('photoshops').src.length);
				//$("#photoshops").attr("src",data);
				
			}
		};
		var errFun = function() {
			alert("加载数据出错!");
		};
		var data = "photoName="+photoName;
		ajaxQueryHTML(url, data, sucFun, errFun);
	}
			
	function tempCardCount(){
		var url = "${ctx}/app/caller/queryVisitorsForTempCard.act?cardType=2";
		openEasyWin("orgInput","临时卡统计",url,"800","600",false);
	}
	function dailyVMCount(){
		var url = "${ctx}/app/visitor/frontReg/dailyVMCountInfo.act";
		openEasyWin("orgInput","当日统计",url,"650","300",false);
	}
	
	
	function selectRespondents1() {
	var url = "${ctx}/app/visitor/frontReg/respondentsTreeSelect.act?state=all";
	var params = "";
	url += params;
	/*
	var width = 400;
	var left = (document.body.scrollWidth - width) / 2;
	$("#companyWin").show();
	$("#companyWin").window({
		title : '请选择受访人',
		onClose : function() {
			//$("#companyWin").window('destroy');
			parent.$("#companyWin").window("close");
			//closeEasyWin('companyWin');
		   // parent.updateCallBack();
		},
		modal : true,
		shadow : false,
		closed : false,
		width : width,
		height : 600,
		top : 50,
		left : left,
	});*/
	openEasyWin("companyWin","请选择受访人",url,"600","400",false);
	$("#companyIframe").attr("src", url);
}
</script>
</head>
<body>
	<div class="main_index">
		<c:if test="${param.type eq 'A'}">
			<jsp:include page="/app/integrateservice/headerWy.jsp">
			    <jsp:param value="/app/integrateservice/init.act" name="gotoParentPath"/>
			</jsp:include>
		</c:if>
		<c:if test="${param.type ne 'A'}">
			<jsp:include page="/app/integrateservice/header.jsp">
			    <jsp:param value="/app/pro/propertyIndex.act" name="gotoParentPath"/>
			</jsp:include>
		</c:if>
		<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
			<div class="bgsgl_border_left">
				<div class="bgsgl_border_left">
					<div class="bgsgl_border_right">访客登记</div>
				</div>
			</div>
			<div class="bgsgl_conter" style="min-height: 300px">

				<div id="vm_ope_content"></div>
				<div class="fkdj_right1">
					<div class="fkdj_botton1">
						<a class="fkdj_botton_left" href="javascript:void(0);"
							onclick="javascript:tempCardCount();">临时卡统计</a>
						<%--<a
						class="fkdj_botton_right" href="javascript:void(0);" onclick="javascript:dailyVMCount();" >当日访客统计</a> --%>
						<div style="padding-left: 10px;float:right;">
							<table width="300px">
								<tr>
									<td width="50%"><span>超时访问人数:</span><span><font
											style="color: red">${vmList.expiredCount}</font></span></td>
									<td><span>来访人数:</span><span class="bottom_span1" id="vm"><font
											style="color: blue">${vmList.userCount}</font></span></td>
								</tr>
							</table>
						</div>
						<div style="padding-left: 10px;float:right;">
							<table width="300px;">
								<tr>
									<td width="50%"><span>在访人数:</span><span
										class="bottom_span2"><font style="color: blue">${vmList.userVisiting}</font></span>
									</td>
									<td><span>离开人数:</span><span class="bottom_span3"><font
											style="color: blue">${vmList.userLeave}</font></span></td>
								</tr>
							</table>
						</div>
					</div>
					<div class="fkdj_lfrycx">
						<div class="bgsgl_right_list_border">
							<div class="bgsgl_right_list_left">来访人员查询</div>
						</div>
						<div class="fkdj_from">
							<form id="queryForm" action="visitorList.act" method="post"
								autocomplete="off">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td class="fkdj_name">访客姓名</td>
										<td><input type="hidden" name="DRIVER_TYPE"
											id="DRIVER_TYPE" value=""> <input type="hidden"
											name="cur_broom_card_no" id="DRIVER_TYPE" value=""> <input
											type="hidden" name="pageNo" id="pageNo" value="1"> <input
											type="text" class="fkdj_from_input" name="viName" id="viName"
											size="18" value="" /></td>
										<td style="display: none"><span>手机号码</span></td>
										<td style="display: none"><input type="text"
											name="viMobile" id="viMobile" size="18" value="" /></td>
										<td class="fkdj_name">访客状态</td>
										<td><s:select list="#application.DICT_VISIT_STATUS"
												cssClass="fkdj_from_input"
												style="height:30px;font-size:15px" headerKey="0"
												headerValue="全部" listKey="dictcode" listValue="dictname"
												name="vsFlag" id="vsFlag" /></td>
									</tr>
									<tr>
										<td class="fkdj_name">访问时间</td>
										<td><input type="text" class="fkdj_from_input"
											onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
											id="vsSt" name="vsSt" value = "${startTime}" cssClass="Wdate" /></td>
										<td class="fkdj_name">至</td>
										<td><input type="text" class="fkdj_from_input"
											onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
											id="vsEt" name="vsEt" value = "${endTime}" cssClass="Wdate" /></td>
									</tr>
								</table>
							</form>

							<div class="fkdj_ch_table">
								<a href="javascript:void(0);" onclick="javascript:queryBtnClick();">搜&nbsp;&nbsp;&nbsp;索</a>
							</div>
						</div>
					</div>
					<div id="left_below" style="overflow:hidden"></div>
				</div>


				<div class="bgsgl_clear"></div>
			</div>
		</div>
		<jsp:include page="/app/integrateservice/footer.jsp" />

		<div id='companyWin' class='easyui-window' collapsible='false'
			minimizable='false' maximizable='true'
			style='padding: 0px; background: #fafafa; overflow: hidden; display: none;'
			closed='true'>
			<iframe id='companyIframe' name='companyIframe'
				style='width: 100%; height: 100%;' frameborder='0'></iframe>
		</div>
	</div>
</body>
</html>