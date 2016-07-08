package org.ehfg.app.search;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.ehfg.app.rest.SearchResultRepresentation;
import org.ehfg.app.twitter.TweetDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;

/**
 * @author patrick
 * @since 06.2016
 */
@Service
public class SearchServiceImpl implements SearchService {
	private static final int MAX_RESULTS = 50;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final Collection<SearchIndexDataProvider<Indexable>> providers;
	private final SearchIndexDataProvider<TweetDTO> tweetProvider;

	private Directory index = new RAMDirectory();

	@Autowired
	public SearchServiceImpl(Collection<SearchIndexDataProvider<Indexable>> providers, SearchIndexDataProvider<TweetDTO> tweetProvider) {
		this.providers = providers;
		this.tweetProvider = tweetProvider;
	}

	@Override
	public SearchResultRepresentation findBy(String input) {
		logger.info("searching for [{}]", input);

		if (StringUtils.isEmpty(input)) {
			return SearchResult.empty();
		}

		try {
			SearchResult result = new SearchResult();
			DirectoryReader directoryReader = StandardDirectoryReader.open(index);
			IndexSearcher indexSearcher = new IndexSearcher(directoryReader);

			TopDocs search = indexSearcher.search(new TermQuery(new Term(Indexable.CONTENT_FIELD, input)), MAX_RESULTS);
			logger.debug("found [{}] results for input [{}]", search.scoreDocs.length, input);
			for (ScoreDoc scoreDoc : search.scoreDocs) {
				Document doc = indexSearcher.doc(scoreDoc.doc);
				if (doc.get(Indexable.TYPE_FIELD).equals(TweetToDocumentMapper.TYPE_TWEET)) {
					result.add(TweetToDocumentMapper.from(doc));
				} else {
					result.add(new SearchResultItem(
							doc.get(Indexable.ID_FIELD),
							ResultType.valueOf(doc.get(Indexable.TYPE_FIELD)),
							doc.get(Indexable.NAME_FIELD)));
				}
			}

			return result;
		} catch (IOException e) {
			logger.error("this IOException should never happen, we are doing no I/O", e);
		}

		return SearchResult.empty();
	}

	@Override
	@EventListener(UpdateIndexEvent.class)
	public void buildIndex() {
		try {
			if (index != null) {
				index.close();
			}

			// create a new index
			index = new RAMDirectory();

			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new StandardAnalyzer());
			try (IndexWriter indexWriter = new IndexWriter(index, indexWriterConfig)) {
				for (SearchIndexDataProvider<Indexable> provider : providers) {
					logger.info("creating index for types [{}]", provider.getResultTypes());
					for (Indexable indexable : provider.getData()) {
						indexWriter.addDocument(buildDocument(indexable));
					}
				}

				logger.info("creating index for tweets...");
				for (TweetDTO tweetDTO : tweetProvider.getData()) {
					logger.debug("creating index for tweet [{}]", tweetDTO.getId());
					indexWriter.addDocument(TweetToDocumentMapper.from(tweetDTO));
				}
			}
		} catch (IOException e) {
			logger.error("this IOException should never happen, we are doing no I/O", e);
		}
	}

	private Document buildDocument(Indexable indexableObject) {
		final Document doc = new Document();
		doc.add(new TextField(Indexable.CONTENT_FIELD, new StringReader(indexableObject.getDescription())));
		doc.add(new StringField(Indexable.NAME_FIELD, indexableObject.getDisplayName(), Field.Store.YES));
		doc.add(new StringField(Indexable.TYPE_FIELD, indexableObject.getType().toString(), Field.Store.YES));
		doc.add(new StringField(Indexable.ID_FIELD, indexableObject.getId(), Field.Store.YES));
		return doc;
	}
}
