package org.ehfg.app.program.strategy;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.ehfg.app.program.data.events.RssEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * @author patrick
 * @since 01.2015
 */
@Service
@Profile({ "mock" })
public class SessionRetrievalMockStrategy extends AbstractDataRetrievalStrategy<RssEvent> {
	private RssEvent rssEvent;
	
	SessionRetrievalMockStrategy() {
		super(RssEvent.class, "");
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		final Resource sessionResource = new ClassPathResource("mock/events.xml");
		final Unmarshaller eventUnmarshaller = JAXBContext.newInstance(RssEvent.class).createUnmarshaller();
		this.rssEvent = (RssEvent) eventUnmarshaller.unmarshal(sessionResource.getFile());
	}
	
	@Override
	public RssEvent fetchData() {
		return rssEvent;
	}
}
