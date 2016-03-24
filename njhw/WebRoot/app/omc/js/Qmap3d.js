//2013.2.27更新
//1.增加OnModelInstDyanmicStart事件
//2.增加OnModelInstDyanmicStop事件
//3.增加QM_SetHawkViewPortHeight方法 ，指定场景范围中鹰眼视口的高度。
//4.增加QM_EnableMiFadein方法，设置系统中新加载模型是否使用渐入方式显。



///***********************************************************************************
///File Name: Qmap3d-core-1.1.js
///Version:1.1
///***********************************************************************************


/*
动态的添加object对象
*/
function AC_Generateobj(objAttrs, params) {
    var divid = "";    //div的id
    var str = '<object ';
    for (var i in objAttrs) {
        if (i == 'id') {
            divid = "div_" + objAttrs[i];
        }
        str += i + '="' + objAttrs[i] + '" ';
    }
    str += '>';
    for (var i in params)
        str += '<param name="' + i + '" value="' + params[i] + '" /> ';
    str += '</object>';

    var strdiv = "<div style=\"display:none;height:100%;width:100%;\" id=\"" + divid + "\" >" + str + "</div>"; //在object外面包装一层div
    document.write(strdiv);

}

function AC_AX_RunContent() {
    var ret = AC_AX_GetArgs(arguments);
    AC_Generateobj(ret.objAttrs, ret.params);
}

function AC_AX_GetArgs(args) {
    var ret = new Object();
    ret.params = new Object();
    ret.objAttrs = new Object();
    for (var i = 0; i < args.length; i = i + 2) {
        var currArg = args[i].toLowerCase();

        switch (currArg) {
            case "data":
            case "codebase":
            case "codebaseurl":
            case "classid":
            case "clsid":
            case "id":
            case "onafterupdate":
            case "onbeforeupdate":
            case "onblur":
            case "oncellchange":
            case "onclick":
            case "ondblClick":
            case "ondrag":
            case "ondragend":
            case "ondragenter":
            case "ondragleave":
            case "ondragover":
            case "ondrop":
            case "onfinish":
            case "onfocus":
            case "onhelp":
            case "onmousedown":
            case "onmouseup":
            case "onmouseover":
            case "onmousemove":
            case "onmouseout":
            case "onkeypress":
            case "onkeydown":
            case "onkeyup":
            case "onload":
            case "onlosecapture":
            case "onpropertychange":
            case "onreadystatechange":
            case "onrowsdelete":
            case "onrowenter":
            case "onrowexit":
            case "onrowsinserted":
            case "onstart":
            case "onscroll":
            case "onbeforeeditfocus":
            case "onactivate":
            case "onbeforedeactivate":
            case "ondeactivate":
            case "width":
            case "height":
            case "align":
            case "vspace":
            case "hspace":
            case "class":
            case "title":
            case "accesskey":
            case "name":
            case "tabindex":
            case "type":
            case "selectedmodel":
                ret.objAttrs[args[i]] = args[i + 1];
                break;
            default:
                ret.params[args[i]] = args[i + 1];
        }
    }
    return ret;
}

(function () {
    /**
    * Namespace: Qmap
    * The Qmap object provides a namespace for all things Qmap
    */
    window.Qmap = {
        Version: "1,5,0,9", //版本号
        Browser: (function () { //判断是否是某浏览器
            var ua = navigator.userAgent;
            var isOpera = Object.prototype.toString.call(window.opera) == '[object Opera]';
            return {
                IE: !!window.attachEvent && !isOpera,
                Opera: isOpera,
                WebKit: ua.indexOf('AppleWebKit/') > -1,
                Gecko: ua.indexOf('Gecko') > -1 && ua.indexOf('KHTML') === -1,
                MobileSafari: /Apple.*Mobile.*Safari/.test(ua)
            }
        })(),


        ScriptFragment: '<script[^>]*>([\\S\\s]*?)<\/script>',
        JSONFilter: /^\/\*-secure-([\s\S]*)\*\/\s*$/,

        emptyFunction: function () { },
        K: function (x) { return x },
        arrMap: [] //声明一个地图数组，生成的地图对象都会保存在里面
    };

})();

///
///返回DOM对象
function qmap(element) {
    return document.getElementById(element);
}
///返回DOM对象方法的简写
var $q = qmap;


/**
* Constructor: Qmap.Class
* Base class used to construct all other classes. Includes support for 
*     multiple inheritance. 
*     
* 
* To create a new Qmap-style class, use the following syntax:
* > var MyClass = Qmap.Class(prototype);
*
* To create a new Qmap-style class with multiple inheritance, use the
*     following syntax:
* > var MyClass = Qmap.Class(Class1, Class2, prototype);
* Note that instanceof reflection will only reveil Class1 as superclass.
* Class2 ff are mixins.
*
*/
Qmap.Class = function () {
    var Class = function () {
        /**
        * This following condition can be removed at 3.0 - this is only for
        * backwards compatibility while the Class.inherit method is still
        * in use.  So at 3.0, the following three lines would be replaced with
        * simply:
        * this.initialize.apply(this, arguments);
        */
        if (arguments && arguments[0] != Qmap.Class.isPrototype) {
            this.initialize.apply(this, arguments);
        }
    }; //定义一个class用来实现继承的功能
    var extended = {}; //定义一个被扩展对象
    var parent, initialize, Type; //父类，初始化，类型
    for (var i = 0, len = arguments.length; i < len; ++i) {   //遍历参数
        Type = arguments[i]; //得到具体的参数
        if (typeof Type == "function") {//判断第一个参数是否是函数（对象）
            // make the class passed as the first argument the superclass
            if (i == 0 && len > 1) {  //判断是否是第一个参数，并且总长度超过1
                initialize = Type.prototype.initialize; //得到原型中的initialize方法,并且赋值给参数initialize
                // replace the initialize method with an empty function,
                // because we do not want to create a real instance here 
                Type.prototype.initialize = function () { }; //用空方法替换原始的原型方法，在这里我们不想创建一个真的实例
                // the line below makes sure that the new class has a
                // superclass
                extended = new Type(); //声明一个被扩展类，但是不包括 initialize 方法，因为 initialize 方法来自parent
                // restore the original initialize method //恢复原始的初始化方法
                if (initialize === undefined) //判断初始化方法是否已经定义,不光是比较值，还比较类型
                {
                    delete Type.prototype.initialize; //删除原型的initialize方法
                } else {
                    Type.prototype.initialize = initialize; //恢复为原始的initialize方法
                }
            }
            // get the prototype of the superclass
            parent = Type.prototype; //得到超类的原型，并且作为parent
        } else {
            // in this case we're extending with the prototype 这条语句是把我们正在扩展用到的原型，赋值到parent上面
            parent = Type;
        }
        Qmap.Util.extend(extended, parent);
    }
    Class.prototype = extended;
    return Class;
};

/**
* Property: isPrototype
* *Deprecated*.  This is no longer needed and will be removed at 3.0.
*/
Qmap.Class.isPrototype = function () { };

Qmap.Util = {
};

/**
* APIFunction: extend
* Copy all properties of a source object to a destination object.  Modifies
*     the passed in destination object.  Any properties on the source object
*     that are set to undefined will not be (re)set on the destination object.
*
* Parameters:
* destination - {Object} The object that will be modified
* source - {Object} The object with properties to be set on the destination
*
* Returns:
* {Object} The destination object.
*/
Qmap.Util.extend = function (destination, source) { //实现继承的函数
    destination = destination || {};
    if (source) {
        for (var property in source) {
            var value = source[property];
            if (value !== undefined) {
                destination[property] = value;
            }
        }

        /**
        * IE doesn't include the toString property when iterating over an object's
        * properties with the for(property in object) syntax.  Explicitly check if
        * the source has its own toString property.
        */

        /*
        * FF/Windows < 2.0.0.13 reports "Illegal operation on WrappedNative
        * prototype object" when calling hawOwnProperty if the source object
        * is an instance of window.Event.
        */

        var sourceIsEvt = typeof window.Event == "function"
                          && source instanceof window.Event;

        if (!sourceIsEvt
           && source.hasOwnProperty && source.hasOwnProperty('toString')) {
            destination.toString = source.toString;
        }
    }
    return destination;
};

/*
为object对象绑定上公共事件
*/
Qmap.Util.extend(Object, {
    isArray: function (object) {
        return Object.prototype.toString.call(object) == "[object Array]";
    },

    isFunction: function (object) {
        return typeof object === "function";
    },

    isString: function (object) {
        return Object.prototype.toString.call(object) == "[object String]";
    },

    isNumber: function (object) {
        return Object.prototype.toString.call(object) == "[object Number]";
    },

    isUndefined: function (object) {
        return typeof object === "undefined";
    }

});

var $break = {};

///Enumerable 是作为混入其他集合对象中的module(模块)
var Enumerable = (function () {
    function each(iterator, context) {
        var index = 0;
        try {
            this._each(function (value) {
                iterator.call(context, value, index++);
            });
        } catch (e) {
            if (e != $break) throw e;
        }
        return this;
    }

    function eachSlice(number, iterator, context) {
        var index = -number, slices = [], array = this.toArray();
        if (number < 1) return array;
        while ((index += number) < array.length)
            slices.push(array.slice(index, index + number));
        return slices.collect(iterator, context);
    }

    function all(iterator, context) {
        iterator = iterator || Qmap.K;
        var result = true;
        this.each(function (value, index) {
            result = result && !!iterator.call(context, value, index);
            if (!result) throw $break;
        });
        return result;
    }

    function any(iterator, context) {
        iterator = iterator || Qmap.K;
        var result = false;
        this.each(function (value, index) {
            if (result = !!iterator.call(context, value, index))
                throw $break;
        });
        return result;
    }

    function collect(iterator, context) {
        iterator = iterator || Qmap.K;
        var results = [];
        this.each(function (value, index) {
            results.push(iterator.call(context, value, index));
        });
        return results;
    }

    function detect(iterator, context) {
        var result;
        this.each(function (value, index) {
            if (iterator.call(context, value, index)) {
                result = value;
                throw $break;
            }
        });
        return result;
    }

    function findAll(iterator, context) {
        var results = [];
        this.each(function (value, index) {
            if (iterator.call(context, value, index))
                results.push(value);
        });
        return results;
    }

    function grep(filter, iterator, context) {
        iterator = iterator || Qmap.K;
        var results = [];

        if (Object.isString(filter))
            filter = new RegExp(RegExp.escape(filter));

        this.each(function (value, index) {
            if (filter.match(value))
                results.push(iterator.call(context, value, index));
        });
        return results;
    }

    function include(object) {
        if (Object.isFunction(this.indexOf))
            if (this.indexOf(object) != -1) return true;

        var found = false;
        this.each(function (value) {
            if (value == object) {
                found = true;
                throw $break;
            }
        });
        return found;
    }

    function inGroupsOf(number, fillWith) {
        fillWith = Object.isUndefined(fillWith) ? null : fillWith;
        return this.eachSlice(number, function (slice) {
            while (slice.length < number) slice.push(fillWith);
            return slice;
        });
    }

    function inject(memo, iterator, context) {
        this.each(function (value, index) {
            memo = iterator.call(context, memo, value, index);
        });
        return memo;
    }

    function invoke(method) {
        var args = $A(arguments).slice(1);
        return this.map(function (value) {
            return value[method].apply(value, args);
        });
    }

    function max(iterator, context) {
        iterator = iterator || Qmap.K;
        var result;
        this.each(function (value, index) {
            value = iterator.call(context, value, index);
            if (result == null || value >= result)
                result = value;
        });
        return result;
    }

    function min(iterator, context) {
        iterator = iterator || Qmap.K;
        var result;
        this.each(function (value, index) {
            value = iterator.call(context, value, index);
            if (result == null || value < result)
                result = value;
        });
        return result;
    }

    function partition(iterator, context) {
        iterator = iterator || Qmap.K;
        var trues = [], falses = [];
        this.each(function (value, index) {
            (iterator.call(context, value, index) ?
        trues : falses).push(value);
        });
        return [trues, falses];
    }

    function pluck(property) {
        var results = [];
        this.each(function (value) {
            results.push(value[property]);
        });
        return results;
    }

    function reject(iterator, context) {
        var results = [];
        this.each(function (value, index) {
            if (!iterator.call(context, value, index))
                results.push(value);
        });
        return results;
    }

    function sortBy(iterator, context) {
        return this.map(function (value, index) {
            return {
                value: value,
                criteria: iterator.call(context, value, index)
            };
        }).sort(function (left, right) {
            var a = left.criteria, b = right.criteria;
            return a < b ? -1 : a > b ? 1 : 0;
        }).pluck('value');
    }

    function toArray() {
        return this.map();
    }

    function zip() {
        var iterator = Qmap.K, args = $A(arguments);
        if (Object.isFunction(args.last()))
            iterator = args.pop();

        var collections = [this].concat(args).map($A);
        return this.map(function (value, index) {
            return iterator(collections.pluck(index));
        });
    }

    function size() {
        return this.toArray().length;
    }

    function inspect() {
        return '#<Enumerable:' + this.toArray().inspect() + '>';
    }

    return {
        each: each,
        eachSlice: eachSlice,
        all: all,
        every: all,
        any: any,
        some: any,
        collect: collect,
        map: collect,
        detect: detect,
        findAll: findAll,
        select: findAll,
        filter: findAll,
        grep: grep,
        include: include,
        member: include,
        inGroupsOf: inGroupsOf,
        inject: inject,
        invoke: invoke,
        max: max,
        min: min,
        partition: partition,
        pluck: pluck,
        reject: reject,
        sortBy: sortBy,
        toArray: toArray,
        entries: toArray,
        zip: zip,
        size: size,
        inspect: inspect,
        find: detect
    };
})();

function $A(iterable) {
    if (!iterable) return [];
    if ('toArray' in Object(iterable)) return iterable.toArray();
    var length = iterable.length || 0, results = new Array(length);
    while (length--) results[length] = iterable[length];
    return results;
}





