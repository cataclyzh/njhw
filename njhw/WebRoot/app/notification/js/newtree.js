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
