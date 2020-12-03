// 类检查："instanceof"


// instanceof操作符用于检查一个对象是否属于某个特定的class，同时，它还考虑了继承
// 语法：obj instanceof Class
// 如果 obj 隶属于 Class 类（或 Class 类的衍生类），则返回 true。
console.log('--------------------------');
class Rabbit {}
let rabbit = new Rabbit();
console.log(rabbit instanceof Rabbit);  // true

// 构造函数，而不是class
function RabbitC() {}
console.log(new RabbitC() instanceof RabbitC);  // true

// 通常，instanceof在检查中会讲原型链考虑在内。可以在静态方法Symbol.hasInstance中设置自定义逻辑

// obj instanceof Class算法的执行过程：
// 1. 如果儿又静态方法Symbol.hasInstance，那就直接调用这个方法
// 设置 instanceOf 检查
// 并假设具有 canEat 属性的都是 animal
console.log('--------------------------');
class Animal {
    static [Symbol.hasInstance](obj) {
        if (obj.canEat) return true;
    }
}
let obj = { canEat: true };
console.log(obj instanceof Animal); // true：Animal[Symbol.hasInstance](obj) 被调用
// 2. 否则，使用obj instanceof Class检查Class.prototype是否等于obj的原型链中的原型之一


// 使用Object.prototype.toString方法来提示类型
// 按照规范，内建的toString方法可以被从对象中提取出来，并在任何其他值的上下文中执行，其结果取决于该值
// 1. 对于number类型，结果是[object Number]
// 2. 对于boolean类型，结果是[object Boolean]
// 3. 对于null，结果是[object Null]
// 4. 对于undefined，结果是[object Undefined]
// 5. 对于数组，结果是[object Undefined]
console.log('--------------------------');
let objectToString = Object.prototype.toString;
let arr = [];
console.log( objectToString.call( arr ));   // [object Array]
console.log( objectToString.call( 123 ));   // [object Number]
console.log( objectToString.call( null ));  // [object Null]
console.log( objectToString.call( console.log ));   // [object Function]


// Symbol.toStringTag
// 可以使用特殊的对象属性Symbol.toStringTag自定义对象的toString方法的行为
console.log('--------------------------');
let user = {
    [Symbol.toStringTag]: "User"
};
console.log( {}.toString.call( user ) );    // [object User]


// 建议：如果想要获取内建对象的类型，并希望能该信息以字符串的形式返回，而不只是检查类型的发，可以用{}.toString.call替代instanceof


// 不按套路出牌的instanceof
// 为什么instanceof会返回true？
console.log('--------------------------');
function A() {}
function B() {}
A.prototype = B.prototype = {};
let a = new A();
console.log( a instanceof B );  // true
// 分析：
// instanceof并不关心函数，而是关心函数的与原型链匹配的prototype，并且这里的a.__proto__ == B.prototype.
// 总之，根据instanceof的逻辑，真正决定类型的是prototype，而不是构造函数