//将类似"rgb(120, 120, 240)"串输出成#7878f0
Qmap.Util.rgbToHTMLColor = function (c) {
    var m = /rgba?\(\s*(\d+)\s*,\s*(\d+)\s*,\s*(\d+)/.exec(c);
    return m ? '#' + (1 << 24 | m[1] << 16 | m[2] << 8 | m[3]).toString(16).substr(1) : c;
};

/*
* @param {string} sColor:"rgb(11,22,33)", "rgb(10%, 30%, 30%)", "#aaa", "#aaaaaa", rgba(12, 34, 56, 1)" 
* @return {integer|false}
*/
Qmap.Util.colorToInt = function (sColor) {

    var sFirst = sColor.charAt(0), i, aFromReg, iR, iG, iB;
    if (!sFirst) return false;
    if (sFirst === '#') {
        if (!!sColor.charAt(6)) {
            sColor = sColor.substr(5, 2) + sColor.substr(3, 2) + sColor.substr(1, 2);
        } else {
            sColor = [sColor.charAt(3), sColor.charAt(3), sColor.charAt(2), sColor.charAt(2), sColor.charAt(1), sColor.charAt(1)].join('');
        }
        i = parseInt(sColor, 16);
    } else {
        /*
        * few other color representations, like "rgb(10%, 30%, 30%)", "rgba(12, 34, 56, 20)"
        */
        aFromReg = /rgba?\s*\(\s*(\d+)(\D)\D*(\d+)\D*(\d+)/i.exec(sColor);
        if (!aFromReg) return false;
        iR = parseInt(aFromReg[1], 10);
        iG = parseInt(aFromReg[3], 10);
        iB = parseInt(aFromReg[4], 10);
        if (aFromReg[2] === '%') {
            iR = Math.round(iR * 2.55);
            iG = Math.round(iG * 2.55);
            iB = Math.round(iB * 2.55);
        }
        i = iB << 16 | iG << 8 | iR;
        // fix gdyby czasem liczby były większe niż 255 podał 16777215 = (1<<24) - 1;
        i &= 16777215;
        aFromReg = iR = iG = iB = null;
    }
    sFirst = sColor = null;
    return i;
};

/*
* @param {integer} integer
* @return {string}
*/
Qmap.Util.intToRGBColor = function (integer) {
    return ['RGB(', integer & 255, ', ', integer >> 8 & 255, ', ', integer >> 16 & 255, ')'].join('');
};

/*
* @param {integer} integer
* @return {string}
*/
Qmap.Util.intToRGBaColor = function (integer) {
    return ['RGB(', integer & 255, ', ', integer >> 8 & 255, ', ', integer >> 16 & 255, ', 1)'].join('');
};

//RGB的值toString(16)可以得到HEX表示
//在HTML中表示和HEX中的表示R和B的位置相反，如HEX：0x0080ff，在HTML中是#FF8000，RGB是RGB(0xff,0x80,0x00)
//得到RGB的值
Qmap.Util.RGB = function () {
    var len = arguments.length;

    if (len == 0)
        return 0;
    else {
        var sumValue = 0;
        for (var i = 0; i < len; i++) {
            var value = arguments[i];
            value = parseInt(value);
            if (!isNaN(value)) {
                switch (i) {
                    case 0: //R
                        sumValue += value;
                        break;
                    case 1: //G
                        sumValue += value * 256;
                        break;
                    case 2: //B
                        sumValue += value * 256 * 256;
                        break;
                    default:
                        break;
                }
            }
        }
        return sumValue;
    }
};


/** 
* Function: Try
* Execute functions until one of them doesn't throw an error. 
*     Capitalized because "try" is a reserved word in JavaScript.
*     Taken directly from OpenLayers.Util.Try()
* 
* Parameters:
* [*] - {Function} Any number of parameters may be passed to Try()
*    It will attempt to execute each of them until one of them 
*    successfully executes. 
*    If none executes successfully, returns null.
* 
* Returns:
* {*} The value returned by the first successfully executed function.
*/
Qmap.Util.Try = function () {
    var returnValue = null;

    for (var i = 0, len = arguments.length; i < len; i++) {
        var lambda = arguments[i];
        try {
            returnValue = lambda();
            break;
        } catch (e) {
        }
    }

    return returnValue;
};



/**
* Namespace: Qmap.Function
* Contains convenience functions for function manipulation.
*/
Qmap.Function = {
    /**
    * APIFunction: bind
    * Bind a function to an object.  Method to easily create closures with
    *     'this' altered.
    * 
    * Parameters:
    * func - {Function} Input function.
    * object - {Object} The object to bind to the input function (as this).
    * 
    * Returns:
    * {Function} A closure with 'this' set to the passed in object.
    */
    bind: function (func, object) {
        // create a reference to all arguments past the second one
        var args = Array.prototype.slice.apply(arguments, [2]);
        return function () {
            // Push on any additional arguments from the actual function call.
            // These will come after those sent to the bind call.
            var newArgs = args.concat(
                Array.prototype.slice.apply(arguments, [0])
            );
            return func.apply(object, newArgs);
        };
    },

    /**
    * APIFunction: bindAsEventListener
    * Bind a function to an object, and configure it to receive the event
    *     object as first parameter when called. 
    * 
    * Parameters:
    * func - {Function} Input function to serve as an event listener.
    * object - {Object} A reference to this.
    * 
    * Returns:
    * {Function}
    */
    bindAsEventListener: function (func, object) {
        return function (event) {
            return func.call(object, event || window.event);
        };
    },

    /**
    * APIFunction: False
    * A simple function to that just does "return false". We use this to 
    * avoid attaching anonymous functions to DOM event handlers, which 
    * causes "issues" on IE<8.
    * 
    * Usage:
    * document.onclick = OpenLayers.Function.False;
    * 
    * Returns:
    * {Boolean}
    */
    False: function () {
        return false;
    },

    /**
    * APIFunction: True
    * A simple function to that just does "return true". We use this to 
    * avoid attaching anonymous functions to DOM event handlers, which 
    * causes "issues" on IE<8.
    * 
    * Usage:
    * document.onclick = OpenLayers.Function.True;
    * 
    * Returns:
    * {Boolean}
    */
    True: function () {
        return true;
    }
};



/**
* Namespace: Qmap.String
* Contains convenience functions for string manipulation.
*/
Qmap.String = {

    /**
    * APIFunction: startsWith
    * Test whether a string starts with another string. 
    * 
    * Parameters:
    * str - {String} The string to test.
    * sub - {Sring} The substring to look for.
    *  
    * Returns:
    * {Boolean} The first string starts with the second.
    */
    startsWith: function (str, sub) {
        return (str.indexOf(sub) == 0);
    },

    /**
    * APIFunction: contains
    * Test whether a string contains another string.
    * 
    * Parameters:
    * str - {String} The string to test.
    * sub - {String} The substring to look for.
    * 
    * Returns:
    * {Boolean} The first string contains the second.
    */
    contains: function (str, sub) {
        return (str.indexOf(sub) != -1);
    },

    /**
    * APIFunction: trim
    * Removes leading and trailing whitespace characters from a string.
    * 
    * Parameters:
    * str - {String} The (potentially) space padded string.  This string is not
    *     modified.
    * 
    * Returns:
    * {String} A trimmed version of the string with all leading and 
    *     trailing spaces removed.
    */
    trim: function (str) {
        return str.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
    },

    /**
    * APIFunction: camelize
    * Camel-case a hyphenated string. 
    *     Ex. "chicken-head" becomes "chickenHead", and
    *     "-chicken-head" becomes "ChickenHead".
    *
    * Parameters:
    * str - {String} The string to be camelized.  The original is not modified.
    * 
    * Returns:
    * {String} The string, camelized
    */
    camelize: function (str) {
        var oStringList = str.split('-');
        var camelizedString = oStringList[0];
        for (var i = 1, len = oStringList.length; i < len; i++) {
            var s = oStringList[i];
            camelizedString += s.charAt(0).toUpperCase() + s.substring(1);
        }
        return camelizedString;
    },

    /**
    * APIFunction: format
    * Given a string with tokens in the form ${token}, return a string
    *     with tokens replaced with properties from the given context
    *     object.  Represent a literal "${" by doubling it, e.g. "${${".
    *
    * Parameters:
    * template - {String} A string with tokens to be replaced.  A template
    *     has the form "literal ${token}" where the token will be replaced
    *     by the value of context["token"].
    * context - {Object} An optional object with properties corresponding
    *     to the tokens in the format string.  If no context is sent, the
    *     window object will be used.
    * args - {Array} Optional arguments to pass to any functions found in
    *     the context.  If a context property is a function, the token
    *     will be replaced by the return from the function called with
    *     these arguments.
    *
    * Returns:
    * {String} A string with tokens replaced from the context object.
    */
    format: function (template, context, args) {
        if (!context) {
            context = window;
        }

        // Example matching: 
        // str   = ${foo.bar}
        // match = foo.bar
        var replacer = function (str, match) {
            var replacement;

            // Loop through all subs. Example: ${a.b.c}
            // 0 -> replacement = context[a];
            // 1 -> replacement = context[a][b];
            // 2 -> replacement = context[a][b][c];
            var subs = match.split(/\.+/);
            for (var i = 0; i < subs.length; i++) {
                if (i == 0) {
                    replacement = context;
                }

                replacement = replacement[subs[i]];
            }

            if (typeof replacement == "function") {
                replacement = args ?
                    replacement.apply(null, args) :
                    replacement();
            }

            // If replacement is undefined, return the string 'undefined'.
            // This is a workaround for a bugs in browsers not properly 
            // dealing with non-participating groups in regular expressions:
            // http://blog.stevenlevithan.com/archives/npcg-javascript
            if (typeof replacement == 'undefined') {
                return 'undefined';
            } else {
                return replacement;
            }
        };

        return template.replace(OpenLayers.String.tokenRegEx, replacer);
    },

    /**
    * Property: Qmap.String.tokenRegEx
    * Used to find tokens in a string.
    * Examples: ${a}, ${a.b.c}, ${a-b}, ${5}
    */
    tokenRegEx: /\$\{([\w.]+?)\}/g,

    /**
    * Property: Qmap.String.numberRegEx
    * Used to test strings as numbers.
    */
    numberRegEx: /^([+-]?)(?=\d|\.\d)\d*(\.\d*)?([Ee]([+-]?\d+))?$/,

    /**
    * APIFunction: Qmap.String.isNumeric
    * Determine whether a string contains only a numeric value.
    *
    * Examples:
    * (code)
    * Qmap.String.isNumeric("6.02e23") // true
    * Qmap.String.isNumeric("12 dozen") // false
    * Qmap.String.isNumeric("4") // true
    * Qmap.String.isNumeric(" 4 ") // false
    * (end)
    *
    * Returns:
    * {Boolean} String contains only a number.
    */
    isNumeric: function (value) {
        return OpenLayers.String.numberRegEx.test(value);
    },

    /**
    * APIFunction: numericIf
    * Converts a string that appears to be a numeric value into a number.
    * 
    * Returns
    * {Number|String} a Number if the passed value is a number, a String
    *     otherwise. 
    */
    numericIf: function (value) {
        return OpenLayers.String.isNumeric(value) ? parseFloat(value) : value;
    }

};

/**
* Function: getParameters
* Parse the parameters from a URL or from the current page itself into a 
*     JavaScript Object. Note that parameter values with commas are separated
*     out into an Array.
* 
* Parameters:
* url - {String} Optional url used to extract the query string.
*                If null, query string is taken from page location.
* 
* Returns:
* {Object} An object of key/value pairs from the query string.
*/
Qmap.Util.getParameters = function (url) {
    // if no url specified, take it from the location bar
    url = url || window.location.href;

    //parse out parameters portion of url string
    var paramsString = "";
    if (Qmap.String.contains(url, '?')) {
        var start = url.indexOf('?') + 1;
        var end = Qmap.String.contains(url, "#") ?
                    url.indexOf('#') : url.length;
        paramsString = url.substring(start, end);
    }

    var parameters = {};
    var pairs = paramsString.split(/[&;]/);
    for (var i = 0, len = pairs.length; i < len; ++i) {
        var keyValue = pairs[i].split('=');
        if (keyValue[0]) {
            var key = decodeURIComponent(keyValue[0]);
            var value = keyValue[1] || ''; //empty string if no value

            //decode individual values (being liberal by replacing "+" with " ")
            value = decodeURIComponent(value.replace(/\+/g, " ")).split(",");

            //if there's only one value, do not return as array                    
            if (value.length == 1) {
                value = value[0];
            }

            parameters[key] = value;
        }
    }
    return parameters;
};

/**
* Function: getParameterString
* 
* Parameters:
* params - {Object}
* 
* Returns:
* {String} A concatenation of the properties of an object in 
*          http parameter notation. 
*          (ex. <i>"key1=value1&key2=value2&key3=value3"</i>)
*          If a parameter is actually a list, that parameter will then
*          be set to a comma-seperated list of values (foo,bar) instead
*          of being URL escaped (foo%3Abar). 
*/
Qmap.Util.getParameterString = function (params) {
    var paramsArray = [];

    for (var key in params) {
        var value = params[key];
        if ((value != null) && (typeof value != 'function')) {
            var encodedValue;
            if (typeof value == 'object' && value.constructor == Array) {
                /* value is an array; encode items and separate with "," */
                var encodedItemArray = [];
                var item;
                for (var itemIndex = 0, len = value.length; itemIndex < len; itemIndex++) {
                    item = value[itemIndex];
                    encodedItemArray.push(encodeURIComponent(
                (item === null || item === undefined) ? "" : item)
            );
                }
                encodedValue = encodedItemArray.join(",");
            }
            else {
                /* value is a string; simply encode */
                encodedValue = encodeURIComponent(value);
            }
            paramsArray.push(encodeURIComponent(key) + "=" + encodedValue);
        }
    }

    return paramsArray.join("&");
};


/** 
* Function: Qmap.parseXMLString
* Parse XML into a doc structure
* 
* Parameters:
* text - {String} 
* 
* Returns:
* {?} Parsed AJAX Responsev
*/
Qmap.parseXMLString = function (text) {

    //MS sucks, if the server is bad it dies
    var index = text.indexOf('<');
    if (index > 0) {
        text = text.substring(index);
    }

    var ajaxResponse = Qmap.Util.Try(
        function () {
            var xmldom = new ActiveXObject('Microsoft.XMLDOM');
            xmldom.loadXML(text);
            return xmldom;
        },
        function () {
            return new DOMParser().parseFromString(text, 'text/xml');
        },
        function () {
            var req = new XMLHttpRequest();
            req.open("GET", "data:" + "text/xml" +
                     ";charset=utf-8," + encodeURIComponent(text), false);
            if (req.overrideMimeType) {
                req.overrideMimeType("text/xml");
            }
            req.send(null);
            return req.responseXML;
        }
    );

    return ajaxResponse;
};

Qmap.ProxyHost = "";

/**
* Namespace: Qmap.Ajax
*/
Qmap.Ajax = {

    /**
    * Method: emptyFunction
    */
    emptyFunction: function () { },

    /**
    * Method: getTransport
    * 
    * Returns: 
    * {Object} Transport mechanism for whichever browser we're in, or false if
    *          none available.
    */
    getTransport: function () {
        return Qmap.Util.Try(
            function () { return new XMLHttpRequest(); },
            function () { return new ActiveXObject('Msxml2.XMLHTTP'); },
            function () { return new ActiveXObject('Microsoft.XMLHTTP'); }
        ) || false;
    },

    /**
    * Property: activeRequestCount
    * {Integer}
    */
    activeRequestCount: 0
};

/**
* Namespace: Qmap.Ajax.Responders
* {Object}
*/
Qmap.Ajax.Responders = {

    /**
    * Property: responders
    * {Array}
    */
    responders: [],

    /**
    * Method: register
    *  
    * Parameters:
    * responderToAdd - {?}
    */
    register: function (responderToAdd) {
        for (var i = 0; i < this.responders.length; i++) {
            if (responderToAdd == this.responders[i]) {
                return;
            }
        }
        this.responders.push(responderToAdd);
    },

    /**
    * Method: unregister
    *  
    * Parameters:
    * responderToRemove - {?}
    */
    unregister: function (responderToRemove) {
        Qmap.Util.removeItem(this.reponders, responderToRemove);
    },

    /**
    * Method: dispatch
    * 
    * Parameters:
    * callback - {?}
    * request - {?}
    * transport - {?}
    */
    dispatch: function (callback, request, transport) {
        var responder;
        for (var i = 0; i < this.responders.length; i++) {
            responder = this.responders[i];

            if (responder[callback] &&
                typeof responder[callback] == 'function') {
                try {
                    responder[callback].apply(responder,
                                              [request, transport]);
                } catch (e) {
                }
            }
        }
    }
};

Qmap.Ajax.Responders.register({
    /** 
    * Function: onCreate
    */
    onCreate: function () {
        Qmap.Ajax.activeRequestCount++;
    },

    /**
    * Function: onComplete
    */
    onComplete: function () {
        Qmap.Ajax.activeRequestCount--;
    }
});

/**
* Class: Qmap.Ajax.Base
*/
Qmap.Ajax.Base = Qmap.Class({

    /**
    * Constructor: Qmap.Ajax.Base
    * 
    * Parameters: 
    * options - {Object}
    */
    initialize: function (options) {
        this.options = {
            method: 'post',
            asynchronous: true,
            contentType: 'application/xml',
            parameters: ''
        };
        Qmap.Util.extend(this.options, options || {});

        this.options.method = this.options.method.toLowerCase();

        if (typeof this.options.parameters == 'string') {
            this.options.parameters =
                Qmap.Util.getParameters(this.options.parameters);
        }
    }
});

/**
* Class: Qmap.Ajax.Request
* *Deprecated*.  Use <Qmap.Request> method instead.
*
* Inherit:
*  - <Qmap.Ajax.Base>
*/
Qmap.Ajax.Request = Qmap.Class(Qmap.Ajax.Base, {

    /**
    * Property: _complete
    *
    * {Boolean}
    */
    _complete: false,

    /**
    * Constructor: Qmap.Ajax.Request
    * 
    * Parameters: 
    * url - {String}
    * options - {Object}
    */
    initialize: function (url, options) {
        Qmap.Ajax.Base.prototype.initialize.apply(this, [options]);

        if (Qmap.ProxyHost && Qmap.String.startsWith(url, "http")) {
            url = Qmap.ProxyHost + encodeURIComponent(url);
        }

        this.transport = Qmap.Ajax.getTransport();
        this.request(url);
    },

    /**
    * Method: request
    * 
    * Parameters:
    * url - {String}
    */
    request: function (url) {
        this.url = url;
        this.method = this.options.method;
        var params = Qmap.Util.extend({}, this.options.parameters);

        if (this.method != 'get' && this.method != 'post') {
            // simulate other verbs over post
            params['_method'] = this.method;
            this.method = 'post';
        }

        this.parameters = params;

        if (params = Qmap.Util.getParameterString(params)) {
            // when GET, append parameters to URL
            if (this.method == 'get') {
                this.url += ((this.url.indexOf('?') > -1) ? '&' : '?') + params;
            } else if (/Konqueror|Safari|KHTML/.test(navigator.userAgent)) {
                params += '&_=';
            }
        }

        this.url += ((this.url.indexOf('?') > -1) ? '&' : '?') + "r=" + new Date().getTime();

        try {
            var response = new Qmap.Ajax.Response(this);
            if (this.options.onCreate) {
                this.options.onCreate(response);
            }

            Qmap.Ajax.Responders.dispatch('onCreate',
                                                this,
                                                response);

            this.transport.open(this.method.toUpperCase(),
                                this.url,
                                this.options.asynchronous);

            if (this.options.asynchronous) {
                window.setTimeout(
                    Qmap.Function.bind(this.respondToReadyState, this, 1),
                    10);
            }

            this.transport.onreadystatechange =
                Qmap.Function.bind(this.onStateChange, this);
            this.setRequestHeaders();

            this.body = this.method == 'post' ?
                (this.options.postBody || params) : null;
            this.transport.send(this.body);

            // Force Firefox to handle ready state 4 for synchronous requests
            if (!this.options.asynchronous &&
                this.transport.overrideMimeType) {
                this.onStateChange();
            }
        } catch (e) {
            this.dispatchException(e);
        }
    },

    /**
    * Method: onStateChange
    */
    onStateChange: function () {
        var readyState = this.transport.readyState;
        if (readyState > 1 && !((readyState == 4) && this._complete)) {
            this.respondToReadyState(this.transport.readyState);
        }
    },

    /**
    * Method: setRequestHeaders
    */
    setRequestHeaders: function () {
        var headers = {
            'X-Requested-With': 'XMLHttpRequest',
            'Accept': 'text/javascript, text/html, application/xml, text/xml, */*',
            'Qmap': true
        };

        if (this.method == 'post') {
            headers['Content-type'] = this.options.contentType +
                (this.options.encoding ? '; charset=' + this.options.encoding : '');

            /* Force "Connection: close" for older Mozilla browsers to work
            * around a bug where XMLHttpRequest sends an incorrect
            * Content-length header. See Mozilla Bugzilla #246651.
            */
            if (this.transport.overrideMimeType &&
                (navigator.userAgent.match(/Gecko\/(\d{4})/) || [0, 2005])[1] < 2005) {
                headers['Connection'] = 'close';
            }
        }
        // user-defined headers
        if (typeof this.options.requestHeaders == 'object') {
            var extras = this.options.requestHeaders;

            if (typeof extras.push == 'function') {
                for (var i = 0, length = extras.length; i < length; i += 2) {
                    headers[extras[i]] = extras[i + 1];
                }
            } else {
                for (var i in extras) {
                    headers[i] = extras[i];
                }
            }
        }

        for (var name in headers) {
            this.transport.setRequestHeader(name, headers[name]);
        }
    },

    /**
    * Method: success
    *
    * Returns:
    * {Boolean} - 
    */
    success: function () {
        var status = this.getStatus();
        return !status || (status >= 200 && status < 300);
    },

    /**
    * Method: getStatus
    *
    * Returns:
    * {Integer} - Status
    */
    getStatus: function () {
        try {
            return this.transport.status || 0;
        } catch (e) {
            return 0;
        }
    },

    /**
    * Method: respondToReadyState
    *
    * Parameters:
    * readyState - {?}
    */
    respondToReadyState: function (readyState) {
        var state = Qmap.Ajax.Request.Events[readyState];
        var response = new Qmap.Ajax.Response(this);

        if (state == 'Complete') {
            try {
                this._complete = true;
                (this.options['on' + response.status] ||
                    this.options['on' + (this.success() ? 'Success' : 'Failure')] ||
                    Qmap.Ajax.emptyFunction)(response);
            } catch (e) {
                this.dispatchException(e);
            }

            var contentType = response.getHeader('Content-type');
        }

        try {
            (this.options['on' + state] ||
             Qmap.Ajax.emptyFunction)(response);
            Qmap.Ajax.Responders.dispatch('on' + state,
                                                 this,
                                                 response);
        } catch (e) {
            this.dispatchException(e);
        }

        if (state == 'Complete') {
            // avoid memory leak in MSIE: clean up
            this.transport.onreadystatechange = Qmap.Ajax.emptyFunction;
        }
    },

    /**
    * Method: getHeader
    * 
    * Parameters:
    * name - {String} Header name
    *
    * Returns:
    * {?} - response header for the given name
    */
    getHeader: function (name) {
        try {
            return this.transport.getResponseHeader(name);
        } catch (e) {
            return null;
        }
    },

    /**
    * Method: dispatchException
    * If the optional onException function is set, execute it
    * and then dispatch the call to any other listener registered
    * for onException.
    * 
    * If no optional onException function is set, we suspect that
    * the user may have also not used
    * Qmap.Ajax.Responders.register to register a listener
    * for the onException call.  To make sure that something
    * gets done with this exception, only dispatch the call if there
    * are listeners.
    *
    * If you explicitly want to swallow exceptions, set
    * request.options.onException to an empty function (function(){})
    * or register an empty function with <Qmap.Ajax.Responders>
    * for onException.
    * 
    * Parameters:
    * exception - {?}
    */
    dispatchException: function (exception) {
        var handler = this.options.onException;
        if (handler) {
            // call options.onException and alert any other listeners
            handler(this, exception);
            Qmap.Ajax.Responders.dispatch('onException', this, exception);
        } else {
            // check if there are any other listeners
            var listener = false;
            var responders = Qmap.Ajax.Responders.responders;
            for (var i = 0; i < responders.length; i++) {
                if (responders[i].onException) {
                    listener = true;
                    break;
                }
            }
            if (listener) {
                // call all listeners
                Qmap.Ajax.Responders.dispatch('onException', this, exception);
            } else {
                // let the exception through
                throw exception;
            }
        }
    }
});

/** 
* Property: Events
* {Array(String)}
*/
Qmap.Ajax.Request.Events =
  ['Uninitialized', 'Loading', 'Loaded', 'Interactive', 'Complete'];

/**
* Class: Qmap.Ajax.Response
*/
Qmap.Ajax.Response = Qmap.Class({

    /**
    * Property: status
    *
    * {Integer}
    */
    status: 0,


    /**
    * Property: statusText
    *
    * {String}
    */
    statusText: '',

    /**
    * Constructor: Qmap.Ajax.Response
    * 
    * Parameters: 
    * request - {Object}
    */
    initialize: function (request) {
        this.request = request;
        var transport = this.transport = request.transport,
            readyState = this.readyState = transport.readyState;

        if ((readyState > 2 &&
            !(!!(window.attachEvent && !window.opera))) ||
            readyState == 4) {
            this.status = this.getStatus();
            this.statusText = this.getStatusText();
            this.responseText = transport.responseText == null ?
                '' : String(transport.responseText);
        }

        if (readyState == 4) {
            var xml = transport.responseXML;
            this.responseXML = xml === undefined ? null : xml;
        }
    },

    /**
    * Method: getStatus
    */
    getStatus: Qmap.Ajax.Request.prototype.getStatus,

    /**
    * Method: getStatustext
    *
    * Returns:
    * {String} - statusText
    */
    getStatusText: function () {
        try {
            return this.transport.statusText || '';
        } catch (e) {
            return '';
        }
    },

    /**
    * Method: getHeader
    */
    getHeader: Qmap.Ajax.Request.prototype.getHeader,

    /** 
    * Method: getResponseHeader
    *
    * Returns:
    * {?} - response header for given name
    */
    getResponseHeader: function (name) {
        return this.transport.getResponseHeader(name);
    }
});



/**
* Function: getElementsByTagNameNS
* 
* Parameters:
* parentnode - {?}
* nsuri - {?}
* nsprefix - {?}
* tagname - {?}
* 
* Returns:
* {?}
*/
Qmap.Ajax.getElementsByTagNameNS = function (parentnode, nsuri,
                                                   nsprefix, tagname) {
    var elem = null;
    if (parentnode.getElementsByTagNameNS) {
        elem = parentnode.getElementsByTagNameNS(nsuri, tagname);
    } else {
        elem = parentnode.getElementsByTagName(nsprefix + ':' + tagname);
    }
    return elem;
};


//此函数保证加载多个onload事件后，事件是按照顺序来执行的。
function addLoadEvent(func) {
    var oldonload = window.onload;
    if (typeof window.onload != 'function') {
        window.onload = func;
    } else {
        window.onload = function () {
            oldonload();
            func();
        }
    }
}


function checkArguments(args, count, methodname) {
    //args : 参数集合，count :应该输入的参数个数，methodname :方法名称
    if (args.length != count) {
        alert(methodname + "方法的参数数量不匹配");
        return false;
    }
    return true;
}

///此函数保证客户安装控件失败的时候，提示客户端进行远程安装
function get_element_id(o) {
    return document.getElementById(o);
}
function showDownloadDiv(qmapid) {
    get_element_id("dialogbox").style.display = 'block';
    if (qmapid != null) qmapid.parentElement.style.display = 'none';
}

function closeDownloadDiv(qmapid) {
    get_element_id("dialogbox").style.display = 'none';
    if (qmapid != null) qmapid.parentElement.style.display = 'block';
}

//检查控件是否安装
function detectQMapPlugin() {

    if (window.Qmap.Browser.IE) {
        return detectQMapActiveX(); //检查AX控件是否安装
    }
    else if (window.Qmap.Browser.WebKit || window.Qmap.Browser.Gecko)//如果是firefox/chrome浏览器
    {
        for (x = 0; x < navigator.plugins.length; x++) {
            if (navigator.plugins[x].name == 'npQMapactivex.dll') //检查是否有NP的DLL
                return true;
        }
    }

    return false;
}

//检查AX控件是否安装
function detectQMapActiveX() {
    try {
        var qmapCtrl = new ActiveXObject("QMap3DActiveX.QMap3D.1");
    }
    catch (e) {
        return false;
    }
    delete qmapCtrl;
    qmapCtrl = null;
    return true;
}


function addPluginDiv(downloadurl) {
    var dg = get_element_id("dialogbox");
    if (dg != null)  //如果页面中已经有这个ID则先移除这个ID所在的DIV再添加DIV
    {
        var pp = dg.parentElement;
        pp.removeChild(dg);
        document.write('<div id="dialogbox" style="background:#ccc;display: none;height:100%">');
        document.write('<p id="instnote" style="width:100%; text-align:center; position: relative; top: 40%; font-family:微软雅黑,宋体; font-size:12pt;color:crimson;line-height:12mm">请等待系统下载安装 Q-MAP 3D矢量插件...<br/><a href="' + downloadurl + '"><img src="images/3d_download_btn.gif" border="0"></a></p>');
        document.write('</div>');
    }
    else {
        document.write('<div id="dialogbox" style="background:#ccc;display: none;height:100%">');
        document.write('<p id="instnote" style="width:100%; text-align:center; position: relative; top: 20%; font-family:微软雅黑,宋体; font-size:12pt;color:crimson;line-height:12mm">安装顺序：1.下载安装地图矢量插件, 2.下载安装NP(FF,Chrome)插件.<br/></p>');
        document.write('<p id="instnote" style="width:100%; text-align:center; position: relative; top: 20%; font-family:微软雅黑,宋体; font-size:12pt;color:crimson;line-height:12mm">下载安装Q-MAP 3D矢量插件<br/><a href="' + downloadurl + '"><img src="images/3d_download_btn.gif" border="0"></a></p>');
        //	    document.write('<p id="instnote" style="width:100%; text-align:center; position: relative; top: 20%; font-family:微软雅黑,宋体; font-size:12pt;color:crimson;line-height:12mm">下载安装Q-MAP NP插件<br/><a href="' + this.options.npsetuppath + '"><img src="images/np_download_btn.gif" border="0"></a></p>');
        document.write('</div>');
    }
}


/**	自动重新加载当前页面
*/
function autoReload() {
    navigator.plugins.refresh();
    if (detectQMapPlugin()) {
        window.location.reload();
    }

    setTimeout('autoReload()', 200)
}
///暂时不用
function detectPlugin(clsid, propname, objid, downloadurl) {

    var pluginDiv = document.createElement("<div id=\"pluginDiv\" style=\"display:none\"></div>")
    document.body.insertBefore(pluginDiv);
    pluginDiv.innerHTML = "<object id=objectForDetectPlugin classid=CLSID:" + clsid + "></object>";
    try {
        //alert(eval("objectForDetectPlugin." + propname));
        if (eval("objectForDetectPlugin." + propname) == undefined) //验证是否安装成功，不成功则显示提示信息
        {
            pluginDiv.removeNode(true); //删除pluginDiv及其所有子元素
            document.getElementById(objid).innerHTML = "<div style='background-color:#707070;'><p style='text-align:center; color:#ccc; font-size:16px; '>请先安装Webmax播放器</p>" +
      "<p style='text-align:center;'><a href=" + downloadurl + "><img src=download_btn.gif border=0></a></p></div>";
            // 请点击<a href=" + downloadurl + ">这里</a>执行手动安装，安装后请刷新页面。</div>";
            return false;
        }
        else {
            pluginDiv.removeNode(true); //删除pluginDiv及其所有子元素
            return true;
        }
    }
    catch (e) {
        ; //return false;
    }
}



//得到object对象
function get3DMapObject() {

    var qmapID;
    //随机值取1~100
    while (true) {
        qmapID = "qmap3d" + parseInt(Math.random() * 100 + 1);

        if (!document.getElementById(qmapID)) {
            break;
        }
    }

    var cbStr = "qmap3d.cab";
    if (this.options.ver != "") cbStr = 'qmap3d_' + this.options.ver.replace(/,/g, '') + '.cab#version=' + this.options.ver;

    if (window.Qmap.Browser.IE) {
        AC_AX_RunContent('classid', 'clsid:C0447E3A-ADAF-45D0-A577-B107683A39A4',
						  'codeBase', cbStr,
						  'id', qmapID,
						  'type', 'application/x-oleobject',
						  'width', this.options.width,
						  'height', this.options.height
        );

    } else {
        AC_AX_RunContent('clsid', '{C0447E3A-ADAF-45D0-A577-B107683A39A4}',
						 'codeBaseURL', cbStr,
						 'type', 'application/QMap-activex',
						 'id', qmapID,
						 'width', this.options.width,
						 'height', this.options.height,
						 'event_OnEndInitialize', 'OnEndInitialize',
						 'event_SelectedModel', 'OnSelectedModel',
						 'event_OnLButtonDown', 'OnLClickDown',
						 'event_OnLButtonUp', 'OnLClickUp',
						 'event_OnRButtonDown', 'OnRClickDown',
						 'event_OnRButtonUp', 'OnRClickUp',
						 'event_OnLButtonDBLCLK', 'OnLDbClick',
						 'event_OnRButtonDBLCLK', 'OnRDbClick',
						 'event_OnMouseMove', 'OnMouseMove',
						 'event_OnMouseWheel', 'OnMouseWheel',
						 'event_OnUserNotify', 'OnUserNotify',
						 'event_OnMenuSelected', 'OnMenuSelected',
						 'event_OnSceneSave', 'OnSceneSave',
						 'event_OnCameraChanged', 'OnCameraChanged',
						 'event_OnRouteStart', 'OnRouteStart',
						 'event_OnRouteStop', 'OnRouteStop',
						 'event_OnRouteTimeTrigger', 'OnRouteTimeTrigger',
						 'event_OnModelInstDyanmicStart', 'OnModelInstDyanmicStart',
						 'event_OnModelInstDyanmicStop', 'OnModelInstDyanmicStop'
		);
    }
    var map = document.getElementById(qmapID);
    Qmap.arrMap.push(map); //压入数组

    return map
}
//定义的事件对象，这些对象都是给FF,CHROME浏览器使用的
var OnEndInitialize;
var OnSelectedModel;
var OnLClickDown;
var OnLClickUp;
var OnRClickDown;
var OnRClickUp;
var OnLDbClick;
var OnRDbClick;
var OnMouseMove;
var OnMouseWheel;
var OnUserNotify;
var OnMenuSelected;
var OnSceneSave;
var OnCameraChanged;
var OnRouteStart;
var OnRouteStop;
var OnRouteTimeTrigger;
var OnModelInstDyanmicStart;
var OnModelInstDyanmicStop;

/*
* Qmap.Map3d类
*/
Qmap.Map3d = Qmap.Class({

    ///真正的控件对象
    handle: null,

    /**
    * Constructor: Qmap.Map3d
    * 
    * Parameters: 
    * options - {Object}
    */
    initialize: function (options) {
        this.options = {
            ver: Qmap.Version,                   //版本号
            width: "100%",                    //宽度
            height: "100%",                    //高度

            //下面都是参数
            dataserveraddress: "www.q-map.com.cn/qmap3dData/test52/", //地图数据服务器地址
            databufferfolder: "C:\\QMap3dData\\test52", //设置地图数据本地存储路径
            setuppath: "http://" + location.host + "/" + location.pathname.split('/')[1], //控件下载手动安装的路径 modified by wuzt 2012-10-23
            npsetuppath: "http://" + location.host + "/" + location.pathname.split('/')[1] + "/qmapnp.exe", //plugin手动安装的路径 modified by wuzt 2012-10-23
            initlatitude: "0", //设置地图初始维度
            initlongitude: "0", //设置地图初始经度
            initaltitude: "0", //设置地图初始海拔高度
            initactivelayer: "default", //设置当前活动层。所有的检索是针对当前活动层的。
            inithorizontalviewangle: "0", //设置地图初始水平方向角度。
            initverticalviewangle: "0", //设置地图初始垂直方向角度。
            initviewradius: "500", //设置地图显示半径。
            initareacode: "0", //设置区域代码。
            autoterrain: "0", //设置地图使用地形数据。
            autoterraintex: "1", //设置地图使用地形贴图。
            showsun: "0", //设置是否显示太阳光晕。1 是0 否
            showsky: "1", //设置是否显示天空。1 是0 否
            defaultterraintexture: "", //设置地图默认的地形贴图文件名。
            onlyusedefaulttex: "0", //是否仅使用默认纹理。 1 是0 否
            backcolor: "", //背景色
            panposition: "2", //设置导航条的位置0 左边1 右边。
            online: "1", //是否使用在线数据。 1 在线数据0 本地数据
            usetransparent: "0", //是否使用透明。1 是0 否
            enablecullback: "0", //是否剔除背面。 1 是0 否
            enablefog: "0", //是否使用雾。 1 是0 否
            antialias: "1", //是否使用反走样。 1 是0 否
            codepage: "936", //场景及模型文件所使用的区域编码。默认是简体
            scenetype: "0", //场景类型。 0小场景1 大场景
            terrainsize: null, //设置地形分块大小，单位米，默认值200米。可以根据地形数据密度设置分块大小
            nearclip: 0.1, //场景近裁面值
            farclip: 10000, //场景近裁面值
            inifile: "option.ini", //场景所用初始化参数文件
            skyboxname: undefined,
            skyboxangle: undefined,
            autoinstall: 1					//是否允许自动安装
        };
        Qmap.Util.extend(this.options, options || {}); //把各种参数绑定到options上面

        //此时QMap控件所在的DIV为不可见
        if (this.options.autoinstall == 1)
            this.handle = get3DMapObject.call(this); //初始化对象

        var obj = this;
        try {

            //添加下载DIV显示，初始为不可见
            if (this.options.ver != "") obj.options.setuppath += "qmap3dax_" + this.options.ver.replace(/,/g, '') + '.exe'; //modified by wuzt 2012-10-10
            else obj.options.setuppath += "qmap3dax.exe";

            if (window.Qmap.Browser.IE) //如果是IE则提供AX的下载
                addPluginDiv(obj.options.setuppath);
            else if (window.Qmap.Browser.WebKit || window.Qmap.Browser.Gecko)   //如果是FF,CHROME则提供PLUGIN的下载
                addPluginDiv(obj.options.npsetuppath);
            else {
                alert("当前只支持IE,FF,Chrome");
                return;
            }
            if (detectQMapPlugin())//检查控件是否在本地注册安装,true-表示已安装
            {
                if (obj.options.autoinstall != 1) {
                    //obj.options.ver = "";//清除版本定义  
                    this.handle = get3DMapObject.call(this); //初始化对象
                }
                closeDownloadDiv(this.handle); //打开QMap控件所在的DIV，关闭下载DIV，可能触发IE的自动升级

            }
            else//这里控件未安装
            {
                //只要控件没安装则 显示下载DIV，关闭QMap控件所在的DIV
                //if (obj.options.autoinstall!=1)
                {
                    showDownloadDiv(this.handle);
                }
                autoReload(); //自动检查是否安装完成，若完成，则刷新页面
                return;

            }


            addLoadEvent(function () {//在window.onload加载完后执行下面的事件

                //加载完后第一个要执行的方法，验证是否安装成功     	
                /*
                if(!detectPlugin("3BBE5C24-0908-4DA4-905F-3A83D4E1695F","AntiAlias", "div_"+obj.handle.id, obj.options.setupPath))
                {
                //alert(1);
                return; //不成功的状态下，则直接跳出
                }
                */

                try {

                    //开始初始化设置 初始化前可调用
                    obj.IniFile(obj.options.inifile);
                    obj.DataServerAddress(obj.options.dataserveraddress);
                    obj.DataBufferFolder(obj.options.databufferfolder);
                    obj.InitAreaCode(obj.options.initareacode);
                    obj.Online(obj.options.online);
                    obj.CodePage(obj.options.codepage);
                    obj.SceneType(obj.options.scenetype);
                    //结束初始化设置 初始化前可调用


                    if (window.Qmap.Browser.IE) { // IE
                        obj.handle.attachEvent("OnEndInitialize", function () { //绑定初始化后的事件
                            obj.EndInitialize.call(obj);
                        });

                    } else if (window.Qmap.Browser.WebKit || window.Qmap.Browser.Gecko) {
                        OnEndInitialize = function () { //绑定初始化后的事件
                            obj.EndInitialize.call(obj);
                        }
                    }

                    obj.Initialize(); //开始初始化

                    //开始初始化设置 初始化后可调用
                    obj.InitLatitude(obj.options.initlatitude);
                    obj.InitLongitude(obj.options.initlongitude);
                    obj.InitAltitude(obj.options.initaltitude);
                    obj.InitActiveLayer(obj.options.initactivelayer);
                    obj.InitHorizontalViewAngle(obj.options.inithorizontalviewangle);
                    obj.InitVerticalViewAngle(obj.options.initverticalviewangle);
                    obj.InitViewRadius(obj.options.initviewradius);
                    obj.ShowSun(obj.options.showsun);
                    obj.ShowSky(obj.options.showsky);
                    obj.AutoTerrain(obj.options.autoterrain);
                    obj.AutoTerrainTex(obj.options.autoterraintex);
                    if (obj.options.defaultterraintexture != "")
                        obj.DefaultTerrainTexture(obj.options.defaultterraintexture);
                    obj.OnlyUseDefaultTex(obj.options.onlyusedefaulttex);
                    if (obj.options.backcolor != "")
                        obj.BackColor(obj.options.backcolor);
                    obj.PanPosition(obj.options.panposition);
                    obj.UseTransParent(obj.options.usetransparent);
                    obj.EnableCullBack(obj.options.enablecullback);
                    obj.EnableFog(obj.options.enablefog);
                    obj.AntiAlias(obj.options.antialias);
                    if (obj.options.terrainsize != null)
                        obj.TerrainSize(obj.options.terrainsize);
                    obj.NearClip(obj.options.nearclip);
                    obj.FarClip(obj.options.farclip);
                    if (typeof (obj.options.skyboxname) != "undefined")
                        obj.SkyboxName(obj.options.skyboxname);
                    if (typeof (obj.options.skyboxangle) != "undefined")
                        obj.SkyboxAngle(obj.options.skyboxangle);
                    //结束初始化设置 初始化后可调用

                    //obj.MoveTo(obj.options.initlongitude,obj.options.initaltitude,obj.options.initlatitude);
                }
                catch (E) {
                }
            });

        } catch (E) {   //第一次未激活控件的时候会抛出异常

        }

    },
    EndInitialize: function () {
        //绑定初始化完成事件
        //执行回调函数
        if (this.options["OnLoad"] !== undefined && typeof (this.options["OnLoad"]) == 'function') {
            this.options["OnLoad"].call(this, this.options);
        } //执行回调函数
    },
    SceneSave: function (file, type, callback) {
        var options = {
            "file": file,
            "type": type
        };
        if (callback != undefined && typeof (callback) == 'function') {
            callback.call(this, options);
        }
    },
    RouteStart: function (objRoute, callback) {
        var options = {
            "objRoute": objRoute
        };
        if (callback != undefined && typeof (callback) == 'function') {
            callback.call(this, options);
        }
    },
    RouteStop: function (objRoute, callback) {
        var options = {
            "objRoute": objRoute
        };
        if (callback != undefined && typeof (callback) == 'function') {
            callback.call(this, options);
        }
    },
    RouteTimeTrigger: function (objRoute, timePos, userData, callback) {
        var options = {
            "objRoute": objRoute,
            "timePos": timePos,
            "userData": userData
        };
        if (callback != undefined && typeof (callback) == 'function') {
            callback.call(this, options);
        }
    },
    CameraChanged: function (type, x, y, z, hAngle, vAngle, callback) {
        var options = {
            "type": type,
            "x": x,
            "y": y,
            "z": z,
            "hAngle": hAngle,
            "vAngle": vAngle
        };
        if (callback != undefined && typeof (callback) == 'function') {
            callback.call(this, options);
        }
    },
    SelectedModel: function (strID, strName, callback) {
        var options = {
            "id": strID,
            "name": strName
        };
        //执行回调函数
        if (callback != undefined && typeof (callback) == 'function') {
            callback.call(this, options);
        }
    },
    LClickDown: function (wParam, logX, logY, grdX, grdY, grdZ, callback) {
        var options = {
            "wParam": wParam,
            "logX": logX,
            "logY": logY,
            "grdX": grdX,
            "grdY": grdY,
            "grdZ": grdZ
        };
        //执行回调函数
        if (callback != undefined && typeof (callback) == 'function') {
            callback.call(this, options);
        }
    },
    LClickUp: function (wParam, logX, logY, grdX, grdY, grdZ, callback) {
        var options = {
            "wParam": wParam,
            "logX": logX,
            "logY": logY,
            "grdX": grdX,
            "grdY": grdY,
            "grdZ": grdZ
        };
        //执行回调函数
        if (callback != undefined && typeof (callback) == 'function') {
            callback.call(this, options);
        }
    },
    RClickDown: function (wParam, logX, logY, grdX, grdY, grdZ, callback) {
        var options = {
            "wParam": wParam,
            "logX": logX,
            "logY": logY,
            "grdX": grdX,
            "grdY": grdY,
            "grdZ": grdZ
        };
        //执行回调函数 
        if (callback != undefined && typeof (callback) == 'function') {
            callback.call(this, options);
        }
    },
    RClickUp: function (wParam, logX, logY, grdX, grdY, grdZ, callback) {
        var options = {
            "wParam": wParam,
            "logX": logX,
            "logY": logY,
            "grdX": grdX,
            "grdY": grdY,
            "grdZ": grdZ
        };
        //执行回调函数 
        if (callback != undefined && typeof (callback) == 'function') {
            callback.call(this, options);
        }
    },
    LDbClick: function (wParam, logX, logY, grdX, grdY, grdZ, callback) {
        var options = {
            "wParam": wParam,
            "logX": logX,
            "logY": logY,
            "grdX": grdX,
            "grdY": grdY,
            "grdZ": grdZ
        };
        //执行回调函数
        if (callback != undefined && typeof (callback) == 'function') {
            callback.call(this, options);
        }
    },
    RDbClick: function (wParam, logX, logY, grdX, grdY, grdZ, callback) {
        var options = {
            "wParam": wParam,
            "logX": logX,
            "logY": logY,
            "grdX": grdX,
            "grdY": grdY,
            "grdZ": grdZ
        };
        //执行回调函数
        if (callback != undefined && typeof (callback) == 'function') {
            callback.call(this, options);
        }
    },
    MouseMove: function (wParam, logX, logY, grdX, grdY, grdZ, callback) {
        var options = {
            "wParam": wParam,
            "logX": logX,
            "logY": logY,
            "grdX": grdX,
            "grdY": grdY,
            "grdZ": grdZ
        };
        //执行回调函数
        if (callback != undefined && typeof (callback) == 'function') {
            callback.call(this, options);
        }
    },
    MouseWheel: function (wParam, logX, logY, grdX, grdY, grdZ, callback) {
        var options = {
            "wParam": wParam,
            "logX": logX,
            "logY": logY,
            "grdX": grdX,
            "grdY": grdY,
            "grdZ": grdZ
        };
        //执行回调函数
        if (callback != undefined && typeof (callback) == 'function') {
            callback.call(this, options);
        }
    },
    UserNotify: function (type, param, callback) {
        var options = {
            "type": type,
            "param": param
        };
        //执行回调函数
        if (callback != undefined && typeof (callback) == 'function') {
            callback.call(this, options);
        }
    },
    MenuSelected: function (menuID, callback) {
        var options = {
            "menuID": menuID
        };
        //执行回调函数
        if (callback != undefined && typeof (callback) == 'function') {
            callback.call(this, options);
        }
    },

    DataServerAddress: function (value) {
        if (value === undefined) {
            return this.handle.DataServerAddress;
        }
        else
            this.handle.DataServerAddress = value;
    },
    DataBufferFolder: function (value) {
        if (value === undefined) {
            return this.handle.DataBufferFolder;
        }
        else {
            this.handle.DataBufferFolder = value;
        }
    },
    InitLatitude: function (value) {
        if (value === undefined) {
            return this.handle.InitLatitude;
        } else {
            this.handle.InitLatitude = value;
        }
    },
    InitLongitude: function (value) {
        if (value === undefined) {
            return this.handle.InitLongitude;
        }
        else {
            this.handle.InitLongitude = value;
        }
    },
    InitAltitude: function (value) {
        if (value === undefined) {
            return this.handle.InitAltitude;
        }
        else {
            this.handle.InitAltitude = value;
        }
    },
    InitActiveLayer: function (value) {
        if (value === undefined) {
            return this.handle.InitActiveLayer;
        }
        else {
            this.handle.InitActiveLayer = value;
        }
    },
    InitHorizontalViewAngle: function (value) {
        if (value === undefined) {
            return this.handle.InitHorizontalViewAngle;
        }
        else {
            this.handle.InitHorizontalViewAngle = value;
        }
    },
    InitVerticalViewAngle: function (value) {
        if (value === undefined) {
            return this.handle.InitVerticalViewAngle;
        }
        else {
            this.handle.InitVerticalViewAngle = value;
        }
    },
    InitViewRadius: function (value) {
        if (value === undefined) {
            return this.handle.InitViewRadius;
        }
        else {
            this.handle.InitViewRadius = value;
        }
    },
    InitAreaCode: function (value) {
        if (value === undefined) {
            return this.handle.InitAreaCode;
        }
        else {
            this.handle.InitAreaCode = value;
        }
    },
    AutoTerrain: function (value) {
        if (value === undefined) {
            return this.handle.AutoTerrain;
        }
        else {
            this.handle.AutoTerrain = value;
        }
    },
    AutoTerrainTex: function (value) {
        if (value === undefined) {
            return this.handle.AutoTerrainTex;
        }
        else {
            this.handle.AutoTerrainTex = value;
        }
    },
    TerrainSize: function (value) {
        if (value === undefined) {
            return this.handle.TerrainSize;
        }
        else {
            this.handle.TerrainSize = value;
        }
    },
    ShowSun: function (value) {
        if (value === undefined) {
            return this.handle.ShowSun;
        }
        else {
            this.handle.ShowSun = value;
        }
    },

    ShowSky: function (value) {
        if (value === undefined) {
            return this.handle.ShowSky;
        }
        else {
            this.handle.ShowSky = value;
        }
    },

    DefaultTerrainTexture: function (value) {
        if (value === undefined) {
            return this.handle.DefaultTerrainTexture;
        }
        else {
            this.handle.DefaultTerrainTexture = value;
        }
    },
    OnlyUseDefaultTex: function (value) {
        if (value === undefined) {
            return this.handle.OnlyUseDefaultTex;
        }
        else {
            this.handle.OnlyUseDefaultTex = value;
        }
    },
    BackColor: function (value) {
        if (value === undefined) {
            return this.handle.BackColor;
        }
        else {
            var color = value.split(",");
            this.handle.BackColor = Qmap.Util.RGB(color[0], color[1], color[2]);
        }
    },
    PanPosition: function (value) {
        if (value === undefined) {
            return this.handle.PanPosition;
        }
        else {
            this.handle.PanPosition = value;
        }
    },
    Online: function (value) {
        if (value === undefined) {
            return this.handle.Online;
        }
        else {
            this.handle.Online = value;
        }
    },
    UseTransParent: function (value) {
        if (value === undefined) {
            return this.handle.UseTransParent;
        }
        else {
            this.handle.UseTransParent = value;
        }
    },
    EnableCullBack: function (value) {
        if (value === undefined) {
            return this.handle.EnableCullBack;
        }
        else {
            this.handle.EnableCullBack = value;
        }
    },
    EnableFog: function (value) {
        if (value === undefined) {
            return this.handle.EnableFog;
        }
        else {
            this.handle.EnableFog = value;
        }
    },
    AntiAlias: function (value) {
        if (value === undefined) {
            return this.handle.AntiAlias;
        }
        else {
            this.handle.AntiAlias = value;
        }
    },
    CodePage: function (value) {
        if (value === undefined) {
            return this.handle.CodePage;
        }
        else {
            this.handle.CodePage = value;
        }
    },
    SceneType: function (value) {
        if (value === undefined) {
            return this.handle.SceneType;
        }
        else {
            this.handle.SceneType = value;
        }
    },

    NearClip: function (value) {
        if (value === undefined) {
            return this.handle.NearClip;
        }
        else {
            this.handle.NearClip = value;
        }
    },
    FarClip: function (value) {
        if (value === undefined) {
            return this.handle.FarClip;
        }
        else {
            this.handle.FarClip = value;
        }
    },
    IniFile: function (value) {
        if (value === undefined) {
            return this.handle.IniFile;
        }
        else {
            this.handle.IniFile = value;
        }
    },
    SkyboxName: function (value) {
        if (value === undefined) {
            return this.handle.SkyboxName;
        }
        else {
            this.handle.SkyboxName = value;
        }
    },
    SkyboxAngle: function (value) {
        if (value === undefined) {
            return this.handle.SkyboxAngle;
        }
        else {
            this.handle.SkyboxAngle = value;
        }
    },
    /*-----------------------------------------------控件消息事件-------------------------------------------------*/

    //保存场景有文件被添加修改或删除时触发该事件
    OnSceneSave: function (callback) {
        var obj = this;
        addLoadEvent(function () {
            if (obj.handle == null) return;
            if (window.Qmap.Browser.IE) //如果是IE则提供AX的下载
            {
                obj.handle.attachEvent("OnSceneSave", function (file, type) {
                    obj.SceneSave.call(obj, file, type, callback);
                });
            }
            else if (window.Qmap.Browser.WebKit || window.Qmap.Browser.Gecko)   //如果是FF,CHROME则提供PLUGIN的下载
            {
                OnSceneSave = function (file, type) {
                    obj.SceneSave.call(obj, file, type, callback);
                };
            }

        })
    },
    //模型移动开始事件
    OnModelInstDyanmicStart: function (callback) {
        var obj = this;
        addLoadEvent(function () {
            if (obj.handle == null) return;
            if (window.Qmap.Browser.IE) //如果是IE则提供AX的下载
            {
                obj.handle.attachEvent("OnModelInstDyanmicStart", function (strID, strName) {
                    obj.ModelInstDyanmicStart.call(obj, strID, strName, callback);
                });
            }
            else if (window.Qmap.Browser.WebKit || window.Qmap.Browser.Gecko)   //如果是FF,CHROME则提供PLUGIN的下载
            {
                OnModelInstDyanmicStart = function (strID, strName) {
                    obj.ModelInstDyanmicStart.call(obj, strID, strName, callback);
                };
            }
        });
    },
    //模型移动结束事件
    OnModelInstDyanmicStop: function (callback) {
        var obj = this;
        addLoadEvent(function () {
            if (obj.handle == null) return;
            if (window.Qmap.Browser.IE) //如果是IE则提供AX的下载
            {
                obj.handle.attachEvent("OnModelInstDyanmicStop", function (strID, strName) {
                    obj.ModelInstDyanmicStop.call(obj, strID, strName, callback);
                });
            }
            else if (window.Qmap.Browser.WebKit || window.Qmap.Browser.Gecko)   //如果是FF,CHROME则提供PLUGIN的下载
            {
                OnModelInstDyanmicStop = function (strID, strName) {
                    obj.ModelInstDyanmicStop.call(obj, strID, strName, callback);
                };
            }
        });
    },

    ModelInstDyanmicStart: function (strID, strName, callback) {
        var options = {
            "strID": strID,
            "strName": strName
        };
        if (callback != undefined && typeof (callback) == 'function') {
            callback.call(this, options);
        }
    },
    ModelInstDyanmicStop: function (strID, strName, callback) {
        var options = {
            "strID": strID,
            "strName": strName
        };
        if (callback != undefined && typeof (callback) == 'function') {
            callback.call(this, options);
        }
    },
    //飞行漫游播放初始事件
    OnRouteStart: function (callback) {
        var obj = this;
        addLoadEvent(function () {
            if (obj.handle == null) return;
            if (window.Qmap.Browser.IE) //如果是IE则提供AX的下载
            {
                obj.handle.attachEvent("OnRouteStart", function (objRoute) {
                    obj.RouteStart.call(obj, objRoute, callback);
                });
            }
            else if (window.Qmap.Browser.WebKit || window.Qmap.Browser.Gecko)   //如果是FF,CHROME则提供PLUGIN的下载
            {
                OnRouteStart = function (objRoute) {
                    obj.RouteStart.call(obj, objRoute, callback);
                };
            }
        });
    },
    //飞行漫游播放结束事件
    OnRouteStop: function (callback) {
        var obj = this;
        addLoadEvent(function () {
            if (obj.handle == null) return;
            if (window.Qmap.Browser.IE) //如果是IE则提供AX的下载
            {
                obj.handle.attachEvent("OnRouteStop", function (objRoute) {
                    obj.RouteStop.call(obj, objRoute, callback);
                });
            }
            else if (window.Qmap.Browser.WebKit || window.Qmap.Browser.Gecko)   //如果是FF,CHROME则提供PLUGIN的下载
            {
                OnRouteStop = function (objRoute) {
                    obj.RouteStop.call(obj, objRoute, callback);
                };
            }
        });
    },
    //飞行漫游播放事件点触发事件
    OnRouteTimeTrigger: function (callback) {
        var obj = this;
        addLoadEvent(function () {
            if (obj.handle == null) return;
            if (window.Qmap.Browser.IE) //如果是IE则提供AX的下载
            {
                obj.handle.attachEvent("OnRouteTimeTrigger", function (objRoute, timePos, userData) {
                    obj.RouteTimeTrigger.call(obj, objRoute, timePos, userData, callback);
                });
            }
            else if (window.Qmap.Browser.WebKit || window.Qmap.Browser.Gecko)   //如果是FF,CHROME则提供PLUGIN的下载
            {
                RouteTimeTrigger = function (objRoute, timePos, userData) {
                    obj.RouteTimeTrigger.call(obj, objRoute, timePos, userData, callback);
                };
            }
        });
    },
    //控件摄像机变化事件
    OnCameraChanged: function (callback) {
        var obj = this;
        addLoadEvent(function () {
            if (obj.handle == null) return;
            if (window.Qmap.Browser.IE) //如果是IE则提供AX的下载
            {
                obj.handle.attachEvent("OnCameraChanged", function (type, x, y, z, hAngle, vAngle) {
                    obj.CameraChanged.call(obj, type, x, y, z, hAngle, vAngle, callback);
                });
            }
            else if (window.Qmap.Browser.WebKit || window.Qmap.Browser.Gecko)   //如果是FF,CHROME则提供PLUGIN的下载
            {
                CameraChanged = function (type, x, y, z, hAngle, vAngle) {
                    obj.CameraChanged.call(obj, type, x, y, z, hAngle, vAngle, callback);
                };
            }
        });
    },
    //模型被选中后事件
    OnSelectedModel: function (callback) {
        var obj = this;
        addLoadEvent(function () {
            if (obj.handle == null) return;
            if (window.Qmap.Browser.IE) //如果是IE则提供AX的下载
            {
                obj.handle.attachEvent("SelectedModel", function (strID, strName) {
                    obj.SelectedModel.call(obj, strID, strName, callback);
                });
            }
            else if (window.Qmap.Browser.WebKit || window.Qmap.Browser.Gecko)   //如果是FF,CHROME则提供PLUGIN的下载
            {
                OnSelectedModel = function (strID, strName) {
                    obj.SelectedModel.call(obj, strID, strName, callback);
                };
            }
        })
    },
    //鼠标左键单击按下事件
    OnLClickDown: function (callback) {
        var obj = this;
        addLoadEvent(function () {
            if (obj.handle == null) return;
            if (window.Qmap.Browser.IE) //如果是IE则提供AX的下载
            {
                obj.handle.attachEvent("OnLButtonDown", function (wParam, logX, logY, grdX, grdY, grdZ) {
                    obj.LClickDown.call(obj, wParam, logX, logY, grdX, grdY, grdZ, callback);
                });
            }
            else if (window.Qmap.Browser.WebKit || window.Qmap.Browser.Gecko)   //如果是FF,CHROME则提供PLUGIN的下载
            {
                LClickDown = function (wParam, logX, logY, grdX, grdY, grdZ) {
                    obj.LClickDown.call(obj, wParam, logX, logY, grdX, grdY, grdZ, callback);
                };
            }
        })
    },

    //鼠标左键单击弹起事件
    OnLClickUp: function (callback) {
        var obj = this;
        addLoadEvent(function () {
            if (obj.handle == null) return;
            if (window.Qmap.Browser.IE) //如果是IE则提供AX的下载
            {
                obj.handle.attachEvent("OnLButtonUp", function (wParam, logX, logY, grdX, grdY, grdZ) {
                    obj.LClickUp.call(obj, wParam, logX, logY, grdX, grdY, grdZ, callback);
                });
            }
            else if (window.Qmap.Browser.WebKit || window.Qmap.Browser.Gecko)   //如果是FF,CHROME则提供PLUGIN的下载
            {
                LClickUp = function (wParam, logX, logY, grdX, grdY, grdZ) {
                    obj.LClickUp.call(obj, wParam, logX, logY, grdX, grdY, grdZ, callback);
                };
            }

        })
    },

    //鼠标右键单击按下的事件
    OnRClickDown: function (callback) {
        var obj = this;
        addLoadEvent(function () {
            if (obj.handle == null) return;
            if (window.Qmap.Browser.IE) //如果是IE则提供AX的下载
            {
                obj.handle.attachEvent("OnRButtonDown", function (wParam, logX, logY, grdX, grdY, grdZ) {
                    obj.RClickDown.call(obj, wParam, logX, logY, grdX, grdY, grdZ, callback);
                });
            }
            else if (window.Qmap.Browser.WebKit || window.Qmap.Browser.Gecko)   //如果是FF,CHROME则提供PLUGIN的下载
            {
                RClickDown = function (wParam, logX, logY, grdX, grdY, grdZ) {
                    obj.RClickDown.call(obj, wParam, logX, logY, grdX, grdY, grdZ, callback);
                };
            }
        })
    },

    //鼠标右键单击弹起的事件
    OnRClickUp: function (callback) {
        var obj = this;
        addLoadEvent(function () {
            if (obj.handle == null) return;
            if (window.Qmap.Browser.IE) //如果是IE则提供AX的下载
            {
                obj.handle.attachEvent("OnRButtonUp", function (wParam, logX, logY, grdX, grdY, grdZ) {
                    obj.RClickUp.call(obj, wParam, logX, logY, grdX, grdY, grdZ, callback);
                });
            }
            else if (window.Qmap.Browser.WebKit || window.Qmap.Browser.Gecko)   //如果是FF,CHROME则提供PLUGIN的下载
            {
                RClickUp = function (wParam, logX, logY, grdX, grdY, grdZ) {
                    obj.RClickUp.call(obj, wParam, logX, logY, grdX, grdY, grdZ, callback);
                };
            }
        })
    },

    //鼠标左键双击的事件
    OnLDbClick: function (callback) {
        var obj = this;
        addLoadEvent(function () {
            if (obj.handle == null) return;
            if (window.Qmap.Browser.IE) //如果是IE则提供AX的下载
            {
                obj.handle.attachEvent("OnLButtonDBLCLK", function (wParam, logX, logY, grdX, grdY, grdZ) {
                    obj.LDbClick.call(obj, wParam, logX, logY, grdX, grdY, grdZ, callback);
                });
            }
            else if (window.Qmap.Browser.WebKit || window.Qmap.Browser.Gecko)   //如果是FF,CHROME则提供PLUGIN的下载
            {
                LDbClick = function (wParam, logX, logY, grdX, grdY, grdZ) {
                    obj.LDbClick.call(obj, wParam, logX, logY, grdX, grdY, grdZ, callback);
                };
            }
        })
    },


    //鼠标右键双击的事件
    OnRDbClick: function (callback) {
        var obj = this;
        addLoadEvent(function () {
            if (obj.handle == null) return;
            if (window.Qmap.Browser.IE) //如果是IE则提供AX的下载
            {
                obj.handle.attachEvent("OnRButtonDBLCLK", function (wParam, logX, logY, grdX, grdY, grdZ) {
                    obj.RDbClick.call(obj, wParam, logX, logY, grdX, grdY, grdZ, callback);
                });
            }
            else if (window.Qmap.Browser.WebKit || window.Qmap.Browser.Gecko)   //如果是FF,CHROME则提供PLUGIN的下载
            {
                RDbClick = function (wParam, logX, logY, grdX, grdY, grdZ) {
                    obj.RDbClick.call(obj, wParam, logX, logY, grdX, grdY, grdZ, callback);
                };
            }
        })
    },

    //地图鼠标移动事件
    OnMouseMove: function (callback) {
        var obj = this;
        addLoadEvent(function () {
            if (obj.handle == null) return;
            if (window.Qmap.Browser.IE) //如果是IE则提供AX的下载
            {
                obj.handle.attachEvent("OnMouseMove", function (wParam, logX, logY, grdX, grdY, grdZ) {
                    obj.MouseMove.call(obj, wParam, logX, logY, grdX, grdY, grdZ, callback);
                });
            }
            else if (window.Qmap.Browser.WebKit || window.Qmap.Browser.Gecko)   //如果是FF,CHROME则提供PLUGIN的下载
            {
                MouseMove = function (wParam, logX, logY, grdX, grdY, grdZ) {
                    obj.MouseMove.call(obj, wParam, logX, logY, grdX, grdY, grdZ, callback);
                };
            }
        })
    },

    //地图鼠标滚轮事件
    OnMouseWheel: function (callback) {
        var obj = this;
        addLoadEvent(function () {
            if (obj.handle == null) return;
            if (window.Qmap.Browser.IE) //如果是IE则提供AX的下载
            {
                obj.handle.attachEvent("OnMouseWheel", function (wParam, logX, logY, grdX, grdY, grdZ) {
                    obj.MouseWheel.call(obj, wParam, logX, logY, grdX, grdY, grdZ, callback);
                });
            }
            else if (window.Qmap.Browser.WebKit || window.Qmap.Browser.Gecko)   //如果是FF,CHROME则提供PLUGIN的下载
            {
                MouseWheel = function (wParam, logX, logY, grdX, grdY, grdZ) {
                    obj.MouseWheel.call(obj, wParam, logX, logY, grdX, grdY, grdZ, callback);
                };
            }
        })
    },


    //用户通知事件
    OnUserNotify: function (callback) {
        var obj = this;
        addLoadEvent(function () {
            if (obj.handle == null) return;
            if (window.Qmap.Browser.IE) //如果是IE则提供AX的下载
            {
                obj.handle.attachEvent("OnUserNotify", function (type, param) {
                    obj.UserNotify.call(obj, type, param, callback);
                });
            }
            else if (window.Qmap.Browser.WebKit || window.Qmap.Browser.Gecko)   //如果是FF,CHROME则提供PLUGIN的下载
            {
                UserNotify = function (type, param) {
                    obj.UserNotify.call(obj, type, param, callback);
                };
            }

        })
    },
    //菜单选中事件
    OnMenuSelected: function (callback) {
        var obj = this;
        addLoadEvent(function () {
            if (obj.handle == null) return;
            if (window.Qmap.Browser.IE) //如果是IE则提供AX的下载
            {
                obj.handle.attachEvent("OnMenuSelected", function (menuID) {
                    obj.MenuSelected.call(obj, menuID, callback);
                });
            }
            else if (window.Qmap.Browser.WebKit || window.Qmap.Browser.Gecko)   //如果是FF,CHROME则提供PLUGIN的下载
            {
                MenuSelected = function (menuID) {
                    obj.MenuSelected.call(obj, menuID, callback);
                };
            }

        })
    },

    /*--------------------------------------------系统基本设置接口-------------------------------------------------*/
    //3D控件的初始化
    Initialize: function () {
        this.handle.QM_Initialize();
    },
    //设置移动速度  
    SetMoveSpeed: function (speed) {
        this.handle.QM_SetMoveSpeed(speed);
    },
    //设置旋转角度,默认30
    SetRotateSpeed: function (speed) {
        this.handle.QM_SetRotateSpeed(speed);
    },
    //是否启用碰撞检测
    UseCollisionDetect: function (bUse) {
        this.handle.QM_UseCollisionDetect(bUse);
    },
    //遇到障碍物是否会跨越
    UseAutoCalStride: function (bUse) {
        this.handle.QM_UseAutoCalStride(bUse);
    },
    //设置可跨越障碍的高度
    SetMaxStride: function (maxstride) {
        this.handle.QM_SetMaxStride(maxstride);
    },
    //设置雾的颜色
    SetFogColor: function () {
        if (arguments.length == 1) //如果是1个参数，则是"1,2,3"的格式
        {
            var color = arguments[0].split(",");
            this.handle.QM_SetFogColor(color[0], color[1], color[2]);
        } else if (arguments.length == 3) //3个参数则是RGB的格式
        {
            this.handle.QM_SetFogColor(arguments[0], arguments[1], arguments[2]);
        }
    },
    //设置闪烁颜色
    SetFlashColor: function (rgba) {
        var color = rgba.split(",");

        var r = parseInt(color[0], 10) / 255;
        var g = parseInt(color[1], 10) / 255;
        var b = parseInt(color[2], 10) / 255;
        var a = color[3];
        if (isNaN(r) || isNaN(g) || isNaN(b) || isNaN(a))
            alert("颜色透明值格式有误！");
        else
            this.handle.QM_SetFlashColor(r, g, b, a);
    },
    //设置选中物体颜色   a,透明度（0-1），0完全透明。1表示完全不透明
    SetSelectInfo: function (rgba) {
        var color = rgba.split(",");

        var r = parseInt(color[0], 10) / 255;
        var g = parseInt(color[1], 10) / 255;
        var b = parseInt(color[2], 10) / 255;
        var a = color[3];
        if (isNaN(r) || isNaN(g) || isNaN(b) || isNaN(a))
            alert("颜色透明值格式有误！");
        else
            this.handle.QM_SetSelectInfo(r, g, b, a);
    },
    //设置地形默认纹理平铺个数
    SetDefaultTexTileCount: function (count) {                //  
        this.handle.QM_SetDefaultTexTileCount(count);
    },
    //设置是否显示纹理，默认1， 1显示，0不显示。
    SetDisplayTexture: function (bDisplayTexture) {
        this.handle.QM_SetDisplayTexture(bDisplayTexture);
    },
    //设置骨架显示，默认0，1显示，0不显示。
    SetDisplayWireFrame: function (bDisplayWireFrame) {
        this.handle.QM_SetDisplayWireFrame(bDisplayWireFrame);
    },
    //设置系统中所使用的类型
    //int, 0:经纬度坐标，1:平面坐标
    SetCoordType: function (type) {
        this.handle.QM_SetCoordType(type);
    },
    //返回系统中所使用的类型
    GetCoordType: function () {
        return this.handle.QM_GetCoordType();
    },
    /*----------------------------------------------=====END=====--------------------------------------------------*/



    /*-----------------------------------------------地图操作接口--------------------------------------------------*/
    //移动到坐标位置
    //经度，高度，纬度
    MoveTo: function (Longitude, Altitude, Latitude) {
        this.handle.QM_MoveTo(Longitude, Latitude, Altitude);
    },
    //设置鼠标操作方式
    SetMouseOper: function (button, opertype) {
        this.handle.QM_SetMouseOper(button, opertype);
    },
    //设置当前视图类型
    SetViewType: function (type) {
        this.handle.QM_SetViewType(type);
    },
    //获取当前视图类型
    GetViewType: function () {
        return this.handle.QM_GetViewType();
    },
    //重新计算模型可见性
    Refresh: function () {
        this.handle.QM_Refresh();
    },
    //重新绘制地图
    Redraw: function () {
        this.handle.QM_Redraw();
    },
    //获取当前位置
    GetRolePosition: function () {
        var pos = this.handle.QM_GetRolePosition();
        var obj = null;
        if (pos != null) {
            obj = new Qmap.Object3D.QMap3DVector3D();
            obj.handle = pos;
        }
        return obj;
    },
    //设置当前水平方向和垂直方向的角度
    SetViewAngle: function (HAngle, VAngle) {
        this.handle.QM_SetViewAngle(HAngle, VAngle);
    },
    //设置包围盒测试
    SetCameraBox: function (min_x, min_y, min_z, max_x, max_y, max_z) {
        this.handle.QM_SetCameraBox(min_x, min_y, min_z, max_x, max_y, max_z);
    },
    //取消包围盒测试
    CancerCameraBox: function () {
        this.handle.QM_CancerCameraBox();
    },
    //设置角色的宽度
    SetRoleWidth: function (rolewidth) {
        this.handle.QM_SetRoleWidth(rolewidth);
    },
    //获取当前角色的角度
    GetRoleAngle: function () {
        var pos = this.handle.QM_GetRoleAngle();
        var obj = null;
        if (pos != null) {
            obj = new Qmap.Object3D.QMap3DVector3D();
            obj.handle = pos;
        }
        return obj;
    },
    /*----------------------------------------------=====END=====--------------------------------------------------*/


    /*-----------------------------------------------飞行漫游接口--------------------------------------------------*/
    //创建一个飞行漫游对象
    CreateRoute: function () {
        return new Qmap.Object3D.QMapRoute();
    },
    //移动到模型实例
    GotoModelInstance: function (layer, strID, dLongitude, dAltitude, dLatitude, type) {
        this.handle.QM_GotoModelInstance(layer, strID, dLongitude, dLatitude, dAltitude, type);
    },
    //飞行到模型实例
    FlytoModelInstance: function (layer, strID, dLongitude, dAltitude, dLatitude, type, height) {
        this.handle.QM_FlytoModelInstance(layer, strID, dLongitude, dLatitude, dAltitude, type, height);
    },
    //飞行到某个位置
    FlytoPosition: function (dLongitude, dAltitude, dLatitude, hAngle, vAngle, dHeight) {
        this.handle.QM_FlytoPosition(dLongitude, dLatitude, dAltitude, hAngle, vAngle, dHeight);
    },
    //设定相机跟踪观察某个模型
    SetWatchingModel: function (layer, strID, eyePosX, eyePosY, eyePosZ, lookAtPosX, lookAtPosY, LookAtPosZ) {
        return this.handle.QM_SetWatchingModel(layer, strID, eyePosX, eyePosY, eyePosZ, lookAtPosX, lookAtPosY, LookAtPosZ);
    },
    //取消设定相机跟踪观察
    UnsetWatchingModel: function () {
        this.handle.QM_UnsetWatchingModel();
    },
    /*----------------------------------------------=====END=====--------------------------------------------------*/




    /*-----------------------------------------------弹出窗口接口---------------------------------------------------*/
    //显示用户自定义窗口
    ShowUserWnd: function (lImgIdx, szUrlPath, lTransType, lTransVal, cTransColor, lShowX, lShowY, lCloseX, lCloseY, lCloseWidth, lCloseHeight, lClientX, lClientY, lClientWidth, lClientHeight) {
        var bColor = cTransColor.split(",");
        return this.handle.QM_ShowUserWnd(lImgIdx, szUrlPath, lTransType, lTransVal, Qmap.Util.RGB(bColor[0], bColor[1], bColor[2]), lShowX, lShowY, lCloseX, lCloseY, lCloseWidth, lCloseHeight, lClientX, lClientY, lClientWidth, lClientHeight);
    },
    //显示用户自定义窗口(多窗口)
    ShowUserWndByID: function (nID, lImgIdx, szUrlPath, lTransType, lTransVal, cTransColor, lShowX, lShowY, lCloseX, lCloseY, lCloseWidth, lCloseHeight, lClientX, lClientY, lClientWidth, lClientHeight) {
        var bColor = cTransColor.split(",");
        return this.handle.QM_ShowUserWndByID(nID, lImgIdx, szUrlPath, lTransType, lTransVal, Qmap.Util.RGB(bColor[0], bColor[1], bColor[2]), lShowX, lShowY, lCloseX, lCloseY, lCloseWidth, lCloseHeight, lClientX, lClientY, lClientWidth, lClientHeight);
    },
    //关闭用户自定义窗口
    CloseUserWnd: function () {
        this.handle.QM_CloseUserWnd();
    },
    //关闭用户自定义窗口(多窗口)
    CloseUserWndByID: function (nID) {
        this.handle.QM_CloseUserWndByID(nID);
    },
    //执行脚本函数
    ExecScript: function (nID, script, language) {
        this.handle.QM_ExecScript(nID, script, language);
    },
    //获取当前弹出窗口的位置信息
    GetUserWndPos: function () {
        return this.handle.QM_GetUserWndPos();
    },
    /*-----------------------------------------------=====END=====--------------------------------------------------*/


    /*-----------------------------------------------弹出菜单接口---------------------------------------------------*/
    //初始化弹出菜单
    InitPopMenu: function (szUrl, lTransVal) {
        return this.handle.QM_InitPopMenu(szUrl, lTransVal);
    },
    //操作菜单行为
    PopupMenu: function (szFunc, szScriptType) {
        return this.handle.QM_PopupMenu(szFunc, szScriptType);
    },
    //弹出菜单传递参数信息
    SelectMenuItem: function (szMenuItem) {
        return this.handle.QM_SelectMenuItem(szMenuItem);
    },
    //在菜单上添加一个菜单
    AddMenuItem: function (strPic, strLabel, intWidth, intHeight, intAlpha) {
        this.handle.QM_AddMenuItem(strPic, strLabel, intWidth, intHeight, intAlpha);
    },
    //删除一个添加的菜单
    DeleteMenuItem: function (strLabel) {
        this.handle.QM_DeleteMenuItem(strLabel);
    },
    //使菜单在弹出窗口中显示
    ShowMenu: function (intX, intY) {
        this.handle.QM_ShowMenu(intX, intY);
    },
    //关闭菜单
    CloseMenu: function () {
        this.handle.QM_CloseMenu();
    },
    /*-----------------------------------------------=====END=====--------------------------------------------------*/


    /*-----------------------------------------------灯光处理接口---------------------------------------------------*/
    //向场景中添加一个灯光
    AddLight: function (strName, intType, intId) {
        return this.handle.QM_AddLight(strName, intType, intId);
    },
    //得到灯光数量
    GetLightCount: function () {
        return this.handle.QM_GetLightCount();
    },
    //设置灯光位置
    SetLightPos: function (nLight, x, y, z) {
        this.handle.QM_SetLightPos(nLight, x, y, z);
    },
    //获取灯光位置
    GetLightPos: function (nLight) {
        var pos = this.handle.QM_GetLightPos(nLight);
        var obj = null;
        if (pos != null) {
            obj = new Qmap.Object3D.QMapData4D();
            obj.handle = pos;
        }
        return obj;
    },
    //设置环境光
    SetLightAmbient: function (nLight, rgba) {
        var color = rgba.split(",");
        var r = parseInt(color[0], 10) / 255;
        var g = parseInt(color[1], 10) / 255;
        var b = parseInt(color[2], 10) / 255;
        var a = color[3];
        if (isNaN(r) || isNaN(g) || isNaN(b) || isNaN(a))
            alert("颜色透明值格式有误！");
        else
            this.handle.QM_SetLightAmbient(nLight, r, g, b, a);
    },
    //获取环境光
    GetLightAmbient: function (nLight) {
        var ambient = this.handle.QM_GetLightAmbient(nLight);
        var obj = null;
        if (ambient != null) {
            obj = new Qmap.Object3D.QMapData4D();
            obj.handle = ambient;
        }
        return obj;
    },
    //设置散射光
    SetLightDiffuse: function (nLight, rgba) {
        var color = rgba.split(",");
        var r = parseInt(color[0], 10) / 255;
        var g = parseInt(color[1], 10) / 255;
        var b = parseInt(color[2], 10) / 255;
        var a = color[3];
        if (isNaN(r) || isNaN(g) || isNaN(b) || isNaN(a))
            alert("颜色透明值格式有误！");
        else
            this.handle.QM_SetLightDiffuse(nLight, r, g, b, a);
    },
    //获取散射光
    GetLightDiffuse: function (nLight) {
        var diffuse = this.handle.QM_GetLightDiffuse(nLight);
        var obj = null;
        if (diffuse != null) {
            obj = new Qmap.Object3D.QMapData4D();
            obj.handle = diffuse;
        }
        return obj;
    },
    //设置反射光
    SetLightSpecular: function (nLight, rgba) {
        var color = rgba.split(",");
        var r = parseInt(color[0], 10) / 255;
        var g = parseInt(color[1], 10) / 255;
        var b = parseInt(color[2], 10) / 255;
        var a = color[3];
        if (isNaN(r) || isNaN(g) || isNaN(b) || isNaN(a))
            alert("颜色透明值格式有误！");
        else
            this.handle.QM_SetLightSpecular(nLight, r, g, b, a);
    },
    //获取反射光
    GetLightSpecular: function (nLight) {
        var specular = this.handle.QM_GetLightSpecular(nLight);
        var obj = null;
        if (specular != null) {
            obj = new Qmap.Object3D.QMapData4D();
            obj.handle = specular;
        }
        return obj;
    },
    //获取灯光的ID号
    GetLightId: function (nIdx) {
        return this.handle.QM_GetLightId(nIdx);
    },
    //根据ID获得灯光的名称
    GetLightName: function (id) {
        return this.handle.QM_GetLightName(id);
    },
    //根据ID设置灯光的方向（ID,经度，高度，纬度）
    SetLightDirection: function (intID, floatX, floatY, floatZ) {
        this.handle.QM_SetLightDirection(intID, floatX, floatY, floatZ);
    },
    //根据ID获得灯光的方向
    GetLightDirection: function (intID, intIdx) {
        return this.handle.QM_GetLightDirection(intID, intIdx);
    },
    //设置灯光照射范围
    SetLightRange: function (intID, floatRange) {
        this.handle.QM_SetLightRange(intID, floatRange);
    },
    //根据ID获得灯光照射范围
    GetLightRange: function (intID) {
        return this.handle.QM_GetLightRange(intID);
    },
    //根据ID设置聚灯光的按锥角衰减指数。范围0~128
    SetLightFallOff: function (intID, floatFallOff) {
        this.handle.QM_SetLightFallOff(intID, floatFallOff);
    },
    //根据ID得到聚光灯的锥角衰减指数
    GetLightFallOff: function (intID) {
        return this.handle.QM_GetLightFallOff(intID);
    },
    //根据ID设置聚光灯的椎体角
    SetLightPhi: function (intID, floatPhi) {
        this.handle.QM_SetLightPhi(intID, floatPhi);
    },
    //根据ID获取聚光灯的椎体角
    GetLightPhi: function (intID) {
        return this.handle.QM_GetLightPhi(intID);
    },
    //根据ID号设置灯光的按距离衰减指数
    SetLightAttenuation0: function (intID, floatA0) {
        this.handle.QM_SetLightAttenuation0(intID, floatA0);
    },
    //根据id获取灯光的按距离衰减指数
    GetLightAttenuation0: function (intID) {
        return this.handle.QM_GetLightAttenuation0(intID);
    },
    //根据ID号设置灯光的按距离衰减指数
    SetLightAttenuation1: function (id, a1) {
        this.handle.QM_SetLightAttenuation1(id, a1);
    },
    //根据id获取灯光的按距离衰减指数
    GetLightAttenuation1: function (id) {
        return this.handle.QM_GetLightAttenuation1(id);
    },
    //根据ID号设置灯光的按距离衰减指数
    SetLightAttenuation2: function (id, a2) {
        this.handle.QM_SetLightAttenuation2(id, a2);
    },
    //根据id获取灯光的按距离衰减指数
    GetLightAttenuation2: function (id) {
        return this.handle.QM_GetLightAttenuation2(id);
    },
    //是否启用镜头跟随   先设置镜头跟随，再设置灯光位置
    EnableCamFallow: function (intLight, boolEnable) {
        this.handle.QM_EnableCamFallow(intLight, boolEnable);
    },
    //是否启用灯光
    EnableLight: function (nLight, bEnable) {
        this.handle.QM_EnableLight(nLight, bEnable);
    },
    //设置一个投射阴影的全局灯光
    SetShadowLight: function (nLightID) {
        this.handle.QM_SetShadowLight(nLightID);
    },
    //获取当前投射阴影的全局灯光ID
    GetShadowLight: function () {
        return this.handle.QM_SetShadowLight();
    },
    /*-----------------------------------------------=====END=====--------------------------------------------------*/


    /*-----------------------------------------------图层处理接口---------------------------------------------------*/
    //获取层数量
    GetLayerCount: function () {
        return this.handle.QM_GetLayerCount();
    },
    //获取层名称
    GetLayerName: function (intLayer) {
        return this.handle.QM_GetLayerName(intLayer);
    },
    //设置层是否显示
    SetLayerVisible: function (layer, bVisible) {
        return this.handle.QM_SetLayerVisible(layer, bVisible);
    },
    //获取层是否显示
    GetLayerVisible: function (layer) {
        return this.handle.QM_GetLayerVisible(layer);
    },
    /*-----------------------------------------------=====END=====--------------------------------------------------*/


    /*-----------------------------------------------动态模型处理---------------------------------------------------*/
    //创建3D模型
    CreateObject: function (name) {
        return this.handle.QM_CreateObject(name);
    },

    //添加3D模型,QMap3DObject对象
    Append3DObject: function (obj) {
        if (obj instanceof Qmap.Object3D.QMap3DObject)
            this.handle.QM_Append3DObject(obj.handle);
        else
            alert("传入的参数不是Qmap.Object3D.QMap3DModel类型");
    },
    //添加3D模型实例,QMap3DModelInstance对象。
    AppendModelInstance: function (layer, obj) {
        if (obj instanceof Qmap.Object3D.QMap3DModelInstance)
            this.handle.QM_AppendModelInstance(layer, obj.handle);
        else
            alert("传入的参数不是Qmap.Object3D.QMap3DModelInstance类型");
    },
    //添加矢量模型QM_AppendVectorModel //QMap3DVectorLine,QMap3DVectorLine,QMap3DVectorPipe,QMap3DVectorCanal,QMap3DVectorWater,QMap3DVectorWaterRoad,QMap3DVectorPOI,QMap3DVector3DPoly,QMap3DVectorAd,QMap3DVectorPlane,QMap3DVectorCross,QMap3DVectorCuboid,QMap3DVectorColumn,QMap3DVectorTaper,QMap3DVectorSphere,QMap3DVectorMillArrow
    AppendVectorModel: function (strLayer, obj) {
        //        if (obj instanceof Qmap.Object3D.QMap3DVectorLine
        //                || obj instanceof Qmap.Object3D.QMap3DVectorPipe
        //                || obj instanceof Qmap.Object3D.QMap3DVectorCanal
        //                || obj instanceof Qmap.Object3D.QMap3DVectorWater
        //                || obj instanceof Qmap.Object3D.QMap3DVectorWaterRoad
        //                || obj instanceof Qmap.Object3D.QMap3DVectorPOI
        //                || obj instanceof Qmap.Object3D.QMap3DVector3DPoly
        //                || obj instanceof Qmap.Object3D.QMap3DVectorAd
        //                || obj instanceof Qmap.Object3D.QMap3DVectorPlane
        //                || obj instanceof Qmap.Object3D.QMap3DVectorCross
        //                || obj instanceof Qmap.Object3D.QMap3DVectorCuboid
        //                || obj instanceof Qmap.Object3D.QMap3DVectorColumn
        //                || obj instanceof Qmap.Object3D.QMap3DVectorTaper
        //                || obj instanceof Qmap.Object3D.QMap3DVectorSphere
        //                || obj instanceof Qmap.Object3D.QMap3DVectorMillArrow) {
        //            this.handle.QM_AppendVectorModel(strLayer, obj.handle);
        //        }
        //        else
        //            alert("传入的参数不是矢量类型");
        this.handle.QM_AppendVectorModel(strLayer, obj.handle);
    },
    //获取模型实例
    GetModelInstance: function (strLayer, id) {
        return this.handle.QM_GetModelInstance(strLayer, id);
    },
    //删除模型实例
    DeleteModelInstance: function (strLayer, strID) {
        return this.handle.QM_DeleteModelInstance(strLayer, strID);
    },
    //设置是否显示纹理
    SetModelInstanceNShowTexture: function (layer, strID, bDisplayTexture) {
        this.handle.QM_SetModelInstanceNShowTexture(layer, strID, bDisplayTexture);
    },
    //设置模型实例骨架是否显示
    SetModelInstanceWireFrame: function (layer, strID, bDisplayWireFrame) {
        this.handle.QM_SetModelInstanceWireFrame(layer, strID, bDisplayWireFrame);
    },
    //设置模型实例是否显示
    SetModelInstanceVisible: function (layer, strID, bHide) {
        this.handle.QM_SetModelInstanceVisible(layer, strID, bHide);
    },
    //设置模型实例是否透明
    SetModelInstanceTransparent: function (layer, strID, bHide, tVal) {
        this.handle.QM_SetModelInstanceTransparent(layer, strID, bHide, tVal);
    },
    //设置模型实例是否闪烁。
    SetModelInstanceFlash: function (layer, strID, bFlash) {
        this.handle.QM_SetModelInstanceFlash(layer, strID, bFlash);
    },
    //设置模型是否处于选中状态
    SetModelInstanceSelected: function (layer, strID, bFlash) {
        this.handle.QM_SetModelInstanceSelected(layer, strID, bFlash);
    },
    //设置模型实例的标签文字
    SetModelInstanceTag: function (layer, strID, strTag) {
        this.handle.QM_SetModelInstanceTag(layer, strID, strTag);
    },
    //设置模型实例的标签类型
    SetModelInstanceTagType: function (type) {
        this.handle.QM_SetModelInstanceTagType(type);
    },
    //设置模型实例的位置
    SetModelInstancePos: function (layer, strID, x, y, z) {
        this.handle.QM_SetModelInstancePos(layer, strID, x, y, z);
    },
    //设置模型实例的旋转角度
    SetModelInstanceRotate: function (layer, strID, x, y, z) {
        this.handle.QM_SetModelInstanceRotate(layer, strID, x, y, z);
    },
    //设置模型实例的大小 x,y,x为个方向长度
    SetModelInstanceSize: function (layer, strID, x, y, z) {
        this.handle.QM_SetModelInstanceSize(layer, strID, x, y, z);
    },
    //设置模型实例的材质纹理
    SetModelInstancetMaterial: function (layer, strID, Ambient, Diffuse, Specular, shiness, texture) {
        this.handle.QM_SetModelInstancetMaterial(layer, strID, Ambient, Diffuse, Specular, shiness, texture);
    },
    //设置模型实例的动态属性
    SetModelInstancetDynamic: function (layer, strID, pDynamic) {
        this.handle.QM_SetModelInstancetDynamic(layer, strID, pDynamic.handle);
    },
    //选择模型
    SelectModel: function (x1, y1, x2, y2) {
        return this.handle.QM_SelectModel(x1, y1, x2, y2);
    },
    //取消所有模型的选中状态
    UnSelectAll: function () {
        this.handle.QM_UnSelectAll();
    },
    /*-----------------------------------------------=====END=====--------------------------------------------------*/


    /*-----------------------------------------------地形处理接口---------------------------------------------------*/
    //在地图上开洞
    AddHole: function (intID, strCoords, intCount) {
        return this.handle.QM_AddHole(intID, strCoords, intCount);
    },
    //删除洞 
    DelHole: function (intID) {
        this.handle.QM_DelHole(intID);
    },
    //删除所有的洞
    DelAllHole: function () {
        this.handle.QM_DelAllHole();
    },
    /*-----------------------------------------------=====END=====--------------------------------------------------*/


    /*----------------------------------------------自动水面处理接口------------------------------------------------*/
    //是否启用自动水面  1启用，0不启用
    SetAutoWaterSurface: function (boolEnable) {
        this.handle.QM_SetAutoWaterSurface(boolEnable);
    },
    //设置自动水面的高度
    SetAutoWaterSurfaceHei: function (dHeight) {
        this.handle.QM_SetAutoWaterSurfaceHei(dHeight);
    },
    //是否启动水中的模糊效果
    SetAutoWaterSurfaceFog: function (bEnable) {
        this.handle.QM_SetAutoWaterSurfaceFog(bEnable);
    },
    //设置水中效果模糊的颜色 rgba
    SetAutoWaterSurfaceFogColor: function (rgba) {
        var color = rgba.split(",");
        var r = parseInt(color[0], 10) / 255;
        var g = parseInt(color[1], 10) / 255;
        var b = parseInt(color[2], 10) / 255;
        var a = color[3];
        if (isNaN(r) || isNaN(g) || isNaN(b) || isNaN(a))
            alert("颜色透明值格式有误！");
        else
            this.handle.QM_SetAutoWaterSurfaceFogColor(r, g, b, a);
    },
    /*-----------------------------------------------=====END=====---------------------------------------------------*/


    /*-----------------------------------------------FLASH控件处理---------------------------------------------------*/
    //添加flash动画到控件中
    AddFlashCtrl: function (flashName, flashFile, alignMode, left, top, width, height, loop, flashType) {
        this.handle.QM_AddFlashCtrl(flashName, flashFile, alignMode, left, top, width, height, loop, flashType);
    },
    //设置flash的Rect
    SetFlashRect: function (flashName, alignMode, intLeft, intTop, intWidth, height, intHeight) {
        this.handle.QM_SetFlashRect(flashName, alignMode, intLeft, intTop, intWidth, height, intHeight);
    },
    //设置flash是否可见
    SetFlashCtrlVisibility: function (flashName, status) {
        this.handle.QM_SetFlashCtrlVisibility(flashName, status);
    },
    //设置flash播放状态
    SetFlashPlayStatus: function (flashName, status) {
        this.handle.QM_SetFlashPlayStatus(flashName, status);
    },
    //删除flash
    DeleteFlashCtrl: function (flashName) {
        this.handle.QM_DeleteFlashCtrl(flashName);
    },
    //获取flash中变量的值
    GetFlashVaribleInfo: function (flashName, varibleName) {
        return this.handle.QM_GetFlashVaribleInfo(flashName, varibleName);
    },
    //设置flash中变量的值
    SetFlashVaribleInfo: function (flashName, varibleName, varibleValue) {
        this.handle.QM_SetFlashVaribleInfo(flashName, varibleName, varibleValue);
    },
    //设置能否flash回调js方法 0不允许，1允许
    SetFlashCallJavascript: function (bEnable) {
        this.handle.QM_SetFlashCallJavascript(bEnable);
    },
    //调用flash中的函数
    CallFlashFunction: function (flashName, funcName, format, args) {
        return this.handle.QM_CallFlashFunction(flashName, funcName, format, args);
    },
    //设置flash中的vars
    SetFlashVars: function (flashName, flashVars) {
        this.handle.QM_SetFlashVars(flashName, flashVars);
    },
    /*-----------------------------------------------=====END=====--------------------------------------------------*/


    /*------------------------------------------屏幕录影，处理场景录影操作------------------------------------------*/
    //开始屏幕录像
    StartRecord: function (file, showCursor, isCompress) {
        this.handle.QM_StartRecord(file, showCursor, isCompress);
    },
    //结束屏幕录像 1成功，0失败
    EndRecord: function () {
        return this.handle.QM_EndRecord();
    },
    //将地图窗口保存成图片
    SaveImage: function (url) {
        return this.handle.QM_SaveImage(url);
    },
    /*-----------------------------------------------=====END=====--------------------------------------------------*/

    /*-----------------------------------------------粒子系统处理接口和其他接口---------------------------------------------------*/
    //粒子处理
    AppendParticleInstance: function (layer, objParticle) {
        return this.handle.QM_AppendParticleInstance(layer, objParticle.handle);
    },
    //上传文件到服务器
    FtpUploadFile: function (server, port, user, pass, rfilename, lfilename) {
        return this.handle.QM_FtpUploadFile(server, port, user, pass, rfilename, lfilename);
    },
    /*-----------------------------------------------=====END=====--------------------------------------------------*/


    /*-----------------------------------------------控件编辑功能---------------------------------------------------*/
    //保存当期场景
    SaveScene: function () {
        return this.handle.QM_SaveScene();
    },
    //重新生成场景
    CreateIndex: function () {
        return this.handle.QM_CreateIndex();
    },
    /*-----------------------------------------------=====END=====--------------------------------------------------*/

    SetHawkViewPortHeight: function (ViewPortHeight) {
        this.handle.QM_SetHawkViewPortHeight(ViewPortHeight);
    },
    EnableMiFadein: function (bEnable) {
        this.handle.QM_EnableMiFadein(bEnable);
    }

    //-----------------------------------------------去除掉的方法@3.1.2012------------------------------------------
    //    SetLightCount:function(count){
    //        this.handle.QM_SetLightCount(count);
    //    },
    //    AppendDynamicLine:function(obj){
    //        if(obj instanceof Qmap.Object3D.QMap3DDynamicLine)
    //            this.handle.QM_AppendDynamicLine(obj.handle);
    //        else
    //            alert("传入的参数不是Qmap.Object3D.QMap3DDynamicLine类型");
    //    },
    //    AppendDynamicPipe:function(obj){
    //        if(obj instanceof Qmap.Object3D.QMap3DDynamicPipe)
    //            this.handle.QM_AppendDynamicPipe(obj.handle);
    //        else
    //            alert("传入的参数不是Qmap.Object3D.QMap3DDynamicPipe类型");
    //    },
    //    GetLightSpecular:function(nLight){
    //        var specular = this.handle.QM_GetLightSpecular(nLight);
    //        var obj =null;
    //        if(specular!=null)
    //        {
    //            obj = new Qmap.Object3D.QMapData4D();
    //            obj.handle = specular;
    //        }
    //        return obj;
    //    }
    //-----------------------------------------------=====END=====-------------------------------------------------- 
});


//Namespace:Object3D
Qmap.Object3D = {

};

///qmap各种元素的基类
Qmap.Object3D.Base = Qmap.Class({
    //BaseMethod:["CursorID","ToolTipText"],
    GetObjType: function () {
        return this.handle.GetObjType();
    },

    //绑定默认参数到参数列表中
    initialize: function () {
        //        var baseOptions={
        //            "cursorid":this.BaseMethod[0],
        //            "tooltiptext":this.BaseMethod[1]
        //        };
        //        Qmap.Util.extend(this.options,baseOptions);
    },
    //初始化参数值
    bindingValue: function (options) {
        for (var property in options) {
            var value = options[property]; //得到值
            try {
                this["Set" + this.options[property.toLowerCase()]](value); //进行赋值
            } catch (E) {
                alert("错误的参数名: " + property);
            }
        }
    },
    createObj: function (objName) {   //根据浏览器创建具体的图形对象
        if (window.Qmap.Browser.IE) {
            this.handle = new ActiveXObject(objName);
        } else {
            if (Qmap.arrMap.length <= 0) {
                alert("请先定义地图对象");
                return;
            }
            else {
                this.handle = Qmap.arrMap[0].QM_CreateObject(objName);
            }
        }
    }
});

///列表类的基类
Qmap.Object3D.ListBase = Qmap.Class({
    _each: function (iterator) {
        // 迭代代码，每次循环时调用 iterator
        // 最基本的迭代算法
        for (var i = 0, length = this.list.length; i < length; i++)
            iterator(this[i]);
    },
    //初始化的构造函数
    initialize: function (options) {
        this.list = options; //为内部的list赋值
        var size = options.length;
        for (var i = 0; i < size; i++) {
            this[i] = options[i];
        }
    }
});

//QMap3DVector3D对象
Qmap.Object3D.QMap3DVector3D = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["X", "Y", "Z"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DShapeObject.QMap3DVector3D");
        this.init(options);
    },

    init: function (options) {
        this.options = {
            "x": this.Method[0],
            "y": this.Method[1],
            "z": this.Method[2]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    //经度属性
    SetX: function (x) {
        this.handle.x = x;
    },
    ///高度属性
    SetY: function (y) {
        this.handle.y = y;
    },
    ///纬度属性
    SetZ: function (z) {
        this.handle.z = z;
    },
    ///返回经度属性
    GetX: function () {
        return this.handle.x;
    },
    ///返回高度属性
    GetY: function () {
        return this.handle.y;
    },
    ///返回纬度属性
    GetZ: function () {
        return this.handle.z;
    }
});

Qmap.Object3D.QMap3DVector3DList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});



