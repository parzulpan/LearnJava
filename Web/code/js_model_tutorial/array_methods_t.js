// arr.splice(str) 方法可以说是处理数组的瑞士军刀。它可以做所有事情：添加，删除和插入元素。
// arr.splice(index[, deleteCount, elem1, ..., elemN])
// 从 index 开始：删除 deleteCount 个元素并在当前位置插入 elem1, ..., elemN。最后返回已删除元素的数组。
let arr = ["I", "study", "JavaScript"];
arr.splice(1, 1); // 从索引 1 开始删除 1 个元素
console.log( arr ); // ["I", "JavaScript"]

let arr1 = ["I", "study", "JavaScript", "right", "now"];
// remove 3 first elements and replace them with another
arr1.splice(0, 3, "Let's", "dance");
console.log( arr1 ) // now ["Let's", "dance", "right", "now"]

let arr2 = ["I", "study", "JavaScript", "right", "now"];
// 删除前两个元素
let removed = arr2.splice(0, 2);
console.log( removed ); // "I", "study" <-- 被从数组中删除了的元素

let arr3 = ["I", "study", "JavaScript"];
// 从索引 2 开始
// 删除 0 个元素
// 然后插入 "complex" 和 "language"
arr3.splice(2, 0, "complex", "language");
console.log( arr3 ); // "I", "study", "complex", "language", "JavaScript"

let arr4 = [1, 2, 5];
// 从索引 -1（尾端前一位）
// 删除 0 个元素，
// 然后插入 3 和 4
arr4.splice(-1, 0, 3, 4);
console.log( arr4 ); // 1,2,3,4,5


// arr.slice([start], [end])
// 它会返回一个新数组，将所有从索引 start 到 end（不包括 end）的数组项复制到一个新的数组。
// start 和 end 都可以是负数，在这种情况下，从末尾计算索引。
// 它和字符串的 str.slice 方法有点像，就是把子字符串替换成子数组。
let arr5 = ["t", "e", "s", "t"];
console.log(arr5.slice(1, 3));
console.log(arr5.slice(-2))


// arr.concat创建一个新数组，其中包含来自于其他数组和其他项的值
// arr.concat(arg1, arg2...)，它接受任意数量的参数(数组和值都可以)，结果是一个包含来自于arr，然后是arg1、arg2的元素的新数组
let arr6 = [1, 2];
// create an array from: arr and [3,4]
console.log( arr6.concat([3, 4]) ); // 1,2,3,4
// create an array from: arr and [3,4] and [5,6]
console.log( arr6.concat([3, 4], [5, 6]) ); // 1,2,3,4,5,6
// create an array from: arr and [3,4], then add values 5 and 6
console.log( arr6.concat([3, 4], 5, 6) ); // 1,2,3,4,5,6



// 遍历：forEach
// arr.forEach 方法允许为数组的每个元素都运行一个函数
// arr.forEach(function(item, index, array) {
//   // ... do something with item
// });
["132", "133", "134", "135", "136", "137", "138"].forEach(console.log);


// 在数组中搜索
// indexOf lastIndexOf includes
// arr.indexOf(item, from) 从索引 from 开始搜索 item，如果找到则返回索引，否则返回 -1
// arr.lastIndexOf(item, from) — 和上面相同，只是从右向左搜索
// arr.includes(item, from) — 从索引 from 开始搜索 item，如果找到则返回 true（译注：如果没找到，则返回 false）
let arr7 = [1, 0, false];
console.log( arr7.indexOf(0) );
console.log( arr7.indexOf(false) );
console.log( arr7.indexOf(34) );
console.log( arr7.includes(1));
console.log( arr7.includes(34));


// find和findindex
// let result = arr.find(function(item, index, array) {
//   // 如果返回 true，则返回 item 并停止迭代
//   // 对于 falsy 则返回 undefined
// });
// arr.findIndex 方法（与 arr.find 方法）基本上是一样的，但它返回找到元素的索引，而不是元素本身。
// 并且在未找到任何内容时返回 -1。
let users = [
    {id: 1, name: 'John'},
    {id: 2, name: 'Pate'},
    {id: 3, name: 'Ja'},
];
let user = users.find(item => item.id === 1);
console.log(user.name);


// filter
// 语法与 find 大致相同，但是 filter 返回的是所有匹配元素组成的数组
// let results = arr.filter(function(item, index, array) {
//   // 如果 true item 被 push 到 results，迭代继续
//   // 如果什么都没找到，则返回空数组
// });
let user1 = [
    {id: 1, name: 'John'},
    {id: 2, name: 'Pate'},
    {id: 3, name: 'Ja'},
]
// 返回前两个用户的数组
let someUsers = user1.filter(item => item.id < 3);
console.log(someUsers.length);


// map
// 它对数组的每个元素都调用函数，并返回结果数组。
// let result = arr.map(function(item, index, array) {
//   // 返回新值而不是当前元素
// })
// 将每个元素转换为它的字符串长度
let lengths = ["Bilbo", "Gandalf", "ss"].map(item => item.length);
console.log(lengths);


// sort
// arr.sort 方法对数组进行 原位（in-place） 排序，更改元素的顺序。译注：原位是指在此数组内，而非生成一个新数组。)
let numberArr = [1, 2, 15];
numberArr.sort();
console.log(numberArr); // 1, 15, 2  这些元素默认情况下被按字符串进行排序。
// 要使用自己的排序顺序，需要提供一个函数作为 arr.sort() 的参数。
function compareNumeric(a, b) {
    // 按数字进行排序
    if (a > b) return 1;
    if (a === b) return 0;
    if (a < b) return -1;
}
numberArr.sort(compareNumeric);
console.log(numberArr);


