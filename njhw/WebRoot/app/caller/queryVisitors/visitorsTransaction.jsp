<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: SQS
- Date: 2013-03-29 15:37:15
- Description: 访问事务
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>访问事务</title>	
	<%@ include file="/common/include/meta.jsp" %>
</head>

<body topmargin="0" leftmargin="0" rightmargin="0" >
 	<table align="center" id="hide_table" border="0" width="100%" class="form_table">
        <tr>
            <td class="form_label">访客姓名：</td>
            <td>${vmVisit.viName}</td>
            <td class="form_label">访问人数：</td>
            <td>${vmVisit.vsNum}</td>
        </tr> 
        <tr>
            <td class="form_label">受访者姓名：</td>
            <td>${vmVisit.userName}</td>
            <td class="form_label">机构名称：</td>
            <td>${vmVisit.orgName}</td>
        </tr> 
        <tr>
            <td class="form_label">访问事由：</td>
            <td colspan="3">${vmVisit.vsInfo}</td>
        </tr> 
        <tr>
            <td class="form_label">预约访问开始时间：</td>
            <td>${vmVisit.vsSt}</td>
            <td class="form_label">预约访问结束时间：</td>
            <td>${vmVisit.vsEt}</td>
        </tr> 
        <tr>
            <td class="form_label">实际访问开始时间：</td>
            <td>${vmVisit.vsSt1}</td>
            <td class="form_label">第一次离开时间：</td>
            <td>${vmVisit.vsSt1Lea}</td>
        </tr> 
        <tr>
            <td class="form_label">第二次进入时间：</td>
            <td>${vmVisit.vsSt1Acc}</td>
            <td class="form_label">实际访问结束时间：</td>
            <td>${vmVisit.vsEt1}</td>
        </tr> 
        <tr>
            <td class="form_label">是否预约：</td>
            <td>
                <c:if test="${vmVisit.vsYn == '0'}">
					否
				</c:if>
                <c:if test="${vmVisit.vsYn == '1'}">
					是
				</c:if>
            </td>
            <td class="form_label">预约类型：</td>
            <td>
            	<c:if test="${vmVisit.vsType == '1' }">
            		主动预约
            	</c:if>
            	<c:if test="${vmVisit.vsType == '2' }">
            		被动预约
            	</c:if>
            </td>
        </tr> 
        <tr>
            <td class="form_label" >卡号：</td>
            <td colspan="3">${vmVisit.cardId}</td>
        </tr> 
        <tr>
            <td class="form_label">卡类型：</td>
            <td>
                <c:if test="${vmVisit.cardType == '2'}">
					临时卡
				</c:if>
                <c:if test="${vmVisit.cardType == '1'}">
					市民卡
				</c:if>
            </td>
            <td class="form_label">事务状态：</td>
	            <td>
	           	    <c:if test="${vmVisit.vsFlag == '00'}">
						初始预约
					</c:if>
	           	    <c:if test="${vmVisit.vsFlag == '01'}">
						预约申请
					</c:if>
	                <c:if test="${vmVisit.vsFlag == '02'}">
						申请确认
					</c:if>
	                <c:if test="${vmVisit.vsFlag == '03'}">
						申请拒绝
					</c:if>
	                <c:if test="${vmVisit.vsFlag == '04'}">
						到访
					</c:if>
	                <c:if test="${vmVisit.vsFlag == '05'}">
						正常结束
					</c:if>
	                <c:if test="${vmVisit.vsFlag == '06'}">
						异常结束
					</c:if>
	                <c:if test="${vmVisit.vsFlag == '99'}">
						取消预约
					</c:if>
	            </td>
        </tr> 
        <tr>
            <td class="form_label">事务状态说明：</td>
            <td colspan="3">${vmVisit.vsCommets}</td>
        </tr> 
        <tr>
            <td class="form_label">是否归还临时卡：</td>
            <td>
                <c:if test="${vmVisit.vsReturn == '1'}">
					归还
				</c:if>
                <c:if test="${vmVisit.vsReturn == '2'}">
					未归还
				</c:if>
            </td>
            <td class="form_label">是否归还副卡：</td>
            <td>
                <c:if test="${vmVisit.vsretSub == '1'}">
					归还
				</c:if>
                <c:if test="${vmVisit.vsretSub == '2'}">
					未归还
				</c:if>
            </td>
        </tr> 
        <tr>
            <td class="form_label">登记人：</td>
            <td>${vmVisit.insertId}</td>
            <td class="form_label">登记时间：</td>
            <td>${vmVisit.insertDate}
    <!--    <s:date name="vmVisit.insertDate" format="yyyy年MM月dd日 hh时mm分" />-->
            </td>
        </tr> 
        <tr>
            <td class="form_label">修改人：</td>
            <td>${vmVisit.modifyId}</td>
            <td class="form_label">修改时间：</td>
            <td>${vmVisit.modifyDate}
     <!--   <s:date name="vmVisit.modifyDate" format="yyyy年MM月dd日 hh时mm分" />-->
            </td>
        </tr> 
 </table>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>