//QMap3DVector4D对象
Qmap.Object3D.QMap3DVector4D = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["X", "Y", "Z", "Angle", "RotateX", "RotateY", "RotateZ"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DShapeObject.QMap3DVector4D");
        this.init(options);
    },

    init: function (options) {
        this.options = {
            "x": this.Method[0],
            "y": this.Method[1],
            "z": this.Method[2],
            "angle": this.Method[3],
            "rotatex": this.Method[4],
            "rotatey": this.Method[5],
            "rotatez": this.Method[6]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    ///经度属性
    SetX: function (x) {
        this.handle.x = x;
    },
    ///纬度属性
    SetY: function (y) {
        this.handle.z = y;
    },
    ///高度属性
    SetZ: function (z) {
        this.handle.y = z;
    },
    ///旋转角度属性
    SetAngle: function (angle) {
        this.handle.angle = angle;
    },
    ///经度上的旋转角度属性
    SetRotateX: function (rotatex) {
        this.handle.rotatex = rotatex;
    },
    ///高度上的旋转角度属性
    SetRotateY: function (rotatey) {
        this.handle.rotatey = rotatey;
    },
    ///纬度上的旋转角度属性
    SetRotateZ: function (rotatez) {
        this.handle.rotatez = rotatez;
    },
    ///返回经度属性
    GetX: function () {
        return this.handle.x;
    },
    ///返回纬度属性
    GetY: function () {
        return this.handle.z;
    },
    ///返回高度属性
    GetZ: function () {
        return this.handle.y;
    },

    ///旋转角度属性
    GetAngle: function () {
        return this.handle.angle;
    },
    ///经度上的旋转角度属性
    GetRotateX: function (rotatex) {
        return this.handle.rotatex;
    },
    ///高度上的旋转角度属性
    GetRotateY: function (rotatey) {
        return this.handle.rotatey;
    },
    ///经度上的旋转角度属性
    GetRotateZ: function (rotatez) {
        return this.handle.rotatez;
    }
});

