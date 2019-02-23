package com.nixsolutions.parser.dom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomParser {

	private static void processXml(Node node) {
		NodeList childs = node.getChildNodes();
		for (int i = 0, n = 0; i < childs.getLength(); i++) {
			if (childs.item(i).getNodeType() == Node.ELEMENT_NODE) {
				if ((n + 1) % 2 == 0) {
					node.removeChild(childs.item(i));
				} else {
					processXml(childs.item(i));
				}
				// increment in case of element
				n++;
			}
		}
	}

	private static void save(Document document, File dest)
			throws TransformerFactoryConfigurationError, FileNotFoundException, TransformerException {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(dest)));
	}

	public static void update(String file1, String file2) throws ParserConfigurationException, SAXException, IOException,
			TransformerFactoryConfigurationError, TransformerException {
		DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = dBuilder.parse(new File(file1));
		processXml(document.getDocumentElement());
		save(document, new File(file2));
	}
}
