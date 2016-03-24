<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
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
	
	
	function upDateReadMark(id) {
		var url="${ctx}/app/fax/upDateReadMark.act";
		var data = {id:id};
		sucFun = function (data){
		};
		errFun = function (data){
		};
		ajaxQueryJSON(url,data,sucFun,errFun);
	}
	
	
	
	
	</script>
<div id="receive_fax_list_page">
	<div class="main_main">
		<table class="main_table" border="0"
			style="border-collapse: collapse;">
			<tr class="table_header">
				<td>
				<input type="checkbox" id="checkAll"/>
				</td>
				<td>
					<span>接收时间</span>
				</td>
				<td>
					<span>主叫号码</span>
				</td>
				<td>
					<span>时长</span>
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
			<c:forEach items="${pageList}" var="result">
				<tr class="odd_tr">

					<td>
						<input type="checkbox" name="_chk" id="chk_${result.FL_ID }" class="checkItem" value="${result.FL_ID }"/>
					</td>
					<td>
						${result.CREATE_ON}
					</td>
					<td>
						${result.CALLER}
					</td>
					<td>
						${result.DURATION}
					</td>
					<td>
						${result.SUBJECT}
					</td>
					<td>
						<a href="http://10.254.101.100/ipcom/index.php/Api/fi_download?session_id=${fax_session_id}&id=${result.FAX_ID}" onclick="upDateReadMark('${result.FL_ID}');" >附件</a>
					</td>
					<td>
						${result.STATUSNAME }
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
