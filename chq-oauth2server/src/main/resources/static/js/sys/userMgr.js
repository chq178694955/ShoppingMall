function UserMgr() {

    var that = this;

    this.init = function(){
        $('#dg_roleMgr' + Frame.getCurId()).datagrid({
            toolbar:[[
                {
                    text: API.I18n.get('com.king.system.btn.query'),
                    iconCls: 'icon-search',
                    handler: this.queryRole
                }
            ]]
        });
        $('#dg' + Frame.getCurId()).datagrid({
            columns:[[
                {width:100,field:'username',align:'center',halign:'center',title:API.I18n.get('com.king.system.user.username')},
                {width:100,field:'password',align:'center',halign:'center',title:API.I18n.get('com.king.system.user.password')},
                {width:100,field:'name',align:'center',halign:'center',title:API.I18n.get('com.king.system.user.name')},
                {width:100,field:'idCardNum',align:'center',halign:'center',title:API.I18n.get('com.king.system.user.idCardNum')},
                {width:100,field:'state',align:'center',halign:'center',title:API.I18n.get('com.king.system.user.state'),
                    formatter:function(value,row,index){
                        if(0 == value){
                            return API.I18n.get('com.king.system.user.state.normal')
                        }else if(1 == value){
                            return API.I18n.get('com.king.system.user.state.locked')
                        }else{
                            return '';
                        }
                    }},
                {width:100,field:'roleName',align:'center',halign:'center',title:API.I18n.get('com.king.system.role.name')}
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
            }]
        });

        $('#state' + Frame.getCurId()).combobox({
            valueField:'val',
            textField:'txt',
            data:[{val:0,txt:API.I18n.get('com.king.system.user.state.normal')},{val:1,txt:API.I18n.get('com.king.system.user.state.locked')}],
            value:0
        })

        $('#save' + Frame.getCurId()).bind('click',this.save);
        $('#reset' + Frame.getCurId()).bind('click',this.reset);

        this.query();
        this.queryRole();
    }

    this.save = function(){
        let username = $('#username' + Frame.getCurId()).textbox('getValue')
        let password = $('#password' + Frame.getCurId()).textbox('getValue')
        let name = $('#name' + Frame.getCurId()).textbox('getValue')
        let idCardNum = $('#idCardNum' + Frame.getCurId()).textbox('getValue')
        let state = $('#state' + Frame.getCurId()).combobox('getValue');

        if(API.isNull(username)){
            API.alertError(API.I18n.get('com.king.sys.user.username.empty'))
            return ;
        }
        if(API.isNull(password)){
            API.alertError(API.I18n.get('com.king.sys.user.password.empty'))
            return ;
        }
        if(API.isNull(name)){
            API.alertError(API.I18n.get('com.king.sys.user.name.empty'))
            return ;
        }
        $.ajax({
            url: API.ROOT + 'sys/user/save',
            data:{
                id: $('#id' + Frame.getCurId()).val(),
                username: username,
                password:  password,
                name: name,
                idCardNum: idCardNum,
                state: state,
                roleId: $('#role' + Frame.getCurId()).combobox('getValue')
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
        let row = $('#dg' + Frame.getCurId()).datagrid('getSelected');
        if(row == null){
            API.alertInfo(API.I18n.get('com.king.system.selectRow'));
            return ;
        }else{
            $.ajax({
                url: API.ROOT + 'sys/user/del',
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
        $('#username' + Frame.getCurId()).textbox('reset')
        $('#password' + Frame.getCurId()).textbox('reset')
        $('#name' + Frame.getCurId()).textbox('reset')
        $('#idCardNum' + Frame.getCurId()).textbox('reset')
        $('#state' + Frame.getCurId()).combobox('setValue',0);
        $('#role' + Frame.getCurId()).combobox('reset');
    }

    this.query = function(){
        $('#dg' + Frame.getCurId()).datagrid({
            url: API.ROOT + 'sys/user/query'
        })
    }

    this.queryRole = function(){
        $.ajax({
            url: API.ROOT + 'sys/role/queryAll',
            success: function(data){
                $('#role' + Frame.getCurId()).combobox('loadData',data)
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
            $('#username' + Frame.getCurId()).textbox('setValue',row.username)
            $('#password' + Frame.getCurId()).textbox('setValue',row.password)
            $('#name' + Frame.getCurId()).textbox('setValue',row.name)
            $('#idCardNum' + Frame.getCurId()).textbox('setValue',row.idCardNum)
            $('#state' + Frame.getCurId()).combobox('setValue',row.accountStateEnum == 'NORMAL' ? 0 : 1);
            $('#role' + Frame.getCurId()).combobox('setValue',row.roleId);
        }
    }

}