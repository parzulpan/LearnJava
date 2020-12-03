// 日期和时间
// 内建对象：日期（Date）。该对象存储日期和时间，并提供了日期/时间的管理方法。


// 创建
console.log('--------------------------');
// 不带参数 - 创建一个表示当前日期和时间的Date对象
let nowDate = new Date();
console.log(nowDate);
// 0 表示 01.01.1970 UTC+0
let Jan01_1970 = new Date(0);
console.log(Jan01_1970);
// 现在增加24小时，得到02.01.1970 UTC+0，传入的参数是毫秒数，该整数被称为时间戳
let Jan02_1970 = new Date(24 * 3600 * 1000);
console.log(Jan02_1970);
let dateString = new Date('2020-05-20');
console.log(dateString);
let dateJ = new Date(2020, 5,20, 5, 20, 5, 20);
console.log(dateJ);


// 访问日期组件
console.log('--------------------------');
console.log(nowDate);
console.log(nowDate.getFullYear());
console.log(nowDate.getMonth());
console.log(nowDate.getDate());
console.log(nowDate.getHours());
console.log(nowDate.getMinutes())
console.log(nowDate.getSeconds());
console.log(nowDate.getMilliseconds());
console.log(nowDate.getDay());  // 获取一周中的第几天，从 0（星期日）到 6（星期六）。第一天始终是星期日，在某些国家可能不是这样的习惯，但是这不能被改变。
console.log(nowDate.getTime()); // 返回日期的时间戳 —— 从 1970-1-1 00:00:00 UTC+0 开始到现在所经过的毫秒数
console.log(nowDate.getTimezoneOffset());   // 返回 UTC 与本地时区之间的时差，以分钟为单位


// 设置日期组件，同上将get改为set


// 日期转为数字，日期差值
// 日期可以相减，相减的结果是以毫秒为单位时间差。
console.log('--------------------------');
// let startTime = new Date();
let startTime = Date.now(); //  这样做更好
for(let i = 0; i < 100000; ++i) {
    let doSomething = i * i * i;
}
// let endTime = new Date();
let endTime = Date.now();
console.log(`The loop took ${endTime - startTime} ms.`);


// 对一个字符串使用Date.parse
// Date.parse(str)方法可以从一个字符串中读取日期
// 字符串的格式应该为：YYYY-MM-DDTHH:mm:ss.sssZ，其中：
// YYYY-MM-DD —— 日期：年-月-日。
// 字符 "T" 是一个分隔符。
// HH:mm:ss.sss —— 时间：小时，分钟，秒，毫秒。
// 可选字符 'Z' 为 +-hh:mm 格式的时区。单个字符 Z 代表 UTC+0 时区。
console.log('--------------------------');
let ms = Date.parse('2012-01-26T13:51:50.417-07:00');
console.log(ms); // 1327611110417  (时间戳)
let msDate = new Date(ms);
console.log(msDate);


// 创建时间
// 创建一个 Date 对象，日期是：Feb 20, 2012, 3:12am。时区是当地时区。
console.log('--------------------------');
let d = new Date(2012, 1, 20, 3, 12);
console.log( d );


// 显示星期数
// 编写一个函数 getWeekDay(date) 以短格式来显示一个日期的星期数：‘MO’，‘TU’，‘WE’，‘TH’，‘FR’，‘SA’，‘SU’。
console.log('--------------------------');
function getWeekDay(date) {
    // 星期日是0
    let days = ['SU', 'MO', 'TU', 'WE', 'TH', 'FR', 'SA'];
    return days[date.getDay()];
}
let date = new Date(2020, 5,20);
console.log(date);
console.log(getWeekDay(date));