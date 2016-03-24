// JavaScript Document
// get request parameters(EX: ?Param1=Value1&Param2=Value2)
/*
var URLParams = new Object() ;
var aParams = document.location.search.substr(1).split('&') ;
for (i=0 ; i < aParams.length ; i++) {
	var aParam = aParams[i].split('=') ;
	URLParams[aParam[0]] = aParam[1] ;
}
*/
function FixNum(frm) {
  var reg = /[^0-9]/gi;
  var num = frm.Page.value;
  if (reg.test(num)) {
	frm.Page.value = num.substring(num.length);
  }
}
function CheckGoPageForm(frm,pagesum) {
  var num = frm.Page.value;
  if (frm.Page.disabled) {
	return false;
  }
  if (num.replace(/ /gi, '') == '') {
	  frm.Page.focus();
	  return false;
  }
  /*alert(isNaN(num));*/
  if (isNaN(num)) {
	frm.Page.value = num.substring(num.length);
	frm.Page.focus();
	return false;
  }
  if(parseInt(num)<=0 || (parseInt(num) > parseInt(pagesum))){
	frm.Page.value = num.substring(num.length); 
	frm.Page.focus();
    return false;
  }
return true;
}
function CheckGoPageForm_HTML(frm,pagesum){
	if(!CheckGoPageForm(frm,pagesum)){
		return false;
	}else{
		var num = frm.Page.value;
		var page = frm.indexpage.value;
		var url = page.substring(0,page.lastIndexOf("/"));
		var gopage = page;
		if(parseInt(num)!=1){
			gopage=url+"/index_"+num+page.substring(page.lastIndexOf("."),page.length());
		}
		location.replace(gopage);
		return false;
	}
}