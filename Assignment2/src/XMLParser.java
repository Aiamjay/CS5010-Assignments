/**
 * This interface represents a simple XML parser that accepts input one character at a time.
 */
public interface XMLParser {
  /**
   * Accept a single character as input, and return the new parser as a result of handling this
   * character.
   *
   * @param c the input character
   * @return the parser after handling the provided character
   * @throws InvalidXMLException if the input causes the XML to be invalid
   */
  XMLParser input(char c) throws InvalidXMLException;

  /**
   * Provide the output of the parser, given all the inputs it has been provided so far. The content
   * and format of this output is defined by individual implementations.
   *
   * @return the output of the parser as a String object
   */
  String output();
}
