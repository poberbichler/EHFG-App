package org.ehfg.app.search;

import org.ehfg.app.twitter.TweetDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Checks whether the registered beans of type SearchIndexDataProvider are valid
 *
 * @author patrick
 * @since 07.2016
 */
@Component
public class ValidateSearchIndexDataProvider {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private Collection<SearchIndexDataProvider<?>> providers;

	@Autowired
	public ValidateSearchIndexDataProvider(Collection<SearchIndexDataProvider<?>> providers) {
		this.providers = providers;
	}

	@PostConstruct
	public void checkIfProvidersAreValid() {
		logger.info("checking if every [{}] is correctly set up", SearchIndexDataProvider.class);

		Set<ResultType> foundTypes = providers.stream()
				.map(SearchIndexDataProvider::getResultTypes)
				.flatMap(Collection::stream)
				.collect(Collectors.toSet());

		if (foundTypes.size()!= ResultType.values().length) {
			logger.error("found dataprovider for [{}], at least one is missing", foundTypes);
			throw new BeanInstantiationException(SearchIndexDataProvider.class,
					String.format("found dataprovider for [%s], at least one is missing", foundTypes));
		}

		List<SearchIndexDataProvider> tweetProviders = providers.stream()
				.filter(provider -> provider.getResultTypes().isEmpty())
				.collect(Collectors.toList());

		if (tweetProviders.size() != 1) {
			logger.error("more then one dataprovider without resulttype found, only one is expected: [{}]", tweetProviders);
			throw new BeanInstantiationException(SearchIndexDataProvider.class,
					String.format("more then one dataprovider withut resulttype found, only one is expected: [%s]", tweetProviders));
		}

		boolean tweetProviderFound = false;
		for (ResolvableType definedInterface : ResolvableType.forInstance(tweetProviders.get(0)).getInterfaces()) {
			for (ResolvableType genericInterface : definedInterface.getGenerics()) {
				if (genericInterface.getRawClass() == TweetDTO.class) {
					tweetProviderFound = true;
				}
			}
		}

		if (!tweetProviderFound) {
			logger.error("no {} for {} found", SearchIndexDataProvider.class, TweetDTO.class);
			throw new BeanInstantiationException(SearchIndexDataProvider.class,
					String.format("no %s for %s was found", SearchIndexDataProvider.class, TweetDTO.class));
		}
	}
}
