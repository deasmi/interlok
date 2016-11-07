package com.adaptris.tester.report.junit;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

@XStreamAlias("error")
@XStreamConverter(value=ToAttributedValueConverter.class, strings={"text"})
public class JUnitReportError extends JUnitReportTestIssueTyped {

  @XStreamAsAttribute
  @XStreamAlias("type")
  private final static String TYPE = "error";
  private String text;

  public JUnitReportError(String message) {
    super(TYPE);
    setMessage(message);
  }

  public JUnitReportError(String message, String text) {
    this(message);
    this.text = text;
  }

  public String getText() {
    return text;
  }
}
