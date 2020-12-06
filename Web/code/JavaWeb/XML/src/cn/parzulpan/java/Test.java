package cn.parzulpan.java;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : parzulpan
 * @Time : 2020-12-06
 * @Desc : dom4j 测试
 */

public class Test {
    public static void main(String[] args) {
        Test test = new Test();
        List<Book> books = test.readXML();
        books.forEach(System.out::println);
    }

    // 获取Document 对象
    public Document getDocument() {
        // SaxReader 对象用于读取 xml 文件
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read("XML/xml/books.xml");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        System.out.println(document);
        return document;
    }

    // 读取文件生成 Book 类
    public List<Book> readXML() {
        ArrayList<Book> list = new ArrayList<>();

        Document document = getDocument();
        // 通过 Document 对象，拿到 XML 的根元素对象
        Element rootElement = document.getRootElement();
        // 通过根元素对象，获取所有的 book 标签对象
        List<Element> books = rootElement.elements("book");
        for (Element book : books) {
            // 它将当前元素转换成为 String 对象
            System.out.println(book.asXML());

            // 拿到 book 的属性值
            String id = book.attributeValue("id");

            // 拿到 book 下面的 name 元素对象
            Element name = book.element("name");
            Element author = book.element("author");
            Element price = book.element("price");

            // getText() 方法拿到起始标签和结束标签之间的文本内容
            list.add(new Book(id, name.getText(), author.getText(), Double.parseDouble(price.getText())));
        }

        return list;
    }
}
