window.Frame = window.Frame || {

    menuRoot: [],//存放菜单数据

    //创建手风琴对象
    createAccordion: function(domId,opts){
        opts = opts == undefined ? {} : opts;
        var domObj = $('#' + domId).accordion({
            width: opts.width != undefined ? opts.width : 'auto',
            height: opts.height != undefined ? opts.height : 'auth',
            fit: opts.fit != undefined ? opts.fit : false,
            animate: opts.animate != undefined ? opts.animate : true,
            multiple: opts.multiple != undefined ? opts.multiple : false,
            selected: opts.selected != undefined ? opts.selected : 0,
            halign: opts.halign != undefined ? opts.halign : 'top'
        })
        return domObj;
    },

    //添加手风琴子对象
    addAccordionItem: function(domId,opts){
        opts = opts == undefined ? {} : opts;
        $('#' + domId).accordion('add',{
            id: opts.id != undefined ? opts.id : '',
            title: opts.title != undefined ? opts.title : '',
            content: opts.content != undefined ? opts.content :'',
            selected: opts.selected != undefined ? opts.selected :false
        })

    },

    //移出手风琴子对象
    removeAccordionItem: function(domId,index){
        index = index == undefined ? 0 : index;
        var panels = $('#' + domId).accordion('panels');
        if(panels.length == 0)API.alert('提示','没有需要移除的子项')
        $('#' + domId).accordion('remove',0);
    },

    //创建一个combotree
    createTree: function(domId,opts,datas){
        opts = opts != undefined ? opts : {}
        var tree = $('#'+ domId).tree({
            data: datas != undefined ? datas : [],
            onClick: opts.clickListener != null ? opts.clickListener : null
        })
        return tree;
    },

    createTabItem: function(domId,opts,callback){
        this.Tabs.add(domId,opts);
        if(typeof(callback) == 'function')callback();
    },

    refreshTabItem: function(domId,opts){
        let tab = this.Tabs.getTab(domId,opts.title);
        this.Tabs.update(domId,tab,opts);
    },

    getCurTabId: function(){
        let domId = 'index_content';
        let tab = this.Tabs.getSelectedTab(domId);
        return $(tab).attr('id');
    },

    getCurTab: function(){
        let domId = 'index_content';
        let tab = this.Tabs.getSelectedTab(domId);
        return tab;
    }

}

Frame.Tabs = Frame.Tabs || {

    maxTab : 8,//最大展开tab数

    exists: function(domId,title){
        return $('#' + domId).tabs('exists',title)
    },

    getTab: function(domId,title){
        let tab = $('#' + domId).tabs('getTab',title);
        return tab;
    },

    getTabIndex: function(domId, tab){
        return $('#' + domId).tabs('getTabIndex', tab);
    },

    getSelectedTab: function(domId){
        return $('#' +domId).tabs('getSelected');
    },

    close: function(domId,title){
        $('#' + domId).tabs('close',title);
    },

    add: function(domId,opts){
        let panels = $('#' + domId).tabs('tabs');
        if(panels.length > this.maxTab){
            API.alert(API.I18n.get('com.king.system.alert.title.warn'),API.I18n.get('com.king.system.overMaxTab'))
            return ;
        }

        if(!this.exists(domId,opts.title)){
            $('#' + domId).tabs('add',opts);
        }else{
            //存在则选中当前面板
            $('#' + domId).tabs('select',opts.title);
        }
    },

    update: function(domId,tab,opts){
        $('#' + domId).tabs('update',{
            tab: tab,
            options: opts
        })
    }

}