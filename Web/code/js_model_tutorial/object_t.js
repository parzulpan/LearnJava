"use strict";

let nameAge = {
    name: "PPP",
    age: 30,
}
// alert(nameAge.name);
// alert(nameAge.age);

nameAge.isAdmin = true;
// alert(nameAge.isAdmin)

delete nameAge.age;
console.log(nameAge)

nameAge["likes birds"] = true;
console.log(nameAge)

console.log( nameAge.age === undefined)

console.log("age" in nameAge)
console.log("isAdmin" in nameAge)

console.log("\n")
for(let key in nameAge) {
    console.log(key);
    console.log(nameAge[key]);
}

let copyNameAge = nameAge;
copyNameAge.age = 400;
console.log(nameAge.age);
console.log(copyNameAge.age);

const user = {
    name: "JJJ",
}
user.age = 25;
console.log(user.age);
// // error!
// user = {
//     name: "lff",
// }

let permissions1 = {canView: true};
let permissions2 = {canEdit: true};
// 对象属性拷贝
Object.assign(user, permissions1, permissions2)
console.log(user)
