package com.shixing.studycode.modedesign;

public class SingleInstance {
    private volatile static SingleInstance sSingleInstance;

    private SingleInstance() {

    }

    /**
     * 最简单的单例模式,不适用于多线程访问
     * @return
     */
    public static SingleInstance getSingleInstance1() {
        if (sSingleInstance == null) {
            sSingleInstance = new SingleInstance();
        }

        return sSingleInstance;
    }

    /**
     * double-check(DCL) 下述代码近乎完美，可以满足几乎所有场合（采用反射和类加载器另当别论）。上述代码的好处在于：第一次创建实例的时候会同步所有线程，
     * 以后有线程再想获取Singleton的实例就不需要进行同步
     * ，直接返回实例即可。还有double-check的意义在于：假设现在有2个线程A和B同时进入了getInstance方法，线程A执行到line
     * A行，线程B执行到line
     * B行，由于B线程还没有初始化完毕，sSingleton还是null，于是线程A通过了sSingleton==null的判断
     * ，并且往下执行，碰巧，当线程A执行到line
     * C的时候，线程B初始化完毕了，然后线程B返回，注意，如果没有double-check，这个时候线程A就执行到了line
     * B，就会再次初始化sSingleton
     * ，这个时候Singleton实际上被new了两次，已经不算完全意义上的单例了，而有了double-check，
     * 就会再进行一次为null的判断，由于B线程已经初始化了sSingleton，所以A线程就不会再次初始化sSingleton。
     * @return
     */
    public static SingleInstance getSingleInstance2() {
        if (sSingleInstance == null) {
            synchronized (SingleInstance.class) {
                if (sSingleInstance == null) {
                    sSingleInstance = new SingleInstance();
                }

            }
        }

        return sSingleInstance;
    }

    /**
     * double-check(DCL)在很大程度上可以满足高并发的需要，尽管如此，它还是有一些小缺点的，
     * 问题的关键在于尽管得到了Singleton的正确引用
     * ，但是却有可能访问到其成员变量的不正确值，这听起来有点抽象，不过没关系，我们只是需要有个感性的认识就好，如果你真的好奇，那么请搜索“java
     * happen-before”。既然DCL单例模式以中彩票的概率存在一些小问题，那么有没有所谓的完美的解决方案呢？答案是有，在给出之前，我要说的是：
     * 单例有很多种写法，我们不能简单地否定其他写法，尽管它们看起来不能很好地处理高并发情况，也许它们本来就是用于低并发情形下的。
     * 下面请看一种所谓完美应用于高并发情形下的单例写法，静态内部类单例模式：
     * @author shixing
     */
    private static class InstanceHolder {
        private static final SingleInstance instance = new SingleInstance();
    }

    public static SingleInstance getInstance3() {
        return InstanceHolder.instance;
    }

}
