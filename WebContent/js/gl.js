var default_panel="";


setTimeout(run,1000);
function run(){
  $('#dashboard').css({
      "transform":"scale(1)",
      "opacity":"1",
      "z-index":"1"
 });
}
var cntnr;
$(document).on('click','[btn=cp]',function(){
 var eID=$(this).attr('rel-pnl');
 var action=$(this).attr('action');
 var element=$(this).attr('element');

 cntnr=$(this).attr('container');// container is - first div which must be displayed in design approach : but in technical seat where to fill

 addingOutDspno();
 //  Transforms outher panel
 $('#'+eID).css({
      "transform":"scale(1)",
      "opacity":"1",
      "z-index":"1"
 });


 addingInDspno('#'+eID);
 //   Transforms inner (related) panel
$('#'+cntnr).css({
      "transform":"scale(1)",
      "opacity":"1",
      "z-index":"1"
 });

  centralSocket(action,element,"0",fakeDataFn(),renderCompanies);


});



/*********    Toggling  realizers    ********/ 
function addingInDspno(outher_element_id){
  $(outher_element_id).children("[toggable=in]").each(function(i, val){ 

    $(this).css({
      "transform":"scale(.2)",
      "opacity":"0",
      "z-index":"0"
      });

   })
};


function addingOutDspno(){
  $("[toggable=out]").each(function(i, val){    
        $(this).css({
      "transform":"scale(.2)",
      "opacity":"0",
      "z-index":"0"
      });
   });
};

/*********   End  Toggling   realizers  ********/ 




//  ***************   Company  *******************


function renderCompanies(data){

console.log(JSON.stringify(data));
  var container=$('#'+cntnr);
  container.empty();
  if(data==null||data=="") {
    container.empty();
    container.html('<h4 class="header-text m-top-md"><i class="fa fa-empire fa-2x add-btn" out="11" dspno="t3311" create="company"> Add Company</i></h4><div style="margin: 40px;font-size: 16px;text-align: center;color:#e36159;">Currently does not exist any company in system. Please add new one</div>');
    return;
  }


var table='<h4 class="header-text m-top-md"><i class="fa fa-empire fa-2x add-btn" out="11" dspno="t3311" create="company"> Add Company</i></h4>'+
          '<div class="row padding-md"><div class="table-responsive"><table class="table borderless"><thead><tr>'+
          '<th><i class="fa fa-list"></i></th><th><b>NAME</b></th><th><b>ADDRESS</b></th><th><b>Country</b></th><th><b>City</b></th>'+
          '<th><b>Phone</b></th><th><b>Email</b></th><th class="tac"><b>Actions</b></th></tr></thead><tbody>';


$.each(data , function(i,e) {
   var body='<tr><td class="no">'+(i+1)+'</td><td>'+e.name+'</td><td>'+e.address+'</td><td>'+
            e.country+'</td><td>'+e.city+'</td><td>'+e.phone+'</td><td>'+e.email+
   '</td><td class="blc"><div class="bt  margin-left" dspno="t3311" out="11" edit="company" e_id="'+e.id+
            '" ><i class="fa fa-pencil fa-2x"></i></div><div class="bt  margin-left"  remove="company" remove_id="'+e.id+
            '"><i class="fa fa-trash-o fa-2x"></i></div></td></tr>';
   table+=body;
});

  table+='</tbody></table></div></div>';
  container.html(table);


}





$(document).on('click','[edit=company]',function(){
  console.log('Editing '+$(this).attr('e_id'));
  renderCompanyC_or_U($(this).attr('dspno'),$(this).attr('out'),"u");
  centralSocket("select","company",$(this).attr('e_id'),fakeDataFn(),renderUpdateMode);
});


$(document).on('click','[remove=company]',function(){
  centralSocket("remove","company",$(this).attr('remove_id'),fakeDataFn(),renderCompanies);
});

$(document).on('click','[create=company]',function(){

  renderCompanyC_or_U($(this).attr('dspno'),$(this).attr('out'),"c");

});



//  *************************************************


