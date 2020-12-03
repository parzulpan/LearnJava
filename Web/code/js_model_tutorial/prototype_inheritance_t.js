// 原型继承


// [[Prototype]]
// 在JS中，对象有一个特殊的隐藏属性[Prototype]]，它要么为null，要么就是对另一个对象的应用，该对象被称为原型
// 属性[[Prototype]]是内部的而且是隐藏的，但是也有很多设置它的方式
console.log('--------------------------');
let animal = {
    eats: true,
    walk() {
        console.log("Animal walk");
    }
};
let rabbit = {
    jumps: true
};
// animal 是 rabbit 的原型
rabbit.__proto__ = animal;
// 现在这两个属性我们都能在 rabbit 中找到：
console.log(rabbit.eats);
console.log(rabbit.jumps);
rabbit.walk();

// __proto__ 是 [[Prototype]] 的因历史原因而留下来的 getter/setter
// 注意，__proto__ 与 [[Prototype]] 不一样。__proto__ 是 [[Prototype]] 的 getter/setter。
// __proto__ 的存在是历史的原因。在现代编程语言中，将其替换为函数 Object.getPrototypeOf/Object.setPrototypeOf 也能 get/set 原型。

// 原型链可以很长，但有几个限制：
// 1. 引用不能形成闭环，如果在一个闭环中分配__proto__，这会报错
// 2. _proto__ 的值可以是对象，也可以是 null。而其他的类型都会被忽略
// 3. 只能有一个 [[Prototype]]，一个对象不能从其他两个对象获得继承


// 写入不使用原型
// 原型仅用于读取属性，对于写入/删除操作可以直接在对象上运行
console.log('--------------------------');
let animal1 = {
    eats: true,
    walk() {

    }
};
let rabbit1 = {
    __proto__: animal1
};
rabbit1.walk = function() {
    console.log("Rabbit! Bounce-bounce!");
};
rabbit1.walk();


// "this"的值
// this不受原型的影像。无论在哪里找到方法：在一个对象还是在原型中。在一个方法调用中，this始终是点符号.前面的对象`
// animal 有一些方法
console.log('--------------------------');
let animal2 = {
    walk() {
        if (!this.isSleeping) {
            console.log(`I walk`);
        }
    },
    sleep() {
        this.isSleeping = true;
    }
};
let rabbit2 = {
    name: "White Rabbit",
    __proto__: animal2
};
// 修改 rabbit.isSleeping
rabbit2.sleep();
console.log(rabbit2.isSleeping); // true
console.log(animal2.isSleeping); // undefined（原型中没有此属性）
// 所以，方法是共享的，但是对象状态不是


// for .. in 循环
// for .. in 循环也会迭代继承的属性
console.log('--------------------------');
let animal3 = {
    eats: true
};
let rabbit3 = {
    jumps: true,
    __proto__: animal3
};
// Object.keys 只返回自己的 key
console.log(Object.keys(rabbit3)); // jumps
// for..in 会遍历自己以及继承的键
for(let prop in rabbit3) console.log(prop); // jumps，然后是 eats

// 但这不是正常情况下想要的，需要排除继承的属性
// 内建方法 obj.hasOwnProperty(key)：如果 obj 具有自己的（非继承的）名为 key 的属性，则返回 true
for(let prop in rabbit3) {
    let isOwn = rabbit3.hasOwnProperty(prop)
    if(isOwn) {
        console.log(`Our: ${prop}`);
    } else {
        console.log(`Inherited: ${prop}`);
    }
}


// 使用原型
// 创建了一对对象，然后对它们进行修改，过程中会显示那些值
console.log('--------------------------');
let animal4 = {
    jumps: null
};
let rabbit4 = {
    __proto__: animal4,
    jumps: true,
};
console.log( rabbit4.jumps );   // true，来自rabbit
delete rabbit4.jumps;
console.log( rabbit4.jumps );   // null，来自animal
delete  animal4.jumps;
console.log( rabbit4.jumps );   // undefined，不再有这样的属性


// 搜索算法
// 使用 __proto__ 来分配原型，以使得任何属性的查找都遵循以下路径：pockets → bed → table → head。
// 例如，pockets.pen 应该是 3（在 table 中找到），bed.glasses 应该是 1（在 head 中找到）。
// 回答问题：通过 pockets.glasses 或 head.glasses 获取 glasses，哪个更快？必要时需要进行基准测试。
console.log('--------------------------');
let head = {
    glasses: 1,
};
let table = {
    pen: 3,
    __proto__: head,
};
let bed = {
    sheet: 1,
    pillow: 2,
    __proto__: table,
};
let pockets = {
    money: 200,
    __proto__: bed,
};
console.log( pockets.pen );
console.log( bed.glasses );
console.log( table.money ); // undefined
// 在现代引擎中，从性能的角度来看，是从对象还是从原型链获取属性都是没有区别的，引擎会记住在哪里找到该属性，并在下一次请求中重用它


// 写在哪里？
// 从 animal5 中继承的 rabbit5
// 如果我们调用 rabbi5t.eat()，哪一个对象会接收到 full 属性：animal5 还是 rabbit5？
console.log('--------------------------');
let animal5 = {
    eat() {
        this.full = true;
    }
};
let rabbit5 = {
    __proto__: animal5
};
rabbit5.eat();
// 是rabbit5
// 因为this是点符号前面的这个对象，因此rabbit5.eat()修改了rabbit5
// 属性查找和执行是两码事，首先在原型中找到rabbit5.eat方法，然后在this=rabbit5的情况下执行


// 为什么两只仓鼠都饱了？
// 有两只仓鼠：speedy 和 lazy 都继承自普通的 hamster 对象。
// 当我们喂其中一只的时候，另一只也吃饱了。为什么？如何修复它？
console.log('--------------------------');
let hamster = {
    stomach: [],
    eat(food) {
        this.stomach.push(food);
        // 解决1：改成简单的赋值
        // this.stomach = [food];
    }
};
let speedy = {
    __proto__: hamster,
    // 解决2：确保每个仓鼠都有自己的胃
    stomach: []
};
let lazy = {
    __proto__: hamster,
    // 解决2：确保每个仓鼠都有自己的胃
    stomach: []
};
speedy.eat("apple");
console.log(speedy.stomach);    // apple
console.log(lazy.stomach); // apple，这只仓鼠也找到了食物，为什么？请修复它！
// 原因：所有的仓鼠共享了一个胃
// 对于lazy.stomach.push(...)和speedy.stomach.push() 而言，属性stomach被在原型中找到，而不是对象本身，然后向其中push了新数据
// 解决1：改成简单的赋值 this.stomach = [food];
// 因为 this.stomach = 不会执行对 stomach 的查找。该值会被直接写入 this 对象。
// 解决2：确保每个仓鼠都有自己的胃



