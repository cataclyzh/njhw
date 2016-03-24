<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/meta.jsp"%>
  <script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
    <link href="${ctx}/app/integrateservice/css/weekMenus/css/wizard_css.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/app/integrateservice/css/weekMenus/css/css.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/app/integrateservice/css/weekMenus/css/fest_index.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
</script>
	<div>
    <input type="hidden" name="fdiTypedd" id="fdiTypedd" value="${fdiType}" />
	<c:if test="${null!=list}">
		<c:forEach items="${list}" var="am" varStatus="num">
		<div class="fest_border_right">${am[0].FM_NAME}<c:if test="${fsdFlag == '2'}"><c:if test="${not empty am[0].FM_BAK2}">(${am[0].FM_BAK2})</c:if></c:if></div>
		<div class="fest_conter_right">
			<ul>
				<c:forEach items="${am}" var="dish" varStatus="index">
					<li><img src="${dish.FD_PHOTO1}" width="118" height="78" /></li>
				</c:forEach>
			</ul>
		<div class="clear"></div>
			<div class="fest_title">${fn:trim(am[0].FD_NAME)}</div>
		</div>
		</c:forEach>
	</c:if>
	<c:if test="${null == list}">
		<div class="clear"></div>
		<div class="fest_span1">${msg}</div>
	</c:if>
 </div>