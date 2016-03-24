//区间中文字两个字节
jQuery.validator.addMethod("byteRangeLength", 
		function(value, element, param) {
			var length = value.length;
			for(var i = 0; i < value.length; i++){
				if(value.charCodeAt(i) > 127){
				length++;
				}
			}
			return this.optional(element) || ( length >= param[0] && length <= param[1] );    
		}, $.validator.format("请确保输入的值在{0}-{1}个字节之间(一个中文字算2个字节)")
);
// 邮政编码验证    
jQuery.validator.addMethod("isZipCode", 
	function(value, element) {    
		var tel = /^[0-9]{6}$/;
		return this.optional(element) || (tel.test(value));
	}, "请正确填写您的邮政编码"
);
//请输入合法的IP信息
jQuery.validator.addMethod("ip", 
	function(value, element) {    
  		return this.optional(element) || (/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/.test(value) && (RegExp.$1 <256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256));    
	}, "请输入合法的IP信息"
); 
//请输入合法的金额信息--add by sjy
jQuery.validator.addMethod("onlyMoney", 
	function(value, element) {
		value = value.replace(/\s/ig,'');
		//var money =  /^-?\d+\.{0,}\d{0,}$/;输入多个小数点能通过校验，导致后台异常。
		var money =  /^-?\d+\.?\d{0,}$/;
  		return this.optional(element) || (money.test(value));    
	}, " 请输入合法的金额信息 ! "
);  
//请输入合法的密文信息--add by sjy
jQuery.validator.addMethod("onlyMw", 
	function(value, element) {  
		var mw =   /[^\d\+\-\*\/\<\>]/;
  		return this.optional(element) || (!mw.test(value));    
	}, " 请输入0-9或+-*/<> "
);  
//请输入合法的纳税人识别号信息
jQuery.validator.addMethod("onlyNsrsbh", 
	function(value, element) {  
		var nsrsbh =   /^[0-9XDKGg]+$/;
  		return this.optional(element) || (nsrsbh.test(value));    
	}, " 请输入数字或XDKGg ! "
);
//只能输入英文字母数字下划线--add by sjy
jQuery.validator.addMethod("noSpecialCaracters", 
	function(value, element) {  
		var caracter =   /^\w+$/;
  		return this.optional(element) || (caracter.test(value));    
	}, " 请输入英文字母或_ ! "
);
//只能输入数字,(不包括小数)--add by sjy 2011-11-15
jQuery.validator.addMethod("onlyNumber", 
	function(value, element) {  
		var caracter =   /^\d+$/;
  		return this.optional(element) || (caracter.test(value));    
	}, " 请输入数字英文字母或_ ! "
);

//判断日期格式例（2010-01-01或2010/01/01）--add by sjy 2011-11-15
jQuery.validator.addMethod("onlyDate", 
	function(value, element) {
		// Upd by ls 2010-12-23 去掉2010/01/01格式日期
  		//var regex = new RegExp("^(?:(?:([0-9]{4}(-|\/)(?:(?:0?[1,3-9]|1[0-2])(-|\/)(?:29|30)|((?:0?[13578]|1[02])(-|\/)31)))|([0-9]{4}(-|\/)(?:0?[1-9]|1[0-2])(-|\/)(?:0?[1-9]|1\\d|2[0-8]))|(((?:(\\d\\d(?:0[48]|[2468][048]|[13579][26]))|(?:0[48]00|[2468][048]00|[13579][26]00))(-|\/)0?2(-|\/)29))))$");
		var regex = new RegExp("^(?:(?:([0-9]{4}(-)(?:(?:0?[1,3-9]|1[0-2])(-)(?:29|30)|((?:0?[13578]|1[02])(-)31)))|([0-9]{4}(-)(?:0?[1-9]|1[0-2])(-)(?:0?[1-9]|1\\d|2[0-8]))|(((?:(\\d\\d(?:0[48]|[2468][048]|[13579][26]))|(?:0[48]00|[2468][048]00|[13579][26]00))(-)0?2(-)29))))$");
		return  regex.test(value);
	//}, " 请输规定格式yyyy-mm-dd或yyyy/mm/dd "
	}, " 请输规定格式yyyy-mm-dd的正确日期 "
);

