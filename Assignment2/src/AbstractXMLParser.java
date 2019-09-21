import java.util.Set;
import java.util.stream.Collectors;

/**
 * Abstract for XMLValidator and XMLLogger. Included: 1. Three different status for XMLValidator
 * output function. 2. Different error message if InvalidXMLException occurs. 3. Common tool
 * functions for subclass.
 */
public abstract class AbstractXMLParser implements XMLParser {

  /*
  1. each tag has a corresponding end tag with the same name.
  2. < > used to start and end the tag. cannot be included for other purpose except for this purpose
  3. between a start tag and a end tag there can only be in another tag or arbitrary string
  4. tags muse be properly nested
  5. tag name can only contain characters 'a-z''A-Z''0-9':_-
  6. a tag name cannot start with a number or -
  7. only one root tag pair
  8. space tab are not allowed at tag name could be in any other places.
  */

  protected enum OutputStatus {
    STATUS_EMPTY("Status:Empty"),
    STATUS_READ_TAG("Status:Incomplete"),
    STATUS_READ_CONTENT("Status:Incomplete"),
    STATUS_INCOMPLETE("Status:Incomplete"),
    STATUS_VALID("Status:Valid");
    private String status;

    OutputStatus(String status) {
      this.status = status;
    }

    /**
     * Get the string description of status.
     *
     * @return
     */
    public String getStatus() {
      return this.status;
    }
  }

  /**
   * When InvalidXMLException occurs, a description will be from one of the followings.
   */
  public enum ErrorMsg {
    INVALID_CHAR_IN_COMTENT("Invalid char in tag content"),
    FIRST_OF_XML_ERROR("The first char of XML should be space or <"),
    ONLY_ONE_ROOT_TAG_PAIR("There is only one root in XML"),
    TAG_PAIR_NAME_NOT_MATCH("The name of Tag pair doesn't match!"),
    INVALID_TAG_CHAR("Invalid char for tag name!"),
    INVALID_TAG_FIRST_CHAR("Invalid first char for tag name!");
    private String error;

    ErrorMsg(String error) {
      this.error = error;
    }

    /**
     * Get the String description of what error occurs.
     *
     * @return string description of error.
     */
    public String getError() {
      return this.error;
    }
  }

  private static final String CONTENT_HEADER = "Characters:";
  private static final String START_HEADER = "Started:";
  private static final String END_HEADER = "Ended:";
  private static final String SPACE = "\n\t\b ";

  private static final String VALID_TAG_CHAR = "abcdefghijklmnopqrstuvwxyz"
          + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
          + ":_";
  private static final char TAG_START_MARKER = '<';
  private static final char TAG_END_MARKER = '>';
  private static final char ENCLOSE_TAG_MARKER = '/';

  private static final Set<Character> VALID_TAG_FIRST_OF_NAME_SET = (VALID_TAG_CHAR + '/').chars()
          .mapToObj(c -> (char) c).collect(Collectors.toSet());
  private static final Set<Character> VALID_TAG_NAME_SET = (VALID_TAG_CHAR + "1234567890-")
          .chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
  private static final Set<Character> SPACE_SET = SPACE.chars()
          .mapToObj(c -> (char) c).collect(Collectors.toSet());

  @Override
  public XMLParser input(char c) throws InvalidXMLException {
    switch (getStatus()) {
      case STATUS_EMPTY:
        if (checkNotSpace(c) && !checkStartOfTag(c)) {
          throwException(ErrorMsg.FIRST_OF_XML_ERROR);
        } else if (checkStartOfTag(c)) {
          setStatus(OutputStatus.STATUS_READ_TAG);
        }
        break;
      case STATUS_VALID:
        if (checkNotSpace(c)) {
          setStatus(OutputStatus.STATUS_INCOMPLETE);
          throwException(ErrorMsg.ONLY_ONE_ROOT_TAG_PAIR);
        }
        break;
      case STATUS_READ_TAG:
        processTag(c);
        break;
      case STATUS_READ_CONTENT:
        if (checkEndOfTag(c)) {
          throwException(ErrorMsg.INVALID_CHAR_IN_COMTENT);
        }
        processContent(c);
        break;
      default:
        break;
    }
    return this;
  }

