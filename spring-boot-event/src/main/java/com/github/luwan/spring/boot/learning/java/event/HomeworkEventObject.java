package com.github.luwan.spring.boot.learning.java.event;

import java.util.EventObject;

public class HomeworkEventObject extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public HomeworkEventObject(Object source) {
        super(source);
    }

    public HomeworkEventObject(Teacher teacher) {
        super(teacher);
    }

    public Teacher getTeacher(){
        return (Teacher) super.getSource();
    }
}
