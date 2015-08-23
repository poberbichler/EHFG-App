package org.ehfg.app.program.repository;

import org.apache.commons.lang3.Validate;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

/**
 * Helper class to create {@link Element} for unit tests
 *
 * @author patrick
 * @since 08.2015
 */
class LinkCreator {
	static Element createWithAttributes(String... inputAttributes) {
		Validate.isTrue(inputAttributes.length % 2 == 0, "attributes are only allowed in pairs");

		final Attributes attributes = new Attributes();
		for (int i=0; i < inputAttributes.length; i += 2) {
			attributes.put(inputAttributes[i], inputAttributes[i+1]);
		}

		return new Element(Tag.valueOf("a"), "url", attributes);
	}
}
