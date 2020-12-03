// 属性的getter和setter


// 有两种类型的属性
// 1. 数据属性，目前使用的所有属性都是数据属性
// 2. 访问器属性，本质上是用于获取和设置值的函数，但从外部代码来看就像常规属性


// getter 和 setter
// 访问器属性由"getter"和"setter"方法表示，在对象字面量中，它们用get和set表示
let obj = {
    get propName() {
        // 当读取 obj.propName 时，getter 起作用
    },

    set propName(value) {
        // 当执行 obj.propName = value 操作时，setter 起作用
    }
};


// 现在有一个具有 name 和 surname 属性的对象 user
let user = {
    name: 'parzul',
    surname: 'pan',
};
// 现在想添加一个 fullName 属性，该属性值应该为 "parzul pan"
console.log('--------------------------');
let user1 = {
    name: 'parzul',
    surname: 'pan',

    get fullName() {
        return `${this.name} ${this.surname}`;
    },

    // 赋值，"虚拟"属性，且可读可写
    set fullName(value) {
        [this.name, this.surname] = value.split(" ");
    },
};
console.log(user1.fullName);
user1.fullName = "Big Master";
console.log(user1.fullName);


// 访问器描述符
// 访问器描述符与数据属性不同，对于它，没有value和writable，但是有get 和 set 函数
// 使用 defineProperty 创建一个 fullName 访问器
console.log('--------------------------');
let user2 = {
    name: "John",
    surname: "Smith"
};
Object.defineProperty(user2, 'fullName', {
    get() {
        return `${this.name} ${this.surname}`;
    },

    set(value) {
        [this.name, this.surname] = value.split(" ");
    }
});
console.log(user2.fullName); // John Smith
for(let key in user2) console.log(key); // name, surname
// 注意：一个属性要么是访问器(具有get/set方法)，要么是数据属性(具有value)，但不能两者都是


// 更聪明的getter/setter
// getter/setter可以用作"真实"属性值的包装器，以便对它们进行更多的控制
// 如果想禁止太短的 user 的 name，可以创建一个 setter name，并将值存储在一个单独的属性 _name 中
console.log('--------------------------');
let user3 = {
    get name() {
        return this._name;
    },

    set name(value) {
        if (value.length < 4) {
            console.log("Name is too short, need at least 4 characters");
            return;
        }
        this._name = value;
    }
};

user3.name = "HeiH";
console.log(user3.name);
user3.name = "X";


// 兼容性
// 访问器一大用途是，它们允许随时通过使用 getter 和 setter 替换"正常的"数据属性，来控制和调整这些属性的行为
console.log('--------------------------');
function User(name, birthday) {
    this.name = name;
    this.birthday = birthday;

    // 年龄是根据当前日期和生日计算得出的
    Object.defineProperty(this, "age", {
        get() {
            let todayYear = new Date().getFullYear();
            return todayYear - this.birthday.getFullYear();
        }
    });
}
let john = new User("John", new Date(1992, 6, 1));
console.log( john.birthday ); // birthday 是可访问的
console.log( john.age );      // ……age 也是可访问的




