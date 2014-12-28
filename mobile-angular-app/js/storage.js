(function() {
	function LocalStorageService() {
		var SPEAKER_STORAGE = 'SPEAKERS';
		var SESSION_STORAGE = 'SESSIONS';
		
		var FAVOURITE_SESSIONS = 'FAVOURITE_SESSIONS';
		var SHOW_FAVOURITE_SESSIONS = 'SHOW_FAVOURITE_SESSIONS';

		var LAST_UPDATE = 'LAST_UPDATE';

		function resolveFromStorage(storageName) {
			return JSON.parse(localStorage.getItem(storageName)) || [];
		}
		
		function setItemInStorage(storageName, item) {
			localStorage.setItem(storageName, JSON.stringify(item));
		}

		function checkForUpdate() {
		   var lastUpdate = localStorage.getItem(LAST_UPDATE);
			if (lastUpdate !== null) {
				var difference = new Date().getTime() - lastUpdate;
				if (difference > 1000 * 60 * 60 * 2) { // 2 hours
					localStorage.removeItem(SPEAKER_STORAGE);
					localStorage.removeItem(SESSION_STORAGE);

					localStorage.setItem(LAST_UPDATE, new Date().getTime());
				}
			}

			else {
				localStorage.setItem(LAST_UPDATE, new Date().getTime());
			}
		}

		return {
			setSpeakers: setSpeakers,
			findSpeakers: findSpeakers,

			setSessions: setSessions,
			findSessions: findSessions,
			
			findFavouriteSessions: findFavouriteSessions,
			showFavouriteSessionsSelected: showFavouriteSessionsSelected,
			setFavouriteSessionSelected: setFavouriteSessionSelected, 
			addToFavouriteSessions: addToFavouriteSessions,
			removeFromFavouriteSessions: removeFromFavouriteSessions, 
			
			resetData: resetData
		}

		function setSpeakers(speakers) {
			setItemInStorage(SPEAKER_STORAGE, speakers);
		}

		function findSpeakers() {
			checkForUpdate();
			return resolveFromStorage(SPEAKER_STORAGE);
		}
		
		function setSessions(sessions) {
			setItemInStorage(SESSION_STORAGE, sessions);
		}
		
		function findSessions() {
			checkForUpdate();
			return resolveFromStorage(SESSION_STORAGE);
		}
		
		function findFavouriteSessions() {
			return resolveFromStorage(FAVOURITE_SESSIONS);
		}
		
		function setFavouriteSessionSelected(value) {
			localStorage.setItem(SHOW_FAVOURITE_SESSIONS, value);
		}
		
		function showFavouriteSessionsSelected() {
			var result = JSON.parse(localStorage.getItem(SHOW_FAVOURITE_SESSIONS));
        	if (result === null) {
        		localStorage.setItem(SHOW_FAVOURITE_SESSIONS, false);
        		return false;
        	}
        	
        	return result;
		}
		
		function addToFavouriteSessions(sessionId) {
			var favourites = this.findFavouriteSessions();
        	favourites.push(sessionId);
        	setItemInStorage(FAVOURITE_SESSIONS, favourites);
		}
		
		function removeFromFavouriteSessions(sessionId) {
			var favourites = this.findFavouriteSessions();
			var index = favourites.indexOf(sessionId);

			if (index !== -1) {
        		favourites.splice(index, 1);
        		setItemInStorage(FAVOURITE_SESSIONS, favourites);
			}
		}
		
		function resetData() {
			localStorage.clear();
		}
	}
	
	angular.module('ehfgApp.storage', []).factory('LocalStorageService', LocalStorageService);
})();