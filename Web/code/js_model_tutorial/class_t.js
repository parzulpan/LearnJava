// Class基本语法


console.log('--------------------------');
class User {

    constructor(name) {
        this.name = name;
    }

    sayHi() {
        console.log(this.name);
    }

}
let user = new User("PPP");
user.sayHi();
// 注意，类的方法之间没有逗号
// 类的实质是什么？类是一种函数！
console.log(typeof  User);  // function
// 更确切地说，是构造器方法
console.log(User === User.prototype.constructor); // true
// 方法在 User.prototype中
console.log(User.prototype.sayHi);  // [Function: sayHi]
// 在原型中实际上由两个方法
console.log(Object.getOwnPropertyNames(User.prototype));    // [ 'constructor', 'sayHi' ]


// Class不仅仅是语法糖
// 虽然实际上可以在没有 class 的情况下用函数声明相同的内容，但是它们之间其实存在巨大差异
// 1. 通过class创建地函数具有特殊地内部属性标记 [[FunctionKind]]:"classConstructor" ，不像普通函数，调用类构造器必须要用new关键字
// 2. 类方法不可枚举，类定义将"prototype"中的所有方法的enumerable标志设置为false
// 3. 类总是使用ues strict，在类构造中的所有代码都将自动进入严格模式


// 类表达式
// 就像函数一样，类可以在另一个表达式被定义，被传递、被返回、被赋值等
console.log('--------------------------');
let UserF = class {
    sayHiF() {
        console.log("HelloF");
    }
};
// 类似于命名函数表达式，类表达式可能也应该有一个名字，如果类表达式有名字，那么该名字仅在类内部可见
// “命名类表达式（Named Class Expression）”
// (规范中没有这样的术语，但是它和命名函数表达式类似)
let UserN = class MyClass {
    sayHi() {
        console.log(MyClass); // MyClass 这个名字仅在类内部可见
    }
};
new UserN().sayHi(); // 正常运行，显示 MyClass 中定义的内容
// console.log(MyClass); // error，MyClass 在外部不可见


// getters/setters及其他速记
// 就像对象字面量，类可能包括getters/setters，计算属性等
console.log('--------------------------');
class UserGS {

    constructor(name) {
        // 调用
        this._name = name;
    }

    get name() {
        return this._name;
    }

    set name(value) {
        if (value.length < 4) {
            console.log( "Name is too short.");
            return;
        }
        this._name = value;
    }
}
let usergs = new UserGS("PXPPP");
console.log(usergs.name);
let usergs1 = new User("set");
console.log(usergs1.name);


// 类字段
// 之前，类仅有方法，"类字段"是一种允许添加任何属性的语法，它是最近才添加到语言中的
console.log('--------------------------');
class UserC {
    name = "YUs";

    sayHi() {
        console.log( `Hello, ${this.name}！` );
    }
}
new UserC().sayHi()
console.log(UserC.prototype.sayHi); // [Function: sayHi]，被放在User.prototype中
console.log(UserC.prototype.name); // undefined，undefined，没有被放在User.prototype中
// 关于类字段重要的是，它们设置在单个对象上的，而不是设置在UserC.prototype上的，即它们是在constructor完成工作后被处理的


// 使用类字段制作绑定方法
// 正如函数绑定那样，JS的函数具有动态的this，它取决于调用上下文，
// 因此，如果一个对象方法传递到某处，或者在另一个上下文被调用，则this将不再是对其对象的引用
console.log('--------------------------');
class Button {
    constructor(value) {
        this.value = value;
        // 2. 将方法绑定到对象
        // this.click = this.click.bind(this);
    }

    // click() {
    //     console.log(this.value);
    // }
    click = () => {
        console.log(this.value);
    }

}
let button = new Button("Hello Button");
button.click();
setTimeout(button.click, 1000); // undefined
// 已知的解决方式：
// 1. 传递一个包装函数，例如setTimeout(() => button.click(), 1000)
// 2. 将方法绑定到对象
// 3. 类字段优雅的语法：click = () => {...}  在每个Button对象上创建一个独立的函数，并将this绑到该对象上，
//    然后就可以将button.click传递到任何地方，并且它会被以正确的this进行调用


// 重写为class
// Clock类是以函数编写的，用class重写它
console.log('--------------------------');
class Clock {
    constructor( {template} ) {
        this.template = template;
    }

    render() {
        let date = new Date();

        let hours = date.getHours();
        if ( hours < 10 ) hours = '0' + hours;

        let minutes = date.getMinutes();
        if ( minutes < 10 ) minutes = '0' + minutes

        let seconds = date.getSeconds();
        if ( seconds < 10 ) seconds = '0' + seconds;

        let output = this.template
            .replace('h', hours)
            .replace('m', minutes)
            .replace('s', seconds);

        console.log(output);
    }

    stop() {
        clearInterval(this.timer);
    }

    start() {
        this.render();
        this.timer = setInterval(() => this.render(), 1000);
    }


}
let clock = new Clock({template: "h:m:s"});
clock.start();

