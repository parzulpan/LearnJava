// Mixin模式


// 在JS中，只能继承单个对象，每个对象只能有一个[[Prototype]]，并且每个类只可以拓展另外一个类
// 百科定义，mixin是一个包含可被其他类使用而无需继承的方法的类


// 在JS构造一个mixin最简单的方式就是构造一个拥有使用方法的对象
// mixin
console.log('--------------------------');
let sayHiMixin = {
    sayHi() {
        console.log(`Hello ${this.name}`);
    },
    sayBye() {
        console.log(`Bye ${this.name}`);
    }
};
// 用法
class User {
    constructor(name) {
        this.name = name;
    }

}
// 拷贝方法
Object.assign(User.prototype, sayHiMixin);
new User("pro").sayHi();    // Hello pro


// EventMixin
// 许多浏览器对象的一个重要功能是它们可以生成事件，事件是向任何有需要的人"广播信息"的好方法
console.log('--------------------------');
let eventMixin = {
    /*
    订阅事件，
    用法：menu.on('select', function(item) { ... }
     */
    on(eventName, handler) {
        if(!this._eventHandlers) {
            this._eventHandlers = {};
        }
        if(!this._eventHandlers[eventName]) {
            this._eventHandlers[eventName] = [];
        }
        this._eventHandlers[eventName].push(handler);
    },

    /*
    取消订阅
    用法：menu.off('select', handler)
     */
    off(eventName, handler) {
        let handlers = this._eventHandlers && this._eventHandlers[eventName];
        if( !handlers ) {
            return;
        }
        for(let i = 0; i < handlers.length; ++i) {
            if( handlers[i] === handler ) {
                handlers.splice(i--, 1);
            }
        }
    },

    /*
    生成具有给定名称和数据的事件
    用法：this.trigger('select', data1, data2)
     */
    trigger(eventName, ...args) {
        if( !this._eventHandlers || !this._eventHandlers[eventName]) {
            // 该事件名称没有对应的事件处理程序(handler)
            return;
        }
        // 调用事件处理程序(handler)
        this._eventHandlers[eventName].forEach(handler => handler.apply(this, args));
    },

};

// 使用
class Menu {
    choose(value) {
        this.trigger("select", value);
    }
}
// 添加带有事件相关方法的mixin
Object.assign(Menu.prototype, eventMixin);
let menu = new Menu();
// 添加一个事件处理程序(handler)，在被选择时被调用
menu.on('select', value => console.log(`value selected ${value}`));
// 触发事件 => 运行上述的事件处理程序(handler)并显示
menu.choose("123");
menu.off();
menu.choose("321");