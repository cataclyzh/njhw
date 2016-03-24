<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Author: caosq
- Date: 2011-11-24 
- Description: 申请表录入
--%>
<html>
    <head>
        <title>录入申请表</title>
        <%@ include file="/common/include/meta.jsp"%>
        <script type="text/javascript">
        var COMPANYID_INDEX = 1;
        var GROUP_INFO_INDEX = 1;
        var groupCompanyCount = 0;
        
        $(document).ready(function(){
            //初始化,新申请,隐藏证书DN输入框
            $("#personalApply").show();
            $("#companyApply").hide();

            //设置证书数量
            $("#personalcanum").val("${applyModel.applynum}");

            initCheck();
        });

        function initGroupCheck(){            
            $(this).chk_init({ 
                all:'#checkAllGroup',
                item:'input:checkbox[id="groupId"]'
            });
        }

        function initCompanyCheck(){
            $(this).chk_init({ 
                    all:'#checkAllCompany',
                    item:'input:checkbox[id="companyId"]'
            });
        }

        function initCheck(){
        	initCompanyCheck();
        	initGroupCheck();
        }

        function resetCheckAllGroup(){
            var groupIds = $('input:checkbox[id="groupId"]');
            if(groupIds.size() == 0){
            	$("#checkAllGroup").attr("checked", false);
            }
        }

        function resetCheckAllCompany(){
            var companyIds = $('input:checkbox[id="companyId"]');
            if(companyIds.size() == 0){
            	$("#checkAllCompany").attr("checked", false);
            }
        }

        //选择个人帐户或公司帐户
        function changeRelation(obj){
            var val = $(obj).val();
            if(val == '1'){
                $("#personalApply").show();
                $("#companyApply").hide();
            }else if(val == '2'){
                $("#companyApply").show();
                $("#personalApply").hide();
            }
        }
        
        //获取指定行和列的内容
        function getCompanyColumnText(rowId, columnIndex){
            return $("#initCompany tr[id='" + rowId + "']").children("td").eq(columnIndex).text();
        }
        
        function getGroupColumnText(rowId, columnIndex){
            return $("#selectedCompany tr[id='" + rowId + "']").children("td").eq(columnIndex).text();
        }
        
        function showInfo(message){
            easyAlert("提示信息",message,"info");
        }

        //设置选中的供应商为一个分组
        function selectCompany(){
            var checkedCompanyIds = $("input:checked[name='companyId']");

            var seletcedCompanyInfos = '';
            var loginnames = '';
            $.each(checkedCompanyIds, function(index,item){
                var checkedValue = $(item).val();
                var companyInfo = getCompanyColumnText(checkedValue, COMPANYID_INDEX);
                var loginname = companyInfo.split("/")[0];
                if(index > 0){
                    seletcedCompanyInfos += ",";
                    loginnames += ",";
                }
                seletcedCompanyInfos += companyInfo;
                loginnames += loginname;
                $("#initCompany tr[id='" + checkedValue + "']").remove();
            });

            addGroupCompany(loginnames, seletcedCompanyInfos);
            resetCheckAllCompany();
        }

        //设置一个供应商为一个分组
        function oneCompanyOneGroup(){
            var checkedCompanyIds = $("input:checked[name='companyId']");
            if(checkedCompanyIds == null || checkedCompanyIds.length == 0){
                showInfo("没有需要分组的供应商信息！");
            }
            
            var seletcedCompanyInfos = '';
            var loginnames = '';
            $.each(checkedCompanyIds, function(index,item){
                var checkedValue = $(item).val();
                var companyInfo = getCompanyColumnText(checkedValue, COMPANYID_INDEX);
                var loginname = companyInfo.split("/")[0];
                $("#initCompany tr[id='" + checkedValue + "']").remove();
                addGroupCompany(loginname, companyInfo);
            });

            resetCheckAllCompany();          
        }

        //添加供应商信息
        function addGroupCompany(loginname,companyInfos) {
            if(companyInfos != null && companyInfos != "") {
                var id = groupCompanyCount++;
                var str = "<tr id='" + id + "'>";
                str += "<td align='center'><input type='checkbox' name='groupId' id='groupId' value='" + id + "'/><input type='hidden' name='loginname' id='loginname' value='" + loginname + "'/></td>";
                str += "<td>" + companyInfos + "</td>";
                str += "<td align='center'><input type='text' name='canum' id='canum' size='10' maxlength='4'/><font style='color: red'>*</font></td>";
                str += "</tr>";
                $('#selectedCompany').append(str);
                initGroupCheck();
            }
            else {
                showInfo("没有需要分组的供应商信息！");
            }
        }

        //删除供应商信息
        function removeGroupCompany(){
            var checkedGroups = $("input:checked[name='groupId']");

            var seletcedGroups = '';
            $.each(checkedGroups, function(index,item){
                var checkedValue = $(item).val();
                var companyInfo = getGroupColumnText(checkedValue, GROUP_INFO_INDEX);
                if(index > 0){
                    seletcedGroups += ",";
                }
                seletcedGroups += companyInfo;
                $("#selectedCompany tr[id='" + checkedValue + "']").remove();
            });

            restoreCompany(seletcedGroups);
            resetCheckAllGroup();
        }

        //还原供应商信息
        function restoreCompany(selectedGroups){
            if(selectedGroups != null && selectedGroups != "") {
                var companyInfos = selectedGroups.split(",");
                for(var i = 0; i < companyInfos.length; i++){
                    var companyInfo = companyInfos[i];
                    var companyCode = companyInfo.split("/")[0];
                    var str = "<tr id='" + companyCode + "'>";
                    str += "<td align='center'><input type='checkbox' name='companyId' id='companyId' value='" + companyCode + "'/></td>";
                    str += "<td>" + companyInfo + "</td>";
                    str += "</tr>";
                    $('#initCompany').append(str);
                }
                initCompanyCheck();
            }
            else {
                showInfo("没有可删除的分组供应商信息！");
            }
        }

        //删除所有的供应商分组信息
        function removeAllGroupCompany(){
        	var groupCompany = $("#selectedCompany tbody tr");
        	if(groupCompany.size() == 2){
                showInfo("没有可删除的分组供应商信息！");
                return ;
            }
        	$.each(groupCompany, function(index,item){
            	if(index > 1){           	
                    var companyInfo = $(item).children("td").eq(GROUP_INFO_INDEX).text();
                    restoreCompany(companyInfo);
                    $(item).remove();
                }
            });

            resetCheckAllGroup();
        }

        //验证是否为大于零的整数
        function isPositiveInt(num){
        	var pattern = /^[1-9]\d*$/;
        	if(pattern.test(num)){
                return true;
            }
        	else {
                return false;
            }
        }

        function doSubmit(){
            var isEmpty = false;
            var isInvalid = false;
            var countCanum = 0;
            var type = $("input:radio[name='applyModel.type']:checked").val();
            if(type == '1'){
                var canum = $("#personalcanum").val();
                if(canum == ""){
                    isEmpty = true;
                }
                else if(!isPositiveInt(canum)){
                	isInvalid = true;
                }
                else {
                	countCanum = canum;
                }
            }
            else if(type == '2'){
                var canums = $("input[type=text]:[name='canum']");
                if(canums == null || canums.length == 0){
                    showInfo("没有分组供应商信息！");
                    return ;
                }
                    
                $.each(canums, function(index,item){
                    var canum = $(item).val();
                    if(canum == ""){
                        isEmpty = true;
                    }
                    else if(!isPositiveInt(canum)){
                		isInvalid = true;
                    }
                    else {
                    	countCanum += parseInt(canum);
                    }
                });
            }
            
            if(isEmpty){
                showInfo("请输入CA个数！");
                return ;     
            }

            if(isInvalid){
        		showInfo("CA个数必须是大于零的整数！");
                return ;
            }

            if(countCanum - ${applyModel.applynum} != 0){
        		showInfo("CA个数总数必须与申请表证书数量(${applyModel.applynum})一致！");
                return ;
            }
   
            if(!isEmpty && !isInvalid){
                var confirmMsg = "确认提交申请？“确定”继续提交，“取消”重新填写！";
                easyConfirm('提示', confirmMsg, function(result){
                    if(result){
                    	$("#submit").attr("disabled",true);
                    	$("#relationInputForm").submit();
                    }
                });                
            }
        }
        
        </script>
    </head>
    <body>
        <form id="relationInputForm" action="saveRelation.act" method="post">
            <s:hidden name="applyModel.applyno"></s:hidden>
            <s:hidden name="applyModel.caid"></s:hidden>
            <s:hidden name="applyModel.applynum"></s:hidden>
            <h:panel width="100%" title="关联帐号" id="list_panel">
            <table width="100%" align="center" class="form_table" border="0">
                <tr>
                    <td height="50" align="center" ><span style="font-size: 12px;font-weight: bold;">关联帐号</span></td>
                </tr>
                <tr>
                    <td>
                        <s:radio list="#{'1':'个人申请 ','2':'公司申请'}" value="1" onclick="changeRelation(this)" name="applyModel.type"></s:radio>
                    </td>
                </tr>
            </table>
            <div id="personalApply">
            <table width="100%" align="center" class="form_table" border="0">
                <tr>
                    <td width="20%" class="form_label" nowrap>供应商登录用户名/名称：<font style="color: red">*</font></td>
                    <td width="30%" nowrap><s:property value="#session._loginname"/>/<s:property value="#session._username"/></td>    
                    <td width="20%" class="form_label" nowrap>CA个数：<font style="color: red">*</font></td>        
                    <td width="30%" nowrap>                        
                        <s:textfield id="personalcanum" name="personalcanum" theme="simple" cssClass="input180box" readonly="true"></s:textfield>
                    </td>
                </tr>
            </table>
            </div>
            <div id="companyApply">    
            <table id="initCompany" width="100%" align="center" class="form_table" border="0">
                <tr>
                    <td colspan="2"><span style="font-size: 12px;font-weight: bold;">待分组供应商列表</span></td>
                </tr>
                <tr>
                    <th width="5%" align="center"><input type="checkbox" id="checkAllCompany" name="checkAllCompany" title="全选"></th>
                    <th width="95%">供应商登录名/名称</th>
                </tr>
                <tbody id="initCompanyBody">
                <s:iterator value="userInfos" var="userInfo">
                <tr id="<s:property value="%{#userInfo.LOGINNAME}"/>">
                    <td align="center"><input type="checkbox" id="companyId" name="companyId" value="<s:property value="%{#userInfo.LOGINNAME}"/>"/></td>
                    <td><s:property value="%{#userInfo.LOGINNAME}"/>/<s:property value="%{#userInfo.USERNAME}"/></td>
                </tr>
                </s:iterator>
                </tbody>
            </table>            
            <table class="form_table" border="0" style="width:100%">
                <tr>
                    <td style="text-align: center; height: 35px">
                        <a id="allToOneGroup" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="selectCompany();">添加至一个分组</a> &nbsp;&nbsp;
                        <a id="oneToOneGroup" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="oneCompanyOneGroup();">一个供应商设置一个分组</a> &nbsp;&nbsp;
                    </td>
                </tr>
            </table>    
            <table id="selectedCompany" width="100%" align="center" class="form_table" border="0">
                <tr>
                    <td colspan="3"><span style="font-size: 12px;font-weight: bold;">已分组供应商列表</span></td>
                </tr>
                <tr>
                    <th width="5%"><input type="checkbox" id="checkAllGroup" name="checkAllGroup" title="全选"></th>
                    <th width="65%">供应商登录名/名称</th>
                    <th width="30%">CA个数</th>
                </tr>
                <tbody id="selectedCompanyBody">
                </tbody>
            </table>
                        
            <table class="form_table" border="0" style="width:100%">
                <tr>
                    <td style="text-align: center; height: 35px">
                        <a id="delete" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="removeGroupCompany();">删除</a> &nbsp;&nbsp;
                        <a id="deleteAll" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="removeAllGroupCompany();">全部删除</a> &nbsp;&nbsp;
                    </td>
                </tr>
            </table>
            </div>
            </h:panel>    
            <table class="form_table" border="0" style="width:100%">
                <tr>
                    <td style="text-align: center; height: 35px">                
                        <a id="submit" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="doSubmit();">提交</a> &nbsp;&nbsp;
                        <span id="loadingdiv" class="icon-loding" style="display: none">正在执行请稍后</span>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>