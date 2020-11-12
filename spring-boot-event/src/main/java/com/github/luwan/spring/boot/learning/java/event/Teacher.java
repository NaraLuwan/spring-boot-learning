package com.github.luwan.spring.boot.learning.java.event;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Teacher {

    private String name;

    private List<String> homeworks;
    /*
     * 教师类要维护一个自己监听器（学生）的列表，为什么？
     * 在观察者模式中，教师是被观察者，继承自java.util.Observable，Observable中含了这个列表
     * 现在我们没有这个列表了，所以要自己创建一个
     */
    private Set<HomeWorkListener> homeworkListenerList;

    public String getName() {
        return this.name;
    }

    public Teacher(String name) {
        this.name = name;
        this.homeworks = new ArrayList<String>();
        this.homeworkListenerList = new HashSet<HomeWorkListener>();
    }

    public void setHomework(String homework) {
        System.out.printf("%s布置了作业%s \n", this.name, homework);
        homeworks.add(homework);
        HomeworkEventObject event = new HomeworkEventObject(this);
        /*
         * 在观察者模式中，我们直接调用Observable的notifyObservers来通知被观察者
         * 现在我们只能自己通知了~~
         */
        for (HomeWorkListener listener : homeworkListenerList) {
            listener.update(event, homework);
        }

    }
    public void addObserver(HomeWorkListener homeworkListener){
        homeworkListenerList.add(homeworkListener);
    }

}
