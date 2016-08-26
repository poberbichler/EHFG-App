package org.ehfg.app.program.repository;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * helper class to remove unnecessary html text from the ehfg server
 * 
 * @author patrick
 * @since 06.2014
 */
final class EscapeUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(EscapeUtils.class);

	private EscapeUtils() {
		// do not allow instantiation
	}

	/**
	 * utility function to removed unnecessary html from the input text <br>
	 * first, every {@code &nbsp;} will be removed, and
	 * {@link StringEscapeUtils#unescapeHtml4(String)} will be called<br>
	 * after that, every image tag will be removed, as well as every empty
	 * paragraph <br>
	 * at last, every snippet of <code>style="font-family: ..."</code> will be
	 * removed
	 * 
	 * @param inputText
	 *            to be escaped
	 * @return unescaped version of the text
	 */
	static String escapeText(String inputText) {
		String escapedText = StringEscapeUtils.unescapeHtml4(inputText.replaceAll("&nbsp;", ""));
		Document document = Jsoup.parse(escapedText);

		// remove stuff overriding our own styles
		document.select("img").remove();
		
		// remove colors of fonts
		document.select("font").removeAttr("color").attr("style", "font-style: italic");

		// remove different fonts
		Elements select = document.select("*[style*=font-family]");
		String styleAttribute = select.attr("style");
		String replace = styleAttribute.replaceAll("font-family:.*[A-Za-z0-9]*;?", "");
		select.attr("style", replace);

		document.select("p").stream()
				.filter(element -> element.text().trim().isEmpty())
				.forEach(Element::remove);

		document.select("a").stream()
				.filter(element -> element.text().trim().isEmpty())
				.forEach(Element::remove);

		return document.body().html();
	}

	static String escapeLinks(String inputText) {
		Document document = Jsoup.parse(inputText);
		document.select("a").stream()
				.filter(LinkPredicates.MAIL_TO.negate())
				.filter(LinkPredicates.SESSION_LINK.negate())
				.forEach(LinkModifiers.HREF_TO_ONCLICK);

		document.select("a").stream()
				.filter(LinkPredicates.SESSION_LINK)
				.forEach(LinkModifiers.TO_APP_LINK);

		return document.body().html();
	}

	enum LinkPredicates implements Predicate<Element> {
		MAIL_TO {
			@Override
			public boolean test(Element element) {
				return element.attr("href").startsWith("mailto");
			}
		},

		SESSION_LINK {
			@Override
			public boolean test(Element element) {
				return element.attr("href").matches("(https?://)?www.ehfg.org/detailevent1?.html\\?eid=\\d+");
			}
		}
	}

	enum LinkModifiers implements Consumer<Element> {
		HREF_TO_ONCLICK {
			@Override
			public void accept(Element element) {
				final String href = element.attr("href");
				element.attr("href", "#");
				element.attr("onclick", String.format("window.open('%s', '_blank')", href));
			}
		},

		TO_APP_LINK {
			@Override
			public void accept(Element element) {
				final String href = element.attr("href");
				final String[] splitString = StringUtils.split(href, "=");

				if (splitString == null || splitString.length != 2) {
					LOGGER.error("wrong url to be parsed as session link [{}] - this should not happen", href);
				} else {
					element.attr("href", "#/sessions/".concat(splitString[1]));
					element.removeAttr("target");
				}
			}
		}
	}
}
