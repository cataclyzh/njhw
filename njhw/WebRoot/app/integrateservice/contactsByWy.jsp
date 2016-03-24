<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%
	//String orgId = request.getParameter("orgId");
	//String type = request.getParameter("type");				//	"tel"|"fax"
	//String selfMac = request.getParameter("smac");	//	当前用户的ip电话MAC地址
	//String selfTel = request.getParameter("stel");	//	当前用户的ip电话号码
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>综合办公首页</title>
<%@ include file="/common/include/meta.jsp"%>
<script src="${ctx}/app/portal/toolbar/showModel.js"
	type="text/javascript"></script>
<script src="${ctx}/app/integrateservice/js/util.js"
	type="text/javascript"></script>
<script src="${ctx}/app/integrateservice/js/newtree.js"
	type="text/javascript"></script>
<script src="${ctx}/app/integrateservice/js/IntegratedOffice.js"
	type="text/javascript"></script>
<link href="${ctx}/app/integrateservice/css/newtree.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/app/integrateservice/css/wizard_css.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/app/portal/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/ca/highcharts.js"></script>
<script type="text/javascript" src="${ctx}/scripts/ca/exporting.js"></script>
<!-- link href="${ctx}/app/integrateservice/css/css.css" rel="stylesheet" type="text/css" /> -->
<style type="text/css">
body {
	background: url(a.jpg) left top repeat-x;
}
.main_left_x1 a {
	cursor: pointer;
}
/***************节能START***********************/
.jienen_titie {
	font-size: 12px;
	width: 39%;
	color: #7a7a7a;
}
.jienen_titie_unit {
	font-size: 12px;
	width: 29%;
	color: #7a7a7a;
}
.jienen_text {
	font-size: 16px;
	color: #7d93bc;
	font-weight: bold;
}
.table_css {
	margin: 5px 20px;
}
/***************节能END***********************/
</style>
</head>
<body style="margin: 0;padding: 0;">
<!-- 右边功能栏开始 -->
<div class="" style="overflow: hidden;margin: 0;padding: 0;">
  <div class="main_left_main1" style="display: block;overflow: hidden;"
			id="multFun"></div>
  <div class="clear" style="height: 0px;"></div>
  <div align="center" style="display:none;">
    <input type="button" style="width: 50px;height:10px;bottom: 5px;"
				id="upOrDown" value="A" onclick="upOrDownCheck()" />
  </div>
  <div class="main_left_main1" id="treeComm" style="display: block;">
    <div class="main_border_01">
      <div class="main_border_02">通讯录</div>
    </div>
    <div class="main_conter" id="treeComm_div" style="height:330px;">
      <div class="main_right3">
        <div class="clear"></div>
        <div class="main_right3_input">
          <input type="text" id="searchTJ" onfocus="inputFun(this, 0)"
							onblur="inputFun(this, 1)" value="请输入姓名或电话" class="input_txt" />
          <input name="" class="input_button" type="button"
							onclick="searchInfo()" id="search" />
        </div>
        <div class="reportTips_tips1">
          <div style="cursor: pointer;" class="tips1" id="bigTreeId"
							onclick="upClassTree('bigTreeId');tipsChange(this, 'tree')">大厦</div>
          <div style="cursor: pointer;" class="tips2" id="treeUnitId"
							onclick="upClassTree('treeUnitId');tipsChange(this, 'treeUnit')">单位</div>
          <div style="cursor: pointer;" class="tips3" id="pabListId"
							onclick="upClassTree('pabListId');tipsChange(this, 'pabList')">个人</div>
          <script type="text/javascript">
							function upClassTree(id) {
								if ("bigTreeId" == id) {
									$("#bigTreeId").attr('class', 'tips1');
									$("#treeUnitId").attr('class', 'tips2');
									$("#pabListId").attr('class', 'tips3');
								} else if ("treeUnitId" == id) {
									$("#bigTreeId").attr('class', 'tips2');
									$("#treeUnitId").attr('class', 'tips1');
									$("#pabListId").attr('class', 'tips3');
								} else if ("pabListId" == id) {
									$("#bigTreeId").attr('class', 'tips2');
									$("#treeUnitId").attr('class', 'tips3');
									$("#pabListId").attr('class', 'tips1');
								}
							}

							//大厦指南事件
							function fun() {

								showGuide('800', '705', _ctx
										+ '/app/guide/guide.html');
							}
						</script>
          <div class="main_img2"> <a href="javascript:void(0)" onclick="refreshAddList();"
								title="刷新" id="refresh_List"><img src="images/shipin_2.jpg"
								width="15" height="9" /></a> </div>
        </div>
        <div class="input_bg"></div>
        <div class="main_right3_more">
          <div class="main_right3_more_sz" id="peopleSet"
							style="display: none;float:left;margin-right:7px"
							onclick="showShelter('1010','410','${ctx}/app/paddressbook/init.act');"
							title="设置"></div>
          <div class="main_right3_more_fd" id="amplifyA"
							style="display: block;float:left" onclick="amplify();" title="放大"></div>
          <div class="refresh_main_right3_more" id="refresh_List"
							style="float:left" onclick="refreshAddList();" title="刷新"></div>
        </div>
        <div class="clear"></div>
      </div>
      <div class="close_search" id="close_search" title="清除"
					onclick="clear_search(this)"></div>
      <!--  <div class="search_span"><span class="num" onclick="searchInfo()" id="search"></span></div>-->
      <input type="hidden" id="showType" name="showType" />
      <div class="address_list" id="address_list" style="height:255px;">
        <div id="tree"></div>
        <div id="treeUnit" style="display: none;"></div>
        <div id="pabList" style="display: none;"></div>
        <div id="searchList" style="display: none; overflow: auto"></div>
      </div>
    </div>
  </div>
  <div class="clear"></div>
