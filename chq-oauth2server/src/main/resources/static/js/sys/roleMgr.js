function RoleMgr(){

    var that = this;

    this.init = function(){
        $('#dg' + Frame.getCurId()).datagrid({
            columns:[[
                {width:100,field:'name',align:'center',halign:'center',title:API.I18n.get('com.king.system.user.name')}
            ]],
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
            }],
            onSelect: function(index,row){
                let roleId = row.id;
                that.loadResources(roleId);
            }
        });

        $('#save' + Frame.getCurId()).bind('click',this.save);
        $('#reset' + Frame.getCurId()).bind('click',this.reset);
    }

    this.query = function(){
        $('#dg' + Frame.getCurId()).datagrid({
            url: API.ROOT + 'sys/role/query'
        })
    }

    this.reset = function(){
        $('#id' + Frame.getCurId()).val('');
        $('#name' + Frame.getCurId()).textbox('reset');
    }

    this.save = function(){
        let name = $('#name' + Frame.getCurId()).textbox('getValue');
        let checkNodes = $('#tree' + Frame.getCurId()).tree('getChecked');
        let checkNodeIds = '';
        for(let i=0;i<checkNodes.length;i++){
            if(i == 0){
                checkNodeIds += checkNodes[i].id;
            }else{
                checkNodeIds += ',' + checkNodes[i].id;
            }

        }

        if(API.isNull(name)){
            API.alertError(API.I18n.get('com.king.system.role.name.empty'));
            return ;
        }
        $.ajax({
            url: API.ROOT + 'sys/role/save',
            data:{
                id: $('#id' + Frame.getCurId()).val(),
                name: name,
                checkNodes: checkNodeIds
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

    this.add = function(){
        that.reset();
    }

    this.edit = function(){
        let row = $('#dg' + Frame.getCurId()).datagrid('getSelected');
        if(row == null){
            API.alertInfo(API.I18n.get('com.king.system.selectRow'));
            return ;
        }else{
            $('#id' + Frame.getCurId()).val(row.id);
            $('#name' + Frame.getCurId()).textbox('setValue',row.name);
        }
    }

    this.loadResources = function(roleId){
        $('#tree' + Frame.getCurId()).tree({
            animate:true,
            checkbox:true,
            lines:true,
            url: API.ROOT + 'sys/resource/all',
            queryParams: {
                roleId: roleId
            }
        });
    }

}