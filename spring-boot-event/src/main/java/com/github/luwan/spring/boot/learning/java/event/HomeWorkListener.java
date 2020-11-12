package com.github.luwan.spring.boot.learning.java.event;

import java.util.EventListener;

public interface HomeWorkListener extends EventListener {

    public void update(HomeworkEventObject o, Object arg);

}