  /**
   * Use this method to reset a Parser, when you want to reuse it.
   */
  public abstract void reset();

  /**
   * Get current parsing status.
   *
   * @return OutputStatus
   */
  protected abstract OutputStatus getStatus();

  /**
   * Set the current output status.
   *
   * @param status output status
   */
  protected abstract void setStatus(OutputStatus status);

  /**
   * Function is intended to read in char as part of a tag name and process it.
   *
   * @param c current char
   * @throws InvalidXMLException exception occurs when current char is not valid char for tag name.
   */
  protected abstract void processTag(char c) throws InvalidXMLException;

  /**
   * Function is intended to read in char as part of a content.
   *
   * @param c current char
   */
  protected abstract void processContent(char c) throws InvalidXMLException;

  /**
   * Check whether current char is a valid name.
   *
   * @param tagName the tag name read in until current char.
   * @param c       current char
   * @throws InvalidXMLException current char is not a valid char for tag name.
   */
  protected static void checkValidTagChar(String tagName, char c) throws InvalidXMLException {
    if (tagName.isEmpty() && isInValidFirstCharOfTagName(c)) {
      throwException(ErrorMsg.INVALID_TAG_FIRST_CHAR);
    } else if (!checkEndOfTag(c) && !tagName.isEmpty() && isInValidCharOfTagName(c)) {
      throwException(ErrorMsg.INVALID_TAG_CHAR);
    }
  }

  /**
   * Check current char c is a valid candidate as the char of a start-tag content, not the first
   * char.
   *
   * @param c current char.
   * @return whether the char ias a valid candidate.
   */
  protected static boolean isInValidCharOfTagName(char c) {
    return !VALID_TAG_NAME_SET.contains(c);
  }

  /**
   * Check the tag pair have the same name. Each char must be the same.
   *
   * @param startTagName start tag name.
   * @param endTagName   end tag name.
   * @throws InvalidXMLException exception
   */
  protected static void checkNameOfTagPair(String startTagName, String endTagName)
          throws InvalidXMLException {
    if (endTagName.length() > 1 && checkEncloseOfTag(endTagName.charAt(0))) {
      if (startTagName.isEmpty() || startTagName.charAt(endTagName.length() - 2)
              != endTagName.charAt(endTagName.length() - 1)) {
        throwException(ErrorMsg.TAG_PAIR_NAME_NOT_MATCH);
      }
    }
  }

  /**
   * Check current char c is a valid candidate as the first char of a start-tag.
   *
   * @param c current char
   * @return c
   */
  protected static boolean isInValidFirstCharOfTagName(char c) {
    return !VALID_TAG_FIRST_OF_NAME_SET.contains(c);
  }

  /**
   * Check current char is a char in \b  \t  \n or space.
   *
   * @param c current char
   * @return whether current char is \b  \t  \n or space
   */
  protected static boolean checkNotSpace(char c) {
    return !SPACE_SET.contains(c);
  }

  /**
   * Check current char equals to <.
   *
   * @param c current char
   * @return c == '<' ?
   */
  protected static boolean checkStartOfTag(char c) {
    return c == TAG_START_MARKER;
  }

  /**
   * Check current char equals to '/'.
   *
   * @param c current char
   * @return c == '/'
   */
  protected static boolean checkEncloseOfTag(char c) {
    return c == ENCLOSE_TAG_MARKER;
  }

  /**
   * Check current char equals to '>'.
   *
   * @param c current char
   * @return c == '>'
   */
  protected static boolean checkEndOfTag(char c) {
    return c == TAG_END_MARKER;
  }

  /**
   * When current content should be logged out, get the right format.
   *
   * @param content content
   * @return right format string
   */
  protected static String getContentStr(String content) {
    return CONTENT_HEADER + content;
  }

  /**
   * When current start tag should be logged out, get the right format.
   *
   * @param content content
   * @return right format string
   */
  protected static String getStartStr(String content) {
    return START_HEADER + content;
  }

  /**
   * When current end tag should be logged out, get the right format.
   *
   * @param content content
   * @return right format string
   */
  protected static String getEndStr(String content) {
    return END_HEADER + content;
  }

  protected static void throwException(ErrorMsg msg) throws InvalidXMLException {
    throw new InvalidXMLException(msg.getError());
  }
}
