package org.ehfg.app.search;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.ehfg.app.twitter.TweetDTO;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author patrick
 * @since 07.2016
 */
class TweetToDocumentMapper {
	private static final String FIELD_AUTHOR_NAME = "author.name";
	private static final String FIELD_AUTHOR_NICKNAME = "author.nickname";
	private static final String FIELD_AUTHOR_IMAGE = "author.profileImage";
	private static final String FIELD_TIMESTAMP = "timestamp";
    private static final String FIELD_RETWEET = "retweet";
    private static final String FIELD_RETWEETED_BY = "retweet.by";

	static final String TYPE_TWEET = "TWEET";

	static Document from(TweetDTO tweet) {
		Document doc = new Document();
		doc.add(new StringField(Indexable.ID_FIELD, tweet.getId(), Field.Store.YES));
		doc.add(new TextField(Indexable.CONTENT_FIELD, tweet.getMessage(), Field.Store.YES));
		doc.add(new StringField(Indexable.TYPE_FIELD, TYPE_TWEET, Field.Store.YES));
		doc.add(new StringField(FIELD_AUTHOR_NAME, tweet.getFullName(), Field.Store.YES));
		doc.add(new StringField(FIELD_AUTHOR_NICKNAME, tweet.getNickName(), Field.Store.YES));
		doc.add(new StringField(FIELD_AUTHOR_IMAGE, tweet.getProfileImage(), Field.Store.YES));
		doc.add(new StringField(FIELD_TIMESTAMP, tweet.getTimestamp().toString(), Field.Store.YES));
        doc.add(new StringField(FIELD_RETWEET, Boolean.toString(tweet.isRetweet()), Field.Store.YES));
        doc.add(new StoredField(FIELD_RETWEETED_BY, tweet.getRetweetedBy().stream().collect(Collectors.joining(","))));
		return doc;
	}

	static TweetDTO from(Document doc) {
		return new TweetDTO(doc.get(Indexable.ID_FIELD),
				doc.get(FIELD_AUTHOR_NAME),
				doc.get(FIELD_AUTHOR_NICKNAME),
				doc.get(Indexable.CONTENT_FIELD),
				doc.get(FIELD_AUTHOR_IMAGE),
				LocalDateTime.parse(doc.get(FIELD_TIMESTAMP)),
                Boolean.parseBoolean(doc.get(FIELD_RETWEET)),
                Arrays.asList(doc.get(FIELD_RETWEETED_BY).split(",")));
	}
}
