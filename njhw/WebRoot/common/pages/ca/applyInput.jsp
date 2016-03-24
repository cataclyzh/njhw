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

        //只允许输入数字--add by csq 2011-11-28
        jQuery.validator.addMethod("positiveInt", 
              function(value, element) {  
                  var pattern = /^[1-9]\d*$/;
                  return this.optional(element) || (pattern.test(value));    
              }, "请输入大于零的整数!"
        );
    
        //传真号码--add by csq 2011-11-28
        jQuery.validator.addMethod("fax", 
              function(value, element) {
                  var pattern = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
                    return this.optional(element) || (pattern.test(value));    
              }, "请输入正确的传真号码!"
        );
    
        //英文/拼音--add by csq 2011-11-28
        jQuery.validator.addMethod("alphabet", 
              function(value, element) {
                  var pattern = /^(\w|\s)+$/;
                    return this.optional(element) || (pattern.test(value));    
              }, "请输入英文/拼音简称!"
        );
    
        //号码--add by csq 2011-11-28
        jQuery.validator.addMethod("code", 
              function(value, element) {
                  var pattern = /^[A-Za-z0-9]+$/;
                    return this.optional(element) || (pattern.test(value));    
              }, "请输入正确的号码!"
        );
          
        function showInfo(message){
            easyAlert("提示信息",message,"info");
        }
        
        $(document).ready(function(){

            $("#cadn").hide();
            $("#cadn").val("none");
            $("#cadnRequired").hide();

            $("#orgidname").hide();
            $("#orgidname").val("none");
            $("#orgidnameRequired").hide();
                       
            <c:if test="${countUserApply ge 10 }">
                showInfo('一个用户最多允许申请10次！');
            </c:if>
            
            $("#applyInputForm").validate({
                onsubmit:true,
                errorElement :"div",
                errorPlacement: function(error, element) { 
                    error.appendTo(element.parent()); 
                },
                rules: {
                    applynum:{
                        required:true,
                        positiveInt:true
                    },
                    caterm:{
                        required:true
                    },
                    catype:{
                        required:true
                    },
                    applytype:{
                        required:true
                    },
                    cadn:{
                        required:true
                    },
                    orgname:{
                        required:true
                    },
                    orgnameen:{
                        required:true,
                        alphabet:true
                    },
                    orgidtype:{
                        required:true
                    },
                    orgidname:{
                        required:true
                    },
                    orgidnum:{
                        required:true,
                        code:true
                    },
                    handler:{
                        required:true
                    },
                    handleridtype:{
                        required:true
                    },
                    handleridnum:{
                        required:true,
                        code:true
                    },
                    handlertel:{
                        required:true,
                        isTelPhone:true
                    },
                    handlerfax:{
                        required:true,
                        fax:true
                    },
                    handlermail:{
                        required:true,
                        email:true
                    },
                    handleraddr:{
                        required:true
                    },
                    handlerpostcode:{
                        required:true,
                        isZipCode:true
                    }
                },
                messages: {
                    applynum:{
                        required:"证书数量不能为空!"
                    },
                    caterm:{
                        required:"证书期限不能为空!"
                    },
                    catype:{
                        required:"证书种类不能为空!"
                    },
                    applytype:{
                        required:"业务类型不能为空!"
                    },
                    cadn:{
                        required:"证书DN不能为空!"
                    },
                    orgname:{
                        required:"机构名称不能为空!"
                    },
                    orgnameen:{
                        required:"英文/拼音简称不能为空!"
                    },
                    orgidtype:{
                        required:"机构证件类型不能为空!"
                    },
                    orgidname:{
                        required:"机构证件类型描述不能为空!"
                    },
                    orgidnum:{
                        required:"机构证件号码不能为空!"
                    },
                    handler:{
                        required:"经办人姓名不能为空!"
                    },
                    handleridtype:{
                        required:"经办人证件类型不能为空!"
                    },
                    handleridnum:{
                        required:"经办人证件号码不能为空!"
                    },
                    handlertel:{
                        required:"电话不能为空!"
                    },
                    handlerfax:{
                        required:"传真不能为空!"
                    },
                    handlermail:{
                        required:"电子邮件不能为空!"
                    },
                    handleraddr:{
                        required:"USB Key寄送地址不能为空!"
                    },
                    handlerpostcode:{
                        required:"邮政编码不能为空!"
                    }
                },
                submitHandler: function(form) {
                	$("#submit").attr("disabled",true);
                    form.submit();
                }
            });
        });

        function changeApplyType(){
            var applyType = $("input:radio[name='applytype']:checked").val();
            if(applyType == "0"){
                $("#cadn").val("none");
                $("#cadn").hide();
                $("#cadnRequired").hide();
                $("#cadn ~ div").hide(); //隐藏错误提示                   
            }
            else {
                $("#cadn").val("");
                $("#cadn").show();
                $("#cadnRequired").show();
            } 
        }

        function changeOrgidType(){
            var orgidType = $("input:radio[name='orgidtype']:checked").val();
            if(orgidType == "2"){
                $("#orgidname").val("");
                $("#orgidname").show(); 
                $("#orgidnameRequired").show();                
            }
            else {
                $("#orgidname").val("none");
                $("#orgidname").hide();
                $("#orgidnameRequired").hide();
                $("#orgidname ~ div").hide(); //隐藏错误提示  
            } 
        }
        
        function doNext(){
            $("#applyInputForm").submit();     
        }

        function nextPage(){
            this.location = "${ctx}/common/ca/apply/initStamp.act";
        }

        function downloadAuthorityBook(){
        	 $("#downloadForm").submit();  
        }
        
        </script>
    </head>
    <body>
        <form id="applyInputForm" action="saveApply.act" method="post">
            <h:panel width="100%" title="申请表" id="list_panel">
            <table width="100%" align="center" class="form_table" border="0">
                <tr>
                    <td height="50" colspan="7" align="center" >
                        <span style="font-size: 12px;font-weight: bold;">证书申请信息</span>
                    </td>
                </tr>
                <tr>
                    <td width="5%" class="form_label" rowspan="3" align="center">
                        <span style="font-size: 12px;font-weight: bold;">证<br/>书<br/>申<br/>请<br/>信<br/>息</span>
                    </td>
                    <td class="form_label" nowrap>
                                                                申请日期：<font style="color: red">*</font>
                    </td>
                    <td nowrap>
                        <f:formatDate value='${applydate}' pattern= 'yyyy年MM月dd日'/>
                    </td>
                    <td class="form_label" nowrap>
                                                               证书数量：<font style="color: red">*</font>
                    </td>                
                    <td nowrap>
                        <s:textfield name="applynum" theme="simple" cssClass="input180box" maxLength="4"></s:textfield>
                    </td>
                    <td class="form_label" nowrap>
                                                               证书期限：<font style="color: red">*</font>
                    </td>
                    <td nowrap>
                        <s:radio id="caterm" name="caterm" list="#application.DICT_CA_CATERM" listKey="dictcode" listValue="dictname" ></s:radio>
                    </td>
                </tr>
                <tr>
                    <td class="form_label" nowrap align="left">
                                                             证书种类：<font style="color: red">*</font>
                    </td>
                    <td colspan="5">
                        <s:radio id="catype" name="catype" list="#application.DICT_CA_CATYPE" listKey="dictcode" listValue="dictname"></s:radio>
                    </td>
                </tr>
                <tr>    
                    <td class="form_label" id="applytypetext">
                                                             业务类型：<font style="color: red">*</font>
                    </td>
                    <td colspan="2" nowrap>
                        <s:radio id="applytype" name="applytype" list="#application.DICT_CA_APPLYTYPE" listKey="dictcode" listValue="dictname" onclick="changeApplyType()"></s:radio>
                    </td>    
                    <td class="form_label" id="cadntext">
                                                              证书DN：<span id="cadnRequired"><font style="color: red">*</font></span>
                    </td>
                    <td colspan="2" nowrap>
                        <s:textfield name="cadn" theme="simple" cssClass="input180box" maxlength="50"></s:textfield>
                    </td>
                </tr>
                <tr>
                    <td class="form_label" rowspan="3" align="center">
                        <span style="font-size: 12px;font-weight: bold;">申<br/>请<br/>机<br/>构<br/>信<br/>息</span>
                    </td>
                    <td class="form_label">
                                                                 机构名称：<font style="color: red">*</font>
                    </td>
                    <td colspan="3">
                        <s:textfield name="orgname" theme="simple" cssClass="input180box" maxlength="75"></s:textfield>
                    </td>
                    <td class="form_label">
                                                                  英文/拼音简称：<font style="color: red">*</font>
                    </td>
                    <td>
                        <s:textfield name="orgnameen" theme="simple" cssClass="input180box" maxlength="150"></s:textfield>
                    </td>
                </tr>
                <tr>
                    <td class="form_label">
                                                                    机构证件类型：<font style="color: red">*</font>
                    </td>
                    <td colspan="3">
                        <s:radio id="orgidtype" name="orgidtype" theme="simple" list="#application.DICT_CA_ORGIDTYPE" listKey="dictcode" listValue="dictname" onclick="changeOrgidType()"></s:radio>                 
                    </td>                   
                    <td class="form_label">
                                                                   请注明：<span id="orgidnameRequired"><font style="color: red">*</font></span>
                    </td>                 
                    <td>
                        <s:textfield name="orgidname" theme="simple" cssClass="input180box" maxlength="75"></s:textfield>
                    </td>
                </tr>
                <tr>    
                    <td class="form_label">
                                                                  机构证件号码：<font style="color: red">*</font>
                    </td>
                    <td colspan="5">
                        <s:textfield name="orgidnum" theme="simple" cssClass="input180box" maxlength="40"></s:textfield>
                    </td>
                </tr>
                <tr>
                    <td class="form_label" rowspan="6" align="center">
                        <span style="font-size: 12px;font-weight: bold;">机<br/>构<br/>经<br/>办<br/>人<br/>信<br/>息</span>
                    </td>
                    <td class="form_label">
                                                                 经办人姓名：<font style="color: red">*</font>
                    </td>
                    <td colspan="5">
                        <s:textfield name="handler" theme="simple" cssClass="input180box" maxlength="20"></s:textfield>
                    </td>
                </tr>
                <tr>    
                    <td class="form_label">
                                                                    经办人证件类型：<font style="color: red">*</font>
                    </td>
                    <td colspan="5">
                        <s:radio id="handleridtype" name="handleridtype" list="#application.DICT_CA_HANDLERIDTYPE" listKey="dictcode" listValue="dictname"></s:radio>
                    </td>
                </tr>
                <tr>    
                    <td class="form_label">
                                                                  经办人证件号码：<font style="color: red">*</font>
                    </td>
                    <td colspan="5">
                        <s:textfield name="handleridnum" theme="simple" cssClass="input180box" maxlength="40"></s:textfield>
                    </td>
                </tr>
                <tr>
                    <td class="form_label">
                                                              电话：<font style="color: red">*</font>
                    </td>
                    <td colspan="2">
                        <s:textfield name="handlertel" theme="simple" cssClass="input180box" maxlength="20"></s:textfield>
                    </td>
                    <td class="form_label">
                                                                传真：<font style="color: red">*</font>
                    </td>
                    <td colspan="2">
                        <s:textfield name="handlerfax" theme="simple" cssClass="input180box" maxlength="20"></s:textfield>
                    </td>
                </tr>
                <tr>
                    <td class="form_label">
                                                                 电子邮件：<font style="color: red">*</font>
                    </td>
                    <td colspan="5">
                        <s:textfield name="handlermail" theme="simple" cssClass="input180box" maxlength="40"></s:textfield>
                    </td>
                </tr>
                <tr>
                    <td class="form_label">
                        <span style="font-weight: bold;color: red">USB Key寄送地址：</span><font style="color: red">*</font>
                    </td>
                    <td colspan="2">
                        <s:textfield name="handleraddr" theme="simple" cssClass="input180box" maxlength="100"></s:textfield>
                    </td>
                    <td class="form_label">
                        <span style="font-weight: bold;color: red">邮政编码：</span><font style="color: red">*</font>
                    </td>
                    <td colspan="2">
                        <s:textfield name="handlerpostcode" theme="simple" cssClass="input180box" maxlength="10"></s:textfield>
                    </td>
                </tr>
                <tr>
                    <td class="form_label" rowspan="2" align="center">
                        <span style="font-size: 12px;font-weight: bold;">申<br/>请<br/>机<br/>构<br/>声<br/>明</span>
                    </td>
                    <td class="form_label" colspan="6">
                                                                    本机构承诺以上所填写的申请资料真实、有效。本机构已认真阅读并同意遵守中金金融认证中心有限公司（CFCA）网站（<a href="http://www.cfca.com.cn" target="top">http://www.cfca.com.cn</a>)发布的《数字证书服务协议》、《电子认证业务规则（CPS））》中规定的相关义务。
                    </td>
                </tr>
                <tr>
                    <td class="form_label">
                                                                   申请机构盖章：
                    </td>
                    <td colspan="2">&nbsp;</td>
                    <td class="form_label">
                                                                   日期：
                    </td>
                    <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="7"><span style="font-size: 12px;font-weight: bold;">注：本表需加盖申请机构公章。申请机构须同时提供机构证件复印件（加盖公章）、经办人身份证件复印件、机构授予经办人的<a href="javascript:void(0);" onclick="downloadAuthorityBook()" title="点击下载">授权书(点击下载)</a>原件。</span></td>
                </tr>
                <tr>
                    <td colspan="7"><span style="font-size: 12px;font-weight: bold;"><f:message key="common.ca.contact_us"/></span></td>
                </tr>
            </table>
            </h:panel>
            
            <c:if test="${countUserApply lt 10 }">
            <table class="form_table" border="0" style="width:100%">
                <tr>
                    <td colspan="4" style="text-align: center; height: 35px">
                        <a id="submit" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="doNext();">下一步</a> &nbsp;&nbsp;
                        <a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#applyInputForm').resetForm();">重置</a>
                        <span id="loadingdiv" class="icon-loding" style="display: none">正在执行请稍后</span>
                    </td>
                </tr>
            </table>
            </c:if>
        </form>
        <form id="downloadForm" name="downloadForm" action="downloadAuthorityBook.act" method="post" autocomplete="off">
        </form>
    </body>
</html>