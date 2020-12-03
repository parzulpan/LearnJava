"use strict";

let user = {
    name: 'PPP',
    age: 30,
};
user.sayHello = function() {
    alert('Hello');
};
user.sayHello()

let user1 = {
    name: 'XXX',
    age: 20,
    sayHello: function() {
        alert(`hello, ${this.name}`);
    }
};
user1.sayHello();



