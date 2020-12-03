// 解构赋值

// 解构赋值是一种特殊的语法，它可以将数组或者对象“拆包”为到一系列变量中，因为有时候使用变量更加方便
// 解构操作对那些具有很多参数和默认值等的函数也很有效


// 数组解构 *
console.log('--------------------------');
let arr1 = ['JF', '9FG'];
let [firstName, surname] = arr1;
console.log(firstName)
console.log(surname)
let [firstName1, surname1] = 'FJ GF9'.split(' ');
console.log(firstName1);
console.log(surname1);
// 忽略使用逗号的应用
let [firstName2, ,surname2] = ['JJJ', 'KKK', '325', '996'];
console.log(firstName2);
console.log(surname2);
// 等号右侧可以是任何可迭代对象
let [aa, bb, cc] = 'abc';
console.log(aa, bb, cc);
let [one, two, three] = new Set([1, 2, 3]);
console.log(one, two, three);


// 与Object.entries()和map对象一起使用
console.log('--------------------------');
let userT = {
    name: 'XP',
    age:34
}
for(let [key, value] of Object.entries(userT)) {
    console.log(`${key}: ${value}`);
}
let userM = new Map();
userM.set('name', 'PX');
userM.set('age', '43');
for(let [key, value] of userM) {
    console.log(`${key}: ${value}`)
}


// 剩余的'...'
console.log('--------------------------');
let [name1, name2, ...rest] = ["Julius", "Caesar", "Consul", "of the Roman Republic"];
console.log(name1); // Julius
console.log(name2); // Caesar
// 请注意，`rest` 的类型是数组
console.log(rest[0]); // Consul
console.log(rest[1]); // of the Roman Republic
console.log(rest.length); // 2


// 默认值
// 如果赋值语句中，变量的数量多于数组中实际元素的数量，赋值不会报错。未赋值的变量被认为是 undefined
// 如果想要一个“默认”值给未赋值的变量，我们可以使用 = 来提供：
console.log('--------------------------');
let [name3 = "Guest", surname3 = "Anonymous", nullName] = ["Julius"];
console.log(name3);    // Julius（来自数组的值）
console.log(surname3); // Anonymous（默认值被使用了）
console.log(nullName); // undefined


// 对象解构 *
console.log('--------------------------');
let options = {
    title: "Menu",
    width: 100,
    height: 200
};
let {title, width, height} = options;
console.log(title);  // Menu
console.log(width);  // 100
console.log(height); // 200

console.log('--------------------------');
let options1 = {
    title1: "Menu",
    width1: 100,
    height1: 200
};
// { sourceProperty: targetVariable }
let {width1: w, height1: h, title1} = options1;
// width -> w
// height -> h
// title -> title
console.log(title);  // Menu
console.log(w);      // 100
console.log(h);      // 200


// 剩余模式(pattern) "..."
console.log('--------------------------');
let options2 = {
    title2: "Menu",
    width2: 100,
    height2: 200
};
let {title2, ...rest2} = options2;
console.log(title2);
console.log(rest2);


// 嵌套解构
console.log('--------------------------');
let optionsQ = {
    sizeQ: {
        widthQ: 100,
        heightQ: 200
    },
    itemsQ: ["Cake", "Donut"],
    extraQ: true
};
// 为了清晰起见，解构赋值语句被写成多行的形式
let {
    sizeQ: { // 把 size 赋值到这里
        widthQ,
        heightQ
    },
    itemsQ: [item1, item2], // 把 items 赋值到这里
    titleQ = "Menu" // 在对象中不存在（使用默认值）
} = optionsQ;
console.log(titleQ);  // Menu
console.log(widthQ);  // 100
console.log(heightQ); // 200
console.log(item1);  // Cake
console.log(item2);  // Donut


// 解构赋值
console.log('--------------------------');
let user = {
    name: "John",
    years: 30
};
let {name, years: age, isAdmin = false} = user;
console.log( name ); // John
console.log( age ); // 30
console.log( isAdmin ); // false


// 最高薪资
// 新建一个函数 topSalary(salaries)，返回收入最高的人的姓名。
// 如果 salaries 是空的，函数应该返回 null。
// 如果有多个收入最高的人，返回其中任意一个即可。
// P.S. 使用 Object.entries 和解构语法来遍历键/值对。
console.log('--------------------------');
function topSalary(salaries) {
    let max = 0;
    let maxName = null;
    for(let [name, salary] of Object.entries(salaries)) {
        if (max < salary) {
            max = salary;
            maxName = name;
        }
    }
    return maxName;
}
let salaries = {
    "John": 100,
    "Pete": 300,
    "Mary": 250,
    "PPP": 300
};
console.log(topSalary(salaries));
