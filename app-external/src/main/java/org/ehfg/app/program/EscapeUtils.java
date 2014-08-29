package org.ehfg.app.program;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * helper class to remove unnecessary html text from the ehfg server
 * 
 * @author patrick
 * @since 22.06.2014
 */
class EscapeUtils {
	private EscapeUtils() {
		// do not allow instantiation
	}

	/**
	 * utility function to removed unnecessary html from the input text <br>
	 * first, every <code>&nbsp;</code> will be removed, and
	 * {@link StringEscapeUtils#unescapeHtml4(String)} will be called<br>
	 * after that, every image tag will be removed, as well as every empty
	 * paragraph <br>
	 * at last, every snippet of <code>style="font-familiy: ..."</code> will be
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
		String replace = styleAttribute.replaceAll("font-family: '[A-Za-z0-9]*'", "");
		select.attr("style", replace);

		// remove empty paragraphs
		for (Element element : document.select("p")) {
			if (element.text().trim().isEmpty()) {
				element.remove();
			}
		}

		return document.body().html();
	}
}
