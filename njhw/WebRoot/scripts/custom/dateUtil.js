
//获取当月第一天
function getFirstDayOfMonth(){
    var today = new Date();
    var year = today.getFullYear();
    var month = today.getMonth();
    
    return year + "-" + decorateDigit(month + 1) + "-01";
}

//获取当日
function getToday(){
    var today = new Date();
    var year = today.getFullYear();
    var month = today.getMonth();
    var date = today.getDate();
    
    return year + "-" + decorateDigit(month + 1) + "-" + decorateDigit(date);
}

//获取当月
function getMonth(){
    var today = new Date();
    var year = today.getFullYear();
    var month = today.getMonth();
    
    return year + "-" + decorateDigit(month + 1);
}

//装饰一位数字
function decorateDigit(digit){
    if(digit < 10){
        return "0" + digit;
    }
    else {
    	return digit;
    }    
}

//比较两个日期的大小
//from <= to 返回true
//from > to 返回false
function validDate(from, to){
    if(from != "" && to != ""){
		var fromDate = new Date(from.replace(/-/g, "/")); 
		var toDate = new Date(to.replace(/-/g, "/"));
  		if((Date.parse(fromDate)-Date.parse(toDate))>0){
  			return false;
  		}
  		else {
  			return true;
  		}
    }
    
    return true;
}

//如果日期为空则默认取当天
function requireDate(date){
	var obj = $("input[name='" + date + "']");
	if(obj.val() == ''){
		obj.val(getToday());
	}
}

//根据日期控件名获取日期值
function getDateValue(date){
	var dateValue = $("input[name='" + date + "']").val();
	return dateValue;
}