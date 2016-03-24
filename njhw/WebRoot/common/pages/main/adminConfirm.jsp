<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>管理员刷卡确认</title>
		<%@ include file="/common/include/metaIframe.jsp"%>
		<script src="${ctx}/scripts/validation/card.js" type="text/javascript"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		<link href="${ctx}/app/personnel/unit/css/count_down.css" rel="stylesheet" type="text/css"/>
		<link href="${ctx}/app/personnel/unit/css/wizard_css.css" rel="stylesheet" type="text/css"/>
		<script src="${ctx}/scripts/juiportal/jquery.lwtCountdown-1.0.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/juiportal/misc.js" type="text/javascript"></script>
		<OBJECT id="WebRiaReader" codeBase="" classid="clsid:13C57329-846A-4C82-AAB1-1AA0EC3CA0BB"></OBJECT>
		<script type="text/javascript">
		var iIdForAdmin = null;
		var isSuccess = false;
		$(document).ready(function(){
			var canEnd = false;
			$('#countdown_dashboard').countDown({
	        	targetOffset: {
	        		'day':      0,
					'month':    0,
					'year':     0,
					'hour':     0,
					'min':      0,
					'sec':      59},
	        	onComplete: function() {
					canEnd = true;
				} 
	    	});

			var cardId =  null;
      		iIdForAdmin = setInterval(function(){ readAdminCard(); }, 300);
      		var ifLinkChecked = false;
      		
      		function readAdminCard() {
      			if (canEnd) {
      				clearInterval(iIdForAdmin);
					parent.closeEasyWin("adminConfirm");
					return;
				}
				if (ifLinkChecked) {
      				return;
      			}
      			if (!WebRiaReader.LinkReader()) {
      				easyAlert('提示信息','无法读取市民卡，请检查读卡器连接状态和市民卡插件安装情况！','error');
      				ifLinkChecked = true;
      				return;
      			} else {
      				ifLinkChecked = false;
      			}
      			var ReadCardId = WebRiaReader.RiaReadCardEngravedNumber();
      			if (cardId == ReadCardId) {
      				return;
      			} else {
      				cardId = ReadCardId;
      			}

				if (cardId != "FoundCard error!") {
					$.ajax({
						type: "POST",
						url: "${ctx}/app/personnel/unit/checkAdminCard.act",
						data: {"cardId": cardId},
						dataType: 'text',
						async : true,
						success: function(json){
							json = eval('(' + json + ')');
							if (json.isAdmin) {
								isSuccess = true;
								clearInterval(iIdForAdmin);
								parent.closeEasyWin("adminConfirm");
								return;
							} else {
								easyAlert('提示信息','必须通过管理员卡号进行验证！','error');
							}
						}
					});
				}
      		}
		});	
		</script>
	</head>

	<body style="background: #fff">
		<div style="height: 20px;"></div>
		<div class="wizard_list" style="width: 506px;">
			<div class="wizerd_border1">请当前管理员刷卡确认</div>
		</div>
		<div style="height: 20px;"></div>
		<div style="margin: 0 auto; width:280px;height:150px;">
		<div id="countdown_dashboard">
			<div class="dash seconds_dash">
				<div class="countdown_left_space"></div>
				<div class="countdown_top_space"></div>
				<div class="digit">0</div>
				<div class="digit">0</div>
			</div>
		</div>
		</div>
		<div class="wizard_right" style="margin: 0 auto;float: none;"></div>
	</body>
</html>
