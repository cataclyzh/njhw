<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/include/meta.jsp" %>
<jsp:include page="/app/portal/toolbar/toolbar_new.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>运营管理系统</title>
<style type="text/css">
    div,td,input
    {
    	font-size:12px;
    }
    .oper_clear {
		clear: both;
		height: 1px;
		width: 100%;
		overflow: hidden;
	}
    
    .oper_fooer {
		background: transparent url("../images/oper_fooer.jpg") no-repeat scroll left top;
		height: 99px;
		text-align: center;
		line-height: 30px;
		color: #157F9B;
		font-size: 12px;
		padding-top: 40px;
	}
	
	ul,li{
			margin: 0;
			padding: 0;
		}
		ul{
			list-style: none;
		}
		a{
			text-decoration: none;
		}
		#wrapper{
			text-align: center;
		}
		#otherVideo{
			
		}
		#otherVideoList{
			/*position: fixed;
			top: 10px;
			*/
			font-size:20px;
		}
		#otherVideoList ul{
			width: 300px;
			text-align: left;
		}
		#otherVideoList ul li{
			text-align: left;
			background-image: url(images/oper_ovder_a.jpg);
			background-repeat:no-repeat;
			padding-left: 35px;
			text-align: left;
			height: 40px
		}
		#otherVideoDetail{
			margin: auto;
			width: 720px;
			min-height: 120px;
		}
		
		#otherVideoDetail ul{
			border: 1px solid #D8E7EB;
			clear: both;
			overflow: hidden;
			min-height: 120px;
		}
		
		#otherVideoDetail .title{
			height: 27px;
			line-height: 27px;
			border-radius: 5px 5px 0 0;
			background-color: #78A6B3;
			text-align: center;				
		}
		#otherVideoDetail .content{
			
		}
		#otherVideoDetail .content ul li{
			float: left;
			margin: 5px;
			width: 120px;
		}
	
</style>
<script type="text/javascript" src="scripts/jquery.js"></script>

<script type="text/javascript" src="${ctx}/app/portal/toolbar/showModel.js" ></script>
<script type="text/javascript" src="${ctx}/app/omc/js/showModel.js" ></script>
<script type="text/javascript">
function logout(){
  	var url ="${ctx}/j_spring_security_logout" + '?t=' + Math.random();
  	window.open(url, "_self");
}

//更换视频IP地址
function changeVideoByIp(ip){
	try{
		var url = 'rtsp://inc:inc@' + ip + '/stream1';
		var idd = vlc.playlist.add(url);		
		vlc.playlist.playItem(idd);		
	}catch(e){		
	}
}  

$(document).ready(function(){
	var $winW = $(window).width();
	var $videoW = $('#vlc').width();
	var vlc = document.getElementById('vlc');
	$(function(){
		var left = ($winW + 720) / 2;
		//$('#otherVideoList').css({'left': left});
		
		$('#otherVideoDetail>ul').eq(0).show().siblings().hide();
		$('#otherVideoList>ul>li').click(function(){
			var index = $(this).index();
			$('#otherVideoDetail>ul').eq(index).show().siblings().hide();
			
		});
	});
});


