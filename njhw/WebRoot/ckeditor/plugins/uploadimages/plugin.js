(function(){
    //Section 1 : 按下自定义按钮时执行的代码
    var a= {
        exec:function(editor){
          //location.href="/CKeditorDEMO/batchupload.jsp";
            window.open ("/CKeditorDEMO/batchupload.jsp", "newwindow", 
        	  "height=330, top=200, left=190,width=650, toolbar =no, menubar=no," +
        	  " scrollbars=yes, resizable=no, location=no, status=no")
        	 
        /*
        window.open( //弹出新窗口的命令
　　		"/CKeditorDEMO/batchupload.jsp",//弹出窗口的文件名
　		"newwindow", //弹出窗口的名字（不是文件名），非必须，可用空''代替
　		"height=180",  //窗口高度
　　		"width=300", //窗口宽度
　　		"top=300", //窗口距离屏幕上方的象素值
　　		"left=100", //窗口距离屏幕左侧的象素值
　　		"toolbar=no", //是否显示工具栏，yes为显示
　　		"menubar=no,scrollbars=yes", //表示菜单栏和滚动栏。
　　		"resizable=no", //是否允许改变窗口大小，yes为允许
　　		"location=no", //是否显示地址栏，yes为允许
　　		"status=no") //是否显示状态栏内的信息（通常是文件已经打开），yes为允许
  		 **/
        }
    },
    //Section 2 : 创建自定义按钮、绑定方法
    b='uploadimages';
    CKEDITOR.plugins.add(b,{
        init:function(editor){
            editor.addCommand(b,a);
            editor.ui.addButton('uploadimages',{
                label:'批量上传',
                icon: this.path + 'pic_08.gif',
                command:b
            });
        }
    });
})();