Qmap.Object3D.QMap3DVector4DList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});



//QMap3DLine对象
Qmap.Object3D.QMap3DLine = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["Vector3D", "Type", "Kind", "Color"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DShapeObject.QMap3DLine");
        this.init(options);
    },

    init: function (options) {
        this.options = {
            "vector3d": this.Method[0],
            "type": this.Method[1],
            "kind": this.Method[2],
            "color": this.Method[3]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    ///设置线类型，折线，样条线
    SetType: function (val) {
        this.handle.Type = val;
    },
    ///设置线型，实线，曲线
    SetKind: function (val) {
        this.handle.Kind = val;
    },
    ///线色
    SetColor: function (val) {
        this.handle.Color = val;
    },
    GetType: function () {
        return this.handle.Type;
    },
    GetKind: function () {
        return this.handle.Kind;
    },
    GetColor: function () {
        return this.handle.Color;
    },
    ///得到点的数量
    GetCount: function () {
        return this.handle.GetCount();
    },
    ///清空点集
    Clear: function () {
        this.handle.Clear();
    },
    ///通过序号得到具体的Vector3D对象ID
    GetVector3D: function (nIdx) {
        this.handle.GetVector3D(nIdx);
    },
    //用于增加QMap3DLine对象的坐标点
    SetVector3D: function (value) {
        if (Object.isArray(value)) {
            for (var i = 0; i < value.length; i++) {
                var parms = value[i];
                this.handle.AddVector3D(parms);
            }
        } else if (Object.isString(value)) {
            if (value.indexOf(";") > -1)//说明是用;进行分割的
            {
                var parmsList = value.split(";");
                for (var i = 0, length = parmsList.length; i < length; i++) {
                    var parms = parmsList[i];
                    this.handle.AddVector3D(parms);
                }

            } else //用,进行分割的
            {
                var parmsList = value.split(",");
                for (var i = 0, length = parmsList.length; i < length; i += 1) {
                    this.handle.AddVector3D(parmsList[i]);
                }
            }
        } else {
            alert("参数必须是字符串，或者数组!");
        }
    }
});

