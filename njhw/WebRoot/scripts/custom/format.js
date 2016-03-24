
//格式化文件
function formatFileSize(size){
    var b_mod = parseInt(size) % 1024;
    var k = Math.floor(parseInt(size) / 1024);
    var k_mod = k % 1024;
    var m = Math.floor(k / 1024);
    var m_mod = m % 1024;
    var g = Math.floor(m / 1024);
    
    var format = "";
    if(g > 0){
    	format += g + "G";
    }
    if(m_mod > 0){
    	format += m_mod + "M";
    }
    if(k_mod > 0){
    	if(b_mod > 0){
    		k_mod += decimalFixTo(parseFloat(b_mod) / 1024, 1);
    	}
    	format += k_mod + "K";
    }
    else {
    	if(b_mod > 0){
    		k_mod += decimalFixTo(parseFloat(b_mod) / 1024, 1);
        	format += k_mod + "K";
    	}
    	else{
    		if(format == ""){
    			return "0K";
    		}
    	}
    }
    
    return format;
}

//数字四舍五入
function decimalFixTo(num, fix){
    var fixTo = Math.pow(10, fix);
    return Math.round(num * fixTo) / fixTo;
}