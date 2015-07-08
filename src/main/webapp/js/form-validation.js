var emailRegExp = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
var nameRegExp = /^[a-zA-Z-]+$/;
var captchaRegExp = /^[a-z0-9-]+$/;
var passwordRegExp = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$/;
var creditCardNumberRegExp = /^[0-9]{16}$/;
var experetionDateMonthRegExp = /^(0[1-9])|(1[012])$/;
var experetionDateYearRegExp = /^[0-9][0-9]$/;
var cardHolderNameRegExp = /^[a-zA-Z-]+ [a-zA-Z-]+$/;
var addressRegExp = /^[\.a-zA-Z0-9- ]+$/;
var cvvRegExp = /^[0-9]{3}$/;
var fileValid = true;

$("#avatar").fileinput({
	allowedFileExtensions : [ 'jpg', 'png' ],
	showUpload : false,
	maxFileCount : 1,
	maxFileSize : 5120,
	mainClass : "inputbox"
});

$('#avatar').on('fileerror', function(event, data) {
	fileValid = false;
});

$('#avatar').on('fileloaded', function(event, data) {
	fileValid = true;
});

$('#avatar').on('filecleared', function(event, data) {
	fileValid = true;
});

function validateLoginForm() {
	var inputsLogin = document.forms["loginForm"];
	var isValid = true;

	var passwordValue = inputsLogin["password"].value;
	if (passwordValue == null || passwordValue == ""
			|| !passwordRegExp.test(passwordValue)) {
		document.getElementById("passwordLabel").className = "error-field";
		isValid = false;
	} else {
		document.getElementById("passwordLabel").className = "valid-field";
	}

	var emailValue = inputsLogin["email"].value;
	if (emailValue == null || emailValue == "" || !emailRegExp.test(emailValue)) {
		document.getElementById("emailLabel").className = "error-field";
		isValid = false;
	} else {
		document.getElementById("emailLabel").className = "valid-field";
	}

	focusFirstInvalid();
	return isValid;
}

function validateLoginFormjQuery() {
	var isValid = true;

	var passwordValue = $("#password").val();
	var passwordLabel = $("#passwordLabel");
	if (passwordValue == null || passwordValue == ""
			|| !passwordRegExp.test(passwordValue)) {
		passwordLabel.addClass("error-field");
		isValid = false;
	} else {
		passwordLabel.removeClass("error-field");
		passwordLabel.addClass("valid-field");
	}

	var emailValue = $("#email").val();
	var emailLabel = $("#emailLabel");
	if (emailValue == null || emailValue == "" || !emailRegExp.test(emailValue)) {
		emailLabel.addClass("error-field");
		isValid = false;
	} else {
		emailLabel.removeClass("error-field");
		emailLabel.addClass("valid-field");
	}
	focusFirstInvalid();

	return isValid;
}

var inputs = document.forms["registerForm"];

function validateSignUpForm() {
	var isValid = true;
	var nameValue = inputs["passwordConfirmation"].value;
	if (nameValue == null || nameValue == ""
			|| nameValue != inputs["password"].value) {
		document.getElementById("passwordConfirmationLabel").className = "error-field";
		isValid = false;
	} else {
		document.getElementById("passwordConfirmationLabel").className = "valid-field";
	}

	isValid &= checkInput("password", passwordRegExp)
			& checkInput("email", emailRegExp)
			& checkInput("lastName", nameRegExp)
			& checkInput("firstName", nameRegExp)
			& checkInput("captcha", captchaRegExp) & fileValid;
	focusFirstInvalid();

	return (isValid == 0) ? false : true;
}

function checkInput(name, pattern) {
	var nameValue = inputs[name].value;
	var isValid = true;
	if (nameValue == null || nameValue == "" || !pattern.test(nameValue)) {
		document.getElementById(name + "Label").className = "error-field";
		isValid = false;
	} else {
		document.getElementById(name + "Label").className = "valid-field";
	}

	return isValid;
}

function validateSignUpFormjQuery() {
	var isValid = true;

	var passConfirm = $("#passwordConfirmation").val();
	var passConfirmLabel = $("#passwordConfirmationLabel");
	if (passConfirm == null || passConfirm == ""
			|| passConfirm != $("#password").val()) {
		passConfirmLabel.addClass("error-field");
		isValid = false;
	} else {
		passConfirmLabel.removeClass("error-field");
		passConfirmLabel.addClass("valid-field");
	}

	isValid &= checkInputjQuery("#password", passwordRegExp)
			& checkInputjQuery("#email", emailRegExp)
			& checkInputjQuery("#lastName", nameRegExp)
			& checkInputjQuery("#firstName", nameRegExp)
			& checkInputjQuery("#captcha", captchaRegExp) & fileValid;
	focusFirstInvalid();

	return (isValid == 0) ? false : true;
}

function checkInputjQuery(name, pattern) {
	var nameValue = $(name).val();
	var isValid = true;
	if (nameValue == null || nameValue == "" || !pattern.test(nameValue)) {
		$(name + "Label").addClass("error-field");
		isValid = false;
	} else {
		$(name + "Label").removeClass("error-field");
		$(name + "Label").addClass("valid-field");
	}

	return isValid;
}

function focusFirstInvalid() {
	if (fileValid) {
		var idLabel = $("label.error-field:first").attr('id');
		$("#" + idLabel.substring(0, idLabel.lastIndexOf("Label"))).focus();
	}
}

function validateCheckoutFormjQuery() {
	var isValid = true;
	if ($("#paymentType").val() == 'card') {
		isValid &= checkInputjQuery("#creditCardNumber", creditCardNumberRegExp)
				& checkInputjQuery("#cardHolderName", cardHolderNameRegExp)
				& checkInputjQuery("#cvv", cvvRegExp);

		var monthValue = $("#experetionDateMonth").val();
		var yearValue = $("#experetionDateMonth").val();
		if (monthValue == null || monthValue == ""
				|| !experetionDateMonthRegExp.test(monthValue)
				|| yearValue == null || yearValue == ""
				|| !experetionDateYearRegExp.test(yearValue)) {
			$("#experetionDateLabel").addClass("error-field");
			isValid = false;
		} else {
			$("#experetionDateLabel").removeClass("error-field");
			$("#experetionDateLabel").addClass("valid-field");
		}

	}

	isValid &= checkInputjQuery("#address", addressRegExp);

	return (isValid == 0) ? false : true;
}