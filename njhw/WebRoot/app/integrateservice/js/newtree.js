var fn3;
var mobilenum = "", pname="", info = "", type="";
	
// 树对象
function treeview(id, path)
{
    // 防止出现this指向全局变量的情况
    var that = this;
    // 当前树的id
    this.id = id;
    // 当前树的图片路径
    this.path = path;// 需要获取路径
    // 当前树的dom对象
    this.dom = (function ()
    {
        var element =  document.createElement("div");
        element.id = that.id;
        return element;
    })();
}
// 插入到指定div
treeview.prototype.create = function(parentID)
{
    var parentDom = document.getElementById(parentID);
    this.dom.style.width = parentDom.offsetWidth + "px";
    parentDom.appendChild(this.dom);
}
treeview.prototype.getPath = function(){return this.path}
// 插入dom节点
treeview.prototype.add = function(node)
{
	if(this.dom.style.width.replace("px","") == "0")
		node.dom.style.width = 500 - 20 + "px";
	else
		node.dom.style.width = this.dom.style.width.replace("px","") - 20 + "px";
		
    this.dom.appendChild(node.dom);
    node.dom.style.display = "block";
}
// 节点对象
function node(id, text, icon, hasChild, title)
{
    // 防止出现this指向全局变量的情况
    var that = this;
    // 当前节点id
    this.id = id;
    // 当前节点文本
    this.text = text;
    // 当前节点的图标
    this.icon = icon;
    //this.path =
    // 子节点对象集合
    this.childNode = [];
    // 是否有子节点
    this.hasChild = hasChild ;
    // 提示内容
    this.title = title ? title : this.text;
    // 当前树的dom对象
    this.dom = (function ()
    {
        var element =  document.createElement("div");
        element.className = "treeNode";
        element.id = that.id;
        // 折叠图片
        var foldElement = document.createElement("div");
        // 判断是否有子节点
        if(that.hasChild == "true")
        {
            foldElement.className = "empty";
        }
        else
        {
            foldElement.className = "unfold";
            foldElement.onclick = toogle;
        }

        element.appendChild(foldElement);
        // 节点文本
        var textElement = document.createElement("p");
        textElement.innerHTML = that.text;
        // title显示内容
        textElement.title = that.title;
        textElement.style.cursor = "pointer";
        // 增加双击事件，用于展开节点
        textElement.ondblclick = nodedbclick;
        element.appendChild(textElement);
        // 下划线节点
        var solidElement =  document.createElement("div");
        solidElement.className = "solidDiv";
        element.appendChild(solidElement);
        return element;
        // 折叠按钮事件
        function toogle()
        {
            if(this.className == "unfold")
            {
                this.className = "fold";
                that.expand();
            }
            else
            {
                this.className = "unfold";
                that.shrink();
            }
        }
        // 节点双击
        function nodedbclick()
        {
        	// 没有子节点不允许点击展开
        	if(that.dom.firstChild.className == "empty")
        		return false;
        	else if(that.dom.firstChild.className == "unfold")
            {
                that.expand();// 双击展开节点
            }
            else
            {
                that.shrink();// 双击闭合节点
            }

        }
    })();
}
// 继承treeview节点的添加节点事件（暂先不使用），由于treeview加载时先显示第一节点
node.prototype.add = function(node)
{
    // 将该节点添加到父节点中
    this.childNode.push(node);
    this.dom.appendChild(node.dom);
}
node.prototype.constructor = node;
// 删除该节点
node.prototype.removeNode = function(){
    var nodeDom = this.dom;
    nodeDom.parentNode.removeChild(nodeDom);
}
// 子节点dom集合
node.prototype.getChild = function(){
    var arr = [];// 子节点数组对象
    var childDom = this.dom.getElementsByTagName("div");
    var len = childDom.length;
    for(var i = 0; i < len; i++)
    {
        if(childDom[i].className == "treeNode" && childDom[i].parentNode == this.dom)
        {
            arr.push(childDom[i]);// 追加到子节点数组对象中
        }
        else
            continue;
    }
    return arr;
}
// 展开节点
node.prototype.expand = function()
{
    this.dom.firstChild.className = "fold";
    if(typeof(eval('callback'))=="function") 
        callback(this);
    // 当前节点下div对象集合
    var childDom = this.dom.getElementsByTagName("div");
    var len = childDom.length;
    for(var i = 0; i < len; i++)
    {
        if(childDom[i].className == "treeNode" && childDom[i].parentNode == this.dom)
        {
            childDom[i].style.display = "block";
            // offsetWidth在display状态为none时，是不能获取的，必须在展开时获取
            childDom[i].style.width = this.dom.offsetWidth - 20 + "px";
        }
        else
            continue;
    }
}
// 收缩节点
node.prototype.shrink = function()
{
    this.dom.firstChild.className = "unfold";
    // 当前节点下div对象集合
    var childDom = this.dom.getElementsByTagName("div");
    var len = childDom.length;
    for(var i = 0; i < len; i++)
    {
        if(childDom[i].className == "treeNode" && childDom[i].parentNode == this.dom)
            childDom[i].style.display = "none";
        else
            continue;
    }
}
// 设置该节点没有子节点,则移除展开图标以及单击和双击事件
node.prototype.SetSingle = function()
{
    this.dom.firstChild.className = "empty";
    this.dom.firstChild.onclick = function(){};
    this.dom.getElementsByTagName("p")[0].ondblclick = function(){};
}