function renderCompanyC_or_U(dspno,outsidePaneID,mode){
 
  if (!$('#'+dspno).hasClass("dspno")) {
   $('#'+dspno).addClass("dspno");
  }

var form='<div class="col-md-6 col-sm-12 col-xs-12 list center pd15" id="company_form">'+
'<h4 class="header-text">Category</h4>'+
'<form class="form-horizontal" style="margin:26px 10px;">'+
  '<div class="form-group">'+
      '<label class="col-sm-4 control-label">NAME</label>'+
      '<div class="col-sm-6">'+
          '<input type="text" class="form-control" id="name" placeholder="Name">'+
      '</div>'+
  '</div>'+
  '<div class="form-group">'+
      '<label class="col-sm-4 control-label">ADDRESS</label>'+
      '<div class="col-sm-6">'+
          '<input type="text" class="form-control" id="address" placeholder="Address">'+
      '</div>'+
  '</div>'+
  '<div class="form-group">'+
      '<label class="col-sm-4 control-label">CITY</label>'+
      '<div class="col-sm-6">'+
          '<input type="text" class="form-control" id="city" placeholder="City">'+
      '</div>'+
  '</div>'+
  '<div class="form-group">'+
      '<label class="col-sm-4 control-label">COUNTRY</label>'+
      '<div class="col-sm-6">'+
          '<input type="text" class="form-control" id="country" placeholder="Country">'+
      '</div>'+
  '</div>'+
  '<div class="form-group">'+
      '<label class="col-sm-4 control-label">EMAIL</label>'+
      '<div class="col-sm-6">'+
          '<input type="text" class="form-control" id="email" placeholder="Email">'+
      '</div>'+
  '</div>'+
  '<div class="form-group">'+
      '<label class="col-sm-4 control-label">PHONE</label>'+
      '<div class="col-sm-6">'+
          '<input type="tel" class="form-control" id="phone" placeholder="Phone">'+
      '</div>'+
  '</div>'+
  '<div class="form-group m-top-lg">'+
      '<label class="col-sm-4 control-label"></label>'+
      '<div class="col-sm-6">'+
          '<a data-toggle="tab" class="btn btn-info m-left-xs right" d-cls="'+dspno+'" >Cancel</a>';

  if(mode=="c"){
    form+='<a class="btn btn-default right" id="ok_company">Create</a>';
    
  }
  else if(mode=="u"){
    form+='<a class="btn btn-default right" id="up_company">Update</a>';
    
  }

    form+='</div></div></form></div>';

 $('#'+outsidePaneID).append(form);
}


function renderUpdateMode(data){
  $('#name').val(data.name);
  $('#address').val(data.address);
  $('#city').val(data.city);
  $('#country').val(data.country);
  $('#email').val(data.email);
  $('#phone').val(data.phone);
  $('#up_company').attr('company_id',data.id);
}


function companyToJSON(){
    return JSON.stringify({
"company": {
    "name":$('#name').val(),
    "address":$('#address').val(),
    "city":$('#city').val(),
    "country":$('#country').val(),
    "email":$('#email').val(),
    "phone":$('#phone').val()
    }
  });
}

$(document).on('click','#ok_company',function(){

  centralSocket("create","company","0",companyToJSON(),renderCompanies);
  toggleCompanyList();
});

$(document).on('click','#up_company',function(){
  centralSocket("update","company",$('#up_company').attr('company_id'),companyToJSON(),renderCompanies);
  toggleCompanyList();
});




$(document).on('click','[d-cls]',function(){
   
  toggleCompanyList();
  
});


function toggleCompanyList(){
    $('#company_form').detach();

  if (!$('#t3311').hasClass("dspno")) {
   $('#t3311').addClass("dspno");
  }else{
    $('#t3311').removeClass("dspno");  
  }
}
//  ************** END Company *******************

























$('#sidebarToggleLG').click(function(){
  
  if($(this).attr('clicked')!="1")
    { 
      $(this).css({"left":"0"});
      $(this).attr('clicked','1');
     }
     else{
      $(this).css({"left":"-243px"});
      $(this).attr('clicked','0');
     }
     
});


window.onresize = function() {
  console.log('****************** width = '+window.innerWidth);
};



/**************************************************************/ 
/**************************************************************/
/**************************************************************/
//            UNIVERSAL Functions

/*************************  AJAX  ***********************************/ 

var rootURL="/store/service/secured/";

function centralSocket(action,element,id,dataFn,doneFn){
 console.log("Socket action = "+action+": element = "+element+": id = "+id);
                 $.ajax({
                       type:'POST',
                       url: '/store/service/secured/router/'+action+'/'+element+'/'+id,
                       contentType: "application/json; charset=utf-8",
                       dataType: "json",
                       data:dataFn
                 }).done(doneFn)
                   .fail(function(err){
       console.log("AJAX failed: " + JSON.stringify(err, null, 2));
                        });
}

function fakeDoneFn(data){
 console.log("Okey done"+JSON.stringify(data));
}
function fakeDataFn(){
  return JSON.stringify({
    "data":"nothing"
  });
}


/**************************************************************/
/**************************************************************/
/**************************************************************/








/********************************************************************************************************/ 
/********************************************************************************************************/
/********************************************************************************************************/
/********************************************************************************************************/
/********************************************************************************************************/

                                /** Some Collective Functions **/ 


