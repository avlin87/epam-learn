package liadov.mypackage.lesson7;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StackOverflowErrorProvider {

    /**
     * Method calls for itself with endless call
     *
     * @param num levels of cake napoleon
     * @throws StackOverflowError for recursive Method
     */
    public int recursiveCall(int num) throws StackOverflowError {
        log.info("recursiveCall started with {} number", num);
        return recursiveCall(++num);
    }
}
