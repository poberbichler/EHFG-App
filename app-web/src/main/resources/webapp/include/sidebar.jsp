<%@page isELIgnored="false"%>

<div class="list-group">
	<a href="/mvc/session/overview" class="list-group-item ${activePage eq 'session' ? 'active' : ''}">Session Overview</a>
	<a href="/mvc/speaker/overview" class="list-group-item ${activePage eq 'speaker' ? 'active' : ''}">Speaker Overview</a>
	<a href="/mvc/maintenance/general" class="list-group-item ${activePage eq 'generalMaintenance' ? 'active' : ''}">General Maintenance</a>
	<a href="/mvc/maintenance/twitterstreams" class="list-group-item ${activePage eq 'twitterStreams' ? 'active' : ''}">Twitter Streams</a>
	<a href="/mvc/maintenance/conferencedays" class="list-group-item ${activePage eq 'conferenceDays' ? 'active' : ''}">Conference Days</a>
	<a href="/mvc/maintenance/pointofinterest" class="list-group-item ${activePage eq 'pointOfInterest' ? 'active' : ''}">Point of Interest</a>
</div>