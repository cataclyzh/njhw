function resetBtn(){
	$("#btn_wodekaoqin").parent().attr("class","m1");
	$("#btn_bumenkaoqin").parent().attr("class","m2");
	$("#btn_shenqingshenpi").parent().attr("class","m3");
	$("#btn_wodeshenqing").parent().attr("class","m4");
	$("#btn_danweikaoqin").parent().attr("class","m5");
	$("#btn_xinchengkaoqin").parent().attr("class","m6");
	$("#btn_piliangshenqing").parent().attr("class","m7");
}

function updateApproveCount(){
	$.ajax({
		type: 'GET',
		url: _ctx + "/app/attendance/getApproveCount.act",
	}).done(function(res){
		
		if (res.approveCount == 0 ){
			//图标还原
			$("#btn_shenqingshenpi").css("background","url('images/qingjiashenpi_normal.png')");
			$("#btn_shenqingshenpi").hover(
					function(){
						$(this).css("background","url('images/qingjiashenpi_hover_normal.png')");
					},function(){
						$(this).css("background","url('images/qingjiashenpi_normal.png')");
					});
		}else{
			var approveCount = parseInt(res.approveCount, 10);
			if(approveCount < 10){
				$('#shenpi_num').html(approveCount).css({left: '214px'});
			}else{
				$('#shenpi_num').css({left: '209px'});
				if(approveCount > 99){
					$('#shenpi_num').html('99+');
				}else{
					$('#shenpi_num').html(approveCount);
				}
			}
			$(".m3").css("margin-bottom","-16px");
			
		}
	}).fail(function(res){
		alert('获取待审批请假数量出错');
		$('#shenpi_num').html(0).css({left: '209px'});
	});
}

$(document).ready(function(){
	updateApproveCount();
	
	$("a[id^='btn_']").click(function() {
		var idName = $(this).attr("id");
		resetBtn();
		if(idName == 'btn_wodekaoqin'){
			$(this).parent().attr("class","m1_select");
			window.parent.frames["main_frame_right"].location.href = _ctx + "/app/attendance/wodekaoqin.jsp";
		}else if(idName == 'btn_danweikaoqin'){
			$(this).parent().attr("class","m5_select");
			window.parent.frames["main_frame_right"].location.href = _ctx + "/app/attendance/danweikaoqin.act";
		}else if(idName == 'btn_bumenkaoqin'){
			$(this).parent().attr("class","m2_select");
			window.parent.frames["main_frame_right"].location.href = _ctx + "/app/attendance/bumenkaoqin.act";
		}else if(idName == 'btn_shenqingshenpi'){
			$(this).parent().attr("class","m3_select");
			window.parent.frames["main_frame_right"].location.href = _ctx + "/app/attendance/shenqingshenpi.act?status=0";
		}else if(idName == 'btn_wodeshenqing'){
			$(this).parent().attr("class","m4_select");
			window.parent.frames["main_frame_right"].location.href = _ctx + "/app/attendance/wodeshenqing.act?status=0";
		}else if(idName == 'btn_xinchengkaoqin'){
			$(this).parent().attr("class","m6_select");
			window.parent.frames["main_frame_right"].location.href = _ctx + "/app/attendance/xinchengkaoqin.act";
		}else if(idName == 'btn_piliangshenqing'){
			$(this).parent().attr("class","m7_select");
			window.parent.frames["main_frame_right"].location.href = _ctx + "/app/attendance/piliangshenqing.jsp";
		}
	});
});

function windowDialog(title,url,w,h,refresh){
 	var body =  window.document.body;
	var left =  body.clientWidth/2 - w/2;
	var top =  body.clientHeight/2+h/4;
	var scrollTop = document.body.scrollTop;
	//alert("left:"+left+"||top:"+top+"||scroll:"+scrollTop);
	top = top + scrollTop;
	// $("#companyWin").show();

    openEasyWin("aaa",title,url,w,h,false);
}