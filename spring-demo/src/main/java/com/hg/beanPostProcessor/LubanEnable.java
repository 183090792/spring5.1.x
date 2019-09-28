package com.hg.beanPostProcessor;

import com.hg.selector.MySelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(MySelector.class)
public @interface LubanEnable {
}
