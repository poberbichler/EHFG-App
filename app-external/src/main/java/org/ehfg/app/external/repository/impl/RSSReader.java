package org.ehfg.app.external.repository.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.ehfg.app.external.data.Rss;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;

/**
 * Reads the data from a rss stream, converts it and caches it for an hour<br>
 * ideally, the time is configurable with a quartz job, but this still has to be
 * done
 * 
 * @author patrick
 * @since 06.06.2014
 */
@Repository
class RSSReader {
	@Value("${rss.url}")
	private String baseUrlString;
	
	private JAXBContext context;

	private final Map<String, Object> dataCache = new HashMap<>();
	private final Timer timer = new Timer();

	@PostConstruct
	private void createTimer() throws IOException, JAXBException, InterruptedException {
		context = JAXBContext.newInstance(Rss.class);
		timer.schedule(new RefreshTask(), 0, 1000 * 60 * 60 * 60);
	}

	private void createDataFromInput() throws IOException, JAXBException {
		Resource inputResource = new UrlResource(baseUrlString);

		Unmarshaller unmarshaller = context.createUnmarshaller();
		Rss createdItem = (Rss) unmarshaller.unmarshal(inputResource.getInputStream());
		createdItem.getClass();
		
		dataCache.getClass();
	}

	private class RefreshTask extends TimerTask {
		@Override
		public void run() {
			try {
				createDataFromInput();
			} catch (IOException | JAXBException e) {
				e.printStackTrace();
			}
		}
	}
}
