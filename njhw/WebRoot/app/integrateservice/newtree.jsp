<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<div class="main_left_main1" id="treeComm" style="display: block;">
	<div class="main_border_01">
		<div class="main_border_02">通讯录</div>
	</div>
	<div class="main_conter" id="treeComm_div" style="height:366px;">
		<div class="main_right3">
			<div class="clear"></div>
			<div class="main_right3_input">
				<input type="text" id="searchTJ" onfocus="inputFun(this, 0)" onblur="inputFun(this, 1)" value="请输入姓名或电话"  class="input_txt"/>
				<input name="" class="input_button" type="button"  onclick="searchInfo()" id="search"/>
			</div>
			<div class="reportTips_tips1">
				<div style="cursor: pointer;" class="tips1" id="bigTreeId" onclick="upClassTree('bigTreeId');tipsChange(this, 'tree')">大厦</div>
				<div style="cursor: pointer;" class="tips2" id="treeUnitId" onclick="upClassTree('treeUnitId');tipsChange(this, 'treeUnit')">单位</div>
				<div style="cursor: pointer;" class="tips3" id="pabListId" onclick="upClassTree('pabListId');tipsChange(this, 'pabList')">个人</div>
				<div style="cursor: pointer;" class="tips2" id="e4Id" onclick="upClassTree('e4Id');tipsChange(this, 'e4List')">E座</div>
				<div class="main_img2" style="width: 55px;">
					<a style="cursor:pointer;">
						<img src="images/more_SZ.png" id="editTree" style="margin-top:-2px;display:block;height: 15px;width:15px;float: left;" title="编辑" onclick="showShelter('1010','480','<%=path%>/app/paddressbook/init.act');"/>
					</a>
					<a style="float: right;padding-right: 20px;" href="javascript:void(0)" onclick="refreshAddList();" title="刷新" id="refresh_List" >
						<img src="images/shipin_2.jpg" width="15" height="9" />
					</a>
				</div>
			</div>
			<div class="input_bg"></div>
			<div class="main_right3_more">
				<div class="main_right3_more_sz" id="peopleSet" style="display: none;float:left;margin-right:7px" onclick="showShelter('1010','410','<%=path%>/app/paddressbook/init.act');" title="设置"></div>
				<div class="main_right3_more_fd" id="amplifyA" style="display: block;float:left" onclick="amplify();" title="放大"></div>
				<div class="refresh_main_right3_more" id="refresh_List" style="float:left" onclick="refreshAddList();" title="刷新"></div>
			</div>
		    <div class="clear"></div>
		</div>

		<input type="hidden" id="showType" name="showType"/>
		<div class="address_list" id="address_list" style="height:580px;">
			<div id="tree"></div>
			<div id="treeUnit" style="display: none;"></div>
			<div id="pabList" style="display: none;"></div>
			<div id="e4List" style="display: none"></div>
			<div id="searchList" style="display: none"></div>
		</div>
	</div>
</div>
</html>
<script type="text/javascript">
	//通讯录变量设置
	var orgIdAddress = '${orgId}';
	var typeAddress = '${type}'; //	"tel"|"fax"
	if(typeAddress == ''){
		typeAddress = 'tel';
	}
	var roomName = '${roomName}';
	var selfMacAddress = '${smac}';//	当前用户的ip电话MAC地址
	var selfTelAddress = '${stel}';//	当前用户的ip电话号码
	$(".tips1").click();

	function upClassTree(id){
		if("bigTreeId" == id){
			$("#bigTreeId").attr('class','tips1');
			$("#treeUnitId").attr('class','tips2');
			$("#pabListId").attr('class','tips3');
			$("#editTree").css('display','none');
			$("#e4Id").attr('class','tips2');
		}else if("treeUnitId" == id){
			$("#bigTreeId").attr('class','tips2');
			$("#treeUnitId").attr('class','tips1');
			$("#pabListId").attr('class','tips3');
			$("#editTree").css('display','none');
			$("#e4Id").attr('class','tips2');
		}else if("pabListId" == id){
			$("#bigTreeId").attr('class','tips2');
			$("#treeUnitId").attr('class','tips3');
			$("#pabListId").attr('class','tips1');
			$("#editTree").css('display','block');
			$("#e4Id").attr('class','tips2');
		}else if("e4Id" == id){
			$("#bigTreeId").attr('class','tips2');
			$("#treeUnitId").attr('class','tips3');
			$("#pabListId").attr('class','tips2');
			$("#editTree").css('display','none');
			$("#e4Id").attr('class','tips1');
		}
	}
</script>