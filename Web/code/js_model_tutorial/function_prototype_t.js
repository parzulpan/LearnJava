// F.prototype


// 这里的F.prototype指的是F的一个名为"prototype"的常规属性，虽然听起来与"原型"这个术语很类似，实际上指的是具有该名字的常规属性
console.log('--------------------------');
let animal = {
    eats: true,
};
function Rabbit(name) {
    this.name = name;
}
// 字面意思：当创建了一个new Rabbit时，把它的[[prototype]]赋值为animal
Rabbit.prototype = animal;
let rabbit = new Rabbit("White Rabbit");    //rabbit.__proto__ == animal
console.log( rabbit.eats ); // true
// F.prototype仅用在new F时，它为新对象的[[prototype]]赋值，如果在创建之后，F.prototype属性有了变换
// 那么通过在new F创建的新对象也将随之拥有新的对象作为[[prototype]]，但已经存在的对象将保持旧有的值


// 默认的F.prototype，构造器属性
// 每个函数都有prototype属性，即使没有手动提供它
// 默认的"prototype"是一个只有属性constructor的对象，属性constructor指向函数自身
console.log('--------------------------');
function Rabbit1() {}
// by default:
// Rabbit.prototype = { constructor: Rabbit }
console.log( Rabbit1.prototype.constructor === Rabbit1 ); // true


// 修改"prototype"
console.log('--------------------------');
function Rabbit2() {}
Rabbit2.prototype = {
    eats: true
};

let rabbit1 = new Rabbit2(); // 引用了上面的prototype

Rabbit2.prototype = {
    eats: false
};

let rabbit2 = new Rabbit2(); // 引用了新定义的的prototype
delete Rabbit2.prototype.eats; // 删除新定义的prototype的eats
console.log( rabbit1.eats ); // 从之前引用的prototype取值 true
console.log( rabbit2.eats ); // 从新的prototype取值 undefined