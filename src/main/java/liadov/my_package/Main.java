package liadov.my_package;

public class Main {
    public static void main(String[] args) {
        Lesson1 lesson = new Lesson1();
        
        lesson.getStringLength("Practice with String");
        lesson.compareStringsIgnoreCase("testString1", "TESTString1");
        lesson.createStringByConstructor("Test String");
        lesson.getCharArrayFromString("Test String");
        lesson.getByteArrayFromString("Test String");
        lesson.convertStringToUpperCase("to upper case");
        lesson.getFirstPositionOfSymbol("test text with \"a\" literal", "a");
        lesson.getLastPositionOfSymbol("test text with \"a\" literal", "a");
        lesson.verifyIfStringContainsWord("at direct Sunlight", "Sun");
        lesson.verifyIfStringEndsWithWord("test string Oracle", "Oracle");
        lesson.verifyIfStringStartsWithWord("Java string Oracle", "Java");
        lesson.replaceAllSymbols("taste strange", "a", "o");
        lesson.getSubstring("It behaves the same and as long as the data set is small enough I would not worry about the small performance impact.");
        lesson.getWordsArrayFromSentence("It behaves the same");
        lesson.reverseSymbolsInString("Madam, I'm Adam");
    }
}