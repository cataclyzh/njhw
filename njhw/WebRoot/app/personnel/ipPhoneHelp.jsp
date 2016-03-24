<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/pager1.tld" prefix="page" %>
<%--
- Author: WXJ
- Date: 2010-08-27 15:37:15
- Description: 组织机构维护页面
--%>
	<%--当前用户组织机构类型为根节点时可添加删除操作 并且不是   --%>

		<c:set var="buttons" value="add,del"/>
	
	<%--当前用户组织机构类型为总部时只能添加
	<c:if test="${_orgtype!='0' and treelevel!=2}">
		<c:set var="buttons" value="add"/>
	</c:if>--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>组织机构列表</title>
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
		var chk_options = { 
			all:'#checkAll',
			item:'.checkItem'
		};
		$(this).chk_init(chk_options);
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
<body style="background:#fff;height: 100%">
<div id="list"></div>
<form id="queryForm" action="ipHelp.act" method="post"  autocomplete="off">
 	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center" id="hide_table"  border="0" width="100%" class="form_table">
        <tr>
          <td class="form_label" width="8%" align="left">
           	标题：
          </td>
          <td width="15%">
          <s:textfield name="title" theme="simple" size="15"/>  
          </td>
        <td width="30%">
         	<s:textfield  name="page.pageSize" id="pageSize" theme="simple" value="10" cssStyle="width:20px;" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
          	<script>$("#pageSize").hide();</script>
          	<a href="javascript:void(0);" class="table_btn_orgframe" id="resetbutton" iconCls="icon-reload" onclick="clearSearch();">重置</a>
          	<a href="javascript:void(0);" class="table_btn_orgframe" id="searchbutton" iconCls="icon-search"  onclick="querySubmit();">查询</a></td>
        </tr>     
      </table>      
    </h:panel>
	
	<page:pager id="list_panel" width="100%" buttons="${buttons}" title="结果列表">
		<d:table name="page.result" id="row"  export="false" class="table"	requestURI="orgList.act?exFile=1">
			<d:col  style="width:45"   class="display_centeralign "  title="<input type=\"checkbox\" class=\"in_sss_left\" id=\"checkAll\"/>" >
				<input type="checkbox" name="_chk" id="_chk" class="checkItem in_sss_left" value="${row.ID}"/>
			</d:col>
		    <d:col property="TITLE" class="display_leftalign" title="简称"/>
	    	<d:col property="WEIGHT" class="display_leftalign" title="排序"/>
			<d:col class="display_centeralign"  media="html" title="操作">
			<a href="javascript:void(0);" onmousemove="this.style.color='#ffae00'" style="color: #8090b4;" onmouseout="this.style.color='#8090b4'" onclick="updateRecord('${row.ID}')">[修改]</a>&nbsp;
			<a href="javascript:void(0);" onmousemove="this.style.color='#ffae00'" style="color: #8090b4;" onmouseout="this.style.color='#8090b4'" onclick="showRecord('${row.ID}')">[详细]</a>&nbsp;
			</d:col>	
		</d:table>
    </page:pager>
</form>
<script type="text/javascript">
	function addRecord(){
		var url = "${ctx}/app/cms/channelcms/ipHelpInputArticle.act";
		openEasyWin("winId","新增IP电话帮助",url,"800","520",true);
		//showShelter("800","450",url);
	}
	function updateRecord(id){
		var url = "${ctx}/app/cms/channelcms/ipHelpInput.act?mid=3&cmsId="+id;
		openEasyWin("winId","IP电话帮助",url,"800","520",true);
		//showShelter("502","272",url);
	}
	function showRecord(id){
		var url = "${ctx}/app/cms/channelcms/ipHelpQuery.act?mid=3&cmsId="+id;
		//openEasyWin("winId","IP电话帮助",url,"800","520",true);
		showShelter("502","390",url);
	}
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
					$('#queryForm').attr("action","deleteIpHelp.act");
					$('#queryForm').submit();
				}
			});		
		}
    }
	
	$(document).ready(function(){
		
	});
	
	/*清空充值查询条件 */
	function clearSearch(){
		$("#title").val("");
	}
</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>