function tipsChange(dom, id) {
	//alert(dom.className);
    dom.parentNode.className = 'reportTips_' + dom.className;
    if(dom.className == "tips3") {
        document.getElementById("peopleSet").style.display = "block";
    } else {
        document.getElementById("peopleSet").style.display = "none";
    }
    
    $("#address_list").children("div").hide();
    $("#"+id).show();
    //$("#searchType").val(type);
    $("#showType").val(id);
    
    // 加载数据
    var tv = new treeview("","");
    var url = "", orgId = 0;
    
    if (id == "tree") {						// 大厦
    	$("#treeUnitValue").val("tree");
    	if(document.getElementById("tree").innerHTML == ""){
	        tv.create("tree");
	        url = _ctx+"/app/integrateservice/loadSonInfo.act?t="+Math.random()+"&orgId=";
	        loadtree(url, tv, orgId);
    	}
    } else if (id == "treeUnit") { 		// 部门
    	$("#treeUnitValue").val("treeUnit");
    	if(document.getElementById("treeUnit").innerHTML == ""){
	    	tv.create("treeUnit");
	        url = _ctx+"/app/integrateservice/loadSonInfo.act?t="+Math.random()+"&orgId=";
	        orgId = orgIdAddress;
	        loadtree(url, tv, orgId);
    	}
    } else if (id == "pabList") {			// 个人
    	tv.create("pabList");
    	if($("#pabList").find("p")!=null&&$("#pabList").find("p").size()==0){
	        url = _ctx+"/app/integrateservice/loadAddressInfo.act?t="+Math.random()+"&gId=";
	        loadaddresstree(url, tv, 0);
    	}
    } else if (id == "e4List"){
    	$("#treeUnitValue").val("e4List");
    	if(document.getElementById("e4List").innerHTML == ""){
	    	tv.create("e4List");
	    	var treeUnitType = $("#treeUnitValue").val();
	        url = _ctx+"/app/integrateservice/loadSonInfo.act?treeUnitType="+treeUnitType+"&t="+Math.random()+"&orgId=";
	        //orgId = orgIdAddress;
	        loadtree(url, tv, orgId);
    	}
    }
}

function amplify() {
	var url = _ctx+"/app/integrateservice/amplify.act?t="+Math.random();	//?showType="+$("#showType").val();
	parent.open_main3('10260' ,'通讯录' ,url);
}
	
