<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/pager1.tld" prefix="page" %>
<%--
- Author: WXJ
- Date: 2010-08-27 15:37:15
- Description: 资源维护页面
--%>
	<%--当前用户资源类型为根节点时可添加删除操作 并且不是   --%>

	<c:set var="buttons" value="add,del"/>
	
	<%--当前用户资源类型为总部时只能添加
	<c:if test="${_orgtype!='0' and treelevel!=2}">
		<c:set var="buttons" value="add"/>
	</c:if>--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>资源列表</title>
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
<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript" ></script>
		
<script type="text/javascript">
	$(document).ready(function(){
		$("#checkAll").bind("click", checkAll);
		
		var chk_options = { 
			all:'#checkAll',
			item:'.checkItem'
		};
		//$(this).chk_init(chk_options);
		changebasiccomponentstyle();
		changedisplaytagstyle();
		changecheckboxstyle();
		
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
.table_btn_orgframe:hover{
	background:#ffc600;
}
.in_sss_left{
	margin:0;
	padding:0;
	border:0;
}
</style>	
</head>
<body style="background:#fff;">
	<div class="bgsgl_right_list_border">
	  	<div class="bgsgl_right_list_left">电子门锁</div>
	</div>
<div id="list"></div>
<form id="queryForm" action="jzsbLockListFath.act" method="post"  autocomplete="off" style="height: 520px;">
	<s:hidden name="PId"/>
	<input id="res" name="res" value="${resType}" type="hidden"/>
 	<input type="hidden" id="nodeId" name="nodeId" value="${param.nodeId }" />
 	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center" id="hide_table"  border="0" width="100%" class="form_table">
        <tr>
        <td class="form_label" style="width: 25px;" align="left">
           	楼层：
        </td>
        <td width="10%">
			<s:textfield name="link" theme="simple" size="12"/>  
        </td>
        <td class="form_label" style="width: 25px;" align="left">
           	房间：
        </td>
        <td width="10%">
			<s:textfield name="memo" theme="simple" size="12"/>  
        </td>
        </tr>
        <tr>
        <td class="form_label" style="width: 25px;" align="left">
           	 门锁地址：
        </td>
        <td width="10%">
			<s:textfield name="name" theme="simple" size="12"/>  
        </td>
        <td class="form_label" style="width: 25px;" align="left">
           	通讯机号：
        </td>
        <td  width="10%">
           <s:textfield name="title" id="title" theme="simple" size="12" onblur="checkTitle()"/>  
        </td>
        <td class="form_label" style="width: 25px;" align="left">
       	  	备注：       	  	
        </td>
        <td width="10%">
            <s:textfield name="keyword" theme="simple" size="12"/>  
        </td>
        <td width="25%" style="text-align: center;">
        	<s:textfield  name="page.pageSize" id="pageSize" theme="simple" cssStyle="width:20px;" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
          	<script>$("#pageSize").hide();</script>
			<a href="javascript:void(0);" class="table_btn_orgframe" id="resetbutton" iconCls="icon-reload" onclick="clearSearch();">重置</a>
        	<a href="javascript:void(0);" class="table_btn_orgframe" id="searchbutton" iconCls="icon-search"  onclick="querySubmit();">查询</a>
        </td>
        </tr>     
      </table>      
   	</h:panel>
	<page:pager id="list_panel" width="100%" buttons="${buttons}" title="结果列表">
		<d:table name="page.result" id="row"  export="false" class="table"	requestURI="list.act?exFile=1">
			<d:col  style="width:45"   class="display_centeralign"  title="<input type=\"checkbox\" class=\"in_sss_left\" id=\"checkAll\"/>" >
				<input type="checkbox" name="_chk" id="_chk" class="checkItem in_sss_left" value="${row.nodeId}" orgValue="${row.PId}" onclick="selectCheckbox(this)" />
			</d:col>
			<d:col property="link" class="display_centeralign"   title="楼层"/>
			<d:col property="memo" class="display_centeralign"   title="房间"/>
		    <d:col property="objAttrib" class="display_centeralign" title="通讯机"/>
		    <d:col property="name"   class="display_centeralign"   title="门锁地址"/>
			<d:col property="keyword" class="display_leftalign" title="备注"/>
			<c:if test="${nodeId > 15}">
				<d:col class="display_centeralign"  media="html" title="操作">
					<a href="javascript:void(0);" onclick="updateRecord('${row.nodeId}','${row.PId }')">[修改]</a>&nbsp;
				</d:col>	
			</c:if>
		</d:table>
	</page:pager>
	<input type="hidden" id="deletes" name="deletes" value="" /> 
	<input type="hidden" id="jampPageNo" name="jampPageNo" value="${page.pageNo}" />
</form>
<script type="text/javascript">
	function selectCheckbox(obj){
		var result = new Array(); 
		$("input[name='_chk']").each(function(){
			if($(this).is(":checked")){
				result.push($(this).attr("orgValue"));
			}
		});	
		$("#deletes").val(result);
		var checkSize = $("input[name='_chk']:checked").size();
		var allSize = $("input[name='_chk']").size();
		if(allSize == checkSize){
			$("#checkAll").attr('checked',true);
		}else{
			$("#checkAll").attr('checked',false);
		}
		var items = $("input[name='_chk']:checked");
		if(items){
			if(items.length==1){
				//$("#chaifen").show();
				//$("#hebing").hide();
				$("input[name='_chk']").attr("disabled",false);
			}else if(items.length==2){
				//$("#chaifen").hide();
				var tempItems = $("input[name='_chk']").not("input[name='_chk']:checked");
				for(var i=0;i<tempItems.size();i++){
					tempItems.eq(i).attr("disabled",false);
				}
			}else if(items.length>2){
				$(obj).checked=false;
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
	
	function addRecord(){
		var nodeId = $("#nodeId").val();
		if("14" != nodeId && "13" != nodeId && "15" != nodeId && "1" != nodeId){
			var url = "${ctx}/app/per/jzsbLockInputTestInput.act?nodeId="+nodeId;
			//showShelter("800","600",url);
			//window.showModalDialog(url);
			//showShelter("550","250",url);
			openEasyWin("winId","设备添加",url,"550","280",true);
		}else{
			//easyAlert('提示信息','设备设施应该在房间下添加！','error');
			var url = "${ctx}/app/per/jzsbLockInputTestInput.act?nodeId="+nodeId;
			//showShelter("550","250",url);
			//window.showModalDialog(url);
			openEasyWin("winId","设备添加",url,"550","280",true);
		}
	}
	function updateRecord(id,setId){
		var url = "${ctx}/app/per/queryInfoLockInput.act?nodeId="+id+"&lockId="+setId;
		//showShelter("800","450",url);
		openEasyWin("winId","设备更新",url,"600","255",false);
	}
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
					var ids = new Array();
					var pageNo = $("#pageNo").val();
					$.each(($("input[name=_chk]:checked")), function(i, n){
						ids[i] = $(n).val();
					});
					var idStr = ids.join(',');
					$.ajax({
						type: "POST",
						url: "${ctx}/app/per/delJzsbLockCheck.act",
						data: {"idStr": idStr,"deletes": $("#deletes").val()},
						dataType: 'text',
						async : true,
						success: function(json){
							easyAlert("提示信息", json,"info");
							jumpPage(pageNo);
						}
					});
					//$('#queryForm').attr("action","mergeHomeById.act");
					//$('#queryForm').attr("action","delJzsbLockCheck.act");  
					//$('#queryForm').submit();
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
			var result = new Array(); 
			$("input[name='_chk']").each(function(){
				if($(this).is(":checked")){
					result.push($(this).attr("orgValue"));
				}
			});	
			$("#deletes").val(result);
		}else{
			$("#checkAll").attr("checked", true);
			$("input[name='_chk']").attr("checked", true);
			var result = new Array(); 
			$("input[name='_chk']").each(function(){
				if($(this).is(":checked")){
					result.push($(this).attr("orgValue"));
				}
			});	
			$("#deletes").val(result);
		}
	}
	
	/*清空充值查询条件 */
	function clearSearch(){
		$("#link").val("");
		$("#memo").val("");
		$("#name").val("");
		$("#title").val("");
		$("#keyword").val("");
	}
	
	function checkTitle(){
		var title = $("#title").val();
		var reg = /^(0|[1-9]\d*)$/;
		if(title != ""){
			if(!reg.test(title)){
				easyAlert('提示信息','通讯机号只能输入数字！','error');
			}
		}
	}
</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
