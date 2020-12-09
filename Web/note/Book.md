# 书城网站

[项目地址](https://github.com/parzulpan/book-demo)

## 登录、注册的验证

* 使用 jQuery 技术对登录中的用户名、密码进行非空验证；

* 使用 jQuery 技术和正则表达式对注册中的用户名、密码、确认密码、邮箱进行格式验证，对验证码进行非空验证；

```html
<script type="text/javascript" src="static/script/jquery.js"></script>
<script type="text/javascript">
    $(function() {
        $("#sub_btn").click(function () {
            // 验证用户名：必须由字母，数字下划线组成，并且长度为 5 到 12 位
            // 1. 获取用户名输入框的内容
            let username = $("#username").val();
            // 2. 创建正则表达式对象
            let usernamePattern = /^\w{5,12}$/;
            // 3. 使用 test 方法验证
            if (!usernamePattern.test(username)) {
                // 4. 提示用户
                $("span.errorMsg").text("用户名格式错误！");

                return false;
            }

            // 验证密码：必须由字母，数字下划线组成，并且长度为 5 到 12 位
            let password = $("#password").val();
            let passwordPattern = /^\w{5,12}$/;
            if (!passwordPattern.test(password)) {
                $("span.errorMsg").text("密码格式错误！");

                return false;
            }

            // 验证确认密码：和密码相同
            let repwd = $("#repwd").val();
            if (repwd !== password) {
                $("span.errorMsg").text("密码不一致！");

                return false;
            }

            // 邮箱验证：xxxxx@xxx.com
            let email = $("#email").val();
            let emailPattern = /^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/;
            if (!emailPattern.test(email)) {
                $("span.errorMsg").text("邮箱格式错误！");

                return false;
            }
            // 验证码：现在只需要验证用户已输入。因为还没讲到服务器。验证码生成。
            let code = $("#code").val();
            let trimCode = $.trim(code);
            if (trimCode == null || trimCode === "") {
                $("span.errorMsg").text("验证码不能为空！");

                return false;
            }

            // 验证成功，去掉提示信息
            $("span.errorMsg").text("");
        })
    })
</script>

```

## 实现登录、注册

### 软件的三层架构

* **UIL**（User Interface Layer 表现层）：主要是指用户交互的界面，用于接收用户输入的数据和显示处理后用户需要的数据；
* **BLL**（Business Logic Layer 业务逻辑层）：表现层和数据访问层之间的桥梁，实现业务逻辑，业务逻辑包括验证、计算、业务规划等；
* DAL（Date Access Layer 数据访问层）：主要实现对数据的增、删、改、查。将存储在数据库中的数据提交给业务层，同时将业务层处理的数据保存到数据库。

![软件的三层架构](../img/软件的三层架构.png)

**三层架构的优点**：

* 结构清晰，耦合度低；
* 可维护性高，可扩展性高；
* 利于开发任务同步进行；
* 容易适应需求变化。

**三层架构的确定**：

* 降低了系统的性能，如果不采用分层式结构，很多业务可以直接造访数据库，以此获取相应的数据，如今却必须通过中间层来完成；
* 增加了代码量，增加了工作量；

### 书城的三层架构

* 表现层
  * HTML、Servlet
  * 接受用户的请求，调用业务逻辑层处理用户请求，显示处理结果
* 业务逻辑层
  * Service
  * 调用数据访问层处理业务逻辑
  * 采用面向接口编程的思想，先定义接口，再创建实现类
* 数据访问层
  * DAO
  * 用来操作数据库，对数据库进行增删改查
  * 采用面向接口编程的思想，先定义接口，再创建实现类

![项目架构](../img/项目架构.png)

**用户注册**：

* 访问注册页面
* 填写注册信息，提交给服务器
* 服务器应该保存用户
* 当用户已经存在，提示用户注册失败，用户名已存在
* 当用户不存在，注册成功

**用户登录**：

* 访问登陆页面
* 填写用户名密码后提交
* 服务器判断用户是否存在
* 如果登陆失败，返回用户名或者密码错误信息
* 如果登录成功，返回登陆成功信息

## 动态化及局部优化

### 页面 jsp 动态化

### 抽取页面中相同内容

### 登录注册错误提示及表单回显

### BaseServlet 抽取

### 数据的封装和抽取 BeanUtils 的使用

### 使用 EL 表达式修改表单回显

## 图书的增删改查

## 登录、登出、验证码、购物车

## 结账及添加事务
