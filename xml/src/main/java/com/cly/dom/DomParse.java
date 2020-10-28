package com.cly.dom;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @Author changleying
 * @Date 2020/3/23 14:28
 **/
public class DomParse {
    public static void main(String[] args) {
        testDomParse(new File("1.xml"));
    }

    public static void testDomParse(File xmlFile) {
        final String xmlStr;
        try {
            xmlStr = FileUtils.readFileToString(new File("1.xml"), "UTF-8");
            testDomParse(xmlStr);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void testDomParse(String xmlStr) {
        try {
            Document document = DocumentHelper.parseText(xmlStr);
            final Element el = document.getRootElement();
            final List<Element> elements = el.elements("data");
            final Object result = elements.get(0).elements("result").get(1);
            final Element element = ((Element) result).element("c_version");

            System.out.println(element.getText());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void iterateNodes(Element node, JSONObject json) { // 获取当前元素的名称
        String nodeName = node.getName(); // 判断已遍历的JSON中是否已经有了该元素的名称
        if (json.containsKey(nodeName)) { // 该元素在同级下有多个
            Object obj = json.get(nodeName);
            JSONArray array = null;
            if (obj instanceof JSONArray) {
                array = (JSONArray) obj;
            } else {
                array = new JSONArray();
                array.add(obj);
            }
            // 获取该元素下所有子元素
            List<Element> listElement = node.elements();
            if (listElement.isEmpty()) { // 该元素无子元素，获取元素的值
                String nodeValue = node.getTextTrim();
                array.add(nodeValue);
                json.put(nodeName, array);
                return;
            }
            // 有子元素
            JSONObject newJson = new JSONObject(); // 遍历所有子元素
            for (Element e : listElement) { // 递归
                iterateNodes(e, newJson);
            }
            array.add(newJson);
            json.put(nodeName, array);
            return;
        }
        // 该元素同级下第一次遍历
        // 获取该元素下所有子元素
        List<Element> listElement = node.elements();
        if (listElement.isEmpty()) { // 该元素无子元素，获取元素的值
            String nodeValue = node.getTextTrim();
            json.put(nodeName, nodeValue);
            return;
        } // 有子节点，新建一个JSONObject来存储该节点下子节点的值
        JSONObject object = new JSONObject(); // 遍历所有一级子节点
        for (Element e : listElement) { // 递归
            iterateNodes(e, object);
        }
        json.put(nodeName, object);
    }
}
