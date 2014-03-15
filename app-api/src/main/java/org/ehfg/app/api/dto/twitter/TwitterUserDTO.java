package org.ehfg.app.api.dto.twitter;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author patrick
 * @since 13.03.2014
 */
public class TwitterUserDTO {
	public final Long id;
	public final String fullName;
	public final String nickName;
	public final String profileImage;

	public TwitterUserDTO(Long id, String fullName, String nickName,
			String profileImage) {
		this.id = id;
		this.fullName = fullName;
		this.nickName = nickName;
		this.profileImage = profileImage;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