// 加载大厦/部门树节点
function loadtree(url, tv, orgId) {
	
	var firsturl = "";
	if (orgId >= 0) firsturl = url + orgId;
	else firsturl = url;
	
    $.getJSON(firsturl, function(json){
        var len = json.sonOrgList.length;
        for(var i = 0; i < len; i++) {
            var id = json.sonOrgList[i].ORG_ID;
            var text = json.sonOrgList[i].NAME.replace(/(^\s*)|(\s*$)/g, "");
            text = addTelFax(json, i, text);
            var node1 = new node(id, text, "");
            var loadnode = new node("", "请等待...", "", "true");	// 不能展开
            node1.add(loadnode);
            tv.add(node1);
        }
        
        // 加载用户
        var perlen = json.sonPersonList.length;
        for(var i = 0; i < perlen; i++) {
            var id = json.sonPersonList[i].USERID;
            var name = json.sonPersonList[i].DISPLAY_NAME ? json.sonPersonList[i].DISPLAY_NAME.replace(/(^\s*)|(\s*$)/g, "") : "";
            var mobile = json.sonPersonList[i].UEP_MOBILE ? json.sonPersonList[i].UEP_MOBILE.replace(/(^\s*)|(\s*$)/g, "") : "";
            var tel = json.sonPersonList[i].TEL ? json.sonPersonList[i].TEL.replace(/(^\s*)|(\s*$)/g, "") : "";
            var fax = json.sonPersonList[i].TEL_NUM ? json.sonPersonList[i].TEL_NUM.replace(/(^\s*)|(\s*$)/g, "") : ""; //UEP_FAX 
            var web_fax = json.sonPersonList[i].WEB_FAX ? json.sonPersonList[i].WEB_FAX.replace(/(^\s*)|(\s*$)/g, "") : "";
            var unitInfo = id+"_"+name+"_"+mobile;

            var text = "<table width='100%'><tr><td width='20%'><span>"+name+"</span></td><td width='80%' style='color: #7d7d7d;'>", title=name;
            
            
            if (typeAddress == "tel") {
	            if ($("#showType").val() == "treeUnit") {
            		if (mobile != "") {
		           		text = text + "<div><span href='javascript:void(0);' info="+unitInfo+"  phoneNum="+mobile+"  pname="+name+" type='unit' onclick='dialing(&quot;"+mobile+"&quot;, &quot;"+name+"&quot;)'>"+mobile+"</span>(手机)</div>";
						title = title + "　手机:" + mobile;
		            }
            	}
	            if (tel != "") {
	           		text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+tel+"&quot;, &quot;"+name+"&quot;)'>"+tel+"</span>(电话)</div>";
					title = title + "　电话:" + tel;
	            }
	            if (fax != "") {
	           		text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+fax+"&quot;, &quot;"+name+"&quot;)'>"+fax+"</span>(传真)</div>";
					title = title + "　传真:" + fax;
	            }
	            if (web_fax != "") {
	           		text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+web_fax+"&quot;, &quot;"+name+"&quot;)'>"+web_fax+"</span>(传真)</div>";
					title = title + "　网络传真:" + web_fax;
	            }

            } else if (typeAddress == "fax") {
            	if (fax != "") {
	           		text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);' style='text-decoration: none' onclick='sendFax(&quot;"+name+"&quot;,&quot;"+tel+"&quot;,&quot;"+fax+"&quot;)'>"+fax+"</span>(传真)</div>";
	           		title = title + "　传真:" + fax;
	            }
	            if (web_fax != "") {
	           		text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);' style='text-decoration: none' onclick='sendFax(&quot;"+web_fax+"&quot;,&quot;"+tel+"&quot;,&quot;"+web_fax+"&quot;)'>"+web_fax+"</span>(传真)</div>";
	           		title = title + "　网络传真:" + fax;
	            }
            }else{
            	if (fax != "") {
	           		text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);' style='text-decoration: none' onclick='sendFax(&quot;"+name+"&quot;,&quot;"+tel+"&quot;,&quot;"+1+"&quot;)'>"+1+"</span>(传真)</div>";
	           		title = title + "　传真:" + fax;
	            }
	            if (web_fax != "") {
	           		text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);' style='text-decoration: none' onclick='sendFax(&quot;"+1+"&quot;,&quot;"+tel+"&quot;,&quot;"+1+"&quot;)'>"+1+"</span>(传真)</div>";
	           		title = title + "　网络传真:" + fax;
	            }
	            if (tel != "") {
	           		text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+tel+"&quot;, &quot;"+name+"&quot;)'>"+tel+"</span>(电话)</div>";
					title = title + "　电话:" + tel;
	            }
            }
            text = text + "</td></tr></table>";
            
            var node1 = new node(id, text, "", "true", title);
            var loadnode = new node("", "请等待...", "", "true");	// 不能展开
            node1.add(loadnode);
            tv.add(node1);
        }
    });
}

function addTelFax(json, i, text){
	var tel = json.sonOrgList[i].TEL_ID;
    if(tel){
    	text = text + " 电话(" + tel + ")";	
    }
    var fax = json.sonOrgList[i].TEL_FAXID;
    if(fax){
    	text = text + " 传真(" + fax + ")";
    }
    
    return text;
}

