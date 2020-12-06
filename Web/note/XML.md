# XML 文件

## 简介

XML 是可拓展的标记性语言。

XML 的主要作用：

* 用来保存数据，且这些数据具有自我描述性；
* **作为项目或模块的配置文件**；
* 作为网络数据传输的格式，但是现在以 JSON 格式为主。

## 语法

一般包括五个部分：

* 文档声明；
* 元素（标签）；
* XML 属性；
* XML 注释；
* 文本区域（CDATA区）。

**文档声明**：

* `<?xml version="1.0" encoding="UTF-8" standalone="yes"?>`
* `version` 版本号
* `encoding` XML 的文件编码
* `standalone="yes/no"` 表示这个 XML 文件是否是独立的 XML 文件

XML 属性：

* XML 的标签属性和 HTML 的标签属性是非常类似的，属性可以提供元素的额外信息
* 一个标签上可以书写多个属性。每个属性的值必须使用**引号**引起来

**语法规则**：

* 所有 XML 元素都须有关闭标签，即闭合；
* XML 标签对大小写敏感；
* XML 必须正确的嵌套；
* XML 文档必须有根元素；
* XML 的属性值必须加引号；
* XML 的特殊字符，例如 `>` `&gt` `<` `&lt`；

**文本区域（CDATA区）**：

* CDATA 语法可以告诉 XML 解析器，CDATA 里的文本内容，只是纯文本，不需要 XML 语法解析；
* 格式：`<![CDATA[ 这里可以把你输入的字符原样显示，不会解析 XML ]]>`

## XML 解析

因为 XML 可扩展的标记语言，不管是 HTML 文件还是 XML 文件它们都是标记型文档，都可以使用 w3c 组织制定的 DOM 技术来解析。

现在比较常用的解析技术是 **dom4j**，这是一个第三方解析技术。

## dom4j

dom4j 是一个用于处理XML的开源框架，该框架与 XPath 集成在一起，并完全支持 DOM，SAX，JAXP 和 Java 平台。

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<books>
    <book id="SN124156869438690">
        <name>Java 编程思想</name>
        <author>XPS</author>
        <price>30.9</price>
    </book>
    <book id="SN152353246262166">
        <name>Effective Java</name>
        <author>GKO</author>
        <price>20.9</price>
    </book>

</books>
```

```java
package cn.parzulpan.java;

/**
 * @Author : parzulpan
 * @Time : 2020-12-06
 * @Desc :
 */

public class Book {
    private String id;
    private String name;
    private String author;
    private Double price;

    public Book() {
    }

    public Book(String id, String name, String author, Double price) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                '}';
    }
}
```

```java
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
```

## 练习和总结
