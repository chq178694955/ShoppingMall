/**
 * Map数据模型.
 */
DataBox = function() {   
     this.datas = new Array();   
     // 添加   
     this.put = function(_key,_value){   
        if(this.containsKey(_key)){   
            this.remove(_key);   
        }   
       this.datas.push({key:_key,value:_value});   
     };   
     // 取值   
    this.get = function(_key){   
        var rtn = null;   
        try  {   
            for(var i=0;i<this.datas.length;i++){   
               if(this.datas[i].key==_key){   
                   rtn = this.datas[i].value;   
                   break;   
                }   
            }   
       } catch (e) {   
           rtn = null;   
       }   
       return rtn;   
     };   
     // 删除   
    this.remove = function(_key){   
       var btn = false;   
       try {   
           for(var i=0;i<this.datas.length;i++){   
               if(this.datas[i].key == _key){   
                   this.datas.splice(i,1);   
                   return true;   
               }   
           }   
       }   
        catch (e) {   
            btn = false;   
        }   
       return btn;   
  
     };   
     // 判断是否存在key   
     this.containsKey = function(_key) {     
        var bln = false;     
       try {     
            for (var i = 0; i < this.datas.length; i++) {     
                if (this.datas[i].key == _key) {     
                    bln = true;     
                    break;   
                }     
            }     
        } catch (e) {     
            bln = false;     
        }     
       return bln;     
   };   
    // 清空   
   this.clear = function(){   
       this.datas = new Array();   
   };   
   
   // 所有元素
   this.keyVals = function(){
	   return this.datas.concat();
   };
};   