package org.ehfg.app.twitter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ehfg.app.rest.TweetRepresentation;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author patrick
 * @since 20.03.2014
 */
public final class TweetDTO implements TweetRepresentation, Comparable<TweetDTO> {
	private final String id;
	private final String fullName;
	private final String nickName;
	private final String message;
	private final String profileImage;
	private final LocalDateTime timestamp;
    private final boolean retweet;
    private final Collection<String> retweetedBy;

    public TweetDTO(String id, String fullName, String nickName, String message, String profileImage, LocalDateTime timestamp, boolean retweet, Collection<String> retweetedBy) {
        this.id = id;
        this.fullName = fullName;
        this.nickName = nickName;
        this.message = message;
        this.profileImage = profileImage;
        this.timestamp = timestamp;
        this.retweet = retweet;
        this.retweetedBy = retweetedBy;
    }

    @Override
	public String getId() {
		return id;
	}

	@Override
	public String getFullName() {
		return fullName;
	}

	@Override
	public String getNickName() {
		return nickName;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getProfileImage() {
		return profileImage;
	}

	@Override
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

    @Override
    public boolean isRetweet() {
        return retweet;
    }

    @Override
    public Collection<String> getRetweetedBy() {
        return retweetedBy;
    }

    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int compareTo(TweetDTO o) {
		return this.getTimestamp().compareTo(o.getTimestamp());
	}
}
