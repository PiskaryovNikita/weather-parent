package com.nixsolutions.parser.sax;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class SaxParser {
	
	public static void update(String file1, String file2) throws ParserConfigurationException, SAXException, IOException {
		StackHandler handler = new StackHandler();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser;
			parser = factory.newSAXParser();
			parser.parse(new File(file1), handler);
			try(FileWriter writer = new FileWriter(new File(file2))){
				writer.write(handler.getXml(), 0, handler.getXml().length());
				writer.flush();
			} 
	}
}
