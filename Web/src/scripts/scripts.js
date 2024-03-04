function login() {
	let username = document.getElementById("username").value;
	let password = document.getElementById("password").value;
	if (!username || !password) {
		alert('Bitte füllen Sie alle Felder aus.');
	} else {
		window.location.href = "dashboard.html";
		document.getElementById("loggedInMessage").style.display = "block";
	}
}

function register() {
	let newPassword = document.getElementById("newPassword").value;
	let confirmPassword = document.getElementById("confirmPassword").value;
	if (newPassword === confirmPassword) {
		document.getElementById("registerForm").style.display = "none";
		document.getElementById("loggedInMessage").style.display = "block";
	} else {
		alert('Passwörter stimmen nicht überein');
	}
}

function showRegistrationForm() {
	document.getElementById("loginForm").style.display = "none";
	document.getElementById("registerForm").style.display = "block";
}

function showLoginForm() {
	document.getElementById("registerForm").style.display = "none";
	document.getElementById("loginForm").style.display = "block";
}

function togglePasswordVisibility(fieldId) {
	let field = document.getElementById(fieldId);
	let button = document.querySelector(`button[onclick="togglePasswordVisibility('${fieldId}')"]`);

	if (field.type === "password") {
		field.type = "text";
		button.classList.remove( 'form-lage--pass-on');
		button.classList.add( 'form-lage--pass-off' );
	} else {
		field.type = "password";
		button.classList.remove( 'form-lage--pass-off');
		button.classList.add( 'form-lage--pass-on' );
	}
}

// LOGOUT //

document.addEventListener( 'DOMContentLoaded', function () {
	// Holen Sie sich den Link anhand seiner ID
	let addTaskLink = document.getElementById('logout');

// Event-Handler für das Klicken auf den Link
	addTaskLink.addEventListener('click', function(event) {
		// Standardverhalten des Links (Weiterleitung) verhindern
		event.preventDefault();

		// Ein Systemdialog mit einer Bestätigungsanfrage anzeigen
		let confirmed = confirm('Möchten Sie wirklich die Seite verlassen?');

		// Wenn der Benutzer bestätigt, zur Startseite navigieren
		if (confirmed) {
			window.location.href = '/'; // ersetzen Sie dies durch den tatsächlichen Pfad zur Startseite
		}
	});
});