// 加载大厦/部门节点信息
function loadnode(url, Pnode) {
 	var firsturl = url + Pnode.id;
    $.getJSON(firsturl, function(json){
        // 加载组织
        var orglen = json.sonOrgList.length;
        for(var i = 0; i < orglen; i++) {
            var id = json.sonOrgList[i].ORG_ID;
            var text = json.sonOrgList[i].NAME.replace(/(^\s*)|(\s*$)/g, "");
            text = addTelFax(json, i, text);
            var node1 = new node(id, text, "");
            node1.dom.style.display = "block";
            node1.dom.style.width = Pnode.dom.offsetWidth - 20 + "px";
            var loadnode = new node("", "请等待...", "");
            node1.add(loadnode);
            Pnode.add(node1);
        }
        // 加载用户
        var perlen = json.sonPersonList.length;
        for(var i = 0; i < perlen; i++) {
        
            var id = json.sonPersonList[i].USERID;
            var name = json.sonPersonList[i].DISPLAY_NAME ? json.sonPersonList[i].DISPLAY_NAME.replace(/(^\s*)|(\s*$)/g, "") : "";
            var mobile = json.sonPersonList[i].UEP_MOBILE ? json.sonPersonList[i].UEP_MOBILE.replace(/(^\s*)|(\s*$)/g, "") : "";
            var tel = json.sonPersonList[i].TEL ? json.sonPersonList[i].TEL.replace(/(^\s*)|(\s*$)/g, "") : "";
            var fax = json.sonPersonList[i].TEL_NUM ? json.sonPersonList[i].TEL_NUM.replace(/(^\s*)|(\s*$)/g, "") : "";
            var web_fax = json.sonPersonList[i].WEB_FAX ? json.sonPersonList[i].WEB_FAX.replace(/(^\s*)|(\s*$)/g, "") : "";
            var unitInfo = id+"_"+name+"_"+mobile;

            var text = "<table width='100%'><tr><td width='20%'><span>"+name+"</span></td><td width='80%' style='color: #7d7d7d;'>", title=name;
            if (typeAddress == "tel") {
	           if ($("#showType").val() == "treeUnit") {
            		if (mobile != "") {
		           		text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' class='tooltip' info="+unitInfo+"  phoneNum="+mobile+"  pname="+name+" type='unit' style='text-decoration: none' onclick='dialing(&quot;"+mobile+"&quot;, &quot;"+name+"&quot;)'>"+mobile+"</a>(手机)</div>";
            			//text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);' class='tooltip' info="+unitInfo+"  phoneNum="+mobile+"  pname="+name+" type='unit' style='text-decoration: none' >"+mobile+"</span>(手机)</div>";
						title = title + "　手机:" + mobile;
		            }
            	}
	            if (tel != "") {
	           		text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+tel+"&quot;, &quot;"+name+"&quot;)'>"+tel+"</a>(电话)</div>";
	            	//text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);' style='text-decoration: none' >"+tel+"</span>(电话)</div>";
					title = title + "　电话:" + tel;
	            }
	            if (fax != "") {
	           		text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+fax+"&quot;, &quot;"+name+"&quot;)'>"+fax+"</a>(传真)</div>";
	           		//text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);' style='text-decoration: none' >"+fax+"</span>(传真)</div>";
					title = title + "　传真:" + fax;
	            }
	            if (web_fax != "") {
	           		text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+web_fax+"&quot;, &quot;"+name+"&quot;)'>"+web_fax+"</a>(传真)</div>";
	           		//text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);' style='text-decoration: none' >"+web_fax+"</span>(传真)</div>";
					title = title + "　网络传真:" + web_fax;
	            }
            } else if (typeAddress == "fax") {
            	if (fax != "") {
	           		text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='sendFax(&quot;"+name+"&quot;,&quot;"+tel+"&quot;,&quot;"+fax+"&quot;)'>"+fax+"</a>(传真)</div>";
	           		//text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);' style='text-decoration: none' >"+fax+"</span>(传真)</div>";
	           		title = title + "　传真:" + fax;
	            }
	            if (web_fax != "") {
	           		text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='sendFax(&quot;"+web_fax+"&quot;,&quot;"+tel+"&quot;,&quot;"+web_fax+"&quot;)'>"+web_fax+"</a>(传真)</div>";
	           		//text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);' style='text-decoration: none' >"+web_fax+"</span>(传真)</div>";
	           		title = title + "　网络传真:" + fax;
	            }
            }
            text = text + "</td></tr></table>";
            var node1 = new node(id, text, "", "true", title);
            node1.dom.style.display = "block";
            node1.dom.style.width = Pnode.dom.offsetWidth - 20 + "px";
            Pnode.add(node1);
        }
        Pnode.childNode[0].removeNode();
        // 没有子节点，则移除展开图标以及单击和双击事件
        if(orglen == 0 && perlen == 0) {
            Pnode.SetSingle();// 设置该节点没有子节点
        }
    });
}

