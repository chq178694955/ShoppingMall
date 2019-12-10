function ClientMgr() {

    this.init = function(){
        $('#dg_clientMgr' + Frame.getCurId()).datagrid({
            toolbar: [{
                text: API.I18n.get('com.king.system.btn.add'),
                iconCls: 'icon-add',
                handler: this.add
            },'-',{
                text: API.I18n.get('com.king.system.btn.update'),
                iconCls: 'icon-edit'
            },'-',{
                text: API.I18n.get('com.king.system.btn.del'),
                iconCls: 'icon-remove'
            },'-',{
                text: API.I18n.get('com.king.system.btn.query'),
                iconCls: 'icon-search',
                handler: this.query
            }]
        });

        $('#query_clientMgr' + Frame.getCurId()).bind('click',this.query);
        $('#cancel_clientMgr' + Frame.getCurId()).bind('click',this.cancel);

        this.query();
    }

    this.query = function(){
        $('#dg_clientMgr' + Frame.getCurId()).datagrid({
            url: API.ROOT + 'sys/client/query'
        })
    }

    this.cancel = function(){
        $('#dlg_clientMgr' + Frame.getCurId()).dialog('close');
    }

    this.add = function(){
        $('#dlg_clientMgr' + Frame.getCurId()).dialog('open');
    }

}