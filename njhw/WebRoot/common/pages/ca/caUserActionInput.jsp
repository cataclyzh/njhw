<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Author: csq
- Date: 2011-12-16
- Description: CA用户操作功能
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>CA用户操作功能</title>
    <%@ include file="/common/include/meta.jsp" %>
    <script type="text/javascript">
    $(document).ready(function(){
        $("#userActionModel_loginname").focus();

        initActionCheck();

    });
    
    function initActionCheck(){            
        $(this).chk_init({ 
            all:'#checkAllAction',
            item:'input:checkbox[name="userActionModel.actioncodes"]'
        });
    }
    
    function showInfo(message){
        easyAlert("提示信息",message,"info");
    }
    
    function doSubmit(){
        var checkedActioncodes = $("input:checked[name='userActionModel.actioncodes']");
        var loginname = $("#userActionModel_loginname").val();
        if(loginname == ''){
            showInfo("请输入用户登录名！");
        }
        else if(checkedActioncodes == null || checkedActioncodes.length == 0){
            showInfo("没有选择操作信息！");
        }
        else {
            $("#loadingdiv").show();
            $("#submit").attr("disabled",true);
            $("#userActionForm").submit();
        }        
    }

    function closeDetail(){
        closeEasyWin('winId');
    }

    //清空操作列表
    function clearActionList(){
        var actionList = $("#actionList tbody tr");
        $.each(actionList, function(index,item){
            $(item).remove();
        });
    }

    //显示操作列表
    function initActionList(){
        if($("#userActionModel_loginname").val() == ""){
             return;
        }
        else if(!isUserExist()){
            showInfo("输入的用户登录名不存在,请重新输入！");
            $("#userActionModel_loginname").val("");
            $("#userActionModel_loginname").focus();
            return;
        }
        
        clearActionList(); //清空操作列表
        
        $.getJSON("${ctx}/common/caUserActionAction/getUserNotHaveActionList.act",{loginname:$("#userActionModel_loginname").val()},
                function(data){ 
                    $.each(data,function(i){
                        var str = "<tr>";
                        str += "<td align='center'>" + (i + 1) + "</td>";
                        str += "<td align='center'><input type='checkbox' name='userActionModel.actioncodes' value='" + data[i].ACTIONCODE + "'/></td>";
                        str += "<td align='left'>" + (data[i].ACTIONCODE) + "</td>";
                        str += "<td align='left'>" + (data[i].ACTIONNAME) + "</td>";
                        str += "<td align='left'>" + (data[i].ACTIONDESC != null ? data[i].ACTIONDESC : "") + "</td>";
                        str += "</tr>";
                        $('#actionList').append(str);
                }) 
        });
    }

    //判断用户名是否存在
    function isUserExist(){
        var exist = true;
        $.ajax({
            type : "POST",
            url : "${ctx}/common/caUserActionAction/isUserExist.act",
            data : "loginname=" + $("#userActionModel_loginname").val(),
            async : false, 
            success : function(msg){
                if(msg != "true"){
                    exist = false;
                }
            }
        });

        return exist;
    }

    <c:if test="${isSuc=='true'}">
    easyAlert('提示信息','保存成功！','info',function(){closeEasyWin('winId');});
    </c:if>
    <c:if test="${isSuc=='false'}">
       easyAlert('提示信息','保存失败！','error');
    </c:if>
    </script>    
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
    <form id="userActionForm" action="save.act" method="post">
        <h:panel width="100%" title="CA用户操作" id="form_panel">
            <table width="100%" align="center" class="form_table" border="0">
                <tr>
                    <td width="20%" class="form_label" nowrap>用户登录名：<font style="color: red">*</font></td>
                    <td width="30%">
                        <s:textfield name="userActionModel.loginname" theme="simple" cssClass="input180box" maxLength="40" onblur="initActionList();"></s:textfield>
                    </td>
                    <td width="20%" class="form_label" nowrap>是否应用CA：<font style="color: red">*</font></td>
                    <td width="30%">
                        <s:select list="#application.DICT_CA_ISVALIDCA" name="userActionModel.isuseca" 
                                 emptyOption="false" listKey="dictcode" listValue="dictname" cssClass="input180box"/>
                    </td>
                </tr>
            </table>    
        </h:panel>
        <h:page id="list_panel" width="100%" title="操作列表">        
            <table id="actionList" width="100%" align="center" class="form_table" border="0">
                <thead>    
                <tr>
                    <th width="5%">序号</th>
                    <th width="5%"><input type="checkbox" id="checkAllAction" name="checkAllAction" title="全选"></th>
                    <th width="10%">操作编码</th>
                    <th width="20%">操作名称</th>
                    <th width="60%">操作说明</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </h:page>
        <table class="form_table" border="0" style="width:100%">
            <tr>
                <td style="text-align: center; height: 35px">                
                    <a id="submit" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="doSubmit();">提交</a> &nbsp;&nbsp;&nbsp;&nbsp;
                    <a id="close" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:closeDetail();">关闭</a>   
                    <span id="loadingdiv" class="icon-loding" style="display: none">正在执行请稍后</span>
                </td>
            </tr>
        </table>        
    </form>
</body>
</html>