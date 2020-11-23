package com.parzulpan.exer;

/**
 * @Author : parzulpan
 * @Time : 2020-11-23
 * @Desc : 线程通信应用：生产者/消费者问题
 * 生产者(Producer)将产品交给店员(Clerk)，而消费者(Customer)从店员处取走产品，店员一次只能持有固定数量的产品(比如:20），
 * 如果生产者试图生产更多的产品，店员会叫生产者停一下，如果店中有空位放产品了再通知生产者继续生产；
 * 如果店中没有产品了，店员会告诉消费者等一下，如果店中有产品了再通知消费者来取走产品。
 * 1. 是否是多线程问题？ 是，生产者线程、消费者线程
 * 2. 是否共享数据？ 是，店员（产品）
 * 3. 是否有安全问题，如果有，如何解决？ 有，使用同步机制，有三种方式。
 * 4. 是否有线程通信？是
 */

public class ProductTest {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();

        Producer producer = new Producer(clerk);
        Consumer consumer = new Consumer(clerk);
        Thread thread1 = new Thread(producer);
        Thread thread2 = new Thread(consumer);
        Thread thread3 = new Thread(consumer);
        thread1.setName("生产者");
        thread2.setName("消费者1");
        thread3.setName("消费者2");
        thread1.start();
        thread2.start();
        thread3.start();

    }
}

// 售货员
class Clerk {
    private int product;

    public synchronized void addProduct() {
        if (product >= 20) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            ++product;
            System.out.println(Thread.currentThread().getName() + " 生产了第 " + product + " 个产品");
            notifyAll();
        }

    }

    public synchronized void removeProduct() {
        if (product <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " 消费了第 " + product + " 个产品");
            --product;
            notifyAll();
        }

    }

}

// 生产者
class Producer implements Runnable {
    private Clerk clerk;

    Producer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " 开始生产产品");
        while (true) {
            try {
                Thread.sleep((long) (Math.random() * 500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.clerk.addProduct();
        }
    }
}

// 消费者
class Consumer implements Runnable {
    private Clerk clerk;

    Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " 开始消费产品");
        while (true) {
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.clerk.removeProduct();
        }
    }
}