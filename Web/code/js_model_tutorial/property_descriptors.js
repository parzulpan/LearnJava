"use strict"

// 属性标志和属性描述符


// 对象可以存储属性，但属性对我们来说只是一个简单的"键值对"，实际上对象属性是更灵活且更强大的东西


// 属性标志
// 对象属性，除value外，还有三个特殊的特性，也就是所谓的"标志"
// 1. writable -- 如果为true，则值可以被修改，否则它只是可读的
// 2. enumerable -- 如果为true，则会被在循环中列出，否则不会被列出
// 3. configurable -- 如果为true，则此特性可以被删除，这些属性也可以被修改，否则不可以
// 以上，默认都为true

// Object.getOwnPropertyDescriptor 方法允许查询有关属性的完整信息
console.log('--------------------------');
let user = {
    name: "ppp",
};
let descriptor = Object.getOwnPropertyDescriptor(user, "name");
console.log(JSON.stringify(descriptor, null, 2));
// {
//   "value": "ppp",
//   "writable": true,
//   "enumerable": true,
//   "configurable": true
// }

// 修改标志，可以使用 Object.defineProperty
// 如果该属性存在，defineProperty会更更新其标志，否则，它会使用给定的值和标志创建属性，在这种情况下，如果没有提供标志，则默认为false
console.log('--------------------------');
let user1 = {};
Object.defineProperty(user1, "name", {
    value: "John"
});
let descriptor1 = Object.getOwnPropertyDescriptor(user1, 'name');
console.log( JSON.stringify(descriptor1, null, 2 ) );
// {
//   "value": "John",
//   "writable": false,
//   "enumerable": false,
//   "configurable": false
// }


// 只读
// 通过更改writable标志来把user.name设置为只读，即user.name不能被重复赋值
console.log('--------------------------');
let user2 = {
    name: 'user2',
};
Object.defineProperty(user2, 'name', {
    writable: false
});
// user2.name = 'Wer'; //  // Error: Cannot assign to read only property 'name'
// 在非严格模式下，在对不可写的属性等进行写入操作时，不会出现错误。但是操作仍然不会成功。
// 在非严格模式下，违反标志的行为（flag-violating action）只会被默默地忽略掉。


// 不可枚举
// 向user添加一个自定义的toString
// 通常，对象的内置toString是不可枚举的，它不会显示在for .. in 中，但是如果添加自己的toString，那么默认情况下它将显示在for .. in
console.log('--------------------------');
let user3 = {
    name: 'user3',
    toString() {
        return this.name;
    }
};
// 设置 enumerable:false， toString就不会被列出
Object.defineProperty(user3, "toString", {
    enumerable: false
});
// 默认情况下，两个属性都会被列出
for(let key in user3) {console.log(key);}


// 不可配置
// 不可配置标志(configurable:false)有时会预设在内建对象和属性中
// 不可配置的属性不能被删除
// 使属性变成不可配置是一条单行道，无法使用defineProperty把它改回去
// 确切地说，不可配置性对defineProperty施加了一些限制：
// 1. 不能修改configurable标志
// 2. 不能修改enumerable标志
// 3. 不能将writable: false 修改为true，反之亦然
// 4. 不能修改访问者属性的get/set(但是没有可以分配它们)
console.log('--------------------------');
let user4 = { };
Object.defineProperty(user4, "name", {
    value: "user4",
    writable: false,
    configurable: false
});
// 不能修改 user4.name 或它的标志
// 下面的所有操作都不起作用：
//   user4.name = "Pete"
//   delete user.name
//   defineProperty(user4, "name", { value: "Pete" })
// Object.defineProperty(user4, "name", {writable: true}); // Error


// Object.defineProperties
// 有一个方法Object.defineProperties(obj, descriptors)，允许一次定义多个属性
console.log('--------------------------');
Object.defineProperties(user, {
    name: { value: "John", writable: false },
    surname: { value: "Smith", writable: false },
    // ...
});


// Object.getOwnPropertyDescriptors
// 要一次获取所有属性描述符，可以使用 Object.getOwnPropertyDescriptors(obj) 方法
console.log('--------------------------');
let clone = Object.defineProperties({}, Object.getOwnPropertyDescriptors(obj));


// 设定一个全局的密封对象
// 属性描述符是在单个属性的级别上工作
// 还有一些限制访问整个对象的方法
// Object.preventExtensions(obj)
// 禁止向对象添加新属性。

// Object.seal(obj)
// 禁止添加/删除/修改属性。为所有现有的属性设置 configurable: false。

// Object.freeze(obj)
// 禁止添加/删除/更改属性。为所有现有的属性设置 configurable: false, writable: false。

// 还有针对它们的测试：
// Object.isExtensible(obj)
// 如果添加属性被禁止，则返回 false，否则返回 true。

// Object.isSealed(obj)
// 如果添加/删除属性被禁止，并且所有现有的属性都具有 configurable: false则返回 true。

// Object.isFrozen(obj)
// 如果添加/删除/更改属性被禁止，并且所有当前属性都是 configurable: false, writable: false，则返回 true。