</div>
</body>
<script type="text/javascript">
	var orgIdAddress = '${orgId}';
	var typeAddress = '${type}'; //	"tel"|"fax"
	
	
	if(typeAddress == ''){
		typeAddress = 'tel';
	}
	
	
	
	var selfMacAddress = '${smac}';//	当前用户的ip电话MAC地址
	var selfTelAddress = '${stel}';//	当前用户的ip电话号码
</script>


<script type="text/javascript">
	var flag1 = true; //上下切换变量
	var num = 10; //存放根据权限所显示的功能数量
	var bottomMin = 35;
	var sizeMax = '${param.height}'; //右边框总高度
	var o, t;

	//判断当前功能有多少行
	if ((parseInt(num % 3)) != 0)
		num = parseInt(num / 3) + 1;
	else
		num = parseInt(num / 3);

	var treeStyles = document.getElementById("multFun").style; //功能栏对象
	var hei = treeStyles.height; //取得功能栏对象的高度
	var size = parseInt(sizeMax) - parseInt(72 * num); // 取得功能栏高度之外的高度
	var treeCommHeight = document.getElementById("treeComm").style; //树对象

	o = parseInt(size) - 49; //取得外框高度
	t = parseInt(size) - 49 - 92; //取得树框高度

	//当size 小于 body高度 - 功能框高度
	var treeStyle = document.getElementById("multFun").style;
	treeStyle.height = 0 + "px";
	$("#treeComm").animate({
		height : sizeMax + "px"
	});
	$("#address_list").animate({
		height : parseInt(sizeMax) - 140 + "px"
	});
	$("#treeComm_div").animate({
		height : parseInt(sizeMax) - 50 + "px"
	});
	//点击事件
	function upOrDownCheck() {
		if (flag1) {
			var treeStyle = document.getElementById("multFun").style;
			treeStyle.height = 0 + "px";
			$("#treeComm").animate({
				height : sizeMax + "px"
			});
			$("#address_list").animate({
				height : parseInt(sizeMax) - 140 + "px"
			});
			$("#treeComm_div").animate({
				height : parseInt(sizeMax) - 50 + "px"
			});
			flag1 = false;
		} else {
			var treeStyle = document.getElementById("treeComm").style;
			if (size < 35) {
				treeStyle.height = 0 + "px";
				$("#multFun").animate({
					height : sizeMax + "px"
				});
			} else {
				//treeStyle.height = sizeMax - (67 * num) + "px";
				$("#multFun").animate({
					height : 72 * num + "px"
				});
				$("#treeComm_div").animate({
					height : o + "px"
				});
				$("#address_list").animate({
					height : t + "px"
				});
			}
			flag1 = true;
		}
	}
