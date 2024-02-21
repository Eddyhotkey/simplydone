const urlParams = new URLSearchParams(window.location.search);
const registering = urlParams.get('registering');

new Vue({
	el: '#app',
	data() {
		return {
			loggedIn: false,
			registering: registering === "true",
			username: '',
			password: '',
			newMail: '',
			newUsername: '',
			newUserSurname: '',
			newPassword: '',
			confirmPassword: ''
		};
	},
	methods: {
		login() {
			if (!this.username || !this.password) {
				alert('Bitte füllen Sie alle Felder aus.');
			} else {
				this.loggedIn = true;
			}
		},
		register() {
			if (this.newPassword === this.confirmPassword) {
				this.registering = false;
			} else {
				alert('Passwörter stimmen nicht überein');
			}
		},
		showRegistrationForm() {
			this.registering = true;
		},

		showLoginForm() {
			this.registering = false;
		},
		togglePasswordVisibility(fieldId) {
			const field = document.getElementById(fieldId);
			const button = document.querySelector(`button[for=${fieldId}]`);

			if (field.type === "password") {
				field.type = "text";
				button.textContent = "Hide";
			} else {
				field.type = "password";
				button.textContent = "Show";
			}
		}
	}
});
