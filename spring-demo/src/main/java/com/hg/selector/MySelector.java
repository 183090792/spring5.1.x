package com.hg.selector;

import com.hg.beanPostProcessor.MyBeanPostProcessor;
import com.hg.dao.ImportSelectorDao;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MySelector implements ImportSelector {
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[]{MyBeanPostProcessor.class.getName()};
//		return new String[]{ImportSelectorDao.class.getName()};
	}
}
