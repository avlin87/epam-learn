package liadov.my_package;

public class Lesson1 {

    /*1*/
    public void getStringLength(String testString) {
        System.out.println("1. get string length: " + testString.length());
    }

    /*2*/
    public void compareStringsIgnoreCase(String testString1, String testString2) {
        System.out.print("2. Compare strings ignore case: ");
        if (testString1.compareToIgnoreCase(testString2) == 0) {
            System.out.println("Strings are equal");
        } else {
            System.out.println("Strings are different");
        }
    }

    /*3*/
    public void createStringByConstructor(String testString) {
        String newString = new String(testString);
        String stringFromPool = newString.intern();
        System.out.println("3. Create string by Constructor and add to pool: " + (testString == stringFromPool));
    }

    /*4*/
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

    /*5*/
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

    /*6*/
    public String convertStringToUpperCase(String to_upper_case) {
        String resultString = to_upper_case.toUpperCase();
        System.out.println("6. Convert String to upper case: " + resultString);
        return resultString;
    }

    /*7*/
    public void getFirstPositionOfSymbol(String test_string, String a) {
        System.out.println("7. Get first position of symbol: " + test_string.indexOf(a));
    }

    /*8*/
    public void getLastPositionOfSymbol(String test_string, String a) {
        System.out.println("8. Get last position of symbol: " + test_string.lastIndexOf(a));
    }

    /*9*/
    public boolean verifyIfStringContainsWord(String test_string, String word) {
        boolean isWordContained = test_string.contains(word);
        System.out.println("9. Check if string contain word \"" + word + "\": " + isWordContained);
        return isWordContained;
    }

    /*10*/
    public boolean verifyIfStringEndsWithWord(String test_string, String word) {
        boolean isWordEndsString = test_string.endsWith(word);
        System.out.println("10. Check if string ends with word \"" + word + "\": " + isWordEndsString);
        return isWordEndsString;
    }

    /*11*/
    public boolean verifyIfStringStartsWithWord(String test_string, String word) {
        boolean isWordStartsString = test_string.startsWith(word);
        System.out.println("11. Check if string starts with word \"" + word + "\": " + isWordStartsString);
        return isWordStartsString;
    }

    /*12*/
    public void replaceAllSymbols(String test_string, String a, String o) {
        String resultString = test_string.replaceAll(a, o);
        System.out.println("12. Replace all \"a\" with \"o\": " + resultString);
    }

    /*13*/
    public String getSubstring(String test_string) {
        String resultString = test_string.substring(44, 90);
        System.out.println("13. Get substring (begin index = 44, end index = 90): " + resultString);
        return resultString;
    }

    /*14*/
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

    /*15*/
    public String reverseSymbolsInString(String test_string){
        StringBuilder stringBuilder = new StringBuilder(test_string);
        String resultString = stringBuilder.reverse().toString();
        System.out.println("15. Reverse symbols in String: " + resultString);
        return resultString;
    }
}
