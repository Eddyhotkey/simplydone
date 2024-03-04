// Функция для определения, является ли дата сегодняшней
function isToday(date) {
	const today = new Date();
	return (
		date.getDate() === today.getDate() &&
		date.getMonth() === today.getMonth() &&
		date.getFullYear() === today.getFullYear()
	);
}

// Funktion zum Löschen einer Aufgabe
function deleteTask(taskElement) {
	taskElement.remove();
}

// Funktion zum Löschen einer Aufgabe über ein Kontrollkästchen
function removeTaskOnCheckbox(taskElement, checkbox) {
	if (checkbox.checked) {
		taskElement.remove();
	}
}

// Funktion zur Bearbeitung einer Aufgabe
function editTask(taskElement) {
	// Hier können Sie die Logik für das Öffnen eines modalen Fensters mit ausgefüllten Aufgabendaten implementieren
	let modal = document.getElementById("taskModal");
	modal.style.display = "block";

	// Abrufen von Aufgabendaten aus dem Element
	let task = {
		title: taskElement.querySelector(".task__title").textContent,
		description: taskElement.querySelector(".task__text").textContent,
		date: taskElement.querySelector(".task__info p:nth-child(1)").textContent.split(":")[1].trim(),
		priority: taskElement.querySelector(".task__info p:nth-child(2)").textContent.split(":")[1].trim()
	};

	// Füllen des modalen Fensters mit Aufgabendaten
	document.getElementById("taskTitle").value = task.title;
	document.getElementById("taskDescription").value = task.description;
	document.getElementById("taskDate").value = task.date;
	document.getElementById("taskPriority").value = task.priority;

	// Schaltfläche "Speichern" hinzufügen
	let saveTaskBtn = document.getElementById("saveTaskBtn");
	if (!saveTaskBtn) {
		saveTaskBtn = document.createElement("button");
		saveTaskBtn.textContent = "Speichern";
		saveTaskBtn.id = "saveTaskBtn";
		saveTaskBtn.className = "modal-task__button";

		let modalContent = document.querySelector(".modal-content");
		modalContent.appendChild(saveTaskBtn);
	}

	// Ereignishandler für die Schaltfläche "Speichern" hinzufügen
	// Im onclick-Handler der Schaltfläche "Speichern"
	saveTaskBtn.onclick = function () {
		task.title = document.getElementById("taskTitle").value;
		task.description = document.getElementById("taskDescription").value;
		task.date = document.getElementById("taskDate").value;
		task.priority = document.getElementById("taskPriority").value;

		// Aktualisieren Sie die Aufgabe auf der Tafel und übergeben Sie das Aufgabenelement und die aktualisierten Aufgabendaten als Argumente
		updateTask(taskElement, task);

		// Schließen Sie das modale Fenster
		modal.style.display = "none";
	};
}

function updateTask(taskElement, task) {
	// Implementieren Sie die Logik für die Aktualisierung von Aufgabendaten
	// Hier können Sie z. B. den Inhalt einer Aufgabenposition entsprechend den aktualisierten Daten aktualisieren
	taskElement.querySelector(".task__title").textContent = task.title;
	taskElement.querySelector(".task__text").textContent = task.description;
	taskElement.querySelector(".task__info p:nth-child(1)").textContent = "Datum: " + task.date;
	taskElement.querySelector(".task__info p:nth-child(2)").textContent = "Priorität: " + task.priority;

	console.log("Aufgabe aktualisiert:", task);
}

