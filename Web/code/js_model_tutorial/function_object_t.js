// 函数对象，NFE


// 在JS中，函数就是值，每个值都有一种类型，函数的类型就是对象
// 即把函数想象成可被调用的"行为对象(action object)"，不仅可以调用它们，还能把它们当作对象来处理：增/删属性，按引用传递等


// 属性"name"
// 一个函数的名字可以通过属性"name"来访问
console.log('--------------------------');
function sayHi() {
    console.log("Hello");
}
console.log(sayHi.name);

// 即使函数被创建时没有名字，名称赋值的逻辑也能给它赋予一个正确的名字，然后进行赋值
console.log('--------------------------');
let sayHiA = function() {
    console.log("Hi");
};
console.log(sayHiA.name); // sayHi（有名字！）
// 规范中把这种特性叫做「上下文命名」。如果函数自己没有提供，那么在赋值中，会根据上下文来推测一个。


// 属性"length"
// 它返回函数入参的个数
console.log('--------------------------');
function f1(a) {}
function f2(a, b) {}
function many(a, b, ...more) {}
console.log(f1.length); // 1
console.log(f2.length); // 2
console.log(many.length); // 2

// 应用，即所谓的多态性--根据参数的类型
console.log('--------------------------');
function ask(question, ...handlers) {
    let isYes = true;
    // let isYes = false;
    for(let handler of handlers) {
        if (handler.length === 0) {
            if (isYes) handler();
        } else {
            handler(isYes);
        }
    }
}
// 对于积极的回答，两个 handler 都会被调用
// 对于负面的回答，只有第二个 handler 被调用
ask("Question?", () => console.log('You said yes'), result => console.log(result));


// 自定义属性
// 添加counter属性，用来跟踪总的调用次数
console.log('--------------------------');
function sayHiC() {
    console.log("Hello");
    sayHiC.counter ++;
}
sayHiC.counter = 0; // 初始值
sayHiC();
sayHiC();
console.log(`Called ${sayHiC.counter} times`);


// 命名函数表达式
// NFE, Named Function Expression ，指带有名字的函数表达式的术语


// 为counter添加set和decrease方法
// 使得counter可以进行加1和设置值的操作
// 1. counter() 应该返回下一个数字
// 2. counter.set(value) 应该将count设置为value
// 3. counter.decrease(value) 应该把count减1
console.log('--------------------------');
function makeCounter() {
    let count = 0;
    function counter() {
        return count++;
    }
    counter.set = value => count = value;
    counter.decrease= () => count--;
    return counter;
}
let counter = makeCounter();
console.log(counter());
console.log(counter());
counter.set(102);
console.log(counter());
counter.decrease();
counter.decrease();
console.log(counter());


// 任意数量的括号求和
// 写一个函数sum，具有这样的功能：
// sum(1)(2) == 3; // 1 + 2
// sum(1)(2)(3) == 6; // 1 + 2 + 3
// sum(5)(-1)(2) == 6
// sum(6)(-1)(-2)(-3) == 0
// sum(0)(1)(2)(3)(4)(5) == 15
// 分析：
// 1. 为了使整个程序无论如何都能正常工作，sum的结果必须是函数
// 2. 这个函数必须将两次调用之间的当前值保存在内存中
// 3. 当函数被用于==比较时必须转换成数字，函数是对象，所以转换规则会按照 对象-原始值转换 进行，提供自己的方法来返回数字
console.log('--------------------------');
function sumSum(a) {
    let currentSum = a;

    function f(b) {
        currentSum += b;
        // 没有递归调用，只是返回了函数自己
        return f;
    }

    f.toString = function() {
        return currentSum;
    }

    return f;
}
console.log(sumSum(1)(2));
console.log(sumSum(1)(2)(3));
console.log(sumSum(5)(-1)(2));
console.log(sumSum(6)(-1)(-2)(-3));
console.log(sumSum(0)(1)(2)(3)(4)(5));