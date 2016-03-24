// 弹出食堂视频
function refectory() {
	var url = _ctx+"/app/integrateservice/refectoryInformation.jsp";
	//openEasyWin('winId','食堂视频',url,'660','500',false);
	showShelter('660','510',url);
}

// 加载系统消息 
function loadMsg() {
	var url = _ctx + "/message/queryMsgJSON.act?stamp=" + Math.random();
	$.getJSON(url, function(json){
		$("div [id^='div_msg_']").remove();
		$("#divMsgBox").html("");
		if (json != null && json.length > 0) {
			$.each(json,function(i){
				if(i >=5 ) return;
				var id= json[i].MSGID;
				var	title = json[i].TITLE;
				var time = json[i].T;
				var status = json[i].STATUS;
				var statusStr;
				var maxLen = 15;
				var classStr;
				if(title.length >= maxLen){
					var tmp =  title.substr(0, maxLen);
					title = tmp + "...";
				}
				
				if(status == 1){
					statusStr = " 已读 ";
					classStr = "main_spant1";
				}else{
					statusStr = " 未读 ";
					classStr = "main3_list4";
				}
				
				$("#divMsgBox").append("<li id='div_msg_msg"+i+"' onclick='shouNewInfo("+id+");'><a class='main_lefts2' href='#'>" + title  + "</a> <a class='main_lefts1' href='#'>" + time + "</a>&nbsp; <a class='"+classStr+"' href='#' id='notice"+id+"'>" + statusStr + "</a></li>");
			});
		}else{
			$("#divMsgBox").html("<img src='images/zhanwuxinxi2.jpg'/>");
		}
	});
}

//load加载我的访客数据 5条
function loadVisitor(){
	$.getJSON(_ctx+"/message/queryVisitJSON.act", function(json){
		$("div [id^='div_msg_']").remove();
		$("#divMsgBox").html("");
		if (json != null && json.length > 0) {
			$.each(json,function(i){
				if(i >=5 ) return;
				var id= json[i].VS_ID;
				var	username = json[i].VI_NAME;
				var	starttime = json[i].VSST;
				var status = json[i].VS_FLAG;
				var statusStr;
				var maxLen = 15;
				var contentStr;
				var classStr;
				if(username.length >= maxLen){
					var tmp =  username.substr(0, maxLen);
					username = tmp + "...";
				}
				else if(status == "00"){
					statusStr = " 初始预约 ";
					classStr = "main_spant1";
				}
				else if(status == "01"){
					statusStr = " 已申请 ";
					classStr = "main3_list4";
				}
				else if(status == "02"){
					statusStr = " 已确认 ";
					classStr = "main_spant1";
				}
				else if(status == "03"){
					statusStr = " 已拒绝 ";
					classStr = "main_spant1";
				}
				else if(status == "04"){
					statusStr = " 已到访 ";
					classStr = "main_spant1";
				}
				else if(status == "05"){
					statusStr = " 已结束";
					classStr = "main_spant1";
				}
				else if(status == "06"){
					statusStr = " 异常结束 ";
					classStr = "main_spant1";
				}
				else if(status == "99"){
					statusStr = " 已取消";
					classStr = "main_spant1";
				}else{
					statusStr = " 异常状态";
					classStr = "main_spant1";
				}
				
				contentStr = username +" 在 "+starttime+" 来访 ";
				$("#divMsgBox").append('<li id="div_msg_Vis'+i+'" onclick="shouNewInfo1(\''+id+'\');"><a class="main_lefts3" href="#">' + contentStr + '</a><a class="'+classStr+'" href="#" id="ReadMyCalls'+id+'">' + statusStr + '</a></li>');
			});
		}else{
			$("#divMsgBox").html("<img src='images/zhanwuxinxi2.jpg'/>");
		}
	});
}

