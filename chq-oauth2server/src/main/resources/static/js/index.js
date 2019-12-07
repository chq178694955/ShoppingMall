var KingIndex = {

    loadLeftMenus: function(){
        var datas = [{
            "id":1,
            "text":"Folder1",
            "iconCls":"icon-save",
            "children":[{
                "text":"File1",
                "checked":true
            },{
                "text":"Books",
                "state":"open",
                "attributes":{
                    "url":"/demo/book/abc",
                    "price":100
                },
                "children":[{
                    "text":"PhotoShop",
                    "checked":true
                },{
                    "id": 8,
                    "text":"Sub Bookds",
                    "state":"closed"
                }]
            }]
        },{
            "text":"Languages",
            "state":"closed",
            "children":[{
                "text":"Java"
            },{
                "text":"C#"
            }]
        }];

        var accordionObj = Frame.createAccordion('index_resources')
        Frame.addAccordionItem('index_resources',{id:'menuItem_1',title:'标题1',content:'内容1',selected:true})
        Frame.addAccordionItem('index_resources',{id:'menuItem_2',title:'标题2',content:'内容2'})
        Frame.addAccordionItem('index_resources',{title:'标题3',content:'内容3'})
        Frame.createTree('menuItem_1',{},datas)
        Frame.createTree('menuItem_2',{},datas)
    }

}

$(document).ready(function(){
    $('#index_btn_logout').bind('click',function(){
        Frame.removeAccordionItem('index_resources')
    })
    KingIndex.loadLeftMenus();
});