<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/pager1.tld" prefix="page" %>
<c:set var="buttons" value="add,del"/>
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
	  	<div class="bgsgl_right_list_left">门锁通讯机</div>
	</div>
<div id="list"></div>
	<form id="queryForm" action="jzsbDoorControllerList.act" method="post"  autocomplete="off" style="height: 520px;">
 	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center" id="hide_table"  border="0" width="100%" class="form_table">
        <tr>
        <td class="form_label" style="width: 25px;" align="left">
           	通讯机编号：
        </td>
        <td width="10%">
        	<input type="text" name="commId" size="12" value="${commId}" id="commId"/>
			<%--<s:textfield name="commId" theme="simple" size="12" value=""/>  --%>
        </td>
        <td class="form_label" style="width: 25px;" align="left">
       	  	通讯机ip：       	  	
        </td>
        <td width="10%">
        	<input type="text" name="commIp" size="12" value="${commIp}" id="commIp"/>
            <%--<s:textfield name="commIp" theme="simple" size="12"/>  --%>
        </td>
        <td class="form_label" style="width: 25px;" align="left">
           	通讯机描述：
        </td>
        <td  width="10%">
           <input type="text" name="memo" size="12" value="${memo}" id="memo"/>
           <%--<s:textfield name="memo" theme="simple" size="12" />  --%>
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
		<d:table name="page.result" id="row"  export="false" class="table"	requestURI="jzsbDoorControllerList.act">
			<d:col  style="width:45"   class="display_centeralign"  title="<input type=\"checkbox\" class=\"in_sss_left\" id=\"checkAll\"/>" >
				<input type="checkbox" name="_chk" id="_chk" class="checkItem in_sss_left" value="${row.id}" onclick="selectCheckbox(this)" />
			</d:col>
		    <d:col property="commId"   class="display_centeralign"   title="通讯机号"/>
		    <d:col property="commIp" class="display_leftalign" title="通讯机ip地址"/>
		    <d:col property="memo" class="display_centeralign" title="通讯机描述" maxLength="40" />
			<d:col class="display_centeralign"  media="html" title="操作">
				<a href="javascript:void(0);" onclick="updateRecord('${row.id}')">[修改]</a>&nbsp;
			</d:col>
			<d:col class="display_centeralign"  media="html" title="上传授权码">
				<a href="javascript:void(0);" onclick="uploadKey('${row.commId}')">[上传授权码]</a>&nbsp;
			</d:col>
		</d:table>
   </page:pager>
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
		url = "${ctx}/app/per/jzsbDoorControllerInput.act";
		openEasyWin("dciId","添加通讯机",url,"500","300",true);
	}
	function updateRecord(id){
		url = "${ctx}/app/per/jzsbDoorControllerInput.act?id="+id;
		openEasyWin("dciId","修改通讯机",url,"800","450",true);
	}
	
	function uploadKey(id){
		$.ajax({
			type: "POST",
			url: "${ctx}/app/per/jzsbDoorControllerUpload.act",
			data: {"commId": id},
			dataType: 'text',
			async : true,
			success: function(json){
				json = eval('(' + json + ')');
				if (json.isSuccess) {
					easyAlert('提示信息','上传授权码完成！','info');
				} else {
					easyAlert('提示信息','通讯机连接异常，请联系管理员！','info');
				}
			}
		});
	}
	
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
					//alert($("input[@type=checkbox]:checked").val());
					//$('#queryForm').attr("action","mergeHomeById.act");
					$('#queryForm').attr("action","${ctx}/app/per/jzsbDoorControllerDel.act");  
					$('#queryForm').submit();
						var pageNo = document.getElementById("pageNo").value;
	   					jumpPage(pageNo);
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
	
	/*清空充值查询条件 */
	function clearSearch(){
		$("#commId").val("");
		$("#commIp").val("");
		$("#memo").val("");
	}
</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
