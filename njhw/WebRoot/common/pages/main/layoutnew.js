function addTabs(title, href,center,mainCenter){
	if(center==null){
		center = '#center-div';
	}
   	if($.browser.msie&&parseInt($.browser.version)<=6){
		var content = '<iframe scrolling="auto" frameborder="0"  src="'+href+'" style="width:100%;height:100%;"></iframe>'
   		$(center).html(content);
   	}else{
	   	var tt = $('#'+mainCenter);
   	}
   	if (tt.tabs('exists', title)){
   		tt.tabs('select', title);
   	}else{
	   	if($('#'+center+' .tabs-inner span').length/2>7){
	   		easyAlert('提示信息','最多能打开8个标签页！','info');
		}else{
	    	if (href){
		    	var content = '<iframe scrolling="auto" frameborder="0"  src="'+href+'" style="width:100%;height:100%;"></iframe>';
	    	} else {
		    	var content = '未实现';
	    	}
	    	tt.tabs('add',{
		    	title:title,
		    	closable:true,
		    	content:content
			});
		}
   	}
   	tabClose();
    tabCloseEven();
}
function tick(){
	var today = new Date();
	$("#systime").html($.showLocale(today));
	window.setTimeout("tick()", 1000);
}

function easyAlert(title,msg,type,fn){
		$.messager.alert(title,msg,type,fn);
}
function easyConfirm(title,msg,fn){
		$.messager.confirm(title,msg,fn);	
}
function closeEasyWin(winId){
	 	$("#"+winId).window('close');
}
function tabClose(mainCenter){
    /*双击关闭TAB选项卡*/
    $(".tabs-inner").dblclick(function(){
        var subtitle = $(this).children("span").text();
        if(subtitle!='首页'){
        	$('#'+mainCenter).tabs('close',subtitle);
        }
    })
    $(".tabs-inner").bind('contextmenu',function(e){
        $('#mm').menu('show', {
            left: e.pageX,
            top: e.pageY
        });
        var subtitle =$(this).children("span").text();
        $('#mm').data("currtab",subtitle);
        return false;
    });
}
//绑定右键菜单事件
function tabCloseEven(mainCenter){
    //关闭当前
    $('#mm-tabclose').click(function(){
        var currtab_title = $('#mm').data("currtab");
        if(currtab_title!='首页'){
        	$('#'+mainCenter).tabs('close',currtab_title);
        }
    })
    //全部关闭
    $('#mm-tabcloseall').click(function(){
        $('.tabs-inner span').each(function(i,n){
            var t = $(n).text();
            if(t!='首页'){
            	$('#'+mainCenter).tabs('close',t);
            }
        });    
    });
    //关闭除当前之外的TAB
    $('#mm-tabcloseother').click(function(){
        var currtab_title = $('#mm').data("currtab");
        $('.tabs-inner span').each(function(i,n){
            var t = $(n).text();
            if(t!=currtab_title&&t!='首页')
                $('#'+mainCenter).tabs('close',t);
        });    
    });
    //关闭当前右侧的TAB
    $('#mm-tabcloseright').click(function(){
        var nextall = $('.tabs-selected').nextAll();
        if(nextall.length==0){
            return false;
        }
        nextall.each(function(i,n){
            var t=$('a:eq(0) span',$(n)).text();
            if(t!='首页'){
            	$('#'+mainCenter).tabs('close',t);
            }
        });
        return false;
    });
    //关闭当前左侧的TAB
    $('#mm-tabcloseleft').click(function(){
        var prevall = $('.tabs-selected').prevAll();
        if(prevall.length==0){
            return false;
        }
        prevall.each(function(i,n){
            var t=$('a:eq(0) span',$(n)).text();
             if(t!='首页'){
            	 $('#'+mainCenter).tabs('close',t);
             }
        });
        return false;
    });
}
function openEditDiv(){
	var addUrl = _ctx+"/common/userOrgMgr/init.act";
	openEasyWin("winId","修改用户信息",addUrl,"650","350");
}
function openPwdDiv(){
	var addUrl = _ctx+"/common/userOrgMgr/initpwd.act";
	openEasyWin("winId","修改密码",addUrl,"650","350");
}
function getMsgCount() {
	addTab("收件箱", _ctx+"/common/bulletinMessage/msgBoxAction_queryReceiverbox.act");
}
function getBltnCount() {
	addTab("公告板", _ctx+"/common/bulletinMessage/msgBoardAction_query.act");
}
