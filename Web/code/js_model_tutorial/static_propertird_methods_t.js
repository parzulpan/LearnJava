// 静态属性和静态方法


// 把一个方法赋值给类的函数本身，而不是赋给他的"prototype"，这样的方法被称为静态的(static)
console.log('--------------------------');
class User {
    static staticMethod() {
        console.log(this === User);
    }
}
User.staticMethod();
// 等价于
User.staticMethod1 = function() {
    console.log(this === User);
};
User.staticMethod1();

// 通常，静态方法用于实现属于该类但不属于该类任何特定对象的函数
console.log('--------------------------');
class Article {
    constructor(title, date) {
        this.title = title;
        this.date = date;
    }

    static compare(article1, article2) {
        return article1.date - article2.date;
    }

}
let articles = [
    new Article("HTML", new Date(2019, 1, 1)),
    new Article("CSS", new Date(2018,2, 3)),
    new Article("JavaScript", new Date(2020, 4, 4))
];
articles.sort(Article.compare);
console.log(articles[0].title);


// 静态属性
// 静态的属性也是可能的，它们看起来就像常规的类属性，但前面加由static
console.log('--------------------------');
class Article1 {
    static published = "PaF";
}
console.log(Article1.published);
// 等价于
Article1.published1 = "FPa";
console.log(Article1.published1);


// 继承静态属性和方法
// 静态属性和方法是可被继承的
console.log('--------------------------');
class Animal {
    static planet = "Earth";

    constructor(name, speed) {
        this.speed = speed;
        this.name = name;
    }

    run(speed=0) {
        this.speed += speed;
        console.log(`${this.name} runs with speed ${this.speed}.`);
    }

    static compare(animal1, animal2) {
        return animal1.speed - animal2.speed;
    }

}
class Rabbit extends Animal {
    hide() {
        console.log(`${this.name} hides.`);
    }

}
let rabbits = [
    new Rabbit("White Rabbit", 10),
    new Rabbit("Black Rabbit", 5),
];
rabbits.sort(Rabbit.compare);
rabbits[0].run();
console.log(Rabbit.planet);
// Rabbit extends Animal 创建两个[[prototype]]引用
// 1. Rabbit函数原型继承自Animal函数
// 2. Rabbit.prototype原型继承自Animal.prototype
// 所以，继承对常规方法和静态方法都有效
console.log(Rabbit.__proto__ === Animal);   // true
console.log(Rabbit.prototype.__proto__ === Animal.prototype);   // true