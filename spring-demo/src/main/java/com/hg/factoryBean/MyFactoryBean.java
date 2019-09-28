package com.hg.factoryBean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class MyFactoryBean implements FactoryBean, InvocationHandler {
	Class clazz;

	public MyFactoryBean(Class clazz){
		this.clazz=clazz;
	}
	@Override
	public Object getObject() throws Exception {
		Class[] classes = new Class[]{clazz};
		Object proxyInstance = Proxy.newProxyInstance(this.clazz.getClassLoader(), classes, this);
		return proxyInstance;
	}

	@Override
	public Class<?> getObjectType() {
		return clazz;
	}

	@Override
	public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
		System.out.println("MyFactoryBean");
		return null;
	}
}
