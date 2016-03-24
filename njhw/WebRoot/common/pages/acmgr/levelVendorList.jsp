<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2012-08-02
- Description: 指定级别的供应商列表页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>指定级别的供应商列表</title>    
    <%@ include file="/common/include/meta.jsp" %>
    <script src="${ctx}/scripts/custom/display.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(document).ready(function() {
        var chk_options = { 
            all:'#checkAll',
            item:'.checkItem'
        };
        $(this).chk_init(chk_options);
        $("#entityOrg_orgname").focus();
        changebasiccomponentstyle();            
        changedisplaytagstyle();
        changecheckboxstyle();

        initQueryToggle("listCnt", "hide_table", "footId");
    });
    
    function showInfo(message){
        easyAlert("提示信息",message,"info");
    }

    function showUpdateLevel(){
        var orgIds = "";
        var chkOrgId = $("input:checked[name='_chk']");
        $.each(chkOrgId, function(i,orgId){
            if(i > 0){
                orgIds += ",";
            }
            orgIds += $(orgId).val();
        });

        if(orgIds == ""){
            showInfo("请选择供应商!");
        }
        else {
            var levelCode = $("#levelCode").val();
            var url = "${ctx}/common/levelMgr/showUpdateVendorLevel.act?orgIds="+orgIds+"&levelCode="+levelCode;
            openEasyWin("updateLevel","更改级别",url,"400","300",true);
        }
    }

    </script>
<style>
</style>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" >
    <form id="queryForm" action="levelVendorList.act" method="post" autocomplete="off">
    <s:hidden name="levelCode"/>
     <h:panel id="query_panel" width="100%" title="查询条件">    
      <table align="center" id="hide_table" border="0" width="100%" class="form_table">
        <tr>
          <td class="form_label" width="20%" align="left">机构名称：</td>
          <td width="30%">
           <s:textfield name="entityOrg.orgname" theme="simple" size="18"/>  
          </td>
          <td class="form_label" width="20%" align="left">机构编码：</td>
          <td width="30%">
            <s:textfield name="entityOrg.orgcode" theme="simple" size="18"/>  
          </td>
        </tr> 
        <tr>
          <td class="form_label" width="20%" align="left">纳税人名称：</td>
          <td width="30%">
           <s:textfield name="entityOrg.taxname" theme="simple" size="18" />  
          </td>
          <td class="form_label" width="20%" align="left">纳税人识别号：</td>
          <td width="30%" >
           <s:textfield name="entityOrg.taxno" theme="simple" size="18" />  
          </td>
        </tr>  
        <tr>
          <td colspan="4" class="form_bottom">
              <s:textfield name="pageVendor.pageSize" id="pageSize" theme="simple" cssStyle="width:20px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
              <a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="querySubmit();">查询</a>&nbsp;&nbsp;
              <a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#queryForm').resetForm();">重置</a> &nbsp;&nbsp;
          </td>
        </tr>
      </table>      
    </h:panel>
    <div id="listCnt" style="OVERFLOW-y:auto;">
    <h:page pageName="pageVendor" id="list_panel" width="100%" title="结果列表">
        <d:table name="pageVendor.result" id="row" export="false" class="table">
            <d:col style="width:30" title="序号" class="display_centeralign">
                ${row_rowNum+((page.pageNo-1)*page.pageSize)}
            </d:col>
            <d:col style="width:45" class="display_centeralign" title="选择<input type='checkbox' id='checkAll'/>" >
                <input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.ORGID}"/>
            </d:col>
            <d:col property="ORGCODE" class="display_centeralign" title="机构编码"/>
            <d:col property="ORGNAME" class="display_leftalign" title="机构名称"/>
            <d:col property="TAXNO" class="display_centeralign" title="纳税人识别号"/>
        </d:table>
    </h:page>
    </div>
    </form>
    <div id="footId" style="text-align:center;">
        <a href="javascript:void(0);" class="easyui-linkbutton" id="updateLevel" iconCls="icon-search" onclick="showUpdateLevel();">更改级别</a>
    </div>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
