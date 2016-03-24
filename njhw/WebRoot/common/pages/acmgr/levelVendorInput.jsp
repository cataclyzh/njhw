<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>更改供应商级别</title>
    <%@ include file="/common/include/meta.jsp" %>
    
    <script type="text/javascript">

    $(document).ready(function() {
        initLevelList();
    });

    function initLevelList(){
        var url = "${ctx}/common/levelMgr/getLevelList.act";
        var company = $("#levelModel_company").val();
        var levelCode = $("#levelModel_levelCode").val();
        $.getJSON(url, {company:company},
            function(data){ 
                $.each(data,function(i){
                    var value = data[i].LEVEL_CODE;
                    var name = data[i].LEVEL_NAME;
                    if(levelCode != value){    
                        $("#changeLevelCode").append("<option value='" + value + "'>" + name + "</option>");
                    }
            });
        });
    }

    function save(){
        $('#inputForm').submit();
    }

    function closeWin(){
        closeEasyWin('updateLevel');
    }

    </script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<form id="inputForm" action="updateVendorLevel.act" method="post" autocomplete="off">
    <s:hidden name="levelModel.orgIds"/>
    <s:hidden name="levelModel.company"/>
    <s:hidden name="levelModel.levelCode"/>
    <table align="center" border="0" width="100%" class="form_table">
        <tr>
            <td width="20%" class="form_label">级别：<font style="color:red">*</font></td>
            <td width="80%">
                <select id="changeLevelCode" name="changeLevelCode"></select>
            </td>
        </tr>
    </table>
    <table align="center" border="0" width="100%" class="form_table">  
        <tr class="form_bottom">
            <td>
                   <a href="javascript:void(0);" id="submit" class="easyui-linkbutton" iconCls="icon-save" onclick="save();">保存</a>
                   &nbsp;&nbsp;&nbsp;&nbsp;
                   <a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#inputForm').resetForm();">重置</a>
                   &nbsp;&nbsp;&nbsp;&nbsp;
                   <a href="javascript:void(0);" id="closeBtn" class="easyui-linkbutton" iconCls="icon-save" onclick="closeWin();">关闭</a>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
    easyAlert('提示信息','更改级别成功！','info',
       function(){closeEasyWin('updateLevel');}
    );
</c:if>

<c:if test="${isSuc=='false'}">
    easyAlert('提示信息','更改级别失败！','error');
</c:if>
</script>