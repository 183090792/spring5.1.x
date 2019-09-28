package com.hg.beanPostProcessor;

import com.hg.dao.UserDao;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Proxy;

public class MyBeanPostProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		Object instance = null;

		if("userDaoImpl".equals(beanName)){
			instance = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{UserDao.class}, new MyInvocationHandler(bean));
		}
		return instance;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return null;
	}
}