// Funktion zum Hinzufügen einer Aufgabe zum Board
function addTaskToDashboard(task) {
	let tasksContainer;
	if (isToday(new Date(task.date))) {
		tasksContainer = document.querySelector(".dashboard-tasks-current");
	} else {
		tasksContainer = document.querySelector(".dashboard-tasks-upcoming");
	}

	// Prüfen, ob ein Container für Aufgaben vorhanden ist
	if (tasksContainer) {
		// Aufgabenelemente erstellen
		let taskElement = document.createElement("div");
		taskElement.classList.add("task");

		// Ein Kontrollkästchen erstellen
		let checkbox = document.createElement("input");
		checkbox.type = "checkbox";

		// Einstellen des onchange-Ereignishandlers für das Kontrollkästchen
		checkbox.onchange = function () {
			removeTaskOnCheckbox(taskElement, this);
		};
		taskElement.appendChild(checkbox);

		let taskContent = document.createElement("div");
		taskContent.classList.add("task__content");

		// Aufgabentitel erstellen
		let taskTitle = document.createElement("h3");
		taskTitle.classList.add("task__title");
		taskTitle.textContent = task.title;

		// Aufgabenbeschreibung erstellen
		let taskDescription = document.createElement("p");
		taskDescription.classList.add("task__text");
		taskDescription.textContent = task.description;

		// Erstellen von Aufgabeninformationen (Datum und Priorität)
		let taskInfo = document.createElement("div");
		taskInfo.classList.add("task__info");

		let taskDate = document.createElement("p");
		taskDate.textContent = "Datum: " + task.date;

		let taskPriority = document.createElement("p");
		taskPriority.textContent = "Priorität: " + task.priority;

		// Navigation für die Aufgabe erstellen (Schaltflächen "Löschen" und "Bearbeiten")
		let taskNav = document.createElement("div");
		taskNav.classList.add("task__nav");

		let deleteButton = document.createElement("button");
		deleteButton.textContent = "Löschen";
		deleteButton.onclick = function () {
			deleteTask(taskElement);
		};

		let editButton = document.createElement("button");
		editButton.textContent = "Bearbeiten";
		// Ändern in:
		editButton.onclick = function () {
			editTask(this.parentElement.parentElement); // Übergabe des übergeordneten Elements der Aufgabe
		};

		// Sammeln Sie die Struktur der Aufgabenelemente
		taskInfo.appendChild(taskDate);
		taskInfo.appendChild(taskPriority);

		taskNav.appendChild(deleteButton);
		taskNav.appendChild(editButton);

		taskContent.appendChild(taskTitle);
		taskContent.appendChild(taskDescription);
		taskContent.appendChild(taskInfo);

		taskElement.appendChild(checkbox);
		taskElement.appendChild(taskContent);
		taskElement.appendChild(taskNav);

		tasksContainer.insertBefore(
			taskElement,
			tasksContainer.querySelector(".dashboard-tasks-title").nextSibling
		);
	}
}

// Referenzen auf Elemente des modalen Fensters abrufen
let modal = document.getElementById("taskModal");
let addTaskBtn = document.getElementById("add-task");
let closeBtn = document.getElementsByClassName("close")[0];

// Funktion zur Anzeige des modalen Fensters
addTaskBtn.onclick = function () {
	modal.style.display = "block";
};

// Funktion zum Schließen des modalen Fensters
closeBtn.onclick = function () {
	modal.style.display = "none";
};

// Schließen eines modalen Fensters beim Anklicken außerhalb des Fensters
window.onclick = function (event) {
	if (event.target === modal) {
		modal.style.display = "none";
	}
};

// Handler für das Drücken der Schaltfläche "Todo hinzufügen"
let addTaskButton = document.getElementById("addTaskBtn");
addTaskButton.onclick = function () {
	let taskTitle = document.getElementById("taskTitle").value;
	let taskDescription = document.getElementById("taskDescription").value;
	let taskDate = document.getElementById("taskDate").value;
	let taskPriority = document.getElementById("taskPriority").value;

	// Erstellen eines Objekts mit Aufgabeninformationen
	let task = {
		title: taskTitle,
		description: taskDescription,
		date: taskDate,
		priority: taskPriority,
	};

	// Hinzufügen der Aufgabe zur Tafel
	addTaskToDashboard(task);

	// Nachdem Sie eine Aufgabe hinzugefügt haben, können Sie das modale Fenster schließen
	modal.style.display = "none";
};

// Получаем сегодняшнюю дату
let todayForInput = new Date();

// Formatierung des Datums im Format JJJJ-JJ-MM-TT
let dd = String(todayForInput.getDate()).padStart(2, "0");
let mm = String(todayForInput.getMonth() + 1).padStart(2, "0"); // January is 0!
let yyyy = todayForInput.getFullYear();
let formattedDate = yyyy + "-" + mm + "-" + dd;

// Den Wert des Attributs value auf ein formatiertes Datum setzen
document.getElementById("taskDate").value = formattedDate;
