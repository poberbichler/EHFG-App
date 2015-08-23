package org.ehfg.app.program.repository;

import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author patrick
 * @since 08.2015
 */
@RunWith(Parameterized.class)
public class LinkModifiersTest {
	private final String givenLink;
	private final String expectedLink;
	private final With targetAttribute;

	public LinkModifiersTest(String givenLink, String expectedLink, With targetAttribute) {
		this.givenLink = givenLink;
		this.expectedLink = expectedLink;
		this.targetAttribute = targetAttribute;
	}

	@Parameterized.Parameters
	public static Iterable<Object[]> parameters() {
		return Arrays.asList(
				new Object[] {"www.ehfg.org/detailevent.html?eid=1", "#/sessions/1", With.TARGET_ATTRIBUTE},
				new Object[] {"www.ehfg.org/detailevent.html?eid=123", "#/sessions/123", With.NO_TARGET_ATTRIBUTE},
				new Object[] {"www.ehfg.org/detailevent.html?eid=", "www.ehfg.org/detailevent.html?eid=", With.TARGET_ATTRIBUTE},
				new Object[] {"asdf", "asdf", With.NO_TARGET_ATTRIBUTE});
	}

	@Test
	public void mapLinkCorrectToAppLinks() {
		final Element element = LinkCreator.createWithAttributes("href", givenLink);
		if (targetAttribute == With.TARGET_ATTRIBUTE) {
			element.attr("target", "_blank");
		}

		EscapeUtils.LinkModifiers.TO_APP_LINK.accept(element);
		assertEquals(expectedLink, element.attr("href"));

		if (expectedLink.equals(givenLink) && targetAttribute == With.TARGET_ATTRIBUTE) {
			assertTrue(element.hasAttr("target"));
		}
	}

	private enum With {
		TARGET_ATTRIBUTE,
		NO_TARGET_ATTRIBUTE;
	}
}