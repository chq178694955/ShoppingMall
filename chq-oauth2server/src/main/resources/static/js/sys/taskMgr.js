var TaskMgrUtil = {
    doTask: function(taskId,taskState){
        console.log("任务ID:" + taskId + "\t|任务状态：" + taskState);
        // 0-预备 1-开始 2-暂停 3-恢复 4-结束
        let url = API.ROOT;
        let flag = false;
        switch (taskState) {
            case 1:
                flag = true;
                url += 'sys/task/start';
                break;
            case 2:
                flag = true;
                url += 'sys/task/pause';
                break;
            case 3:
                flag = true;
                url += 'sys/task/resume';
                break;
            case 4:
                flag = true;
                url += 'sys/task/finish';
                break;
            default:
                break;
        }
        if(flag){
            $.ajax({
                url: url,
                data:{
                    taskId: taskId,
                    taskState: taskState
                },
                success: function(result){
                    if(API.isNotNull(result)){
                        if(result.code == 0){
                            API.alertInfo(result.msg);

                            $('#dg' + Frame.getCurId()).datagrid({
                                url: API.ROOT + 'sys/task/query'
                            })
                        }else{
                            API.alertError(result.msg);
                        }
                    }
                }
            });
        }else{
            console.log('操作有误!');
        }

    }
}