// 加载个人通讯录树节点
function loadaddresstree(url, tv, gId) {
	var firsturl = "";
	if (gId >= 0) firsturl = url + gId;
	else firsturl = url;
	
    $.getJSON(firsturl, function(json){
        var len = json.groupList.length;
        for(var i = 0; i < len; i++) {
            var id = json.groupList[i].NAG_ID;
            var text = json.groupList[i].NAG_NAME.replace(/(^\s*)|(\s*$)/g, "");
            
            var node1 = new node(id, text, "");
            var loadnode = new node("", "请等待...", "", "true");// 不能展开
            node1.add(loadnode);
            tv.add(node1);
        }
        
        // 加载组内人员
        var orglen = json.personList.length;
        for(var i = 0; i < orglen; i++) {
            var id = json.personList[i].NUA_ID;
            var name = json.personList[i].NUA_NAME.replace(/(^\s*)|(\s*$)/g, "");
            var phone = json.personList[i].NUA_PHONE ? json.personList[i].NUA_PHONE.replace(/(^\s*)|(\s*$)/g, "") : "";
            var telHome = json.personList[i].NUA_TEL1 ? json.personList[i].NUA_TEL1.replace(/(^\s*)|(\s*$)/g, "") : "";
            var telOffice = json.personList[i].NUA_TEL2 ? json.personList[i].NUA_TEL2.replace(/(^\s*)|(\s*$)/g, "") : "";
            var telOther = json.personList[i].NUA_TEL3 ? json.personList[i].NUA_TEL3.replace(/(^\s*)|(\s*$)/g, "") : "";
            var addressInfo = "";
            
            var text = "<table width='100%'><tr><td width='20%'><span>"+name+"</span></td><td width='80%' style='color: #7d7d7d;'>", title=name;
            if (phone != "") {		// 多电话循环
            	if (phone.indexOf(',') > 0) {
            		var phoneList = phone.split(',');
            		for ( var j = 0; j <  phoneList.length; j++) {
            			addressInfo = id+"-"+phoneList[j]+"_"+name+"_"+phoneList[j];
						text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);' phoneNum="+phoneList[j]+" info="+addressInfo+"  pname="+name+" type='address' style='text-decoration: none' onclick='dialing(&quot;"+phoneList[j]+"&quot;, &quot;"+name+"&quot;)'>"+phoneList[j]+"</span>(手机)</div>";
						title = title + "　手机:" + phoneList[j];
					}
            	} else {
            		addressInfo = id+"_"+name+"_"+phone;
            		text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);' phoneNum="+phone+" info="+addressInfo+"  pname="+name+" type='address' style='text-decoration: none' onclick='dialing(&quot;"+phone+"&quot;, &quot;"+name+"&quot;)'>"+phone+"</span>(手机)</div>";
					title = title + "　手机:" + phone;
            	}
            }
            if (telHome != "") {
            	if (telHome.indexOf(',') > 0) {
            		var telHomeList = telHome.split(',');
            		for ( var j = 0; j <  telHomeList.length; j++) {
						text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+telHomeList[j]+"&quot;, &quot;"+name+"&quot;)'>"+telHomeList[j]+"</span>(住宅)</div>";
						title = title + "　住宅:" + telHomeList[j];
					}
            	} else {
            		text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+telHome+"&quot;, &quot;"+name+"&quot;)'>"+telHome+"</span>(住宅)</div>";
           			title = title + "　住宅:" + telHome;
            	}
            }
            if (telOffice != "") {
            	if (telOffice.indexOf(',') > 0) {
            		var telOfficeList = telOffice.split(',');
            		for ( var j = 0; j <  telOfficeList.length; j++) {
						text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+telOfficeList[j]+"&quot;, &quot;"+name+"&quot;)'>"+telOfficeList[j]+"</span>(办公)</div>";
						title = title + "　办公:" + telOfficeList[j];
					}
            	} else {
            		text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telOffice+"&quot;, &quot;"+name+"&quot;)'>"+telOffice+"</span>(办公)</div>";
           			title = title + "　办公:" + telOffice;
            	}
            }
            if (telOther != "") {
            	if (telOther.indexOf(',') > 0) {
            		var telOtherList = telOther.split(',');
            		for ( var j = 0; j <  telOtherList.length; j++) {
						text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telOtherList[j]+"&quot;, &quot;"+name+"&quot;)'>"+telOtherList[j]+"</span>(其他)</div>";
						title = title + "　其他:" + telOtherList[j];
					}
            	} else {
            		text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telOther+"&quot;, &quot;"+name+"&quot;)'>"+telOther+"</span>(其他)</div>";
           			title = title + "　其他:" + telOther;
            	}
            }
            text = text + "</td></tr></table>";
            
            var node1 = new node(id, text, "", "true", title);
            var loadnode = new node("", "请等待...", "", "true");	// 不能展开
            node1.add(loadnode);
            tv.add(node1);
        }
    });
}

