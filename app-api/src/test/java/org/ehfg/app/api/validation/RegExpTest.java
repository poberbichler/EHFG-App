package org.ehfg.app.api.validation;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author patrick
 * @since 21.04.2014
 */
@RunWith(Parameterized.class)
public class RegExpTest {
	private final String website;
	private final boolean value;

	public RegExpTest(String website, boolean value) {
		this.website = website;
		this.value = value;
	}

	@Parameters
	public static final Collection<Object[]> getWebsites() {
		return Arrays.asList(new Object[][] { 
				{ "asdf" , false}, 
				{ "dfgjsdflgkj" , false},
				{ "", false},
				{"www.ehfg!", false},
				{"ehfg.org", false},
				{"http://www.ehfg.org", true},
				{"https://www.ehfg.org", true},
				{"www.ehfg.org", true},
				{"www.ehfg.org?asdf=123", true},
			});
	}

	@Test
	@Ignore
	public void regExpShouldReturnCorrectResults() {
		Assert.assertEquals(String.format("'%s' should%s match regular expression '%s'.", website, (value ? "" : " not"), RegExp.WEBSITE), 
				value, website.matches(RegExp.WEBSITE));
	}
}
