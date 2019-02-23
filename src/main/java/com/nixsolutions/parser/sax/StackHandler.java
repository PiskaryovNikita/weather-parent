package com.nixsolutions.parser.sax;

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class StackHandler extends DefaultHandler {
	private Stack<String> stackStr = new Stack<>();
	private Stack<Integer> stackInt = new Stack<>();
	private StringBuilder xml = new StringBuilder();
	private boolean write;

	@Override
	public void startDocument() throws SAXException {
		stackInt.push(1);
		stackStr.push("start");
		xml.append("<?xml version = \"1.0\" encoding = \"UTF-8\"?>");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (!qName.equals(stackStr.peek()) && stackInt.peek() % 2 == 0) {
			write = false;
			return;
		}
		if (!stackStr.contains(qName)) {
			stackStr.push(qName);
			stackInt.push(1);
			appendTag(qName, attributes);
			write = true;
		} else if (stackStr.peek().equals(qName)) {
			stackInt.push(stackInt.pop() + 1);
			write = false;
			if (stackInt.peek() % 2 != 0) {
				appendTag(qName, attributes);
				write = true;
			}
		}
	}

	private void appendTag(String qName, Attributes attributes) {
		xml.append("<").append(qName);
		if (attributes != null) {
			for (int i = 0; i < attributes.getLength(); i++) {
				xml.append(" ").append(attributes.getQName(i)).append("=\"").append(attributes.getValue(i)).append('"');
			}
		}
		xml.append(">");
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (write) {
			xml.append(new String(ch, start, length).trim());
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (stackStr.peek().equals(qName) && stackInt.peek() % 2 != 0) {
			xml.append("</" + qName + ">");
		} else if (stackStr.contains(qName) && !stackStr.peek().equals(qName)) {
			stackInt.pop();
			stackStr.pop();
			if (stackInt.peek() % 2 != 0) {
				xml.append("</" + qName + ">");
			}
		}
	}

	public String getXml() {
		return xml.toString();
	}
}
