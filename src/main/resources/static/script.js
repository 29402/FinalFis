$(function(){
	let introH = $("#intro").innerHeight();//save in
	let header = $("#header");
	let scrollOffset = $(window).scrollTop();
	
	//header--fixed animation
	checkScroll(scrollOffset);
	
	$(window).on("scroll", function(){
		
		scrollOffset = $(this).scrollTop();
		
		checkScroll(scrollOffset);
		
	});
	
	
	function checkScroll(scrollOffset){
		
		if(scrollOffset >= introH){
			header.addClass("header--fixed");  
		} else{
			header.removeClass("header--fixed");
		}
	}
	
	/*animation*/
	
	$("[data-scroll]").on('click', function(event){
		event.preventDefault();
		//отменяет действие события по умолчаниюs
		
		let $this = $(this),
			blockId = $this.data('scroll'),
			blockOffset = $(blockId).offset().top;
		

		$("#nav a").removeClass("active");
		$this.addClass("active");
		
		
		$("html, body").animate({
		 scrollTop: blockOffset	
		}, 500);
		
		$(this).removeClass("active");
		$("#nav").removeClass("active");
	});
	
	
	
	/*burber*/
	
	$("#nav_toggle").on('click',function(event){
		  event.preventDefault(); //чтобы не было скачка верх страницы
		
		$(this).toggleClass("active");
		$("#nav").toggleClass("active");
		
	});
	
	
	
});

$('.message a').click(function(){
   $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
});

var LocalStorageJSON;

var loggedUser;

var admin = {
	login : "admin@gmail.com",
	pass: "123"
}




function checkPass(pass)
{
    if(/^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,16}$/.test(pass)){
    	return true;
    }
    alert("Password must consist at least of 1 Capital letter, 1 lower case letter, 1 special symbol, also it's length must be more than 8 and less than 16!");
    return false;
}

function checkEmail(email) 
{
	if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email))
	{
    	return true;
	}
    alert("You have entered an invalid email address!");
    return false;
}

$( "#regForm" ).submit(function( event ) {
  event.preventDefault();

	name = $(this).children("input[name='name']").val();
	login = $(this).children("input[name='email']").val();
	pass = $(this).children("input[name='pass']").val();

	if (LocalStorageJSON[login] != null) {
		alert("User with this email already exists!");
		return;
	}

	user = {
		name: name,
		pass: pass,
		active: true
	}

	LocalStorageJSON[login] = user;
	localStorage.setItem('info',JSON.stringify(LocalStorageJSON));

	alert("Success!");
	location.reload();

});

$( "#loginForm" ).submit(function( event ) {
	event.preventDefault();

	login = $(this).children("input[name='email']").val();
	pass = $(this).children("input[name='pass']").val();

	if (admin['login'] == login && admin['pass'] == pass) {
		localStorage.setItem('loggedUser',login);
		location.href = 'admin.html';
		return;
	}	

	if (LocalStorageJSON[login] == null) {
		alert("User does not exist");
		return;
	}

	if (LocalStorageJSON[login]['active'] == false) {
		alert("Your account has been blocked!");
		return;
	}

	if (LocalStorageJSON[login]['pass'] == pass) {
		localStorage.setItem('loggedUser',login);
		location.href = 'main.html';
	}
	else {
		alert("Incorrect password!");
	}

});

function LogOut() {
	localStorage.removeItem('loggedUser');	
	location.href = 'authorize.html';
}