Qmap.Object3D.QMap3DLineList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});



//QMap3DMaterial对象
Qmap.Object3D.QMap3DMaterial = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["Ambient", "Diffuse", "Specular", "Name", "Shininess", "Emissive", "TexColor", "EnableLighting", "EnableFog", "TexSource",
    "TexDiff", "TexDiff2", "TexNormal", "TexSpecular", "TexAnimate", "EnvTexData"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DShapeObject.QMap3DMaterial");
        this.init(options);
    },

    init: function (options) {
        this.options = {
            "ambient": this.Method[0],
            "diffuse": this.Method[1],
            "specular": this.Method[2],
            "name": this.Method[3],
            "shininess": this.Method[4],
            "emissive": this.Method[5],
            "texcolor": this.Method[6],
            "enablelighting": this.Method[7],
            "enablefog": this.Method[8],
            "texsource": this.Method[9],
            "texdiff": this.Method[10],
            "texdiff2": this.Method[11],
            "texnormal": this.Method[12],
            "texspecular": this.Method[13],
            "texanimate": this.Method[14],
            "envtexdata": this.Method[15]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetAmbient: function (rgba) {
        var color = rgba.split(',');
        this.handle.SetAmbient(color[0], color[1], color[2], color[3]);
    },
    GetAmbient: function () {
        return this.handle.GetAmbient();
    },
    SetDiffuse: function (rgba) {
        var color = rgba.split(',');
        this.handle.SetDiffuse(color[0], color[1], color[2], color[3]);
    },
    GetDiffuse: function () {
        return this.handle.GetDiffuse();
    },
    SetSpecular: function (rgba) {
        var color = rgba.split(',');
        this.handle.SetSpecular(color[0], color[1], color[2], color[3]);
    },
    GetSpecular: function () {
        return this.handle.GetSpecular();
    },
    SetName: function (strName) {
        this.handle.SetName(strName);
    },
    GetName: function () {
        return this.handle.GetName();
    },
    SetShininess: function (val) {
        this.handle.Shininess = val;
    },
    GetShininess: function () {
        return this.handle.Shininess;
    },
    SetEmissive: function (rgba) {
        var color = rgba.split(',');
        this.handle.SetEmissive(color[0], color[1], color[2], color[3]);
    },
    GetEmissive: function () {
        return this.handle.GetEmissive();
    },
    SetTexColor: function (rgba) {
        var color = rgba.split(',');
        this.handle.SetTexColor(color[0], color[1], color[2], color[3]);
    },
    GetTexColor: function () {
        return this.handle.GetTexColor();
    },
    SetEnableLighting: function (val) {
        this.handle.EnableLighting = val;
    },
    GetEnableLighting: function () {
        return this.handle.EnableLighting;
    },
    SetEnableFog: function (val) {
        this.handle.EnableFog = val;
    },
    GetEnableFog: function () {
        return this.handle.EnableFog;
    },
    SetTexDiff: function (TexUnit) {
        this.handle.SetTexDiff(TexUnit);
    },
    GetTexSource: function () {
        return this.handle.GetTexSource();
    },
    SetTexDiff: function (TexUnit) {
        this.handle.SetTexDiff(TexUnit);
    },
    GetTexDiff: function () {
        return this.handle.GetTexDiff();
    },
    SetTexDiff2: function (TexUnit) {
        this.handle.SetTexDiff2(TexUnit);
    },
    GetTexDiff2: function () {
        return this.handle.GetTexDiff2();
    },
    SetTexNormal: function (TexUnit) {
        this.handle.SetTexNormal(TexUnit);
    },
    GetTexNormal: function () {
        return this.handle.GetTexNormal();
    },
    SetTexSpecular: function (TexUnit) {
        this.handle.SetTexSpecular(TexUnit);
    },
    GetTexSpecular: function () {
        return this.handle.GetTexSpecular();
    },
    SetTexAnimate: function (TexAnimate) {
        this.handle.SetTexAnimate(TexAnimate);
    },
    GetTexAnimate: function () {
        return this.handle.GetTexAnimate();
    },
    SetEnvTexData: function (EnvTex) {
        this.handle.SetEnvTexData(EnvTex);
    },
    GetEnvTexData: function () {
        return this.handle.GetEnvTexData();
    }
});

Qmap.Object3D.QMap3DMaterialList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});



//QMap3DTexture对象
Qmap.Object3D.QMap3DTexture = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["Name", "FileName", "AddressModeU", "AddressModeV", "FilterModeMin", "FilterModeMag", "OpMode"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DShapeObject.QMap3DTexture");
        this.init(options);
    },

    init: function (options) {
        this.options = {
            "name": this.Method[0],
            "filename": this.Method[1],
            "addressmodeu": this.Method[2],
            "addressmodev": this.Method[3],
            "filtermodemin": this.Method[4],
            "filtermodemag": this.Method[5],
            "opmode": this.Method[6]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetName: function (strName) {
        this.handle.SetName(strName);
    },
    GetName: function () {
        return this.handle.GetName();
    },
    SetFileName: function (strFileName) {
        this.handle.SetFileName(strFileName);
    },
    GetFileName: function () {
        return this.handle.GetFileName();
    },
    SetAddressModeU: function (AddrModeU) {
        this.handle.SetAddressModeU(AddrModeU);
    },
    GetAddressModeU: function () {
        return this.handle.GetAddressModeU();
    },
    SetAddressModeV: function (AddrModeV) {
        this.handle.SetAddressModeV(AddrModeV);
    },
    GetAddressModeV: function () {
        return this.handle.GetAddressModeV();
    },
    SetFilterModeMin: function (FltModeMin) {
        this.handle.SetFilterModeMin(FltModeMin);
    },
    GetFilterModeMin: function () {
        return this.handle.GetFilterModeMin();
    },
    SetFilterModeMag: function (FltModeMag) {
        this.handle.SetFilterModeMag(FltModeMag);
    },
    GetFilterModeMag: function () {
        return this.handle.GetFilterModeMag();
    },
    SetOpMode: function (OpMode) {
        this.handle.SetOpMode(OpMode);
    },
    GetOpMode: function () {
        return this.handle.GetOpMode();
    }

});

Qmap.Object3D.QMap3DTextureList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});



//QMap3DPoly对象
Qmap.Object3D.QMap3DPoly = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["Hide", "IgnoreCollisionDetection", "Normal", "TextureXY", "AutoCalcNormal", "Vector3D"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DShapeObject.QMap3DPoly");
        this.init(options);
    },

    init: function (options) {
        this.options = {
            "hide": this.Method[0],
            "ignorecollisiondetection": this.Method[1],
            "normal": this.Method[2],
            "texturexy": this.Method[3],
            "autocalcnormal": this.Method[4],
            "vector3d": this.Method[5]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    //设置对象是否隐藏
    SetHide: function (bHide) {
        this.handle.SetHide(bHide);
    },
    //得到对象是否隐藏
    GetHide: function () {
        return this.handle.GetHide();
    },
    //设定是否忽略碰撞检测
    SetIgnoreCollisionDetection: function (bIgnoreCollisionDetection) {
        this.handle.SetIgnoreCollisionDetection(bIgnoreCollisionDetection);
    },
    //得到是否忽略碰撞检测的状态
    GetIgnoreCollisionDetection: function () {
        return this.handle.GetIgnoreCollisionDetection();
    },
    //设定QMap3DPoly对象使用的材质
    //    SetMaterial: function(material) {
    //        this.handle.SetMaterial(material);
    //    },
    //    GetMaterial: function() {
    //        return this.handle.GetMaterial();
    //    },
    SetVector3D: function (value) {
        if (Object.isArray(value)) {
            for (var i = 0; i < value.length; i++) {
                var parms = value[i];
                this.handle.AddVector3D(parms);
            }
        } else if (Object.isString(value)) {
            if (value.indexOf(";") > -1)//说明是用;进行分割的
            {
                var parmsList = value.split(";");
                for (var i = 0, length = parmsList.length; i < length; i++) {
                    var parms = parmsList[i];
                    this.handle.AddVector3D(parms);
                }

            } else //用,进行分割的
            {
                var parmsList = value.split(",");
                for (var i = 0, length = parmsList.length; i < length; i += 1) {
                    this.handle.AddVector3D(parmsList[i]);
                }
            }
        } else {
            alert("参数必须是字符串，或者数组!");
        }
    },
    GetCount: function () {
        return this.handle.GetCount();
    },
    Clear: function () {
        this.handle.Clear();
    },
    GetVector3D: function (nIdx) {
        return this.handle.GetVector3D(nIdx);
    },
    SetAutoCalcNormal: function (bAutoCalcNormal) {
        this.handle.SetAutoCalcNormal(bAutoCalcNormal);
    },
    GetAutoCalcNormal: function () {
        return this.handle.GetAutoCalcNormal();
    },
    //设定QMap3DPoly对象的法线
    SetNormal: function (obj) {
        if (obj instanceof Qmap.Object3D.QMap3DVector3D)
            this.handle.SetNormal(obj.handle);
        else
            alert("法线传入的不是QMap3DVector3D对象");
    },
    //得到QMap3DPoly对象的法线
    GetNormal: function () {
        var texture = this.handle.GetNormal();
        var obj = null;
        if (texture != null) {
            obj = new Qmap.Object3D.QMap3DVector3D();
            obj.handle = texture;
        }
        return obj;
    },
    SetTextureXY: function (obj) {
        if (obj instanceof Qmap.Object3D.QMap3DVector3D)
            this.handle.AddTextureXY(obj.handle);
        else
            alert("传入的参数不是QMap3DVector3D对象");
    },
    GetTextureXY: function () {
        var texture = this.handle.GetTextureXY();
        var obj = null;
        if (texture != null) {
            obj = new Qmap.Object3D.QMap3DVector3D();
            obj.handle = texture;
        }
        return obj;
    }

});

Qmap.Object3D.QMap3DPolyList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});


//QMap3DObject对象
Qmap.Object3D.QMap3DObject = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["Name", "Material", "Texture", "FaceIndex", "LineIndex"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DShapeObject.QMap3DObject");
        this.init(options);
    },

    init: function (options) {
        this.options = {
            "name": this.Method[0],
            "material": this.Method[1],
            "texture": this.Method[2],
            "faceindex": this.Method[3],
            "lineindex": this.Method[4]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetName: function (strName) {
        this.handle.SetName(strName);
    },
    GetName: function () {
        return this.handle.GetName();
    },
    SetMaterial: function (id) {
        this.handle.SetMaterial(id);
    },
    GetMaterial: function () {
        return this.handle.GetMaterial();
    },
    SetTexture: function (texture) {
        this.handle.SetTexture(texture);
    },
    GetTexture: function () {
        return this.handle.GetTexture();
    },
    SetFaceIndex: function (nFaceIndex) {
        this.handle.AddFaceIndex(nFaceIndex);
    },
    GetFaceCount: function () {
        return this.handle.GetFaceCount();
    },
    GetFaceIndex: function (nIdx) {
        return this.handle.GetFaceIndex(nIdx);
    },
    ///用于增加QMap3DObject 对象的线
    SetLineIndex: function (nLineIndex) {
        this.handle.AddLineIndex(nLineIndex);
    },
    GetLineCount: function () {
        return this.handle.GetLineCount();
    },
    //获取QMap3DObject 对象的线信息
    GetLineIndex: function (nIdx) {
        return this.handle.GetLineIndex(nIdx);
    },
    Clear: function () {
        this.handle.Clear();
    }

});

Qmap.Object3D.QMap3DObjectList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});




//QMap3DModel对象
Qmap.Object3D.QMap3DModel = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["Name", "Vector3D", "Line", "Poly", "Material", "Texture", "Object"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DShapeObject.QMap3DModel");
        this.init(options);
    },

    init: function (options) {
        this.options = {
            "name": this.Method[0],
            "vector3d": this.Method[1],
            "line": this.Method[2],
            "poly": this.Method[3],
            "material": this.Method[4],
            "texture": this.Method[5],
            "object": this.Method[6]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetName: function (strName) {
        this.handle.SetName(strName);
    },
    GetName: function () {
        return this.handle.GetName();
    },
    SetVector3D: function (value) {
        if (Object.isArray(value)) {
            for (var i = 0; i < value.length; i++) {
                var parms = value[i];
                this.handle.AddVector3D(parms.handle);
            }
        }
        else {
            alert("参数必须是数组!");
        }
    },
    GetVector3DCount: function () {
        return this.handle.GetVector3DCount();
    },
    GetVector3D: function (nIdx) {
        return this.handle.GetVector3D(nIdx);
    },
    SetLine: function (nLine) {
        this.handle.AddLine(nLine);
    },
    GetLineCount: function () {
        return this.handle.GetLineCount();
    },
    GetLine: function (nIdx) {
        return this.handle.GetLine();
    },
    SetPoly: function (nPoly) {
        this.handle.AddPoly(nPoly);
    },
    GetPolyCount: function () {
        return this.handle.GetPolyCount();
    },
    GetPoly: function (nIdx) {
        return this.handle.GetPoly(nIdx);
    },
    SetMaterial: function (material) {
        this.handle.AddMaterial(material);
    },
    GetMaterialCount: function () {
        return this.handle.GetMaterialCount();
    },
    GetMaterial: function (nIdx) {
        var material = this.handle.GetMaterial(nIdx);
        var obj = null;
        if (material != null) {
            obj = new Qmap.Object3D.QMap3DMaterial();
            obj.handle = material;
        }
        return obj;
    },
    SetTexture: function (texture) {
        this.handle.AddTexture(texture);
    },
    GetTextureCount: function () {
        return this.handle.GetTextureCount();
    },
    GetTexture: function (nIdx) {
        var texture = this.handle.GetTexture(nIdx);
        var obj = null;
        if (texture != null) {
            obj = new Qmap.Object3D.QMap3DTexture();
            obj.handle = texture;
        }
        return obj;
    },
    SetObject: function (obj) {
        this.handle.AddObject(obj);
    },
    GetObjectCount: function () {
        return this.handle.GetObjectCount();
    },
    Get3DObject: function (nIdx) {
        var texture = this.handle.Get3DObject(nIdx);
        var obj = null;
        if (texture != null) {
            obj = new Qmap.Object3D.QMap3DObject();
            obj.handle = texture;
        }
        return obj;
    },
    Clear: function () {
        this.handle.Clear();
    }

});

Qmap.Object3D.QMap3DModelList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});


//QMap3DVectorPlane对象  贴图
Qmap.Object3D.QMap3DVectorPlane = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    Method: ["GroupID", "StrID", "Level", "VectorSize", "VectorRotate", "ModelInstanceName", "LinkInfo", "Texture", "Height", "AdPosition"],
    initialize: function (options) {
        this.createObj("QMap3DShapeObject.QMap3DVectorPlane");
        this.init(options);
    },
    init: function (options) {
        this.options = {
            "groupid": this.Method[0],
            "strid": this.Method[1],
            "level": this.Method[2],
            "vectorsize": this.Method[3],
            "vectorrotate": this.Method[4],
            "modelinstancename": this.Method[5],
            "linkinfo": this.Method[6],
            "texture": this.Method[7],
            "height": this.Method[8],
            "adposition": this.Method[9]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetGroupID: function (groupID) {
        this.handle.SetGroupID(groupID);
    },
    GetGroupID: function () {
        return this.handle.GetGroupID();
    },
    SetStrID: function (strID) {
        this.handle.SetStrID(strID);
    },
    GetStrID: function () {
        return this.handle.GetStrID();
    },
    SetLevel: function (level) {
        this.handle.SetLevel(level);
    },
    GetLevel: function () {
        return this.handle.GetLevel();
    },
    SetVectorSize: function (xyz) {
        var rect = xyz.split(',');
        this.handle.SetVectorSize(rect[0], rect[1], rect[2]);
    },
    GetVectorSize: function () {
        return this.handle.GetVectorSize();
    },
    SetVectorRotate: function (xyz) {
        var rotate = xyz.split(',');
        this.handle.SetVectorRotate(rotate[0], rotate[1], rotate[2]);
    },
    GetVectorRotate: function () {
        return this.handle.GetVectorRotate();
    },
    SetModelInstanceName: function (strName) {
        this.handle.SetModelInstanceName(strName);
    },
    GetModelInstanceName: function () {
        return this.handle.GetModelInstanceName();
    },
    SetLinkInfo: function (linkinfo) {
        this.handle.SetLinkInfo(linkinfo);
    },
    GetLinkInfo: function () {
        return this.handle.GetLinkInfo();
    },
    SetTexture: function (texture) {
        this.handle.SetTexture(texture);
    },
    GetTexture: function () {
        return this.handle.GetTexture();
    },
    SetHeight: function (height) {
        this.handle.SetHeight(height);
    },
    GetHeight: function () {
        return this.handle.GetHeight();
    },
    SetAdPosition: function (xyz) {
        var pos = xyz.split(',');
        this.handle.SetAdPosition(pos[0], pos[2], pos[1]);
    },
    GetAdPosition: function () {
        return this.handle.GetAdPosition();
    },
    GetAdPositionxyz: function (index) {
        return this.handle.GetAdPositionxyz(index);
    }
});


