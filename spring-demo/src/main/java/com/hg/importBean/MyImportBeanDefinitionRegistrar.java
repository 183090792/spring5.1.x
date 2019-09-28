package com.hg.importBean;

import com.hg.beanPostProcessor.MyBeanPostProcessor;
import com.hg.dao.UserDao;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(UserDao.class);
//		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MyBeanPostProcessor.class);
		GenericBeanDefinition beanDefinition = (GenericBeanDefinition) builder.getBeanDefinition();
		System.out.println(beanDefinition.getBeanClassName());
		beanDefinition.getConstructorArgumentValues().addGenericArgumentValue( "com.hg.dao.UserDao");
		beanDefinition.setBeanClass(MyImportBeanDefinitionRegistrar.class);
		registry.registerBeanDefinition("userDao",beanDefinition);
	}
}
