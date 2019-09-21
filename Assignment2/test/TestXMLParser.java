import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Test case for testing XMLValidator and XMLInfoLogger.
 */
public class TestXMLParser {

  private XMLValidator validator;
  private XMLInfoLogger logger;

  private static final String STATUS_EMPTY = "Status:Empty";
  private static final String STATUS_INCOMPLETE = "Status:Incomplete";
  private static final String STATUS_VALID = "Status:Valid";

  /**
   * Initialize parser before using.
   */
  @Before
  public void prepareBefore() {
    validator = new XMLValidator();
    assertEquals(STATUS_EMPTY, validator.output());
    logger = new XMLInfoLogger();
    assertEquals("", logger.output());
  }

  /**
   * Test case for testing XMLInfoLogger.
   */
  @Test
  public void commonTestCasesForLogger() {
    String a = "<html> This is a body</html>";
    assertTrue(processXml(logger, a));

    String result = "Started:html\n"
            + "Characters: This is a body\n"
            + "Ended:html\n";
    assertEquals(result, logger.output());

    a = "<html> This is \n a body <";
    logger.reset();
    assertTrue(processXml(logger, a));
    result = "Started:html\n";
    assertEquals(result, logger.output());

    a = "<html> This is    a body</html>";
    logger.reset();
    assertTrue(processXml(logger, a));
    result = "Started:html\n"
            + "Characters: This is    a body\n"
            + "Ended:html\n";
    assertEquals(result, logger.output());

    a = "<html>_<head> This is a heading</head><p>Paragraph</p></html>";
    logger.reset();
    assertTrue(processXml(logger, a));
    result = "Started:html\n"
            + "Characters:_\n"
            + "Started:head\n"
            + "Characters: This is a heading\n"
            + "Ended:head\n"
            + "Started:p\n"
            + "Characters:Paragraph\n"
            + "Ended:p\n"
            + "Ended:html\n";
    assertEquals(result, logger.output());
  }

  /**
   * Test cases to test XMLValidator.
   */
  @Test
  public void commonTestCasesForValidator() {
    String a = "<h>a<h>";
    assertTrue(processXml(validator, a));
    assertEquals(STATUS_INCOMPLETE, validator.output());

    a = "<h>a</h>";
    validator.reset();
    assertTrue(processXml(validator, a));
    assertEquals(STATUS_VALID, validator.output());

    a = "</a>";
    validator.reset();
    assertFalse(processXml(validator, a));
    assertEquals(STATUS_INCOMPLETE, validator.output());

    a = "<a><b>h</b><c>h</c></a>";
    validator.reset();
    assertTrue(processXml(validator, a));
    assertEquals(STATUS_VALID, validator.output());

    a = "<a>"
            + "<b>h</b>"
            + "<c>h</c>"
            + "hhh"
            + "</a>";
    validator.reset();
    assertTrue(processXml(validator, a));
    assertEquals(STATUS_VALID, validator.output());

    a = "<a>h</a><a>h</a>";
    validator.reset();
    assertFalse(processXml(validator, a));
    assertEquals(STATUS_INCOMPLETE, validator.output());

    a = "<html> This is an HTML page</html>";
    validator.reset();
    assertTrue(processXml(validator, a));
    assertEquals(STATUS_VALID, validator.output());

    a = "<root> <leaf> Leaf </leaf></root>";
    validator.reset();
    assertTrue(processXml(validator, a));
    assertEquals(STATUS_VALID, validator.output());

    a = "<root>\n"
            + " Some data\n"
            + " <child> Some child data </child>\n"
            + "</root>";
    validator.reset();
    assertTrue(processXml(validator, a));
    assertEquals(STATUS_VALID, validator.output());

    a = "<html>\n"
            + "</html>";
    validator.reset();
    assertTrue(processXml(validator, a));
    assertEquals(STATUS_VALID, validator.output());

    a = "<root>\n"
            + " <leaf>This is a leaf </leaf>\n"
            + " <leaf>This is another leaf </leaf>\n"
            + "</root>";
    validator.reset();
    assertTrue(processXml(validator, a));
    assertEquals(STATUS_VALID, validator.output());

    a = "<a/a>!#!@</a/a>";
    validator.reset();
    assertFalse(processXml(validator, a));
    assertEquals(STATUS_INCOMPLETE, validator.output());

    a = "<html>\n"
            + " <body0> ... </body0>\n"
            + " <_xml> ...</_xml>\n"
            + "</html>";
    validator.reset();
    assertTrue(processXml(validator, a));
    assertEquals(STATUS_VALID, validator.output());

    a = "<ht<ml>\n"
            + " <0body> ... </0body>\n"
            + " <-xml> ...</-xml>\n"
            + "</html>";
    validator.reset();
    assertFalse(processXml(validator, a));
    assertEquals(STATUS_INCOMPLETE, validator.output());

    a = "<html>\n"
            + " <body>This is a body </body>\n"
            + "</html>\n"
            + "<html>\n"
            + " <body>This is another body </body>\n"
            + "</html>";
    validator.reset();
    assertFalse(processXml(validator, a));
    assertEquals(STATUS_INCOMPLETE, validator.output());

    a = "<ht<ml></html>";
    validator.reset();
    assertFalse(processXml(validator, a));
    assertEquals(STATUS_INCOMPLETE, validator.output());

    a = "   <a>h</a>   ";
    validator.reset();
    assertTrue(processXml(validator, a));
    assertEquals(STATUS_VALID, validator.output());
  }

  /**
   * Special function to test single case when debugging.
   */
  @Test
  public void bugFixTest() {
    String a = "<htm>l><head></head><div><p></p><p></p></div></html>";
    a = " <htm>l><head></head><div><p></p><p></p></div></html>";
    a = "<html><head></hea>d><div><p></p><p></p></div></html>";
    logger.reset();
    validator.reset();
    assertFalse(processXml(logger, a));
    assertFalse(processXml(validator, a));
    System.out.println(logger.output());
  }

  private boolean processXml(AbstractXMLParser parser, String xml) {
    try {
      for (char c : xml.toCharArray()) {
        parser.input(c);
      }
      return true;
    } catch (InvalidXMLException e) {
      System.out.println(e);
      return false;
    }
  }
}
