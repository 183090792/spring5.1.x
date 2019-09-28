package com.hg.test;

import com.hg.config.App;
import com.hg.dao.ImportSelectorDao;
import com.hg.dao.UserDao;
import com.hg.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;

public class Test {
	public static void main(String[] args) {
		// 准备工厂    DefaultListableBeanFactory
		// 实例化一个bdReader 和一个scanner
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		applicationContext.register(App.class);
		applicationContext.refresh();
		// 完成了扫描并不是 AnnotationConfigApplicationContext 里面的 scanner
//		UserService userService = (UserService) applicationContext.getBean("userService");
//		ImportSelectorDao userService1 = (ImportSelectorDao) applicationContext.getBean("com.hg.dao.ImportSelectorDao");
		UserDao userDao = (UserDao) applicationContext.getBean("userDaoImpl");
//		System.out.println(userService.hashCode() + "===============" + userService1.hashCode());
		System.out.println(userDao+"==============");
		userDao.query();
//		userService.query();
	}
}