//QMap3DModelInstance对象
Qmap.Object3D.QMap3DModelInstance = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["Model", "Layer", "GroupID", "StrID", "Level", "AdPosition", "AfSize", "AfRotate", "ModelInstanceName"
                , "LinkInfo", "Visible", "RotateBase", "RotateUnit", "Trans", "TransUnit", "TransRotateUnit"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DShapeObject.QMap3DModelInstance");
        this.init(options);
    },

    init: function (options) {
        this.options = {
            "model": this.Method[0],
            "layer": this.Method[1],
            "groupid": this.Method[2],
            "strid": this.Method[3],
            "level": this.Method[4],
            "adposition": this.Method[5],
            "afsize": this.Method[6],
            "afrotate": this.Method[7],
            "modelinstancename": this.Method[8],
            "linkinfo": this.Method[9],
            "visible": this.Method[10],
            "rotatebase": this.Method[11],
            "rotateunit": this.Method[12],
            "trans": this.Method[13],
            "transunit": this.Method[14],
            "transrotateunit": this.Method[15]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetModel: function (model) {
        this.handle.SetModel(model);
    },
    GetModel: function () {
        return this.handle.GetModel();
    },
    SetLayer: function (layer) {
        this.handle.SetLayer(layer);
    },
    GetLayer: function () {
        return this.handle.GetLayer();
    },
    SetGroupID: function (groupID) {
        this.handle.SetGroupID(groupID);
    },
    GetGroupID: function () {
        return this.handle.GetGroupID();
    },
    SetStrID: function (strID) {
        this.handle.SetStrID(strID);
    },
    GetStrID: function () {
        return this.handle.GetStrID();
    },
    SetLevel: function (level) {
        this.handle.SetLevel(level);
    },
    GetLevel: function () {
        return this.handle.GetLevel();
    },
    //参数顺序，经度，高度，纬度
    SetAdPosition: function (xyz) {
        var pos = xyz.split(',');
        this.handle.SetAdPosition(pos[0], pos[2], pos[1]);
    },
    GetAdPosition: function () {
        return this.handle.GetAdPosition();
    },
    SetAfSize: function (xyz) {
        var pos = xyz.split(',');
        this.handle.SetAfSize(pos[0], pos[2], pos[1]);
    },
    GetAfSize: function () {
        return this.handle.GetAfSize();
    },
    SetAfRotate: function (xyz) {
        var pos = xyz.split(',');
        this.handle.SetAfRotate(pos[0], pos[2], pos[1]);
    },
    GetAfRotate: function (nIdx) {
        return this.handle.GetAfRotate(nIdx);
    },
    SetModelInstanceName: function (strName) {
        this.handle.SetModelInstanceName(strName);
    },
    GetModelInstanceName: function () {
        return this.handle.GetModelInstanceName();
    },
    SetLinkInfo: function (linkInfo) {
        this.handle.SetLinkInfo(linkInfo);
    },
    GetLinkInfo: function () {
        return this.handle.GetLinkInfo();
    },
    SetVisible: function (visible) {
        this.handle.SetVisible(visible);
    },
    GetVisible: function () {
        return this.handle.GetVisible();
    },
    SetRotateBase: function (xyz) {
        var pos = xyz.split(',');
        this.handle.SetRotateBase(pos[0], pos[2], pos[1]);
    },
    GetRotateBase: function (nIdx) {
        return this.handle.GetRotateBase(nIdx);
    },
    SetRotateUnit: function (nRotateUnit) {
        this.handle.SetRotateUnit(nRotateUnit);
    },
    GetRotateUnit: function () {
        return this.handle.GetRotateUnit();
    },
    SetTransUnit: function (nTransUnit) {
        this.handle.SetTransUnit(nTransUnit);
    },
    GetTransUnit: function () {
        return this.handle.GetTransUnit();
    },
    SetTrans: function (obj) {
        this.handle.AddTrans(obj);
    },
    GetTransCount: function () {
        return this.handle.GetTransCount();
    },
    GetTrans: function (nIdx) {
        var trans = this.handle.GetTrans(nIdx);
        var obj = null;
        if (trans != null) {
            obj = new Qmap.Object3D.QMap3DVector4D();
            obj.handle = trans;
        }
        return obj;
    },
    Clear: function () {
        this.handle.Clear();
    },
    SetTransRotateUnit: function (nTransRotateUnit) {
        this.handle.SetTransRotateUnit(nTransRotateUnit);
    },
    GetTransRotateUnit: function () {
        return this.handle.GetTransRotateUnit();
    }

});

Qmap.Object3D.QMap3DModelInstanceList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});




//QMapRoute对象
Qmap.Object3D.QMapRoute = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    parentHandle: null,
    //方法名的key
    Method: ["StepTime", "StepDistance", "StepAngle", "AutoRepeat"],
    //初始化的构造函数
    initialize: function (options) {
        this.handle = Qmap.arrMap[0].QM_CreateRoute();
        this.init(options);
    },

    init: function (options) {
        this.options = {
            "steptime": this.Method[0],
            "stepdistance": this.Method[1],
            "stepangle": this.Method[2],
            "autorepeat": this.Method[3]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    AddWayPoint: function () {
        this.handle.AddWayPoint();
    },
    GetWayPoint: function (nIdx, nType) {
        return this.handle.GetWayPoint(nIdx, nType);
    },
    //经度，纬度，高度
    ModifyWaypoint: function (nIdx, x, y, z, hAngle, vAngle) {
        this.handle.ModifyWaypoint(nIdx, x, z, y, hAngle, vAngle);
    },
    NumberOfWaypoints: function () {
        return this.handle.NumberOfWaypoints();
    },
    Play: function () {
        this.handle.Play();
    },
    Pause: function (bPause) {
        return this.handle.Pause(bPause);
    },
    Stop: function () {
        this.handle.Stop();
    },
    GoNextStep: function () {
        this.handle.GoNextStep();
    },
    LoadFile: function (strFile) {
        this.handle.LoadFile(strFile);
    },
    SaveFile: function (strFile) {
        this.handle.SaveFile(strFile);
    },
    AddwaypointEx: function (nIdx, x, y, z, hAngle, vAngle) {
        this.handle.AddwaypointEx(nIdx, x, y, z, hAngle, vAngle);
    },
    AddTimeTrigger: function (timepos, userData) {
        this.handle.AddTimeTrigger(timepos, userData);
    },
    SetStepTime: function (time) {
        this.handle.StepTime = time;
    },
    GetStepTime: function () {
        return this.handle.StepTime;
    },
    SetStepDistance: function (distance) {
        this.handle.StepDistance = distance;
    },
    GetStepDistance: function () {
        return this.handle.StepDistance;
    },
    SetStepAngle: function (angle) {
        this.handle.StepAngle = angle;
    },
    GetStepAngle: function () {
        return this.handle.StepAngle;
    },
    SetAutoRepeat: function (isrepeat) {
        this.handle.AutoRepeat = isrepeat;
    },
    GetAutoRepeat: function () {
        return this.handle.AutoRepeat;
    }

});

Qmap.Object3D.QMapRouteList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});



//----------------------------------------------------刘静远---------------------------------------------------------------------
//QMap3DParticle对象
Qmap.Object3D.QMap3DParticle = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["ParticleName", "Layer", "GroupID", "StrID", "Level", "AdPosition", "AfSize", "AfRotate", "ModelInstanceName"
                , "LinkInfo", "Visible"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DShapeObject.QMap3DParticle");
        this.init(options);
    },
    init: function (options) {
        this.options = {
            "particlename": this.Method[0],
            "layer": this.Method[1],
            "groupid": this.Method[2],
            "strid": this.Method[3],
            "level": this.Method[4],
            "adposition": this.Method[5],
            "afsize": this.Method[6],
            "afrotate": this.Method[7],
            "modelinstancename": this.Method[8],
            "linkinfo": this.Method[9],
            "visible": this.Method[10]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetParticleName: function (particleName) {
        this.handle.SetParticleName(particleName);
    },
    GetParticleName: function () {
        return this.handle.GetParticleName();
    },
    SetLayer: function (layer) {
        this.handle.SetLayer(layer);
    },
    GetLayer: function () {
        return this.handle.GetLayer();
    },
    SetGroupID: function (groupID) {
        this.handle.SetGroupID(groupID);
    },
    GetGroupID: function () {
        return this.handle.GetGroupID();
    },
    SetStrID: function (strID) {
        this.handle.SetStrID(strID);
    },
    GetStrID: function () {
        return this.handle.GetStrID();
    },
    SetLevel: function (level) {
        this.handle.SetLevel(level);
    },
    GetLevel: function () {
        return this.handle.GetLevel();
    },
    SetAdPosition: function (xyz) {
        var pos = xyz.split(',');
        this.handle.SetAdPosition(pos[0], pos[2], pos[1]);
    },
    GetAdPosition: function () {
        return this.handle.GetAdPosition();
    },
    SetAfSize: function (xyz) {
        var pos = xyz.split(',');
        this.handle.SetAfSize(pos[0], pos[2], pos[1]);
    },
    GetAfSize: function () {
        return this.handle.GetAfSize();
    },
    SetAfRotate: function (xyz) {
        var pos = xyz.split(',');
        this.handle.SetAfRotate(pos[0], pos[2], pos[1]);
    },
    GetAfRotate: function (nIdx) {
        return this.handle.GetAfRotate(nIdx);
    },
    SetModelInstanceName: function (strName) {
        this.handle.SetModelInstanceName(strName);
    },
    GetModelInstanceName: function () {
        return this.handle.GetModelInstanceName();
    },
    SetLinkInfo: function (linkInfo) {
        this.handle.SetLinkInfo(linkInfo);
    },
    GetLinkInfo: function () {
        return this.handle.GetLinkInfo();
    },
    SetVisible: function (visible) {
        this.handle.SetVisible(visible);
    },
    GetVisible: function () {
        return this.handle.GetVisible();
    }
});

Qmap.Object3D.QMap3DParticleList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});



//QMap3DVectorLine对象
Qmap.Object3D.QMap3DVectorLine = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["GroupID", "StrID", "Level", "ModelInstanceName", "LinkInfo", "LineWidth", "LineType", "LineFragment", "Color", "Coords", "Dynamic"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DShapeObject.QMap3DVectorLine");
        this.init(options);
    },
    init: function (options) {
        this.options = {
            "groupid": this.Method[0],
            "strid": this.Method[1],
            "level": this.Method[2],
            "modelinstancename": this.Method[3],
            "linkinfo": this.Method[4],
            "linewidth": this.Method[5],
            "linetype": this.Method[6],
            "linefragment": this.Method[7],
            "color": this.Method[8],
            "coords": this.Method[9],
            "dynamic": this.Method[10]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetGroupID: function (groupID) {
        this.handle.SetGroupID(groupID);
    },
    GetGroupID: function () {
        return this.handle.GetGroupID();
    },
    SetStrID: function (strID) {
        this.handle.SetStrID(strID);
    },
    GetStrID: function () {
        return this.handle.GetStrID();
    },
    SetLevel: function (level) {
        this.handle.SetLevel(level);
    },
    GetLevel: function () {
        return this.handle.GetLevel();
    },
    SetModelInstanceName: function (strName) {
        this.handle.SetModelInstanceName(strName);
    },
    GetModelInstanceName: function () {
        return this.handle.GetModelInstanceName();
    },
    SetLinkInfo: function (linkInfo) {
        this.handle.SetLinkInfo(linkInfo);
    },
    GetLinkInfo: function () {
        return this.handle.GetLinkInfo();
    },
    SetLineWidth: function (lineWidth) {
        this.handle.SetLineWidth(lineWidth);
    },
    GetLineWidth: function () {
        return this.handle.GetLineWidth();
    },
    SetLineType: function (type) {
        this.handle.SetLineType(type);
    },
    GetLineType: function () {
        return this.handle.GetLineType();
    },
    SetLineFragment: function (fragment) {
        this.handle.SetLineFragment(fragment);
    },
    GetLineFragment: function () {
        return this.handle.GetLineFragment();
    },
    SetColor: function (color) {
        var colors = color.split(",");
        this.handle.SetColor(colors[0], colors[1], colors[2], colors[3]);
    },
    GetColor: function () {
        return this.handle.GetColor();
    },
    SetCoords: function (value) {
        if (Object.isArray(value)) {
            for (var i = 0; i < value.length; i++) {
                var parms = value[i].split(",");
                this.handle.AddCoord(parms[0], parms[2], parms[1]);
            }
        } else if (Object.isString(value)) {
            if (value.indexOf(";") > -1)//说明是用;进行分割的
            {
                var parmsList = value.split(";");
                for (var i = 0, length = parmsList.length; i < length; i++) {
                    var parms = parmsList[i].split(",");
                    this.handle.AddCoord(parms[0], parms[2], parms[1]);
                }

            } else //用,进行分割的
            {
                var parmsList = value.split(",");
                for (var i = 0, length = parmsList.length; i < length; i += 3) {
                    this.handle.AddCoord(parmsList[i], parmsList[i + 2], parmsList[i + 1]);
                }
            }
        } else {
            alert("参数必须是字符串，或者数组!");
        }
    },
    GetCoord: function (nIdx) {
        return this.handle.GetCoord(nIdx);
    },
    GetCoordxyz: function (cIdx, nIdx) {
        return this.handle.GetCoordxyz(cIdx, nIdx);
    },
    GetCoordsCount: function () {
        return this.handle.GetCoordsCount();
    },
    SetDynamic: function (dynamic) {
        return this.handle.SetDynamic(dynamic);
    },
    GetDynamic: function () {
        return this.handle.GetDynamic();
    },
    Clear: function () {
        this.handle.Clear();
    }
});

Qmap.Object3D.QMap3DVectorLineList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});




//QMap3DVectorPoly对象
Qmap.Object3D.QMap3DVectorPoly = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["GroupID", "StrID", "Level", "ModelInstanceName", "LinkInfo", "Texture", "Color", "Coords"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DShapeObject.QMap3DVectorPoly");
        this.init(options);
    },
    init: function (options) {
        this.options = {
            "groupid": this.Method[0],
            "strid": this.Method[1],
            "level": this.Method[2],
            "modelinstancename": this.Method[3],
            "linkinfo": this.Method[4],
            "texture": this.Method[5],
            "color": this.Method[6],
            "coords": this.Method[7]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetGroupID: function (groupID) {
        this.handle.SetGroupID(groupID);
    },
    GetGroupID: function () {
        return this.handle.GetGroupID();
    },
    SetStrID: function (strID) {
        this.handle.SetStrID(strID);
    },
    GetStrID: function () {
        return this.handle.GetStrID();
    },
    SetLevel: function (level) {
        this.handle.SetLevel(level);
    },
    GetLevel: function () {
        return this.handle.GetLevel();
    },
    SetModelInstanceName: function (strName) {
        this.handle.SetModelInstanceName(strName);
    },
    GetModelInstanceName: function () {
        return this.handle.GetModelInstanceName();
    },
    SetLinkInfo: function (linkInfo) {
        this.handle.SetLinkInfo(linkInfo);
    },
    GetLinkInfo: function () {
        return this.handle.GetLinkInfo();
    },
    SetTexture: function (texture) {
        this.handle.SetTexture(texture);
    },
    GetTexture: function () {
        return this.handle.GetTexture();
    },
    SetColor: function (color) {
        var colors = color.split(",");
        this.handle.SetColor(colors[0], colors[1], colors[2], colors[3]);
    },
    GetColor: function () {
        return this.handle.GetColor();
    },
    SetCoords: function (value) {
        if (Object.isArray(value)) {
            for (var i = 0; i < value.length; i++) {
                var parms = value[i].split(",");
                this.handle.AddCoord(parms[0], parms[2], parms[1]);
            }
        } else if (Object.isString(value)) {
            if (value.indexOf(";") > -1)//说明是用;进行分割的
            {
                var parmsList = value.split(";");
                for (var i = 0, length = parmsList.length; i < length; i++) {
                    var parms = parmsList[i].split(",");
                    this.handle.AddCoord(parms[0], parms[2], parms[1]);
                }

            } else //用,进行分割的
            {
                var parmsList = value.split(",");
                for (var i = 0, length = parmsList.length; i < length; i += 3) {
                    this.handle.AddCoord(parmsList[i], parmsList[i + 2], parmsList[i + 1]);
                }
            }
        } else {
            alert("参数必须是字符串，或者数组!");
        }
    },
    GetCoord: function (nIdx) {
        return this.handle.GetCoord(nIdx);
    },
    GetCoordxyz: function (cIdx, nIdx) {
        return this.handle.GetCoordxyz(cIdx, nIdx);
    },
    GetCoordsCount: function () {
        return this.handle.GetCoordsCount();
    },
    Clear: function () {
        this.handle.Clear();
    }
});

Qmap.Object3D.QMap3DVectorPolyList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});




//QMap3DVectorPipe对象
Qmap.Object3D.QMap3DVectorPipe = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["GroupID", "StrID", "Level", "ModelInstanceName", "LinkInfo", "Radius", "Texture", "Ambient", "Diffuse", "Specular"
                , "Shininess", "Material", "Coords"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DShapeObject.QMap3DVectorPipe");
        this.init(options);
    },
    init: function (options) {
        this.options = {
            "groupid": this.Method[0],
            "strid": this.Method[1],
            "level": this.Method[2],
            "modelinstancename": this.Method[3],
            "linkinfo": this.Method[4],
            "radius": this.Method[5],
            "texture": this.Method[6],
            "ambient": this.Method[7],
            "diffuse": this.Method[8],
            "specular": this.Method[9],
            "shininess": this.Method[10],
            "material": this.Method[11],
            "coords": this.Method[12]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetGroupID: function (groupID) {
        this.handle.SetGroupID(groupID);
    },
    GetGroupID: function () {
        return this.handle.GetGroupID();
    },
    SetStrID: function (strID) {
        this.handle.SetStrID(strID);
    },
    GetStrID: function () {
        return this.handle.GetStrID();
    },
    SetLevel: function (level) {
        this.handle.SetLevel(level);
    },
    GetLevel: function () {
        return this.handle.GetLevel();
    },
    SetModelInstanceName: function (strName) {
        this.handle.SetModelInstanceName(strName);
    },
    GetModelInstanceName: function () {
        return this.handle.GetModelInstanceName();
    },
    SetLinkInfo: function (linkInfo) {
        this.handle.SetLinkInfo(linkInfo);
    },
    GetLinkInfo: function () {
        return this.handle.GetLinkInfo();
    },
    SetRadius: function (radius) {
        this.handle.SetRadius(radius);
    },
    GetRadius: function () {
        return this.handle.GetRadius();
    },
    SetTexture: function (texture) {
        this.handle.SetTexture(texture);
    },
    GetTexture: function () {
        return this.handle.GetTexture();
    },
    SetAmbient: function (color) {
        var colors = color.split(",");
        this.handle.SetAmbient(colors[0], colors[1], colors[2], colors[3]);
    },
    GetAmbient: function () {
        return this.handle.GetAmbient();
    },
    SetDiffuse: function (color) {
        var colors = color.split(",");
        this.handle.SetDiffuse(colors[0], colors[1], colors[2], colors[3]);
    },
    GetDiffuse: function () {
        return this.handle.GetDiffuse();
    },
    SetSpecular: function (color) {
        var colors = color.split(",");
        this.handle.SetSpecular(colors[0], colors[1], colors[2], colors[3]);
    },
    GetSpecular: function () {
        return this.handle.GetSpecular();
    },
    SetShininess: function (shininess) {
        this.handle.SetShininess(shininess);
    },
    GetShininess: function () {
        return this.handle.GetShininess();
    },
    SetMaterial: function (material) {
        this.handle.SetMaterial(material);
    },
    GetMaterial: function () {
        return this.handle.GetMaterial();
    },
    SetCoords: function (value) {
        if (Object.isArray(value)) {
            for (var i = 0; i < value.length; i++) {
                var parms = value[i].split(",");
                this.handle.AddCoord(parms[0], parms[2], parms[1]);
            }
        } else if (Object.isString(value)) {
            if (value.indexOf(";") > -1)//说明是用;进行分割的
            {
                var parmsList = value.split(";");
                for (var i = 0, length = parmsList.length; i < length; i++) {
                    var parms = parmsList[i].split(",");
                    this.handle.AddCoord(parms[0], parms[2], parms[1]);
                }

            } else //用,进行分割的
            {
                var parmsList = value.split(",");
                for (var i = 0, length = parmsList.length; i < length; i += 3) {
                    this.handle.AddCoord(parmsList[i], parmsList[i + 2], parmsList[i + 1]);
                }
            }
        } else {
            alert("参数必须是字符串，或者数组!");
        }
    },
    GetCoord: function (nIdx) {
        return this.handle.GetCoord(nIdx);
    },
    GetCoordxyz: function (cIdx, nIdx) {
        return this.handle.GetCoordxyz(cIdx, nIdx);
    },
    GetCoordsCount: function () {
        return this.handle.GetCoordsCount();
    },
    Clear: function () {
        this.handle.Clear();
    }
});

Qmap.Object3D.QMap3DVectorPipeList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});



//QMap3DVectorCanal对象
Qmap.Object3D.QMap3DVectorCanal = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["GroupID", "StrID", "Level", "ModelInstanceName", "LinkInfo", "Width", "Height", "Texture", "Ambient", "Diffuse", "Specular"
                , "Shininess", "Material", "Coords"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DShapeObject.QMap3DVectorCanal");
        this.init(options);
    },
    init: function (options) {
        this.options = {
            "groupid": this.Method[0],
            "strid": this.Method[1],
            "level": this.Method[2],
            "modelinstancename": this.Method[3],
            "linkinfo": this.Method[4],
            "width": this.Method[5],
            "height": this.Method[6],
            "texture": this.Method[7],
            "ambient": this.Method[8],
            "diffuse": this.Method[9],
            "specular": this.Method[10],
            "shininess": this.Method[11],
            "material": this.Method[12],
            "coords": this.Method[13]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetGroupID: function (groupID) {
        this.handle.SetGroupID(groupID);
    },
    GetGroupID: function () {
        return this.handle.GetGroupID();
    },
    SetStrID: function (strID) {
        this.handle.SetStrID(strID);
    },
    GetStrID: function () {
        return this.handle.GetStrID();
    },
    SetLevel: function (level) {
        this.handle.SetLevel(level);
    },
    GetLevel: function () {
        return this.handle.GetLevel();
    },
    SetModelInstanceName: function (strName) {
        this.handle.SetModelInstanceName(strName);
    },
    GetModelInstanceName: function () {
        return this.handle.GetModelInstanceName();
    },
    SetLinkInfo: function (linkInfo) {
        this.handle.SetLinkInfo(linkInfo);
    },
    GetLinkInfo: function () {
        return this.handle.GetLinkInfo();
    },
    SetWidth: function (width) {
        this.handle.SetWidth(width);
    },
    GetWidth: function () {
        return this.handle.GetWidth();
    },
    SetHeight: function (height) {
        this.handle.SetHeight(height);
    },
    GetHeight: function () {
        return this.handle.GetHeight();
    },
    SetTexture: function (texture) {
        this.handle.SetTexture(texture);
    },
    GetTexture: function () {
        return this.handle.GetTexture();
    },
    SetAmbient: function (color) {
        var colors = color.split(",");
        this.handle.SetAmbient(colors[0], colors[1], colors[2], colors[3]);
    },
    GetAmbient: function () {
        return this.handle.GetAmbient();
    },
    SetDiffuse: function (color) {
        var colors = color.split(",");
        this.handle.SetDiffuse(colors[0], colors[1], colors[2], colors[3]);
    },
    GetDiffuse: function () {
        return this.handle.GetDiffuse();
    },
    SetSpecular: function (color) {
        var colors = color.split(",");
        this.handle.SetSpecular(colors[0], colors[1], colors[2], colors[3]);
    },
    GetSpecular: function () {
        return this.handle.GetSpecular();
    },
    SetShininess: function (shininess) {
        this.handle.SetShininess(shininess);
    },
    GetShininess: function () {
        return this.handle.GetShininess();
    },
    SetMaterial: function (material) {
        this.handle.SetMaterial(material);
    },
    GetMaterial: function () {
        return this.handle.GetMaterial();
    },
    SetCoords: function (value) {
        if (Object.isArray(value)) {
            for (var i = 0; i < value.length; i++) {
                var parms = value[i].split(",");
                this.handle.AddCoord(parms[0], parms[2], parms[1]);
            }
        } else if (Object.isString(value)) {
            if (value.indexOf(";") > -1)//说明是用;进行分割的
            {
                var parmsList = value.split(";");
                for (var i = 0, length = parmsList.length; i < length; i++) {
                    var parms = parmsList[i].split(",");
                    this.handle.AddCoord(parms[0], parms[2], parms[1]);
                }

            } else //用,进行分割的
            {
                var parmsList = value.split(",");
                for (var i = 0, length = parmsList.length; i < length; i += 3) {
                    this.handle.AddCoord(parmsList[i], parmsList[i + 2], parmsList[i + 1]);
                }
            }
        } else {
            alert("参数必须是字符串，或者数组!");
        }
    },
    GetCoord: function (nIdx) {
        return this.handle.GetCoord(nIdx);
    },
    GetCoordxyz: function (cIdx, nIdx) {
        return this.handle.GetCoordxyz(cIdx, nIdx);
    },
    GetCoordsCount: function () {
        return this.handle.GetCoordsCount();
    },
    Clear: function () {
        this.handle.Clear();
    }
});

Qmap.Object3D.QMap3DVectorCanalList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});



//QMap3DVectorWater对象
Qmap.Object3D.QMap3DVectorWater = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["GroupID", "StrID", "Level", "ModelInstanceName", "LinkInfo", "Texture", "WaterType", "Ambient", "Diffuse", "Specular"
                , "Shininess", "Material", "Coords"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DShapeObject.QMap3DVectorWater");
        this.init(options);
    },
    init: function (options) {
        this.options = {
            "groupid": this.Method[0],
            "strid": this.Method[1],
            "level": this.Method[2],
            "modelinstancename": this.Method[3],
            "linkinfo": this.Method[4],
            "texture": this.Method[5],
            "watertype": this.Method[6],
            "ambient": this.Method[7],
            "diffuse": this.Method[8],
            "specular": this.Method[9],
            "shininess": this.Method[10],
            "material": this.Method[11],
            "coords": this.Method[12]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetGroupID: function (groupID) {
        this.handle.SetGroupID(groupID);
    },
    GetGroupID: function () {
        return this.handle.GetGroupID();
    },
    SetStrID: function (strID) {
        this.handle.SetStrID(strID);
    },
    GetStrID: function () {
        return this.handle.GetStrID();
    },
    SetLevel: function (level) {
        this.handle.SetLevel(level);
    },
    GetLevel: function () {
        return this.handle.GetLevel();
    },
    SetModelInstanceName: function (strName) {
        this.handle.SetModelInstanceName(strName);
    },
    GetModelInstanceName: function () {
        return this.handle.GetModelInstanceName();
    },
    SetLinkInfo: function (linkInfo) {
        this.handle.SetLinkInfo(linkInfo);
    },
    GetLinkInfo: function () {
        return this.handle.GetLinkInfo();
    },
    SetTexture: function (texture) {
        this.handle.SetTexture(texture);
    },
    GetTexture: function () {
        return this.handle.GetTexture();
    },
    SetWaterType: function (waterType) {
        this.handle.SetWaterType(waterType);
    },
    GetWaterType: function () {
        return this.handle.GetWaterType();
    },
    SetAmbient: function (color) {
        var colors = color.split(",");
        this.handle.SetAmbient(colors[0], colors[1], colors[2], colors[3]);
    },
    GetAmbient: function () {
        return this.handle.GetAmbient();
    },
    SetDiffuse: function (color) {
        var colors = color.split(",");
        this.handle.SetDiffuse(colors[0], colors[1], colors[2], colors[3]);
    },
    GetDiffuse: function () {
        return this.handle.GetDiffuse();
    },
    SetSpecular: function (color) {
        var colors = color.split(",");
        this.handle.SetSpecular(colors[0], colors[1], colors[2], colors[3]);
    },
    GetSpecular: function () {
        return this.handle.GetSpecular();
    },
    SetShininess: function (shininess) {
        this.handle.SetShininess(shininess);
    },
    GetShininess: function () {
        return this.handle.GetShininess();
    },
    SetMaterial: function (material) {
        this.handle.SetMaterial(material);
    },
    GetMaterial: function () {
        return this.handle.GetMaterial();
    },
    SetCoords: function (value) {
        if (Object.isArray(value)) {
            for (var i = 0; i < value.length; i++) {
                var parms = value[i].split(",");
                this.handle.AddCoord(parms[0], parms[2], parms[1]);
            }
        } else if (Object.isString(value)) {
            if (value.indexOf(";") > -1)//说明是用;进行分割的
            {
                var parmsList = value.split(";");
                for (var i = 0, length = parmsList.length; i < length; i++) {
                    var parms = parmsList[i].split(",");
                    this.handle.AddCoord(parms[0], parms[2], parms[1]);
                }

            } else //用,进行分割的
            {
                var parmsList = value.split(",");
                for (var i = 0, length = parmsList.length; i < length; i += 3) {
                    this.handle.AddCoord(parmsList[i], parmsList[i + 2], parmsList[i + 1]);
                }
            }
        } else {
            alert("参数必须是字符串，或者数组!");
        }
    },
    GetCoord: function (nIdx) {
        return this.handle.GetCoord(nIdx);
    },
    GetCoordxyz: function (cIdx, nIdx) {
        return this.handle.GetCoordxyz(cIdx, nIdx);
    },
    GetCoordsCount: function () {
        return this.handle.GetCoordsCount();
    },
    Clear: function () {
        this.handle.Clear();
    }
});

