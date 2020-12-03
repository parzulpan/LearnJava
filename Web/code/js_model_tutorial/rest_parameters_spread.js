// Rest参数与Spread语法

// 如何编程实现支持函数可传入任意数量的参数，以及如何将数组作为参数传递给这类函数


// Rest参数...
// 在JS中，无论函数是如何定义的，都可以使用任意数量的参数调用函数
// Rest参数可以通过使用三个点...并在后面跟着包含剩余参数的数组名称，来将他们包含在函数定义中，即将剩余参数收集到一个数组中
// * Rest参数会收集剩余的所有参数，故它必须放到参数列表的末尾
console.log('--------------------------');
function sumAll(...args) {
    let sum = 0;
    for (let arg of args) {
        sum += arg;
    }
    return sum;
}
console.log(sumAll(1));
console.log(sumAll(1, 2));
console.log(sumAll(1, 2, 3));

// 也可以选择就获取前面的参数作为变量，并将剩余的参数收集起来
console.log('--------------------------');
function showName(firstName, lastName, ...titles) {
    console.log( firstName + ' ' + lastName ); // Julius Caesar
    // 剩余的参数被放入 titles 数组中
    // i.e. titles = ["Consul", "Imper"]
    console.log( titles[0] ); // Consul
    console.log( titles[1] ); // Imper
    console.log( titles.length ); // 2
}
showName("Julius", "Caesar", "Consul", "Imper");


// "arguments"变量
// arguments是一个特殊的类数组对象，该对象按参数索引包含所有参数
// 注意，这是老式用法，尽管 arguments 是一个类数组且可遍历的变量，但它终究不是数组。它不支持数组方法，因此不能调用 arguments.map(...) 等方法。
// 此外，它始终包含所有参数，不能像使用 rest 参数那样只截取入参的一部分。
// 因此，当需要这些功能时，最好使用Rest参数
console.log('--------------------------');
function showName1() {
    console.log( arguments.length );
    console.log( arguments[0] );
    console.log( arguments[1] );

    // 它是可遍历的
    // for(let arg of arguments) alert(arg);
}
// 依次显示：2，Julius，Caesar
showName1("Julius", "Caesar");
// 依次显示：1，add，undefined（没有第二个参数）
showName1("add");


// Spread语法
// 它看起来和Rest参数很像，也使用...，但是二者的用途完全相反
console.log('--------------------------');
let arrA = [3, 5, 1];
console.log( Math.max(arrA) ); // NaN
let arrS = [3, 5, 1];
console.log( Math.max(...arrS) );

// 传递多个可迭代对象
let arrS1 = [235435,434,54,1];
let arrS2 = [1,35768,4589,3,546,0];
console.log( Math.max(...arrS1, ...arrS2) );
console.log( Math.max(...arrS1, ...arrS2, 2154325634) );

// 使用spread语法合并数组
let arrS3 = [3, 5, 1];
let arrS4 = [241, 46, 1];
let merged = [0, ...arrS3, 324, ...arrS4];
console.log(merged);

// 将字符串转换为字符数组
console.log('--------------------------');
// 使用Spread语法
// Spread语法内部使用了迭代器收集元素，与for .. of的方式相同
let strPP = "Hello";
console.log([...strPP]);
// 使用Array.from
console.log(Array.from(strPP));
// Array.from(obj) 和 [...obj] 存在一个细微的差别
// 1. Array.from适用于类数组对象也适用于可迭代对象
// 2. Spread语法自适用于可迭代对象


// 获取一个object/array的副本
console.log('--------------------------');
let arr = [1, 2, 3];
let arrCopy = [...arr]; // 将数组 spread 到参数列表中, 然后将结果放到一个新数组
// 两个数组中的内容相同吗？
console.log(JSON.stringify(arr) === JSON.stringify(arrCopy)); // true
// 两个数组相等吗？
console.log(arr === arrCopy); // false（它们的引用是不同的）
// 修改我们初始的数组不会修改副本：
arr.push(4);
console.log(arr); // 1, 2, 3, 4
console.log(arrCopy); // 1, 2, 3
// 这种方式比使用 let arrCopy = Object.assign([], arr); 来复制数组，
// 或使用 let objCopy = Object.assign({}, obj)； 来复制对象的代码量要短得多，更加推荐使用它