//判断纳税人识别号的长度sbhLength --add by sjy 2011-11-15
jQuery.validator.addMethod("sbhLength",
	function(value,element){
		return (value.length==15||value.length==17||value.length==18||value.length==20);		
	},
	"纳税人识别号长度应为15、17、18或20位"
);
//判断代开纳税人识别号的长度dksbhLength --add by sjy 2011-11-18
jQuery.validator.addMethod("dksbhLength",
	function(value,element){
		return (value.length==15||value.length==17||value.length==18||value.length==20);		
	},
	"代开纳税人识别号长度应为15、17、18或20位"
);
//判断是否为代开纳税人识别号（以DK结尾） --add by sjy 2011-11-18
jQuery.validator.addMethod("onlyDksbh",
	function(value,element){
		//return (value.substr(value.length-2) == "DK");
		var dkindex = value.indexOf("DK");
		return ( dkindex >0 && value.indexOf("DK", dkindex+1) < 0);
	},
	"代开识别号需以DK结尾且只能包含一组DK"
);
/* 
要求：一、移动电话号码为11或12位，如果为12位,那么第一位为0 
二、11位移动电话号码的第一位和第二位为"13" 
三、12位移动电话号码的第二位和第三位为"13" 
用途：检查输入手机号码是否正确 
输入： 
s：字符串 
返回： 
如果通过验证返回true,否则返回false 
*/ 
jQuery.validator.addMethod("isMobileNumber", 
	function(value, element) {  
		var caracter =   /(^[1][3,5,8][0-9]{9}$)|(^0[1][3][0-9]{9}$)/;
  		return this.optional(element) || (caracter.test(value));    
	}, " 请输入正确的手机号码! "
);
/* 
要求：一、电话号码由数字、"("、")"和"-"构成 
二、电话号码为3到8位 
三、如果电话号码中包含有区号，那么区号为三位或四位 
四、区号用"("、")"或"-"和其他部分隔开 
用途：检查输入的电话号码格式是否正确 
输入： 
strPhone：字符串 
返回： 
如果通过验证返回true,否则返回false 
*/ 
jQuery.validator.addMethod("isPhoneNumber", 
	function(value, element) {  
		//var caracter =  /(^([0][1-9]{2,3}[-])?\d{3,8}(-\d{1,6})?$)|(^\([0][1-9]{2,3}\)\d{3,8}(\(\d{1,6}\))?$)|(^\d{3,8}$)/;
		var caracter =  /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
  		return this.optional(element) || (caracter.test(value));    
	}, " 请输入正确的电话号码! "
);
/**
 *isTelPhone（）校验电话号码或手机号 
 * add by sjy 2010-11-12
 * @return {TypeName} true or false;
 */
jQuery.validator.addMethod("isTelPhone", 
	function(value, element) { 
		var param =   /(^[1][3,5,8][0-9]{9}$)|(^0[1][3][0-9]{9}$)/;		
		var caracter =  /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
  		return this.optional(element) || (caracter.test(value)) || (param.test(value));    
	}, " 请输入正确的手机或电话号码! "
);

/**
 * notZero()金额不可以为零，录入时用
 * @param {Object} object
 * @return {TypeName} 
 * add by sjy 2010-11-15
 */
jQuery.validator.addMethod("notZero",
	function(value,element){
		return !(value=='0' || value=='0.00')		
	},
	"金额不能为零！"
);

//输入日期不能大于当前日期
jQuery.validator.addMethod("isDateBefore", 
	function(value, element) { 
		var d1 = new Date(); 		
		var d2 = new Date(value.replace(/-/g, "/")); 
  		return this.optional(element) || (Date.parse(d1)-Date.parse(d2))>0;    
	}, " 不能大于当前日期! "
);
//只能数字或字母
jQuery.validator.addMethod("isNumOrLetter", 
	function(value, element) { 
		var param =   /^[A-Za-z0-9]+$/;		
  		return this.optional(element) ||  (param.test(value));    
	}, " 请输入数字或字母! "
);
//判断非防伪税控代码长度  expkindnoLength --add by ZM 2011-11-23
jQuery.validator.addMethod("expkindnoLength",
	function(value,element){
		return (value.length==0||value.length==8||value.length==12);		
	},
	"代码长度应为8、或12位"
);
//密码复杂度判断 add by sjy 2010-12-22
jQuery.validator.addMethod("checkPass",
	function(value,element){
		var passLevel = 0;
		if(value.match(/([a-zA-Z])+/)){
			passLevel ++;
		}
		if(value.match(/([0-9])+/)){  	 
			passLevel++;     
		} 
		if(value.match(/[^a-zA-Z0-9]+/)){   
			passLevel++;   		  
		} 
		return passLevel>=2;
	},
	"密码复杂度不够!"
);

