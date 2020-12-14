# JSON 文件

## 什么是 JSON

JSON（JavaScript Object Notation），即 JS 对象符号。 是一种轻量级（相对于 XML 来说）的数据交换格式，易于阅读和编写，同时也易于机器解析和生成。JSON 采用完全独立于语言的文本格式，而且很多语言都提供了对 JSON 的支持，也有很多优秀的第三方库，这使得 JSON 成为理想的数据交换格式。其中数据交换指的是客户端和服务器之间的业务数据的传递格式。

## JSON 在 JS 中的使用

**JSON 的定义**：

* 由键值对组成，并且由花括号包围
* 每个键由引号引起来
* 键和值之间使用冒号进行分隔，多组键值对之间进行逗号进行分隔

```javascript
var jsonObj = {
    "key1": 12,
    "key2": "abc",
    "key3": true,
    "key4": [11, "arr", false],
    "key5": {
        "key5_1": 551,
        "key5_2": "key5_2_value"
    },
    "key6":[{
        "key6_1_1": 6611,
        "key6_1_2": "key6_1_2_value"
        }, {
        "key6_2_1": 6621,
        "key6_2_2": "key6_2_2_value"
    }]
};
```

**JSON 的访问**：

* 它本身是一个对象，其中的 key 可以理解为是对象中的一个属性
* key 访问就跟访问对象的属性一样： `JSON 对象.key`

```javascript
alert(typeof(jsonObj));// object, 说明 json 就是一个对象
alert(jsonObj.key1); // 12
alert(jsonObj.key2); // abc
alert(jsonObj.key3); // true
alert(jsonObj.key4);// 得到数组[11,"arr",false]

// json 中 数组值的遍历
for(var i = 0; i < jsonObj.key4.length; i++) {
    alert(jsonObj.key4[i]);
}

alert(jsonObj.key5.key5_1);//551
alert(jsonObj.key5.key5_2);//key5_2_value
alert(jsonObj.key6 );// 得到 json 数组

// 取出来每一个元素都是 json 对象
var jsonItem = jsonObj.key6[0];
// alert( jsonItem.key6_1_1 ); //6611
alert( jsonItem.key6_1_2 ); //key6_1_2_value
```

**JSON 的常用方法**：

* JSON 的存在形式：
  * 对象的形式存在，称作 JSON 对象，**操作 JSON 中的数据时需要这种形式**
  * 字符串的形式存在，称作 JSON 字符串，**在客户端和服务器之间进行数据交换时需要这种形式**
* JSON.stringfy() 把 JSON 对象 转换为 JSON 字符串
* JSON.parse() 把 JSON 字符串 转换为 JSON 对象

```javascript
// 把 json 对象转换成为 json 字符串
var jsonObjString = JSON.stringify(jsonObj); // 特别像 Java 中对象的 toString
alert(jsonObjString)

// 把 json 字符串转换成为 json 对象
var jsonObj2 = JSON.parse(jsonObjString);
alert(jsonObj2.key1);// 12
alert(jsonObj2.key2);// abc
```

## JSON 在 Java 中的使用

这里以 gson 为例。

### JavaBean 和 JSON 的互转

```java
package cn.parzulpan.test;

import cn.parzulpan.bean.User;
import com.google.gson.Gson;

/**
 * @Author : parzulpan
 * @Time : 2020-12-14
 * @Desc : JavaBean 和 JSON 的互转
 */

public class JavaBeanAndJSON {
    public static void main(String[] args) {
        // 创建 JavaBean 对象
        User user = new User(1, "parzulpan", "123456", "parzulpan@321.com");
        // 创建 Gson 对象
        Gson gson = new Gson();

        // toJson 方法可以把 java 对象转换成为 json 字符串
        String userJsonString = gson.toJson(user);
        System.out.println(userJsonString);

        // fromJson 把 json 字符串转换回 Java 对象
        // 第一个参数是 json 字符串
        // 第二个参数是转换回去的 Java 对象类型，对于 JavaBean 可以直接 .class
        User user1 = gson.fromJson(userJsonString, User.class);
        System.out.println(user1);
    }
}
```

### List 和 JSON 的互转

