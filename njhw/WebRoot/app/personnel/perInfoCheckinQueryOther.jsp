<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/include/meta.jsp" %>
<title>用户列表</title>	
<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
 <script src="${ctx}/scripts/validation/card.js" type="text/javascript"></script>
 <link type="text/css" rel="stylesheet" href="${ctx}/app/personnel/telAndNumber/css/TelMessage.css" />
 <link href="${ctx}/styles/personinfo/css/info.css" rel="stylesheet" type="text/css" />
 <script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript" ></script>
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
	function querySubmit(){
		$("#paQuerNo").val('1');
		$("#queryForm").submit(); 
	}
		
		function reSet1()
		{   
		    $("#displayName").val('');
		    $("#tel").val('');
		    $("#cityCardType").val('');
		    $("#plateType").val('');
		    $("#phone").val('');
		    $("#roomInfo").val('');
		    $("#personType").val('');
			$("#paQuerNo").val('1');
			$("#cardIsnormal").val('');
			$("#queryForm").submit(); 
		}
		function editPerson(userId) 
		{   
			parent.window.top.$("#tt").window("close");
			var url = "${ctx}/app/per/inputRegisterOther.act?userId="+userId+"&orgId="+$("#orgId").val()+"&backList=true";
			//top.njhwAdmin.ifrmOrgList.location.href = url;
			top.ifrmOrgList.location.href = url;
		}
		
		function editDepartment(orgId)
		{  
			var url = "${ctx}/app/per/orgInput.act?orgId="+orgId;
			openEasyWin("orgInput","修改组织机构信息",url,"800","350");
		}
		
		$(function() {
			if ("${recu}" == "on") $("#recu").attr("checked", "checked");
		})
		
	function addRecord()
	{
		if (${org.orgId}=="")
		{
			parent.easyAlert('提示信息','请先选择单位再新增人员！','warning');
			return;
		}
		var url = "${ctx}/app/per/inputRegisterAdmin.act?ispopup=n&orgId=" + '${org.orgId}';
		window.parent.parent.frames.ifrmOrgList.location.href = url;
	}

	/*管理员重置用户密码*/
	function pwdReset(userid,disname){
		parent.parent.easyConfirm('提示', disname+"的密码将被重置为：123456，是否确定？", function(r){
			if (r) {
				var url = "${ctx}/app/personnel/unit/manage/resetPwd.act";
				var data = {userid:userid};
				var sucFun =  function(data){
					if (data.status == '1'){
						parent.parent.easyAlert("提示信息", "重置密码成功!","info");
					}else {
						parent.parent.easyAlert("提示信息", "重置密码失败!","info");
					}
				};
				var errFun = function (){
					parent.parent.easyAlert("提示信息", "重置密码失败,请检查服务器是否正常!","info");
				};
				ajaxQueryJSON(url, data, sucFun, errFun);
			}
		});
	}
	
	
	// 选中全部
	function chk_all() {
		$("#row input[type='checkbox']").each(function(i,item) {
			if ($(item).attr("disabled")!="disabled")	{		
				if ($("#checkAll").attr("checked") == "checked"){
					$(item).attr("checked", "true");
				} else {
					$(item).removeAttr("checked");
				}
			}
		});
	}
	
	
	function cancleAdmin()
	{   
	    var notChecked = $("input:checkbox[name='_chk']").not("input:checked").val();
	    if(null == notChecked)
	    {
               parent.parent.easyAlert("提示信息", "请选择人员！","info");
               return false;
		}
		modifySMA("c");
	}
	
	function modifySMA(opt) {
		if ($("#userid").val() != ""){
			var optType = "";
			var optConfirmMsg = "确定设置为管理员？";
			if(opt=="c")
			{
				optConfirmMsg = "确定取消管理员？";
			}
			
			
		var ids='';
		var checks='';
		if(opt =="s")
		{
			var list= $('input:checkbox[name="_chk"]:checked').val();
			if(null == list){ 
                parent.parent.easyAlert("提示信息", "请选择人员！","info");
                return false;
   			}
  			}
  			
		$("#row input[type='checkbox']").each(function(i,item) {
			if (this.id != 'checkAll'){		
				ids += $(this).val()+",";
				if ($(item).attr("checked")=="checked"){
					checks += "1"+",";
				} else {
					checks += "0"+",";
				}
			}
		});
		parent.parent.easyConfirm('提示', optConfirmMsg, function(r){
				if (r) {
					$.ajax({
			            type: "POST",
			            url: "${ctx}/app/per/modifySMAdmin.act",
			            data: {"userid": $("#userid").val(),"ids":ids,"checks":checks},
			            dataType: 'text',
			            async : false,
			            success: function(msg){
		                    if(msg == "success")
		                    {
		                    	parent.parent.easyAlert("提示信息", "设置成功！","info");
		                    	querySubmit();
							}
		                    else 
		                    {
								parent.parent.easyAlert("提示信息", "设置失败！","info");
							}
			            },
			            error: function(msg, status, e){
			            	parent.parent.easyAlert("提示信息", "操作出错！","info");
			            }
			         });
			     }
			});
		}
		
	}