//取元素位top,left的位置--add by sjy
$.extend({    
        getLeft : function(object) {    
            var go = object;    
            var oParent, oLeft = go.offsetLeft;    
            while (go.offsetParent != null) {    
                oParent = go.offsetParent;    
                oLeft += oParent.offsetLeft;    
                go = oParent;    
            }    
            return oLeft;    
        },    
        getTop : function(object) {    
            var go = object;    
            var goHeight = go.height;    
            var oParent, oTop = go.offsetTop;    
            while (go.offsetParent != null) {    
                oParent = go.offsetParent;    
                oTop += oParent.offsetTop;    
                go = oParent;    
            }    
            return oTop + 22;// 之所以加22不加控件高度,为了兼容ie6.    
        }    
});
//showErrors 自定义错误显示方式，加图标，可以在这个方法里面扩展鼠标移到图片上时显示错误信息，移开时不显示。add by sjy
function showErrors(){    
        var t = this;    
        for ( var i = 0; this.errorList[i]; i++ ) {    
            var error = this.errorList[i];    
            this.settings.highlight && this.settings.highlight.call( this, error.element, this.settings.errorClass, this.settings.validClass );    
                
            var elename = this.idOrName(error.element);    
            // 错误信息div    
            var errdiv = $('div[htmlfor='+ elename + ']');    
            var errimg = $('img[htmlfor='+ elename + ']');      
            if(errimg.length == 0){ // 没有img则创建    
                errimg = $('<img alt="提示" src='+_ctx+'"/images/unchecked.gif"  width="14" height="14">')    
                errimg.attr({"for":  this.idOrName(error.element), generated: true});    
                errimg.insertAfter(error.element);    
            }    
            errimg.show();
            if(errdiv.length == 0){ // 没有div则创建    
                errdiv = $('<div>' 
                        + '<div class="errmsgdiv fl errmsg"></div>'
                        + '</div>');    
                callerTopPosition = $(error.element).offset().top;
				callerleftPosition = $(error.element).offset().left;
				callerWidth =  $(error.element).width();
				inputHeight = $(errdiv).height();
				callerleftPosition +=  callerWidth + 16; 
                errdiv.attr({"for":  this.idOrName(error.element), generated: true})    
                .addClass(this.settings.errorClass);    
	            errdiv.css({left :  callerleftPosition ,top :  callerTopPosition ,opacity:100}); // 显示在错误图片的左上
                errdiv.appendTo($('body'));    
            }   
            errdiv.show();
            errdiv.find(".errmsg").html(error.message || "");     
        }    
            
        // 校验成功的去掉错误提示    
        for ( var i = 0; this.successList[i]; i++ ) {    
                $('div[htmlfor="'+ this.idOrName(this.successList[i]) + '"]').remove();    
                $('img[htmlfor='+ this.idOrName(this.successList[i]) + ']').hide();    
        }    
            
        // 自定义高亮    
        if (this.settings.unhighlight) {    
            for ( var i = 0, elements = this.validElements(); elements[i]; i++ ) {    
                this.settings.unhighlight.call( this, elements[i], this.settings.errorClass, this.settings.validClass );    
            }    
        }    
    }
//运输税号规则  add by xiangmeng
jQuery.validator.addMethod("isFriTaxNo", 
	function(value, element) { 
		var param =   /(^\+?[A-Za-z0-9]{15}$)|(^\+?[A-Za-z0-9]{17}$)|(^\+?[A-Za-z0-9]{18}$)|(^\+?[A-Za-z0-9]{20}$)/;		
  		return this.optional(element) ||  (param.test(value));    
	}, " 运输税号应为15,17,18或20位数字或字母! "
);

//notEqualTo规则  add by xiangmeng
jQuery.validator.addMethod("notEqualTo", 
	function(value, element, param) {
		// bind to the blur event of the target in order to revalidate whenever the target field is updated
		// find a way to bind the event just once, avoiding the unbind-rebind overhead
		var target = $(param).unbind(".validate-notEqualTo").bind("blur.validate-notEqualTo", function() {
			$(element).valid();
		});
		return value != target.val();
	}, " 请输入不同值! "
);
//isInvnumMatchType规则  代码与类型不符
jQuery.validator.addMethod("isInvkindMatchType", 
	function(value, element, param) {
		if(value.substring(7,8)=='6'){
			if($(param).val()=='s'){
				return false;
			}else{
				return true;
			}
		}else{
			if($(param).val()=='c'){
				return false;
			}else{
				return true;
			}
		}
	}, "代码与类型不符，请重新输入! "
);
//运输代开税务机关规则  add by wht
jQuery.validator.addMethod("isBureau", 
	function(value, element) { 
		var param =   /(^\+?[0-9]{7}$)|(^\+?[0-9]{9}$)|(^\+?[0-9]{11}$)/;		
  		return this.optional(element) ||  (param.test(value));    
	}, " 代开税务机关应为7,9,11位数字! "
);

//运输承运人纳税人识别号规则  add by wht
jQuery.validator.addMethod("isCarryTaxNo", 
	function(value, element) { 
		var param =   /(^[A-Za-z0-9]{15}$)|(^[A-Za-z0-9]{17}$)|(^[A-Za-z0-9]{18}$)|(^[A-Za-z0-9]{20}$)/;		
  		return this.optional(element) ||  (param.test(value));    
	}, " 承运人号应为15,17,18或20位数字或字母!"
);
