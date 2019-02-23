package com.nixsolutions.parser;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.nixsolutions.parser.dom.DomParser;
import com.nixsolutions.parser.sax.SaxParser;

public class DomSaxTest {
	@Before
	public void setUp() {
		XMLUnit.setIgnoreComments(true);
		XMLUnit.setIgnoreWhitespace(true);
	}

	@Test
	public void testDomParserSucces()
			throws ParserConfigurationException, IOException, SAXException, TransformerException {
		DomParser.update("src/test/resources/source.xml", "src/test/resources/actualDom.xml");
		SaxParser.update("src/test/resources/source.xml", "src/test/resources/actualSax.xml");
		DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document1 = documentBuilder.parse("src/test/resources/actualDom.xml");
		Document document2 = documentBuilder.parse("src/test/resources/actualSax.xml");
		Diff diff = new Diff(document1, document2);
		assertTrue("xml was parsed wrong", diff.similar());
	}
}
