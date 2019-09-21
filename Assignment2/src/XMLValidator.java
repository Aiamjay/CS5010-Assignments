import java.util.Stack;

/**
 * XML parser for checking whether it is a valid xml string.
 */
public class XMLValidator extends AbstractXMLParser {
  private Stack<String> tagStack;
  private String curTagName = "";
  private OutputStatus status = OutputStatus.STATUS_EMPTY;

  /**
   * Constructor for XMLValidator.
   */
  public XMLValidator() {
    tagStack = new Stack<>();
  }

  @Override
  public void reset() {
    tagStack.clear();
    curTagName = "";
    status = OutputStatus.STATUS_EMPTY;
  }


  @Override
  protected void processTag(char c) throws InvalidXMLException {
    checkValidTagChar(curTagName, c);
    if (checkEndOfTag(c)) {
      if (checkEncloseOfTag(curTagName.charAt(0))) {
        if (curTagName.length() - 1 != tagStack.peek().length()) {
          throwException(ErrorMsg.TAG_PAIR_NAME_NOT_MATCH);
        }
        tagStack.pop();
      } else {
        tagStack.push(curTagName);
      }
      curTagName = "";
      if (tagStack.isEmpty()) {
        status = OutputStatus.STATUS_VALID;
      } else {
        status = OutputStatus.STATUS_READ_CONTENT;
      }
      return;
    }
    curTagName += c;
    checkNameOfTagPair(tagStack.isEmpty() ? "" : tagStack.peek(), curTagName);
  }

  @Override
  protected void processContent(char c) throws InvalidXMLException {
    if (checkStartOfTag(c)) {
      status = OutputStatus.STATUS_READ_TAG;
    }
  }

  @Override
  public String output() {
    return status.getStatus();
  }

  @Override
  public OutputStatus getStatus() {
    return status;
  }

  @Override
  public void setStatus(OutputStatus status) {
    this.status = status;
  }
}
