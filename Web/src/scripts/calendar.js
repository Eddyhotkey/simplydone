'use strict';

let currentDate = new Date();
let currentMonth = currentDate.getMonth();
let currentYear = currentDate.getFullYear();
const monthNames = [
	"Januar", "Februar", "März", "April", "Mai", "Juni",
	"Juli", "August", "September", "Oktober", "November", "Dezember"
];

function generateCalendar(month, year) {
	const daysInMonth = new Date(year, month + 1, 0).getDate();
	const firstDayOfMonth = new Date(year, month, 1).getDay();
	const startDay = (firstDayOfMonth === 0) ? 6 : firstDayOfMonth - 1;

	const tableBody = document.querySelector('#calendar tbody');
	const monthTitle = document.querySelector('#month-title');

	let html = '';

	let dayCounter = 1;

	const rowCount = Math.ceil((startDay + daysInMonth) / 7);

	const currentDate = new Date();
	const currentYear = currentDate.getFullYear();
	const currentMonth = currentDate.getMonth();
	const currentDay = currentDate.getDate();

	for (let i = 0; i < rowCount; i++) {
		html += '<tr>';

		for (let j = 0; j < 7; j++) {
			const currentIndex = (i * 7 + j - startDay + 7) % 7;

			if (i === 0 && j < startDay) {
				html += '<td></td>';
			} else if (dayCounter > daysInMonth) {
				html += '<td></td>';
			} else {
				let classes = '';
				if (year === currentYear && month === currentMonth && dayCounter === currentDay) {
					classes = 'active';
				}
				html += `<td class="${classes}">${dayCounter}</td>`;
				dayCounter++;
			}
		}

		html += '</tr>';
	}

	tableBody.innerHTML = html;
	monthTitle.textContent = `${monthNames[month]} ${year}`;
}

function previousMonth() {
	currentMonth--;
	if (currentMonth < 0) {
		currentMonth = 11;
		currentYear--;
	}
	generateCalendar(currentMonth, currentYear);
}

function nextMonth() {
	currentMonth++;
	if (currentMonth > 11) {
		currentMonth = 0;
		currentYear++;
	}
	generateCalendar(currentMonth, currentYear);
}

function setCurrentMonth() {
	currentDate = new Date();
	currentMonth = currentDate.getMonth();
	currentYear = currentDate.getFullYear();
	generateCalendar(currentMonth, currentYear);
}

generateCalendar(currentMonth, currentYear);