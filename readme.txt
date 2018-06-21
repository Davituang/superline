一、springboot创建多模块项目
    参考1：https://blog.csdn.net/liyanlei5858/article/details/79047884
    参考2：https://www.jb51.net/article/124707.htm

    思考：
    1.对于springboot创建多模块来说，在创建父模块时，可以直接创建springboot工程，但是请选择创建pom工程，因为父模块只进行模块的管理
    和jar版本的统一管理，创建的是springboot类的父工程时，很多jar已经被spring-boot-parent管理了，我们在父工程中都不用进行管理，我们需要
    管理的是一些没有被springboot管理的jar,比如dubbo。

    2.注意，子模块在指定父模块时不要指定到spring-boot-parent上去了。

    3.创建好子模块后，查看创建好的模块是否成功成为maven项目，如果不是，右键单击子模块pom.xml，选择add to maven project,在查看java包和
    resource包是否是功能包，不是，则单击file，选择project structure,选择modules,指定该模块下的jave为source,resource包为resource

    4.子模块指定的父工程后，在父工程的版本后添加<relativePath>../pom.xml</relativePath>

    5.入口程序所在的工程一定要导入springboot的web包，否则启动不起来。

    6.当导入了springboot整合的包，如redis,mybaits,activemq这些包时，需要完成对应的服务配置且正常可用才能正常启动项目，所以在进行整合时，尚未进行
    整合的包要么用的时候再导入，要么先注释起来，以免影响其他功能的开发与测试。强烈建议用到哪个导入哪个，正常运行之后再导入下一个，方便排查问题。



二、整合dubbo,有两种方式，一种为通过配置xml整合，一种为通过阿里提供的dubbo-spring-boot-starter整合
    xml整合参考：https://blog.csdn.net/liyanlei5858/article/details/79048245
    springbootstarter整合参考：https://github.com/alibaba/dubbo-spring-boot-starter/blob/master/README_zh.md

    说明，本demo中使用的是springbootstarter方式整合，这样可以不用编写xml文件，直接通过配置简单的属性加上注解即可使用，踩的坑
    1.在导入springbootstarter jar包时，注意版本，导入2.0.0版本的，应为测试其他版本暂时并没有测通，注意，该jar中已经依赖了dubbo jar,
    在项目中不必再引入dubbo jar，但需要引入注册中心jar。

    2.记得在入口程序上开启dubbo注解的支持

    3.在项目中出现项目既是服务提供方，又是服务消费方时，启动项目时，消费方默认会去对服务进行健康检查，如果服务未启动则报错，我们可以通过
    关闭接口健康检查来规避这个错误。

    4.当接口在dubbo中是唯一的时，消费方在注入接口时，@Reference注解后的括号内可以指定接口，也可以不指定。

    5.消费方在调用服务时务必记得引入该服务的二方包。

    6.消费方的属性配置文件与消费方的配置文件基本一致，除了应用名之外。

    7.如果涉及到对象在服务方与消费方之间的传递，该对象对应的实体类必须序列化，否则会传递失败，导致服务调用失败。

三、关于入口程序的注解后配置扫描包的问题
    1.这里有一个特性，入口程序只会扫描它的同级包及子包，为其中添加了注解的类创建对象并交由spring管理，所以在进行包结构设计时，应当规范统一，
    使需要被spring管理的类都在入口程序的扫描范围之内，设计良好的包结构，甚至可以不需要在入口程序的注解后面添加扫描器

四、使用内存型数据库H2
    参考文档：https://www.jb51.net/article/118092.htm
    1.导入内存型数据库依赖包
    2.在属性配置文件中配置内存型数据库参数，指定数据库类型，驱动，加载建表sql,数据数据sql
    3.属性配置文件说明:

        a. jdbc:h2:file:E:/data/H2 表示将初始化的数据和H2 Console控制台执行的数据保存到E盘下data/H2文件夹中，即使应用重启，数据不会丢失。

        b. jdbc:h2:~/testdatabase这里就需要说明一下”~”这个符号在window操作系统下代表什么意思了，在Window操作系统下，”~”这个符号代表的
        就是当前登录到操作系统的用户对应的用户目录，所以testdatabase数据库对应的文件存放在登录到操作系统的用户对应的用户目录当中，比如我当前
        是使用Administrator用户登录操作系统的，所以在”C:\Documents and Settings\Administrator\h2”目录中就可以找到test数据库对应的数据库
        文件了

        c.持久化本地的问题：由于本地已经存在表，而应用每次启动都会创建表，导致下次启动时会启动报错。除非手动注掉application.properties中新建表的配置，
        或则删除本地对应目录的文件。

        d.jdbc:h2:mem:soa_service_api、jdbc:h2:mem:~/.h2/url类似与这种配置的，表示将初始化和h2 console控制台上操作的数据保存在内存（mem-memory）
        保存到内存的问题：由于每次重启应用内存释放掉后，对应的数据也会消失，当然初始化的表+初始化数据就都没了。然后重启会从data.sql中重新初始化数据，
        启动正常。但是你通过h2 console操作的其他数据则全部丢失。解决办法是把在h2 console新添加的接口地址配置到data.sql中。然后重新启动才行。

