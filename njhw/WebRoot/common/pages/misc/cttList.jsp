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
<input type="hidden" id="cttKeyHidden" value="${cttKey }"/>
<input type="hidden" id="cttTypeHidden" value="${cttType }"/>
<table class="main_table" border="0" style="border-collapse: collapse;">
	<tr class="table_header">
		<%-- 
		<td>
			<span class="table_span">序号</span>
		</td>
		<td>
			<span class="table_span">选择</span><input type="checkbox" id="checkAll"/>
		</td>
		--%>
		<td>
			<span class="table_span">常量键</span>
		</td>
		<td>
			<span class="table_span">常量值</span>
		</td>
		<%-- <td>
			<span class="table_span">类型</span>
		</td>--%>
		<td>
			<span class="table_span">描述<span>
		</td>
		<td>
			<span class="table_span">操作</span>
		</td>
	</tr>
	<c:forEach items="${page.result}" var="ctt" varStatus="cttStatus">
		<tr>
			<%--
			<td>
				${cttStatus.index+1 }
			</td>
			<td>
				<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${ctt.cttid }"/>
			</td>
			--%>
			<td>
				${ctt.cttKey }
			</td>
			<td>
				<span title="${ctt.cttValue }" style="color:#808080;">
					<c:choose>
						<c:when test="${not empty(ctt.cttValue) and fn:length(ctt.cttValue) > 50}">
							${fn:substring(ctt.cttValue, 0, 50) }...
						</c:when>
						<c:otherwise>
							${ctt.cttValue }
						</c:otherwise>
					</c:choose>
				</span>
			</td>
			<%--
			<td>
				${ctt.cttType }
			</td>
			 --%>
			<td>
				<span title="${ctt.cttDesc }" style="color:#808080;">
					<c:choose>
						<c:when test="${not empty(ctt.cttDesc) and fn:length(ctt.cttDesc) > 10}">
							${fn:substring(ctt.cttDesc, 0, 10) }...
						</c:when>
						<c:otherwise>
							${ctt.cttDesc }
						</c:otherwise>
					</c:choose>
				</span>
			</td>
			<td>
				<a href="javascript:void(0);" onclick="updateRecord('${ctt.cttid}')">[修改]</a>&nbsp;
			</td>
		</tr>
	
	</c:forEach>
</table>
<%-- 
<table border="0" style="border-collapse: collapse;margin-top: 10px;margin-bottom: 10px">
	<tr>
		<td width="50%">
		<a class="fkdj_botton_reset" id="addRecord" href="javascript:void(0);" onclick="addRecord()">新&nbsp;&nbsp;增</a>
		</td>
		<td width="50%">
		<a class="fkdj_botton_reset" id="delRecord" href="javascript:void(0);" onclick="delRecord()">删&nbsp;&nbsp;除</a>
		</td>
	</tr>
</table>
--%>
<w:pager pageSize="${page.pageSize}" pageNo="${page.pageNo}"  url="${ctx}/common/constants/list.act" recordCount="${page.totalCount}"/>
</div>
