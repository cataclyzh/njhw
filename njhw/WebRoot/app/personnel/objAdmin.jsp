<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
<head>
<%@ include file="/common/include/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>资源权限管理</title>
<link href="${ctx}/app/portal/toolbar/css/toolbar.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/common/pages/misc/css/qbyj_index.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/common/pages/misc/css/table.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/common/pages/misc/css/admin_css.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/app/integrateservice/css/css.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/app/integrateservice/css/wizard_css.css"
	rel="stylesheet" type="text/css" />
<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>		
<script src="${ctx}/common/pages/misc/js/ctt.js" type="text/javascript"></script>
<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function(){
		var chk_options = { 
			all:'#checkAll',
			item:'.checkItem'
		};
		$(this).chk_init(chk_options);
		changebasiccomponentstyle();
		changedisplaytagstyle();
		changecheckboxstyle();
	});
	function getObjAdminUserTreeList(){
		//var url = "${ctx}/app/per/orgUserTree.act?roomId="+roomId+"&dtype=room";
		var url = "${ctx}/app/per/orgAdminUserTree.act?nodeId="+$("#nodeId").val();
		showShelter('500','400',url);
	}

	function windowDialog(title, url, w, h, roomId) {
		var body = window.document.body;
		var left = body.clientWidth / 2 - w / 2;
		var top = body.clientHeight / 2 - h / 2;
		var scrollTop = document.body.scrollTop;
		//alert("left:"+left+"||top:"+top+"||scroll:"+scrollTop);
		top = top + scrollTop;
		$("#companyWin").show();
		$("#companyWin").window( {
			title : title,
			modal : true,
			shadow : false,
			closed : false,
			width : w,
			height : h,
			top : top,
			left : left,
			onBeforeClose : function() {
			}
		});
		$("#companyIframe").attr("src", url);
	}
	
	function closeWindow(type,roomId){
		$("#companyWin").window("close");
	}

	function windowDialogNew(title, url, w, h, roomId) {
		var body = window.document.body;
		var left = body.clientWidth / 2 - w / 2;
		var top = body.clientHeight / 2 - h / 2;
		var scrollTop = document.body.scrollTop;
		//alert("left:"+left+"||top:"+top+"||scroll:"+scrollTop);
		top = top + scrollTop;
		$("#companyWin").show();
		$("#companyWin").window( {
			title : title,
			modal : true,
			shadow : false,
			closed : false,
			width : w,
			height : h,
			top : top,
			left : left,
			onBeforeClose : function() {
			}
		});
		$("#companyIframe").attr("src", url);
	}
	
	 
</script>
<style>
	.table_btn_orgframe
	{
	    width: 68px;
	    height: 22px;
	    background:#8090b2;
		color:#fff;
		line-height:22px;
		text-align:center;
		font-family:"微软雅黑";
		margin-right:10px;
		float:right;
		cursor:pointer;
		text-decoration: none;
	}
</style>
</head>
<body>
	<input id="pageNo" value="1" type="hidden">
	<div class="main_index">
		<jsp:include page="/app/integrateservice/header.jsp"></jsp:include>
		<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
			<div style="min-height: 300px">
				<div class="fkdj_index">
					<div class="images_left">
						<%@ include file="/common/pages/misc/cttLeft.jsp"%>
					</div>
					<div class="images_right">
						<div class="bgsgl_border_left">
							<div class="ctt_bgsgl_border_right" style="background: url('${ctx}/styles/images/border_left.jpg') no-repeat scroll left top transparent;color: #FFFFFF;font-family:'微软雅黑';font-size: 18px;font-weight: bold;height: 36px;line-height: 36px;padding-left: 30px;">
								资源权限管理
							</div>
						</div>
						<div class="bgsgl_conter">
							<div class="admin_conter_left" style="height:100%">
								<div class="bgsgl_right_list_border">
									<div class="bgsgl_right_list_left">
											功能菜单
									</div>
								</div>
								<div class="admin_height_10">
									<iframe scrolling="auto" frameborder="0" name="ifrmOrgTree" id="ifrmObjTree" src="${ctx}/app/per/objAdminTree.act?resType=M"
										border="0" marginheight="0" marginwidth="0" frameborder="0" 
										style="width:100%;height:100%;padding:0px;min-height: 453px"></iframe>
								</div>
							</div>
							<div class="admin_conter_right">
								<div class="bgsgl_right_list_border" style="margin-bottom:10px;">
								
								</div>
								<div class="qbyj_conter" id="qbyj_conter_div">
								<iframe scrolling="auto" frameborder="0" name="ifrmOrgList" id="ifrmObjList" src="${ctx}/app/per/objAdminList.act?nodeId=30&amp;resType=M" style="width:100%;height:453px;padding:0px;"></iframe>
								</div>
							</div>
					<div class="bgsgl_clear"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="bgsgl_clear"></div>
	</div>
<script type="text/javascript">
	var flag = false;
	// CHECKED 全选效果
	function checkAll(){
		if(flag){
			$("td [id^='checked_0']").attr("checked",false);
			flag = false;
		}else{
			//选中全部
			$("td [id^='checked_0']").attr("checked",true);
			flag = true;
		}
	}
		
	function addRecord(){
		var url = "${ctx}/app/per/objInput.act?PId=" +$('#PId').val() +"&res="+$('#res').val() ;
		showShelter("800","450",url);
	}
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			//using('messager', function () {
			//	$.messager.alert('提示信息','请勾选要删除的记录！','info');
			//});
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			//using('messager', function () {
				easyConfirm('提示', '确定删除？', function(r){
					if (r){
						var result = new Array(); 
						$("td [id^='checked_0']").each(function(){
							if($(this).is(":checked")){
								result.push($(this).attr("value"));
							}
						});	
						var msgIds = result.join(",");
						//alert(msgIds);
						url = "${ctx}/app/per/delObjAdminUser.act?ids="+msgIds+"&nodeId="+$("#nodeId").val();
						window.location.href = url;
						//$('#queryForm').attr("action","msgBoxAction_delMess.act");
						//$('#queryForm').submit();
					}
				});
			}
		}
</script>
<jsp:include page="/app/integrateservice/footer.jsp" />
</body>
</html>
