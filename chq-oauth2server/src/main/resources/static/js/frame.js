window.Frame = window.Frame || {

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
            data: datas != undefined ? datas : []
        })
        return tree;
    }

}