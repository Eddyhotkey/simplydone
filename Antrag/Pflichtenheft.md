# Pflichtenheft

## Einleitung

Das Projekt soll eine übersichtliche Lösung bieten um Aufgaben einfach anzulegen, zu verwalten und sie zu überblicken.

## **Zielsetzung**

Ziel ist es, eine Applikation und Webapplikation zu entwerfen um umzusetzen, die im Austausch zueinander stehen.

## Rahmenbedingungen

**Technische Rahmenbedingungen**

- Unterstützung für mobile Endgeräte, Dekstop-PCs und Laptops

**Organisatorische Rahmenbedingungen**

- Rashid (50% Arbeitsanteil, Webentwicklung, Design)
- Martin (50% Arbeitsanteil, Appentwicklung, Projektmanagement)
- Martin & Rashid (Konzeptionierung, Datenbankentwurf, Dokumentation)

## **Anforderungen**

**Funktionelle Anforderungen:**

**Registrierung:**

- Ein Besucher kann sich registrieren, um einen Account zu erstellen und die App zu nutzen.

**Anmeldung:**

- Registrierte Benutzer können sich anmelden und auf ihre ToDo-Liste zugreifen.
- Benutzer können sich abmelden, um ihren Account zu schützen.

**Profilverwaltung:**

- Benutzer können ihr Profil verwalten, um ihre Daten aktuell zu halten.

**Optionen:**

- Benutzer können die Darstellung ändern, um ein ansprechenderes Design zu erhalten.

**Kategorienverwaltung:**

- Benutzer können neue Kategorien anlegen, bestehende bearbeiten oder löschen, um eine individuelle Kategorisierung zu ermöglichen.

**To-Do-Verwaltung:**

- Benutzer können neue To-Dos anlegen, bestehende bearbeiten oder löschen, um ihre Aufgaben zu organisieren.

**Benutzeroberfläche:**

- Benutzerfreundliche und intuitive Benutzeroberfläche für eine einfache Nutzung.
- Barrierefreie Benutzeroberfläche für eine inklusive Bedienung.

**Nicht-funktionelle Anforderungen:**

**Sicherheit:**

- Sichere Speicherung der Benutzerdaten.
- Möglichkeit für Benutzer, ihr Passwort zu ändern.

**Leistung:**

- Reaktionsschnelligkeit der App, auch bei vielen To-Do-Einträgen.
- Schnelle Ladezeiten für eine effiziente Nutzung.

## **Systemarchitektur**

Die Systemarchitektur umfasst:

- Eine mehrschichtige Architektur, bestehend aus einer Datenbank, einer Applikationsschicht und einer Benutzerschnittstelle.
- Verwendung von RESTful APIs für die Kommunikation zwischen der Webapplikation und der mobilen App.
- Nutzung von Web- und Datenbankserver bei all-inkl.com

## Datenmodell

Das Datenmodell umfasst:

- Tabellen für Benutzer, To-Dos, Kategorien und andere relevante Daten.
- Beziehungen zwischen den Tabellen, um Abhängigkeiten und Verknüpfungen zu modellieren.

## Systemkomponenten

Die Hauptkomponenten des Systems sind:

- Frontend für die Webapplikation (HTML, CSS, JavaScript, jQuery).
- Frontend für die mobile App (entwickelt mit plattformspezifischen Technologien wie React Native oder Flutter).
- Backend-Server (Node.js, Express) für die Verarbeitung von Anfragen und Datenbankzugriff.

## Technologien und Frameworks

Die wichtigsten Technologien und Frameworks sind:

- Ajax für das Web-Frontend.
- Node.js mit Express für das Backend.
- Datenbankmanagement mit MySQL.
- Verwendung von RESTful APIs für die Kommunikation zwischen den Komponenten.

## **Benutzerschnittstellen**

**Anmeldung:**

- Eingabefelder für E-Mail und Passwort.
- Schaltflächen für Anmeldung und Registrierung.:

**Registrierung**

- Eingabefelder für Name, E-Mail, Passwort und Bestätigung des Passworts.

**Profilseite:**

- Möglichkeit, Profilbild hochzuladen.
- Eingabefelder für den Namen.
- Schaltfläche zum Ändern des Passworts.
- Schaltfläche zum Speichern von Profiländerungen.

**Kategorien-Verwaltung:**

- Liste der vorhandenen Kategorien.
- Schaltflächen zum Hinzufügen, Bearbeiten und Löschen von Kategorien.

**To-Do-Liste:**

- Liste der To-Do-Einträge mit Titel.
- Schaltflächen zum Hinzufügen, Bearbeiten und Löschen von To-Do-Einträgen.
- Filteroptionen nach Kategorie.

**Aktuelle und Allgemeine Aufgaben-Ansicht:**

- Liste der aktuellen Aufgaben.
- Liste der anstehenden Aufgaben.

**Responsive Design:**

- Anpassung der Benutzeroberfläche an verschiedene Bildschirmgrößen und Geräte.

## **Qualitätsanforderungen**

**Zuverlässige Datenbereitstellung:**

- Daten sollen reibungslos zwischen den Anwendungen synchronisiert werden können.

**Reibungsloser Ablauf bei der Erstellung von ToDos:**

- To-Dos sollen einfach anzulegen sein und ohne Komplikationen im System aufgenommen werden.

**Barrierefreiheit:**

- Die Anwendungen sollen für alle Menschen zugänglich sein.

**Datensicherheit:**

- Daten sollen sicher gespeichert werden und für Dritte unzugänglich sein.

## **Test- und Validierungsanforderungen**

- Akzeptanztests.
- Performancetests.
- Usability-Tests.

## **Risiken und Gegenmaßnahmen**

**Technologische Unsicherheit:**

- Risiko, dass das Projekt nicht die gewünschte Leistungsfähigkeit oder Skalierbarkeit bietet.

  Maßnahme: Stetiger Austausch und agile Entwicklungsweise.


**Plattform- und Gerätekompatibilität:**

- Risiko, dass das Projekt nicht auf allen gewünschten Plattformen reibungslos funktioniert, insbesondere in Bezug auf Responsivität.

  Maßnahme: Stetige Tests und Anpassungen, bereits bei der Entwicklung.