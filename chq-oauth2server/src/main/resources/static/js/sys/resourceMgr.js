function ResourceMgr(){

    var that = this;

    this.init = function(){
        this.query();
        this.loadTypeDict();

        $('#add' + Frame.getCurId()).bind('click',this.add);
        $('#save' + Frame.getCurId()).bind('click',this.save);
        $('#del' + Frame.getCurId()).bind('click',this.del);
    }

    this.loadTypeDict = function(){
        $.ajax({
            url: API.ROOT + 'sys/dict/findByClass',
            data: {
                classNo: Constants.resourceType,
                isMultiple: false
            },
            success: function(datas){
                $('#type' + Frame.getCurId()).combobox('loadData',datas);
                $('#type' + Frame.getCurId()).combobox('setValue',-1);
            }
        });
    }

    this.query = function(){
        $('#tree' + Frame.getCurId()).tree({
            animate:true,
            lines:true,
            url: API.ROOT + 'sys/resource/all',
            onSelect: function(node){
                if(API.isNotNull(node)){
                    let parent = $('#tree' + Frame.getCurId()).tree('getParent',node.target);
                    $('#pid' + Frame.getCurId()).val(node.pid);
                    $('#id' + Frame.getCurId()).val(node.id);
                    $('#name' + Frame.getCurId()).textbox('setValue',node.text);
                    $('#parentName' + Frame.getCurId()).textbox('setValue',API.isNull(parent) ?  '' : parent.text);
                    $('#url' + Frame.getCurId()).textbox('setValue',node.url);
                    $('#permission' + Frame.getCurId()).textbox('setValue',node.permission);
                    $('#type' + Frame.getCurId()).combobox('setValue',node.type);
                }
            }
        });
    }

    this.save = function(){
        let id = $('#id' + Frame.getCurId()).val();
        let pid = $('#pid' + Frame.getCurId()).val();
        let name = $('#name' + Frame.getCurId()).textbox('getValue');
        let type = $('#type' + Frame.getCurId()).combobox('getValue');
        let url = $('#url' + Frame.getCurId()).textbox('getValue');
        let permission = $('#permission' + Frame.getCurId()).textbox('getValue');

        if(API.isNull(name)){
            API.alertError(API.I18n.get('com.king.system.resource.name.empty'));
            return ;
        }
        $.ajax({
            url: API.ROOT + 'sys/resource/save',
            data:{
                id: id,
                name: name,
                pid: pid,
                type: type,
                url: url,
                permission: permission
            },
            success: function(result){
                if(API.isNotNull(result)){
                    if(result.code == 0){
                        API.alertInfo(result.msg);
                        that.query();
                        that.add();
                    }else{
                        API.alertError(result.msg);
                    }
                }
            }
        });
    }

    this.add = function(){
        let node = $('#tree' + Frame.getCurId()).tree('getSelected');
        if(API.isNull(node)){
            $('#id' + Frame.getCurId()).val('')
            $('#pid' + Frame.getCurId()).val('');
            $('#parentName' + Frame.getCurId()).textbox('reset');
            $('#name' + Frame.getCurId()).textbox('reset');
            $('#type' + Frame.getCurId()).combobox('setValue',-1);
            $('#url' + Frame.getCurId()).textbox('reset');
            $('#permission' + Frame.getCurId()).textbox('reset');
        }else{
            $('#id' + Frame.getCurId()).val('')
            $('#pid' + Frame.getCurId()).val(node.id);
            $('#parentName' + Frame.getCurId()).textbox('setValue',node.text);
            $('#name' + Frame.getCurId()).textbox('reset');
            $('#type' + Frame.getCurId()).combobox('setValue',-1);
            $('#url' + Frame.getCurId()).textbox('reset');
            $('#permission' + Frame.getCurId()).textbox('reset');
        }
    }

    this.del = function(){
        let node = $('#tree' + Frame.getCurId()).tree('getSelected');
        if(API.isNotNull(node)){
            let isLeaf = $('#tree' + Frame.getCurId()).tree('isLeaf',node.target);
            if(isLeaf){
                $.ajax({
                    url: API.ROOT + 'sys/resource/del',
                    data:{
                        id: node.id
                    },
                    success: function(result){
                        if(API.isNotNull(result)){
                            if(result.code == 0){
                                API.alertInfo(result.msg);
                                that.query();
                                that.add();
                            }else{
                                API.alertError(result.msg);
                            }
                        }
                    }
                });
            }else{
                API.alertInfo(API.I18n.get('com.king.system.tree.selectLeaf'));
                return ;
            }
        }else{
            API.alertInfo(API.I18n.get('com.king.system.tree.selectTreeNode'));
            return ;
        }
    }

}