</script>
</head>
  
 
<body style="background: #566D75 url('images/oper_index.jpg') repeat-x scroll left top;">
<div style="width:1338px; margin: 0px auto;">
	<div style="background: transparent url('images/oper_logo.jpg') no-repeat scroll left top;height: 99px;">
		<div style="width: 440px;height: 80px;cursor: pointer;float: left;">
		   <div style="float:left;width:280px;height:80px;cursor:pointer;" onclick="location.href='${ctx}/app/integrateservice/init.act';">&nbsp;</div>
		   <div style="float:left;width:160px;height:80px;cursor:pointer;" onclick="location.href='${ctx}/app/omc/newIndex.jsp';">&nbsp;</div>
		</div>
		<div style="width:878px;height:80px;float:right;line-height:80px;text-align:right;padding-right:20px;font-size:12px;color:#FFF;">
        	<span style="background:transparent url('images/oper_mang.jpg') no-repeat scroll left top; padding-left: 20px;">当前用户：
        		<script type="text/javascript">var len = '${_username}';if(len.replace(/(^\s*)|(\s*$)/g, "").length > 6){var text = len.substring(0,6) + "..."}else{text = len}document.write(text);</script>
        	</span>
        	<a style="cursor:pointer;background: transparent url('images/oper_mist.jpg') no-repeat scroll left top; padding-left: 20px; color: #FFF; height: 20px; line-height: 20px;	margin-left: 10px; display: inline-block;text-decoration: none;" onclick="showShelter('780','510','${ctx}/common/userOrgMgr/personInfoChreach.act')">个人设置</a>
        	<a style="cursor:pointer;background: transparent url('images/oper_out.jpg') no-repeat scroll left top;  padding-left: 20px; color: #FFF; height: 20px; line-height: 20px;	margin-left: 10px; display: inline-block;text-decoration: none;"  onclick="logout();return false">退出登录</a>
        </div>
	</div>
	<div style="background:#FFF none repeat scroll 0% 0%; padding: 0px 10px 10px; height:490px; background-color:#FFFFFF" >
		<!-- 中间 -->
		<div style="width: 730px; height:500px; float: left;padding-left: 10px;">
			<div style="padding: 10px 0 0 0">
				<div  style="color: red ; font-size: 14px;">
		         	&nbsp;&nbsp;&nbsp; 说明：首次使用，请下载并安装&nbsp;<a href="scripts/vlc.exe" title="vlc" target="_blank">视频驱动</a>
			    </div>
                <div style="width:548px;margin-top: 2px;">
					<div id="otherVideo">
						<!--[if IE]>
						   <object type='application/x-vlc-plugin' id='vlc' events='True'
							   classid='clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921' codebase="http://downloads.videolan.org/pub/videolan/vlc/latest/win32/axvlc.cab" width="720" height="540">
								  <param name='mrl' value='rtsp://inc:inc@10.250.248.240/stream1' />
								  <param name='volume' value='0' />
								  <param name='autoplay' value='true' />
								  <param name='loop' value='false' />
								  <param name='fullscreen' value='false' />
							</object>
						<![endif]-->
						<!--[if !IE]><!-->
							<object type='application/x-vlc-plugin' id='vlc' events='True' width="720" height="450" pluginspage="http://www.videolan.org" codebase="http://downloads.videolan.org/pub/videolan/vlc-webplugins/2.0.6/npapi-vlc-2.0.6.tar.xz">
								<param name='mrl' value='rtsp://inc:inc@10.250.248.240/stream1' />
								<param name='volume' value='0' />
								<param name='autoplay' value='true' />
								<param name='loop' value='false' />
								<param name='fullscreen' value='false' />
							</object>
						<!--<![endif]-->
					</div>
		   		</div>  				
            </div>
        </div>
        <div id="otherVideoList" style=" margin:30px 0 0 0; width: 200px; height:435px; float: left; padding:15px 0 0 10px; background-color:green">
			<ul>
				<li>
					<a href="javascript:void(0)">A座南</a>
				</li>                   
				<li>                    
					<a href="javascript:void(0)">A座东</a>
				</li>                   
				<li>                    
					<a href="javascript:void(0)">B座西</a>
				</li>                   
				<li>                    
					<a href="javascript:void(0)">B座南</a>
				</li>                   
				<li>                    
					<a href="javascript:void(0)">C座北</a>
				</li>                   
				<li>                    
					<a href="javascript:void(0)">C座西</a>
				</li>                   
				<li>                    
					<a href="javascript:void(0)">D座东</a>
				</li>                  
				<li>                   
					<a href="javascript:void(0)">D座北</a>
				</li>                  
				<li>
					<a href="javascript:void(0)">3层球机</a>
				</li>                   
				<li>                    
					<a href="javascript:void(0)">19层球机</a>
				</li>
			</ul>
		</div>
		
		<div id="otherVideoDetail" style="margin: 30px 0 0 0;width: 340px; height:435px; float: left; padding:15px 15px 0 10px; background-color:green">
			<ul>
				<li class="title"><strong>A座南</strong></li>
				<li class="content">
					<ul>
						<li id="10.250.248.93" onclick="changeVideoByIp('10.250.248.93')"><input type="radio" name="SelectA"/>A座-1层南</li>
						<li id="10.250.248.57" onclick="changeVideoByIp('10.250.248.57')"><input type="radio" name="SelectA"/>A座1层南</li>
						<li id="10.250.248.90" onclick="changeVideoByIp('10.250.248.90')"><input type="radio" name="SelectA"/>A座2层南</li>
						<li id="10.250.248.151" onclick="changeVideoByIp('10.250.248.151')"><input type="radio" name="SelectA"/>A座3层南</li>
						<li id="10.250.248.51" onclick="changeVideoByIp('10.250.248.51')"><input type="radio" name="SelectA"/>A座4层南</li>
						<li id="10.250.248.53" onclick="changeVideoByIp('10.250.248.53')"><input type="radio" name="SelectA"/>A座5层南</li>
						<li id="10.250.248.35" onclick="changeVideoByIp('10.250.248.35')"><input type="radio" name="SelectA"/>A座6层南</li>
						<li id="10.250.248.21" onclick="changeVideoByIp('10.250.248.21')"><input type="radio" name="SelectA"/>A座7层南</li>
						<li id="10.250.248.23" onclick="changeVideoByIp('10.250.248.23')"><input type="radio" name="SelectA"/>A座8层南</li>
						<li id="10.250.248.76" onclick="changeVideoByIp('10.250.248.76')"><input type="radio" name="SelectA"/>A座9层南</li>
						<li id="10.250.248.26" onclick="changeVideoByIp('10.250.248.26')"><input type="radio" name="SelectA"/>A座10层南</li>
						<li id="10.250.248.27" onclick="changeVideoByIp('10.250.248.27')"><input type="radio" name="SelectA"/>A座11层南</li>
						<li id="10.250.248.48" onclick="changeVideoByIp('10.250.248.48')"><input type="radio" name="SelectA"/>A座12层南</li>
						<li id="10.250.248.69" onclick="changeVideoByIp('10.250.248.69')"><input type="radio" name="SelectA"/>A座13层南</li>
						<li id="10.250.248.74" onclick="changeVideoByIp('10.250.248.74')"><input type="radio" name="SelectA"/>A座14层南</li>
						<li id="10.250.248.30" onclick="changeVideoByIp('10.250.248.30')"><input type="radio" name="SelectA"/>A座15层南</li>
						<li id="10.250.248.31" onclick="changeVideoByIp('10.250.248.31')"><input type="radio" name="SelectA"/>A座16层南</li>
						<li id="10.250.248.33" onclick="changeVideoByIp('10.250.248.33')"><input type="radio" name="SelectA"/>A座17层南</li>
						<li id="10.250.248.42" onclick="changeVideoByIp('10.250.248.42')"><input type="radio" name="SelectA"/>A座18层南</li>
						<li id="10.250.248.96" onclick="changeVideoByIp('10.250.248.96')"><input type="radio" name="SelectA"/>A座19层南</li>
					</ul>
				</li>
			</ul>
			<ul>
				<li class="title"><strong>A座东</strong></li>
				<li class="content">
					<ul>
						<li id="10.250.248.94" onclick="changeVideoByIp('10.250.248.94')"><input type="radio" name="SelectA"/>A座-1层东</li>
						<li id="10.250.248.59" onclick="changeVideoByIp('10.250.248.59')"><input type="radio" name="SelectA"/>A座1层东</li>
						<li id="10.250.248.91" onclick="changeVideoByIp('10.250.248.91')"><input type="radio" name="SelectA"/>A座2层东</li>
						<li id="10.250.248.152" onclick="changeVideoByIp('10.250.248.152')"><input type="radio" name="SelectA"/>A座3层东</li>
						<li id="10.250.248.52" onclick="changeVideoByIp('10.250.248.52')"><input type="radio" name="SelectA"/>A座4层东</li>
						<li id="10.250.248.68" onclick="changeVideoByIp('10.250.248.68')"><input type="radio" name="SelectA"/>A座5层东</li>
						<li id="10.250.248.54" onclick="changeVideoByIp('10.250.248.54')"><input type="radio" name="SelectA"/>A座6层东</li>
						<li id="10.250.248.22" onclick="changeVideoByIp('10.250.248.22')"><input type="radio" name="SelectA"/>A座7层东</li>
						<li id="10.250.248.24" onclick="changeVideoByIp('10.250.248.24')"><input type="radio" name="SelectA"/>A座8层东</li>
						<li id="10.250.248.25" onclick="changeVideoByIp('10.250.248.25')"><input type="radio" name="SelectA"/>A座9层东</li>
						<li id="10.250.248.75" onclick="changeVideoByIp('10.250.248.75')"><input type="radio" name="SelectA"/>A座10层东</li>
						<li id="10.250.248.49" onclick="changeVideoByIp('10.250.248.49')"><input type="radio" name="SelectA"/>A座11层东</li>
						<li id="10.250.248.36" onclick="changeVideoByIp('10.250.248.36')"><input type="radio" name="SelectA"/>A座12层东</li>
						<li id="10.250.248.28" onclick="changeVideoByIp('10.250.248.28')"><input type="radio" name="SelectA"/>A座13层东</li>
						<li id="10.250.248.29" onclick="changeVideoByIp('10.250.248.29')"><input type="radio" name="SelectA"/>A座14层东</li>
						<li id="10.250.248.73" onclick="changeVideoByIp('10.250.248.73')"><input type="radio" name="SelectA"/>A座15层东</li>
						<li id="10.250.248.32" onclick="changeVideoByIp('10.250.248.32')"><input type="radio" name="SelectA"/>A座16层东</li>
						<li id="10.250.248.72" onclick="changeVideoByIp('10.250.248.72')"><input type="radio" name="SelectA"/>A座17层东</li>
						<li id="10.250.248.41" onclick="changeVideoByIp('10.250.248.41')"><input type="radio" name="SelectA"/>A座18层东</li>
						<li id="10.250.248.97" onclick="changeVideoByIp('10.250.248.97')"><input type="radio" name="SelectA"/>A座19层东</li>
					</ul>
				</li>
			</ul>
			<ul>
				<li class="title"><strong>B座西</strong></li>
				<li class="content">
					<ul>
						<li id="10.250.248.95" onclick="changeVideoByIp('10.250.248.95')"><input type="radio" name="SelectA"/>B座-1层西</li>
						<li id="10.250.248.62" onclick="changeVideoByIp('10.250.248.62')"><input type="radio" name="SelectA"/>B座1层西</li>
						<li id="10.250.248.34" onclick="changeVideoByIp('10.250.248.34')"><input type="radio" name="SelectA"/>B座2层西</li>
						<li id="10.250.248.153" onclick="changeVideoByIp('10.250.248.153')"><input type="radio" name="SelectA"/>B座3层西</li>
						<li id="10.250.248.39" onclick="changeVideoByIp('10.250.248.39')"><input type="radio" name="SelectA"/>B座4层西</li>
						<li id="10.250.248.47" onclick="changeVideoByIp('10.250.248.47')"><input type="radio" name="SelectA"/>B座5层西</li>
						<li id="10.250.248.63" onclick="changeVideoByIp('10.250.248.63')"><input type="radio" name="SelectA"/>B座6层西</li>
						<li id="10.250.248.45" onclick="changeVideoByIp('10.250.248.45')"><input type="radio" name="SelectA"/>B座7层西</li>
						<li id="10.250.248.46" onclick="changeVideoByIp('10.250.248.46')"><input type="radio" name="SelectA"/>B座8层西</li>
						<li id="10.250.248.40" onclick="changeVideoByIp('10.250.248.40')"><input type="radio" name="SelectA"/>B座9层西</li>
						<li id="10.250.248.86" onclick="changeVideoByIp('10.250.248.86')"><input type="radio" name="SelectA"/>B座10层西</li>
						<li id="10.250.248.88" onclick="changeVideoByIp('10.250.248.88')"><input type="radio" name="SelectA"/>B座11层西</li>
						<li id="10.250.248.85" onclick="changeVideoByIp('10.250.248.85')"><input type="radio" name="SelectA"/>B座12层西</li>
						<li id="10.250.248.84" onclick="changeVideoByIp('10.250.248.84')"><input type="radio" name="SelectA"/>B座13层西</li>
						<li id="10.250.248.81" onclick="changeVideoByIp('10.250.248.81')"><input type="radio" name="SelectA"/>B座14层西</li>
						<li id="10.250.248.80" onclick="changeVideoByIp('10.250.248.80')"><input type="radio" name="SelectA"/>B座15层西</li>
						<li id="10.250.248.78" onclick="changeVideoByIp('10.250.248.78')"><input type="radio" name="SelectA"/>B座16层西</li>
						<li id="10.250.248.60" onclick="changeVideoByIp('10.250.248.60')"><input type="radio" name="SelectA"/>B座17层西</li>
						<li id="10.250.248.77" onclick="changeVideoByIp('10.250.248.77')"><input type="radio" name="SelectA"/>B座18层西</li>
					</ul>
				</li>
			</ul>
			<ul>
				<li class="title"><strong>B座南</strong></li>
				<li class="content">
					<ul>
						<li id="10.250.248.92" onclick="changeVideoByIp('10.250.248.92')"><input type="radio" name="SelectA"/>B座-1层南</li>
						<li id="10.250.248.66" onclick="changeVideoByIp('10.250.248.66')"><input type="radio" name="SelectA"/>B座1层南</li>
						<li id="10.250.248.55" onclick="changeVideoByIp('10.250.248.55')"><input type="radio" name="SelectA"/>B座2层南</li>
						<li id="10.250.248.154" onclick="changeVideoByIp('10.250.248.154')"><input type="radio" name="SelectA"/>B座3层南</li>
						<li id="10.250.248.37" onclick="changeVideoByIp('10.250.248.37')"><input type="radio" name="SelectA"/>B座4层南</li>
						<li id="10.250.248.38" onclick="changeVideoByIp('10.250.248.38')"><input type="radio" name="SelectA"/>B座5层南</li>
						<li id="10.250.248.67" onclick="changeVideoByIp('10.250.248.67')"><input type="radio" name="SelectA"/>B座6层南</li>
						<li id="10.250.248.44" onclick="changeVideoByIp('10.250.248.44')"><input type="radio" name="SelectA"/>B座7层南</li>
						<li id="10.250.248.89" onclick="changeVideoByIp('10.250.248.89')"><input type="radio" name="SelectA"/>B座8层南</li>
						<li id="10.250.248.61" onclick="changeVideoByIp('10.250.248.61')"><input type="radio" name="SelectA"/>B座9层南</li>
						<li id="10.250.248.50" onclick="changeVideoByIp('10.250.248.50')"><input type="radio" name="SelectA"/>B座10层南</li>
						<li id="10.250.248.87" onclick="changeVideoByIp('10.250.248.87')"><input type="radio" name="SelectA"/>B座11层南</li>
						<li id="10.250.248.65" onclick="changeVideoByIp('10.250.248.65')"><input type="radio" name="SelectA"/>B座12层南</li>
						<li id="10.250.248.83" onclick="changeVideoByIp('10.250.248.83')"><input type="radio" name="SelectA"/>B座13层南</li>
						<li id="10.250.248.82" onclick="changeVideoByIp('10.250.248.82')"><input type="radio" name="SelectA"/>B座14层南</li>
						<li id="10.250.248.56" onclick="changeVideoByIp('10.250.248.56')"><input type="radio" name="SelectA"/>B座15层南</li>
						<li id="10.250.248.79" onclick="changeVideoByIp('10.250.248.79')"><input type="radio" name="SelectA"/>B座16层南</li>
						<li id="10.250.248.64" onclick="changeVideoByIp('10.250.248.64')"><input type="radio" name="SelectA"/>B座17层南</li>
						<li id="10.250.248.43" onclick="changeVideoByIp('10.250.248.43')"><input type="radio" name="SelectA"/>B座18层南</li>
					</ul>
				</li>
			</ul>
			<ul>
				<li class="title"><strong>C座北</strong></li>
				<li class="content">
					<ul>
						<li id="10.250.247.94" onclick="changeVideoByIp('10.250.247.94')"><input type="radio" name="SelectA"/>C座-1层北</li>
						<li id="10.250.247.48" onclick="changeVideoByIp('10.250.247.48')"><input type="radio" name="SelectA"/>C座1层北</li>
						<li id="10.250.247.65" onclick="changeVideoByIp('10.250.247.65')"><input type="radio" name="SelectA"/>C座2层北</li>
						<li id="10.250.247.151" onclick="changeVideoByIp('10.250.247.151')"><input type="radio" name="SelectA"/>C座3层北</li>
						<li id="10.250.247.66" onclick="changeVideoByIp('10.250.247.66')"><input type="radio" name="SelectA"/>C座4层北</li>
						<li id="10.250.247.74" onclick="changeVideoByIp('10.250.247.74')"><input type="radio" name="SelectA"/>C座5层北</li>
						<li id="10.250.247.25" onclick="changeVideoByIp('10.250.247.25')"><input type="radio" name="SelectA"/>C座6层北</li>
						<li id="10.250.247.30" onclick="changeVideoByIp('10.250.247.30')"><input type="radio" name="SelectA"/>C座7层北</li>
						<li id="10.250.247.22" onclick="changeVideoByIp('10.250.247.22')"><input type="radio" name="SelectA"/>C座8层北</li>
						<li id="10.250.247.33" onclick="changeVideoByIp('10.250.247.33')"><input type="radio" name="SelectA"/>C座9层北</li>
						<li id="10.250.247.69" onclick="changeVideoByIp('10.250.247.69')"><input type="radio" name="SelectA"/>C座10层北</li>
						<li id="10.250.247.38" onclick="changeVideoByIp('10.250.247.38')"><input type="radio" name="SelectA"/>C座11层北</li>
						<li id="10.250.247.24" onclick="changeVideoByIp('10.250.247.24')"><input type="radio" name="SelectA"/>C座12层北</li>
						<li id="10.250.247.23" onclick="changeVideoByIp('10.250.247.23')"><input type="radio" name="SelectA"/>C座13层北</li>
						<li id="10.250.247.88" onclick="changeVideoByIp('10.250.247.88')"><input type="radio" name="SelectA"/>C座14层北</li>
						<li id="10.250.247.52" onclick="changeVideoByIp('10.250.247.52')"><input type="radio" name="SelectA"/>C座15层北</li>
						<li id="10.250.247.55" onclick="changeVideoByIp('10.250.247.55')"><input type="radio" name="SelectA"/>C座16层北</li>
						<li id="10.250.247.51" onclick="changeVideoByIp('10.250.247.51')"><input type="radio" name="SelectA"/>C座17层北</li>
						<li id="10.250.247.54" onclick="changeVideoByIp('10.250.247.54')"><input type="radio" name="SelectA"/>C座18层北</li>
						<li id="10.250.247.62" onclick="changeVideoByIp('10.250.247.62')"><input type="radio" name="SelectA"/>C座19层北</li>
					</ul>
				</li>
			</ul>
			<ul>
				<li class="title"><strong>C座西</strong></li>
				<li class="content">
					<ul>
						<li id="10.250.247.91" onclick="changeVideoByIp('10.250.247.91')"><input type="radio" name="SelectA"/>C座-1层西</li>
						<li id="10.250.247.95" onclick="changeVideoByIp('10.250.247.95')"><input type="radio" name="SelectA"/>C座1层西</li>
						<li id="10.250.247.47" onclick="changeVideoByIp('10.250.247.47')"><input type="radio" name="SelectA"/>C座2层西</li>
						<li id="10.250.247.152" onclick="changeVideoByIp('10.250.247.152')"><input type="radio" name="SelectA"/>C座3层西</li>
						<li id="10.250.247.72" onclick="changeVideoByIp('10.250.247.72')"><input type="radio" name="SelectA"/>C座4层西</li>
						<li id="10.250.247.73" onclick="changeVideoByIp('10.250.247.73')"><input type="radio" name="SelectA"/>C座5层西</li>
						<li id="10.250.247.34" onclick="changeVideoByIp('10.250.247.34')"><input type="radio" name="SelectA"/>C座6层西</li>
						<li id="10.250.247.89" onclick="changeVideoByIp('10.250.247.89')"><input type="radio" name="SelectA"/>C座7层西</li>
						<li id="10.250.247.26" onclick="changeVideoByIp('10.250.247.26')"><input type="radio" name="SelectA"/>C座8层西</li>
						<li id="10.250.247.31" onclick="changeVideoByIp('10.250.247.31')"><input type="radio" name="SelectA"/>C座9层西</li>
						<li id="10.250.247.36" onclick="changeVideoByIp('10.250.247.36')"><input type="radio" name="SelectA"/>C座10层西</li>
						<li id="10.250.247.39" onclick="changeVideoByIp('10.250.247.39')"><input type="radio" name="SelectA"/>C座11层西</li>
						<li id="10.250.247.21" onclick="changeVideoByIp('10.250.247.21')"><input type="radio" name="SelectA"/>C座12层西</li>
						<li id="10.250.247.27" onclick="changeVideoByIp('10.250.247.27')"><input type="radio" name="SelectA"/>C座13层西</li>
						<li id="10.250.247.37" onclick="changeVideoByIp('10.250.247.37')"><input type="radio" name="SelectA"/>C座14层西</li>
						<li id="10.250.247.90" onclick="changeVideoByIp('10.250.247.90')"><input type="radio" name="SelectA"/>C座15层西</li>
						<li id="10.250.247.53" onclick="changeVideoByIp('10.250.247.53')"><input type="radio" name="SelectA"/>C座16层西</li>
						<li id="10.250.247.56" onclick="changeVideoByIp('10.250.247.56')"><input type="radio" name="SelectA"/>C座17层西</li>
						<li id="10.250.247.57" onclick="changeVideoByIp('10.250.247.57')"><input type="radio" name="SelectA"/>C座18层西</li>
						<li id="10.250.247.145" onclick="changeVideoByIp('10.250.247.145')"><input type="radio" name="SelectA"/>C座19层房间</li>
					</ul>
				</li>
			</ul>
			<ul>
				<li class="title"><strong>D座东</strong></li>
				<li class="content">
					<ul>
						<li id="10.250.247.92" onclick="changeVideoByIp('10.250.247.92')"><input type="radio" name="SelectA"/>D座-1层东</li>
						<li id="10.250.247.68" onclick="changeVideoByIp('10.250.247.68')"><input type="radio" name="SelectA"/>D座1层东</li>
						<li id="10.250.247.198" onclick="changeVideoByIp('10.250.247.198')"><input type="radio" name="SelectA"/>D座1层房间</li>
						<li id="10.250.247.199" onclick="changeVideoByIp('10.250.247.199')"><input type="radio" name="SelectA"/>D座1层房间</li>
						<li id="10.250.247.86" onclick="changeVideoByIp('10.250.247.86')"><input type="radio" name="SelectA"/>D座2层东</li>
						<li id="10.250.247.153" onclick="changeVideoByIp('10.250.247.153')"><input type="radio" name="SelectA"/>D座3层东</li>
						<li id="10.250.247.32" onclick="changeVideoByIp('10.250.247.32')"><input type="radio" name="SelectA"/>D座4层东</li>
						<li id="10.250.247.85" onclick="changeVideoByIp('10.250.247.85')"><input type="radio" name="SelectA"/>D座5层东</li>
						<li id="10.250.247.42" onclick="changeVideoByIp('10.250.247.42')"><input type="radio" name="SelectA"/>D座6层东</li>
						<li id="10.250.247.43" onclick="changeVideoByIp('10.250.247.43')"><input type="radio" name="SelectA"/>D座7层东</li>
						<li id="10.250.247.84" onclick="changeVideoByIp('10.250.247.84')"><input type="radio" name="SelectA"/>D座8层东</li>
						<li id="10.250.247.60" onclick="changeVideoByIp('10.250.247.60')"><input type="radio" name="SelectA"/>D座9层东</li>
						<li id="10.250.247.67" onclick="changeVideoByIp('10.250.247.67')"><input type="radio" name="SelectA"/>D座10层东</li>
						<li id="10.250.247.81" onclick="changeVideoByIp('10.250.247.81')"><input type="radio" name="SelectA"/>D座11层东</li>
						<li id="10.250.247.40" onclick="changeVideoByIp('10.250.247.40')"><input type="radio" name="SelectA"/>D座12层东</li>
						<li id="10.250.247.46" onclick="changeVideoByIp('10.250.247.46')"><input type="radio" name="SelectA"/>D座13层东</li>
						<li id="10.250.247.77" onclick="changeVideoByIp('10.250.247.77')"><input type="radio" name="SelectA"/>D座14层东</li>
						<li id="10.250.247.80" onclick="changeVideoByIp('10.250.247.80')"><input type="radio" name="SelectA"/>D座15层东</li>
						<li id="10.250.247.35" onclick="changeVideoByIp('10.250.247.35')"><input type="radio" name="SelectA"/>D座16层东</li>
						<li id="10.250.247.70" onclick="changeVideoByIp('10.250.247.70')"><input type="radio" name="SelectA"/>D座17层东</li>
						<li id="10.250.247.75" onclick="changeVideoByIp('10.250.247.75')"><input type="radio" name="SelectA"/>D座18层东</li>
						<li id="10.250.247.61" onclick="changeVideoByIp('10.250.247.61')"><input type="radio" name="SelectA"/>D座19层北</li>
					</ul>
				</li>
			</ul>		
			<ul>
				<li class="title"><strong>D座北</strong></li>
				<li class="content">
					<ul>
						<li id="10.250.247.93" onclick="changeVideoByIp('10.250.247.93')"><input type="radio" name="SelectA"/>D座-1层北</li>
						<li id="10.250.247.63" onclick="changeVideoByIp('10.250.247.63')"><input type="radio" name="SelectA"/>D座1层北</li>
						<li id="10.250.247.64" onclick="changeVideoByIp('10.250.247.64')"><input type="radio" name="SelectA"/>1层保安室</li>
						<li id="10.250.247.87" onclick="changeVideoByIp('10.250.247.87')"><input type="radio" name="SelectA"/>D座2层北</li>
						<li id="10.250.247.154" onclick="changeVideoByIp('10.250.247.154')"><input type="radio" name="SelectA"/>D座3层北</li>
						<li id="10.250.247.28" onclick="changeVideoByIp('10.250.247.28')"><input type="radio" name="SelectA"/>D座4层北</li>
						<li id="10.250.247.29" onclick="changeVideoByIp('10.250.247.29')"><input type="radio" name="SelectA"/>D座5层北</li>
						<li id="10.250.247.49" onclick="changeVideoByIp('10.250.247.49')"><input type="radio" name="SelectA"/>D座6层北</li>
						<li id="10.250.247.44" onclick="changeVideoByIp('10.250.247.44')"><input type="radio" name="SelectA"/>D座7层北</li>
						<li id="10.250.247.58" onclick="changeVideoByIp('10.250.247.58')"><input type="radio" name="SelectA"/>D座8层北</li>
						<li id="10.250.247.59" onclick="changeVideoByIp('10.250.247.59')"><input type="radio" name="SelectA"/>D座9层北</li>
						<li id="10.250.247.82" onclick="changeVideoByIp('10.250.247.82')"><input type="radio" name="SelectA"/>D座10层北</li>
						<li id="10.250.247.83" onclick="changeVideoByIp('10.250.247.83')"><input type="radio" name="SelectA"/>D座11层北</li>
						<li id="10.250.247.45" onclick="changeVideoByIp('10.250.247.45')"><input type="radio" name="SelectA"/>D座12层北</li>
						<li id="10.250.247.41" onclick="changeVideoByIp('10.250.247.41')"><input type="radio" name="SelectA"/>D座13层北</li>
						<li id="10.250.247.78" onclick="changeVideoByIp('10.250.247.78')"><input type="radio" name="SelectA"/>D座14层北</li>
						<li id="10.250.247.79" onclick="changeVideoByIp('10.250.247.79')"><input type="radio" name="SelectA"/>D座15层北</li>
						<li id="10.250.247.50" onclick="changeVideoByIp('10.250.247.50')"><input type="radio" name="SelectA"/>D座16层北</li>
						<li id="10.250.247.71" onclick="changeVideoByIp('10.250.247.71')"><input type="radio" name="SelectA"/>D座17层北</li>
						<li id="10.250.247.76" onclick="changeVideoByIp('10.250.247.76')"><input type="radio" name="SelectA"/>D座18层北</li>
					</ul>
				</li>
			</ul>		
			<ul>
				<li class="title"><strong>3层球机</strong></li>
				<li class="content">
					<ul>
						<li id="10.250.247.246" onclick="changeVideoByIp('10.250.247.246')"><input type="radio" name="SelectA"/>C座北</li>
						<li id="10.250.248.244" onclick="changeVideoByIp('10.250.248.244')"><input type="radio" name="SelectA"/>A座东</li>
						<li id="10.250.248.245" onclick="changeVideoByIp('10.250.248.245')"><input type="radio" name="SelectA"/>A座南</li>
						<li id="10.250.248.247" onclick="changeVideoByIp('10.250.248.247')"><input type="radio" name="SelectA"/>B座南</li>
						<li id="10.250.248.248" onclick="changeVideoByIp('10.250.248.248')"><input type="radio" name="SelectA"/>B座西</li>
					</ul>
				</li>
			</ul>
			<ul>
				<li class="title"><strong>19层球机</strong></li>
				<li class="content">
					<ul>
						<li id="10.250.247.241" onclick="changeVideoByIp('10.250.247.241')"><input type="radio" name="SelectA"/>D座北</li>
						<li id="10.250.247.242" onclick="changeVideoByIp('10.250.247.242')"><input type="radio" name="SelectA"/>D座东</li>
						<li id="10.250.248.240" onclick="changeVideoByIp('10.250.248.240')"><input type="radio" name="SelectA"/>B座西南角</li>
						<li id="10.250.248.241" onclick="changeVideoByIp('10.250.248.241')"><input type="radio" name="SelectA"/>A座南</li>
						<li id="10.250.248.242" onclick="changeVideoByIp('10.250.248.242')"><input type="radio" name="SelectA"/>A座东</li>
						<li id="10.250.248.243" onclick="changeVideoByIp('10.250.248.243')"><input type="radio" name="SelectA"/>B座西</li>
					</ul>
				</li>
			</ul>
		</div>
        <!-- 中间 -->
	</div>
	<div class="oper_clear"></div>
	<div class="oper_fooer">
       	市信息中心服务热线：68789555　 服务邮箱：NJIC@njnet.gov.cn<br />市机关物业管理服务中心服务热线：68789666　 服务邮箱：NJIC@njnet.gov.cn
    </div>
</div>
</body>
</html>
