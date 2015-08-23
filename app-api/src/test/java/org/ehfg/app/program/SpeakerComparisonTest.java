package org.ehfg.app.program;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * TODO: should be updated to new logic
 * 
 * @author patrick
 * @since 05.07.2014
 */
@Ignore
public final class SpeakerComparisonTest {
	@Test
	public void shouldReturn0forSameSpeaker() {
		assertEquals(0, speaker("a", "b").compareTo(speaker("a", "b")));
		assertEquals(0, speaker("", "b").compareTo(speaker("", "b")));
		assertEquals(0, speaker("a", "").compareTo(speaker("a", "")));
	}
	
	@Test
	public void shouldPutSpeakersWithoutFirstnameToEnd() {
		assertEquals(1, speaker("", "aaa").compareTo(speaker("zzzz", "bbbbb")));
		assertEquals(1, speaker("", "bbbbb").compareTo(speaker("zzzz", "aaaaa")));
		
		assertEquals(-1, speaker("zzzz", "bbbbb").compareTo(speaker("", "aaaaa")));
		assertEquals(-1, speaker("aaaaa", "bbbbb").compareTo(speaker("", "aaaaa")));
	}
	
	@Test
	public void shouldPutWithoutLastnameToEnd() {
		assertEquals(1, speaker("aaaa", "").compareTo(speaker("zzzz", "bbbb")));
		assertEquals(-1, speaker("zzzz", "bbbb").compareTo(speaker("aaaa", "")));
	}
	
	@Test
	public void shouldCompareLastnamesIfFirstnamesAreEmpty() {
		assertEquals(-1, speaker("", "aaaa").compareTo(speaker("", "bbbb")));
		assertEquals(1, speaker("", "bbbb").compareTo(speaker("", "aaaa")));
		assertEquals(0, speaker("", "zzzz").compareTo(speaker("", "zzzz")));
	}
	
	/**
	 * @return {@link SpeakerDTO} with the given parameters
	 */
	private SpeakerDTO speaker(String firstName, String lastName) {
		return new SpeakerDTO.Builder().firstName(firstName).lastName(lastName).build();
	}
}
