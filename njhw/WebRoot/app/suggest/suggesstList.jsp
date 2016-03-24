﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/pager.tld" prefix="w" %>
<%@ include file="/common/include/taglibs.jsp"%>
<input type="hidden" id="startDateHidden" value="${startDate }"/>
<input type="hidden" id="endDateHidden" value="${endDate }"/>
<input type="hidden" id="statusHidden" value="${status }"/>
<input type="hidden" id="contentHidden" value="${content }"/>
<c:forEach items="${suggestPage.result}" var="sug">
	<div class="qbyj_center_on">
		<span class="qbyj_on_t">提出时间：<c:out value="${sug.createtimeString}"></c:out></span>
		<span class="qbyj_on_t">状态：</span>
		<c:choose>
			<c:when test="${sug.status=='1'}">
				<span class="qbyj_on_s">[已答复]</span>
				<a class="qbyj_no_as" href="javascript:void(0);" onclick="toggleReply('hf_'+${sug.sugid},this)">查看答复</a>
			</c:when>
			<c:otherwise>
				<span class="qbyj_on_t">未回复</span>
			</c:otherwise>
		</c:choose>
	</div>
	<div class="qbyj_txt_ons" style="overflow: auto;">
	     <p >
	     <textarea class="show_textarea" readonly="readonly" style="overflow: auto;height: 120px;width: 99%;" name="content">${sug.content}</textarea>
         </p>
	</div>
	<div class="qbyj_right_fost">
	<div class="qbyj_list_right">
	<c:if test="${not empty sug.suggestReplyVOList and fn:length(sug.suggestReplyVOList) gt 0}">
		<c:forEach items="${sug.suggestReplyVOList}" var="rep" varStatus="repStatus" >
			<c:choose>
				<c:when test="${repStatus.count%2==0}">
					<div class="qbyj_center_ons1" id="hf_${sug.sugid }_bt" style="display: none;overflow: auto;">
						<span class="qbyj_on_t">回复时间：${rep.createtimeString }</span>
						<span class="qbyj_on_t">回复人：${rep.displayName}( ${rep.shortName} )</span>
					</div>
					<div class="qbyj_txt_ons1" id="hf_${sug.sugid }_nr" style="display: none;">
						<textarea class="show_textarea" readonly="readonly" style="overflow: auto;height: 120px;width: 99%;" name="content">${rep.content }</textarea>
					</div>
				</c:when>
				<c:otherwise>
					<div class="qbyj_center_ons" id="hf_${sug.sugid }_bt" style="display: none;">
						<span class="qbyj_on_t">回复时间：${rep.createtimeString }</span>
						<span class="qbyj_on_t">回复人：${rep.displayName}( ${rep.shortName} )</span>
					</div>
					<div class="qbyj_txt_ons" id="hf_${sug.sugid }_nr" style="display: none;overflow: auto;">
						<textarea class="show_textarea" readonly="readonly" style="overflow: auto;height: 120px;width: 99%;" name="content">${rep.content }</textarea>
					</div>
				</c:otherwise>
			</c:choose>
			<c:if test=""></c:if>
			
		</c:forEach>
	</c:if>
</div>
</div>
</c:forEach>
<div class="bgsgl_clear" style="padding: 3px"></div>
<w:pager pageSize="${suggestPage.pageSize}" pageNo="${suggestPage.pageNo}"  url="${ctx}/app/suggest/suggestList.act" recordCount="${suggestPage.totalCount}"/>