/*
 * Copyright 2015 Adaptris Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package com.adaptris.core.services.splitter;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.adaptris.core.AdaptrisMessage;
import com.adaptris.core.AdaptrisMessageFactory;
import com.adaptris.core.CoreException;
import com.adaptris.core.stubs.MockMessageProducer;
import com.adaptris.core.util.DocumentBuilderFactoryBuilder;
import com.adaptris.util.KeyValuePair;

public class XpathDocumentCopierTest extends SplitterCase {

  static final String XPATH_DOCUMENT_COUNT = "count(/envelope/document)";

  private static Log logR = LogFactory.getLog(XpathDocumentCopierTest.class);
  private MockMessageProducer producer;
  private BasicMessageSplitterService service;

  public XpathDocumentCopierTest(java.lang.String testName) {
    super(testName);
  }

  @Override
  protected void setUp() throws Exception {
    producer = new MockMessageProducer();
    service = createBasic(new XpathDocumentCopier(XPATH_DOCUMENT_COUNT));
    service.setProducer(producer);
  }

  @Override
  protected void tearDown() throws Exception {
  }

  @Override
  protected String createBaseFileName(Object object) {
    return super.createBaseFileName(object) + "-XpathDocumentCopier";
  }

  @Override
  protected Object retrieveObjectForSampleConfig() {
    return null; // over-rides retrieveServices below instead
  }

  @Override
  protected List retrieveObjectsForSampleConfig() {
    return createExamples(new XpathDocumentCopier(XPATH_DOCUMENT_COUNT));
  }

  @Override
  protected String getExampleCommentHeader(Object o) {
    return super.getExampleCommentHeader(o) + "\n<!-- \n The example document for this split process is\n"
        + SplitterCase.XML_MESSAGE + "\n which would create 3 new messages each containing the entire XML document\n-->\n";
  }

  @Override
  protected XpathDocumentCopier createSplitterForTests() {
    return new XpathDocumentCopier();
  }

  public void testConstructors() throws Exception {
    XpathDocumentCopier splitter = new XpathDocumentCopier(XPATH_DOCUMENT_COUNT);
    assertEquals(XPATH_DOCUMENT_COUNT, splitter.getXpath());
    splitter = new XpathDocumentCopier();
    assertNull(splitter.getXpath());
  }

  public void testSetters() throws Exception {
    XpathDocumentCopier splitter = new XpathDocumentCopier();
    assertNull(splitter.getXpath());
    splitter.setXpath("FRED");
    assertEquals("FRED", splitter.getXpath());
    splitter.setXpath(null);
    assertNull(splitter.getXpath());
    splitter.setXpath("");
    assertEquals("", splitter.getXpath());
  }

  public void testSplit() throws Exception {
    AdaptrisMessage msg = AdaptrisMessageFactory.getDefaultInstance().newMessage(XML_MESSAGE);
    String obj = "ABCDEFG";
    msg.addObjectHeader(obj, obj);
    XpathDocumentCopier splitter = new XpathDocumentCopier(XPATH_DOCUMENT_COUNT);
    List<AdaptrisMessage> result = splitToList(splitter, msg);
    assertEquals(3, result.size());
    for (AdaptrisMessage m : result) {
      assertFalse("No Object Metadata", m.getObjectHeaders().containsKey(obj));
    }
  }

  public void testSplit_EmptyXPath() throws Exception {
    AdaptrisMessage msg = AdaptrisMessageFactory.getDefaultInstance().newMessage(XML_MESSAGE);
    String obj = "ABCDEFG";
    msg.addObjectHeader(obj, obj);
    XpathDocumentCopier splitter = new XpathDocumentCopier("XXXX");
    List<AdaptrisMessage> result = splitToList(splitter, msg);
    assertEquals(0, result.size());
  }

  public void testSplitWithObjectMetadata() throws Exception {
    AdaptrisMessage msg = AdaptrisMessageFactory.getDefaultInstance().newMessage(XML_MESSAGE);
    String obj = "ABCDEFG";
    msg.addObjectHeader(obj, obj);
    XpathDocumentCopier splitter = new XpathDocumentCopier(XPATH_DOCUMENT_COUNT);
    splitter.setCopyObjectMetadata(true);
    List<AdaptrisMessage> result = splitToList(splitter, msg);
    assertEquals(3, result.size());
    for (AdaptrisMessage m : result) {
      assertTrue("Object Metadata", m.getObjectHeaders().containsKey(obj));
      assertEquals(obj, m.getObjectHeaders().get(obj));
    }
  }

  public void testSplitThrowsException() throws Exception {
    AdaptrisMessage msg = AdaptrisMessageFactory.getDefaultInstance().newMessage(XML_MESSAGE);
    msg.setContent(XML_MESSAGE, msg.getContentEncoding());
    XpathDocumentCopier splitter = new XpathDocumentCopier("/document/envelope[");
    try {
      List<AdaptrisMessage> result = splitToList(splitter, msg);
      fail();
    }
    catch (CoreException expected) {

    }
  }

  public void testSplit_DocTypeNotAllowed() throws Exception {
    AdaptrisMessage msg = AdaptrisMessageFactory.getDefaultInstance().newMessage();
    msg.setContent(XML_WITH_DOCTYPE, msg.getContentEncoding());
    XpathDocumentCopier splitter = new XpathDocumentCopier(XPATH_DOCUMENT_COUNT);
    DocumentBuilderFactoryBuilder builder = new DocumentBuilderFactoryBuilder();
    builder.getFeatures().add(new KeyValuePair("http://apache.org/xml/features/disallow-doctype-decl", "true"));
    splitter.setXmlDocumentFactoryConfig(builder);
    try {
      List<AdaptrisMessage> result = splitToList(splitter, msg);
      fail();
    } catch (CoreException expected) {
      assertTrue(expected.getMessage().contains("DOCTYPE is disallowed"));
    }
  }

  public void testService() throws Exception {
    AdaptrisMessage msg = AdaptrisMessageFactory.getDefaultInstance().newMessage(XML_MESSAGE);
    msg.addMetadata("key", "value");
    execute(service, msg);
    assertEquals("Number of messages", 3, producer.getMessages().size());
  }

}
