package com.cly.sax;

import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * @Author changleying
 * @Date 2020/3/23 14:16
 **/
public class SaxParse {
    public static void main(String[] args) {


    }

    public static void testSaxParse(InputStream is) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            final SAXParser saxParser = factory.newSAXParser();
            SAXParserHandler handler = new SAXParserHandler();
            saxParser.parse(is, handler);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testSaxParse(String uri) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            final SAXParser saxParser = factory.newSAXParser();
            SAXParserHandler handler = new SAXParserHandler();
            saxParser.parse("1.xml", handler);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
