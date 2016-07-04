package org.ehfg.app.search;

import org.apache.lucene.analysis.Analyzer;
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
import org.ehfg.app.base.LocationDTO;
import org.ehfg.app.base.MasterDataFacade;
import org.ehfg.app.base.PointOfInterestDTO;
import org.ehfg.app.program.ProgramFacade;
import org.ehfg.app.program.SessionDTO;
import org.ehfg.app.program.SpeakerDTO;
import org.ehfg.app.rest.SearchResultRepresentation;
import org.ehfg.app.twitter.TweetDTO;
import org.ehfg.app.twitter.TwitterFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;

/**
 * @author patrick
 * @since 06.2016
 */
@Service
public class SearchServiceImpl implements SearchService {
	public static final int MAX_RESULTS = 50;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final ProgramFacade programFacade;
	private final MasterDataFacade masterDataFacade;
	private final TwitterFacade twitterFacade;

	private Directory index = new RAMDirectory();

	@Autowired
	public SearchServiceImpl(ProgramFacade programFacade, MasterDataFacade masterDataFacade, TwitterFacade twitterFacade) {
		this.programFacade = programFacade;
		this.masterDataFacade = masterDataFacade;
		this.twitterFacade = twitterFacade;
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
	@PostConstruct
	public void buildIndex() {
		try {
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			IndexWriter indexWriter = new IndexWriter(index, indexWriterConfig);

			for (SpeakerDTO speakerDTO : programFacade.findAllSpeakers()) {
				logger.debug("creating index for speaker [{}]", speakerDTO.getDisplayName());
				indexWriter.addDocument(buildDocument(speakerDTO));
			}

			for (SessionDTO sessionDTO : programFacade.findAllSessionsWithoutDayInformation()) {
				logger.debug("creating index for session [{}]", sessionDTO.getDisplayName());
				indexWriter.addDocument(buildDocument(sessionDTO));
			}

			for (LocationDTO locationDTO : masterDataFacade.findAllLocation()) {
				logger.debug("creating index for location [{}]", locationDTO.getDisplayName());
				indexWriter.addDocument(buildDocument(locationDTO));

			}

			for (PointOfInterestDTO pointOfInterestDTO : masterDataFacade.findAllPointsOfInterest()) {
				logger.debug("creating index for point [{}]", pointOfInterestDTO.getDisplayName());
				indexWriter.addDocument(buildDocument(pointOfInterestDTO));
			}

			String hashtag = masterDataFacade.getAppConfiguration().getHashtag();
			for (TweetDTO tweetDTO : twitterFacade.findTweetsForExport(hashtag)) {
				logger.debug("creating index for tweet [{}]", tweetDTO.getId());
				indexWriter.addDocument(TweetToDocumentMapper.from(tweetDTO));
			}


			indexWriter.close();
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
