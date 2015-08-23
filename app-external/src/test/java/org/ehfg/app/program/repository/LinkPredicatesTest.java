package org.ehfg.app.program.repository;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * @author patrick
 * @since 08.2015
 */
@RunWith(Parameterized.class)
public class LinkPredicatesTest {
	private final String testedValue;
	private final boolean expectedResult;

	public LinkPredicatesTest(String testedValue, boolean expectedResult) {
		this.testedValue = testedValue;
		this.expectedResult = expectedResult;
	}

	@Parameterized.Parameters
	public static Iterable<Object[]> parameters() {
		return Arrays.asList(
				new Object[] {"asdf", false},
				new Object[] {"mailto:asdfadsf", false},
				new Object[] {"www.ehfg.org", false},
				new Object[] {"detailevent.html?eid=", false},
				new Object[] {"www.ehfg.org/detailevent.html?eid=", false},
				new Object[] {"www.ehfg.org/detailevent.html?eid=123", true},
				new Object[] {"https://www.ehfg.org/detailevent.html?eid=1", true},
				new Object[] {"https://www.ehfg.org/detailevent1.html?eid=1", true},
				new Object[] {"https://www.ehfg.org/detailevent1.html?eid=", false},
				new Object[] {"http://www.ehfg.org/detailevent.html?eid=123345345", true});
	}

	@Test
	public void shouldCheckSessionLinks() {
		final Attributes attributes = new Attributes();
		attributes.put("href", testedValue);

		final Element inputElement = new Element(Tag.valueOf("a"), "url", attributes);
		assertEquals(expectedResult, EscapeUtils.LinkPredicates.SESSION_LINK.test(inputElement));
	}
}