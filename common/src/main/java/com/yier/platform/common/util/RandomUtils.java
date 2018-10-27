package com.yier.platform.common.util;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;

public class RandomUtils {
	private static RandomUtils _instance;

	private RandomStringGenerator alphabeticGenerator;

	private RandomStringGenerator alphaNumericGenerator;
	

	private RandomStringGenerator numericGenerator;

	public static RandomUtils instance() {
		if (_instance == null) {
			_instance = new RandomUtils();

			_instance.alphabeticGenerator = new RandomStringGenerator.Builder().filteredBy(CharacterPredicates.LETTERS)
					.withinRange('A', 'z').build();

			_instance.alphaNumericGenerator = new RandomStringGenerator.Builder()
					.filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS).withinRange('0', 'z').build();
			

			_instance.numericGenerator = new RandomStringGenerator.Builder()
					.filteredBy(CharacterPredicates.DIGITS).withinRange('0', '9').build();

		}
		return _instance;
	}

	/**
	 * <p>
	 * Creates a random string whose length is the number of characters specified.
	 * </p>
	 *
	 * <p>
	 * Characters will be chosen from the set of Latin alphabetic characters (a-z,
	 * A-Z).
	 * </p>
	 *
	 * @param count
	 *            the length of random string to create
	 * @return the random string
	 */
	public static String randomAlphabetic(final int length) {
		return RandomUtils.instance().alphabeticGenerator.generate(length);
	}

	/**
	 * <p>
	 * Creates a random string whose length is the number of characters specified.
	 * </p>
	 *
	 * <p>
	 * Characters will be chosen from the set of Latin alphabetic characters (a-z,
	 * A-Z) and the digits 0-9.
	 * </p>
	 *
	 * @param count
	 *            the length of random string to create
	 * @return the random string
	 */
	public static String randomAlphanumeric(final int length) {
		return RandomUtils.instance().alphaNumericGenerator.generate(length);
	}
	
	

    /**
     * <p>Creates a random string whose length is the number of characters
     * specified.</p>
     *
     * <p>Characters will be chosen from the set of numeric
     * characters.</p>
     *
     * @param count  the length of random string to create
     * @return the random string
     */
    public static String randomNumeric(final int length) {
    		return RandomUtils.instance().numericGenerator.generate(length);
    }

}
