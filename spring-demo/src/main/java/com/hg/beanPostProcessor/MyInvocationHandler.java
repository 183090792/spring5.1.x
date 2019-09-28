package com.hg.beanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {
	Object clazz;
	public MyInvocationHandler(Object clazz){
		this.clazz = clazz;
	}

	@Override
	public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
		System.out.println("proxy");
		return method.invoke(clazz,objects);
	}
}
