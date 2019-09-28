package com.hg.config;


import com.hg.beanPostProcessor.LubanEnable;
import com.hg.beanPostProcessor.MyBeanPostProcessor;
import com.hg.dao.ImportSelectorDao;
import com.hg.dao.UserDao;
import com.hg.importBean.MyImportBeanDefinitionRegistrar;
import com.hg.selector.MySelector;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@LubanEnable
@ComponentScan("com.hg")
public class App {

}
