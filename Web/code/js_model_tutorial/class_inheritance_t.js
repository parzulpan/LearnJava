// 类继承

// 类继承是一个类扩展成另一个类的一种方式


// "extends"关键字
console.log('--------------------------');
class Animal {
    constructor(name) {
        this.speed = 0;
        this.name = name;
    }

    run(speed) {
        this.speed = speed;
        console.log(`${this.name} runs with speed ${this.speed}.`);
    }

    stop() {
        this.speed = 0;
        console.log(`${this.name} stands still.`);
    }
}
let animal = new Animal("My animal");
animal.run(10);
animal.stop();

// 让Rabbit继承Animal
console.log('--------------------------');
class Rabbit extends Animal {
    hide() {
        console.log(`${this.name} hide!`);
    }
}
let rabbit = new Rabbit("White Rabbit");
rabbit.run(5);
rabbit.hide();
// 在内部，关键字extends使用了很好的旧的原型机制进行工作，他将Rabbit.prototype.[[Prototype]]设置为Animal.prototype，
// 所以，如果在Rabbit.prototype中找不到一个方法，JS就会从Animal.prototype中获取该方法
// 要查找rabbit.run方法，JS引擎会进行如下检查
// 1. 查找对象rabbit(没有run)
// 2. 查找它的原型，即Rabbit.prototype(有hide，但没有run)
// 3. 查找它的原型，即Animal.prototype，在这里找到了run


// 重写方法
// 通常来说，不希望完全替换父类的方法，而是希望在父类的基础上进行调整或扩展其功能
// Class为此提供了"super"关键字
// 1. 执行super.method(...)来调用一个父类方法
// 2. 执行super(...)来调用一个父类constructor
console.log('--------------------------');
class Rabbit1 extends Animal {
    hide() {
        console.log(`${this.name} hide!`);
    }

    // rabbit 在停下来的时候自动 hide
    stop() {
        super.stop();
        this.hide();
    }
}
let rabbit1 = new Rabbit1("White Rabbit 1");
rabbit1.run(5);
rabbit1.stop();


// 重写constructor
// 根据规范，如果一个类扩展了另一个类并且没有constructor，那么将生成"空"的constructor
console.log('--------------------------');
class Rabbit2 extends Animal {
    // 为没有自己的 constructor 的扩展类生成的
    constructor(...args) {
        super(...args);
    }
}

class Rabbit3 extends Animal {

    constructor(name, earLength) {
        super();
        this.speed = 0;
        this.name = name;
        this.earLength = earLength;
    }

    // ...
}

// 不工作！必须继承类的 constructor 必须调用 super(...)，并且一定要在使用 this 之前调用
// let rabbit3 = new Rabbit3("White Rabbit", 10); // Error: this is not defined.
let rabbit3 = new Rabbit3("White Rabbit 3", 10);
rabbit3.run(5);
// 不工作的原因：
// 在JS中，继承类(所谓的"派生构造器")的构造函数和其他函数之间是由区别的，
// 派生构造器具有特殊的内部属性 [[ConstructorKind]]:"derived"，这是一个特殊的内部标签
// 1. 当通过new执行一个常规函数时，它将创建一个空对象，并将这个空对象赋值给this
// 2. 当继承的constructor执行时，它不会执行此操作，它期望父类的constructor来完成这项工作


// 深入：内部探究和[[HomeObject]]
// 这部分下次看的时候再深入理解


// 扩展clock
// 创建一个继承自 Clock 的新的类 ExtendedClock，并添加参数 precision — 每次 “ticks” 之间间隔的毫秒数，默认是 1000（1 秒）
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

class ExtendedClock extends Clock {
    constructor(options) {
        super(options);
        let {precision = 1000} = options;
        this.precision = precision;
    }

    start() {
       this.render();
       this.timer = setInterval(() => this.render(), this.precision);

    }
}
let clock = new ExtendedClock({template: "h:m:s"});
clock.start();


// 类扩展自对象
// class Rabbit              	                 class Rabbit extends Object
// –	                                         needs to call super() in constructor
// Rabbit.__proto__ === Function.prototype	     Rabbit.__proto__ === Object