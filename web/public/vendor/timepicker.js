/*! version : 1.0.2
 =========================================================
 tpicker.js
 https://github.com/sonukedia55
 Created by Sonu Kedia - 2019
 =========================================================
 */

 var fhr = 0; // Start from 0 for 24-hour format
 var fmi = 0;
 var showpicker = 0;
 var elid = 'none';
 
 function showpickers(a){
     if(showpicker){
         $('.tpicker').hide();
         showpicker=0;
     }else{
         elid = a;
         var x = $("#"+elid).offset();
         $('.tpicker').show();
         var kk = $("#"+elid).outerHeight();
         $('.tpicker').offset({ top: x.top+kk, left: x.left});
         showpicker=1;
     }
 }
 
 function updatetime(){
     $('#'+elid).val(("0" + fhr).slice(-2)+":"+("0" + fmi).slice(-2));
     $('.hrhere').html(("0" + fhr).slice(-2));
     $('.minhere').html(("0" + fmi).slice(-2));
 }
 
 $(function(){
 
     var pickerhtml = '<div class="tpicker"><div class="pk1"><div class="d-flex"><div class="hr"><i class="fa fa-angle-up hrup"></i><a class="hrhere">00</a><i class="fa fa-angle-down hrdown"></i></div><div class="dot2">:</div><div class="hr">	<i class="fa fa-angle-up minup"></i><a class="minhere">00</a><i class="fa fa-angle-down mindown"></i></div></div></div><div class="pk2 mintt"><table class="table table-bordered mintable"><tr><td>00</td><td>05</td><td>10</td><td>15</td></tr><tr><td>20</td><td>25</td><td>30</td><td>35</td></tr><tr><td>40</td><td>45</td><td>50</td><td>55</td></tr></table></div><div class="pk2 hrtt"><table class="table table-bordered hrtable"><tr><td>00</td><td>01</td><td>02</td><td>03</td></tr><tr><td>04</td><td>05</td><td>06</td><td>07</td></tr><tr><td>08</td><td>09</td><td>10</td><td>11</td></tr><tr><td>12</td><td>13</td><td>14</td><td>15</td></tr><tr><td>16</td><td>17</td><td>18</td><td>19</td></tr><tr><td>20</td><td>21</td><td>22</td><td>23</td></tr></table></div></div>';
 
     $('.timepicker').html(pickerhtml);
 
     $('.hrup').click(function(){
         if(fhr<23){fhr++;updatetime();}else{fhr=0;updatetime();}
     });
     $('.hrdown').click(function(){
         if(fhr>0){fhr--;updatetime();}else{fhr=23;updatetime();}
     });
     $('.minup').click(function(){
         if(fmi<59){fmi++;updatetime();}else{fmi=0;updatetime();}
     });
     $('.mindown').click(function(){
         if(fmi>0){fmi--;updatetime();}else{fmi=59;updatetime();}
     });
 
     $('.hrhere').click(function(){
         $('.hrtt').show();
         $('.pk1').hide();
         $('.mintt').hide();
     });
     $('.minhere').click(function(){
         $('.hrtt').hide();
         $('.pk1').hide();
         $('.mintt').show();
     });
     $('.hrtable td').click(function(){
         var vaa = $(this).html();
         $('.hrtt').hide();
         $('.pk1').show();
         $('.mintt').hide();
         fhr = parseInt(vaa);updatetime();
     });
     $('.mintable td').click(function(){
         var vaa = $(this).html();
         $('.hrtt').hide();
         $('.pk1').show();
         $('.mintt').hide();
         fmi = parseInt(vaa);updatetime();
     });
 });
 