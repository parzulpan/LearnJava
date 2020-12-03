// 扩展内建类


// 内建的类，例如Array、Map等也都是可以扩展的
console.log('--------------------------');
class PowerArray extends Array {
    isEmpty() {
        return this.length === 0;
    }

    // 如果想要map或filter这样的内建方法返回常规数组，可以在Symbol.species中返回Array
    // static get [Symbol.species]() {
    //     return Array;
    // }
}
let arr = new PowerArray(1, 2, 50, 3, 55);
console.log(arr.isEmpty());

let filteredArr = arr.filter(item => item >= 10);
console.log(filteredArr);   // PowerArray(2) [ 50, 55 ]
console.log(filteredArr.isEmpty());
// 一个有趣的事情，内建的方法例如filter,map等返回的正是子类PowerArray的新对象，它们内部使用了对象的constructor属性来实现这以功能
console.log(arr.constructor === PowerArray);    // true
// 当arr.filter()被调用时，它的内部使用的是arr.constructor来创建新的结果数组，
// 而不是使用原生的Array，这很好，这样可以在结果数组上继续使用PowerArray方法


// 内建类没有静态方法继承
// 内建对象有它们自己的静态方法，例如Object.keys、Array.isArray等
// 原生的类互相扩展，通常，当一个类扩展另一个类时，静态方法和非静态方法都会被继承，但内建类是一个例外，它们相互间不继承静态方法







