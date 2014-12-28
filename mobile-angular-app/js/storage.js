(function() {
	function LocalStorageService() {
		var SPEAKER_STORAGE = 'SPEAKERS';
		var SESSION_STORAGE = 'SESSIONS';
		
		var FAVOURITE_SESSIONS = 'FAVOURITE_SESSIONS';
		var SHOW_FAVOURITE_SESSIONS = 'SHOW_FAVOURITE_SESSIONS';
		
		function resolveFromStorage(storageName) {
			return JSON.parse(localStorage.getItem(storageName)) || [];
		}
		
		function setItemInStorage(storageName, item) {
			localStorage.setItem(storageName, JSON.stringify(item));
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
			return resolveFromStorage(SPEAKER_STORAGE);
		}
		
		function setSessions(sessions) {
			setItemInStorage(SESSION_STORAGE, sessions);
		}
		
		function findSessions() {
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