//待办事项
function LoadMatter(){
	$.getJSON(_ctx+"/message/queryMatter.act", function(json){
		$("div [id^='div_msg_']").remove();
		$("#divMsgBox").html("");
		if (json != null && json.length > 0) {
			$.each(json,function(i){
				if(i >=5 ) return;
				var id= json[i].MESSAGEITEMGUID;
				var	starttime = json[i].GENERATEDATE;
				var title = json[i].TITLE;
				var maxLen = 18;
				if(title.length >= maxLen){
					var tmp =  title.substr(0, maxLen);
					title = tmp + "...";
				}
				$("#divMsgBox").append('<li id="div_msg_Vis'+i+'" onclick="shouNewInfo2(\''+id+'\');"><a class="main_db2" style="cursor:pointer;">' + title + '</a> <a class="main_lefts1" href="#">' + starttime + '</a><a class="main_spant1" href="#" id="notice'+id+'"></a></li>');
			});
		}else{
			$("#divMsgBox").html("<img src='images/zhanwuxinxi2.jpg'/>");
		}
	});
}

//加载传真消息 
function loadFax() {
	$.getJSON(_ctx + "/message/queryFaxList.act", function(json){
		$("div [id^='div_msg_']").remove();
		$("#divMsgBox").html("");
		if (json != null && json.length > 0) {
			$.each(json,function(i){
				if(i >=5 ) return;
				var	created_on = json[i].created_on;
				var	caller = json[i].caller; //主叫号码
				var textStr;
				textStr = "来自主叫号码   " + caller;
				
				$("#divMsgBox").append("<li id='div_msg_msg"+i+"'><a style='cursor: default;' class='main_lefts2'>" + textStr  + "</a> <a class='main_lefts1' style='cursor: default;'>" + created_on + "</a>&nbsp;</li>");
			});
		}else{
			$("#divMsgBox").html("<img src='images/zhanwuxinxi2.jpg'/>");
		}
	});
}

//加载我的物业通知数据
function loadBoard(){
	$.getJSON(_ctx+"/common/bulletinMessage/msgBoardAction_queryBoardJSON.act", function(json){
		if (json != null && json.length > 0) {
			$("div [id^='board_msg']").remove();
			$.each(json,function(i){
				if(i >= 4 ) return;
				var id= json[i].MSGID;
				var	starttime = json[i].MSGTIME;
				var title = json[i].TITLE;
				var maxLen = 18;
				if(title.length >= maxLen){
					var tmp =  title.substr(0, maxLen);
					title = tmp + "...";
				}
				$("#boardNoti").append('<li id="board_msg'+i+'" class="mina2_h"><a style="cursor: pointer;" onclick="showBoard('+id+');" id="board_content_'+id+'">'+title+'</a><span style="float:right;color:#808080;">'+starttime+'</span></li>');
			});
		}else{
			$("#boardNoti").html("<img src='images/zhanwuxinxi1.jpg'/>");
		}
	});
}

//失物认领
function loadArticle(){
	///app/personnel/cusservice/queryArticleJSON.act
	$.getJSON(_ctx+"/app/pro/queryLostFoundsListJSON.act", function(json){
		if (json != null && json.length > 0) {
			$("div [id^='article_msg']").remove();
			$.each(json,function(i){
				if(i >= 4 ) return;
				var id= json[i].lostFoundId;
				var	starttime = json[i].lostFoundIntimeString;
				if(starttime == null){
					starttime = "";
				}
				var title = json[i].lostFoundTitle;
				var maxLen = 18;
				if(title.length >= maxLen){
					var tmp =  title.substr(0, maxLen);
					title = tmp + "...";
				}
				$("#article").append("<li id='article_msg"+i+"' class='mina2_h'><a style='cursor: pointer;' onclick=\"showShelter('670','500','"+_ctx+"/app/pro/viewLostFoundById.act?lostFoundId="+id+"','670','500',true);\">"+title+"</a><span style='float:right;color:#808080;'>"+starttime+"</span></li>");
			});
		}else{
			$("#article").html("<img style='width:300px;height:325px' src='images/zhanwuxinxi1.jpg'/>");
		}
	});
}

//显示我的物业通知详细信息
function showBoard(msgid) {
	var url = _ctx+"/common/bulletinMessage/msgBoardAction_detail.act?msgId="+msgid;
	//openEasyWinNotResizable('winId','物业通知',url,'800','450',false);
	showShelter('670','520',url);
	$("#board_content_"+msgid).css("fontWeight", "");
}

//我的消息弹框效果
function shouNewInfo(msgId){
	$("#notice"+msgId).html("已读");
	var url = _ctx+"/common/bulletinMessage/msgBoxAction_detailReciverbox.act?msgId=" + msgId;
	showShelter('700','430',url);
}

