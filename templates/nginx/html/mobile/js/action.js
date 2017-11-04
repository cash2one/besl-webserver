//头图轮播
var action={
	'toolsInfo':function(){
		action.checkTopLoop();
	},
	/* 头图轮播 */
	'checkTopLoop':function(){
		if ($('#pic-loop .swiper-slide').length > 1){
			var mySwiper = new Swiper('#pic-loop',{
				loop: true,
				pagination: '.pagination',
				paginationClickable: false,
				mode: 'horizontal',  //水平
				cssWidthAndHeight: true
			});
			setInterval(function(){mySwiper.swipeNext()}, 5000);
		}
	}
}
action.toolsInfo();	
//头图轮播end

//倒计时
function timeOut(parent,time){
    var parents=parent;
    var timeNum=time;
    var times=null;
    var daybox=parents.children('.day'),
        hourbox=parents.children('.hour'),
        minutebox=parents.children('.minute'),
        secondbox=parents.children('.second');
    function zero(num){
        if(num<10){
            return '0'+num;
        }else if(num>=10){
            return ''+num;
        }
    };
    times=setInterval(function(){
      checkTime();
    },1000);
    function checkTime(){
        var future= Date.parse(timeNum);
        var now=new Date();
        var nowTime=now.getTime();
        var mistiming=(future-nowTime)/1000,
            d=zero(parseInt(mistiming/86400)),
            h=zero(parseInt((mistiming)/3600)),
            f=zero(parseInt((mistiming%86400%3600)/60)),
            m=zero(parseInt(mistiming%86400%360%60));
        getTimes(d,h,f,m);
        if(future<=nowTime){
          clearInterval(times);
          setTimes();
          return false;
        };
    };
    function getTimes(d,h,f,m){
        daybox.children('code').eq(0).text(d.substr(0,1));
        daybox.children('code').eq(1).text(d.substr(1,1));
        hourbox.children('code').eq(0).text(h.substr(0,1));
        hourbox.children('code').eq(1).text(h.substr(1,1));
        minutebox.children('code').eq(0).text(f.substr(0,1));
        minutebox.children('code').eq(1).text(f.substr(1,1));
        secondbox.children('code').eq(0).text(m.substr(0,1));
        secondbox.children('code').eq(1).text(m.substr(1,1));
    };
    function setTimes(){
        daybox.children('code').eq(0).text(0);
        daybox.children('code').eq(1).text(0);
        hourbox.children('code').eq(0).text(0);
        hourbox.children('code').eq(1).text(0);
        minutebox.children('code').eq(0).text(0);
        minutebox.children('code').eq(1).text(0);
        secondbox.children('code').eq(0).text(0);
        secondbox.children('code').eq(1).text(0);
    }
};

/*倒计时end*/


/*进度条*/
function progress(){
    $('.surplus').each(function(){
       var sw=$(this).width();
       var oCite=$(this).find('.sur-top').width();
       var oNin=$(this).find('.sur-top-val');
       var len=sw*0.65;
       if(oCite>len){
          oNin.addClass('progress-tiao');
       }else{
          oNin.removeClass('progress-tiao');
       }
    });
};
progress();
/*进度条end*/

//换一换1
function addclass(){
var wp=$(window).width(),
    oUl=$('.big-box1').find('.tab-box1'),
    oli=$('.big-box1').find('.com-tab1'),
    len = oli.length,
    iNow=0,
    index=1;

  $.fn.imgFade = function (idx) {   
       return this.each(function () {
            var _this=$(this);
            var iNow=0;
            var times=null;
            var comTab1=null;
            //换一换点击
            _this.on('touchstart', function(e){
              
              iNow++;
              if(iNow>2){
                iNow=0;
              };

              comTab1=$(this).parents('h2').siblings('.big-box1').find('.com-tab1');
              clearTimeout(times);
              function showFn(){
                comTab1.eq(iNow).addClass('tabDis').siblings('.com-tab1').removeClass('tabDis');
                times=setTimeout(function(){
                  comTab1.eq(iNow).addClass('tab-block').siblings('.com-tab1').removeClass('tab-block');
                },50);
              }
              showFn();
              e.preventDefault();
            });           
        });
    } 
$('.next-group1').imgFade();
}
addclass();
/*换一换end*/

/*关闭下载*/
function clsoeDownload(){
    $('.closeImg').on('touchstart',function(){
      $('.download').hide();
    })
}
clsoeDownload();
/*关闭下载end*/