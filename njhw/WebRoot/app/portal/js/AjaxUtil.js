function ajaxQuery(method,url,data,dataType,sucFun,errFun){
	$.ajax({
        type: method,
        url: url,
        data: data,
        dataType: dataType,
        async : false,
        success: function(data){
        	sucFun(data);
        },
        error: function(msg, status, e){
           errFun(e);
        }
     });
	
}

/**
 * 返回xml
 * @param url
 * @param data
 * @param sucFun
 * @param errFun
 */
function ajaxQueryXML(url,data,sucFun,errFun){
	
	ajaxQuery("POST",url,data,"text",sucFun,errFun);
}

/**
 * 返回json
 * @param url
 * @param data
 * @param sucFun
 * @param errFun
 */
function ajaxQueryJSON(url,data,sucFun,errFun){
	ajaxQuery("POST",url,data,"JSON",sucFun,errFun);
}

/**
 * 返回html
 * @param url
 * @param data
 * @param sucFun
 * @param errFun
 */
function ajaxQueryHTML(url,data,sucFun,errFun){
	ajaxQuery("POST",url,data,"html",sucFun,errFun);
}