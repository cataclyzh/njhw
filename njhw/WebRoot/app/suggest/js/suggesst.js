 //切换左边tab
function changeSuggestType(tabId){
    var url = "";
	if(tabId == 'add'){
	    url = _ctx+"/app/suggest/newSuggest.act";
		window.location.href = url;
		return ;
	}else if(tabId == 'listAll'){
		url = _ctx+"/app/suggest/init.act?isAll=true";
		window.location.href = url;
		return ;
	}else if(tabId == 'listMine'){
		url = _ctx+"/app/suggest/init.act?isAll=false";
		window.location.href = url;
		return ;
	}
}


$(function(){
	var href = window.location.href;
	if(/init/.test(href)&&/false/.test(href)){
		//alert("我的");
		$(".qbyj_send").attr("class","qbyj_send_select");
	}else if(/init/.test(href)&&/true/.test(href)||/saveSuggest/.test(href)){
		$(".qbyj_lnbox").attr("class","qbyj_lnbox_select");
		//alert("全部"); 
	}else if(/newSuggest/.test(href)){
		//alert("新增");
		$(".qbyj_been").attr("class","qbyj_been_select");
	}
});