// reverse
// arr.reverse 方法用于颠倒 arr 中元素的顺序
let reverseArr = [1, 2, 3, 4, 5, 6, 7, 8, 9];
reverseArr.reverse();
console.log(reverseArr);


// split和join
let names = 'Bill, Curry, KD';
let arrSplit = names.split(', ')
for(let name of arrSplit) {
    console.log(`A message to ${name}`);
}
arrSplit.map(item => console.log(`A message to ${item}`));
let strSplit = "test";
console.log(strSplit.split(''));    // [t, e, s, t]


// reduce和reduceRight
// 用于根据数组计算单个值，arr.reduceRight 和 arr.reduce 方法的功能一样，只是遍历为从右到左。
// let value = arr.reduce(function(accumulator, item, index, array) {
//   // ...
// }, [initial]);
// 一行代码得到一个数组的总和
let arrReduce = [1, 2, 3, 4, 5, 6, 7, 8, 9];
console.log( arrReduce.reduce((sum, current) => sum + current, 0) );


// Array.isArray
// 用于判断：Array.isArray(value)。如果 value 是一个数组，则返回 true；否则返回 false。
console.log( Array.isArray({}) );
console.log( Array.isArray([]) );


// 编写函数 camelize(str) 将诸如 “my-short-string” 之类的由短划线分隔的单词变成骆驼式的 “myShortString”。
// 即：删除所有短横线，并将短横线后的每一个单词的首字母变为大写。
function camelize(str) {
    return str
        .split('-')
        .map((word, index) => index === 0 ? word : word[0].toUpperCase() + word.slice(1))
        .join('');
}
console.log( camelize("background-color") === 'backgroundColor');
console.log( camelize("list-style-image") === 'listStyleImage' );
console.log( camelize("-webkit-transition") === 'WebkitTransition' );


// 写一个函数 filterRange(arr, a, b)，该函数获取一个数组 arr，在其中查找数值大小在 a 和 b 之间的元素，并返回它们的数组。
// 该函数不应该修改原数组。它应该返回新的数组。
function filterRange(arr, a, b) {
    return arr.filter( item => (a <= item && item <=b));
}
let arrFilterRange = [5, 3, 15, 1];
let arrFilterRanged = filterRange(arrFilterRange, 1 ,4);
console.log( arrFilterRange, arrFilterRanged);


// 写一个函数 filterRangeInPlace(arr, a, b)，该函数获取一个数组 arr，并删除其中介于 a 和 b 区间以外的所有值。检查：a ≤ arr[i] ≤ b。
// 该函数应该只修改数组。它不应该返回任何东西。
function filterRangeInPlace(arr, a, b) {
    for(let i = 0; i < arr.length; ++i) {
        let val = arr[i];
        if(val < a || val > b) {
            arr.splice(i, 1);
            --i;
        }
    }
}
let arrFilterRangeInPlace = [5, 3, 15, 1];
filterRangeInPlace(arrFilterRangeInPlace, 1, 4);
console.log( arrFilterRangeInPlace );


// 降序排列
let arrDescendingOrder = [5, 2, 1, -10, 8];
arr.sort((a, b) => b - a);
console.log( arrDescendingOrder );


// 有一个字符串数组 arr。希望有一个排序过的副本，但保持 arr 不变。
// 创建一个函数 copySorted(arr) 返回这样一个副本。
function copySorted(arr) {
    return arr.slice().sort();
}
let arrSorted = ["HTML", "JavaScript", "CSS"];
let arrCopySorted = copySorted(arrSorted);
console.log( arrSorted );
console.log( arrCopySorted );


// 创建一个可扩展的calculator
function Calculator() {

    this.methods = {
        "-": (a, b) => a - b,
        "+": (a, b) => a + b,
    };

     this.calculate = function(str) {
         let split = str.split(" "),
             a = +split[0],
             op = split[1],
             b = +split[2]

         if(!this.methods[op] || isNaN(a) || isNaN(b)) {
             return NaN;
         }

         return this.methods[op](a, b);
     };

     this.addMethod = function(name, func) {
         this.methods[name] = func;
     };
}
let powerCalc = new Calculator;
powerCalc.addMethod("*", (a, b) => a * b);
powerCalc.addMethod("/", (a, b) => a / b);
powerCalc.addMethod("**", (a, b) => a ** b);
powerCalc.addMethod("**", (a, b) => a % b);
console.log(powerCalc.calculate("2 + 3"));
console.log(powerCalc.calculate("2 * 3"));
console.log(powerCalc.calculate("2 / 3"));
console.log(powerCalc.calculate("2 ** 3"));
console.log(powerCalc.calculate("3 % 2"));


// 数组去重
// 创建一个函数 unique(arr)，返回去除重复元素后的数组 arr。
function unique(arr) {
    let result = [];

    for (let str of arr) {
        if (!result.includes(str)) {
            result.push(str);
        }
    }

    return result;
}
let strings = ["Hare", "Krishna", "Hare", "Krishna",
    "Krishna", "Krishna", "Hare", "Hare", ":-O"
];
console.log( unique(strings) ); // Hare, Krishna, :-O
// 这种实现方式还有很大的优化空间


// 从数组创建键（值）对象
// 假设收到了一个用户数组，形式为：{id:..., name:..., age... }。
// 创建一个函数 groupById(arr) 从该数组创建对象，以 id 为键（key），数组项为值。
function groupById(array) {
    return array.reduce((obj, value) => {
        obj[value.id] = value;
        return obj;
    }, {})
}
let usersGroupById = [
    {id: 'john', name: "John Smith", age: 20},
    {id: 'ann', name: "Ann Smith", age: 24},
    {id: 'pete', name: "Pete Peterson", age: 31},
];
let usersById = groupById(usersGroupById);
console.log( usersById );

