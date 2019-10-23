package expression;

public interface Expression<T> {

    /**
     * This function is to return the infix form of a expression string.
     *
     * @return the infix string of a expression string.
     */
    String infix();

    /**
     * This function takes no arguments and returns the result of evaluating the expression as a double.
     *
     * @return the result of evaluating
     */
    double evaluate();

    /**
     * This method schemeExpression takes no arguments and returns the expression as a string, which would be
     * typed in the functional programming language scheme.
     *
     * @return the string expression in scheme form.
     */
    String schemeExpression();

}
