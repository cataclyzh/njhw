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

	<c:if test="${ExtResType ne 'R'}">
		<c:set var="buttons" value=""/>
	</c:if>
	<c:if test="${ExtResType eq 'R'}">
		<c:set var="buttons" value="add,del"/>
	</c:if>
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
		if($("#ExtResType").val() == "R"){
			$("#addSource").css('display','');
			$("#delSource").css('display','');
		}else{
			$("#addSource").css('display','none');
			$("#delSource").css('display','none');
		}
			
		var chk_options = { 
			all:'#checkAll',
			item:'.checkItem'
		};
		//$(this).chk_init(chk_options);
		changebasiccomponentstyle();
		changedisplaytagstyle();
		changecheckboxstyle();
		
	});
	
	function getObjAdminUserTreeList(){
		var url = "${ctx}/app/per/orgAdminUserTree.act?nodeId="+$("#nodeId").val();
		showShelter('500','400',url);
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
		  <div class="bgsgl_right_list_left">建筑结构</div>
	</div>
<div id="list"></div>
	<form id="queryForm" action="jzjgList.act" method="post"  autocomplete="off" style="height: 520px;">
	<s:hidden name="PId"/>
	<input id="res" name="res" value="${resType}" type="hidden"/>
 	
 	<h:panel id="query_panel" width="100%" title="查询条件">	
 	<%--<input type="button" onclick="addRecord()" value="新增"/>--%> 
      <table align="center" id="hide_table"  border="0" width="100%" class="form_table">
        <tr>
        <td class="form_label" style="width: 25px;" align="left">
       	  	门牌号码：       	  	
        </td>
        <td width="10%">
            <s:textfield name="name" theme="simple" size="12" id="name"/>  
        </td>
        <td class="form_label" style="width: 25px;" align="left">
           	房间名称：
        </td>
        <td width="10%">
			<s:textfield name="title" theme="simple" size="12" id="title"/>  
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
				<input type="checkbox" name="_chk" id="_chk" class="checkItem in_sss_left" value="${row.nodeId}" onclick="selectCheckbox(this)" />
			</d:col>
		    <d:col property="name"   class="display_centeralign"   title="门牌号码"/>
		    <d:col property="title" class="display_leftalign" title="房间名称"/>
			
			<c:if test="${ExtResType == 'R'}">
			<d:col class="display_centeralign"  media="html" title="操作">
				<a href="javascript:void(0);" onclick="updateRecord('${row.nodeId}')">[修改]</a>&nbsp;
			</d:col>	
			</c:if>
		</d:table>
   </page:pager>
<input type="hidden" id="ExtResType" value="${ExtResType}" />
<input type="hidden" id="deleteIds" name="deleteIds" value="" />
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
					//$("#Merger").css('display','');房间合并功能
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
	
	function addRecord(){
		var url = "${ctx}/app/per/jzjgInput.act?PId=" +$('#PId').val() +"&res="+$('#res').val() ;
		openEasyWin("jzjgId","建筑设备维护",url,"400","170",true);
	}
	function updateRecord(id){
		var url = "${ctx}/app/per/jzjgInput.act?nodeId="+id +"&res="+$('#res').val();
		openEasyWin("jzjgId","建筑设备维护",url,"400","170",true);
		//showShelter("800","450",url);
	}
	function marge(){
		easyConfirm('提示', '确定合并这两个房间？', function(r){
			if (r){
				/*
				var result = new Array();
				$("input[@type=checkbox]:checked").each(function(){
					if($(this).is(":checked")){
						result.push($(this).attr("value"));
					}
				});	
				var jzjgId = result.join(",");
				*/
				$('#queryForm').attr("action","mergeHomeById.act"); //合并方法
				$("#queryForm").submit();
			}
		});
	}
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
		
			var ids = new Array();
			var pageNo = $("#pageNo").val();
			$.each(($("input[name=_chk]:checked")), function(i, n){
				ids[i] = $(n).val();
			});
			var idStr = ids.join(',');
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
						$.ajax({
							type: "POST",
							url: "${ctx}/app/per/jzjgDelete.act",
							data: {"idStr": idStr},
							dataType: 'text',
							async : true,
							success: function(json){
								easyAlert("提示信息", json,"info");
								jumpPage(pageNo);
							}
						});
					}
				});
			/*
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
					//alert($("input[@type=checkbox]:checked").val());
					//$('#queryForm').attr("action","mergeHomeById.act"); 合并方法
					$("#deleteIds").val($("input[@type=checkbox]:checked").val()); //删除选中的列
					$('#queryForm').attr("action","jzjgDelete.act");  
					$('#queryForm').submit();
				}
			});	*/	
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
	
	/*清空充值查询条件 */
	function clearSearch(){
		$("#name").val("");
		$("#title").val("");
	}
	
</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>