```java
package cn.parzulpan.test;

import cn.parzulpan.bean.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * @Author : parzulpan
 * @Time : 2020-12-14
 * @Desc : List 和 JSON 的互转
 */

public class ListAndJSON {
    public static void main(String[] args) {
        ArrayList<User> userList = new ArrayList<>();
        userList.add(new User(1, "parzulpan", "123456", "parzulpan@321.com"));
        userList.add(new User(null, "tom", "214531", "tom@321.com"));

        Gson gson = new Gson();

        // 把 List 转换为 JSON 字符串
        String userListJsonString = gson.toJson(userList);
        System.out.println(userListJsonString);

        // 把 JSON 字符串 转换为 List，对于集合，第二个参数需要 Type typeOfT
        // 为了确保范型，需要实现一个类继承 TypeToken
        System.out.println(new UserListType().getType());   // class java.util.ArrayList
        System.out.println(new UserListType().getRawType());    // class cn.parzulpan.test.UserListType
        ArrayList<User> list = gson.fromJson(userListJsonString, new UserListType().getType());
        System.out.println(list);
        User user = list.get(0);
        System.out.println(user);
    }
}

class UserListType extends TypeToken<ArrayList<User>> {

}
```

### Map 和 JSON 的互转

```java
package cn.parzulpan.test;

import cn.parzulpan.bean.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author : parzulpan
 * @Time : 2020-12-14
 * @Desc : Map 和 JSON 的互转
 */

public class MapAndJSON {
    public static void main(String[] args) {
        Map<Integer, User> userMap = new LinkedHashMap<>();

        userMap.put(1, new User(1, "parzulpan", "123456", "parzulpan@321.com"));
        userMap.put(2, new User(null, "tom", "214531", "tom@321.com"));

        Gson gson = new Gson();

        String userMapJsonString = gson.toJson(userMap);
        System.out.println(userMapJsonString);

//        Map<Integer, User> userMap1 = gson.fromJson(userMapJsonString, new UserMapType().getType());
        // 或者用匿名类，推荐这种做法
        Map<Integer, User> userMap1 = gson.fromJson(userMapJsonString, new TypeToken<LinkedHashMap<Integer, User>>() {
        }.getType());

        System.out.println(userMap1);
        User user = userMap1.get(1);
        System.out.println(user);
    }
}

class UserMapType extends TypeToken<LinkedHashMap<Integer, User>> {

}
```

## 开源类库

### gson

Gson 是目前功能最全的 Json 解析神器，Gson 当初是为 Google 公司内部需求而由 Google 自行研发而来，但自从在 2008 年五月公开发布第一版后已被许多公司或用户应用。

Gson 的应用主要为 toJson 与 fromJson 两个转换函数，无依赖，不需要例外额外的 jar，能够直接跑在 JDK 上。

在使用这种对象转换之前，需先创建好对象的类型以及其成员才能成功的将 JSON 字符串成功转换成相对应的对象。类里面只要有 get 和 set 方法，Gson 完全可以实现复杂类型的 json 和 bean 的相互转换转换。

```java
public class GsonUtil {
    private static Gson gson = new GsonBuilder().create();

    public static String bean2Json(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
        return gson.fromJson(jsonStr, objClass);
    }

    public static String jsonFormatter(String uglyJsonStr) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(uglyJsonStr);
        return gson.toJson(je);
    }
}
```

### fastjson

Fastjson 是一个 Java 语言编写的高性能的 JSON 处理器,由阿里巴巴公司开发。无依赖，不需要例外额外的 jar，能够直接跑在JDK上。

FastJson 在复杂类型的 Bean 转换 Json 上会出现一些问题，可能会出现引用的类型，导致 Json 转换出错，需要制定引用。FastJson 采用独创的算法，将 parse 的速度提升到极致，超过所有 json 库。

```java
public class FastJsonUtil {
    public static String bean2Json(Object obj) {
        return JSON.toJSONString(obj);
    }

    public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
        return JSON.parseObject(jsonStr, objClass);
    }
}
```

### jackson

Jackson 社区相对比较活跃，更新速度也比较快，从 Github 中的统计来看，Jackson 是最流行的 json 解析器之一，Spring MVC 的默认 json 解析器便是 Jackson。

Jackson 的核心模块由三部分组成：

* **jackson-core 核心包**，提供基于”流模式”解析的相关 API，它包括 JsonPaser 和 JsonGenerator。Jackson 内部实现正是通过高性能的流模式 API 的 JsonGenerator 和 JsonParser 来生成和解析 json
* **jackson-annotations 注解包**，提供标准注解功能
* **jackson-databind 数据绑定包**，提供基于”对象绑定” 解析的相关 API（ ObjectMapper ）和”树模型” 解析的相关 API（JsonNode）；基于”对象绑定” 解析的 API 和”树模型”解析的 API 依赖基于”流模式”解析的 API

```java
public class JacksonUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    public static String bean2Json(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
        try {
            return mapper.readValue(jsonStr, objClass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
```

## 练习和总结
