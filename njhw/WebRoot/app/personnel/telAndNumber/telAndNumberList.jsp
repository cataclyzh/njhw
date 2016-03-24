<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/pager.tld" prefix="w" %>
<%@ include file="/common/include/taglibs.jsp"%>
<input type="hidden" id="resTypeHidden" value="${resType}" />
<input type="hidden" id="orgIdHidden" value="${orgId}" />
<input type="hidden" id="permitHidden" value="${permit}" />
<input type="hidden" id="telNumHidden" value="${telNum}" />
<input type="hidden" id="disStatusHidden" value="${disStatus}" />
<input type="hidden" id="displayNameHidden" value="${displayName}" />
<input type="hidden" id="allocatedTelHidden" value="${allocatedTel}" />
<input type="hidden" id="unAllocatedTelHidden" value="${unAllocatedTel}" />
<input type="hidden" id="totalTelHidden" value="${totalTel}" />
<div class="main_right_bottom" id="tel_num_list_div">
		<table class="main_table" border="0"
			style="border-collapse: collapse;">
			<tr class="table_header">
				<td>
					<span class="table_span">号码</span>
				</td>
				<td>
					<span class="table_span">类型</span>
				</td>
				<td>
					<span class="table_span">使用人员</span>
				</td>
				<td>
					<span class="table_span">处室</span>
				</td>
				<td>
					<span class="table_span">房间</span>
				</td>
				<!-- 
				<td>
					<span>话机所在房间</span>
				</td>
				 -->
				<td>
					<span class="table_span">话机MAC</span>
				</td>
				<td>
					<span class="table_span">权限</span>
				</td>
				<td>
					<span class="table_span">功能</span>
				</td>
				<td>
					<span class="table_span">对外公布</span>
				</td>
				<td>
					<span class="table_span">操作日期</span>
				</td>

				<td>
					<span class="table_span">操作</span>
				</td>
				<td>
				</td>
			</tr>
			<c:forEach items="${page.result}" var="data" varStatus="stat">
			<tr <c:if test="${stat.index%2 == 0}">class="odd_tr"</c:if><c:if test="${stat.index%2 == 1}">class="even_tr"</c:if> >
				<td>
					${data.RES_NAME}
				</td>
				<td>
					<c:if test="${data.TEL_TYPE=='1'}">IP电话</c:if>
					<c:if test="${data.TEL_TYPE=='2'}">传真</c:if>
					<c:if test="${data.TEL_TYPE=='3'}">网络传真</c:if>
				</td>
				<td>
					${data.DISPLAY_NAME}
				</td>
				<td>
					${data.NAME}
				</td>
				<td>
					${data.ROOM_INFO}
				</td>
				<td>
					${data.TEL_MAC}
				</td>
				<td>
					<c:if test="${data.TEL_IDD=='2'}">国际</c:if>
					<c:if test="${data.TEL_DDD=='2'}">国内</c:if>
					<c:if test="${data.TEL_LOCAL=='2'}">市话</c:if>
					<c:if test="${data.TEL_CORNET=='2'}">内网</c:if>
				</td>
				<td>
					<c:if test="${data.TEL_FORWARD=='2'}">呼叫转移</c:if>
					<c:if test="${data.TEL_CW=='2'}">呼叫等待</c:if>
				</td>
				<td>
					<c:if test="${data.TEL_EXT=='2'}">是</c:if>
					<c:if test="${data.TEL_EXT=='1'}">否</c:if>
				</td>
				<td>
					${data.REQ_DATE}
				</td>

				<td>
					<c:if test="${data.USERID != null && data.USERID > 1}">
						<a href = "javascript:void(0);" onclick="modifyDistrbute('${data.USERID}','${data.RES_ID}','${data.TEL_TYPE}');">变更</a>
					</c:if>
					<c:if test="${data.USERID == null || data.USERID == ''}">
						<a href = "javascript:void(0);" onclick="distribute('${data.RES_ID}','${data.TEL_TYPE}');">新增</a>
					</c:if>
				</td>
			</tr>
			</c:forEach>
		</table>
	<%-- 
		<w:pager pageSize="${page.pageSize}" pageNo="${page.pageNo}"  url="${ctx}/app/personnel/telAndNumber/telAndNumberList.act" recordCount="${page.totalCount}"/>
	--%>	
	</div>

