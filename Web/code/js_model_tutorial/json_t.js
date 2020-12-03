// JSON方法，toJSON

// JSON(JavaScript Object Notation) 是表示值和对象的通用格式
// 1. JSON.stringify 将对象转换为JSON
// 2. JSON.parse 将JSON转换会对象
console.log('--------------------------');
let student = {
    name: 'PPP',
    age: 30,
    isAdmin: false,
    courses: ['html', 'css', 'js'],
    wife: null
}
let json = JSON.stringify(student);
console.log(typeof json);
console.log(json);
// JSON编码的对象和对象字面量有几个重要的区别
// 1. 字符串使用双引号。JSON中没有单引号或者反引号。
// 2. 对象属性名称也是双引号的。这是强制性的。

// JSON.stringify 也可以应用于原始数据类型
// JSON支持一下数据类型：
// 1. Objects {...}
// 2. Arrays [...]
// 3. Primitives:
//    strings
//    numbers
//    boolean values true/false
//    null
// JSON是语言无关的纯数据规范，因此一些特定于JS的对象属性会被JSON.stringify跳过，即函数属性(方法)、Symbol类型的属性、存储undefined的属性
console.log('--------------------------');
let user = {
    sayHi() { // 被忽略
        alert("Hello");
    },
    [Symbol("id")]: 123, // 被忽略
    something: undefined // 被忽略
};
console.log( JSON.stringify(user) ); // {}（空对象）


// 支持嵌套对象转换，并且可以自动对其进行转换
console.log('--------------------------');
let meetup = {
    title: "Conference",
    room: {
        number: 23,
        participants: ["john", "ann"]
    }
};
console.log( JSON.stringify(meetup) );
/* 整个解构都被字符串化了
{
  "title":"Conference",
  "room":{"number":23,"participants":["john","ann"]},
}
*/


// JSON一个重要的限制，不得有循环引用
let room = {
    number: 23
};
let meetupA = {
    title: "Conference",
    participants: ["john", "ann"]
};
meetupA.place = room;       // meetup 引用了 room
room.occupiedBy = meetupA; // room 引用了 meetup
// console.log(JSON.stringify(meetupA)); // Error: Converting circular structure to JSON


// 排除和转换：replacer
// JSON.stringify 的完整语法是：let json = JSON.stringify(value[, replacer, space])
// value：要编码的值 replacer：要编码的属性数组或映射函数function(key, value) space：用于格式化的空格数量
// 为了滤掉循环引用，就可以使用 JSON.stringify 的第二个参数。
console.log('--------------------------');
let roomB = {
    number: 24
};
let meetupB = {
    title: "GOOD JS",
    participants: [{name: "PPP"}, {name: "XXX"}],
    place: roomB    // meetupB引用了roomB
};
roomB.occupiedBy = meetupB; // roomB引用了meetupB
console.log(JSON.stringify(meetupB, ['title', 'participants', 'place', 'name', 'number'])); // 过滤掉occupiedBy
// 但是上面的列表有时会很长，所以可以用一个函数代替
console.log(JSON.stringify(meetupB, function(key, value) {
    return (key === 'occupiedBy') ? undefined : value;
}));


// 格式化：space
// spaces 参数仅用于日志记录和美化输出。
console.log('--------------------------');
let userS = {
    name: "John",
    age: 25,
    roles: {
        isAdmin: false,
        isEditor: true
    }
};
console.log(JSON.stringify(userS, null, 2));
/* 两个空格的缩进：
{
  "name": "John",
  "age": 25,
  "roles": {
    "isAdmin": false,
    "isEditor": true
  }
}
*/
console.log(JSON.stringify(userS, null, 4));
/* 四个空格的缩进：
{
    "name": "John",
    "age": 25,
    "roles": {
        "isAdmin": false,
        "isEditor": true
    }
}
*/


// 自定义 "toJSON"
// 像 toString 进行字符串转换，对象也可以提供 toJSON 方法来进行 JSON 转换。如果可用，JSON.stringify 会自动调用它。
console.log('--------------------------');
let roomTS = {
    number: 23,
    // 添加一个自定义的 toJSON
    toJSON() {
        return this.number;
    }
};

let meetupTS = {
    title: "Conference",
    date: new Date(Date.UTC(2017, 0, 1)),
    roomTS
};
console.log( JSON.stringify(meetupTS) );
/*
  {
    "title":"Conference",
    "date":"2017-01-01T00:00:00.000Z",  // (1)
    "room": {"number":23}               // (2)
    "room": 23                          // (2)
  }
*/


// JSON.parse
// 解码JSON字符串 语法：let value = JSON.parse(str, [reviver]);
// str：要解析的JSON字符串 reviver：可选的函数function(key, value)，该函数将为每个(key, value)对调用，并可以对值进行转换
// 字符串化数组
console.log('--------------------------');
let numbers = "[0, 1, 2, 3]";
numbers = JSON.parse(numbers);
console.log( numbers[1] ); // 1
// 对于嵌套对象
let userData = '{ "name": "John", "age": 35, "isAdmin": false, "friends": [0,1,2,3] }';
let userQ = JSON.parse(userData);
console.log( userQ.friends[1] ); // 1


// 使用reviver
console.log('--------------------------');
let str = '{"title":"Conference","date":"2017-11-30T12:00:00.000Z"}';
let meetupR = JSON.parse(str);
// console.log( meetupR.date.getDate() ); // Error! meetupR.date.getDate is not a function
// 分析：meetup.date 的值是一个字符串，而不是 Date 对象。JSON.parse 怎么知道应该将字符串转换为 Date 呢？
let meetupRR = JSON.parse(str, function(key, value) {
    if(key === 'date') return new Date(value);
    return value;
})
console.log( meetupRR.date.getDate() );


// 将对象转换为JSON，然后再转换回来
console.log('--------------------------');
let userTT = {
    name: "John Smith",
    age: 35
};
let user2 = JSON.parse(JSON.stringify(userTT));
console.log(user2);


// 排除反向引用
// 编写 replacer 函数，移除引用 meetup 的属性，并将其他所有属性序列化：
console.log('--------------------------');
let roomP = {
    number: 23
};

let meetupP = {
    title: "Conference",
    occupiedBy: [{name: "John"}, {name: "Alice"}],
    place: roomP
};

roomP.occupiedBy = meetupP;
meetupP.self = meetupP;

console.log( JSON.stringify(meetup, function replacer(key, value) {
    return (key !== "" && value === meetupP) ? undefined : value;
}, space=2));

/*
{
  "title":"Conference",
  "occupiedBy":[{"name":"John"},{"name":"Alice"}],
  "place":{"number":23}
}
*/