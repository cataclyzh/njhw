<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/pager.tld" prefix="w" %>
<script type="text/javascript">
		$(document).ready(function(){
			var chk_options = { 
				all:'#checkAll',
				item:'.checkItem'
			};
			$(this).chk_init(chk_options);
			changebasiccomponentstyle();
			changedisplaytagstyle();
			changecheckboxstyle();
		});
		
		
	</script>
<div class="main_right_bottom" id="tel_num_list_div">
<input type="hidden" id="insertNameHidden" value="${insertName}"/>
<input type="hidden" id="resNameHidden" value="${resName}"/>
<input type="hidden" id="bmTypeHidden" value="${bmType}"/>
<input type="hidden" id="startTimeHidden" value="${startTime}"/>
<input type="hidden" id="endTimeHidden" value="${endTime}"/>
<table class="main_table" border="0" style="border-collapse: collapse;">
	<tr class="table_header">
		<td>
			<span class="table_span">设备名称</span>
		</td>
		<td>
			<span class="table_span">设备类型</span>
		</td>
		<td>
			<span class="table_span">操作内容</span>
		</td>
		<td>
			<span class="table_span">操作描述</span>
		</td>
		<td>
			<span class="table_span">操作人</span>
		</td>
		<td>
			<span class="table_span">操作结果</span>
		</td>
		<td>
			<span class="table_span">操作时间<span>
		</td>
	</tr>
	<c:forEach items="${page.result}" var="log" varStatus="logStatus">
		<tr>
			<td>
				<span title="${log.RESNAME }">${fn:substring(log.RESNAME,0,10)}</span>
			</td>
			<td>
				${log.BMTYPE }
			</td>
			<td>
				<span title="${log.BMDETAIL }">${fn:substring(log.BMDETAIL,0,10)}</span>
			</td>
			<td>
				<span title="${log.BMEXP2 }">${fn:substring(log.BMEXP2,0,10)}</span>
			</td>
			<td>
				${log.INSERTNAME }
			</td>
			<td>
				<c:choose>
					<c:when test="${not empty log.BMEXP4 and (fn:toLowerCase(log.BMEXP4) eq 'yes')}">
						成功
					</c:when>
					<c:otherwise>
						失败
					</c:otherwise>
				</c:choose>
			</td>
			<td>
				${log.INSERTDATE }
			</td>
		</tr>
	</c:forEach>
	<tr>
		<td colspan="7">
			<a href="javascript:void(0);" class="fkdj_botton_right" style="color:#fff; float:right;" onclick="expLog()">导出EXCEL</a>	
		</td>
	</tr>
</table>
</br>
<w:pager pageSize="${page.pageSize}" pageNo="${page.pageNo}"  url="${ctx}/app/log/query/list.act" recordCount="${page.totalCount}"/>
</div>