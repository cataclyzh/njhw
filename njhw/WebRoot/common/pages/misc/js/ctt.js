 //切换左边tab
function changeCttType(tabId){
    var url = "";
	if(tabId == 'constant'){
	    url = _ctx+"/common/constants/index.act";
		window.location.href = url;
		return ;
	}else if(tabId == 'objMenuManager'){
		url = _ctx+"/app/per/objMenuManager.act";
		window.location.href = url;
		return ;
	}else if(tabId=='logIndex'){
		url = _ctx+"/app/log/query/logIndex.act";
		window.location.href = url;
		return ;
	}else if(tabId=='business'){
		url = _ctx+"/common/dictMgr/dicttypeIndex.act";
		window.location.href = url;
		return ;
	}else if(tabId=='objAdmin'){
		url = _ctx+"/app/per/objAdmin.act";
		window.location.href = url;
		return ;
	}else if(tabId=='userOrgTree'){
		url = _ctx+"/app/per/orgTreeUserCheckinFrameAdmin.act";
		window.location.href = url;
		return ;
	}else if(tabId=='setHoliday'){
		url = _ctx+"/app/attendance/setHoliday.jsp";
		window.location.href = url;
		return ;
	}
}


$(function(){
	var href = window.location.href;
	if(/app\/integrateservice\/init/.test(href)){
		$(".images_nav_a3").attr("class","images_nav_a3_select");
	}else if(/common\/constants\/index/.test(href)){
		$(".images_nav_a2").attr("class","images_nav_a2_select");
	}else if(/app\/per\/objMenuManager/.test(href)){
		$(".images_nav_a3").attr("class","images_nav_a3_select");
	}else if(/app\/log\/query\/logIndex/.test(href)){
		$(".images_nav_a5").attr("class","images_nav_a5_select");
	}else if(/common\/dictMgr\/dicttypeIndex/.test(href)){
		$(".images_nav_a1").attr("class","images_nav_a1_select");
	}else if(/app\/per\/objAdmin/.test(href)){
		$(".images_nav_a4").attr("class","images_nav_a4_select");
	}else if(/app\/per\/orgTreeUserCheckinFrameAdmin/.test(href)){
		$(".images_nav_a6").attr("class","images_nav_a6_select");
	}else if(/app\/attendance\/setHoliday/.test(href)){
		$(".images_nav_a7").attr("class","images_nav_a7_select");
	}
	
});