//我的访客弹框
function shouNewInfo1(msgId){
	var url = _ctx+"/app/myvisit/showOpt.act?vsid=" + msgId;
	showShelter('670','465',url);
}

//我的待办事项弹框
function shouNewInfo2(msgId){
	var url = _ctx+"/message/queryMyMatterInfo.act?MessageItemGuid=" + msgId;
	showShelter('670','520',url);

}

//显示我的消息详细页面
function showInfo(msgId) {
	$("#notice"+msgId).html("已读");
	var url = _ctx+"/common/bulletinMessage/msgBoxAction_detailReciverbox.act?msgId=" + msgId;
	openEasyWin('winId','查看消息',url,'700','500',true);
	$("#content_"+msgId).css("fontWeight", "");
	//init();
}

//改变更多连接跳转的Action
function UpAction(action,Id){
	$("#action").attr('value',action);
	$("a[name='tongzhi']").removeClass("selected");
	$("#"+Id).addClass("selected");
	if(Id=='queryFaxId')
	{
		$("#imageSrc").attr('src','images/chakanxiangxi.jpg');
		
	}else
	{
		$("#imageSrc").attr('src','images/t_4.jpg');
	}
	
	if("queryMsgList" == action)
		loadMsg();
	if("queryMyCallList" == action)
		loadVisitor();
	if("queryMyMatter" == action)
		LoadMatter();
	if("queryFax" == action){
		loadFax();
	}
}

function init(){
	
	//读取未读消息数量
	$.getJSON(_ctx+"/message/queryMsgNotReadNum.act", function(json){
		var num = json.NUM;
		$("#msgNotReadNum").text('['+num+'条]');
		if(num > 0)
			$("#msgNotReadNum").css('color','red');
	});
	
	// 读取我的访客未读消息数量
	$.getJSON(_ctx+"/message/getVisitorNotReadCount.act", function(json){
		var num = json.NUM;
		$("#visitorNotReadCount").text('['+num+'条]');
		if(num > 0)
			$("#visitorNotReadCount").css('color','red');
	});
}
function boardInit(){
	//默认加载我的物业通知
	loadBoard();		
	loadArticle();
}
//更多连接跳转
function JumpUrl(){
	if(JumpUrl == "" || JumpUrl == null){
		$("#action").attr('value',"queryMsgList");
	}
	if($("#action").val() == "queryFax")
	{
		url = _ctx+"/app/fax/index.act";
		//window.open(url);	
		window.location.href = url;
		return ;
	}
	url = _ctx+"/message/"+$("#action").val() + ".act";
	window.location.href = url;
}

//显示我的访客详细页面
function showVis(vsid){
	var url = _ctx+"/app/myvisit/showOpt.act?vsid="+vsid;
	openEasyWin("winId","访问申请",url,"700","500",true);
}

//显示我的待办事项详细信息页面
function showMatter(MessageItemGuid){
	var url = _ctx+"/message/queryMyMatterInfo.act?MessageItemGuid="+MessageItemGuid;
	openEasyWin("winId","待办事项",url,"700","500",true);
}

//
var CalendarData=new Array(100);
var madd=new Array(12);
var tgString="甲乙丙丁戊己庚辛壬癸";
var dzString="子丑寅卯辰巳午未申酉戌亥";
var numString="一二三四五六七八九十";
var monString="正二三四五六七八九十冬腊";
var weekString="日一二三四五六";
var sx="鼠牛虎兔龙蛇马羊猴鸡狗猪";
var cYear,cMonth,cDay,TheDate;
CalendarData = new Array(0xA4B,0x5164B,0x6A5,0x6D4,0x415B5,0x2B6,0x957,0x2092F,0x497,0x60C96,0xD4A,0xEA5,0x50DA9,0x5AD,0x2B6,0x3126E, 0x92E,0x7192D,0xC95,0xD4A,0x61B4A,0xB55,0x56A,0x4155B, 0x25D,0x92D,0x2192B,0xA95,0x71695,0x6CA,0xB55,0x50AB5,0x4DA,0xA5B,0x30A57,0x52B,0x8152A,0xE95,0x6AA,0x615AA,0xAB5,0x4B6,0x414AE,0xA57,0x526,0x31D26,0xD95,0x70B55,0x56A,0x96D,0x5095D,0x4AD,0xA4D,0x41A4D,0xD25,0x81AA5,0xB54,0xB6A,0x612DA,0x95B,0x49B,0x41497,0xA4B,0xA164B, 0x6A5,0x6D4,0x615B4,0xAB6,0x957,0x5092F,0x497,0x64B, 0x30D4A,0xEA5,0x80D65,0x5AC,0xAB6,0x5126D,0x92E,0xC96,0x41A95,0xD4A,0xDA5,0x20B55,0x56A,0x7155B,0x25D,0x92D,0x5192B,0xA95,0xB4A,0x416AA,0xAD5,0x90AB5,0x4BA,0xA5B, 0x60A57,0x52B,0xA93,0x40E95);
madd[0]=0;
madd[1]=31;
madd[2]=59;
madd[3]=90;
madd[4]=120;
madd[5]=151;
madd[6]=181;
madd[7]=212;
madd[8]=243;
madd[9]=273;
madd[10]=304;
madd[11]=334;
 
