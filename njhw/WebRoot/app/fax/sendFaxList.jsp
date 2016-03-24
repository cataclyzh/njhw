<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
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
<div id="send_fax_list_page">
	<div class="main_main">
		<table class="main_table" border="0"
			style="border-collapse: collapse;">
			<tr class="table_header">
				<td>
				<input type="checkbox" id="checkAll"/>
				</td>
				<td>
					<span>发送时间</span>
				</td>
				<td>
					<span>被叫号码</span>
				</td>
				<td>
					<span>接受账号</span>
				</td>
				<td>
					<span>传真主题</span>
				</td>
				<td>
					<span>附件</span>
				</td>
				
				<td>
					<span>接受状态</span>
				</td>

			</tr>
			<c:forEach items="${pageList}" var="data">
				<tr class="odd_tr">
					<td>
						<input type="checkbox" name="_chk" id="chk_${data.FL_ID }" class="checkItem" value="${data.FL_ID }"/>
					</td>
					<td>
						${data.CREATE_ON}
					</td>
					<td>
						${data.CALLED}
					</td>
					<td>
						${data.RECEIVER}
					</td>
					<td>
						${data.SUBJECT} 
					</td>
					<td>
						<c:if test="${null != data.EOFA_DESC && data.EOFA_DESC != ''}">
							<a href="${ctx}/app/fax/downLoadAttach.act?filePath=${data.EOFA_DESC}" >附件</a>
						</c:if>
						
					</td>
					<td>
						${data.STATUSNAME }
					</td>

				</tr>
			</c:forEach>

		</table>
	</div>
	<div class="main_main_bottom">
		<jsp:include page="/common/include/paging/gopage.jsp" flush="true">
			<jsp:param name="type" value="0" />
			<jsp:param name="position" value="down" />
		</jsp:include>
	</div>
</div>