五、关于属性配置文件的加载
    参考：https://blog.csdn.net/thc1987/article/details/78789426
    加载与注入
    1.在属性配置文件中，我们可以自定义属性，可以配置的属性有字符串型，数组型，map型（键值对），详情见工程案例
    2.springboot通过@PropertySource("文件路径")加载指定的文件，如果是application.properties则不用写，默认就是加载它
    3.通过@ConfiguratonProperties来实现属性与配置类字段的映射，推荐使用。
    4.对于字符串0类型的注入，可以通过@value直接注入

    获取
    1.通过在需要使用的地方注入属性配置文件获取
    2.通过注入环境对象获取
    3.通过直接注入获取@value直接注入值
    4.通过配置文件监听器加载和获取

六、表单校验的使用，我们使用springboot集成的Annotaion JSR-303标准的验证
    参考：https://blog.csdn.net/u012706811/article/details/51079740
    1.导入jar,可在创建工程时直接选用
    2.在需要校验的实体类的字段上加上注解和错误信息
    3.在controller方法形参中加入@validated 实体类 变量，BindingResult br
    4.调用br.hasErrors来判断是否有不符合要求的地方，可以获取到错误信息返回给前端。

七、springboot中使用aop
    参考：https://www.cnblogs.com/bigben0123/p/7779357.html
    1.导入aop jar包
    2.编写切面类
    3.在切面类上配置@Aspect，表示该类是个切面类
    4.添加空方法，配置切入点表达式
    5.在方法上添加注释，配置前置通知，后置通知，异常通知，最红通知，环绕通知等。

    常用注解说明
        1.@Aspect：作用是把当前类标识为一个切面供容器读取

        2.@Before：标识一个前置增强方法，相当于BeforeAdvice的功能

        3.@AfterReturning：后置增强，相当于AfterReturningAdvice，方法退出时执行

        4.@AfterThrowing：异常抛出增强，相当于ThrowsAdvice

        5.@After：final增强，不管是抛出异常或者正常退出都会执行

        6.@Around：环绕增强，相当于MethodInterceptor

    各方法参数说明：
        除了@Around外，每个方法里都可以加或者不加参数JoinPoint，如果有用JoinPoint的地方就加，不加也可以，
        JoinPoint里包含了类名、被切面的方法名，参数等属性，可供读取使用。
        @Around参数必须为ProceedingJoinPoint，pjp.proceed相应于执行被切面的方法。
        @AfterReturning方法里，可以加returning = “XXX”，XXX即为在controller里方法的返回值。
        @AfterThrowing方法里，可以加throwing = "XXX"，供读取异常信息。

八、springboot中使用事物管理
    1.引入jar，在本工程中，由于使用的是mybatis-starter,在这个整合包中已经依赖了事物，所以不用再次引入
    2.在springboot启动类上添加@EnableTransactionManagement,开启事物
    3.在需要事物控制的业务层的接口/类/方法上添加@Transactional注解，并制定事物传播行为，是否只读，隔离级别等

九、整合mybaits
    1.引入jar,推荐在创建springboot工程时就选择好依赖mybatis，在未开发时现将其注释掉
    2.在springboot启动类上添加@MapperScan("path"),指定mybaits的dao的mapper接口的包路径，如果这里不配置，也可以在每个mapper接口上添加@Mapper注解
    实现的效果是一样的，两者必须配置其中一个，否则报错。
    3.在属性配置文件中指定mapper.xml文件位置，以及配置别名，同时可以开启驼峰命名法
        mybatis.mapper-locations= classpath:com.heiyo.superline.dao.mapper/*.xml
        mybatis.type-aliases-package=com.heiyo.superline.domain
        mybatis.configuration.map-underscore-to-camel-case=true

十、整合redis
    1.引入jar,推荐在创建springboot工程时就选择好redis依赖，在未开发时将其注释掉
    2.填写属性配置文件
    3.新建redis配置类，创建字符串操作模板和对象操作模板
    4.对象操作模板的创建依赖redis序列化策略，手动实现redis序列化策略，并注入对象操作模板实例
    5.基本配置完成，在需要使用redis存储的地方注入这两个操作模板，调用方法进行存取删

    注意事项
    1.正确理解redis存储方式，redis存储方式共5中，分别为string,list,set,sortedset,hash
    2.除string方式外，其余存取方式都可以存取单个实例，实例数组，实例集合，这里的实例可以是string实例也可以是自定义类实例。
    3.两个操作模板不能同时往一个key中存取数据，否则会导致读取数据解析失败。