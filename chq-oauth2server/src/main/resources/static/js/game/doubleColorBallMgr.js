function DoubleColorBallMgr(){

    var that = this;

    this.init = function(){
        $('#dcb' + Frame.getCurId()).panel({
            title:'发财靠你啦！',
            fit:true,
            tools:[{
                iconCls: 'icon-add',
                handler: this.createBalls
            },'-',{
                iconCls: 'icon-remove',
                handler: this.clearBalls
            }],
            content:'中奖吧！'
        });
    }

    this.createBalls = function(){
        $.ajax({
            url: API.ROOT + 'game/doubleColorBall/genBall',
            success: function(data){
                that.buildBallHtml(data);
            }
        })
    }

    this.buildBallHtml = function(data){
        let redBalls = data.KEY_RED_SORT;
        let blueBalls = data.KEY_BLUE;
        let ballHtml = "<ul class='king-game-ul'>";
        for(let i=0;i<redBalls.length;i++){
            ballHtml += "<li class='king-game-circle king-bg-red king-game-ul-li'>"+redBalls[i]+"</li>";
        }
        for(let i=0;i<blueBalls.length;i++){
            ballHtml += "<li class='king-game-circle king-bg-blue king-game-ul-li'>"+blueBalls[i]+"</li>";
        }
        ballHtml += "</ul>";
        $('#dcb' + Frame.getCurId()).panel({
            content: ballHtml
        });
    }

    this.clearBalls = function(){
        $('#dcb' + Frame.getCurId()).panel({
            content: '期待您中奖！'
        });
    }

}