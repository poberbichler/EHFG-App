package org.ehfg.app.program.strategy;

import org.ehfg.app.MockService;
import org.ehfg.app.program.data.events.RssEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 * @author patrick
 * @since 01.2015
 */
@MockService
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