// 加载个人通讯录节点信息
function loadaddressnode(url, Pnode) {
    var firsturl = url + Pnode.id;
    $.getJSON(firsturl, function(json){
        // 加载组内人员
        var orglen = json.personList.length;
        for(var i = 0; i < orglen; i++) {
            var id = json.personList[i].NUA_ID;
            var name = json.personList[i].NUA_NAME.replace(/(^\s*)|(\s*$)/g, "");
            var phone = json.personList[i].NUA_PHONE ? json.personList[i].NUA_PHONE.replace(/(^\s*)|(\s*$)/g, "") : "";
            var telHome = json.personList[i].NUA_TEL1 ? json.personList[i].NUA_TEL1.replace(/(^\s*)|(\s*$)/g, "") : "";
            var telOffice = json.personList[i].NUA_TEL2 ? json.personList[i].NUA_TEL2.replace(/(^\s*)|(\s*$)/g, "") : "";
            var telOther = json.personList[i].NUA_TEL3 ? json.personList[i].NUA_TEL3.replace(/(^\s*)|(\s*$)/g, "") : "";
            var addressInfo = "";
            var text = "<table width='100%'><tr><td width='20%'><span>"+name+"</span></td><td width='80%' style='color: #7d7d7d;'>", title=name;
            if (phone != "") {		// 多电话循环
            	if (phone.indexOf(',') > 0) {
            		var phoneList = phone.split(',');
            		for ( var j = 0; j <  phoneList.length; j++) {
            			addressInfo = id+"-"+phoneList[j]+"_"+name+"_"+phoneList[j];
						text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0); phoneNum="+phoneList[j]+" info="+addressInfo+"  pname="+name+" type='address' style='text-decoration: none' onclick='dialing(&quot;"+phoneList[j]+"&quot;, &quot;"+name+"&quot;)'>"+phoneList[j]+"</span>(手机)</div>";
						title = title + "　手机:" + phoneList[j];
					}
            	} else {
            		addressInfo = id+"_"+name+"_"+phone;
            		text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);' phoneNum="+phone+" info="+addressInfo+"  pname="+name+" type='address'  style='text-decoration: none' onclick='dialing(&quot;"+phone+"&quot;, &quot;"+name+"&quot;)'>"+phone+"</span>(手机)</div>";
					title = title + "　手机:" + phone;
            	}
            }
            if (telHome != "") {
            	if (telHome.indexOf(',') > 0) {
            		var telHomeList = telHome.split(',');
            		for ( var j = 0; j <  telHomeList.length; j++) {
						text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telHomeList[j]+"&quot;, &quot;"+name+"&quot;)'>"+telHomeList[j]+"</span>(住宅)</div>";
						title = title + "　住宅:" + telHomeList[j];
					}
            	} else {
            		text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telHome+"&quot;, &quot;"+name+"&quot;)'>"+telHome+"</span>(住宅)</div>";
           			title = title + "　住宅:" + telHome;
            	}
            }
            if (telOffice != "") {
            	if (telOffice.indexOf(',') > 0) {
            		var telOfficeList = telOffice.split(',');
            		for ( var j = 0; j <  telOfficeList.length; j++) {
						text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telOfficeList[j]+"&quot;, &quot;"+name+"&quot;)'>"+telOfficeList[j]+"</span>(办公)</div>";
						title = title + "　办公:" + telOfficeList[j];
					}
            	} else {
            		text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telOffice+"&quot;, &quot;"+name+"&quot;)'>"+telOffice+"</span>(办公)</div>";
           			title = title + "　办公:" + telOffice;
            	}
            }
            if (telOther != "") {
            	if (telOther.indexOf(',') > 0) {
            		var telOtherList = telOther.split(',');
            		for ( var j = 0; j <  telOtherList.length; j++) {
						text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telOtherList[j]+"&quot;, &quot;"+name+"&quot;)'>"+telOtherList[j]+"</span>(其他)</div>";
						title = title + "　其他:" + telOtherList[j];
					}
            	} else {
            		text = text + "<div style='float:left; width:125px;'><span href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telOther+"&quot;, &quot;"+name+"&quot;)'>"+telOther+"</span>(其他)</div>";
           			title = title + "　其他:" + telOther;
            	}
            }
            text = text + "</td></tr></table>";
            
            var node1 = new node(id, text, "", "true", title);
            node1.dom.style.display = "block";
            node1.dom.style.width = Pnode.dom.offsetWidth - 20 + "px";
            var loadnode = new node("", "请等待...", "", "true");
            node1.add(loadnode);
            Pnode.add(node1);
        }
        Pnode.childNode[0].removeNode();
        // 没有子节点，则移除展开图标以及单击和双击事件
        if(orglen == 0) {
            Pnode.SetSingle();// 设置该节点没有子节点
        }
    });
}

function dialing(called, pname) {
	var url = _ctx+"/app/integrateservice/inputCallPhone.act?selfMac="+selfMacAddress+"&selfTel="+selfTelAddress+"&called="+called+"&pname="+pname;
	//openEasyWinNotResizable("winId","拨号确认",url,"400","170",true);		
	showShelter('400','170',url);
}

function sendMsg(info, name, type) {
	showShelter('350','600','${ctx}/app/messagingplatform/messagingPlatformInit.act?mobile='+called+"&name="+name);
	var url = _ctx+"/app/sendsms/inputSMS.act?info="+info+"&name="+name+"&type="+type;
	openEasyWin("winId","发送短信",url,"650","400",true);
}

function callback(node) {
    // 子节点对象集合
    if(node.childNode.length == 1 && node.childNode[0].text == "请等待...") {
    	if(document.getElementById("pabList").style.display != "none") {
		    var url = _ctx+"/app/integrateservice/loadAddressInfo.act?t="+Math.random()+"&gId=";
		    loadaddressnode(url, node);
	    } else {
	    	var treeUnitType = $("#treeUnitValue").val();
	    	var url = _ctx+"/app/integrateservice/loadSonInfo.act?treeUnitType="+treeUnitType+"&t="+Math.random()+"&orgId=";
		    loadnode(url, node);
	    }
    }
    //setTimeout("initBindTag()", 2000);
}

