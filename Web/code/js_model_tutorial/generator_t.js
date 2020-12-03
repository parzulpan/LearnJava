// Generator


// 常规函数只会返回一个单一值，或者不返回任何值
// 而Generator可以按需一个接一个地返回("yield")多个值，它们可与iterable完美配合使用，从而轻松地创建数据流



// Generator 函数
// 要创建一个 generator，需要一个特殊的语法结构：function* ，即所谓的"generator function"
function* generateSequence() {
    yield 1;
    yield 2;
    return 3;
}

// Generator函数与常规函数的行为不同，在此类函数被调用时，它不会允许其代码，而是返回一个被称为"generator object"的特殊对象，来管理执行流程
let generator = generateSequence();
console.log(generator);     // Object [Generator] {}

// 一个 generator 的主要方法就是 next()，它的结果始终是一个具有两个属性的对象
// 1. value -- 产出的(yielded)的值
// 2. done -- 如果generator反复已执行完成则为true，否则为false
let one = generator.next();
console.log(JSON.stringify(one));   // {"value":1,"done":false}
let two = generator.next();
console.log(JSON.stringify(two));   // {"value":2,"done":false}
let three = generator.next();
console.log(JSON.stringify(three)); // {"value":3,"done":true}
let done = generator.next();
console.log(JSON.stringify(done));  // "done":true}


let generator1 = generateSequence();
// Generator是可迭代的
for( let value of generator1) {
    console.log(value); // 只显示了1和2，没显示3，因为当 done: true 时，for..of 循环会忽略最后一个 value
}

function* generateSequence1() {
    yield 1;
    yield 2;
    yield 3;
}
let generator2 = generateSequence1();
// Generator是可迭代的
for( let value of generator2) {
    console.log(value); // 都显示
}
let generator3 = generateSequence1();
console.log( [0, ...generator3] );


// 使用generator进行迭代
// 之前创建过一个可迭代的range对象，它返回from..to的值
let range = {
    from: 1,
    to: 5,

    [Symbol.iterator]() {
        return {
            current: this.from,
            last: this.to,

            next() {
                if(this.current <= this.last ) {
                    return {value: this.current++, done: false}
                } else {
                    return {done: true}
                }
            }
        };
    }
};
console.log( [...range] );

// 可以通过提供一个 generator 函数作为 Symbol.iterator，来使用 generator 进行迭代
let rangeG = {
    from: 1,
    to: 5,

    *[Symbol.iterator]() {  // [Symbol.iterator]: function*() 的简写形式
        for(let value=this.from; value<=this.to; value++) {
            yield value;
        }
    }
};
console.log( [...rangeG])
// 之所以代码正常工作，是因为range[Symbol.iterator]()现在返回一个generator，而generator方法正是for..of说期望的
// 1. 它具有.next()方法
// 2. 它以 {value: ..., done: true/false} 的形式返回值



// Generator组合
// Generator组合是generator的一个特殊功能，它允许透明地将generator彼此"嵌入"到一起
// 假设有一个生成数字序列的函数
console.log('--------------------------')
function* generateSequenceG(start, end) {
    for (let i = start; i <= end; i++) yield i;
}
// 但是现在想重用这个函数，生成更复杂地序列(数字、大写字母、小写字母)
function* generatePasswordCodes() {
    // 0..9
    yield* generateSequenceG(48, 57);   // yield* 指令将执行委托给另一个generator

    // A..Z
    yield* generateSequenceG(65, 90);

    // a..z
    yield* generateSequenceG(97, 122);
}

let str = '';
for (let code of generatePasswordCodes()) {
    str += String.fromCharCode(code);
}
console.log(str);
// Generator 组合（composition）是将一个generator流插入到另一个generator流自然的方式，它不需要使用额外的内存来存储中间结果



// "yield"是一条双向路
// 它不仅可以向外返回结果，而且还可以将外部的值传递到generator内
// 调用generator.next(arg)，就能将参数arg传递到generator内部，这个arg参数会变成yield的结果
console.log('--------------------------')
function* gen() {
    // 向外部代码传递一个问题并等待答案
    let result = yield "2 + 2 = ?";
    console.log(result);
}
let generatorGen = gen();
let question = generatorGen.next().value;  // yield返回的value
console.log(question);
generatorGen.next(4);
generatorGen.next();



// generator.throw
// 要向yield传递一个error，应该调用generator.throw(err)，在这种情况下，err将被抛到对应的yield所在的那一行
console.log('--------------------------')
function* genE() {
    try {
        let result = yield "3654 * 3654 = ?";
        console.log("The execution does not reach here, because the exception is thrown above");
    } catch (err) {
        console.log(err.name + err.message);
    }
}
let gE = genE();
let questionE = gE.next().value;
console.log(questionE);
gE.throw(new Error("The answer is not found in my database"));



// 伪随机generator
// 这里的任务是创建一个 generator 函数 pseudoRandom(seed)，它将 seed 作为参数并使用此公式创建 generator。
// 公式：next = previous * 16807 % 2147483647
console.log('--------------------------')
function* pseudoRandom(seed) {
    let value = seed;
    while (true) {
        value = value * 16807 % 2147483647
        yield value;
    }
}

let gR = pseudoRandom(1)
console.log(gR.next().value);   // 16807
console.log(gR.next().value);   // 282475249
console.log(gR.next().value);   // 1622650073