</script>
<script type="text/javascript">
	//通讯录
	$(function() {
		$(".tips1").click();
	});

	var fn3;
	var mobilenum = "", pname = "", info = "", type = "";

	function refreshAddList() {
		var id = $("#showType").val();
		$("#" + id).children().remove();
		var tjVal = $("#searchTJ").val().replace(/(^\s*)|(\s*$)/g, "");
		var tv = new treeview("", "");
		var url = "", orgId = 0;
		if(tjVal != "请输入姓名或电话"){
	    	searchInfo();
	    }else{
	    	$("#searchList").css('display','none');
			if (id == "tree") { // 大厦
				tv.create("tree");
				$("#tree").css('display','block');
				url = "${ctx}/app/integrateservice/loadSonInfo.act?orgId=";
				loadtree(url, tv, orgId);
			} else if (id == "treeUnit") { // 部门 
				$("#treeUnit").css('display','block');
				tv.create("treeUnit");
				url = "${ctx}/app/integrateservice/loadSonInfo.act?orgId=";
				orgId = orgIdAddress;
				loadtree(url, tv, orgId);
			} else if (id == "pabList") { // 个人
				$("#pabList").css('display','block');
				tv.create("pabList");
				url = "${ctx}/app/integrateservice/loadAddressInfo.act?gId=";
				loadaddresstree(url, tv, 0);
			}
	    }
		//setTimeout("initBindTag()", 1000);
	}

	function amplify() {
		var url = "${ctx}/app/integrateservice/amplify.act"; //?showType="+$("#showType").val();
		parent.open_main3('10260', '通讯录', url);
	}

	// 加载大厦/部门树节点
	function loadtree(url, tv, orgId) {

		var firsturl = "";
		if (orgId >= 0)
			firsturl = url + orgId;
		else
			firsturl = url;

		$
				.getJSON(
						firsturl,
						function(json) {
							var len = json.sonOrgList.length;
							for ( var i = 0; i < len; i++) {
								var id = json.sonOrgList[i].ORG_ID;
								var text = json.sonOrgList[i].NAME.replace(
										/(^\s*)|(\s*$)/g, "");

								var node1 = new node(id, text, "");
								var loadnode = new node("", "请等待...", "",
										"true"); // 不能展开
								node1.add(loadnode);
								tv.add(node1);
							}

							// 加载用户
							var perlen = json.sonPersonList.length;
							for ( var i = 0; i < perlen; i++) {
								var id = json.sonPersonList[i].USERID;
								var name = json.sonPersonList[i].DISPLAY_NAME ? json.sonPersonList[i].DISPLAY_NAME
										.replace(/(^\s*)|(\s*$)/g, "")
										: "";
								var mobile = json.sonPersonList[i].UEP_MOBILE ? json.sonPersonList[i].UEP_MOBILE
										.replace(/(^\s*)|(\s*$)/g, "")
										: "";
								var tel = json.sonPersonList[i].TEL ? json.sonPersonList[i].TEL
										.replace(/(^\s*)|(\s*$)/g, "")
										: "";
								var fax = json.sonPersonList[i].UEP_FAX ? json.sonPersonList[i].UEP_FAX
										.replace(/(^\s*)|(\s*$)/g, "")
										: "";
								var web_fax = json.sonPersonList[i].WEB_FAX ? json.sonPersonList[i].WEB_FAX
										.replace(/(^\s*)|(\s*$)/g, "")
										: "";
								var unitInfo = id + "_" + name + "_" + mobile;

								var text = "<table width='100%'><tr><td width='20%'><span>"
										+ name
										+ "</span></td><td width='80%' style='color: #7d7d7d;'>", title = name;
								if (typeAddress == "tel") {
									if ($("#showType").val() == "treeUnit") {
										if (mobile != "") {
											text = text
													+ "<div><a href='#' info="
													+ unitInfo
													+ "  phoneNum="
													+ mobile
													+ "  pname="
													+ name
													+ " type='unit' onclick='dialing(&quot;"
													+ mobile + "&quot;, &quot;"
													+ name + "&quot;)'>"
													+ mobile + "</a>(手机)</div>";
											title = title + "　手机:" + mobile;
										}
									}
									if (tel != "") {
										text = text
												+ "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"
												+ tel + "&quot;, &quot;" + name
												+ "&quot;)'>" + tel
												+ "</a>(电话)</div>";
										title = title + "　电话:" + tel;
									}
									if (fax != "") {
										text = text
												+ "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"
												+ fax + "&quot;, &quot;" + name
												+ "&quot;,true)'>" + fax
												+ "</a>(传真)</div>";
										title = title + "　传真:" + fax;
									}
									if (web_fax != "") {
										text = text
												+ "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"
												+ web_fax + "&quot;, &quot;"
												+ name + "&quot;,true)'>" + web_fax
												+ "</a>(传真)</div>";
										title = title + "　网络传真:" + web_fax;
									}

								} else if (typeAddress == "fax") {
									if (fax != "") {
										text = text
												+ "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='sendFax(&quot;"
												+ name + "&quot;,&quot;" + tel
												+ "&quot;,&quot;" + fax
												+ "&quot;,true)'>" + fax
												+ "</a>(传真)</div>";
										title = title + "　传真:" + fax;
									}
									if (web_fax != "") {
										text = text
												+ "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='sendFax(&quot;"
												+ web_fax + "&quot;,&quot;"
												+ tel + "&quot;,&quot;"
												+ web_fax + "&quot;,true)'>"
												+ web_fax + "</a>(传真)</div>";
										title = title + "　网络传真:" + fax;
									}
								}
								text = text + "</td></tr></table>";

								var node1 = new node(id, text, "", "true",
										title);
								var loadnode = new node("", "请等待...", "",
										"true"); // 不能展开
								node1.add(loadnode);
								tv.add(node1);
							}
						});
	}

	// 加载大厦/部门节点信息
	function loadnode(url, Pnode) {
		var firsturl = url + Pnode.id;
		$
				.getJSON(
						firsturl,
						function(json) {
							// 加载组织
							var orglen = json.sonOrgList.length;
							for ( var i = 0; i < orglen; i++) {
								var id = json.sonOrgList[i].ORG_ID;
								var text = json.sonOrgList[i].NAME.replace(
										/(^\s*)|(\s*$)/g, "");

								var node1 = new node(id, text, "");
								node1.dom.style.display = "block";
								node1.dom.style.width = Pnode.dom.offsetWidth
										- 20 + "px";
								var loadnode = new node("", "请等待...", "");
								node1.add(loadnode);
								Pnode.add(node1);
							}
							// 加载用户
							var perlen = json.sonPersonList.length;
							for ( var i = 0; i < perlen; i++) {

								var id = json.sonPersonList[i].USERID;
								var name = json.sonPersonList[i].DISPLAY_NAME ? json.sonPersonList[i].DISPLAY_NAME
										.replace(/(^\s*)|(\s*$)/g, "")
										: "";
								var mobile = json.sonPersonList[i].UEP_MOBILE ? json.sonPersonList[i].UEP_MOBILE
										.replace(/(^\s*)|(\s*$)/g, "")
										: "";
								var tel = json.sonPersonList[i].TEL ? json.sonPersonList[i].TEL
										.replace(/(^\s*)|(\s*$)/g, "")
										: "";
								var fax = json.sonPersonList[i].UEP_FAX ? json.sonPersonList[i].UEP_FAX
										.replace(/(^\s*)|(\s*$)/g, "")
										: "";
								var web_fax = json.sonPersonList[i].WEB_FAX ? json.sonPersonList[i].WEB_FAX
										.replace(/(^\s*)|(\s*$)/g, "")
										: "";
								var unitInfo = id + "_" + name + "_" + mobile;

								var text = "<table width='100%'><tr><td width='20%'><span>"
										+ name
										+ "</span></td><td width='80%' style='color: #7d7d7d;'>", title = name;
								if (typeAddress == "tel") {
									if ($("#showType").val() == "treeUnit") {
										if (mobile != "") {
											text = text
													+ "<div style='float:left; width:125px;'><a href='javascript:void(0);' class='tooltip' info="
													+ unitInfo
													+ "  phoneNum="
													+ mobile
													+ "  pname="
													+ name
													+ " type='unit' style='text-decoration: none' onclick='dialing(&quot;"
													+ mobile + "&quot;, &quot;"
													+ name + "&quot;)'>"
													+ mobile + "</a>(手机)</div>";
											title = title + "　手机:" + mobile;
										}
									}
									if (tel != "") {
										text = text
												+ "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"
												+ tel + "&quot;, &quot;" + name
												+ "&quot;)'>" + tel
												+ "</a>(电话)</div>";
										title = title + "　电话:" + tel;
									}
									if (fax != "") {
										text = text
												+ "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"
												+ fax + "&quot;, &quot;" + name
												+ "&quot;,true)'>" + fax
												+ "</a>(传真)</div>";
										title = title + "　传真:" + fax;
									}
									if (web_fax != "") {
										text = text
												+ "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"
												+ web_fax + "&quot;, &quot;"
												+ name + "&quot;,true)'>" + web_fax
												+ "</a>(传真)</div>";
										title = title + "　网络传真:" + web_fax;
									}

								} else if (typeAddress == "fax") {
									if (fax != "") {
										text = text
												+ "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='sendFax(&quot;"
												+ name + "&quot;,&quot;" + tel
												+ "&quot;,&quot;" + fax
												+ "&quot;,true)'>" + fax
												+ "</a>(传真)</div>";
										title = title + "　传真:" + fax;
									}
									if (web_fax != "") {
										text = text
												+ "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='sendFax(&quot;"
												+ web_fax + "&quot;,&quot;"
												+ tel + "&quot;,&quot;"
												+ web_fax + "&quot;,true)'>"
												+ web_fax + "</a>(传真)</div>";
										title = title + "　网络传真:" + fax;
									}
								}
								text = text + "</td></tr></table>";
								var node1 = new node(id, text, "", "true",
										title);
								node1.dom.style.display = "block";
								node1.dom.style.width = Pnode.dom.offsetWidth
										- 20 + "px";
								Pnode.add(node1);
							}
							Pnode.childNode[0].removeNode();
							// 没有子节点，则移除展开图标以及单击和双击事件
							if (orglen == 0 && perlen == 0) {
								Pnode.SetSingle();// 设置该节点没有子节点
							}
						});
	}

	// 加载个人通讯录树节点
	function loadaddresstree(url, tv, gId) {
		var firsturl = "";
		if (gId >= 0)
			firsturl = url + gId;
		else
			firsturl = url;

		$
				.getJSON(
						firsturl,
						function(json) {
							var len = json.groupList.length;
							for ( var i = 0; i < len; i++) {
								var id = json.groupList[i].NAG_ID;
								var text = json.groupList[i].NAG_NAME.replace(
										/(^\s*)|(\s*$)/g, "");

								var node1 = new node(id, text, "");
								var loadnode = new node("", "请等待...", "",
										"true");// 不能展开
								node1.add(loadnode);
								tv.add(node1);
							}

							// 加载组内人员
							var orglen = json.personList.length;
							for ( var i = 0; i < orglen; i++) {
								var id = json.personList[i].NUA_ID;
								var name = json.personList[i].NUA_NAME.replace(
										/(^\s*)|(\s*$)/g, "");
								var phone = json.personList[i].NUA_PHONE ? json.personList[i].NUA_PHONE
										.replace(/(^\s*)|(\s*$)/g, "")
										: "";
								var telHome = json.personList[i].NUA_TEL1 ? json.personList[i].NUA_TEL1
										.replace(/(^\s*)|(\s*$)/g, "")
										: "";
								var telOffice = json.personList[i].NUA_TEL2 ? json.personList[i].NUA_TEL2
										.replace(/(^\s*)|(\s*$)/g, "")
										: "";
								var telOther = json.personList[i].NUA_TEL3 ? json.personList[i].NUA_TEL3
										.replace(/(^\s*)|(\s*$)/g, "")
										: "";
								var addressInfo = "";

								var text = "<table width='100%'><tr><td width='20%'><span>"
										+ name
										+ "</span></td><td width='80%' style='color: #7d7d7d;'>", title = name;
								if (phone != "") { // 多电话循环
									if (phone.indexOf(',') > 0) {
										var phoneList = phone.split(',');
										for ( var j = 0; j < phoneList.length; j++) {
											addressInfo = id + "-"
													+ phoneList[j] + "_" + name
													+ "_" + phoneList[j];
											text = text
													+ "<div style='float:left; width:125px;'><a href='javascript:void(0);' class='tooltip' phoneNum="
													+ phoneList[j]
													+ " info="
													+ addressInfo
													+ "  pname="
													+ name
													+ " type='address' style='text-decoration: none' onclick='dialing(&quot;"
													+ phoneList[j]
													+ "&quot;, &quot;" + name
													+ "&quot;)'>"
													+ phoneList[j]
													+ "</a>(手机)</div>";
											title = title + "　手机:"
													+ phoneList[j];
										}
									} else {
										addressInfo = id + "_" + name + "_"
												+ phone;
										text = text
												+ "<div style='float:left; width:125px;'><a href='javascript:void(0);'  class='tooltip' phoneNum="
												+ phone
												+ " info="
												+ addressInfo
												+ "  pname="
												+ name
												+ " type='address' style='text-decoration: none' onclick='dialing(&quot;"
												+ phone + "&quot;, &quot;"
												+ name + "&quot;)'>" + phone
												+ "</a>(手机)</div>";
										title = title + "　手机:" + phone;
									}
								}
								if (telHome != "") {
									if (telHome.indexOf(',') > 0) {
										var telHomeList = telHome.split(',');
										for ( var j = 0; j < telHomeList.length; j++) {
											text = text
													+ "<div style='float:left; width:125px;'><a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"
													+ telHomeList[j]
													+ "&quot;, &quot;" + name
													+ "&quot;)'>"
													+ telHomeList[j]
													+ "</a>(住宅)</div>";
											title = title + "　住宅:"
													+ telHomeList[j];
										}
									} else {
										text = text
												+ "<div style='float:left; width:125px;'><a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"
												+ telHome + "&quot;, &quot;"
												+ name + "&quot;)'>" + telHome
												+ "</a>(住宅)</div>";
										title = title + "　住宅:" + telHome;
									}
								}
								if (telOffice != "") {
									if (telOffice.indexOf(',') > 0) {
										var telOfficeList = telOffice
												.split(',');
										for ( var j = 0; j < telOfficeList.length; j++) {
											text = text
													+ "<div style='float:left; width:125px;'><a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"
													+ telOfficeList[j]
													+ "&quot;, &quot;" + name
													+ "&quot;)'>"
													+ telOfficeList[j]
													+ "</a>(办公)</div>";
											title = title + "　办公:"
													+ telOfficeList[j];
										}
									} else {
										text = text
												+ "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"
												+ telOffice + "&quot;, &quot;"
												+ name + "&quot;)'>"
												+ telOffice + "</a>(办公)</div>";
										title = title + "　办公:" + telOffice;
									}
								}
								if (telOther != "") {
									if (telOther.indexOf(',') > 0) {
										var telOtherList = telOther.split(',');
										for ( var j = 0; j < telOtherList.length; j++) {
											text = text
													+ "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"
													+ telOtherList[j]
													+ "&quot;, &quot;" + name
													+ "&quot;)'>"
													+ telOtherList[j]
													+ "</a>(其他)</div>";
											title = title + "　其他:"
													+ telOtherList[j];
										}
									} else {
										text = text
												+ "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"
												+ telOther + "&quot;, &quot;"
												+ name + "&quot;)'>" + telOther
												+ "</a>(其他)</div>";
										title = title + "　其他:" + telOther;
									}
								}
								text = text + "</td></tr></table>";

								var node1 = new node(id, text, "", "true",
										title);
								var loadnode = new node("", "请等待...", "",
										"true"); // 不能展开
								node1.add(loadnode);
								tv.add(node1);
							}
						});
	}

	// 加载个人通讯录节点信息
	function loadaddressnode(url, Pnode) {
		var firsturl = url + Pnode.id;
		$
				.getJSON(
						firsturl,
						function(json) {
							// 加载组内人员
							var orglen = json.personList.length;
							for ( var i = 0; i < orglen; i++) {
								var id = json.personList[i].NUA_ID;
								var name = json.personList[i].NUA_NAME.replace(
										/(^\s*)|(\s*$)/g, "");
								var phone = json.personList[i].NUA_PHONE ? json.personList[i].NUA_PHONE
										.replace(/(^\s*)|(\s*$)/g, "")
										: "";
								var telHome = json.personList[i].NUA_TEL1 ? json.personList[i].NUA_TEL1
										.replace(/(^\s*)|(\s*$)/g, "")
										: "";
								var telOffice = json.personList[i].NUA_TEL2 ? json.personList[i].NUA_TEL2
										.replace(/(^\s*)|(\s*$)/g, "")
										: "";
								var telOther = json.personList[i].NUA_TEL3 ? json.personList[i].NUA_TEL3
										.replace(/(^\s*)|(\s*$)/g, "")
										: "";
								var addressInfo = "";
								var text = "<table width='100%'><tr><td width='20%'><span>"
										+ name
										+ "</span></td><td width='80%' style='color: #7d7d7d;'>", title = name;
								if (phone != "") { // 多电话循环
									if (phone.indexOf(',') > 0) {
										var phoneList = phone.split(',');
										for ( var j = 0; j < phoneList.length; j++) {
											addressInfo = id + "-"
													+ phoneList[j] + "_" + name
													+ "_" + phoneList[j];
											text = text
													+ "<div style='float:left; width:125px;'><a href='javascript:void(0);' class='tooltip' phoneNum="
													+ phoneList[j]
													+ " info="
													+ addressInfo
													+ "  pname="
													+ name
													+ " type='address' style='text-decoration: none' onclick='dialing(&quot;"
													+ phoneList[j]
													+ "&quot;, &quot;" + name
													+ "&quot;)'>"
													+ phoneList[j]
													+ "</a>(手机)</div>";
											title = title + "　手机:"
													+ phoneList[j];
										}
									} else {
										addressInfo = id + "_" + name + "_"
												+ phone;
										text = text
												+ "<div style='float:left; width:125px;'><a href='javascript:void(0);'  class='tooltip' phoneNum="
												+ phone
												+ " info="
												+ addressInfo
												+ "  pname="
												+ name
												+ " type='address'  style='text-decoration: none' onclick='dialing(&quot;"
												+ phone + "&quot;, &quot;"
												+ name + "&quot;)'>" + phone
												+ "</a>(手机)</div>";
										title = title + "　手机:" + phone;
									}
								}
								if (telHome != "") {
									if (telHome.indexOf(',') > 0) {
										var telHomeList = telHome.split(',');
										for ( var j = 0; j < telHomeList.length; j++) {
											text = text
													+ "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"
													+ telHomeList[j]
													+ "&quot;, &quot;" + name
													+ "&quot;)'>"
													+ telHomeList[j]
													+ "</a>(住宅)</div>";
											title = title + "　住宅:"
													+ telHomeList[j];
										}
									} else {
										text = text
												+ "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"
												+ telHome + "&quot;, &quot;"
												+ name + "&quot;)'>" + telHome
												+ "</a>(住宅)</div>";
										title = title + "　住宅:" + telHome;
									}
								}
								if (telOffice != "") {
									if (telOffice.indexOf(',') > 0) {
										var telOfficeList = telOffice
												.split(',');
										for ( var j = 0; j < telOfficeList.length; j++) {
											text = text
													+ "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"
													+ telOfficeList[j]
													+ "&quot;, &quot;" + name
													+ "&quot;)'>"
													+ telOfficeList[j]
													+ "</a>(办公)</div>";
											title = title + "　办公:"
													+ telOfficeList[j];
										}
									} else {
										text = text
												+ "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"
												+ telOffice + "&quot;, &quot;"
												+ name + "&quot;)'>"
												+ telOffice + "</a>(办公)</div>";
										title = title + "　办公:" + telOffice;
									}
								}
								if (telOther != "") {
									if (telOther.indexOf(',') > 0) {
										var telOtherList = telOther.split(',');
										for ( var j = 0; j < telOtherList.length; j++) {
											text = text
													+ "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"
													+ telOtherList[j]
													+ "&quot;, &quot;" + name
													+ "&quot;)'>"
													+ telOtherList[j]
													+ "</a>(其他)</div>";
											title = title + "　其他:"
													+ telOtherList[j];
										}
									} else {
										text = text
												+ "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"
												+ telOther + "&quot;, &quot;"
												+ name + "&quot;)'>" + telOther
												+ "</a>(其他)</div>";
										title = title + "　其他:" + telOther;
									}
								}
								text = text + "</td></tr></table>";

								var node1 = new node(id, text, "", "true",
										title);
								node1.dom.style.display = "block";
								node1.dom.style.width = Pnode.dom.offsetWidth
										- 20 + "px";
								var loadnode = new node("", "请等待...", "",
										"true");
								node1.add(loadnode);
								Pnode.add(node1);
							}
							Pnode.childNode[0].removeNode();
							// 没有子节点，则移除展开图标以及单击和双击事件
							if (orglen == 0) {
								Pnode.SetSingle();// 设置该节点没有子节点
							}
						});
	}

	function dialing(called, pname,isFax) {
		//window.parent.document.getElementById("fo_to").value += called.replace("-u", "") + ";";
		//判断是否是传真、手机、座机
		
		if (isFax){
			
			//var url = _ctx+"/app/integrateservice/inputCallPhone.act?selfMac="+selfMacAddress+"&selfTel="+selfTelAddress+"&called="+called+"&pname="+pname;
			//openEasyWinNotResizable("winId","拨号确认",url,"400","170",true);		
			//showShelter('400','170',url);
			window.parent.document.getElementById("fo_to").value += called + ";";
			
		}
		/* var url = "${ctx}/app/integrateservice/inputCallPhone.act?selfMac="
				+ selfMacAddress + "&selfTel=" + selfTelAddress + "&called="
				+ called + "&pname=" + pname;
		openEasyWinNotResizable("winId", "拨号确认", url, "400", "170", true); */
	}

	function sendMsg(info, name, type) {
		//showShelter('350','600','${ctx}/app/messagingplatform/messagingPlatformInit.act?mobile='+called+"&name="+name);
		var url = "${ctx}/app/sendsms/inputSMS.act?info=" + info + "&name="
				+ name + "&type=" + type;
		openEasyWin("winId", "发送短信", url, "650", "400", true);
	}

	function callback(node) {
		// 子节点对象集合
		if (node.childNode.length == 1 && node.childNode[0].text == "请等待...") {
			if (document.getElementById("pabList").style.display != "none") {
				var url = "${ctx}/app/integrateservice/loadAddressInfo.act?gId=";
				loadaddressnode(url, node);
			} else {
				var url = "${ctx}/app/integrateservice/loadSonInfo.act?orgId=";
				loadnode(url, node);
			}
		}
		//setTimeout("initBindTag()", 2000);
	}

	$(document).keyup(function(event) {
		if (event.keyCode == 13) {
			$("#search").trigger("click");
		}
	});

	function searchInfo() {
		var showType = $("#showType").val();
		var tjVal = $("#searchTJ").val().replace(/(^\s*)|(\s*$)/g, "");
		if (tjVal == "" || tjVal == "请输入姓名或电话") {
			return;
		} else {
			$("#address_list").children("div").hide();
			$("#searchList").show();
			$("#close_search").show();
			$("#searchList").children().remove();
			var url = "", orgId = 0;

			if (showType == "pabList")
				url = "${ctx}/app/integrateservice/queryAddress.act"; // 个人
			else if (showType == "treeUnit") {
				url = "${ctx}/app/integrateservice/queryContact.act"; // 部门
				orgId = orgIdAddress;
			} else if (showType == "tree") {
				url = "${ctx}/app/integrateservice/queryContact.act"; // 大厦
			}

			$
					.ajax({
						type : "POST",
						url : url,
						data : {
							"tjVal" : tjVal,
							"type" : showType,
							"orgId" : orgId
						},
						dataType : 'json',
						async : false,
						success : function(json) {
							if (json.length > 0) {
								$("#searchList")
										.append(
												"<table style='margin-left:5px; width:500px;' cellspacing='20' id='contact_tab'></table>");
								if (showType == "pabList") {
									$("#contact_tab")
											.append(
													"<tr><td>姓名</td><td>手机</td><td>办公</td><td>住宅</td></tr>");
								} else if (showType == "treeUnit") {
									$("#contact_tab")
											.append(
													"<tr><td>单位</td><td>姓名</td><td>电话</td><td>传真</td><td>网络传真</td><td>手机</td></tr>");
								} else if (showType == "tree") {
									$("#contact_tab")
											.append(
													"<tr><td>单位</td><td>姓名</td><td>电话</td><td>传真</td><td>网络传真</td></tr>");
								}

								var name, num1, num2, num3, num2web, _info;
								$
										.each(
												json,
												function(i) {
													var text1 = "", text2 = "", text3 = "", text4 = "";
													if (showType == "pabList") {
														name = json[i].NUA_NAME ? json[i].NUA_NAME
																.replace(
																		/(^\s*)|(\s*$)/g,
																		"")
																: "";

														num1 = json[i].NUA_PHONE ? json[i].NUA_PHONE
																.replace(
																		/(^\s*)|(\s*$)/g,
																		"")
																: "";
														if (num1.indexOf(',') > 0)
															text1 = num1
																	.substring(
																			0,
																			11)
																	+ "...";
														else {
															_info = json[i].NUA_ID
																	+ "_"
																	+ name
																	+ "_"
																	+ num1;
															text1 = "<a href='javascript:void(0);'  class='tooltip'  phoneNum="
																	+ num1
																	+ " info="
																	+ _info
																	+ " type='address'  pname="
																	+ name
																	+ " style='text-decoration: none' onclick='dialing(&quot;"
																	+ num1
																	+ "&quot;, &quot;"
																	+ name
																	+ "&quot;)'>"
																	+ num1
																	+ "</a>";
														}

														num2 = json[i].NUA_TEL2 ? json[i].NUA_TEL2
																.replace(
																		/(^\s*)|(\s*$)/g,
																		"")
																: "";
														if (num2.indexOf(',') > 0)
															text2 = num2
																	.substring(
																			0,
																			11)
																	+ "...";
														else
															text2 = "<a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"
																	+ num2
																	+ "&quot;, &quot;"
																	+ name
																	+ "&quot;)'>"
																	+ num2
																	+ "</a>";

														num3 = json[i].NUA_TEL1 ? json[i].NUA_TEL1
																.replace(
																		/(^\s*)|(\s*$)/g,
																		"")
																: "";
														if (num3.indexOf(',') > 0)
															text3 = num3
																	.substring(
																			0,
																			11)
																	+ "...";
														else
															text3 = "<a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"
																	+ num3
																	+ "&quot;, &quot;"
																	+ name
																	+ "&quot;)'>"
																	+ num3
																	+ "</a>";

														//num4 = json[i].NUA_TEL3 ? json[i].NUA_TEL3.replace(/(^\s*)|(\s*$)/g, "") : "";
														//if (num4.indexOf(',') > 0)	num4 = num4.substring(0,11)+"...";

														$("#contact_tab")
																.append(
																		"<tr><td>"
																				+ name
																				+ "</td><td>"
																				+ text1
																				+ "</td><td>"
																				+ text2
																				+ "</td><td>"
																				+ text3
																				+ "</td></tr>");
													} else if (showType == "treeUnit") {
														unitname = json[i].DEPARTMENT_NAME ? json[i].DEPARTMENT_NAME
																.replace(
																		/(^\s*)|(\s*$)/g,
																		"")
																: ""; // 本单位下的部门名称
														name = json[i].DISPLAY_NAME ? json[i].DISPLAY_NAME  //显示名称
																.replace(
																		/(^\s*)|(\s*$)/g,
																		"")
																: "";
														num1 = json[i].TEL ? json[i].TEL    //电话
																.replace(
																		/(^\s*)|(\s*$)/g,
																		"")
																: "";
														num2 = json[i].UEP_FAX ? json[i].UEP_FAX  //传真
																.replace(
																		/(^\s*)|(\s*$)/g,
																		"")
																: "";
														num2web = json[i].WEB_FAX ? json[i].WEB_FAX  //网络传真
																.replace(
																		/(^\s*)|(\s*$)/g,
																		"")
																: "";
														num3 = json[i].UEP_MOBILE ? json[i].UEP_MOBILE   //手机
																.replace(
																		/(^\s*)|(\s*$)/g,
																		"")
																: "";
														_info = json[i].NUA_ID
																+ "_" + name
																+ "_" + num3;

														text1 = "<a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"
																+ num1
																+ "&quot;, &quot;"
																+ name
																+ "&quot;)'>"
																+ num1 + "</a>";
														text2 = "<a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"
																+ num2
																+ "&quot;, &quot;"
																+ name
																+ "&type=fax"
																+ "&quot;)'>"
																+ num2 + "</a>";
														text4 = "<a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"
																+ num2web
																+ "&quot;, &quot;"
																+ name
																+ "&type=webFax"
																+ "&quot;)'>"
																+ num2web
																+ "</a>";
														text3 = "<a href='javascript:void(0);'  class='tooltip' phoneNum="   //手机拨打
																+ num3
																+ "  info="
																+ _info
																+ " type='unit'  pname="
																+ name
																+ " style='text-decoration: none' onclick='dialing(&quot;"
																+ num3
																+ "&quot;, &quot;"
																+ name
																+ "&quot;)'>"
																+ num3 + "</a>";

														$("#contact_tab")
																.append(
																		"<tr><td>"
																				+ unitname
																				+ "</td><td>"
																				+ name
																				+ "</td><td>"
																				+ text1
																				+ "</td><td>"
																				+ text2
																				+ "</td><td>"
																				+ text4
																				+ "</td><td>"
																				+ text3
																				+ "</td></tr>");
													} else if (showType == "tree") {
														unitname = json[i].ORG_NAME ? json[i].ORG_NAME
																.replace(
																		/(^\s*)|(\s*$)/g,
																		"")
																: ""; // 一级单位
														name = json[i].DISPLAY_NAME ? json[i].DISPLAY_NAME
																.replace(
																		/(^\s*)|(\s*$)/g,
																		"")
																: "";
														num1 = json[i].TEL ? json[i].TEL
																.replace(
																		/(^\s*)|(\s*$)/g,
																		"")
																: "";
														num2 = json[i].UEP_FAX ? json[i].UEP_FAX
																.replace(
																		/(^\s*)|(\s*$)/g,
																		"")
																: "";
														num2web = json[i].WEB_FAX ? json[i].WEB_FAX
																.replace(
																		/(^\s*)|(\s*$)/g,
																		"")
																: "";

														text1 = "<a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"
																+ num1
																+ "&quot;, &quot;"
																+ name
																+ "&quot;)'>"
																+ num1 + "</a>";
														text2 = "<a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"
																+ num2
																+ "&quot;, &quot;"
																+ name
																+ "&quot;)'>"
																+ num2
																+ "</a>";
														text4 = "<a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"
																+ num2web
																+ "&quot;, &quot;"
																+ name
																+ "&quot;)'>"
																+ num2web
																+ "</a>";

														$("#contact_tab")
																.append(
																		"<tr><td>"
																				+ unitname
																				+ "</td><td>"
																				+ name
																				+ "</td><td>"
																				+ text1
																				+ "</td><td>"
																				+ text2
																				+ "</td><td>"
																				+ text4
																				+ "</td></tr>");
													}
												});
							}
						},
						error : function(msg, status, e) {
							easyAlert("提示信息", "操作出错", "info");
						}
					});
			//setTimeout("initBindTag()", 1000);
		}
	}

	// 查询输入框
	function inputFun(dom, num) {
		if (num == 0)
			dom.value = "";
		if (num == 1 && dom.value == "")
			dom.value = "请输入姓名或电话";
	}

	// 清除按钮
	function clear_search(dom) {
		var st = $("#showType").val(); // 退出显示最后一次显示的标签页
		$("#" + st).show();
		document.getElementById("searchList").style.display = "none";
		document.getElementById("searchTJ").value = "请输入姓名或电话";
		dom.style.display = "none";
	}

	function tipsChange(dom, id) {
		//alert(dom.className);
		dom.parentNode.className = 'reportTips_' + dom.className;
		if (dom.className == "tips3") {
			document.getElementById("peopleSet").style.display = "block";
		} else {
			document.getElementById("peopleSet").style.display = "none";
		}

		$("#address_list").children("div").hide();
		$("#" + id).show();
		//$("#searchType").val(type);
		$("#showType").val(id);

		// 加载数据
		var tv = new treeview("", "");
		var url = "", orgId = 0;
		if (id == "tree" && document.getElementById("tree").innerHTML == "") { // 大厦
			tv.create("tree");
			url = "${ctx}/app/integrateservice/loadSonInfo.act?orgId=";
			loadtree(url, tv, orgId);
		} else if (id == "treeUnit"
				&& document.getElementById("treeUnit").innerHTML == "") { // 部门
			tv.create("treeUnit");
			url = "${ctx}/app/integrateservice/loadSonInfo.act?orgId=";
			orgId = orgIdAddress;
			loadtree(url, tv, orgId);
		} else if (id == "pabList"
				&& document.getElementById("pabList").innerHTML == "") { // 个人
			tv.create("pabList");
			url = "${ctx}/app/integrateservice/loadAddressInfo.act?gId=";
			loadaddresstree(url, tv, 0);
		}
	}

	function controllerDevice(idPrefix, deviceType, optType) {
		var temperature = $("#conditioner_temperature").html();
		var coolId = $("#conditioner_temperature").attr("coolId");
		$.ajax({
			type : "POST",
			url : "${ctx}/app/integrateservice/controllerDevice.act",
			data : {
				"deviceType" : deviceType,
				"optType" : optType,
				"temperature" : temperature,
				"coolId" : coolId
			},
			dataType : 'text',
			async : false,
			success : function(msg) {
				if (msg == "success") {
					if (deviceType == "door") {
						$("#" + idPrefix + "_on").hide();
						$("#" + idPrefix + "_off").show();
						setTimeout("close_door()", 1000); // 开门1秒之后关门
					} else if (deviceType == "conditioner") {
						if (optType == "add") {
							temperature = parseFloat(temperature) + 0.5;
						} else {
							temperature = parseFloat(temperature) - 0.5;
						}
						$("#conditioner_temperature").html(temperature);
					} else {
						if (optType == "on") {
							$("#" + idPrefix + "_on").hide();
							$("#" + idPrefix + "_off").show();
						} else {
							$("#" + idPrefix + "_on").show();
							$("#" + idPrefix + "_off").hide();
						}
					}
				} else
					easyAlert("提示信息", "操作失败", "info");
			},
			error : function(msg, status, e) {
				easyAlert("提示信息", "操作出错", "info");
			}
		});
	}

	function logout() {
		var url = "${ctx}/j_spring_security_logout" + '?t=' + Math.random();
		window.open(url, "_self");
	}
	
	function dialing(called, pname) {
		var url = _ctx+"/app/integrateservice/inputCallPhone.act?selfMac="+selfMacAddress+"&selfTel="+selfTelAddress+"&called="+called+"&pname="+pname;
		//openEasyWinNotResizable("winId","拨号确认",url,"400","170",true);		
		showShelter('400','170',url);
	}
</script>
</html>