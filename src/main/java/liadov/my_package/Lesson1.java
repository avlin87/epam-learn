package liadov.my_package;

/**
 * Lesson1 contains String practice methods
 *
 * @author Aleksandr Liadov
 */

public class Lesson1 {

    /**
     * Task 1 - Get length of a String
     * @param testString length of this String variable will be printed in Console
     */
    public void getStringLength(String testString) {
        System.out.println("1. get string length: " + testString.length());
    }

    /**
     * Task 2 - Compare two Strings ignoring case
     * @param testString1 first String for comparison
     * @param testString2 second String for comparison
     */
    public void compareStringsIgnoreCase(String testString1, String testString2) {
        System.out.print("2. Compare strings ignore case: ");
        if (testString1.compareToIgnoreCase(testString2) == 0) {
            System.out.println("Strings are equal");
        } else {
            System.out.println("Strings are different");
        }
    }

    /**
     * Task 3 -  Create new String via Constructor and add it to String Pool
     * @param testString string value that will be used for creation of new String
     */
    public void createStringByConstructor(String testString) {
        String newString = new String(testString);
        String stringFromPool = newString.intern();
        System.out.println("3. Create string by Constructor and add to pool: " + (testString == stringFromPool));
    }

    /**
     * Task 4 - Get char array from String
     * @param test_string this string will be converted to char array
     * @return char[]
     */
    public char[] getCharArrayFromString(String test_string) {
        System.out.print("4. Get Char array from String: [");
        char[] resultCharArray = test_string.toCharArray();
        for (int i = 0; i < resultCharArray.length; i++) {
            if (i < resultCharArray.length - 1)
                System.out.print(resultCharArray[i] + " ");
            else
                System.out.print(resultCharArray[i]);
        }
        System.out.println("]");
        return resultCharArray;
    }

    /**
     * Task 5 - Get byte Array from String
     * @param test_string this string will be converted to byte array
     * @return byte[]
     */
    public byte[] getByteArrayFromString(String test_string) {
        System.out.print("5. Get Byte array from String: [");
        byte[] resultByteArray = test_string.getBytes();
        for (int i = 0; i < resultByteArray.length; i++) {
            if (i < resultByteArray.length - 1)
                System.out.print(resultByteArray[i] + " ");
            else
                System.out.print(resultByteArray[i]);
        }
        System.out.println("]");
        return resultByteArray;
    }

    /**
     * Task 6 - Convert String to upper case
     * @param to_upper_case this string will be converted to upper case
     * @return String
     */
    public String convertStringToUpperCase(String to_upper_case) {
        String resultString = to_upper_case.toUpperCase();
        System.out.println("6. Convert String to upper case: " + resultString);
        return resultString;
    }

    /**
     * Task 7 - Find first position of a symbol in string
     * @param test_string target string for search operation
     * @param a symbol which position will be printed in console
     */
    public void getFirstPositionOfSymbol(String test_string, String a) {
        System.out.println("7. Get first position of symbol: " + test_string.indexOf(a));
    }

    /**
     * Task 8 - Find last position of a symbol
     * @param test_string target string for search operation
     * @param a symbol which position will be printed in console
     */
    public void getLastPositionOfSymbol(String test_string, String a) {
        System.out.println("8. Get last position of symbol: " + test_string.lastIndexOf(a));
    }

    /**
     * Task 9 - Check whether String variable contains "Sun" word
     * @param test_string target string for search operation
     * @param word this string be checked whether target string contains it
     * @return boolean
     */
    public boolean verifyIfStringContainsWord(String test_string, String word) {
        boolean isWordContained = test_string.contains(word);
        System.out.println("9. Check if string contain word \"" + word + "\": " + isWordContained);
        return isWordContained;
    }

    /**
     * Task 10 - Check whether String variable ends with "Oracle" word
     * @param test_string target string for search operation
     * @param word this string be checked whether target string ends with it
     * @return boolean
     */
    public boolean verifyIfStringEndsWithWord(String test_string, String word) {
        boolean isWordEndsString = test_string.endsWith(word);
        System.out.println("10. Check if string ends with word \"" + word + "\": " + isWordEndsString);
        return isWordEndsString;
    }

    /**
     * Task 11 - Check whether String variable starts with "Oracle" word
     * @param test_string target string for search operation
     * @param word this string be checked whether target string starts with it
     * @return boolean
     */
    public boolean verifyIfStringStartsWithWord(String test_string, String word) {
        boolean isWordStartsString = test_string.startsWith(word);
        System.out.println("11. Check if string starts with word \"" + word + "\": " + isWordStartsString);
        return isWordStartsString;
    }

    /**
     * Task 12 - Replace all "a" symbols with "o" symbols
     * @param test_string target string for replace operation
     * @param a symbol that will be replaced
     * @param o replacement will be by this symbol
     */
    public void replaceAllSymbols(String test_string, String a, String o) {
        String resultString = test_string.replaceAll(a, o);
        System.out.println("12. Replace all \"a\" with \"o\": " + resultString);
    }

    /**
     * Task 13 - Get substring from string variable (start = 44, end = 90)
     * @param test_string target string for substring operation
     * @return String
     */
    public String getSubstring(String test_string) {
        String resultString = test_string.substring(44, 90);
        System.out.println("13. Get substring (begin index = 44, end index = 90): " + resultString);
        return resultString;
    }

    /**
     * Task 14 - Get String array from string variable via splitting string variable by space symbol
     * @param test_string target string for splitting operation
     * @return String[]
     */
    public String[] getWordsArrayFromSentence(String test_string) {
        System.out.print("14. Words array from sentence: [");
        String[] resultWordsArray = test_string.split(" ");
        for (int i = 0; i < resultWordsArray.length; i++) {
            if (i < resultWordsArray.length - 1)
                System.out.print("\"" + resultWordsArray[i] + "\", ");
            else
                System.out.print(resultWordsArray[i] + "\"");
        }
        System.out.println("]");
        return resultWordsArray;
    }

    /**
     * Task 15 - Change sequence of symbols in String to reversed
     * @param test_string target string for reverse operation
     * @return String
     */
    public String reverseSymbolsInString(String test_string) {
        StringBuilder stringBuilder = new StringBuilder(test_string);
        String resultString = stringBuilder.reverse().toString();
        System.out.println("15. Reverse symbols in String: " + resultString);
        return resultString;
    }
}