Qmap.Object3D.QMap3DVectorWaterList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});



//QMap3DVectorRoad对象
Qmap.Object3D.QMap3DVectorRoad = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["GroupID", "StrID", "Level", "ModelInstanceName", "LinkInfo", "Texture", "RoadWidth", "Color", "FontName"
                , "ColorBelt", "ColorLane", "LaneInfo", "Ambient", "Diffuse", "Specular"
                , "Shininess", "Material", "Coords"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DShapeObject.QMap3DVectorRoad");
        this.init(options);
    },
    init: function (options) {
        this.options = {
            "groupid": this.Method[0],
            "strid": this.Method[1],
            "level": this.Method[2],
            "modelinstancename": this.Method[3],
            "linkinfo": this.Method[4],
            "texture": this.Method[5],
            "roadwidth": this.Method[6],
            "color": this.Method[7],
            "fontname": this.Method[8],
            "colorbelt": this.Method[9],
            "colorlane": this.Method[10],
            "laneinfo": this.Method[11],
            "ambient": this.Method[12],
            "diffuse": this.Method[13],
            "specular": this.Method[14],
            "shininess": this.Method[15],
            "material": this.Method[16],
            "coords": this.Method[17]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetGroupID: function (groupID) {
        this.handle.SetGroupID(groupID);
    },
    GetGroupID: function () {
        return this.handle.GetGroupID();
    },
    SetStrID: function (strID) {
        this.handle.SetStrID(strID);
    },
    GetStrID: function () {
        return this.handle.GetStrID();
    },
    SetLevel: function (level) {
        this.handle.SetLevel(level);
    },
    GetLevel: function () {
        return this.handle.GetLevel();
    },
    SetModelInstanceName: function (strName) {
        this.handle.SetModelInstanceName(strName);
    },
    GetModelInstanceName: function () {
        return this.handle.GetModelInstanceName();
    },
    SetLinkInfo: function (linkInfo) {
        this.handle.SetLinkInfo(linkInfo);
    },
    GetLinkInfo: function () {
        return this.handle.GetLinkInfo();
    },
    SetTexture: function (texture) {
        var value = texture.split(",");
        this.handle.SetTexture(value[0], value[1]);
    },
    GetTexture: function () {
        return this.handle.GetTexture();
    },
    SetRoadWidth: function (roadWidth) {
        this.handle.SetRoadWidth(roadWidth);
    },
    GetRoadWidth: function () {
        return this.handle.GetRoadWidth();
    },
    SetColor: function (color) {
        var colors = color.split(",");
        this.handle.SetColor(colors[0], colors[1], colors[2], colors[3]);
    },
    GetColor: function () {
        return this.handle.GetColor();
    },
    SetFontName: function (fontName) {
        this.handle.SetFontName(fontName);
    },
    GetFontName: function () {
        return this.handle.GetAmbient();
    },
    SetColorBelt: function (color) {
        var colors = color.split(",");
        this.handle.SetColorBelt(colors[0], colors[1], colors[2], colors[3]);
    },
    GetColorBelt: function () {
        return this.handle.GetColorBelt();
    },
    SetColorLane: function (color) {
        var colors = color.split(",");
        this.handle.SetColorLane(colors[0], colors[1], colors[2], colors[3]);
    },
    GetColorLane: function () {
        return this.handle.GetColorLane();
    },
    SetLaneInfo: function (value) {
        var values = value.split(",");
        var bl = false;
        if (values[2] == "1") bl = true;
        this.handle.SetLaneInfo(values[0], values[1], bl);
    },
    GetLaneInfoPos: function () {
        return this.handle.GetLaneInfoPos();
    },
    GetLaneInfoNeg: function () {
        return this.handle.GetLaneInfoNeg();
    },
    GetLaneInfoShowBelt: function () {
        return this.handle.GetLaneInfoShowBelt();
    },
    SetAmbient: function (color) {
        var colors = color.split(",");
        this.handle.SetAmbient(colors[0], colors[1], colors[2], colors[3]);
    },
    GetAmbient: function () {
        return this.handle.GetAmbient();
    },
    SetDiffuse: function (color) {
        var colors = color.split(",");
        this.handle.SetDiffuse(colors[0], colors[1], colors[2], colors[3]);
    },
    GetDiffuse: function () {
        return this.handle.GetDiffuse();
    },
    SetSpecular: function (color) {
        var colors = color.split(",");
        this.handle.SetSpecular(colors[0], colors[1], colors[2], colors[3]);
    },
    GetSpecular: function () {
        return this.handle.GetSpecular();
    },
    SetShininess: function (shininess) {
        this.handle.SetShininess(shininess);
    },
    GetShininess: function () {
        return this.handle.GetShininess();
    },
    SetMaterial: function (material) {
        this.handle.SetMaterial(material);
    },
    GetMaterial: function () {
        return this.handle.GetMaterial();
    },
    SetCoords: function (value) {
        if (Object.isArray(value)) {
            for (var i = 0; i < value.length; i++) {
                var parms = value[i].split(",");
                this.handle.AddCoord(parms[0], parms[2], parms[1]);
            }
        } else if (Object.isString(value)) {
            if (value.indexOf(";") > -1)//说明是用;进行分割的
            {
                var parmsList = value.split(";");
                for (var i = 0, length = parmsList.length; i < length; i++) {
                    var parms = parmsList[i].split(",");
                    this.handle.AddCoord(parms[0], parms[2], parms[1]);
                }

            } else //用,进行分割的
            {
                var parmsList = value.split(",");
                for (var i = 0, length = parmsList.length; i < length; i += 3) {
                    this.handle.AddCoord(parmsList[i], parmsList[i + 2], parmsList[i + 1]);
                }
            }
        } else {
            alert("参数必须是字符串，或者数组!");
        }
    },
    GetCoord: function (nIdx) {
        return this.handle.GetCoord(nIdx);
    },
    GetCoordxyz: function (cIdx, nIdx) {
        return this.handle.GetCoordxyz(cIdx, nIdx);
    },
    GetCoordsCount: function () {
        return this.handle.GetCoordsCount();
    },
    Clear: function () {
        this.handle.Clear();
    }
});

Qmap.Object3D.QMap3DVectorRoadList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});



//QMap3DVectorPOI对象
Qmap.Object3D.QMap3DVectorPOI = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["GroupID", "StrID", "Level", "ModelInstanceName", "LinkInfo", "Texture", "Color", "Dynamic", "FontName"
                , "FontSize", "AdPosition"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DShapeObject.QMap3DVectorPOI");
        this.init(options);
    },
    init: function (options) {
        this.options = {
            "groupid": this.Method[0],
            "strid": this.Method[1],
            "level": this.Method[2],
            "modelinstancename": this.Method[3],
            "linkinfo": this.Method[4],
            "texture": this.Method[5],
            "color": this.Method[6],
            "dynamic": this.Method[7],
            "fontname": this.Method[8],
            "fontsize": this.Method[9],
            "adposition": this.Method[10]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetGroupID: function (groupID) {
        this.handle.SetGroupID(groupID);
    },
    GetGroupID: function () {
        return this.handle.GetGroupID();
    },
    SetStrID: function (strID) {
        this.handle.SetStrID(strID);
    },
    GetStrID: function () {
        return this.handle.GetStrID();
    },
    SetLevel: function (level) {
        this.handle.SetLevel(level);
    },
    GetLevel: function () {
        return this.handle.GetLevel();
    },
    SetModelInstanceName: function (strName) {
        this.handle.SetModelInstanceName(strName);
    },
    GetModelInstanceName: function () {
        return this.handle.GetModelInstanceName();
    },
    SetLinkInfo: function (linkInfo) {
        this.handle.SetLinkInfo(linkInfo);
    },
    GetLinkInfo: function () {
        return this.handle.GetLinkInfo();
    },
    SetTexture: function (texture) {
        this.handle.SetTexture(texture);
    },
    GetTexture: function () {
        return this.handle.GetTexture();
    },
    SetColor: function (color) {
        var colors = color.split(",");
        this.handle.SetColor(colors[0], colors[1], colors[2], colors[3]);
    },
    GetColor: function () {
        return this.handle.GetColor();
    },
    SetDynamic: function (dynamic) {
        this.handle.SetDynamic(dynamic);
    },
    GetDynamic: function () {
        return this.handle.GetDynamic();
    },
    SetFontName: function (fontName) {
        this.handle.SetFontName(fontName);
    },
    GetFontName: function () {
        return this.handle.GetFontName();
    },
    SetFontSize: function (fontSize) {
        this.handle.SetFontSize(fontSize);
    },
    GetFontSize: function () {
        return this.handle.GetFontSize();
    },
    SetAdPosition: function (xyz) {
        var pos = xyz.split(",");
        this.handle.SetAdPosition(pos[0], pos[2], pos[1]);
    },
    GetAdPosition: function () {
        return this.handle.GetAdPosition();
    },
    GetAdPositionxyz: function (nIdx) {
        return this.handle.GetAdPositionxyz(nIdx);
    }
});

Qmap.Object3D.QMap3DVectorPOIList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});



//QMap3DVector3DPoly
Qmap.Object3D.QMap3DVector3DPoly = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["GroupID", "StrID", "Level", "ModelInstanceName", "LinkInfo", "Height", "Ambient", "Diffuse", "Specular"
                , "Shininess", "Material", "Coords"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DShapeObject.QMap3DVector3DPoly");
        this.init(options);
    },
    init: function (options) {
        this.options = {
            "groupid": this.Method[0],
            "strid": this.Method[1],
            "level": this.Method[2],
            "modelinstancename": this.Method[3],
            "linkinfo": this.Method[4],
            "height": this.Method[5],
            "ambient": this.Method[6],
            "diffuse": this.Method[7],
            "specular": this.Method[8],
            "shininess": this.Method[9],
            "material": this.Method[10],
            "coords": this.Method[11]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetGroupID: function (groupID) {
        this.handle.SetGroupID(groupID);
    },
    GetGroupID: function () {
        return this.handle.GetGroupID();
    },
    SetStrID: function (strID) {
        this.handle.SetStrID(strID);
    },
    GetStrID: function () {
        return this.handle.GetStrID();
    },
    SetLevel: function (level) {
        this.handle.SetLevel(level);
    },
    GetLevel: function () {
        return this.handle.GetLevel();
    },
    SetModelInstanceName: function (strName) {
        this.handle.SetModelInstanceName(strName);
    },
    GetModelInstanceName: function () {
        return this.handle.GetModelInstanceName();
    },
    SetLinkInfo: function (linkInfo) {
        this.handle.SetLinkInfo(linkInfo);
    },
    GetLinkInfo: function () {
        return this.handle.GetLinkInfo();
    },
    SetHeight: function (height) {
        this.handle.SetHeight(height);
    },
    GetHeight: function () {
        return this.handle.GetHeight();
    },
    SetAmbient: function (color) {
        var colors = color.split(",");
        this.handle.SetAmbient(colors[0], colors[1], colors[2], colors[3]);
    },
    GetAmbient: function () {
        return this.handle.GetAmbient();
    },
    SetDiffuse: function (color) {
        var colors = color.split(",");
        this.handle.SetDiffuse(colors[0], colors[1], colors[2], colors[3]);
    },
    GetDiffuse: function () {
        return this.handle.GetDiffuse();
    },
    SetSpecular: function (color) {
        var colors = color.split(",");
        this.handle.SetSpecular(colors[0], colors[1], colors[2], colors[3]);
    },
    GetSpecular: function () {
        return this.handle.GetSpecular();
    },
    SetShininess: function (shininess) {
        this.handle.SetShininess(shininess);
    },
    GetShininess: function () {
        return this.handle.GetShininess();
    },
    SetMaterial: function (material) {
        this.handle.SetMaterial(material);
    },
    GetMaterial: function () {
        return this.handle.GetMaterial();
    },
    SetCoords: function (value) {
        if (Object.isArray(value)) {
            for (var i = 0; i < value.length; i++) {
                var parms = value[i].split(",");
                this.handle.AddCoord(parms[0], parms[2], parms[1]);
            }
        } else if (Object.isString(value)) {
            if (value.indexOf(";") > -1)//说明是用;进行分割的
            {
                var parmsList = value.split(";");
                for (var i = 0, length = parmsList.length; i < length; i++) {
                    var parms = parmsList[i].split(",");
                    this.handle.AddCoord(parms[0], parms[2], parms[1]);
                }

            } else //用,进行分割的
            {
                var parmsList = value.split(",");
                for (var i = 0, length = parmsList.length; i < length; i += 3) {
                    this.handle.AddCoord(parmsList[i], parmsList[i + 2], parmsList[i + 1]);
                }
            }
        } else {
            alert("参数必须是字符串，或者数组!");
        }
    },
    GetCoord: function (nIdx) {
        return this.handle.GetCoord(nIdx);
    },
    GetCoordxyz: function (cIdx, nIdx) {
        return this.handle.GetCoordxyz(cIdx, nIdx);
    },
    GetCoordsCount: function () {
        return this.handle.GetCoordsCount();
    },
    Clear: function () {
        this.handle.Clear();
    }
});

Qmap.Object3D.QMap3DVector3DPolyList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});



//QMap3DVectorAd对象
Qmap.Object3D.QMap3DVectorAd = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["GroupID", "StrID", "Level", "ModelInstanceName", "LinkInfo", "Width", "Height", "ChangeTime", "Ambient", "Diffuse", "Specular"
                , "Shininess", "Material", "AdPosition"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DShapeObject.QMap3DVectorAd");
        this.init(options);
    },
    init: function (options) {
        this.options = {
            "groupid": this.Method[0],
            "strid": this.Method[1],
            "level": this.Method[2],
            "modelinstancename": this.Method[3],
            "linkinfo": this.Method[4],
            "width": this.Method[5],
            "height": this.Method[6],
            //"texture": this.Method[7],
            //"texcount": this.Method[8],
            "changetime": this.Method[7],
            "ambient": this.Method[8],
            "diffuse": this.Method[9],
            "specular": this.Method[10],
            "shininess": this.Method[11],
            "material": this.Method[12],
            "adposition": this.Method[13]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetGroupID: function (groupID) {
        this.handle.SetGroupID(groupID);
    },
    GetGroupID: function () {
        return this.handle.GetGroupID();
    },
    SetStrID: function (strID) {
        this.handle.SetStrID(strID);
    },
    GetStrID: function () {
        return this.handle.GetStrID();
    },
    SetLevel: function (level) {
        this.handle.SetLevel(level);
    },
    GetLevel: function () {
        return this.handle.GetLevel();
    },
    SetModelInstanceName: function (strName) {
        this.handle.SetModelInstanceName(strName);
    },
    GetModelInstanceName: function () {
        return this.handle.GetModelInstanceName();
    },
    SetLinkInfo: function (linkInfo) {
        this.handle.SetLinkInfo(linkInfo);
    },
    GetLinkInfo: function () {
        return this.handle.GetLinkInfo();
    },
    SetWidth: function (width) {
        this.handle.SetWidth(width);
    },
    GetWidth: function () {
        return this.handle.GetWidth();
    },
    SetHeight: function (height) {
        this.handle.SetHeight(height);
    },
    GetHeight: function () {
        return this.handle.GetHeight();
    },
    AddTexture: function (pos, texture) {
        this.handle.AddTexture(pos, texture);
    },
    GetTexture: function () {
        return this.handle.GetTexture();
    },
    SetTexCount: function (nValue) {
        this.handle.SetTexCount(nValue);
    },
    GetTexCount: function () {
        return this.handle.GetTexCount();
    },
    SetChangeTime: function (nTime) {
        this.handle.SetChangeTime(nTime);
    },
    GetChangeTime: function () {
        return this.handle.GetChangeTime();
    },
    SetAmbient: function (color) {
        var colors = color.split(",");
        this.handle.SetAmbient(colors[0], colors[1], colors[2], colors[3]);
    },
    GetAmbient: function () {
        return this.handle.GetAmbient();
    },
    SetDiffuse: function (color) {
        var colors = color.split(",");
        this.handle.SetDiffuse(colors[0], colors[1], colors[2], colors[3]);
    },
    GetDiffuse: function () {
        return this.handle.GetDiffuse();
    },
    SetSpecular: function (color) {
        var colors = color.split(",");
        this.handle.SetSpecular(colors[0], colors[1], colors[2], colors[3]);
    },
    GetSpecular: function () {
        return this.handle.GetSpecular();
    },
    SetShininess: function (shininess) {
        this.handle.SetShininess(shininess);
    },
    GetShininess: function () {
        return this.handle.GetShininess();
    },
    SetMaterial: function (material) {
        this.handle.SetMaterial(material);
    },
    GetMaterial: function () {
        return this.handle.GetMaterial();
    },
    SetAdPosition: function (xyz) {
        var pos = xyz.split(",");
        this.handle.SetAdPosition(pos[0], pos[2], pos[1]);
    },
    GetAdPosition: function () {
        return this.handle.GetAdPosition();
    },
    GetAdPositionxyz: function (nIdx) {
        return this.handle.GetAdPositionxyz(nIdx);
    },
    Clear: function () {
        this.handle.Clear();
    }
});

Qmap.Object3D.QMap3DVectorAdList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});



//QMap3DVectorCross对象
Qmap.Object3D.QMap3DVectorCross = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["GroupID", "StrID", "Level", "VectorSize", "VectorRotate", "ModelInstanceName", "LinkInfo"
                , "Texture", "RoadWidth", "RoadWidth2", "Road1Angle", "Road2Angle", "Road1TwoSide", "Road2TwoSide", "Ambient", "Diffuse", "Specular"
                , "Shininess", "Material", "AdPosition"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DShapeObject.QMap3DVectorCross");
        this.init(options);
    },
    init: function (options) {
        this.options = {
            "groupid": this.Method[0],
            "strid": this.Method[1],
            "level": this.Method[2],
            "vectorsize": this.Method[3],
            "vectorrotate": this.Method[4],
            "modelinstancename": this.Method[5],
            "linkinfo": this.Method[6],
            "texture": this.Method[7],
            "roadwidth": this.Method[8],
            "roadwidth2": this.Method[9],
            "road1angle": this.Method[10],
            "road2angle": this.Method[11],
            "road1twoside": this.Method[12],
            "road2twoside": this.Method[13],
            "ambient": this.Method[14],
            "diffuse": this.Method[15],
            "specular": this.Method[16],
            "shininess": this.Method[17],
            "material": this.Method[18],
            "adposition": this.Method[19]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetGroupID: function (groupID) {
        this.handle.SetGroupID(groupID);
    },
    GetGroupID: function () {
        return this.handle.GetGroupID();
    },
    SetStrID: function (strID) {
        this.handle.SetStrID(strID);
    },
    GetStrID: function () {
        return this.handle.GetStrID();
    },
    SetLevel: function (level) {
        this.handle.SetLevel(level);
    },
    GetLevel: function () {
        return this.handle.GetLevel();
    },
    SetVectorSize: function (xyz) {
        var size = xyz.split(",");
        this.handle.SetVectorSize(size[0], size[1], size[2]);
    },
    GetVectorSize: function () {
        return this.handle.GetVectorSize();
    },
    SetVectorRotate: function (xyz) {
        var size = xyz.split(",");
        this.handle.SetVectorRotate(size[0], size[1], size[2]);
    },
    GetVectorRotate: function () {
        return this.handle.GetVectorRotate();
    },
    SetModelInstanceName: function (strName) {
        this.handle.SetModelInstanceName(strName);
    },
    GetModelInstanceName: function () {
        return this.handle.GetModelInstanceName();
    },
    SetLinkInfo: function (linkInfo) {
        this.handle.SetLinkInfo(linkInfo);
    },
    GetLinkInfo: function () {
        return this.handle.GetLinkInfo();
    },
    SetTexture: function (texture) {
        this.handle.SetTexture(texture);
    },
    GetTexture: function () {
        return this.handle.GetTexture();
    },
    SetRoadWidth: function (roadWidth) {
        this.handle.SetRoadWidth(roadWidth);
    },
    GetRoadWidth: function () {
        return this.handle.GetRoadWidth();
    },
    SetRoadWidth2: function (roadWidth) {
        this.handle.SetRoadWidth2(roadWidth);
    },
    GetRoadWidth2: function () {
        return this.handle.GetRoadWidth2();
    },
    SetRoad1Angle: function (angle) {
        this.handle.SetRoad1Angle(angle);
    },
    GetRoad1Angle: function () {
        return this.handle.GetRoad1Angle();
    },
    SetRoad2Angle: function (angle) {
        this.handle.SetRoad2Angle(angle);
    },
    GetRoad2Angle: function () {
        return this.handle.GetRoad2Angle();
    },
    SetRoad1TwoSide: function (twoSide) {
        this.handle.SetRoad1TwoSide(twoSide);
    },
    GetRoad1TwoSide: function () {
        return this.handle.GetRoad1TwoSide();
    },
    SetRoad2TwoSide: function (roadWidth) {
        this.handle.SetRoad2TwoSide(roadWidth);
    },
    GetRoad2TwoSide: function () {
        return this.handle.GetRoad2TwoSide();
    },
    SetAmbient: function (color) {
        var colors = color.split(",");
        this.handle.SetAmbient(colors[0], colors[1], colors[2], colors[3]);
    },
    GetAmbient: function () {
        return this.handle.GetAmbient();
    },
    SetDiffuse: function (color) {
        var colors = color.split(",");
        this.handle.SetDiffuse(colors[0], colors[1], colors[2], colors[3]);
    },
    GetDiffuse: function () {
        return this.handle.GetDiffuse();
    },
    SetSpecular: function (color) {
        var colors = color.split(",");
        this.handle.SetSpecular(colors[0], colors[1], colors[2], colors[3]);
    },
    GetSpecular: function () {
        return this.handle.GetSpecular();
    },
    SetShininess: function (shininess) {
        this.handle.SetShininess(shininess);
    },
    GetShininess: function () {
        return this.handle.GetShininess();
    },
    SetMaterial: function (material) {
        this.handle.SetMaterial(material);
    },
    GetMaterial: function () {
        return this.handle.GetMaterial();
    },
    SetAdPosition: function (xyz) {
        var pos = xyz.split(",");
        this.handle.SetAdPosition(pos[0], pos[2], pos[1]);
    },
    GetAdPosition: function () {
        return this.handle.GetAdPosition();
    },
    GetAdPositionxyz: function (nIdx) {
        return this.handle.GetAdPositionxyz(nIdx);
    },
    Clear: function () {
        this.handle.Clear();
    }
});

Qmap.Object3D.QMap3DVectorCrossList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});



//QMap3DVectorCuboid
Qmap.Object3D.QMap3DVectorCuboid = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["GroupID", "StrID", "Level", "VectorSize", "VectorRotate", "ModelInstanceName", "LinkInfo"
                , "Texture", "Ambient", "Diffuse", "Specular"
                , "Shininess", "Material", "AdPosition"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DShapeObject.QMap3DVectorCuboid");
        this.init(options);
    },
    init: function (options) {
        this.options = {
            "groupid": this.Method[0],
            "strid": this.Method[1],
            "level": this.Method[2],
            "vectorsize": this.Method[3],
            "vectorrotate": this.Method[4],
            "modelinstancename": this.Method[5],
            "linkinfo": this.Method[6],
            "texture": this.Method[7],
            "ambient": this.Method[8],
            "diffuse": this.Method[9],
            "specular": this.Method[10],
            "shininess": this.Method[11],
            "material": this.Method[12],
            "adposition": this.Method[13]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetGroupID: function (groupID) {
        this.handle.SetGroupID(groupID);
    },
    GetGroupID: function () {
        return this.handle.GetGroupID();
    },
    SetStrID: function (strID) {
        this.handle.SetStrID(strID);
    },
    GetStrID: function () {
        return this.handle.GetStrID();
    },
    SetLevel: function (level) {
        this.handle.SetLevel(level);
    },
    GetLevel: function () {
        return this.handle.GetLevel();
    },
    SetVectorSize: function (xyz) {
        var size = xyz.split(",");
        this.handle.SetVectorSize(size[0], size[1], size[2]);
    },
    GetVectorSize: function () {
        return this.handle.GetVectorSize();
    },
    SetVectorRotate: function (xyz) {
        var size = xyz.split(",");
        this.handle.SetVectorRotate(size[0], size[1], size[2]);
    },
    GetVectorRotate: function () {
        return this.handle.GetVectorRotate();
    },
    SetModelInstanceName: function (strName) {
        this.handle.SetModelInstanceName(strName);
    },
    GetModelInstanceName: function () {
        return this.handle.GetModelInstanceName();
    },
    SetLinkInfo: function (linkInfo) {
        this.handle.SetLinkInfo(linkInfo);
    },
    GetLinkInfo: function () {
        return this.handle.GetLinkInfo();
    },
    SetTexture: function (texture) {
        this.handle.SetTexture(texture);
    },
    GetTexture: function () {
        return this.handle.GetTexture();
    },
    SetAmbient: function (color) {
        var colors = color.split(",");
        this.handle.SetAmbient(colors[0], colors[1], colors[2], colors[3]);
    },
    GetAmbient: function () {
        return this.handle.GetAmbient();
    },
    SetDiffuse: function (color) {
        var colors = color.split(",");
        this.handle.SetDiffuse(colors[0], colors[1], colors[2], colors[3]);
    },
    GetDiffuse: function () {
        return this.handle.GetDiffuse();
    },
    SetSpecular: function (color) {
        var colors = color.split(",");
        this.handle.SetSpecular(colors[0], colors[1], colors[2], colors[3]);
    },
    GetSpecular: function () {
        return this.handle.GetSpecular();
    },
    SetShininess: function (shininess) {
        this.handle.SetShininess(shininess);
    },
    GetShininess: function () {
        return this.handle.GetShininess();
    },
    SetMaterial: function (material) {
        this.handle.SetMaterial(material);
    },
    GetMaterial: function () {
        return this.handle.GetMaterial();
    },
    SetAdPosition: function (xyz) {
        var pos = xyz.split(",");
        this.handle.SetAdPosition(pos[0], pos[2], pos[1]);
    },
    GetAdPosition: function () {
        return this.handle.GetAdPosition();
    },
    GetAdPositionxyz: function (nIdx) {
        return this.handle.GetAdPositionxyz(nIdx);
    }
});