/********************************************************************************************************/
/********************************************************************************************************/
/********************************************************************************************************/
/********************************************************************************************************/


function renPhoto(e){
      if(e!=null){
      return e;
    }else{
      return "default.jpg";
    }
}

function fnExist(element_id,error_msg){

$(element_id).blur(function(){

                    $(this).next().remove();
                    $(this).parent().next().children().remove();

        if($('#users_email').attr("not_exist")==="true"){
                    // $(this).parent().next().append('<i class="fa fa-check green"></i>');
                    // $(this).parent().next().css({"visibility":"visible"});
                    $(this).attr("user_ok",true);
                    console.log("Works fine yes ");
                        }
         else{
                  $(this).parent().next().css({"visibility":"visible"});
                  $(this).after('<div style="color:red;" class="error-div"> '+error_msg+'</div>');
                  $(this).attr("user_ok",false);
         }                
});


};  //  End of fnExist function

//             ***************************************************************

 function frontEndControl(elementosForControl){
      var emptinessResult=true;
        $(elementosForControl).find('input[type=text]').each(function() {
            if($(this).val().trim()===""){
              console.log("1 id si "+elementosForControl+" olan forumun  "+$(this).attr('id')+" elementi boshdur : attributu "+$(this).attr('valid'));
              emptinessResult=false;
              return false;
              }
              else if($(this).attr('valid')=="true"){
               emptinessResult=true; 
               console.log("2 id si "+elementosForControl+" olan forumun  "+$(this).attr('id')+" elementi okeydi "+$(this).attr('valid'));
              }
              else{
                console.log("3 id si "+elementosForControl+" olan forumun  "+$(this).attr('id')+" elementi xetalidir");
                emptinessResult=false;
                return false;
              }                  
           });
        return emptinessResult;
       }



function isNull(element){
   if( element ) 
      return  element;
    
    else return "";
}

function messagedAlert(message){

              $.jAlert({
              'title': 'Error!',
              'content': message,
              'theme': 'red',
              'backgroundColor':'white',
              'size':'sm',
              'btns': { 'text': 'CLOSE' }
              // ,
              // 'showAnimation': 'rubberBand',
              // 'hideAnimation': 'rollOut' 
            });

}

function messagedAlertBlue(message){

              $.jAlert({
              'title': 'Informational!',
              'content': message,
              'theme': 'blue',
              'backgroundColor':'black',
              'size':'sm',
              'btns': { 'text': 'CLOSE' }
              // ,
              // 'showAnimation': 'rubberBand',
              // 'hideAnimation': 'rollOut' 
            });

}

function reset(){
    $('input[type=text]').val('');  
    $('.textarea').val(''); 
    $('input[type=select]').val('');
    $('input[type=radio]').val('');
    $('input[type=checkbox]').val(''); 
    $('input[type=password]').val('');
    $('input[type=date]').val(''); 
    $('[error]').detach();
    $('input:checked').attr('checked',false);
    // $("input:checkbox:checked")
}

// *******************************  VALIDATION  ***********************************


 function formEmptinessControl(elementosForControl){
      var controlResult=false;
        $(elementosForControl).find('input').each(function() {
            if($(this).val().trim()===""){
              controlResult=false;
              console.log($(this).attr('id')+' Element is empty');
              return;
            }
            else{
              controlResult=true;
              }           
           });
        return controlResult;
       }



 function emptynessCntrl(elementosForControl){
      var controlResult=0;
        $(elementosForControl).find('input').each(function() {
            if($(this).val().trim()===""){
              controlResult=0;
              console.log($(this).attr('id')+' Element is empty');
              return controlResult;
            }
            else{
              controlResult=1;
              }           
           });
        return controlResult;
       }



// ******************************* END VALIDATION ***********************************

var mainImageName;
var mainImageBnr;

function convertMainImage(evt) {
 var files = evt.target.files;
 var file = files[0];
    mainImageName=file;
                         
    if (files && file) {
       var reader = new FileReader();

      reader.onload = function(readerEvt) {
      var binaryString = readerEvt.target.result;
      mainImageBnr = btoa(binaryString);
             };
              reader.readAsBinaryString(file);
                    }
};



var images=[];
var types=[];
function convertOtherImages(evt) {
 
 images=[]; // I have to clean array every time because of ->  images[images.length]=btoa(binaryString);
 types=[];
 var files = evt.target.files;
 console.log(files);
    for (var i = 0; i < files.length; i++) {
      types[i]=getExt(files[i].name);
    }

    if (files) {
      for (var i = 0; i < files.length; i++) {

                    var reader = new FileReader();
                    reader.readAsBinaryString(files[i]);
                    reader.onload = function(readerEvt) {
                    var binaryString = readerEvt.target.result;

                    images[images.length]=btoa(binaryString);
                    
             };
             
      };  
    }
};

