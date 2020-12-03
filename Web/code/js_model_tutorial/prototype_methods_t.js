// 原型方法，没有__proto__的对象


// __proto__被认为是过时且不推荐使用的，这里的不推荐是指JS规范中规定，proto必须仅在浏览器环境下才能得到支持
// 现代的方法：
// 1. Object.create(proto, [descriptors])  利用给定的proto作为[[Prototype]]和可选的属性描述来创建一个空对象
// 2. Object.getPrototypeOf(obj)  返回对象obj的[[Prototype]]
// 3. Object.setPrototypeOf(obj, proto)  将对象obj的[[Prototype]]设置为proto
// 应该用这些现代的方法，替代__proto__
console.log('--------------------------');
let animal = {
    eats: true
};
// 创建一个以animal为原型的新对象，并利用第二个可选参数：属性描述器，为新对象提供额外的属性
let rabbit = Object.create(animal, {
    jumps: {
        value: true
    }
});
console.log(rabbit.eats);   // true
console.log(Object.getPrototypeOf(rabbit) === animal);  // true
// 修改rabbit的原型
Object.setPrototypeOf(rabbit, {});
console.log(Object.getPrototypeOf(rabbit) === animal);  // false


// "Very plain" objects
// 对象可以用作关联数组来存储键值对，但是如果尝试在其中存储用户提供的键，有会一个小故障：所有的键都正常工作，除了"__proto__"
console.log('--------------------------');
let obj = {};
let key = "__proto__";
obj[key] = "some value";
console.log(obj[key]);  // [object Object]，并不是 "some value"！
// 原因：因为__proto__属性很特别，它必须是对象或者null，字符串不能称为prototype
// 解决：使用Object.create(proto, [descriptors])
let obj1 = Object.create(null);
let key1 = "__proto__";
obj1[key1] = "some value";
console.log(obj1[key1]);    // some value


// 为dictionary添加toString方法
// 这儿有一个通过 Object.create(null) 创建的，用来存储任意 key/value 对的对象 dictionary。
// 为该对象添加 dictionary.toString() 方法，该方法应该返回以逗号分隔的所有键的列表。
// 你的 toString 方法不应该在使用 for...in 循环遍历数组的时候显现出来。
console.log('--------------------------');
let dictionary = Object.create(null, {
    // 定义toString属性
    toString: {
        value() {
            return Object.keys(this).join();
        },
    }
});
dictionary.apple = "Apple";
dictionary.__proto__ = "test";
// apple和__proto__在循环中
for(let key in dictionary) {
    console.log(key);
}
// 通过toString处理获得的以逗号分割的属性列表
console.log(dictionary);


// 调用方式的差异
console.log('--------------------------');
function Rabbit(name) {
    this.name = name;
}
Rabbit.prototype.sayHi = function() {
    console.log( this.name );
}
let rabbit2 = new Rabbit("Rabbit");
rabbit2.sayHi();                        // Rabbit
Rabbit.prototype.sayHi();               // undefined
Object.getPrototypeOf(rabbit2).sayHi(); // undefined
rabbit2.__proto__.sayHi();              // undefined

