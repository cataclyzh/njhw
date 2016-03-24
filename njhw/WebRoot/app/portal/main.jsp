<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<%@ include file="/common/include/meta.jsp"%>
		<title>智慧政务社区信息系统</title>
		<link type="text/css" rel="stylesheet" href="${ctx}/app/portal/css/main.css" />
		<style type="text/css">
		</style>
		<script type="text/javascript" src="${ctx}/app/portal/js/main.js"></script>
		<script type="text/javascript" src="${ctx}/app/portal/js/jquery.min.js"></script>
		<script type="text/javascript" src="${ctx}/app/portal/js/util.js"></script>
		<script type="text/javascript" src="${ctx}/app/portal/js/AjaxUtil.js"></script>
		<script type="text/javascript" src="${ctx}/common/pages/main/layout.js"></script>
		<script src="${ctx}/scripts/constants.js" type="text/javascript"></script>
		<script type="text/javascript">	
			var basePath = '<%=basePath%>';
			var loginurl = MXW_SERVER_URL+'?command=login&uid=${loginname}&pwd_md5=${loginpwd}&t=' + Math.random();
		</script>
		<script language="Javascript">
		/*@cc_on 
		_d=document;
		eval('var document=_d');
		@*/
		</script>
		<!--[if IE 6]> 
		<style type="text/css">
		.LeftMenu a{background-image:url(images/main/out_icon.gif);}
		.LeftMenu a:hover{background:url(images/main/out_icon.gif) 10px -100px no-repeat;}
		</style>
		<script type="text/javascript" src="js/ie_png.js"></script>
		<script type="text/javascript">
		  ie_png.fix('#logo, #cms_txt img,  #tip2, #main_img img, #main_img h1, #main_img div, #btn_news div, #btn_news li,#footer div,#footer_btn6,#footer_btn7,#footer_btn8,#footer_btn9,#footer_btn10, #img_right,#img_left,#news div,#user_mes div,#up,#table_top_span span,#menu_lib,#bg_main,#out_push,#header2_left,#header2_right');  
		</script>
		<![endif]-->
	</head>
	<body onmousemove="moving(event)" onresize="resize()">
	<jsp:include page="/app/portal/toolbar/toolbar.jsp"></jsp:include>
	<iframe  id="loginframe" style="position:absolute;display:none;"></iframe>
	
		<img src="" style="display:none" id="img_select"/>
		<iframe src="" id="header3_iframe" frameborder="0" width="114" class="header3_iframe"></iframe>
		
		<!-- 
		<div class="user_mes" id="user_mes">
			<div class="us_bg1" id="us_bg1"></div>
			<div class="user_name" id="user_name"><span>${username}</span></div>
			<div class="us_bg2" id="us_bg2" title="设置" onmouseover="change_col(1,0,'-50px','us_bg2')" onmouseout="change_col(1,1,'-50px','us_bg2')" onclick="openPwdDiv()"></div>
			<div class="set" id="set"><a title="设置" id="UserInfo" onclick="openPwdDiv()" onmouseover="change_col(1,0,'-50px','us_bg2')" onmouseout="change_col(1,1,'-50px','us_bg2')" hidefocus>设置</a></div>
			<div class="us_bg4" id="us_bg4" title="讨论" onmouseover="change_col(3,0,'-150px','us_bg4')" onmouseout="change_col(3,1,'-150px','us_bg4')" onclick="open_top(3)"></div>
			<div class="talk" id="talk"><a title="讨论" id="Discuss" onclick="open_main2(this, this.id)" onmouseover="change_col(3,0,'-150px','us_bg4')" onmouseout="change_col(3,1,'-150px','us_bg4')" hidefocus>讨论</a></div>
			<div class="us_bg3" id="us_bg3" title="帮助" onmouseover="change_col(4,0,'-100px','us_bg3')" onmouseout="change_col(4,1,'-100px','us_bg3')" onclick="open_top(4)"></div>
			<div class="help1" id="help1"><a title="帮助" id="help" onclick="open_main2(this, this.id)" onmouseover="change_col(4,0,'-100px','us_bg3')" onmouseout="change_col(4,1,'-100px','us_bg3')" hidefocus>帮助</a></div>
			<div class="us_bg6" id="us_bg6" title="退出" onmouseover="change_col(5,0,'-300px','us_bg6')" onmouseout="change_col(5,1,'-300px','us_bg6')" onclick="edit();return false"></div>
			<div class="edt" id="edt"><a href="javascript:void(0)" title="退出" onclick="edit();return false" onmouseover="change_col(5,0,'-300px','us_bg6')" onmouseout="change_col(5,1,'-300px','us_bg6')" hidefocus>退出</a></div>
		</div>
		 -->
		<table width="100%" style="position:absolute;left:0;top:0;z-index:10;" height="130px">
		<tr>
		<td width="310px"><img src="images/manilogo.png" alt="" class="logo" id="logo"/></td>
		<td nowrap="nowrap">
		<div class="header" id="header" onmouseout="header_out()" onmouseover="header_over()">
			<div class="header_left" id="header_left"></div>
			<div class="header_main" id="header_main">
				<ul>
					<li class="li_dom" id="header_load">
						<div class="dom_left"></div>
						<a href="javascript:void(0);" hidefocus>
							请等待</a>
						<div class="dom_right"></div>
					</li>
				</ul>
			</div>
			<div class="header_right" id="header_right"></div>
		</div>
		</td>
		</tr>
		</table>
		<div class="tip2" id="tip2"></div>
		<div class="header2" id="header2">
			<div class="header2_left" id="header2_left"></div>
			<div class="header2_main" id="header2_main">
				<ul id="header2_load">
					<li>
						<a href="javascript:void(0);" hidefocus>
							请等待</a></li>
				</ul>
			</div>
			<div class="header2_right" id="header2_right"></div>
		</div>
		<div class="header3" id="header3">
			<div class="header3_top" id="header3_top"></div>
			<div class="header3_main" id="header3_main">
				<ul id="header3_load">
					<li onclick="open_main2()">
						<a href="javascript:void(0)" hidefocus>请等待</a></li>
				</ul>
			</div>
			<div class="header3_bottom" id="header3_bottom"></div>
		</div>
		<div class="up" onclick="header_up()" title="隐藏" id="up"></div>
		<div class="main_body" id="main_body">
			<div class="main_img" id="main_img">
			<div class="img1_div" onmouseover="img_over2('img1_d',0,'img1')" onmouseout="img_over2('img1_d',1,'img1')" id="img1_div" onclick="img_clk2('img1')"></div>
			<div class="img2_div" onmouseover="img_over2('img2_d',0,'img2')" onmouseout="img_over2('img2_d',1,'img2')" id="img2_div" onclick="img_clk2('img2')"></div>
			<div class="img3_div" onmouseover="img_over2('img3_d',0,'img3')" onmouseout="img_over2('img3_d',1,'img3')" id="img3_div" onclick="img_clk2('img3')"></div>
			<div class="img4_div" onmouseover="img_over2('img4_d',0,'img4')" onmouseout="img_over2('img4_d',1,'img4')" id="img4_div" onclick="img_clk2('img4')"></div>
			<div class="img5_div" onmouseover="img_over2('img5_d',0,'img5')" onmouseout="img_over2('img5_d',1,'img5')" id="img5_div" onclick="img_clk2('img5')"></div>
			<div class="img6_div" onmouseover="img_over2('img6_d',0,'img6')" onmouseout="img_over2('img6_d',1,'img6')" id="img6_div" onclick="img_clk2('img6')"></div>
			<div class="img7_div" onmouseover="img_over2('img7_d',0,'img7')" onmouseout="img_over2('img7_d',1,'img7')" id="img7_div" onclick="img_clk2('img7')"></div>
			<div class="img8_div" onmouseover="img_over2('img8_d',0,'img8')" onmouseout="img_over2('img8_d',1,'img8')" id="img8_div" onclick="img_clk2('img8')"></div>
			<div class="img9_div" onmouseover="img_over2('img9_d',0,'img9')" onmouseout="img_over2('img9_d',1,'img9')" id="img9_div" onclick="img_clk2('img9')"></div>
			<div class="img10_div" onmouseover="img_over2('img10_d',0,'img10')" onmouseout="img_over2('img10_d',1,'img10')" id="img10_div" onclick="img_clk2('img10')"></div>
			<div class="img11_div" onmouseover="img_over2('img11_d',0,'img11')" onmouseout="img_over2('img11_d',1,'img11')" id="img11_div" onclick="img_clk2('img11')"></div>
			<div class="img12_div" onmouseover="img_over2('img12_d',0,'img12')" onmouseout="img_over2('img12_d',1,'img12')" id="img12_div" onclick="img_clk2('img12')"></div>
			<div class="img13_div" onmouseover="img_over2('img13_d',0,'img13')" onmouseout="img_over2('img13_d',1,'img13')" id="img13_div" onclick="img_clk2('img13')"></div>
			<div class="img14_div" onmouseover="img_over2('img14_d',0,'img14')" onmouseout="img_over2('img14_d',1,'img14')" id="img14_div" onclick="img_clk2('img14')"></div>
			<div class="img15_div" onmouseover="img_over2('img15_d',0,'img15')" onmouseout="img_over2('img15_d',1,'img15')" id="img15_div" onclick="img_clk2('img15')"></div>
			<div class="img16_div" onmouseover="img_over2('img16_d',0,'img16')" onmouseout="img_over2('img16_d',1,'img16')" id="img16_div" onclick="img_clk2('img16')"></div>
			<div class="img17_div" onmouseover="img_over2('img17_d',0,'img17')" onmouseout="img_over2('img17_d',1,'img17')" id="img17_div" onclick="img_clk2('img17')"></div>
			<div class="img18_div" onmouseover="img_over2('img18_d',0,'img18')" onmouseout="img_over2('img18_d',1,'img18')" id="img18_div" onclick="img_clk2('img18')"></div>
			<div class="img1_d" id="img1_d"></div>
			<div class="img2_d" id="img2_d"></div>
			<div class="img3_d" id="img3_d"></div>
			<div class="img4_d" id="img4_d"></div>
			<div class="img5_d" id="img5_d"></div>
			<div class="img6_d" id="img6_d"></div>
			<div class="img7_d" id="img7_d"></div>
			<div class="img8_d" id="img8_d"></div>
			<div class="img9_d" id="img9_d"></div>
			<div class="img10_d" id="img10_d"></div>
			<div class="img11_d" id="img11_d"></div>
			<div class="img12_d" id="img12_d"></div>
			<div class="img13_d" id="img13_d"></div>
			<div class="img14_d" id="img14_d"></div>
			<div class="img15_d" id="img15_d"></div>
			<div class="img16_d" id="img16_d"></div>
			<div class="img17_d" id="img17_d"></div>
			<div class="img18_d" id="img18_d"></div>
				<h1 class="img1" id="img1" onclick="img_clk2(this)"><img src="images/main/lemo.png" onmouseover="img_over(this,0)" onmouseout="img_over(this,1)"
						onclick="img_clk(this)" Link="" title_txt="" /><span></span></h1>
				<h1 class="img2" id="img2" onclick="img_clk2(this)"><img src="images/main/lemo.png" onmouseover="img_over(this,0)" onmouseout="img_over(this,1)"
						onclick="img_clk(this)" Link="" title_txt="" /><span></span></h1>
				<h1 class="img3" id="img3" onclick="img_clk2(this)"><img src="images/main/lemo.png" onmouseover="img_over(this,0)" onmouseout="img_over(this,1)"
						onclick="img_clk(this)" Link="" title_txt="" /><span></span></h1>
				<h1 class="img4" id="img4" onclick="img_clk2(this)"><img src="images/main/lemo.png" onmouseover="img_over(this,0)" onmouseout="img_over(this,1)"
						onclick="img_clk(this)" Link="" title_txt="" /><span></span></h1>
				<h1 class="img5" id="img5" onclick="img_clk2(this)"><img src="images/main/lemo.png" onmouseover="img_over(this,0)" onmouseout="img_over(this,1)"
						onclick="img_clk(this)" Link="" title_txt="" /><span></span></h1>
				<h1 class="img6" id="img6" onclick="img_clk2(this)"><img src="images/main/lemo.png" onmouseover="img_over(this,0)" onmouseout="img_over(this,1)"
						onclick="img_clk(this)" Link="" title_txt="" /><span></span></h1>
				<h1 class="img7" id="img7" onclick="img_clk2(this)"><img src="images/main/lemo.png" onmouseover="img_over(this,0)" onmouseout="img_over(this,1)"
						onclick="img_clk(this)" Link="" title_txt="" /><span></span></h1>
				<h1 class="img8" id="img8" onclick="img_clk2(this)"><img src="images/main/lemo.png" onmouseover="img_over(this,0)" onmouseout="img_over(this,1)"
						onclick="img_clk(this)" Link="" title_txt="" /><span></span></h1>
				<h1 class="img9" id="img9" onclick="img_clk2(this)"><img src="images/main/lemo.png" onmouseover="img_over(this,0)" onmouseout="img_over(this,1)"
						onclick="img_clk(this)" Link="" title_txt="" /><span></span></h1>
				<h1 class="img10" id="img10" onclick="img_clk2(this)"><img src="images/main/lemo.png" onmouseover="img_over(this,0)" onmouseout="img_over(this,1)"
						onclick="img_clk(this)" Link="" title_txt="" /><span></span></h1>
				<h1 class="img11" id="img11" onclick="img_clk2(this)"><img src="images/main/lemo.png" onmouseover="img_over(this,0)" onmouseout="img_over(this,1)"
						onclick="img_clk(this)" Link="" title_txt="" /><span></span></h1>
				<h1 class="img12" id="img12" onclick="img_clk2(this)"><img src="images/main/lemo.png" onmouseover="img_over(this,0)" onmouseout="img_over(this,1)"
						onclick="img_clk(this)" Link="" title_txt="" /><span></span></h1>
				<h1 class="img13" id="img13" onclick="img_clk2(this)"><img src="images/main/lemo.png" onmouseover="img_over(this,0)" onmouseout="img_over(this,1)"
						onclick="img_clk(this)" Link="" title_txt="" /><span></span></h1>
				<h1 class="img14" id="img14" onclick="img_clk2(this)"><img src="images/main/lemo.png" onmouseover="img_over(this,0)" onmouseout="img_over(this,1)"
						onclick="img_clk(this)" Link="" title_txt="" /><span></span></h1>
				<h1 class="img15" id="img15" onclick="img_clk2(this)"><img src="images/main/lemo.png" onmouseover="img_over(this,0)" onmouseout="img_over(this,1)"
						onclick="img_clk(this)" Link="" title_txt="" /><span></span></h1>
				<h1 class="img16" id="img16" onclick="img_clk2(this)"><img src="images/main/lemo.png" onmouseover="img_over(this,0)" onmouseout="img_over(this,1)"
						onclick="img_clk(this)" Link="" title_txt="" /><span></span></h1>
				<h1 class="img17" id="img17" onclick="img_clk2(this)"><img src="images/main/lemo.png" onmouseover="img_over(this,0)" onmouseout="img_over(this,1)"
						onclick="img_clk(this)" Link="" title_txt="" /><span></span></h1>
				<h1 class="img18" id="img18" onclick="img_clk2(this)"><img src="images/main/lemo.png" onmouseover="img_over(this,0)" onmouseout="img_over(this,1)"
						onclick="img_clk(this)" Link="" title_txt="" /><span></span></h1>
				</div>
				<div class="img_left" id="img_left" onclick="pic_left()" title="上一页" onmouseover="left_change(this,0)"
					onmouseout="left_change(this,1)"></div>
				<div class="img_right" id="img_right" onclick="pic_right()" title="下一页" onmouseover="right_change(this,0)"
					onmouseout="right_change(this,1)"></div>
				<div class="footer_btn6" onclick="list_change(this,0)" id="footer_btn6"></div>
				<div class="footer_btn7" onclick="list_change(this,1)" id="footer_btn7"></div>
				<div class="footer_btn8" onclick="list_change(this,2)" id="footer_btn8"></div>
				<div class="footer_btn9" onclick="list_change(this,3)" id="footer_btn9"></div>
				<div class="footer_btn10" onclick="list_change(this,4)" id="footer_btn10"></div>
			<div class="footer" id="footer">
				<div class="footer1" id="footer1">
					<div class="footer_btn" id="footer_btn">
						<div class="footer_btn1" id="footer_btn1"></div>
						<div class="footer_btn2"  onmouseover="onchange_bg(event)" onmouseout="outchange_bg(event)"
							title="帮助" id="help" onclick="open_main2(this, this.id)"></div>
						<div class="footer_btn0"  onmouseover="onchange_bg(event)" onmouseout="outchange_bg(event)"
							title="退出" id="edit"  onclick="edit()"></div>
						<div class="footer_btn3"  onmouseover="onchange_bg(event)" onmouseout="outchange_bg(event)"
							title="讨论" id="Discuss" onclick="open_main2(this, this.id)"></div>
						<div class="footer_btn4" onmouseover="onchange_bg(event)" onmouseout="outchange_bg(event)"
							title="设置" id="UserInfo" onclick="openPwdDiv()"></div>
						<div class="footer_btn5" onclick="playWin('open')" title="显示消息窗口" id="footer_btn5"></div>
						<div class="footer_btn5_user"  id="footer_btn5_user"></div>
					</div>
				</div>
				<div class="footer_user" id="footer_user"><span>当前用户：</span><span>${username}</span></div>
				<div class="footer2" id="footer2"></div>
				<div class="footer3" id="footer3"></div>
			</div>
			<div class="news" id="news">
				<div class="close_news" id="close_news" onmouseover="close_news_on(this, 0)" onmouseout="close_news_on(this, 1)"
					title="隐藏" onclick="close_news()"></div>
				<div class="news_top1" id="news_top1">
					<div class="news_top1_left" id="news_top1_left"></div>
					<div class="news_top1_main" id="news_top1_main">
						<div class="news_top1_main_top" id="news_top1_main_top"></div>
						<div class="news_top1_main_bottom" id="news_top1_main_bottom"></div>
					</div>
					<div class="news_top1_right" id="news_top1_right"></div>
				</div>
				<div class="news_main1" id="news_main1">
					<div class="news_main1_left" id="news_main1_left"></div>
					<div class="news_main1_main" id="news_main1_main">
						<iframe src="" frameborder="0" scrolling="no" id="message_iframe"></iframe>
					</div>
					<div class="news_main1_right" id="news_main1_right"></div>
				</div>
			</div>
			<div class="btn_news" id="btn_news">
				<div class="btn_news_top" id="btn_news_top"></div>
				<div class="btn_news_main" id="btn_news_main" type="">	
					<div onmouseout="news_out2(this)" onmouseover="news_on2(this)" class="open_window" id="open_window">打开窗口></div>
				</div>
				<div class="btn_news_bottom" id="btn_news_bottom"></div>
			</div>
			<a href="javascript:void(0)" class="btn_div" id="btn_div" onmouseover="openchange_on()" onmouseout="openchange_out()" onclick="openchange_click()" onblur="memo_dis()" hidefocus></a>
			<div class="btn_news_main3" id="btn_news_main3">
				<div class="btn_news_main3_top" id="btn_news_main3_top"></div>
				<div class="btn_news_main3_main" id="btn_news_main3_main">
					<ul>
					</ul>
				</div>
				<div class="btn_news_main3_bottom" id="btn_news_main3_bottom"></div>
			</div>
			<div class="btn_news_main2" id="btn_news_main2">
				<div class="btn_news_main2_top" id="btn_news_main2_top"></div>
				<div class="btn_news_main2_main" id="btn_news_main2_main">
					<ul id="btn_news_main2_load">
						<li>
							<a href="javascript:void(0);">请等待</a></li>
					</ul>
				</div>
				<div class="btn_news_main2_bottom" id="btn_news_main2_bottom"></div>
			</div>
			<span class="cms_txt" id="cms_txt">
				<img src="images/main/register.png" /></span>
		</div>
		<table class="bg_03"  id="bg_03" cellspacing="0" onclick="memo_dis()" ID="Table1">
			<tr>
				<td class="table_top" id="table_top">
				<div class="bg_main" id="bg_main">
				<span class="table_top_span" id="table_top_span">
				<span  class="span1" onmouseover="span_over(1, this)" onmouseout="span_out(1, this)" title="左移" id="span1" onclick="top_left()"></span>
				<span  class="span2" onmouseover="span_over(2, this)" onmouseout="span_out(2, this)" title="右移" id="span2" onclick="top_right()"></span>
				<span class="span3" onmouseover="span_over(3, this)" onmouseout="span_out(3, this)" title="最小化" onclick="open_main1()"></span>
				</span>
				<div id="table_top_div1" class="table_top_div1">
				<div id="table_top_div" class="table_top_div">
					<div class="label_01" onclick="tab_change(this)" file="">
						<p class="lableleft_on"></p>
						<p class="lablmidle_on"></p>
						<p class="lablright_on">
							<span class="closs_01" onmouseover="close_over(this,0)" onmouseout="close_over(this,1)"
								onmousedown="close_clik(this)"></span>
						</p>
					</div>
				</div>
				</div>
				</div>
				</td>
			</tr>
			<tr class="table_body" id="table_body">
				<td>
					<div class="table_body1" id="table_body1"></div>
					<div class="out_push"  id="out_push" onmousedown="begin()" onMouseup="end()"></div>
					<div class="out_main" id="out_main">
						<div class="menu_lib"  id="menu_lib" onclick="lib_hidden()" title="隐藏"></div>
						<iframe class="menu_lib_iframe" frameborder="0" id="menu_lib_iframe"></iframe>
						<div class="out" id="out" onMouseup="end()">
						</div>
					</div>
				</td>
			</tr>
		</table>
		<div class="bg_01" id="bg_01">
		</div>
		<div class="bg_02" id="bg_02">
			<img src="images/main_bg.jpg" alt="" />
		</div>
		<div id="sw_display" class="sw_display"><iframe ></iframe></div>
	</body>
</html>

<div id='companyWin' class='easyui-window' collapsible='false'
	minimizable='false' maximizable='true'
	style='padding: 0px; background: #fafafa; overflow: hidden; display: none;'
	closed='true'>
	<iframe id='companyIframe' name='companyIframe'
		style='width: 100%; height: 100%;' frameborder='0'></iframe>
</div>