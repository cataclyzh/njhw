<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2012-08-02
- Description: 级别列表页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>级别列表</title>    
    <%@ include file="/common/include/meta.jsp" %>
    
    <script src="${ctx}/scripts/custom/display.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(document).ready(function() {
        var chk_options = { 
            all:'#checkAll',
            item:'.checkItem'
        };
        $(this).chk_init(chk_options);
        $("#levelName").focus();
        changebasiccomponentstyle();            
        changedisplaytagstyle();
        changecheckboxstyle();

        initQueryToggle("listCnt", "hide_table", "");
    });
    
    function showVendor(levelCode){
        var url = "${ctx}/common/levelMgr/levelVendorList.act?seed";
        var params = "&levelCode=" + levelCode;
        url += params;
        parent.frames.ifrmVendor.location.href = url;
    }
    
    function addRecord(){
        var url = "${ctx}/common/levelMgr/input.act?seed";
        var params = "";
        url += params;

        openEasyWin("levelInput","录入 级别信息",url,"800","450",true);
    }
    
    function updateRecord(id){
        var url = "${ctx}/common/levelMgr/input.act?seed";
        var params = "&levelId=" + id;
        url += params;
        
        openEasyWin("levelInput","修改级别信息",url,"800","450",true);
    }

    function detail(id){
        var url = "${ctx}/common/knowledge/detail.act?seed";
        var params = "&levelId=" + id;
        url += params;
        
        openEasyWin("levelDetail","级别信息",url,"800","450",true);
    }
    
    function delRecord(){
        if($("input[@type=checkbox]:checked").size()==0){
            easyAlert('提示信息','请勾选要删除的记录！','info');
        }else{
            //获取已经被使用的级别编码
            var usedLevelCodes = getUsedLevelCode();
            if(usedLevelCodes != ""){
                easyAlert('提示信息', "级别编号:" + usedLevelCodes + ', 已经被使用，不能被删除！请重新选择！','info');
                return ;
            }
                
            easyConfirm('提示', '确定删除？', function(r){
                if (r){
                    $('#queryForm').attr("action","deleteLevels.act");
                    $('#queryForm').submit();
                }
            });        
        }
    }

    function getUsedLevelCode(){
        var levelCodes = "";
        var chkLevelId = $("input:checked[name='_chk']");
        $.each(chkLevelId, function(i,levelCode){
            if(i > 0){
                levelCodes += ",";
            }
            levelCodes += $("#levelCode_" + $(levelCode).val()).val();
        });

        var url = "${ctx}/common/levelMgr/queryUsedLevelCodeList.act";
        var usedLevelCodes = "";
        $.ajaxSettings.async = false;
        $.getJSON(url, {levelCodes:levelCodes},
            function(data){ 
                $.each(data,function(i){
                    if(i > 0){
                        usedLevelCodes += ",";
                    }
                    usedLevelCodes += data[i].EXTF4;
            });
        });

        return usedLevelCodes;
    }
    </script>
<style>
</style>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" >
    <form id="queryForm" action="levelList.act" method="post" autocomplete="off">
    <s:hidden name="levelModel.company"/>
     <h:panel id="query_panel" width="100%" title="查询条件">    
      <table align="center" id="hide_table" border="0" width="100%" class="form_table">
        <tr>
          <td class="form_label" width="20%" align="left">级别名称：</td>
          <td width="30%">
           <s:textfield name="levelModel.levelName" theme="simple" size="10" maxLength="10"/>  
          </td>
          <td class="form_label" width="20%" align="left">级别编码：</td>
          <td width="30%">
           <s:textfield name="levelModel.levelCode" theme="simple" size="10" maxLength="20"/>  
          </td>
        </tr>     
        <tr>
          <td colspan="4" class="form_bottom">
            <s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="width:20px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
              <a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="querySubmit();">查询</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="resetCheckboxForm('#queryForm');">重置</a>            
          </td>
        </tr>
      </table>      
    </h:panel>
    <div id="listCnt" style="OVERFLOW-y:auto;">
    <h:page id="list_panel" width="100%" title="结果列表" buttons="add,del">        
        <d:table name="page.result" id="row" export="false" class="table">
            <d:col style="width:30" title="序号" class="display_centeralign">
                ${row_rowNum+((page.pageNo-1)*page.pageSize)}
            </d:col>
            <d:col style="width:45" class="display_centeralign" title="选择<input type='checkbox' id='checkAll'/>" >
                <input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.LEVEL_ID}"/>
                <input type="hidden" name="levelCode_${row.LEVEL_ID}" id="levelCode_${row.LEVEL_ID}" value="${row.LEVEL_CODE}"/>
            </d:col>
            <d:col property="LEVEL_CODE" class="display_centeralign" title="级别编码"/>    
            <d:col property="LEVEL_NAME" class="display_leftalign" title="级别名称"/>    
            <d:col property="REMARK" class="display_leftalign" title="说明"/>
            <d:col class="display_centeralign"  media="html" title="操作">
                <a href="javascript:void(0);" onclick="updateRecord('${row.LEVEL_ID}')">[修改]</a>
                &nbsp;
                <a href="javascript:void(0);" onclick="showVendor('${row.LEVEL_CODE}')">[供应商授权]</a>
            </d:col>                
        </d:table>
   </h:page>
   </div>
   </form>
</body>
</html>
