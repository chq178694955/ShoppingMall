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
    }
}