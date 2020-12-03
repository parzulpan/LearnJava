// 可迭代对象
let str = '𝒳😂';
console.log( str );


let range = {
    from: 0,
    to: 5
};
// 希望 for..of这样运行
// for(let num of range) // num = 1, 2, 3, 4, 5
// 为了让 range 对象可迭代（也就让 for..of 可以运行）
// 需要为对象添加一个名为 Symbol.iterator 的方法（一个专门用于使对象可迭代的内置 symbol）。
// 1. 当 for..of 循环启动时，它会调用这个方法（如果没找到，就会报错）。
// 这个方法必须返回一个 迭代器（iterator） —— 一个有 next 方法的对象。
// 2. 从此开始，for..of 仅适用于这个被返回的对象。
// 3. 当 for..of 循环希望取得下一个数值，它就调用这个对象的 next() 方法。
// 4. next() 方法返回的结果的格式必须是 {done: Boolean, value: any}，
// 当 done=true 时，表示迭代结束，否则 value 是下一个值。
range[Symbol.iterator] = function() {
    return {
        current: this.from,
        last: this.to,
        next() {
            if (this.current <= this.last) {
                return {done: false, value: this.current++};
            } else {
                return {done: true};
            }
        }
    };
};
for(let num of range) {
    console.log( num );
}


// 显式调用迭代器
let strI = `Hello`;
let iteratorI = strI[Symbol.iterator]();
while(true) {
    let result = iteratorI.next();
    if(result.done) break;
    console.log(result.value);
}