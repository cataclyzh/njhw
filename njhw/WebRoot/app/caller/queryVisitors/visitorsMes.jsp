<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: SQS
- Date: 2013-03-17 15:37:15
- Description: 页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>访客列表</title>	
	<%@ include file="/common/include/meta.jsp" %>
</head>

<body topmargin="0" leftmargin="0" rightmargin="0" >
 	<table align="center" id="hide_table" border="0" width="100%" class="form_table">
        <tr>
            <td class="form_label">访客名称：</td>
            <td>
                <s:property value="vmVisitorinfo.viName"/>
            </td>
            <td class="form_label">访客类型：</td>
            <td>
            	<c:if test="${vmVisitorinfo.viType == '01'}">
            	 政府单位
            	</c:if>
            	<c:if test="${vmVisitorinfo.viType == '02'}">
            	公众个人
            	</c:if>
            	<c:if test="${vmVisitorinfo.viType == '03'}">
            	企业客户
            	</c:if>
            	<c:if test="${vmVisitorinfo.viType == '04'}">
            	服务业人员
            	</c:if>
            </td>
        </tr> 
        <tr>
            <td class="form_label">身份证号码：</td>
            <td>
                <s:property value="vmVisitorinfo.residentNo"/>
            </td>
            <td class="form_label">手机号码：</td>
            <td>
                <s:property value="vmVisitorinfo.viMobile"/>
            </td>
        </tr> 
        <tr>
            <td class="form_label">邮箱：</td>
            <td>
                <s:property value="vmVisitorinfo.viMail"/>
            </td>
            <td class="form_label">车牌号码：</td>
            <td>
                <s:property value="vmVisitorinfo.plateNum"/>
            </td>
        </tr> 
        <tr>
            <td class="form_label">网站用户名：</td>
            <td>
                <s:property value="vmVisitorinfo.viWname"/>
            </td>
            <td class="form_label">来源：</td>
            <td>
                <s:property value="vmVisitorinfo.viOrigin"/>
            </td>
        </tr> 
        <tr>
            <td class="form_label">是否认证：</td>
            <td>
                <s:property value="vmVisitorinfo.viFlag"/>
            </td>
            <td class="form_label">有效与否：</td>
            <td>
            <c:if test="${vmVisitorinfo.useFlag == '1'}">
                                 有效
            </c:if>
            </td>
        </tr> 
        <tr>
            <td class="form_label">登记人：</td>
            <td>
                <s:property value="vmVisitorinfo.insertId"/>
            </td>
            <td class="form_label">登记时间：</td>
            <td>
                <s:date name="vmVisitorinfo.insertDate" format="yyyy年MM月dd日 hh时mm分" />
            </td>
        </tr> 
        <tr>
            <td class="form_label">修改人：</td>
            <td>
                <s:property value="vmVisitorinfo.modifyId"/>
            </td>
            <td class="form_label">修改时间：</td>
            <td>
                <s:date name="vmVisitorinfo.modifyDate" format="yyyy年MM月dd日 hh时mm分" />
            </td>
        </tr> 
 </table>
</body>
</html>