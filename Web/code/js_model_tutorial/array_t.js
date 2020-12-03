let arr = [];
let fruits = ["Apple", "Orange", "Plum"];
console.log(fruits, fruits.length);

// 作用于数组末端的方法
// 取出并返回数组的最后一个元素
console.log(fruits.pop());
console.log(fruits);
// 在数组末端添加元素
console.log(fruits.push("Pear"));
console.log(fruits);

// 作用于数组首端的方法
// 取出数组的第一个元素并返回它
console.log(fruits.shift());
console.log(fruits);
// 在数组的首端添加元素
console.log(fruits.unshift("Pineapple"));
console.log(fruits);

// push和unshift都可以一次添加多个元素
let letter = ["A"];
letter.push("B", "C", );
letter.unshift("G", "H", );
console.log(letter);

// 数组是一个对象
// 通过引用复制，两个变量引用的是相同的数组
let copyLetter  = letter;
console.log(copyLetter === letter);
copyLetter.push("W");
console.log(letter);

// 因为数组是基于对象的，可以给它们添加任何属性
// 但是，Javascript 引擎会发现，我们在像使用常规对象一样使用数组，那么针对数组的优化就不再适用了，然后对应的优化就会被关闭，这些优化所带来的优势也就荡然无存了。
// 数组误用的几种方式
// 1. 添加一个非数字的属性，比如arr.test = 5
// 2. 制造空洞，比如arr[0]，然后添加arr[1000](它们中间什么都没有)
// 3. 以倒序填充数组，比如arr[1000]，arr[999]
// 请将数组视为作用于 有序数据 的特殊结构

// 循环
for (let i = 0; i <copyLetter.length; ++i) {
    console.log(copyLetter[i]);
}
// 通常来说，不应该使用 for .. in 来使用数组
for(let le of copyLetter) {
    console.log(le);
}

// 关于length
// 1. 当修改数组的时候，length 属性会自动更新。准确来说，它实际上不是数组里元素的个数，而是最大的数字索引值加一。
// 2. length 属性的另一个有意思的点是它是可写的。手动增加它，则不会发生任何有趣的事儿。但是如果减少它，数组就会被截断。该过程是不可逆的。
// 所以，清空数组最简单的方法就是：arr.length = 0;
let lengthL = ["f","uu"];
lengthL[123] = "fa";
console.log(lengthL.length);


// 多维数组
console.log("----------------");
let matrix = [
    [1, 2, 3],
    [4, 5, 6],
    [7, 8, 9],
];
console.log(matrix.length, matrix[1][1]);
// 数组有自己的 toString 方法的实现，会返回以逗号隔开的元素列表
console.log( "" + 1 ); // "1"
console.log( "1" + 1 ); // "11"
console.log( "1,2" + 1 ); // "1,21"


let arrL = ["a", "b"];
arrL.push(function() {
    let temp = "";
    return this ;
});
console.log(arrL[2]()); // "a","b",function


// 输入数字求和
function sumInput() {

    let numbers = [];

    while (true) {

        let value = prompt("A number please?", 0);

        // 应该结束了吗？
        if (value === "" || value === null || !isFinite(value)) break;

        numbers.push(+value);
    }

    let sum = 0;
    for (let number of numbers) {
        sum += number;
    }
    return sum;
}

// alert( sumInput() );


// 最大子数组
// 找出所有项的和最大的 arr 数组的连续子数组，写出函数 getMaxSubSum(arr)，用其找出并返回最大和
// 思路：遍历数组，将当前局部元素的和保存在变量s中，如果s在某一点变成负数了，就重新分配s=0，所以s中的最大值就是答案
function getMaxSubSum(arr) {
    let maxSum = 0;
    let partialSum = 0;

    for (let item of arr) {
        partialSum += item;
        maxSum = Math.max(maxSum, partialSum);
        if (partialSum < 0)
            partialSum = 0;
    }

    return maxSum;
}
console.log("-----------");
console.log(getMaxSubSum([-1, 2, 3, -9]));
console.log(getMaxSubSum([-1, 2, 3, -9, 11]));
console.log(getMaxSubSum([-2, -1, 1, 2]));
console.log(getMaxSubSum([100, -9, 2, -3, 5]));
