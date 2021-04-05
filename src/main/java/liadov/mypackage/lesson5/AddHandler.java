package liadov.mypackage.lesson5;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddHandler {
    private int rowNumber;
    private String fileName;
    private String text;
    private String[] inputText;

    public AddHandler(String inputText) {
        this.inputText = inputText.split(" ");
        log.info(this.toString());
    }

    public void proceedAddScenario() {
        validateRowNumber();
        validateFileName();
        validateText();
    }

    private boolean validateRowNumber() {
        try {
            rowNumber = Integer.parseInt(inputText[1]);
            log.info("rowNumber parsed as {}", rowNumber);
            return true;
        } catch (NumberFormatException e) {
            log.warn(e.toString());
        }
        return false;
    }

    private boolean validateFileName() {
        try {
            fileName = inputText[2];
            log.info("fileName parsed as {}", fileName);
            return true;
        } catch (Exception e) {
            log.warn(e.toString());
        }
        return false;
    }

    private boolean validateText() {
        try {
            StringBuilder sb = new StringBuilder();
            if (inputText.length>3){
                for (int i = 3; i <inputText.length ; i++) {
                    sb.append(inputText[i]);
                    sb.append(" ");
                }
                sb.trimToSize();
            }
            text = sb.toString();
            log.info("text parsed as {} ", text);
            return true;
        } catch (Exception e) {
            log.warn(e.toString());
        }
        return false;
    }

}
