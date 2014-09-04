package org.ehfg.app.program.strategy;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

/**
 * Abstract strategy for retrieving data from the ehfg server <br>
 * to use it, this class has to be subclassed - that's it
 * 
 * @author patrick
 * @since 21.06.2014
 */
@Service
public abstract class AbstractDataRetrievalStrategy<T> {
	private static final String URL_DELIMITER = "/";
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${rss.base.url}")
	private String baseUrl;

	private Resource dataResource;
	private JAXBContext jaxbContext;

	private final String urlSnippet;
	private final Class<T> fetchedClazz;

	/**
	 * @param fetchedClazz class to be searched for
	 * @param urlSnippet snipped of the url, e.g. <code>"events"</code> or <code>"speakers"</code>
	 */
	protected AbstractDataRetrievalStrategy(Class<T> fetchedClazz, String urlSnippet) {
		this.fetchedClazz = fetchedClazz;
		this.urlSnippet = urlSnippet;
	}

	@PostConstruct
	private void initializeContextAndResource() throws MalformedURLException, JAXBException {
		dataResource = new UrlResource(buildUrl());
		jaxbContext = JAXBContext.newInstance(fetchedClazz);
	}

	public T fetchData() throws JAXBException, IOException {
		logger.info("fetching data from {}", dataResource.getURL().getPath());

		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Object data = unmarshaller.unmarshal(dataResource.getInputStream());

		return fetchedClazz.cast(data);
	}

	public Class<T> getFetchedClass() {
		return fetchedClazz;
	}

	/**
	 * builds the valid url, e.g.: <br>
	 * <code>http://www.ehfg.org/feed/events/data.rss</code>
	 * <p>
	 * leading or ending slashed will automatically be removed/added, so the result will always be a valid url
	 * 
	 * @return
	 * @throws MalformedURLException
	 *             in case of an error
	 */
	private String buildUrl() throws MalformedURLException {
		if (baseUrl == null) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		builder.append(baseUrl);

		if (!baseUrl.endsWith(URL_DELIMITER) && !urlSnippet.startsWith(URL_DELIMITER)) {
			builder.append("/");
		}

		builder.append(urlSnippet);
		if (!urlSnippet.endsWith(URL_DELIMITER)) {
			builder.append(URL_DELIMITER);
		}

		builder.append("data.rss");

		return builder.toString();
	}
}