$(document).keyup(function(event){
	  if(event.keyCode ==13){
	    $("#search").trigger("click");
	  }
});

	function searchInfo(){
		var showType = $("#showType").val();
		
		var tjVal = $("#searchTJ").val().replace(/(^\s*)|(\s*$)/g, "");
		if (tjVal == "" || tjVal == "请输入姓名或电话") {
			return;
		} else {
			$("#address_list").children("div").hide();
	    	$("#searchList").show();
	    	$("#close_search").show();
			$("#searchList").children().remove();  			
			var url = "", orgId = 0;
			var treeUnitType = $("#treeUnitValue").val();
			
			if (showType == "pabList") {
				url = _ctx+"/app/integrateservice/queryAddress.act?t="+Math.random();	// 个人
			} else if (showType == "treeUnit") {
				url = _ctx+"/app/integrateservice/queryContact.act?treeUnitType="+treeUnitType+"&t="+Math.random();	// 部门
				orgId = orgIdAddress;
			} else if (showType == "tree") {
				url = _ctx+"/app/integrateservice/queryContact.act?treeUnitType="+treeUnitType+"&t="+Math.random();	// 大厦
			} else if (showType == "e4List"){
				url = _ctx+"/app/integrateservice/queryContact.act?treeUnitType="+treeUnitType+"&t="+Math.random();	// E4座
			}
				
	  		$.ajax({
				type: "POST",
				url: url,
				data: {"tjVal": tjVal, "type": showType, "orgId": orgId},
				dataType: 'json',
				async : false,
				success: function(json){
					if(json.length > 0) {
						$("#searchList").append("<table style='margin-left:5px;overflow-x:auto;width:500px;' cellspacing='20' id='contact_tab'></table>");
						if (showType == "pabList") {
							$("#contact_tab").append("<tr><td>姓名</td><td>手机</td><td>办公</td><td>住宅</td></tr>");
						} else if (showType == "treeUnit") {
							$("#contact_tab").append("<tr><td>单位</td><td>姓名</td><td>电话</td><td>传真</td><td>网络传真</td><td>手机</td></tr>");
						} else if (showType == "tree") {
							$("#contact_tab").append("<tr><td>单位</td><td>姓名</td><td>电话</td><td>传真</td><td>网络传真</td></tr>");
						} else if (showType == "e4List"){
							$("#contact_tab").append("<tr><td>单位</td><td>姓名</td><td>电话</td><td>传真</td><td>网络传真</td></tr>");
						}
						
						var name, num1, num2, num3, num2web, _info;
						$.each(json, function(i) {
							var text1 = "", text2 = "", text3 = "", text4 = "";
							if (showType == "pabList") {
								name = json[i].NUA_NAME ? json[i].NUA_NAME.replace(/(^\s*)|(\s*$)/g, "") : "";
								
				            	num1 = json[i].NUA_PHONE ? json[i].NUA_PHONE.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	if (num1.indexOf(',') > 0)	text1 = num1.substring(0,11)+"...";
				            	else {
				            		_info = json[i].NUA_ID+"_"+name+"_"+num1;
				            		text1 = "<span href='javascript:void(0);'  phoneNum="+num1+" info="+_info+" type='address'  pname="+name+" style='text-decoration: none' onclick='dialing(&quot;"+num1+"&quot;, &quot;"+name+"&quot;)'>"+num1+"</span>";
				            	}
				            	num2 = json[i].NUA_TEL2 ? json[i].NUA_TEL2.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	if (num2.indexOf(',') > 0)	text2 = num2.substring(0,11)+"...";
				            	else text2 = "<span href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+num2+"&quot;, &quot;"+name+"&quot;)'>"+num2+"</span>";
				            	
				            	num3 = json[i].NUA_TEL1 ? json[i].NUA_TEL1.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	if (num3.indexOf(',') > 0)	text3 = num3.substring(0,11)+"...";
				            	else text3 = "<span href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+num3+"&quot;, &quot;"+name+"&quot;)'>"+num3+"</span>";
				            	
				            	//num4 = json[i].NUA_TEL3 ? json[i].NUA_TEL3.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	//if (num4.indexOf(',') > 0)	num4 = num4.substring(0,11)+"...";
				            	
				            	$("#contact_tab").append("<tr><td>"+name+"</td><td><span  title='"+num1+"'>"+text1+"</span></td><td><span title='"+num2+"'>"+text2+"</span></td><td><span title='"+num3+"'>"+text3+"</span></td></tr>");
							} else if (showType == "treeUnit") { 
								unitname = json[i].DEPARTMENT_NAME ? json[i].DEPARTMENT_NAME.replace(/(^\s*)|(\s*$)/g, "") : "";	// 本单位下的部门名称
								name = json[i].DISPLAY_NAME ? json[i].DISPLAY_NAME.replace(/(^\s*)|(\s*$)/g, "") : "";
				           	 	num1 = json[i].TEL ? json[i].TEL.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	num2 = json[i].TEL_NUM ? json[i].TEL_NUM.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	num2web = json[i].WEB_FAX ? json[i].WEB_FAX.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	num3 = json[i].UEP_MOBILE ? json[i].UEP_MOBILE.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	_info = json[i].NUA_ID+"_"+name+"_"+num3;
				            	
				            	text1 = "<span href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+num1+"&quot;, &quot;"+name+"&quot;)'>"+num1+"</span>";
				            	text2 = "<span href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+num2+"&quot;, &quot;"+name+"&quot;)'>"+num2+"</span>";
				            	text4 = "<span href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+num2web+"&quot;, &quot;"+name+"&quot;)'>"+num2web+"</span>";
				            	text3 = "<span href='javascript:void(0);'  class='tooltip' phoneNum="+num3+"  info="+_info+" type='unit'  pname="+name+" style='text-decoration: none' onclick='dialing(&quot;"+num3+"&quot;, &quot;"+name+"&quot;)'>"+num3+"</span>";
				            	
				            	$("#contact_tab").append("<tr><td>"+unitname+"</td><td>"+name+"</td><td>"+text1+"</td><td>"+text2+"</td><td>"+text4+"</td><td>"+text3+"</td></tr>");
							} else if (showType == "tree") { 
								unitname = json[i].ORG_NAME ? json[i].ORG_NAME.replace(/(^\s*)|(\s*$)/g, "") : "";		// 一级单位
								name = json[i].DISPLAY_NAME ? json[i].DISPLAY_NAME.replace(/(^\s*)|(\s*$)/g, "") : "";
				           	 	num1 = json[i].TEL ? json[i].TEL.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	num2 = json[i].TEL_NUM ? json[i].TEL_NUM.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	num2web = json[i].WEB_FAX ? json[i].WEB_FAX.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	
				            	text1 = "<span href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+num1+"&quot;, &quot;"+name+"&quot;)'>"+num1+"</span>";
				            	text2 = "<span href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+num2+"&quot;, &quot;"+name+"&quot;)'>"+num2+"</span>";
				            	text4 = "<span href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+num2web+"&quot;, &quot;"+name+"&quot;)'>"+num2web+"</span>";
				            	
				            	$("#contact_tab").append("<tr><td>"+unitname+"</td><td>"+name+"</td><td>"+text1+"</td><td>"+text2+"</td><td>"+text4+"</td></tr>");
							} else if (showType == "e4List"){
								unitname = json[i].ORG_NAME ? json[i].ORG_NAME.replace(/(^\s*)|(\s*$)/g, "") : "";		// 一级单位
								name = json[i].DISPLAY_NAME ? json[i].DISPLAY_NAME.replace(/(^\s*)|(\s*$)/g, "") : "";
				           	 	num1 = json[i].TEL ? json[i].TEL.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	num2 = json[i].TEL_NUM ? json[i].TEL_NUM.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	num2web = json[i].WEB_FAX ? json[i].WEB_FAX.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	
				            	text1 = "<span href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+num1+"&quot;, &quot;"+name+"&quot;)'>"+num1+"</span>";
				            	text2 = "<span href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+num2+"&quot;, &quot;"+name+"&quot;)'>"+num2+"</span>";
				            	text4 = "<span href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+num2web+"&quot;, &quot;"+name+"&quot;)'>"+num2web+"</span>";
				            	
				            	$("#contact_tab").append("<tr><td>"+unitname+"</td><td>"+name+"</td><td>"+text1+"</td><td>"+text2+"</td><td>"+text4+"</td></tr>");
							}
						});
					}
				 },
				 error: function(msg, status, e) {
					 easyAlert("提示信息", "操作出错","info");
				 }
			 });
		 //setTimeout("initBindTag()", 1000);
	 }
	}

