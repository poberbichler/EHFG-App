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
import org.ehfg.app.base.MasterDataFacade;
import org.ehfg.app.program.ProgramFacade;
import org.ehfg.app.program.SpeakerDTO;
import org.ehfg.app.rest.SearchResultRepresentation;
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
			final IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
			final TermQuery query = new TermQuery(new Term("content", input));

			final TopDocs search = indexSearcher.search(query, 50);
			for (ScoreDoc scoreDoc : search.scoreDocs) {
				final Document doc = indexSearcher.doc(scoreDoc.doc);
				result.add(new SearchResultItem(doc.get("id"), ResultType.valueOf(doc.get("type")), doc.get("name")));
				logger.info("{}", doc);
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
				logger.info("creating index for speaker [{}]", speakerDTO.getFullName());
				final Document doc = new Document();
				doc.add(new TextField("content", new StringReader(speakerDTO.getDescription())));
				doc.add(new StringField("name", speakerDTO.getFullName(), Field.Store.YES));
				doc.add(new StringField("type", ResultType.SPEAKER.toString(), Field.Store.YES));
				doc.add(new StringField("id", speakerDTO.getId(), Field.Store.YES));
				indexWriter.addDocument(doc);
			}

			indexWriter.close();
		} catch (IOException e) {
			logger.error("this IOException should never happen, we are doing no I/O", e);
		}
	}
}
