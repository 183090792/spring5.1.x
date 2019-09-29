# <img src="src/docs/asciidoc/images/spring-framework.png" width="80" height="80"> Spring Framework
**spring的后置处理器**

BeanPostProcessor:
    
   postProcessBeforeInitialization & postProcessAfterInitialization :
   
    在目标对象被实例化之后，并且属性也被设置之后调用的

InstantiationAwareBeanPostProcessor：

    InstantiationAwareBeanPostProcessor接口继承BeanPostProcessor接口，它内部提供了3个方法，再加上BeanPostProcessor接口内部的2个方法，所以实现这个接口需要实现5个方法。InstantiationAwareBeanPostProcessor接口的主要作用在于目标对象的实例化过程中需要处理的事情，包括实例化对象的前后过程以及实例的属性设置
    
    在org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#createBean()方法的Object bean = resolveBeforeInstantiation(beanName, mbdToUse);方法里面执行了这个后置处理器
    
    
   postProcessBeforeInstantiation:
   
    在目标对象实例化之前调用，方法的返回值类型是Object，我们可以返回任何类型的值。由于这个时候目标对象还未实例化，所以这个返回值可以用来代替原本该生成的目标对象的实例(一般都是代理对象)。如果该方法的返回值代替原本该生成的目标对象，后续只有postProcessAfterInitialization方法会调用，其它方法不再调用；否则按照正常的流程走
   
   postProcessAfterInstantiation:
   
    方法在目标对象实例化之后调用，这个时候对象已经被实例化，但是该实例的属性还未被设置，都是null。如果该方法返回false，会忽略属性值的设置；如果返回true，会按照正常流程设置属性值。方法不管postProcessBeforeInstantiation方法的返回值是什么都会执行
    
   postProcessPropertyValues:
   
    方法对属性值进行修改(这个时候属性值还未被设置，但是我们可以修改原本该设置进去的属性值)。如果postProcessAfterInstantiation方法返回false，该方法不会被调用。可以在该方法内对属性值进行修改
    
   postProcessBeforeInitialization & postProcessAfterInitialization:
    
     父接口BeanPostProcessor的2个方法postProcessBeforeInitialization和postProcessAfterInitialization都是在目标对象被实例化之后，并且属性也被设置之后调用的
     
SmartInstantiationAwareBeanPostProcessor:
    
    智能实例化Bean后置处理器（继承InstantiationAwareBeanPostProcessor）

   determineCandidateConstructors:
   
    检测Bean的构造器，可以检测出多个候选构造器
    
   getEarlyBeanReference:
    
    循环引用的后置处理器，这个东西比较复杂， 获得提前暴露的bean引用。主要用于解决循环引用的问题，只有单例对象才会调用此方法
   
   predictBeanType:
   
    预测bean的类型
    
MergedBeanDefinitionPostProcessor:
    
   postProcessMergedBeanDefinition:
    
    缓存bean的注入信息的后置处理器，仅仅是缓存或者干脆叫做查找更加合适，没有完成注入，注入是另外一个后置处理器的作用

**spring生命周期**

1、在 new AnnotationConfigApplicationContext() 时会调用父类 GenericApplicationContext 的构造方法初始化工厂，beanFactory当中有许多很重要额属性，比如单例池，

2、AnnotationConfigApplicationContext 的无参构造方法里面，往 beanDefinitionMap 中注册很多 beanDefinition（spring自己的是五个，其中最重要的一个就是ConfigurationClassPostProcessor，这个类是spring当中最重要的类之一）

3、实例化一些功能性对象 AnnotatedBeanDefinitionReader、ClassPathBeanDefinitionScanner

4、register(annotatedClasses) 注册配置类，这里仅仅注册了一个 配置类到 beanDefinitionMap 当中，方便后面实例化这个配置类

5、refresh() 方法里面有12个方法，重要的是三个方法  invokeBeanFactoryPostProcessors(beanFactory)、registerBeanPostProcessors(beanFactory)、finishBeanFactoryInitialization(beanFactory)

6、invokeBeanFactoryPostProcessors(beanFactory) 方法执行用户和spring提供的 BeanDefinitionRegistryPostProcessor 和 BeanFactoryPostProcessor 的实现类， BeanDefinitionRegistryPostProcessor --> ConfigurationClassPostProcessor.postProcessBeanDefinitionRegistry();主要做类的扫描(扫描包含普通类扫描，也包含@Bean的扫描)和类的解析成 beanDefinition 对象，比如  Import 的解析也在这个方法里面， import 分为三种类型，分别是：普通类、ImportSelector、ImportBeanDefinitionRegistrar。BeanFactoryPostProcessor--> ConfigurationClassPostProcessor.postProcessBeanFactory(),判断我们的配置类是不是全配置类 full，如果是 full 需要给配置类加上cglib代理

7、registerBeanPostProcessors(beanFactory) 方法注册spring当中的后置处理器，包括自己添加的扩展后置处理器 

8、finishBeanFactoryInitialization(beanFactory) 初始化单例bean，这个里面就是 spring bean 的生命周期

8.1、getBean(beanName) --> createBean()--> resolveBeforeInstantiation() (备注：第一次应用 spring 的后置处理器) --> InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation() (备注：如果这个方法返回了对象，则只会执行 BeanPostProcessor.postProcessAfterInitialization() )

8.2、doCreateBean() --> createBeanInstance()(备注: 方法的返回值就是 new 出来的对象，但是对象的属性未设置) --> determineConstructorsFromBeanPostProcessors() (备注：第二次执行后置处理器,推断构造方法) --> SmartInstantiationAwareBeanPostProcessor.determineCandidateConstructors()

8.3、applyMergedBeanDefinitionPostProcessors() (备注: 第三次执行后置处理器) --> MergedBeanDefinitionPostProcessor.postProcessMergedBeanDefinition() (备注: 找出并缓存对象的注解信息，主要是自动注入)

8.4、addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, mbd, bean)) (备注: 第四次执行后置处理器,把ObjectFactory对象(他包含了访问该对象的API)缓存起来) --> SmartInstantiationAwareBeanPostProcessor.getEarlyBeanReference() (备注: 主要是解决循环依赖)

8.5、populateBean() (备注: 第五次执行后置处理器,为 spring bean 填充属性、自动注入、这个方法是 spring 中相当重要的方法) --> InstantiationAwareBeanPostProcessor.postProcessAfterInstantiation() (备注： 判断对象是否需要填充属性)  和 InstantiationAwareBeanPostProcessor.postProcessPropertyValues() (备注: 第六次执行后置处理器,完成装配，即完成属性的注入,也就是大家常说的自动注入)

8.6、initializeBean() --> applyBeanPostProcessorsBeforeInitialization() --> BeanPostProcessor.postProcessBeforeInitialization() (备注:第七次执行后置处理器,在这里将原生对象变为代理对象，执行后置处理器的 before 方法，在springBean init() 之前做处理 )

8.7、invokeInitMethods() --> 执行 springBean的生命周期方法(init) 

8.8、 applyBeanPostProcessorsAfterInitialization() --> BeanPostProcessor.postProcessAfterInitialization() (备注:第八次执行后置处理器,在这里将原生对象变为代理对象，执行后置处理器的 after 方法，在springBean init() 之后做处理 )

