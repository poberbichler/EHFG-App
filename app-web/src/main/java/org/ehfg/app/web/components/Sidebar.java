package org.ehfg.app.web.components;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Loop;
import org.apache.tapestry5.corelib.components.PageLink;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.ehfg.app.web.pages.Index;
import org.ehfg.app.web.pages.SessionOverview;
import org.ehfg.app.web.pages.SpeakerOverview;
import org.ehfg.app.web.pages.maintenance.GeneralMaintenance;
import org.ehfg.app.web.pages.maintenance.SessionDayMaintenance;
import org.ehfg.app.web.pages.maintenance.TwitterStreamMaintenance;

public class Sidebar {
	@Component(parameters = {"source=pageList", "value=currentPage"})
	private Loop<String> pageLoop;
	
	@Component(parameters = {"page=prop:currentPage"})
	private PageLink currentLink;
	
	@Inject
	private Messages messages;
	
	@Inject
	private ComponentResources resouces;
	
	@Inject
	private ComponentClassResolver resolver;
	
	@Property
	private String currentPage;
	
	public List<String> getPageList() {
		return Arrays.asList(
				resolver.resolvePageClassNameToPageName(Index.class.getName()),
				resolver.resolvePageClassNameToPageName(SpeakerOverview.class.getName()),
				resolver.resolvePageClassNameToPageName(SessionOverview.class.getName()),
				resolver.resolvePageClassNameToPageName(GeneralMaintenance.class.getName()),
				resolver.resolvePageClassNameToPageName(TwitterStreamMaintenance.class.getName()),
				resolver.resolvePageClassNameToPageName(SessionDayMaintenance.class.getName())
		);
	}
	
	public String getPageLabel() {
		return messages.get("pagelabel." + currentPage);
	}
	
	public String getActive() {
		return resouces.getPageName().equals(currentPage) ? "active" : null;
	}
}
