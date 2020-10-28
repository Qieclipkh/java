package com.cly.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @Author changleying
 * @Date 2020/3/23 14:18
 **/
public class SAXParserHandler extends DefaultHandler {
    String eName = null;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        eName = qName;
    }

    String value = null;

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        value = new String(ch, start, length);
        if (eName != null && !value.trim().equals("")) {
            System.out.println(eName+"节点值是：" + value);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        qName = null;
    }
}

