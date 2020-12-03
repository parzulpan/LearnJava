function User(name) {
    // 1. this = {}; (隐式创建)

    // 2. 添加属性到this
    this.name = name;
    this.isAdmin = false;
    this.sayHello = function() {
        alert("My name is " + this.name);
    }

    // 3. return this; (隐式返回)
}
let user = new User("POC");
alert(user.name);
alert(user.isAdmin);
user.sayHello();
// 当一个函数被使用new操作符执行时，它按照以下步骤：
// 1. 一个新的空对象被创建并被分配给this
// 2. 函数体执行。通常它会修改this，为其添加新的属性。
// 3. 返回this的值


function Calculator() {

    this.read = function() {
        this.a = +prompt('a?', 0);
        this.b = +prompt('b?', 0);
    };

    this.sum = function() {
        return this.a + this.b;
    };

    this.mul = function() {
        return this.a * this.b;
    };
}
let calculator = new Calculator();
calculator.read();
alert( "Sum=" + calculator.sum() );
alert( "Mul=" + calculator.mul() );