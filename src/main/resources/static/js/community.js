///开关register_form 

	function openme(){
	document.getElementById('login-interface').style.display='block';
	document.getElementById('login-background').style.display='block';
	}
	function closeLogin(){
	document.getElementById('login-interface').style.display='none';
	document.getElementById('login-background').style.display='none';
	}
	///开关admin_form 
	function openForm(){
	document.getElementById('admin-interface').style.display='block';
	document.getElementById('admin-background').style.display='block';
	}
	function closeForm(){
	document.getElementById('admin-interface').style.display='none';
	document.getElementById('admin-background').style.display='none';
	}
//返回主页

//qq登录
	function qqlogin(){
		window.open("https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=101809971&redirect_uri=http://192.168.43.104/connect&scope=get_user_info")
		window.localStorage.setItem("closable",true);
		window.location.reload();
	}
//GitHub登录
	function githublogin(){
		window.open("https://github.com/login/oauth/authorize?client_id=d7b4e607d978f48fcc38&redirect_uri=http://192.168.43.104/callback&scope=user&state=1")
		window.localStorage.setItem("closable",true);
		window.location.reload();
	}

//注册传值
	function registerPost(){
		var username = $("#register_name").val();
		var userpassword = $("#register_password").val();
		var text_password = $("#text_password").val();
		var address = $("#address").val();
		var email = $("#email").val();
		if(text_password!=userpassword){
			alert("密码不一致！");
			return;
		}
		register(username,userpassword,1,address,email,1);
		
	}
	
//注册传值
function adminPost(){
	var username = $("#register_name").val();
	var userpassword = $("#register_password").val();
	var role = $("#inlineRadioOptions").val();
	var address = $("#address").val();
	var email = $("#email").val();
	
	register(username,userpassword,role,address,email,1);
	
}

function register(username,userpassword,role,address,email,sex){
	
	if(!username){
		alert("name is null！");
		return;
	}
	if(!userpassword){
		alert("password is null！");
		return;
	}else{
		var pattern = /^[\w_-]{6,16}$/;
		if(!pattern.test(userpassword)){
			alert("密码最短6位！");
			return;
		}
	}
	if(!address){
		alert("address is null！");
		return;
	}else{
		var myreg=/^[1][3,4,5,6,7,8,9][0-9]{9}$/;
	    if (!myreg.test(address)) {
	    	alert("手机号格式不正确！");
	        return ;
	    } 
	}
	
	if(!email){
		alert("email is null！");		
		return;
	}else{
		var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
		if(!reg.test(email)){
			alert("邮箱格式不正确");
			return;
		}

	}
	
	
	
		$.ajax({
			  type: "POST",
			  url: "/adduser",
			  contentType:'application/json',
			  data: JSON.stringify({
				  "username":username,
				  "userpassword":userpassword,
				  "role":role,
				  "sex":sex,
				  "address":address,
				  "email":email
			  }) ,
			  success: function(response){
	
				  if(response.code==200){
					   alert(response.message); 
					  window.location.reload();
				  }else{			
							  alert(response.message);
							  window.location.reload();
					  
				  }
				
			  },
			  
			  dataType: "json"
			});
	
}


//回复传值

function post(){
	var questionId = $("#question_id").val();
	var content = $("#comment_content").val();
	
	comment2target(questionId,1,content);
	
}

function comment2target(targetId,type,content){
	
	if(!content){
		alert("不能回复空内容！");
		return;
	}
		$.ajax({
			  type: "POST",
			  url: "/comment",
			  contentType:'application/json',
			  data: JSON.stringify({
				  "parentId":targetId,
				  "content":content,
				  "type":type
			  }) ,
			  success: function(response){
				  if(response.code==200){
					  $("#comment_section").hide();
					  window.location.reload();
				  }else{
					 
					  if(response.code==2003){
						  var isAccepted=confirm(response.message);
						  if(isAccepted){
							  window.open("https://github.com/login/oauth/authorize?client_id=d7b4e607d978f48fcc38&redirect_uri=http://192.168.43.104/callback&scope=user&state=1")
							  window.localStorage.setItem("closable",true);
						  }
					  }else{
							   alert(response.message);
						  }
					
					  
					  
				  }
				
			  },
			  
			  dataType: "json"
			});
	
}

function comment(e){
	var commentId=e.getAttribute("data-id");
	var content =$("#input-"+commentId).val();
	comment2target(commentId,2,content);
}

/**
 * 
 * 展开二级评论
 */
function collapseComments(e){
	var id=e.getAttribute("data-id");
	var comments=$("#comment-"+id);
	
	//获取二级评论的展开状态
	var collapse=e.getAttribute("data-collapse");
	if(collapse)
		{
		//折叠二级评论
		comments.removeClass("in");
		e.removeAttribute("data-collapse");
		e.classList.remove("active");
		}else{
			var subCommentContainer=$("#comment-"+id);
			if(subCommentContainer.children().length!=1){

				//展开二级评论
				comments.addClass("in");
				//标记二级评论展开状态
				e.setAttribute("data-collapse","in");
				e.classList.add("active");
			}else{
				$.getJSON("/comment/"+id,function (data){			
					$.each(data.data,function(index,comment){						
						var mediaLeftElement=$("<div/>",{
							"class":"media-left"
						}).append($("<img/>",{
							"class":"media-object  img-rounded",
							"src":comment.user.avatarUrl
							}));
						var mediaBodyElement=$("<div/>",{
							"class":"media-body"
						}).append($("<h5/>",{
							"class":"media-heading",
							"html":comment.user.name
						})).append($("<div/>",{
							"html":comment.content
						})).append($("<div/>",{
							"class":"menu",
						}).append($("<span/>",{
							"class":"pull-right",
							"html":moment(comment.gmtCreate).format('YYYY-MM-DD')
						})));	
						
						var mediaElement=$("<div/>",{
							"class":"media"
						}).append(mediaLeftElement)
							.append(mediaBodyElement)
						
						var commentElement=$("<div/>",{
							"class":"col-lg-12 col-md-12	col-sm-12	col-xs-12  comments",
						}).append(mediaElement);
						;
						subCommentContainer.prepend(commentElement);
					});	
					//展开二级评论
					comments.addClass("in");
					//标记二级评论展开状态
					e.setAttribute("data-collapse","in");
					e.classList.add("active");
				});
		}	
	}
	
	
}





function selectTag(e){
	var value=e.getAttribute("data-tag");
	var previous=$("#tag").val();
	if(previous.indexOf(value)==-1){
		if(previous){
		$("#tag").val(previous +','+value);
		}else{
			$("#tag").val(value);
			}
	}
	
}
function showSelectTag(){
	$("#select-tag").show();
}