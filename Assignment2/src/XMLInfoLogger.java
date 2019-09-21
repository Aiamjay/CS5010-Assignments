import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * XML parser for parsing xml content, and output some log.
 */
public class XMLInfoLogger extends AbstractXMLParser {

  private Stack<String> tagStack;
  private String curContent = "";
  private List<String> content;
  private String curTagName = "";
  private OutputStatus status = OutputStatus.STATUS_EMPTY;

  /**
   * Constructor for XMLInfoLogger.
   */
  public XMLInfoLogger() {
    content = new ArrayList<>();
    tagStack = new Stack<>();
  }

  @Override
  public void reset() {
    tagStack.clear();
    content.clear();
    curTagName = "";
    status = OutputStatus.STATUS_EMPTY;
  }

  @Override
  protected void processTag(char c) throws InvalidXMLException {
    checkValidTagChar(curTagName, c);

    if (checkEndOfTag(c)) {
      if (!tagStack.isEmpty()) {
        if (!curContent.isEmpty()) {
          content.add(getContentStr(curContent));
        }
      }
      if (checkEncloseOfTag(curTagName.charAt(0))) {
        if (curTagName.length() - 1 != tagStack.peek().length()) {
          throwException(ErrorMsg.TAG_PAIR_NAME_NOT_MATCH);
        }
        content.add(getEndStr(tagStack.pop()));
      } else {
        tagStack.push(curTagName);
        content.add(getStartStr(curTagName));
      }
      curContent = "";
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
    } else {
      curContent += c;
    }
  }

  @Override
  public String output() {
    String ans = String.join("\n", content);
    if (ans.isEmpty()) {
      return "";
    }
    return ans + "\n";
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
