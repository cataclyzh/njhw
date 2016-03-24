<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%
	String orgId = request.getParameter("orgId");
	String type = request.getParameter("type");				//	"tel"|"fax"
	String selfMac = request.getParameter("smac");	//	当前用户的ip电话MAC地址
	String selfTel = request.getParameter("stel");	//	当前用户的ip电话号码
%>
<%--
- Author: zh
- Date: 2013-4-18
- Description: 公共通讯录
--%>
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
	<title>公共通讯录</title>
	<%@ include file="/common/include/meta.jsp" %>
	
	<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript" ></script>
	<script src="${ctx}/app/integrateservice/js/util.js" type="text/javascript"></script>
	<script src="${ctx}/app/integrateservice/js/newtree.js" type="text/javascript"></script>
	<link href="${ctx}/app/integrateservice/css/newtree.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/app/integrateservice/css/addresslist.css" type="text/css" rel="stylesheet"  />
	<link href="${ctx}/app/integrateservice/css/toolbar.css" type="text/css" rel="stylesheet"  />
</head>
  
<body>
	<div class="main_right3">
		<div class="reportTips_tips1">
            <div class="tips1" onclick="tipsChange(this, 'tree')"></div>
            <div class="tips2" onclick="tipsChange(this, 'treeUnit')"></div>
            <div class="tips3" onclick="tipsChange(this, 'pabList')"></div>
        </div>
        <div class="input_bg"></div>
		<div class="main_right3_more">
			<div class="main_right3_more_sz" id="peopleSet" style="display: none;float:left;margin-right:7px" onclick="showShelter('1010','410','${ctx}/app/paddressbook/init.act');" title="设置"></div>
			<div class="main_right3_more_fd" id="amplifyA" style="display: block;float:left" onclick="amplify();" title="放大"></div>
			<div class="refresh_main_right3_more" id="refresh_List" style="float:left" onclick="refreshAddList();" title="刷新"></div>
		</div>
		<div class="main_right3_input">
			<input type="text" id="searchTJ" onfocus="inputFun(this, 0)" onblur="inputFun(this, 1)" value="请输入姓名或电话" style="color:#878885;border:none;"/>
		</div>
        <div class="close_search" id="close_search" title="清除" onclick="clear_search(this)"></div>
		<div class="search_span"><span class="num" onclick="searchInfo()">查&nbsp找</span></div>
<!--		<input type="hidden" id="searchType" name="searchType"/>-->
		<input type="hidden" id="showType" name="showType"/>
		<div class="address_list" id="address_list">
			<div id="tree"></div>
			<div id="treeUnit" style="display: none;"></div>
			<div id="pabList" style="display: none;"></div>
			<div id="searchList" style="display: none; overflow: auto"></div>
		</div>
	</div>
</body>
  
