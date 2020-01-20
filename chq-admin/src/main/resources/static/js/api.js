window.API = window.API || {
    //系统全路径
    ROOT : '/',
    //本地语言 zh_CN
    LANG_COUNTRY :'',
    //错误信息
    errMsg : '',
    //系统需要加载的资源文件名称
    messageNames : ['messages'],
    //初始化资源文件对象
    I18n: new DataBox(),
    //请求服务端读取资源文件并复制给map对象
    loadI18n: function(){
        $.ajax({
            url:API.ROOT + 'golbalLoadI18n',
            data:{filenames: API.messageNames.join(",")},
            dataType:'json',
            success:function(result){
                if(result && result.length > 0){
                    for(let i = 0;i < result.length ;i++){
                        let key = result[i][0];
                        let val = result[i][1];
                        API.I18n.put(key,val);
                    }
                }
            }
        })
    },

    isNull: function(obj){
        if(obj == undefined || obj == 'undefined' || obj == null || obj == '' || obj == 'null'){
            return true;
        }else{
            false;
        }
    },

    isNotNull: function(obj){
        if(obj != undefined && obj != 'undefined' && obj != null && obj != '' && obj != 'null'){
            return true;
        }else{
            false;
        }
    },

    alert: function(title,context){
        $.messager.alert(title,context);
    },
    alertInfo: function(content){
        $.messager.alert(API.I18n.get('com.king.system.alert.title.info'),content);
    },
    alertWarn: function(content){
        $.messager.alert(API.I18n.get('com.king.system.alert.title.warn'),content);
    },
    alertError: function(content){
        $.messager.alert(API.I18n.get('com.king.system.alert.title.error'),content);
    },

    //动态加载js文件
    loadJS: function(file,callback){
        if(file && file.id != null && file.url != null){
            const oldScript = document.getElementById("FUNC_" + Frame.getCurId());
            if(!oldScript){
                let script = document.createElement("script");
                callback = callback || function(){};
                script.id = file.id;
                script.type="text/javascript";
                script.src=API.ROOT + file.url;
                document.body.appendChild(script);
                //IE
                if(script.readyState){
                    script.onreadystatechange = function(){
                        if(script.readyState == 'loaded' || script.readyState == 'complete'){
                            script.onreadystatechange = null;
                            callback();
                        }
                    }
                }else{
                    //其他浏览器
                    document.getElementsByTagName('head')[0].appendChild(script);
                    script.onload = function(){
                        callback();
                    }
                }
            }else{
                let oldsrc = oldScript.src;
                let newsrc = API.ROOT + file.url;
                if(oldsrc == newsrc)callback();
            }
        }else{
            throw {message: API.I18n.get('com.king.system.loadJS.error')};
        }
    }

}

API.Date = {

    fmt1: "yyyy-MM-dd HH:mm:ss",
    fmt2: "yyyy-MM-dd",
    fmt3: "yyyy年MM月dd日",
    fmt4: "HH:mm",
    fmt5: "HH时mm分",

    /**
     * 格式化日期为字符串.
     * @param date 日期
     * @param fmt 日期格式
     * @returns 日期字符串
     */
    formatDate : function(date, fmt) {
        date = (typeof date == 'number') ? new Date(date) : date;
        fmt = fmt || 'yyyy-MM-dd HH:mm:ss';
        var obj = {
            'y': date.getFullYear(), // 年份，注意必须用getFullYear
            'M': date.getMonth() + 1, // 月份，注意是从0-11
            'd': date.getDate(), // 日期
            'q': Math.floor((date.getMonth() + 3) / 3), // 季度
            'w': date.getDay(), // 星期，注意是0-6
            'H': date.getHours(), // 24小时制
            'h': date.getHours() % 12 == 0 ? 12 : date.getHours() % 12, // 12小时制
            'm': date.getMinutes(), // 分钟
            's': date.getSeconds(), // 秒
            'S': date.getMilliseconds() // 毫秒
        };
        for(var i in obj) {
            fmt = fmt.replace(new RegExp(i+'+', 'g'), function(m) {
                var val = obj[i] + '';
                for(var j = 0, len = val.length; j < m.length - len; j++) {
                    val = '0' + val;
                }
                return m.length == 1 ? val
                    : val.substring(val.length - m.length);
            });
        }
        return fmt;
    },

    /**
     * 解析字符串为日期.
     * @param dateStr 日期字符串
     * @param fmt 日期格式
     * @returns {Date} 日期
     */
    parseDate : function(dateStr, fmt) {
        if(dateStr instanceof Array) {
            dateStr = dateStr[0];
        }
        fmt = fmt || 'yyyy-MM-dd';
        var obj = {y: 0, M: 1, d: 0, H: 0, h: 0, m: 0, s: 0, S: 0};
        fmt.replace(/([^yMdHmsS]*?)(([yMdHmsS])\3*)([^yMdHmsS]*?)/g, function(m, $1, $2, $3, $4, idx, old) {
            dateStr = dateStr.replace(new RegExp($1+'(\\d{'+$2.length+'})'+$4), function(_m, _$1) {
                obj[$3] = parseInt(_$1);
                return '';
            });
            return '';
        });
        obj.M--; // 月份是从0开始的，所以要减去1
        var date = new Date(obj.y, obj.M, obj.d, obj.H, obj.m, obj.s);
        if(obj.S !== 0) {
            date.setMilliseconds(obj.S); // 如果设置了毫秒
        }
        return date;
    }

}