function GetBit(m,n){
	return (m>>n)&1;
}
function e2c(){
	TheDate= (arguments.length!=3) ? new Date() : new Date(arguments[0],arguments[1],arguments[2]);
	var total,m,n,k;
	var isEnd=false;
	var tmp=TheDate.getYear();
	if(tmp<1900){
	 tmp+=1900;
	}
	total=(tmp-1921)*365+Math.floor((tmp-1921)/4)+madd[TheDate.getMonth()]+TheDate.getDate()-38;
	 
	if(TheDate.getYear()%4==0&&TheDate.getMonth()>1) {
	 total++;
	}
	for(m=0;;m++){
		k=(CalendarData[m]<0xfff)?11:12;
		for(n=k;n>=0;n--){
			if(total<=29+GetBit(CalendarData[m],n)){
			isEnd=true; break;
			}
			total=total-29-GetBit(CalendarData[m],n);
		}
		if(isEnd) break;
	}
	cYear=1921 + m;
	cMonth=k-n+1;
	cDay=total;
	if(k==12){
		if(cMonth==Math.floor(CalendarData[m]/0x10000)+1){
			cMonth=1-cMonth;
		}
		if(cMonth>Math.floor(CalendarData[m]/0x10000)+1){
			cMonth--;
		}
	}
}
 
function GetcDateString(){
	var tmp="";
	tmp+=tgString.charAt((cYear-4)%10);
	tmp+=dzString.charAt((cYear-4)%12);
	tmp+="(";
	tmp+=sx.charAt((cYear-4)%12);
	tmp+=")年 ";
	if(cMonth<1){
		tmp+="(闰)";
		tmp+=monString.charAt(-cMonth-1);
	}else{
		tmp+=monString.charAt(cMonth-1);
	}
	tmp+="月";
	tmp+=(cDay<11)?"初":((cDay<20)?"十":((cDay<30)?"廿":"三十"));
	if (cDay%10!=0||cDay==10){
		tmp+=numString.charAt((cDay-1)%10);
	}
	return tmp;
}
 
function GetLunarDay(solarYear,solarMonth,solarDay){
	//solarYear = solarYear<1900?(1900+solarYear):solarYear;
	if(solarYear<1921 || solarYear>2020){
		return "";
	}else{
		solarMonth = (parseInt(solarMonth)>0) ? (solarMonth-1) : 11;
		e2c(solarYear,solarMonth,solarDay);
		return GetcDateString();
	}
}
 
var D=new Date();
var yy=D.getFullYear();
var mm=D.getMonth()+1;
var dd=D.getDate();
var ww=D.getDay();
var ss=parseInt(D.getTime() / 1000);
if (yy<100) yy="19"+yy;
function showCal(){
	document.write(GetLunarDay(yy,mm,dd));
}

function showCal1(D){
	//var D=new Date();
    var yy=D.getFullYear();
    var mm=D.getMonth()+1;
    var dd=D.getDate();
    var ww=D.getDay();
    var ss=parseInt(D.getTime() / 1000);
    if (yy<100) yy="19"+yy;
	//document.write();
    //alert(GetLunarDay(yy,mm,dd));
    return GetLunarDay(yy,mm,dd);
}