Qmap.Object3D.QMap3DVectorCuboidList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});



//QMap3DVectorColumn
Qmap.Object3D.QMap3DVectorColumn = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["GroupID", "StrID", "Level", "VectorSize", "VectorRotate", "ModelInstanceName", "LinkInfo"
                , "Texture", "Ambient", "Diffuse", "Specular"
                , "Shininess", "Material", "AdPosition"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DShapeObject.QMap3DVectorColumn");
        this.init(options);
    },
    init: function (options) {
        this.options = {
            "groupid": this.Method[0],
            "strid": this.Method[1],
            "level": this.Method[2],
            "vectorsize": this.Method[3],
            "vectorrotate": this.Method[4],
            "modelinstancename": this.Method[5],
            "linkinfo": this.Method[6],
            "texture": this.Method[7],
            "ambient": this.Method[8],
            "diffuse": this.Method[9],
            "specular": this.Method[10],
            "shininess": this.Method[11],
            "material": this.Method[12],
            "adposition": this.Method[13]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetGroupID: function (groupID) {
        this.handle.SetGroupID(groupID);
    },
    GetGroupID: function () {
        return this.handle.GetGroupID();
    },
    SetStrID: function (strID) {
        this.handle.SetStrID(strID);
    },
    GetStrID: function () {
        return this.handle.GetStrID();
    },
    SetLevel: function (level) {
        this.handle.SetLevel(level);
    },
    GetLevel: function () {
        return this.handle.GetLevel();
    },
    SetVectorSize: function (xyz) {
        var size = xyz.split(",");
        this.handle.SetVectorSize(size[0], size[1]);
    },
    GetVectorSize: function () {
        return this.handle.GetVectorSize();
    },
    SetVectorRotate: function (xyz) {
        var size = xyz.split(",");
        this.handle.SetVectorRotate(size[0], size[1], size[2]);
    },
    GetVectorRotate: function () {
        return this.handle.GetVectorRotate();
    },
    SetModelInstanceName: function (strName) {
        this.handle.SetModelInstanceName(strName);
    },
    GetModelInstanceName: function () {
        return this.handle.GetModelInstanceName();
    },
    SetLinkInfo: function (linkInfo) {
        this.handle.SetLinkInfo(linkInfo);
    },
    GetLinkInfo: function () {
        return this.handle.GetLinkInfo();
    },
    SetTexture: function (texture) {
        this.handle.SetTexture(texture);
    },
    GetTexture: function () {
        return this.handle.GetTexture();
    },
    SetAmbient: function (color) {
        var colors = color.split(",");
        this.handle.SetAmbient(colors[0], colors[1], colors[2], colors[3]);
    },
    GetAmbient: function () {
        return this.handle.GetAmbient();
    },
    SetDiffuse: function (color) {
        var colors = color.split(",");
        this.handle.SetDiffuse(colors[0], colors[1], colors[2], colors[3]);
    },
    GetDiffuse: function () {
        return this.handle.GetDiffuse();
    },
    SetSpecular: function (color) {
        var colors = color.split(",");
        this.handle.SetSpecular(colors[0], colors[1], colors[2], colors[3]);
    },
    GetSpecular: function () {
        return this.handle.GetSpecular();
    },
    SetShininess: function (shininess) {
        this.handle.SetShininess(shininess);
    },
    GetShininess: function () {
        return this.handle.GetShininess();
    },
    SetMaterial: function (material) {
        this.handle.SetMaterial(material);
    },
    GetMaterial: function () {
        return this.handle.GetMaterial();
    },
    SetAdPosition: function (xyz) {
        var pos = xyz.split(",");
        this.handle.SetAdPosition(pos[0], pos[2], pos[1]);
    },
    GetAdPosition: function () {
        return this.handle.GetAdPosition();
    },
    GetAdPositionxyz: function (nIdx) {
        return this.handle.GetAdPositionxyz(nIdx);
    }
});

Qmap.Object3D.QMap3DVectorColumnList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});



//QMap3DVectorTaper
Qmap.Object3D.QMap3DVectorTaper = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["GroupID", "StrID", "Level", "VectorSize", "VectorRotate", "ModelInstanceName", "LinkInfo"
                , "Texture", "Ambient", "Diffuse", "Specular"
                , "Shininess", "Material", "AdPosition"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DShapeObject.QMap3DVectorTaper");
        this.init(options);
    },
    init: function (options) {
        this.options = {
            "groupid": this.Method[0],
            "strid": this.Method[1],
            "level": this.Method[2],
            "vectorsize": this.Method[3],
            "vectorrotate": this.Method[4],
            "modelinstancename": this.Method[5],
            "linkinfo": this.Method[6],
            "texture": this.Method[7],
            "ambient": this.Method[8],
            "diffuse": this.Method[9],
            "specular": this.Method[10],
            "shininess": this.Method[11],
            "material": this.Method[12],
            "adposition": this.Method[13]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetGroupID: function (groupID) {
        this.handle.SetGroupID(groupID);
    },
    GetGroupID: function () {
        return this.handle.GetGroupID();
    },
    SetStrID: function (strID) {
        this.handle.SetStrID(strID);
    },
    GetStrID: function () {
        return this.handle.GetStrID();
    },
    SetLevel: function (level) {
        this.handle.SetLevel(level);
    },
    GetLevel: function () {
        return this.handle.GetLevel();
    },
    SetVectorSize: function (xyz) {
        var size = xyz.split(",");
        this.handle.SetVectorSize(size[0], size[1]);
    },
    GetVectorSize: function () {
        return this.handle.GetVectorSize();
    },
    SetVectorRotate: function (xyz) {
        var size = xyz.split(",");
        this.handle.SetVectorRotate(size[0], size[1], size[2]);
    },
    GetVectorRotate: function () {
        return this.handle.GetVectorRotate();
    },
    SetModelInstanceName: function (strName) {
        this.handle.SetModelInstanceName(strName);
    },
    GetModelInstanceName: function () {
        return this.handle.GetModelInstanceName();
    },
    SetLinkInfo: function (linkInfo) {
        this.handle.SetLinkInfo(linkInfo);
    },
    GetLinkInfo: function () {
        return this.handle.GetLinkInfo();
    },
    SetTexture: function (texture) {
        this.handle.SetTexture(texture);
    },
    GetTexture: function () {
        return this.handle.GetTexture();
    },
    SetAmbient: function (color) {
        var colors = color.split(",");
        this.handle.SetAmbient(colors[0], colors[1], colors[2], colors[3]);
    },
    GetAmbient: function () {
        return this.handle.GetAmbient();
    },
    SetDiffuse: function (color) {
        var colors = color.split(",");
        this.handle.SetDiffuse(colors[0], colors[1], colors[2], colors[3]);
    },
    GetDiffuse: function () {
        return this.handle.GetDiffuse();
    },
    SetSpecular: function (color) {
        var colors = color.split(",");
        this.handle.SetSpecular(colors[0], colors[1], colors[2], colors[3]);
    },
    GetSpecular: function () {
        return this.handle.GetSpecular();
    },
    SetShininess: function (shininess) {
        this.handle.SetShininess(shininess);
    },
    GetShininess: function () {
        return this.handle.GetShininess();
    },
    SetMaterial: function (material) {
        this.handle.SetMaterial(material);
    },
    GetMaterial: function () {
        return this.handle.GetMaterial();
    },
    SetAdPosition: function (xyz) {
        var pos = xyz.split(",");
        this.handle.SetAdPosition(pos[0], pos[2], pos[1]);
    },
    GetAdPosition: function () {
        return this.handle.GetAdPosition();
    },
    GetAdPositionxyz: function (nIdx) {
        return this.handle.GetAdPositionxyz(nIdx);
    }
});

Qmap.Object3D.QMap3DVectorTaperList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});






//QMap3DVectorSphere
Qmap.Object3D.QMap3DVectorSphere = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["GroupID", "StrID", "Level", "VectorSize", "ModelInstanceName", "LinkInfo"
                , "Texture", "Ambient", "Diffuse", "Specular"
                , "Shininess", "Material", "AdPosition"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DShapeObject.QMap3DVectorSphere");
        this.init(options);
    },
    init: function (options) {
        this.options = {
            "groupid": this.Method[0],
            "strid": this.Method[1],
            "level": this.Method[2],
            "vectorsize": this.Method[3],
            "modelinstancename": this.Method[4],
            "linkinfo": this.Method[5],
            "texture": this.Method[6],
            "ambient": this.Method[7],
            "diffuse": this.Method[8],
            "specular": this.Method[9],
            "shininess": this.Method[10],
            "material": this.Method[11],
            "adposition": this.Method[12]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetGroupID: function (groupID) {
        this.handle.SetGroupID(groupID);
    },
    GetGroupID: function () {
        return this.handle.GetGroupID();
    },
    SetStrID: function (strID) {
        this.handle.SetStrID(strID);
    },
    GetStrID: function () {
        return this.handle.GetStrID();
    },
    SetLevel: function (level) {
        this.handle.SetLevel(level);
    },
    GetLevel: function () {
        return this.handle.GetLevel();
    },
    SetVectorSize: function (xyz) {
        this.handle.SetVectorSize(xyz);
    },
    GetVectorSize: function () {
        return this.handle.GetVectorSize();
    },
    SetModelInstanceName: function (strName) {
        this.handle.SetModelInstanceName(strName);
    },
    GetModelInstanceName: function () {
        return this.handle.GetModelInstanceName();
    },
    SetLinkInfo: function (linkInfo) {
        this.handle.SetLinkInfo(linkInfo);
    },
    GetLinkInfo: function () {
        return this.handle.GetLinkInfo();
    },
    SetTexture: function (texture) {
        this.handle.SetTexture(texture);
    },
    GetTexture: function () {
        return this.handle.GetTexture();
    },
    SetAmbient: function (color) {
        var colors = color.split(",");
        this.handle.SetAmbient(colors[0], colors[1], colors[2], colors[3]);
    },
    GetAmbient: function () {
        return this.handle.GetAmbient();
    },
    SetDiffuse: function (color) {
        var colors = color.split(",");
        this.handle.SetDiffuse(colors[0], colors[1], colors[2], colors[3]);
    },
    GetDiffuse: function () {
        return this.handle.GetDiffuse();
    },
    SetSpecular: function (color) {
        var colors = color.split(",");
        this.handle.SetSpecular(colors[0], colors[1], colors[2], colors[3]);
    },
    GetSpecular: function () {
        return this.handle.GetSpecular();
    },
    SetShininess: function (shininess) {
        this.handle.SetShininess(shininess);
    },
    GetShininess: function () {
        return this.handle.GetShininess();
    },
    SetMaterial: function (material) {
        this.handle.SetMaterial(material);
    },
    GetMaterial: function () {
        return this.handle.GetMaterial();
    },
    SetAdPosition: function (xyz) {
        var pos = xyz.split(",");
        this.handle.SetAdPosition(pos[0], pos[2], pos[1]);
    },
    GetAdPosition: function () {
        return this.handle.GetAdPosition();
    },
    GetAdPositionxyz: function (nIdx) {
        return this.handle.GetAdPositionxyz(nIdx);
    }
});

Qmap.Object3D.QMap3DVectorSphereList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});




//QMap3DVectorMillArrow
Qmap.Object3D.QMap3DVectorMillArrow = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["GroupID", "StrID", "Level", "ModelInstanceName", "LinkInfo", "Height", "Texture", "Ambient", "Diffuse", "Specular"
                , "Shininess", "Material", "Coords"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DShapeObject.QMap3DVectorMillArrow");
        this.init(options);
    },
    init: function (options) {
        this.options = {
            "groupid": this.Method[0],
            "strid": this.Method[1],
            "level": this.Method[2],
            "modelinstancename": this.Method[3],
            "linkinfo": this.Method[4],
            "height": this.Method[5],
            "texture": this.Method[6],
            "ambient": this.Method[7],
            "diffuse": this.Method[8],
            "specular": this.Method[9],
            "shininess": this.Method[10],
            "material": this.Method[11],
            "coords": this.Method[12]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetGroupID: function (groupID) {
        this.handle.SetGroupID(groupID);
    },
    GetGroupID: function () {
        return this.handle.GetGroupID();
    },
    SetStrID: function (strID) {
        this.handle.SetStrID(strID);
    },
    GetStrID: function () {
        return this.handle.GetStrID();
    },
    SetLevel: function (level) {
        this.handle.SetLevel(level);
    },
    GetLevel: function () {
        return this.handle.GetLevel();
    },
    SetModelInstanceName: function (strName) {
        this.handle.SetModelInstanceName(strName);
    },
    GetModelInstanceName: function () {
        return this.handle.GetModelInstanceName();
    },
    SetLinkInfo: function (linkInfo) {
        this.handle.SetLinkInfo(linkInfo);
    },
    GetLinkInfo: function () {
        return this.handle.GetLinkInfo();
    },
    SetHeight: function (height) {
        this.handle.SetHeight(height);
    },
    GetHeight: function () {
        return this.handle.GetHeight();
    },
    SetTexture: function (texture) {
        this.handle.SetTexture(texture);
    },
    GetTexture: function () {
        return this.handle.GetTexture();
    },
    SetAmbient: function (color) {
        var colors = color.split(",");
        this.handle.SetAmbient(colors[0], colors[1], colors[2], colors[3]);
    },
    GetAmbient: function () {
        return this.handle.GetAmbient();
    },
    SetDiffuse: function (color) {
        var colors = color.split(",");
        this.handle.SetDiffuse(colors[0], colors[1], colors[2], colors[3]);
    },
    GetDiffuse: function () {
        return this.handle.GetDiffuse();
    },
    SetSpecular: function (color) {
        var colors = color.split(",");
        this.handle.SetSpecular(colors[0], colors[1], colors[2], colors[3]);
    },
    GetSpecular: function () {
        return this.handle.GetSpecular();
    },
    SetShininess: function (shininess) {
        this.handle.SetShininess(shininess);
    },
    GetShininess: function () {
        return this.handle.GetShininess();
    },
    SetMaterial: function (material) {
        this.handle.SetMaterial(material);
    },
    GetMaterial: function () {
        return this.handle.GetMaterial();
    },
    SetCoords: function (value) {
        if (Object.isArray(value)) {
            for (var i = 0; i < value.length; i++) {
                var parms = value[i].split(",");
                this.handle.AddCoord(parms[0], parms[2], parms[1]);
            }
        } else if (Object.isString(value)) {
            if (value.indexOf(";") > -1)//说明是用;进行分割的
            {
                var parmsList = value.split(";");
                for (var i = 0, length = parmsList.length; i < length; i++) {
                    var parms = parmsList[i].split(",");
                    this.handle.AddCoord(parms[0], parms[2], parms[1]);
                }

            } else //用,进行分割的
            {
                var parmsList = value.split(",");
                for (var i = 0, length = parmsList.length; i < length; i += 3) {
                    this.handle.AddCoord(parmsList[i], parmsList[i + 2], parmsList[i + 1]);
                }
            }
        } else {
            alert("参数必须是字符串，或者数组!");
        }
    },
    GetCoord: function (nIdx) {
        return this.handle.GetCoord(nIdx);
    },
    GetCoordxyz: function (cIdx, nIdx) {
        return this.handle.GetCoordxyz(cIdx, nIdx);
    },
    GetCoordsCount: function () {
        return this.handle.GetCoordsCount();
    },
    Clear: function () {
        this.handle.Clear();
    }
});

Qmap.Object3D.QMap3DVectorMillArrowList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});



//QMap3DMIDynamic
Qmap.Object3D.QMap3DMIDynamic = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["TransUnit", "RotateUnit", "Trans", "TransAngle", "RotateCenter", "AniLoop"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DActiveX.QMap3DMIDynamic");
        this.init(options);
    },
    init: function (options) {
        this.options = {
            "transunit": this.Method[0],
            "rotateunit": this.Method[1],
            "trans": this.Method[2],
            "transangle": this.Method[3],
            "rotatecenter": this.Method[4],
            "aniloop": this.Method[5]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetTransUnit: function (dbVal) {
        this.handle.SetTransUnit(dbVal);
    },
    GetRotateUnit: function () {
        return this.handle.GetRotateUnit();
    },
    SetRotateUnit: function (nVal) {
        this.handle.SetRotateUnit(nVal);
    },
    GetRotateUnit: function () {
        return this.handle.GetRotateUnit();
    },
    SetTrans: function (value) {
        if (Object.isArray(value)) {
            for (var i = 0; i < value.length; i++) {
                var parms = value[i].split(",");
                this.handle.AddTrans(parms[0], parms[2], parms[1], 0);
            }
        } else if (Object.isString(value)) {
            if (value.indexOf(";") > -1)//说明是用;进行分割的
            {
                var parmsList = value.split(";");
                for (var i = 0, length = parmsList.length; i < length; i++) {
                    var parms = parmsList[i].split(",");
                    this.handle.AddTrans(parms[0], parms[2], parms[1], 0);
                }

            } else //用,进行分割的
            {
                var parmsList = value.split(",");
                for (var i = 0, length = parmsList.length; i < length; i += 3) {
                    this.handle.AddTrans(parmsList[i], parmsList[i + 2], parmsList[i + 1], 0);
                }
            }
        } else {
            alert("参数必须是字符串，或者数组!");
        }
    },
    GetTrans: function (idx, npos) {
        return this.handle.GetTrans(idx, npos);
    },
    GetTransSize: function () {
        this.handle.GetTransSize();
    },
    ClearTrans: function () {
        return this.handle.ClearTrans();
    },
    SetTransAngle: function (value) {
        if (Object.isArray(value)) {
            for (var i = 0; i < value.length; i++) {
                var parms = value[i].split(",");
                this.handle.AddTransAngle(parms[0], parms[1], 0, 0);
            }
        } else if (Object.isString(value)) {
            if (value.indexOf(";") > -1)//说明是用;进行分割的
            {
                var parmsList = value.split(";");
                for (var i = 0, length = parmsList.length; i < length; i++) {
                    var parms = parmsList[i].split(",");
                    this.handle.AddTransAngle(parms[0], parms[1], 0, 0);
                }

            } else //用,进行分割的
            {
                var parmsList = value.split(",");
                for (var i = 0, length = parmsList.length; i < length; i += 2) {
                    this.handle.AddTransAngle(parmsList[i], parmsList[i + 1], 0, 0);
                }
            }
        } else {
            alert("参数必须是字符串，或者数组!");
        }
    },
    GetTransAngle: function (idx, npos) {
        return this.handle.GetTransAngle(idx, npos);
    },
    GetTransAngleSize: function () {
        this.handle.GetTransAngleSize();
    },
    ClearTransAngle: function () {
        return this.handle.ClearTransAngle();
    },
    SetRotateCenter: function (xyz) {
        var val = xyz.split(",");
        return this.handle.SetRotateCenter(val[0], val[1], val[2]);
    },
    GetRotateCenter: function (npos) {
        return this.handle.GetRotateCenter(npos);
    },
    SetAniLoop: function (bLoop) {
        return this.handle.SetAniLoop(bLoop);
    },
    GetAniLoop: function () {
        return this.handle.GetAniLoop();
    }
});

Qmap.Object3D.QMap3DMIDynamicList = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});




//QMap3DColorRGBA
Qmap.Object3D.QMap3DColorRGBA = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["R", "G", "B", "A"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DActiveX.QMap3DColorRGBA");
        this.init(options);
    },
    init: function (options) {
        this.options = {
            "r": this.Method[0],
            "g": this.Method[1],
            "b": this.Method[2],
            "a": this.Method[3]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetR: function (r) {
        this.handle.r = r;
    },
    GetR: function () {
        return this.handle.r;
    },
    SetG: function (g) {
        this.handle.g = g;
    },
    GetG: function () {
        return this.handle.g;
    },
    SetB: function (b) {
        this.handle.b = b;
    },
    GetB: function () {
        return this.handle.b;
    },
    SetA: function (a) {
        this.handle.a = a;
    },
    GetA: function () {
        return this.handle.a;
    }
});

Qmap.Object3D.QMap3DColorRGBA = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});



//QMap3DVector2D
Qmap.Object3D.QMap3DVector2D = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["X", "Y"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DActiveX.QMap3DVector2D");
        this.init(options);
    },
    init: function (options) {
        this.options = {
            "x": this.Method[0],
            "y": this.Method[1]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetX: function (x) {
        this.handle.x = x;
    },
    GetX: function () {
        return this.handle.x;
    },
    SetY: function (y) {
        this.handle.y = y;
    },
    GetY: function () {
        return this.handle.y;
    }
});

Qmap.Object3D.QMap3DVector2D = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});



//QMap3DTexSource
Qmap.Object3D.QMap3DTexSource = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["DataType", "TexData", "TexWidth", "TexHeight"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DActiveX.QMap3DTexSource");
        this.init(options);
    },
    init: function (options) {
        this.options = {
            "datatype": this.Method[0],
            "texdata": this.Method[1],
            "texwidth": this.Method[2],
            "texheight": this.Method[3]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetDataType: function (dType) {
        this.handle.SetDataType(dType);
    },
    GetDataType: function () {
        return this.handle.GetDataType();
    },
    SetTexData: function (TexData) {
        this.handle.SetTexData(TexData);
    },
    GetTexData: function () {
        return this.handle.GetTexData();
    },
    SetTexWidth: function (Width) {
        this.handle.SetTexWidth(Width);
    },
    GetTexWidth: function () {
        return this.handle.GetTexWidth();
    },
    SetTexHeight: function (Height) {
        this.handle.SetTexHeight(Height);
    },
    GetTexHeight: function () {
        return this.handle.GetTexHeight();
    }
});

Qmap.Object3D.QMap3DTexSource = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});



//QMap3DTexAnimate
Qmap.Object3D.QMap3DTexAnimate = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["SpeedU", "SpeedV", "SpeedRot", "RotOff", "SliceFrames", "SFramesPerRow", "SliceWidth", "SliceHeight", "SliceInterval"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DActiveX.QMap3DTexAnimate");
        this.init(options);
    },
    init: function (options) {
        this.options = {
            "speedu": this.Method[0],
            "speedv": this.Method[1],
            "speedrot": this.Method[2],
            "rotoff": this.Method[3],
            "sliceframes": this.Method[4],
            "sframesperrow": this.Method[5],
            "slicewidth": this.Method[6],
            "sliceheight": this.Method[7],
            "sliceinterval": this.Method[8]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetSpeedU: function (SpeedU) {
        this.handle.SetSpeedU(SpeedU);
    },
    GetSpeedU: function () {
        return this.handle.GetSpeedU();
    },
    SetSpeedV: function (SpeedV) {
        this.handle.SetSpeedV(SpeedV);
    },
    GetSpeedV: function () {
        return this.handle.GetSpeedV();
    },
    SetSpeedRot: function (SpeedRot) {
        this.handle.SetSpeedRot(SpeedRot);
    },
    GetSpeedRot: function () {
        return this.handle.GetSpeedRot();
    },
    SetRotOff: function (RotOffX, RotOffY) {
        this.handle.SetRotOff(RotOffX, RotOffY);
    },
    GetRotOff: function () {
        return this.handle.GetRotOff();
    },
    SetSliceFrames: function (SliceFrames) {
        this.handle.SetSliceFrames(SliceFrames);
    },
    GetSliceFrames: function () {
        return this.handle.GetSliceFrames();
    },
    SetSFramesPerRow: function (SFramesPerRow) {
        this.handle.SetSFramesPerRow(SFramesPerRow);
    },
    GetSFramesPerRow: function () {
        return this.handle.GetSFramesPerRow();
    },
    SetSliceWidth: function (SliceWidth) {
        this.handle.SetSliceWidth(SliceWidth);
    },
    GetSliceWidth: function () {
        return this.handle.GetSliceWidth();
    },
    SetSliceHeight: function (SliceHeight) {
        this.handle.SetSliceHeight(SliceHeight);
    },
    GetSliceHeight: function () {
        return this.handle.GetSliceHeight();
    },
    SetSliceInterval: function (SliceInterval) {
        this.handle.SetSliceInterval(SliceInterval);
    },
    GetSliceInterval: function () {
        return this.handle.GetSliceInterval();
    }
});

Qmap.Object3D.QMap3DTexAnimate = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});



//QMap3DEnvTexData
Qmap.Object3D.QMap3DEnvTexData = Qmap.Class(Qmap.Object3D.Base, {
    objid: 0,
    handle: null,
    //方法名的key
    Method: ["EnvTexType", "TexName", "ReflectColor", "RefractColor", "Eta", "FresnelPower"],
    //初始化的构造函数
    initialize: function (options) {
        this.createObj("QMap3DActiveX.QMap3DEnvTexData");
        this.init(options);
    },
    init: function (options) {
        this.options = {
            "envtextype": this.Method[0],
            "texname": this.Method[1],
            "reflectcolor": this.Method[2],
            "refractcolor": this.Method[3],
            "eta": this.Method[4],
            "fresnelpower": this.Method[5]
        };
        Qmap.Object3D.Base.prototype.initialize.apply(this);
        Qmap.Object3D.Base.prototype.bindingValue.call(this, options);
    },
    SetEnvTexType: function (TexType) {
        this.handle.SetEnvTexType(TexType);
    },
    GetEnvTexType: function () {
        return this.handle.GetEnvTexType();
    },
    SetTexName: function (TexName) {
        this.handle.SetTexName(TexName);
    },
    GetTexName: function () {
        return this.handle.GetTexName();
    },
    SetReflectColor: function (colors) {
        var colors = color.split(",");
        this.handle.SetReflectColor(colors[0], colors[1], colors[2], colors[3]);
    },
    GetReflectColor: function () {
        return this.handle.GetReflectColor();
    },
    SetRefractColor: function (colors) {
        var colors = color.split(",");
        this.handle.SetRefractColor(colors[0], colors[1], colors[2], colors[3]);
    },
    GetRefractColor: function () {
        return this.handle.GetRefractColor();
    },
    SetEta: function (Eta) {
        this.handle.SetEta(Eta);
    },
    GetEta: function () {
        return this.handle.GetEta();
    },
    SetFresnelPower: function (FresnelPower) {
        this.handle.SetFresnelPower(FresnelPower);
    },
    GetFresnelPower: function () {
        return this.handle.GetFresnelPower();
    }
});

Qmap.Object3D.QMap3DEnvTexData = Qmap.Class(Enumerable, Qmap.Object3D.ListBase, {});