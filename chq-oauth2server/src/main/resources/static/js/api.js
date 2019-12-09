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

    alert: function(title,context){
        $.messager.alert(title,context);
    },

    //动态加载js文件
    loadJS: function(file,callback){
        if(file && file.id != null && file.url != null){
            const oldScript = document.getElementById("FUNC_" + Frame.getCurTabId());
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