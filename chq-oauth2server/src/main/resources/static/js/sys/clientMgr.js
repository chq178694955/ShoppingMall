function ClientMgr() {

    var that = this;

    this.init = function(){
        $('#dg_clientMgr' + Frame.getCurId()).datagrid({
            toolbar: [{
                text: API.I18n.get('com.king.system.btn.add'),
                iconCls: 'icon-add',
                handler: this.add
            },'-',{
                text: API.I18n.get('com.king.system.btn.update'),
                iconCls: 'icon-edit',
                handler: this.edit
            },'-',{
                text: API.I18n.get('com.king.system.btn.del'),
                iconCls: 'icon-remove',
                handler: this.del
            },'-',{
                text: API.I18n.get('com.king.system.btn.query'),
                iconCls: 'icon-search',
                handler: this.query
            }]
        });

        $('#save_clientMgr' + Frame.getCurId()).bind('click',this.save);
        $('#reset_clientMgr' + Frame.getCurId()).bind('click',this.reset);

        this.query();
    }

    this.save = function(){
        let clientName = $('#clientName' + Frame.getCurId()).textbox('getValue');
        if(API.isNull(clientName)){
            API.alertError(API.I18n.get('com.king.system.client.name.empty'))
            return ;
        }
        $.ajax({
            url: API.ROOT + 'sys/client/save',
            data:{
                id: $('#id' + Frame.getCurId()).val(),
                clientName: clientName,
                clientId:  $('#clientKey' + Frame.getCurId()).textbox('getValue'),
                clientSecret: $('#clientSecret' + Frame.getCurId()).textbox('getValue')
            },
            success: function(result){
                if(API.isNotNull(result)){
                    if(result.code == 0){
                        API.alertInfo(result.msg);
                        that.reset();
                        that.query();
                    }else{
                        API.alertError(result.msg);
                    }
                }
            }
        });
    }

    this.del = function(){
        let row = $('#dg_clientMgr' + Frame.getCurId()).datagrid('getSelected');
        if(row == null){
            API.alertInfo(API.I18n.get('com.king.system.selectRow'));
            return ;
        }else{
            $.ajax({
                url: API.ROOT + 'sys/client/del',
                data:{
                    id: row.id
                },
                success: function(result){
                    if(API.isNotNull(result)){
                        if(result.code == 0){
                            API.alertInfo(result.msg);
                            that.query();
                        }else{
                            API.alertError(result.msg);
                        }
                    }
                }
            });
        }
    }

    this.reset = function(){
        $('#id' + Frame.getCurId()).val('');
        $('#clientName' + Frame.getCurId()).textbox('reset')
        $('#clientKey' + Frame.getCurId()).textbox('reset')
        $('#clientSecret' + Frame.getCurId()).textbox('reset')
    }

    this.query = function(){
        $('#dg_clientMgr' + Frame.getCurId()).datagrid({
            url: API.ROOT + 'sys/client/query'
        })
    }

    this.cancel = function(){
        $('#ly_clientMgr' + Frame.getCurId()).layout('collapse','south');
    }

    this.add = function(){
        that.reset();
        $('#ly_clientMgr' + Frame.getCurId()).layout('expand','south');
    }

    this.edit = function(){
        let row = $('#dg_clientMgr' + Frame.getCurId()).datagrid('getSelected');
        if(row == null){
            API.alertInfo(API.I18n.get('com.king.system.selectRow'));
            return ;
        }else{
            $('#id' + Frame.getCurId()).val(row.id);
            $('#clientName' + Frame.getCurId()).textbox('setValue',row.clientName)
            $('#clientKey' + Frame.getCurId()).textbox('setValue',row.clientId)
            $('#clientSecret' + Frame.getCurId()).textbox('setValue',row.clientSecret)
            $('#ly_clientMgr' + Frame.getCurId()).layout('expand','south');
        }
    }

}