// 查询输入框
function inputFun(dom, num) {
    if(num == 0) dom.value = "";
    if(num == 1 && dom.value == "") dom.value = "请输入姓名或电话";
}

// 清除按钮
function clear_search(dom) {
	var st = $("#showType").val();	// 退出显示最后一次显示的标签页
	$("#"+st).show();
    document.getElementById("searchList").style.display = "none";
    document.getElementById("searchTJ").value = "请输入姓名或电话";
    dom.style.display = "none";
}

function refreshAddList() {
	var id = $("#showType").val();
	var tjVal = $("#searchTJ").val().replace(/(^\s*)|(\s*$)/g, "");
	$("#"+id).children().remove();
    var tv = new treeview("","");
    var url = "", orgId = 0;
    if(tjVal != "请输入姓名或电话"){
    	searchInfo();
    }else{
    	$("#searchList").css('display','none');
	    if (id == "tree" ) {				// 大厦
	    	$("#tree").css('display','block');
	        tv.create("tree");
	        url = _ctx+"/app/integrateservice/loadSonInfo.act?t="+Math.random()+"&orgId=";
	        loadtree(url, tv, orgId);
	    } else if (id == "treeUnit") { 		// 部门 
	    	$("#treeUnit").css('display','block');
	    	tv.create("treeUnit");
	        url = _ctx+"/app/integrateservice/loadSonInfo.act?t="+Math.random()+"&orgId=";
	        orgId = orgIdAddress;
	        loadtree(url, tv, orgId);
	    } else if (id == "pabList") {		// 个人
	    	$("#pabList").css('display','block');
	    	tv.create("pabList");
	        url = _ctx+"/app/integrateservice/loadAddressInfo.act?t="+Math.random()+"&gId=";
	        loadaddresstree(url, tv, 0);
	    } else if(id == "e4List"){			//E4座
	    	$("#e4List").css('display','block');
	    	tv.create("e4List");
	        var treeUnitType = $("#treeUnitValue").val();
	        url = _ctx+"/app/integrateservice/loadSonInfo.act?treeUnitType="+treeUnitType+"&t="+Math.random()+"&orgId=";
	        loadtree(url, tv, orgId);
	    }
    }
    //setTimeout("initBindTag()", 1000);
}