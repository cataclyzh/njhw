<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/pager.tld" prefix="w" %>
<%@ include file="/common/include/taglibs.jsp"%>
<input type="hidden" id="startDateHidden" value="${startDate }"/>
<input type="hidden" id="endDateHidden" value="${endDate }"/>
<input type="hidden" id="statusHidden" value="${status }"/>
<input type="hidden" id="contentHidden" value="${content }"/>
<c:forEach items="${suggestPage.result}" var="sug">
<div id="div_${sug.sugid}_id">
	<div class="qbyj_center_on">
		<span class="qbyj_on_t">提出时间：<c:out value="${sug.createtimeString}"></c:out></span>
		<span class="qbyj_on_t">状态：</span>
		<c:choose>
			<c:when test="${sug.status=='1'}">
				<span class="qbyj_on_s">[已答复]</span>
				<a class="qbyj_no_as" href="javascript:void(0);" onclick="delSuggest(${sug.sugid},this)">删除意见</a>
				<c:set var="repid" value=""></c:set>
				<c:if test="${not empty sug.suggestReplyVOList and fn:length(sug.suggestReplyVOList) gt 0}">
					<c:forEach items="${sug.suggestReplyVOList}" var="rep" varStatus="repStatus" >
						<c:if test="${rep.userid eq _userid}">
							<c:set var="repid" value="${rep.repid}"></c:set>
						</c:if>
					</c:forEach>
				</c:if>
				<c:choose>
					<c:when test="${not empty repid}">
						<a class="qbyj_no_a" href="javascript:void(0);" onclick="viewSuggestReply(${sug.sugid},${repid},this)">编辑答复</a>
						<c:set var="repid" value=""></c:set>
					</c:when>
					<c:otherwise>
						<a class="qbyj_no_a" href="javascript:void(0);" onclick="viewSuggestReply(${sug.sugid},'',this)">答复意见</a>
					</c:otherwise>
				</c:choose>
				<a class="qbyj_no_as" href="javascript:void(0);" onclick="toggleReply('hf_'+${sug.sugid},this)">查看答复</a>
			</c:when>
			<c:otherwise>
				<span class="qbyj_on_t">[未回复]</span>
				<a class="qbyj_no_as" href="javascript:void(0);" onclick="delSuggest(${sug.sugid},this)">删除意见</a>
				<a class="qbyj_no_a" href="javascript:void(0);" onclick="viewSuggestReply(${sug.sugid},'',this)">答复意见</a>
				<a onclick="" href="javascript:void(0);" class="qbyj_disable_a" title="查看回复不可用">查看答复</a>
			</c:otherwise>
		</c:choose>
	</div>
	<div class="qbyj_txt_ons" style="overflow: auto;">
		<textarea class="show_textarea" readonly="readonly" style="overflow: auto;height: 120px;width: 99%;" name="content">${sug.content }</textarea>
	</div>
	<div class="qbyj_right_fost">
		<div class="qbyj_list_right">
		<c:if test="${not empty sug.suggestReplyVOList and fn:length(sug.suggestReplyVOList) gt 0}">
			<c:forEach items="${sug.suggestReplyVOList}" var="rep" varStatus="repStatus" >
				<c:choose>
					<c:when test="${repStatus.count%2==0}">
						<div class="qbyj_center_ons1" id="hf_${sug.sugid }_bt" style="display: none;">
							<span class="qbyj_on_t">回复时间：${rep.createtimeString }</span>
							<span class="qbyj_on_t">回复人：${rep.displayName }( ${rep.shortName} )</span>
							<c:if test="${rep.userid eq _userid}">
								<a class="qbyj_no_as" href="javascript:void(0);" onclick="delSuggestReply(${rep.repid},this)">删除回复</a>
							</c:if>
						</div>
						<div class="qbyj_txt_ons1" id="hf_${sug.sugid }_nr" style="display: none;overflow: auto;">
							<textarea class="show_textarea" readonly="readonly" style="overflow: auto;height: 120px;width: 99%;" name="content">${rep.content }</textarea>
						</div>
					</c:when>
					<c:otherwise>
						<div class="qbyj_center_ons" id="hf_${sug.sugid }_bt" style="display: none;">
							<span class="qbyj_on_t">回复时间：${rep.createtimeString }</span>
							<span class="qbyj_on_t">回复人：${rep.displayName }( ${rep.shortName} )</span>
							<c:if test="${rep.userid eq _userid}">
								<a class="qbyj_no_as" href="javascript:void(0);" onclick="delSuggestReply(${rep.repid},${sug.sugid })">删除回复</a>
							</c:if>
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
</div>
</c:forEach>
<w:pager pageSize="${suggestPage.pageSize}" pageNo="${suggestPage.pageNo}"  url="${ctx}/app/suggest/replyList.act" recordCount="${suggestPage.totalCount}"/>