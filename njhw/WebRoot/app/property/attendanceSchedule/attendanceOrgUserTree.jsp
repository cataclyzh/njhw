<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Author: WXJ
- Date: 2010-09-02 13:26:22
- Description: 管理机构及人员树页面
--%>

<style>
<!--
.icon-xbjbutton {
	background: url('${ctx}/styles/images/xbj.gif') no-repeat;
	background-color: Transparent;
	width: 46;
	height: 22;
	border: none;
	font-size: 12px;
	color: #585858;
	padding: 5px 5px;
	margin: 0;
	outline: 0;
	overflow: visible;
	vertical-align: top;
	cursor: pointer;
}
-->
</style>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>机构及人员树</title>
<%@ include file="/common/include/meta.jsp"%>
<link href="${ctx}/scripts/widgets/dhtmlxTree/codebase/dhtmlxtree.css"
	rel="stylesheet" type="text/css" />

</head>
<body STYLE='OVERFLOW:none;background: white; margin: 0;padding: 0;'>
	<div class="main_left_main1" id="treeComm" style="display: block;height: 100%;">
		<div class="main_border_01">
			<div class="main_border_02">人员目录</div>
		</div>
		<div class="main_conter" id="treeComm_div" style="height:92%;overflow-y:auto;">
			<form id="inputForm" action="orgUserSelectMes.act" method="post"
				autocomplete="off">
				<s:hidden name="orgId" />
				<s:hidden name="roomId" />
				<s:hidden name="dtype" />
				<s:hidden name="checkedIds" />
				<div id="divTree" style="border:0px;"></div>
			</form>
		</div>
	</div>

	<script
		src="${ctx}/scripts/widgets/dhtmlxTree/codebase/dhtmlxcommon.js"
		type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/dhtmlxTree/codebase/dhtmlxtree.js"
		type="text/javascript"></script>
	
	<script type="text/javascript">
		var menu;

		var tree;
		//初始化tree----------------------------------------------------------////
		tree = new dhtmlXTreeObject("divTree", "94%", "auto", 0);
		tree.setImagePath("${ctx}/scripts/widgets/dtree/images/DhtxTree/default/");
		//绑定时间处理函数,注意一定要放在loadXML之前否则右键处理函数在ie下失效
		tree.enableCheckBoxes(1);
		tree.enableDragAndDrop(0);
		tree.enableThreeStateCheckboxes(true);
		tree.setOnClickHandler(tonclick);
		tree.setOnCheckHandler(toncheck);
		tree.setOnDblClickHandler(tondblclick);

		tree.setXMLAutoLoading("${ctx}/app/pro/getAttendanceOrgUserTreeData.act?type=open");
		tree.loadXML("${ctx}/app/pro/getAttendanceOrgUserTreeData.act?ids=${ids}&type=init");//

		//树链接处理函数------------------------------------------------------////
		function tondblclick(itemId) {
			var url = tree.getUserData(itemId, "url");
			if (url != null && url != "") {
				parent.frames.ifrmMenuSubList.location.href = url;
			}

		}
		//checkbox事件
		function toncheck(id, state) {
			saveData();
		};

		//树事件处理函数------------------------------------------------------////
		function tonclick(sId, sPreId) {
			var sTxt = tree.getItemText(sId);
			var sTxtP = tree.getItemText(sPreId);
			//alert("你点击了节点:"+sTxt+",上次点击节点:"+sTxtP);

		}

		function saveData() {
			var treeUId = "", treeUserName = "";
			if (tree.getAllChecked() != "") {
				var idsArray = tree.getAllChecked().split(",");
				for ( var i = 0; i < idsArray.length; i++) {
					if (idsArray[i].indexOf("-o") != -1) {
						continue;
					}
					treeUId += idsArray[i].replace("-u", "") + ",";
					treeUserName += tree.getItemText(idsArray[i]).replace(
							/(^\s*)|(\s*$)/g, "")
							+ ",";
				}
			}
			/**
			 2014-03-06
			 根据物业的要求取消考勤排班的时候，
			 是否绑定定位卡的验证
			 By : ChunJing
			*/
			/*
			var flag = false;
			if(treeUId!=null&&treeUId.length>0){
				$.ajax({
				   type: "POST",
				   url: "${ctx}/app/pro/checkUsers.act",
				   data: {"treeuid":treeUId},
				   async : false,
				   success: function(msg){
				   		if(msg){
				   			alert(msg);
				   			flag = true;
				   		}
				   }
				}); 
			}
			if(flag){
				return ;
			}
			*/
			parent.$('#treeUserId').val(treeUId);
			parent.$('#receiver').val(treeUserName);
			parent.$('#receiver').focus();
			parent.$('#add_attendanceScheduleInTime_dayStart').focus();
			//parent.$("#companyWin").window("close");
		}
	</script>
</body>
</html>