<script type="text/javascript">

	$(function() {
		$(".tips1").click();
	});
	
	var TOPSHOW = 0;	// 判断提示框是否显示
	var fn;				// 定时器全局变量
	var fn2;
	var fn3;
	var mobilenum = "", pname="", info = "", type="";
	
	function initBindTag(){
	    var x = 10, y = 20;
		$("a.tooltip").mouseover(function(e){
		
			mobilenum = $(this).attr("phoneNum");
			pname = $(this).attr("pname");
			info = $(this).attr("info");
			type = $(this).attr("type");
			
	        if($('#tooltip')){
	            $('#tooltip').remove();
	            $('#bgdiv').remove();
	            clearTimeout(fn)
	        }
	       	//this.myTitle = this.title;
			//this.title = "";
	        var DomLeft = mousePos(e).x;
       		var DomTop = mousePos(e).y;
		    var tooltip = "<div id='tooltip'><div id='sendPhone' class='sendPhone' title='打电话'></div><div id='sendMessage' class='sendMessage' title='发短信'></div><\/div>"; //创建 div 元素
	        var bgDiv = "<div class='bgdiv' id='bgdiv'></div>";
			$("body").append(tooltip);	//把它追加到文档中
	        $("body").append(bgDiv);	//把它追加到文档中
			$("#tooltip").css({"top": (DomTop - 50) + "px","left": (DomLeft-30) + "px" //设置x坐标和y坐标，并且显示
			}).show("fast").mouseover(function(){
	                TOPSHOW = 1;
	        });
	        $("#bgdiv").css({"top": (DomTop - 60) + "px","left": (DomLeft - 55) + "px" //设置x坐标和y坐标，并且显示
            }).mouseover(function(){
                $('#tooltip').remove();
                $('#bgdiv').remove();
                TOPSHOW = 0;
            });
            
	        $("#sendPhone").mouseover(function(obj){
	            TOPSHOW = 1;
	            this.className = this.className + "_hover";
	            $("#bgdiv").show();
	            //clearTimeout(fn2);
	            //mobilenum = $(this).attr("phoneNum");
	        }).mouseout(function(){
	            TOPSHOW = 0;
	            this.className = this.className.replace("_hover", "");
	        }).click(function() {
	        	//alert("拨打"+mobilenum+"调用拨号功能");
	        	dialing(mobilenum);
	        });
	        
	        $("#sendMessage").mouseover(function(){
	            TOPSHOW = 1;
	            this.className = this.className + "_hover";
	            $("#bgdiv").show();
	            //clearTimeout(fn2);
	        }).mouseout(function(){
	            TOPSHOW = 0;
	            this.className = this.className.replace("_hover", "");
	        }).click(function() {
	        	//alert("调用"+mobilenum+"短信发送功能");
	        	if (mobilenum == "") {
					alert(pname+" 无手机号!");
	        	} else {
	        		sendMsg(info, pname, type);
	        	}
	        });
	        
	    }).mouseout(function(){
	        fn = setTimeout("if(TOPSHOW == 0) {$('#tooltip').remove();$('#bgdiv').remove();}", 1000)//定时器，一秒后如果未点击，自动消失
	    });
	}
	
	function mousePos(e){
		var x,y;
		var e = e||window.event;
		return {
			x:e.clientX+document.body.scrollLeft+document.documentElement.scrollLeft,
			y:e.clientY+document.body.scrollTop+document.documentElement.scrollTop
		};
	};
	
	function refreshAddList() {
		var id = $("#showType").val();
		$("#"+id).children().remove();
		
        var tv = new treeview("","");
        var url = "", orgId = 0;
        if (id == "tree" ) {				// 大厦
	        tv.create("tree");
	        url = "${ctx}/app/integrateservice/loadSonInfo.act?orgId=";
	        loadtree(url, tv, orgId);
        } else if (id == "treeUnit") { 		// 部门 
        	tv.create("treeUnit");
	        url = "${ctx}/app/integrateservice/loadSonInfo.act?orgId=";
	        orgId = '<%=orgId%>';
	        loadtree(url, tv, orgId);
        } else if (id == "pabList") {		// 个人
        	tv.create("pabList");
	        url = "${ctx}/app/integrateservice/loadAddressInfo.act?gId=";
	        loadaddresstree(url, tv, 0);
        }
        
        setTimeout("initBindTag()", 1000);
	}
	
	function amplify() {
		var url = "${ctx}/app/integrateservice/amplify.act";	//?showType="+$("#showType").val();
		parent.open_main3('10260' ,'通讯录' ,url);
	}
  	
    // 加载大厦/部门树节点
    function loadtree(url, tv, orgId) {
    	var firsturl = "";
    	if (orgId >= 0) firsturl = url + orgId;
    	else firsturl = url;
    	
        $.getJSON(firsturl, function(json){
	        var len = json.sonOrgList.length;
	        for(var i = 0; i < len; i++) {
	            var id = json.sonOrgList[i].ORG_ID;
	            var text = json.sonOrgList[i].NAME.replace(/(^\s*)|(\s*$)/g, "");
	            
	            var node1 = new node(id, text, "");
	            var loadnode = new node("", "请等待...", "", "true");	// 不能展开
	            node1.add(loadnode);
	            tv.add(node1);
	        }
	        
	        // 加载用户
	        var perlen = json.sonPersonList.length;
	        for(var i = 0; i < perlen; i++) {
	            var id = json.sonPersonList[i].USERID;
	            var name = json.sonPersonList[i].DISPLAY_NAME ? json.sonPersonList[i].DISPLAY_NAME.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var mobile = json.sonPersonList[i].UEP_MOBILE ? json.sonPersonList[i].UEP_MOBILE.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var tel = json.sonPersonList[i].TEL ? json.sonPersonList[i].TEL.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var fax = json.sonPersonList[i].UEP_FAX ? json.sonPersonList[i].UEP_FAX.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var web_fax = json.sonPersonList[i].WEB_FAX ? json.sonPersonList[i].WEB_FAX.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var unitInfo = id+"_"+name+"_"+mobile;
	            
	            var text = "<table width='100%'><tr><td width='20%'><span>"+name+"</span></td><td width='80%' style='color: #7d7d7d;'>", title=name;
	            if ('<%=type%>' == "tel") {
		           if ($("#showType").val() == "treeUnit") {
	            		if (mobile != "") {
			           		text = text + "<a href='javascript:void(0);'  class='tooltip' info="+unitInfo+"  phoneNum="+mobile+"  pname="+name+" type='unit' style='text-decoration: none' onclick='dialing(&quot;"+mobile+"&quot;)'>"+mobile+"</a>(手机)　　";
							title = title + "　手机:" + mobile;
			            }
	            	}
		            if (tel != "") {
		           		text = text + "<a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+tel+"&quot;)'>"+tel+"</a>(电话)　　";
						title = title + "　电话:" + tel;
		            }
		            if (fax != "") {
		           		text = text + "<a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+fax+"&quot;)'>"+fax+"</a>(传真)　　";
						title = title + "　传真:" + fax;
		            }
		            if (web_fax != "") {
		           		text = text + "<a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+web_fax+"&quot;)'>"+web_fax+"</a>(传真)</td></tr></table>";
						title = title + "　网络传真:" + web_fax;
		            }

	            } else if ('<%=type%>' == "fax") {
	            	if (fax != "") {
		           		text = text + "<a href='javascript:void(0);' style='text-decoration: none' onclick='sendFax(&quot;"+name+"&quot;,&quot;"+tel+"&quot;,&quot;"+fax+"&quot;)'>"+fax+"</a>(传真)";
		           		title = title + "　传真:" + fax;
		            }
		            if (web_fax != "") {
		           		text = text + "<a href='javascript:void(0);' style='text-decoration: none' onclick='sendFax(&quot;"+web_fax+"&quot;,&quot;"+tel+"&quot;,&quot;"+web_fax+"&quot;)'>"+web_fax+"</a>(传真)";
		           		title = title + "　网络传真:" + fax;
		            }
	            }
	            
	            var node1 = new node(id, text, "", "true", title);
	            var loadnode = new node("", "请等待...", "", "true");	// 不能展开
	            node1.add(loadnode);
	            tv.add(node1);
	        }
        });
    }
    
    // 加载大厦/部门节点信息
    function loadnode(url, Pnode) {
     	var firsturl = url + Pnode.id;
	    $.getJSON(firsturl, function(json){
	        // 加载组织
	        var orglen = json.sonOrgList.length;
	        for(var i = 0; i < orglen; i++) {
	            var id = json.sonOrgList[i].ORG_ID;
	            var text = json.sonOrgList[i].NAME.replace(/(^\s*)|(\s*$)/g, "");
	            
	            var node1 = new node(id, text, "");
	            node1.dom.style.display = "block";
	            node1.dom.style.width = Pnode.dom.offsetWidth - 20 + "px";
	            var loadnode = new node("", "请等待...", "");
	            node1.add(loadnode);
	            Pnode.add(node1);
	        }
	        // 加载用户
	        var perlen = json.sonPersonList.length;
	        for(var i = 0; i < perlen; i++) {
	        
	            var id = json.sonPersonList[i].USERID;
	            var name = json.sonPersonList[i].DISPLAY_NAME ? json.sonPersonList[i].DISPLAY_NAME.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var mobile = json.sonPersonList[i].UEP_MOBILE ? json.sonPersonList[i].UEP_MOBILE.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var tel = json.sonPersonList[i].TEL ? json.sonPersonList[i].TEL.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var fax = json.sonPersonList[i].UEP_FAX ? json.sonPersonList[i].UEP_FAX.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var web_fax = json.sonPersonList[i].WEB_FAX ? json.sonPersonList[i].WEB_FAX.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var unitInfo = id+"_"+name+"_"+mobile;
	            
	            var text = "<table width='100%'><tr><td width='20%'><span>"+name+"</span></td><td width='80%' style='color: #7d7d7d;'>", title=name;
	            if ('<%=type%>' == "tel") {
		           if ($("#showType").val() == "treeUnit") {
	            		if (mobile != "") {
			           		text = text + "<a href='javascript:void(0);' class='tooltip' info="+unitInfo+"  phoneNum="+mobile+"  pname="+name+" type='unit' style='text-decoration: none' onclick='dialing(&quot;"+mobile+"&quot;)'>"+mobile+"</a>(手机)　　";
							title = title + "　手机:" + mobile;
			            }
	            	}
		            if (tel != "") {
		           		text = text + "<a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+tel+"&quot;)'>"+tel+"</a>(电话)　　";
						title = title + "　电话:" + tel;
		            }
		            if (fax != "") {
		           		text = text + "<a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+fax+"&quot;)'>"+fax+"</a>(传真)　　";
						title = title + "　传真:" + fax;
		            }
		            if (web_fax != "") {
		           		text = text + "<a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+web_fax+"&quot;)'>"+web_fax+"</a>(传真)</td></tr></table>";
						title = title + "　网络传真:" + web_fax;
		            }

	            } else if ('<%=type%>' == "fax") {
	            	if (fax != "") {
		           		text = text + "<a href='javascript:void(0);' style='text-decoration: none' onclick='sendFax(&quot;"+name+"&quot;,&quot;"+tel+"&quot;,&quot;"+fax+"&quot;)'>"+fax+"</a>(传真)";
		           		title = title + "传真:" + fax;
		            }
		            if (web_fax != "") {
		           		text = text + "<a href='javascript:void(0);' style='text-decoration: none' onclick='sendFax(&quot;"+web_fax+"&quot;,&quot;"+tel+"&quot;,&quot;"+web_fax+"&quot;)'>"+web_fax+"</a>(传真)";
		           		title = title + "　网络传真:" + fax;
		            }
	            }
	            
	            var node1 = new node(id, text, "", "true", title);
	            node1.dom.style.display = "block";
	            node1.dom.style.width = Pnode.dom.offsetWidth - 20 + "px";
	            Pnode.add(node1);
	        }
	        Pnode.childNode[0].removeNode();
	        // 没有子节点，则移除展开图标以及单击和双击事件
	        if(orglen == 0 && perlen == 0) {
	            Pnode.SetSingle();// 设置该节点没有子节点
	        }
        });
    }
    
    // 加载个人通讯录树节点
    function loadaddresstree(url, tv, gId) {
    	var firsturl = "";
    	if (gId >= 0) firsturl = url + gId;
    	else firsturl = url;
    	
        $.getJSON(firsturl, function(json){
	        var len = json.groupList.length;
	        for(var i = 0; i < len; i++) {
	            var id = json.groupList[i].NAG_ID;
	            var text = json.groupList[i].NAG_NAME.replace(/(^\s*)|(\s*$)/g, "");
	            
	            var node1 = new node(id, text, "");
	            var loadnode = new node("", "请等待...", "", "true");// 不能展开
	            node1.add(loadnode);
	            tv.add(node1);
	        }
	        
	        // 加载组内人员
	        var orglen = json.personList.length;
	        for(var i = 0; i < orglen; i++) {
	            var id = json.personList[i].NUA_ID;
	            var name = json.personList[i].NUA_NAME.replace(/(^\s*)|(\s*$)/g, "");
	            var phone = json.personList[i].NUA_PHONE ? json.personList[i].NUA_PHONE.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var telHome = json.personList[i].NUA_TEL1 ? json.personList[i].NUA_TEL1.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var telOffice = json.personList[i].NUA_TEL2 ? json.personList[i].NUA_TEL2.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var telOther = json.personList[i].NUA_TEL3 ? json.personList[i].NUA_TEL3.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var addressInfo = "";
	            
	            var text = "<table width='100%'><tr><td width='20%'><span>"+name+"</span></td><td width='80%' style='color: #7d7d7d;'>", title=name;
	            if (phone != "") {		// 多电话循环
	            	if (phone.indexOf(',') > 0) {
	            		var phoneList = phone.split(',');
	            		for ( var j = 0; j <  phoneList.length; j++) {
	            			addressInfo = id+"-"+phoneList[j]+"_"+name+"_"+phoneList[j];
							text = text + "<a href='javascript:void(0);' class='tooltip' phoneNum="+phoneList[j]+" info="+addressInfo+"  pname="+name+" type='address' style='text-decoration: none' onclick='dialing(&quot;"+phoneList[j]+"&quot;)'>"+phoneList[j]+"</a>(手机)　　";
							title = title + "　手机:" + phoneList[j];
						}
	            	} else {
	            		addressInfo = id+"_"+name+"_"+phone;
	            		text = text + "<a href='javascript:void(0);'  class='tooltip' phoneNum="+phone+" info="+addressInfo+"  pname="+name+" type='address' style='text-decoration: none' onclick='dialing(&quot;"+phone+"&quot;)'>"+phone+"</a>(手机)　　";
						title = title + "　手机:" + phone;
	            	}
	            }
	            if (telHome != "") {
	            	if (telHome.indexOf(',') > 0) {
	            		var telHomeList = telHome.split(',');
	            		for ( var j = 0; j <  telHomeList.length; j++) {
							text = text + "<a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+telHomeList[j]+"&quot;)'>"+telHomeList[j]+"</a>(住宅)　　";
							title = title + "　住宅:" + telHomeList[j];
						}
	            	} else {
	            		text = text + "<a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+telHome+"&quot;)'>"+telHome+"</a>(住宅)　　";
	           			title = title + "　住宅:" + telHome;
	            	}
	            }
	            if (telOffice != "") {
	            	if (telOffice.indexOf(',') > 0) {
	            		var telOfficeList = telOffice.split(',');
	            		for ( var j = 0; j <  telOfficeList.length; j++) {
							text = text + "<a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+telOfficeList[j]+"&quot;)'>"+telOfficeList[j]+"</a>(办公)　　";
							title = title + "　办公:" + telOfficeList[j];
						}
	            	} else {
	            		text = text + "<a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telOffice+"&quot;)'>"+telOffice+"</a>(其他)　　";
	           			title = title + "　办公:" + telOffice;
	            	}
	            }
	            if (telOther != "") {
	            	if (telOther.indexOf(',') > 0) {
	            		var telOtherList = telOther.split(',');
	            		for ( var j = 0; j <  telOtherList.length; j++) {
							text = text + "<a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telOtherList[j]+"&quot;)'>"+telOtherList[j]+"</a>(其他)　　";
							title = title + "　其他:" + telOtherList[j];
						}
	            	} else {
	            		text = text + "<a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telOther+"&quot;)'>"+telOther+"</a>(其他)</td></tr></table>";
	           			title = title + "　其他:" + telOther;
	            	}
	            }
	            
	            var node1 = new node(id, text, "", "true", title);
	            var loadnode = new node("", "请等待...", "", "true");	// 不能展开
	            node1.add(loadnode);
	            tv.add(node1);
	        }
        });
    }
    
    // 加载个人通讯录节点信息
    function loadaddressnode(url, Pnode) {
        var firsturl = url + Pnode.id;
	    $.getJSON(firsturl, function(json){
	        // 加载组内人员
	        var orglen = json.personList.length;
	        for(var i = 0; i < orglen; i++) {
	            var id = json.personList[i].NUA_ID;
	            var name = json.personList[i].NUA_NAME.replace(/(^\s*)|(\s*$)/g, "");
	            var phone = json.personList[i].NUA_PHONE ? json.personList[i].NUA_PHONE.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var telHome = json.personList[i].NUA_TEL1 ? json.personList[i].NUA_TEL1.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var telOffice = json.personList[i].NUA_TEL2 ? json.personList[i].NUA_TEL2.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var telOther = json.personList[i].NUA_TEL3 ? json.personList[i].NUA_TEL3.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var addressInfo = "";
	            var text = "<table width='100%'><tr><td width='20%'><span>"+name+"</span></td><td width='80%' style='color: #7d7d7d;'>", title=name;
	            if (phone != "") {		// 多电话循环
	            	if (phone.indexOf(',') > 0) {
	            		var phoneList = phone.split(',');
	            		for ( var j = 0; j <  phoneList.length; j++) {
	            			addressInfo = id+"-"+phoneList[j]+"_"+name+"_"+phoneList[j];
							text = text + "<a href='javascript:void(0);' class='tooltip' phoneNum="+phoneList[j]+" info="+addressInfo+"  pname="+name+" type='address' style='text-decoration: none' onclick='dialing(&quot;"+phoneList[j]+"&quot;)'>"+phoneList[j]+"</a>(手机)　　";
							title = title + "　手机:" + phoneList[j];
						}
	            	} else {
	            		addressInfo = id+"_"+name+"_"+phone;
	            		text = text + "<a href='javascript:void(0);'  class='tooltip' phoneNum="+phone+" info="+addressInfo+"  pname="+name+" type='address'  style='text-decoration: none' onclick='dialing(&quot;"+phone+"&quot;)'>"+phone+"</a>(手机)　　";
						title = title + "　手机:" + phone;
	            	}
	            }
	            if (telHome != "") {
	            	if (telHome.indexOf(',') > 0) {
	            		var telHomeList = telHome.split(',');
	            		for ( var j = 0; j <  telHomeList.length; j++) {
							text = text + "<a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telHomeList[j]+"&quot;)'>"+telHomeList[j]+"</a>(住宅)　　";
							title = title + "　住宅:" + telHomeList[j];
						}
	            	} else {
	            		text = text + "<a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telHome+"&quot;)'>"+telHome+"</a>(住宅)　　";
	           			title = title + "　住宅:" + telHome;
	            	}
	            }
	            if (telOffice != "") {
	            	if (telOffice.indexOf(',') > 0) {
	            		var telOfficeList = telOffice.split(',');
	            		for ( var j = 0; j <  telOfficeList.length; j++) {
							text = text + "<a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telOfficeList[j]+"&quot;)'>"+telOfficeList[j]+"</a>(办公)　　";
							title = title + "　办公:" + telOfficeList[j];
						}
	            	} else {
	            		text = text + "<a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telOffice+"&quot;)'>"+telOffice+"</a>(其他)　　";
	           			title = title + "　办公:" + telOffice;
	            	}
	            }
	            if (telOther != "") {
	            	if (telOther.indexOf(',') > 0) {
	            		var telOtherList = telOther.split(',');
	            		for ( var j = 0; j <  telOtherList.length; j++) {
							text = text + "<a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telOtherList[j]+"&quot;)'>"+telOtherList[j]+"</a>(其他)　　";
							title = title + "　其他:" + telOtherList[j];
						}
	            	} else {
	            		text = text + "<a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telOther+"&quot;)'>"+telOther+"</a>(其他)</td></tr></table>";
	           			title = title + "　其他:" + telOther;
	            	}
	            }
	            
	            var node1 = new node(id, text, "", "true", title);
	            node1.dom.style.display = "block";
	            node1.dom.style.width = Pnode.dom.offsetWidth - 20 + "px";
	            var loadnode = new node("", "请等待...", "", "true");
	            node1.add(loadnode);
	            Pnode.add(node1);
	        }
	        Pnode.childNode[0].removeNode();
	        // 没有子节点，则移除展开图标以及单击和双击事件
	        if(orglen == 0) {
	            Pnode.SetSingle();// 设置该节点没有子节点
	        }
        });
    }
    
    function dialing(called) {
  		var url = "${ctx}/app/integrateservice/inputCallPhone.act?selfMac="+'<%=selfMac%>'+"&selfTel="+'<%=selfTel%>'+"&called="+called;
		openEasyWin("winId","拨打电话",url,"400","305",true);
	  	//$.ajax({
		//	type: "POST",
		//	url:  "${ctx}/app/integrateservice/ipCall.act",
		//	data: {"selfMac": '<%=selfMac%>', "selfTel": '<%=selfTel%>', "called": called},
		//	dataType: 'text',
		//	async : false,
		//	success: function(msg){
		//		if(msg != "success")  easyAlert("提示信息", "拨号失败","info");
		//	 },
		//	 error: function(msg, status, e){
		//		 easyAlert("提示信息", "拨号出错","info");
		//	 }
		//});
    }
    
    function sendMsg(info, name, type) {
  		//showShelter('350','600','${ctx}/app/messagingplatform/messagingPlatformInit.act?mobile='+called+"&name="+name);
  		var url = "${ctx}/app/sendsms/inputSMS.act?info="+info+"&name="+name+"&type="+type;
		openEasyWin("winId","发送短信",url,"650","400",true);
  	}
    
    function callback(node) {
	    // 子节点对象集合
	    if(node.childNode.length == 1 && node.childNode[0].text == "请等待...") {
	    	if(document.getElementById("pabList").style.display != "none") {
			    var url = "${ctx}/app/integrateservice/loadAddressInfo.act?gId=";
			    loadaddressnode(url, node);
		    } else {
		    	var url = "${ctx}/app/integrateservice/loadSonInfo.act?orgId=";
			    loadnode(url, node);
		    }
	    }
	    setTimeout("initBindTag()", 2000);
    }
    
  	function searchInfo(){
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
  				url = "${ctx}/app/integrateservice/queryAddress.act";	// 个人
  			else if (showType == "treeUnit") {
  				url = "${ctx}/app/integrateservice/queryContact.act";	// 部门
  				orgId = '<%=orgId%>';
  			} else if (showType == "tree") {
  				url = "${ctx}/app/integrateservice/queryContact.act";	// 大厦
  			}
  				
	  		$.ajax({
				type: "POST",
				url: url,
				data: {"tjVal": tjVal, "type": showType, "orgId": orgId},
				dataType: 'json',
				async : false,
				success: function(json){
					if(json.length > 0) {
						$("#searchList").append("<table style='margin-left:5px; width:500px;' cellspacing='20' id='contact_tab'></table>");
						if (showType == "pabList") {
							$("#contact_tab").append("<tr><td>姓名</td><td>手机</td><td>办公</td><td>住宅</td></tr>");
						} else if (showType == "treeUnit") {
							$("#contact_tab").append("<tr><td>单位</td><td>姓名</td><td>电话</td><td>传真</td><td>网络传真</td><td>手机</td></tr>");
						} else if (showType == "tree") {
							$("#contact_tab").append("<tr><td>单位</td><td>姓名</td><td>电话</td><td>传真</td><td>网络传真</td></tr>");
						}
						
						var name, num1, num2, num3, num2web, _info;
						$.each(json, function(i) {
							var text1 = "", text2 = "", text3 = "", text4 = "";
							if (showType == "pabList") {
								name = json[i].NUA_NAME ? json[i].NUA_NAME.replace(/(^\s*)|(\s*$)/g, "") : "";
								
				            	num1 = json[i].NUA_PHONE ? json[i].NUA_PHONE.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	if (num1.indexOf(',') > 0)	text1 = num1.substring(0,11)+"...";
				            	else {
				            		_info = json[i].NUA_ID+"_"+name+"_"+num1;
				            		text1 = "<a href='javascript:void(0);'  class='tooltip'  phoneNum="+num1+" info="+_info+" type='address'  pname="+name+" style='text-decoration: none' onclick='dialing(&quot;"+num1+"&quot;)'>"+num1+"</a>";
				            	}
				            	
				            	num2 = json[i].NUA_TEL2 ? json[i].NUA_TEL2.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	if (num2.indexOf(',') > 0)	text2 = num2.substring(0,11)+"...";
				            	else text2 = "<a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+num2+"&quot;)'>"+num2+"</a>";
				            	
				            	num3 = json[i].NUA_TEL1 ? json[i].NUA_TEL1.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	if (num3.indexOf(',') > 0)	text3 = num3.substring(0,11)+"...";
				            	else text3 = "<a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+num3+"&quot;)'>"+num3+"</a>";
				            	
				            	//num4 = json[i].NUA_TEL3 ? json[i].NUA_TEL3.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	//if (num4.indexOf(',') > 0)	num4 = num4.substring(0,11)+"...";
				            	
				            	$("#contact_tab").append("<tr><td>"+name+"</td><td>"+text1+"</td><td>"+text2+"</td><td>"+text3+"</td></tr>");
							} else if (showType == "treeUnit") { 
								unitname = json[i].DEPARTMENT_NAME ? json[i].DEPARTMENT_NAME.replace(/(^\s*)|(\s*$)/g, "") : "";	// 本单位下的部门名称
								name = json[i].DISPLAY_NAME ? json[i].DISPLAY_NAME.replace(/(^\s*)|(\s*$)/g, "") : "";
				           	 	num1 = json[i].TEL ? json[i].TEL.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	num2 = json[i].UEP_FAX ? json[i].UEP_FAX.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	num2web = json[i].WEB_FAX ? json[i].WEB_FAX.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	num3 = json[i].UEP_MOBILE ? json[i].UEP_MOBILE.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	_info = json[i].NUA_ID+"_"+name+"_"+num3;
				            	
				            	text1 = "<a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+num1+"&quot;)'>"+num1+"</a>";
				            	text2 = "<a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+num2+"&quot;)'>"+num2+"</a>";
				            	text4 = "<a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+num2web+"&quot;)'>"+num2web+"</a>";
				            	text3 = "<a href='javascript:void(0);'  class='tooltip' phoneNum="+num3+"  info="+_info+" type='unit'  pname="+name+" style='text-decoration: none' onclick='dialing(&quot;"+num3+"&quot;)'>"+num3+"</a>";
				            	
				            	$("#contact_tab").append("<tr><td>"+unitname+"</td><td>"+name+"</td><td>"+text1+"</td><td>"+text2+"</td><td>"+text4+"</td><td>"+text3+"</td></tr>");
							} else if (showType == "tree") { 
								unitname = json[i].ORG_NAME ? json[i].ORG_NAME.replace(/(^\s*)|(\s*$)/g, "") : "";		// 一级单位
								name = json[i].DISPLAY_NAME ? json[i].DISPLAY_NAME.replace(/(^\s*)|(\s*$)/g, "") : "";
				           	 	num1 = json[i].TEL ? json[i].TEL.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	num2 = json[i].UEP_FAX ? json[i].UEP_FAX.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	num2web = json[i].WEB_FAX ? json[i].WEB_FAX.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	
				            	text1 = "<a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+num1+"&quot;)'>"+num1+"</a>";
				            	text2 = "<a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+num2+"&quot;)'>"+num2+"</a>";
				            	text4 = "<a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+num2web+"&quot;)'>"+num2web+"</a>";
				            	
				            	$("#contact_tab").append("<tr><td>"+unitname+"</td><td>"+name+"</td><td>"+text1+"</td><td>"+text2+"</td><td>"+text4+"</td></tr>");
							}
						});
					}
				 },
				 error: function(msg, status, e) {
					 easyAlert("提示信息", "操作出错","info");
				 }
			 });
			 setTimeout("initBindTag()", 1000);
		 }
  	}
	
    // 查询输入框
    function inputFun(dom, num) {
        if(num == 0) dom.value = "";
        if(num == 1 && dom.value == "") dom.value = "请输入姓名或电话";
    }
    
    // 清除按钮
    function clear_search(dom) {
    	var st = $("#showType").val();	// 退出显示最后一次显示的标签页
    	$("#"+st).show();
        document.getElementById("searchList").style.display = "none";
        document.getElementById("searchTJ").value = "请输入姓名或电话";
        dom.style.display = "none";
    }
	
	function tipsChange(dom, id) {
        dom.parentNode.className = 'reportTips_' + dom.className;
        if(dom.className == "tips3") {
            document.getElementById("peopleSet").style.display = "block";
        } else {
            document.getElementById("peopleSet").style.display = "none";
        }
        
        $("#address_list").children("div").hide();
        $("#"+id).show();
        //$("#searchType").val(type);
        $("#showType").val(id);
        
        // 加载数据
        var tv = new treeview("","");
        var url = "", orgId = 0;
        if (id == "tree" && document.getElementById("tree").innerHTML == "") {						// 大厦
	        tv.create("tree");
	        url = "${ctx}/app/integrateservice/loadSonInfo.act?orgId=";
	        loadtree(url, tv, orgId);
        } else if (id == "treeUnit" && document.getElementById("treeUnit").innerHTML == "") { 		// 部门
        	tv.create("treeUnit");
	        url = "${ctx}/app/integrateservice/loadSonInfo.act?orgId=";
	        orgId = '<%=orgId%>';
	        loadtree(url, tv, orgId);
        } else if (id == "pabList" && document.getElementById("pabList").innerHTML == "") {			// 个人
        	tv.create("pabList");
	        url = "${ctx}/app/integrateservice/loadAddressInfo.act?gId=";
	        loadaddresstree(url, tv, 0);
        }
        
        setTimeout("initBindTag()", 1000);
    }
</script>
</html>