function TaskMgr() {

    var that = this;

    this.init = function(){

        $('#dg' + Frame.getCurId()).datagrid({
            columns:[[
                {width:100,field:'name',align:'center',halign:'center',title:API.I18n.get('com.king.system.task.name')},
                {width:100,field:'groupName',align:'center',halign:'center',title:API.I18n.get('com.king.system.task.groupName')},
                {width:100,field:'cronExpress',align:'center',halign:'center',title:API.I18n.get('com.king.system.task.express')},
                {width:100,field:'currentState',align:'center',halign:'center',title:API.I18n.get('com.king.system.task.currentState'),
                    formatter:function(value,row,index){
                        if(0 == value){
                            return API.I18n.get('com.king.system.task.state.ready')
                        }else if(1 == value){
                            return API.I18n.get('com.king.system.task.state.start')
                        }else if(2 == value){
                            return API.I18n.get('com.king.system.task.state.pause')
                        }else if(3 == value){
                            return API.I18n.get('com.king.system.task.state.resume')
                        }else if(4 == value){
                            return API.I18n.get('com.king.system.task.state.finish')
                        }else{
                            return '';
                        }
                    }
                },
                {width:100,field:'defaultState',align:'center',halign:'center',title:API.I18n.get('com.king.system.task.defaultState'),
                    formatter:function(value,row,index){
                        if(0 == value){
                            return API.I18n.get('com.king.system.task.state.ready')
                        }else if(1 == value){
                            return API.I18n.get('com.king.system.task.state.start')
                        }else if(2 == value){
                            return API.I18n.get('com.king.system.task.state.pause')
                        }else if(3 == value){
                            return API.I18n.get('com.king.system.task.state.resume')
                        }else if(4 == value){
                            return API.I18n.get('com.king.system.task.state.finish')
                        }else{
                            return '';
                        }
                     }
                 },
                {width:100,field:'lastTime',align:'center',halign:'center',title:API.I18n.get('com.king.system.task.lastTime'),
                    formatter: function(value,row,index){
                        let date = new Date(value * 1000);
                        return API.Date.formatDate(date,API.Date.fmt1);
                    }
                 },
                {width:100,field:'description',align:'center',halign:'center',title:API.I18n.get('com.king.system.task.description')},
                {width:200,field:'oper',align:'center',halign:'center',title:API.I18n.get('com.king.system.task.oper'),
                    formatter:function(value,row,index){
                        let taskId = row.id;
                        let currentState = row.currentState;
                        switch (currentState) {
                            case 0:
                                return '<a href="javascript:;" onclick="TaskMgrUtil.doTask('+taskId+',1)" class="easyui-linkbutton">'+API.I18n.get('com.king.system.task.state.start')+'</a>';
                            case 1:
                                return '<a href="javascript:;" onclick="TaskMgrUtil.doTask('+taskId+',0)" class="easyui-linkbutton">'+API.I18n.get('com.king.system.task.state.ready')+'</a>&nbsp;&nbsp;'
                                    +'<a href="javascript:;" onclick="TaskMgrUtil.doTask('+taskId+',2)" class="easyui-linkbutton">'+API.I18n.get('com.king.system.task.state.pause')+'</a>&nbsp;&nbsp;'
                                    +'<a href="javascript:;" onclick="TaskMgrUtil.doTask('+taskId+',4)" class="easyui-linkbutton">'+API.I18n.get('com.king.system.task.state.finish')+'</a>';
                            case 2:
                                return '<a href="javascript:;" onclick="TaskMgrUtil.doTask('+taskId+',0)" class="easyui-linkbutton">'+API.I18n.get('com.king.system.task.state.ready')+'</a>&nbsp;&nbsp;'
                                    +'<a href="javascript:;" onclick="TaskMgrUtil.doTask('+taskId+',3)" class="easyui-linkbutton">'+API.I18n.get('com.king.system.task.state.resume')+'</a>&nbsp;&nbsp;'
                                    +'<a href="javascript:;" onclick="TaskMgrUtil.doTask('+taskId+',4)" class="easyui-linkbutton">'+API.I18n.get('com.king.system.task.state.finish')+'</a>';
                            case 3:
                                return '<a href="javascript:;" onclick="TaskMgrUtil.doTask('+taskId+',0)" class="easyui-linkbutton">'+API.I18n.get('com.king.system.task.state.ready')+'</a>&nbsp;&nbsp;'
                                    +'<a href="javascript:;" onclick="TaskMgrUtil.doTask('+taskId+',2)" class="easyui-linkbutton">'+API.I18n.get('com.king.system.task.state.pause')+'</a>&nbsp;&nbsp;'
                                    +'<a href="javascript:;" onclick="TaskMgrUtil.doTask('+taskId+',4)" class="easyui-linkbutton">'+API.I18n.get('com.king.system.task.state.finish')+'</a>';
                            case 4:
                                return '<a href="javascript:;" onclick="TaskMgrUtil.doTask('+taskId+',0)" class="easyui-linkbutton">'+API.I18n.get('com.king.system.task.state.ready')+'</a>&nbsp;&nbsp;'
                                    + '<a href="javascript:;" onclick="TaskMgrUtil.doTask('+taskId+',1)" class="easyui-linkbutton">'+API.I18n.get('com.king.system.task.state.start')+'</a>&nbsp;&nbsp;'
                                    +'<a href="javascript:;" onclick="TaskMgrUtil.doTask('+taskId+',4)" class="easyui-linkbutton">'+API.I18n.get('com.king.system.task.state.finish')+'</a>';
                        }
                        // return '<a href="javascript:;" onclick="start()" class="easyui-linkbutton">'+API.I18n.get('com.king.system.task.state.ready')+'</a>&nbsp;&nbsp;'
                        //     + '<a href="javascript:;" class="easyui-linkbutton">'+API.I18n.get('com.king.system.task.state.start')+'</a>&nbsp;&nbsp;'
                        //     +'<a href="javascript:;" class="easyui-linkbutton">'+API.I18n.get('com.king.system.task.state.pause')+'</a>&nbsp;&nbsp;'
                        //     +'<a href="javascript:;" class="easyui-linkbutton">'+API.I18n.get('com.king.system.task.state.resume')+'</a>&nbsp;&nbsp;'
                        //     +'<a href="javascript:;" class="easyui-linkbutton">'+API.I18n.get('com.king.system.task.state.finish')+'</a>';
                    }
                }
            ]],
            toolbar: [{
                text: API.I18n.get('com.king.system.btn.add'),
                iconCls: 'icon-add',
                handler: this.add
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

        $('#currentState' + Frame.getCurId()).combobox({
            valueField:'val',
            textField:'txt',
            data:[{val:0,txt:API.I18n.get('com.king.system.task.state.ready')},{val:1,txt:API.I18n.get('com.king.system.task.state.start')},{val:2,txt:API.I18n.get('com.king.system.task.state.pause')},{val:3,txt:API.I18n.get('com.king.system.task.state.resume')},{val:4,txt:API.I18n.get('com.king.system.task.state.finish')}],
            value:0
        })
        $('#defaultState' + Frame.getCurId()).combobox({
            valueField:'val',
            textField:'txt',
            data:[{val:0,txt:API.I18n.get('com.king.system.task.state.ready')},{val:1,txt:API.I18n.get('com.king.system.task.state.start')},{val:2,txt:API.I18n.get('com.king.system.task.state.pause')},{val:3,txt:API.I18n.get('com.king.system.task.state.resume')},{val:4,txt:API.I18n.get('com.king.system.task.state.finish')}],
            value:0
        })

        $('#save' + Frame.getCurId()).bind('click',this.save);
        $('#reset' + Frame.getCurId()).bind('click',this.reset);

        this.query();
    }

    this.save = function(){
        let name = $('#name' + Frame.getCurId()).textbox('getValue')
        let groupName = $('#groupName' + Frame.getCurId()).textbox('getValue')
        let express = $('#express' + Frame.getCurId()).textbox('getValue')
        let currentState = $('#currentState' + Frame.getCurId()).combobox('getValue')
        let defaultState = $('#defaultState' + Frame.getCurId()).combobox('getValue');
        let description = $('#description' + Frame.getCurId()).textbox('getValue');

        if(API.isNull(name)){
            API.alertError(API.I18n.get('com.king.system.task.name.empty'))
            return ;
        }
        if(API.isNull(groupName)){
            API.alertError(API.I18n.get('com.king.system.task.groupName.empty'))
            return ;
        }
        if(API.isNull(express)){
            API.alertError(API.I18n.get('com.king.system.task.express.empty'))
            return ;
        }
        $.ajax({
            url: API.ROOT + 'sys/task/save',
            data:{
                id: $('#id' + Frame.getCurId()).val(),
                name: name,
                groupName:  groupName,
                express: express,
                currentState: currentState,
                defaultState: defaultState,
                description: description
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
        $('#name' + Frame.getCurId()).textbox('reset')
        $('#groupName' + Frame.getCurId()).textbox('reset')
        $('#express' + Frame.getCurId()).textbox('reset')
        $('#currentState' + Frame.getCurId()).combobox('setValue',0)
        $('#defaultState' + Frame.getCurId()).combobox('setValue',0);
        $('#description' + Frame.getCurId()).textbox('reset');
    }

    this.query = function(){
        $('#dg' + Frame.getCurId()).datagrid({
            url: API.ROOT + 'sys/task/query'
        })
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
            $('#name' + Frame.getCurId()).textbox('setValue',row.name)
            $('#groupName' + Frame.getCurId()).textbox('setValue',row.groupName)
            $('#express' + Frame.getCurId()).textbox('setValue',row.cronExpress)
            $('#currentState' + Frame.getCurId()).combobox('setValue',row.currentState)
            $('#defaultState' + Frame.getCurId()).combobox('setValue',row.defaultState);
            $('#description' + Frame.getCurId()).textbox('setValue',row.description);
        }
    }

}