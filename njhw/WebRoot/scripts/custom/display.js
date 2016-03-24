
function animateContent(contentId, removeHeight){
    var mainHeight = document.documentElement.clientHeight;
    $("#" + contentId).animate({ height: mainHeight - removeHeight - 28 }, 100);
}

function calculateHeight(ids){
	var height = 0;
	if(ids != null && ids != ""){
		var idArr = ids.split(",");	
		for(var i = 0; i < idArr.length; i++){
			if(idArr[i] != ''){
				height += $("#" + idArr[i]).height();			
			}
		}
	}
	
	return height;
}

function setQueryToggle(contentId, toggleIds, fixIds){
	var toggleHeight = calculateHeight(toggleIds);
	var fixHeight = calculateHeight(fixIds);
	var status = $("#togglestatus").val();
	if(status == 'hide'){
		animateContent(contentId, fixHeight);
	}
	else {
		animateContent(contentId, toggleHeight + fixHeight);
	}	
}

function initQueryToggle(contentId, toggleIds, fixIds){
	setQueryToggle(contentId, toggleIds, fixIds);
	var status = $("#togglestatus").val();
	
    $("#panel1_button").toggle(
    	function(){
    		if(status == 'hide'){
    			animateContent(contentId, calculateHeight(toggleIds) + calculateHeight(fixIds));
    		}
    		else {
    			animateContent(contentId, calculateHeight(fixIds));
    		}
    	},
    	function(){
    		if(status == 'hide'){
    			animateContent(contentId, calculateHeight(fixIds));
    		}
    		else {
    			animateContent(contentId, calculateHeight(toggleIds) + calculateHeight(fixIds));
    		}
    	}
    );
}