if (window.File && window.FileReader && window.FileList && window.Blob) {
   // $('#promainimage').change(convertMainImage);
   $(document).on('change','#promainimage',convertMainImage);
   $(document).on('change','#proimages',convertOtherImages);
  } else {
    messagedAlert('The File APIs are not fully supported in this browser.');
   }


keyupUpperCase('#users_fin');
keyupUpperCase('#pe_fin');

function keyupUpperCase(element){
$(element).bind("keyup",function(){
    this.value = this.value.toUpperCase();
});
}


function sbtstmp(tmstmp){
  return tmstmp.substring(0,10);
}


function timestampToFormattedDate(dataInTimestamp){
  var d = new Date(dataInTimestamp);
  return addZeroToDate(d.getDate()) + "." + addZeroToDate((d.getMonth() + 1)) + "." + d.getFullYear();

}

function cnvrtTmstmp(timestamp){
var d = new Date(timestamp);
var month = new Array();
month[0] = "Yanvar";
month[1] = "Fevral";
month[2] = "Mart";
month[3] = "Aprel";
month[4] = "May";
month[5] = "İyun";
month[6] = "İyul";
month[7] = "Avqust";
month[8] = "Sentyabr";
month[9] = "Oktyabr";
month[10] = "Noyabr";
month[11] = "Dekabr";
var n = month[d.getMonth()];

  var hours = (d.getHours() < 10) ? "0" + d.getHours() : d.getHours();
  var minutes = (d.getMinutes() < 10) ? "0" + d.getMinutes() : d.getMinutes();
  var formattedTime = hours + ":" + minutes;

 return  n+"  "+addZeroToDate(d.getDate())+" , "+d.getFullYear()+",  "+formattedTime;
}

function currentDate(){
var d = new Date();
var month = new Array();
month[0] = "Yanvar";
month[1] = "Fevral";
month[2] = "Mart";
month[3] = "Aprel";
month[4] = "May";
month[5] = "İyun";
month[6] = "İyul";
month[7] = "Avqust";
month[8] = "Sentyabr";
month[9] = "Oktyabr";
month[10] = "Noyabr";
month[11] = "Dekabr";
var n = month[d.getMonth()];

 return  n+"  "+addZeroToDate(d.getDate())+" , "+d.getFullYear();
}


function addZeroToDate(date){

     if(date<10){
        return "0"+date;
     }
     else return date;

}

 $.ajaxSetup({
  statusCode: {
        401: function() {
          reset();
          resetLists();
          messagedAlert("Session expired.");
        },
         422: function() {
          reset();
          resetLists();
          messagedAlert("Input parameters are invalid.");
        },
        404: function(){
          console.log('URL is invalid.');
        }
      }    
   });


document.addEventListener("keydown", KeyCheck);

function KeyCheck(event)
{  
   var KeyID = event.keyCode;
   var target=$(event.target);
   switch(KeyID)
   {
      case 8:
      if(!target.is("input")&&!target.is("textarea")){
        event.preventDefault();
        

        if(default_panel=="pro"){
                default_panel="cat"
                addingInDspno('#11');
                
                $('#t1111').css({
                      "transform":"scale(1)",
                      "opacity":"1",
                      "z-index":"1"
                });          



        }else if(default_panel=="det"){
                default_panel="pro"
                addingInDspno('#11');
                
                $('#t1122').css({
                      "transform":"scale(1)",
                      "opacity":"1",
                      "z-index":"1"
                });   

        }else{
          addingOutDspno();
          run();
        // Return to Dashboard
        // reset();
        }


      }   
      break;
      case 13:  
      if(target.is("#search_data")){
        event.preventDefault();        
        // reset();
      }   
      break;
      default:
      break;
   }
}



$(document).on('click','[btn=cncl]',reset);


function convertError(error){
var message="";
var data = error;
var arr = data.split(':');
// $("#date").html("<span>"+arr[0] + "</span></br>" + arr[1]);
var errorCode=parseInt(arr[1].replace(/[a-z_A-Z]/g,""));


if(errorCode=="401"){
  message="API İstifadəçi adı və ya şifrə səhvdir!"
}
if(errorCode=="403"){
  message="Bu servisi çalışdırmaq hüququnuz yoxdur!"
}
if(errorCode=="404"){
  message="Mərkəzin ünvanı səhv göstərilib!"
}
if(errorCode=="500"){
  message="Mərkəzdə xata baş verdi!"
}

return message;
}



// var map = {};
// // add a item
// map[key1] = value1;
// // or remove it
// delete map[key1];
// // or determine whether a key exists
// key1 in map;
// bele olur

