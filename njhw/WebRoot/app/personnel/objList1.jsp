<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/pager1.tld" prefix="page" %>
<%--
- Author: WXJ
- Date: 2010-08-27 15:37:15
- Description: 资源维护页面
--%>
	<%--当前用户资源类型为根节点时可添加删除操作 并且不是   --%>

	<c:set var="buttons" value="add,del,marge"/>
	
	<%--当前用户资源类型为总部时只能添加
	<c:if test="${_orgtype!='0' and treelevel!=2}">
		<c:set var="buttons" value="add"/>
	</c:if>--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>菜单列表</title>	
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<script type="text/javascript">var _ctx = '${ctx}';</script>
<link href="${ctx}/styles/default.all.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/app/portal/toolbar/css/toolbar.css" rel="stylesheet" type="text/css"/>
<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>

<link href="${ctx}/app/integrateservice/css/fex_center.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/integrateservice/css/css_body.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/integrateservice/css/css_index.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript" ></script>
<script src="${ctx}/app/integrateservice/js/util.js" type="text/javascript"></script>
<script src="${ctx}/app/integrateservice/js/newtree.js" type="text/javascript"></script>
<script src="${ctx}/app/integrateservice/js/IntegratedOffice.js" type="text/javascript"></script>
<script src="${ctx}/scripts/ca/exporting.js" type="text/javascript"></script>

<link href="${ctx}/app/integrateservice/css/wizard_css.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/integrateservice/css/jienen.css" rel="stylesheet" type="text/css" />
	
	
<script type="text/javascript">
	$(document).ready(function(){
		/*
		var chk_options = { 
			all:'#checkAll',
			item:'.checkItem'
		};
		$(this).chk_init(chk_options);
		changebasiccomponentstyle();
		changedisplaytagstyle();
		changecheckboxstyle();*/
	});
	
	$(document).ready(function(){
		/*
		var chk_options = { 
			all:'#checkAll',
			item:'.checkItem'
		};
		$(this).chk_init(chk_options);
		changebasiccomponentstyle();
		changedisplaytagstyle();
		changecheckboxstyle();*/
	});
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
<body style="background:#fff;">
<input type="hidden" id="titleHidden" value="${title }"/>
<div id="list"></div>
	<form id="queryForm" action="objList.act" method="post"  autocomplete="off" style="height: 405px;">
	<s:hidden name="PId"/>
	<input id="res" name="res" value="${resType}" type="hidden"/>
	<page:pager id="list_panel" width="100%" buttons="${buttons}" callback="goPage" title="结果列表">
		<d:table name="page.result" id="row"  export="false" class="table"	requestURI="list.act?exFile=1">
			<d:col  style="width:30"  title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
			<d:col  style="width:45"   class="display_centeralign"  title="<input type=\"checkbox\" id=\"checkAll\"/>" >
				<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.nodeId},${row.title}" onclick="selectCheckbox(this)" />
			</d:col>
		    <d:col property="title" class="display_leftalign" title="菜单名称"/>
		    <d:col property="sort" class="display_leftalign" title="排序"/>
			<c:if test="${resType == 'D'}">
			<d:col class="display_centeralign"  media="html" title="操作">
				<a href="javascript:void(0);" onclick="updateRecord('${row.nodeId}')"><font style="color:blue">[修改]</font></a>&nbsp;
			</d:col>	
			</c:if>
			<c:if test="${resType == 'M'}">
			<d:col class="display_centeralign"  media="html" title="操作">
				<a href="javascript:void(0);" onclick="updateRecord('${row.nodeId}')"><font style="color:blue">[修改]</font></a>&nbsp;
			</d:col>	
			</c:if>
		</d:table>
   </page:pager>
   <input type="hidden" id="ExtResType" value="${ExtResType}" />
</form>
<script type="text/javascript">
	function selectCheckbox(obj){
		var checkSize = $("input[name='_chk']:checked").size();
		var allSize = $("input[name='_chk']").size();
		if(allSize == checkSize){
			$("#checkAll").attr('checked',true);
		}else{
			$("#checkAll").attr('checked',false);
		}
		var ExtResType = $("#ExtResType").val();
		var items = $("input[name='_chk']:checked");
		if(items){
			if(items.length==1){
				$("#Merger").css('display','none');
				//$("#chaifen").show();
				//$("#hebing").hide();
				$("input[name='_chk']").attr("disabled",false);
			}else if(items.length==2){
				if("R" == ExtResType){
					$("#Merger").css('display','');
				}
				//$("#chaifen").hide();
				var tempItems = $("input[name='_chk']").not("input[name='_chk']:checked");
				for(var i=0;i<tempItems.size();i++){
					tempItems.eq(i).attr("disabled",false);
				}
			}else if(items.length>2){
				$(obj).checked=false;
				$("#Merger").css('display','none');
				$("input[name='_chk']").attr("disabled",false);
			}else{
				$("#chaifen").hide();
				$("#hebing").hide();
				$("input[name='_chk']").attr("disabled",false);
			}
		}else{
			$("#chaifen").hide();
			$("#hebing").hide();
			$("input[name='_chk']").attr("disabled",false);
		}
	}
	
	
	function delRecord(){
				var items = $("input[name='_chk']:checked");
				var chkArray = new Array();
				var chkArray1=new Array();
				var chkArray2=new Array();
				for(var i=0;i<items.size();i++){
					chkArray.push(items.eq(i).val());
					chkArray1.push(items.eq(i).val().split(',')[0]);
					chkArray2.push(items.eq(i).val().split(',')[1]);
				}
				if($("input[@type=checkbox]:checked").size()==0){
					easyAlert('提示信息','请勾选要删除的记录！','info');
				}else{
					easyConfirm('提示', '确定删除？', function(r){
						if (r){
							$.ajax({
								   type: "POST",
								   url: "${ctx}/app/per/objDelete.act",
								   datatype:"json",
								   data:{"deleteIds":chkArray1.join(","),"blackName":chkArray2.join(",")},
								   success: function(msg){
								       alert(msg);
								   	   window.top[0].tree.refreshItem(window.top[0].tree.getSelectedItemId());
								       searchRes();
								   	   parent.updateCallBack();
								   }
								}); 
						}
					});		
				}
		    }
	
	var resetCheckBox = function(){
		var totalCheckBox = $("input[id*='checked_0']").size();
		var checkboxSize = $("input[id*='checked_0']:checked").size();
		//alert(checkboxSize);
		if(checkboxSize == totalCheckBox){
			$("#totalCheckBox").attr("checked", true);
			//$("input[id*='checked_0']").attr("checked", true);
		}else{
			$("#totalCheckBox").attr("checked", false);
		}
	}
	
	var checkAll = function(){
		var totalCheckBox = $("input[name='_chk']").size();
		var checkboxSize = $("input[name='_chk']:checked").size();
		if(checkboxSize == totalCheckBox){
			$("#checkAll").attr("checked", false);
			$("input[name='_chk']").attr("checked", false);
		}else{
			$("#checkAll").attr("checked", true);
			$("input[name='_chk']").attr("checked", true);
		}
	}
	$(document).ready(function(){
		$("#checkAll").bind("click", checkAll);
	});
</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
