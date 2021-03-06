var KingIndex = {

    loadLeftMenus: function(){
        if(Frame.menuRoot.length > 0){
            Frame.createAccordion('index_resources')
            for(let i = 0;i < Frame.menuRoot.length; i++){
                let menu = Frame.menuRoot[i];
                let selected = true;
                if(i > 0)selected = false;
                Frame.addAccordionItem('index_resources',{id:'menuItem_' + menu.id,title:menu.text,content:'',selected:selected})
                if(menu.children.length > 0){
                    Frame.createTree('menuItem_' + menu.id,{clickListener: KingIndex.menuClick},menu.children)
                }
            }
        }else{
            API.alert(API.I18n.get('com.king.system.alert.title.info'),API.I18n.get('com.king.system.nodata'))
        }
    },

    menuClick: function(node){
        let rootId = node.rootId;
        let parentTitle = $('#menuItem_' + rootId).panel('options').title;
        let textAry = [];
        KingIndex.getParentMenu('menuItem_' + rootId,node,textAry,rootId);
        textAry.reverse();//倒序数组

        //渲染面包屑导航
        let strCrumb = API.I18n.get('com.king.system.crumb.curPosition') + parentTitle;
        for(let i=0;i<textAry.length;i++){
            strCrumb += ' >> ' + textAry[i];
        }
        strCrumb += ' >> <b>' + node.text + '</b>';
        $('#index_crumb').html(strCrumb);

        //index_content
        Frame.createTabItem('index_content',{id:'tabItem_' + node.id,title:node.text,closable:true},function(){
            KingIndex.loadTabContent(node)
        });
    },

    getParentMenu: function(treeDomId,node,ary,rootId){
        let parentNode = $('#' + treeDomId).tree('getParent',node.target);
        if(parentNode != null){
            ary.push(parentNode.text);
            if(parentNode.id != rootId){
                KingIndex.getParentMenu(treeDomId,parentNode,ary,rootId);
            }
        }
    },

    loadTabContent: function(node){
        let text = node.text;
        let url = node.url;
        $.ajax({
            url: API.ROOT + url + '?menuId=' + node.id,
            type: 'get',
            success: function(data){
                if(data){
                    Frame.refreshTabItem('index_content',{title:text,content:data})
                }
            }
        });

    }

}

$(document).ready(function(){
    //tab关闭后相应事件
    $('#index_content').tabs({
        onClose: function(title,index){
            //渲染面包屑导航
            let strCrumb = API.I18n.get('com.king.system.crumb.curPosition');
            $('#index_crumb').html(strCrumb);
        }
    });
    //退出
    $('#index_btn_logout').bind('click',function(){
        location.href = API.ROOT + 'logout';
    })
    //加载菜单
    KingIndex.loadLeftMenus();
});