</script>
<style>
</style>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" style="background:#fff !important;">
	<div style="margin-left:10px;">
	<s:hidden name="vi_Id" id="vi_Id"/>
	
	<c:if test="${org.levelNum <= '2' && loginId ne '1'}">
	<div class="bgsgl_right_list_border">
		<div class="bgsgl_right_list_left">${org.name}</div>
			</div>
	</c:if>
	<c:if test="${org.levelNum > '2' && loginId ne '1'}">
	<div class="bgsgl_right_list_border">
		<div class="bgsgl_right_list_left">
        	<a href="javascript:void(0);" onclick="editDepartment('${org.orgId}')">${org.name}</a>&nbsp;
        </div>
        	</div>
	</c:if>
 	<form id="queryForm" action="perInfoCheckinOther.act" method="post"  autocomplete="off">
	 	<h:panel id="query_panel" width="100%" title="查询条件">
	 	<s:hidden name = "paQuerNo" id="paQuerNo"/>
		<s:hidden name="orgId" id="orgId"/>
	      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
	        <tr>
				<td class="form_label" width="10%" align="left">
	            	姓名：
	          	</td>
	          	<td width="20%">
	           		<s:textfield name="displayName" theme="simple" size="20" cssStyle="height:15px"/>  
	          	</td>
	          	<td class="form_label" width="10%" align="left">
	            	卡类型：
	          	</td>
	          	<td width="20%">
	          		<s:select list="#application.DICT_CARD_TYPE" cssStyle="width:90%;list-style:none;" 
	          		headerKey="" headerValue="全部" listKey="dictcode" listValue="dictname" name="cityCardType"/>
	           	</td>
				<td class="form_label" width="10%" align="left">
	            	卡状态：
	          	</td>
	          	<td width="20%">
	          		<s:select list="#application.DICT_CARD_ISNORMAL" cssStyle="width:90%;list-style:none;" 
	          		headerKey="" headerValue="全部" listKey="dictcode" listValue="dictname" name="cardIsnormal"/>
	           	</td>
			</tr>
			<tr>
				<td class="form_label" align="left">
	            	电话号码：
	          	</td>
	          	<td>
	           		<s:textfield name="tel" theme="simple" size="20" cssStyle="height:15px" />  
	          	</td>
	          	<td class="form_label" align="left">
	       	     	手机号：      	 
	          	</td>
	          	<td>
	            	<s:textfield name="phone"  cssStyle="height:15px" id="phone" theme="simple" size="20"/>  
	          	</td>
				<td class="form_bottom" colspan = "2"  style ="text-align:right;padding-right:10px">
					<a href="javascript:void(0);" class="infrom_a" style="width:70px;margin:0 5px;" id="resetbutton" onclick="reSet1();">重置</a>
            		<a href="javascript:void(0);" class="infrom_a" style="width:70px;margin:0 5px;" id="searchbutton" onclick="querySubmit();">查询</a>
				</td>
			</tr>
			<tr>
				<td class="form_label" width="8%" align="left">
	            	是否内部车位：
	          	</td>
	          	<td width="7%">
	          		<s:select list="#application.DICT_PLATE_TYPE" cssStyle="width:60%;list-style:none;" 
	          		headerKey="" headerValue="全部" listKey="dictcode" listValue="dictname" name="plateType"/>
	           	</td>
			</tr>
	      </table>     
		</h:panel>

		<h:page id="list_panel" width="100%" title="查询出符合条件的人数为:${page.totalCount}">
			
			<d:table name="page.result" id="row" export="false" class="table"	requestURI="list.act?exFile=1">	
					<d:col   class="display_leftalign" title="处室"  media="html">
						${row.DEPARTMENT_NAME}
					</d:col>
				<d:col property="DISPLAY_NAME"   class="display_leftalign"   title="姓名"/>
				<d:col property="UEP_SEX"  dictTypeId="SEX"  class="display_centeralign" title="性别"/>

					<d:col property="TEL_NUM"  class="display_centeralign" title="电话号码"/>
				<d:col property="UEP_MOBILE"  class="display_centeralign" title="手机号码"/>
				
				<d:col class="display_leftalign" title="卡状态"> 
					<c:if test="${row.CARD_LOSTED eq '0' &&  row.PUICSTATUS eq '1' && row.CARDSTATUS eq '0'}">
							正常
					</c:if>
					<c:if test="${row.CARD_LOSTED eq '1'}">黑名单</c:if>
					<c:if test="${row.PUICSTATUS eq '0'}">&nbsp;&nbsp;未开卡</c:if>
					<c:if test="${row.CARDSTATUS eq '1'}">&nbsp;&nbsp;挂失</c:if>
				
					</d:col>
					<d:col class="display_centeralign"  media="html" title="操作">
						<a href="javascript:void(0);" onclick="editPerson('${row.USERID}')">[编辑]</a>&nbsp;
					</d:col>

			</d:table>
			
	   </h:page>
    </form>
    </div>
</body>
</html>

<s:actionmessage theme="custom" cssClass="success"/>
