// Abrufen des Parameters userLogin aus der URL
const urlParams = new URLSearchParams(window.location.search);
const userLogin = urlParams.get('userLogin');

fetch('../src/backend/fetch_profile.php')
	.then(response => {
		if (!response.ok) {
			throw new Error('Fehler beim Aktualisieren der JSON-Datei');
		}
		console.log('JSON-Datei wurde erfolgreich aktualisiert');
	})
	.catch(error => {
		console.error('Fehler:', error);
	});

// Laden der JSON-Datei
fetch('../src/backend/data.json')
	.then(response => response.json())
	.then(data => {
// Suche nach Daten für den angegebenen Benutzer
		const userData = data.find(user => user.Username === userLogin);

		if(userData) {
// Ersetzen von Daten in HTML-Elementen
			document.getElementById('vorname').textContent = userData.Vorname;
			document.getElementById('nachname').textContent = userData.Nachname;
			document.getElementById('email').textContent = userData.Email;
		} else {
// Wenn der Benutzer nicht gefunden wird, wird eine Fehlermeldung ausgegeben
			console.error('Benutzer nicht gefunden.');
		}
	})
	.catch(error => {
		console.error('Fehler beim Laden der JSON-Datei:', error);
	});


// Warten, bis die Seite geladen ist
document.addEventListener("DOMContentLoaded", function() {
	// Abrufen des Parameters userLogin aus der URL
	const urlParams = new URLSearchParams(window.location.search);
	const userLogin = urlParams.get('userLogin');

	// Wenn userLogin nicht leer ist
	if (userLogin) {
		// Laden der Datei data.json
		fetch('../src/backend/data.json')
			.then(response => response.json())
			.then(data => {
				// Suche nach Benutzer nach Benutzername
				const userData = data.find(item => item.Username === userLogin);
				if (userData) {
					// Ausgabe der Benutzer-ID
					console.log('Benutzer-ID für', userLogin, ':', userData.UserID);
				} else {
					console.log('Es wurde kein Benutzer mit diesem Login gefunden.');
				}
			})
			.catch(error => console.error('Fehler beim Laden der Datei data.json:', error));
	} else {
		console.log('Der Parameter userLogin wird in der URL nicht gefunden.');
	}
});
