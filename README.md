# Spring4 整合 Hibernate3
[TOC]

## 步骤 1 ：导入 maven 依赖
1、Spring4 的模块
spring-core、spring-context、spring-beans、spring-jdbc、spring-orm、spring-tx、spring-test
2、mysql 驱动
mysql-connector-java
3、hibernate3
```
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-core</artifactId>
    <version>3.6.10.Final</version>
</dependency>
```
4、Druid 数据源
druid
5、javassist
```
<dependency>
    <groupId>org.javassist</groupId>
    <artifactId>javassist</artifactId>
    <version>3.20.0-GA</version>
</dependency>
```
6、aspectjweaver
```
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.8.7</version>
</dependency>
```
## 步骤 2 ：编写 beans 核心配置文件
0、基本配置
```
<context:component-scan base-package="com.liwei.dao"/>
```

1、配置数据源
```
<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" destroy-method="close">
        <property name="username" value="root"></property>
        <property name="password" value="123456"></property>
        <property name="url" value="jdbc:mysql://127.0.0.1:3306/spring_hibernate"></property>
        <property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
    </bean>
```

2、配置 SessionFactory
```
<bean id="sessionFactory" class="org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean">
    <property name="dataSource" ref="dataSource"></property>
    <property name="packagesToScan">
        <value>com.liwei.model</value>
    </property>
    <property name="hibernateProperties">
        <props>
            <prop key="hibernate.dialect">org.hibernate.dialect.MySQLDialect</prop>
            <prop key="hibernate.show_sql">true</prop>
            <prop key="hibernate.hbm2ddl.auto">update</prop>
            <prop key="hibernate.format_sql">false</prop>
        </props>
    </property>
</bean>
```
注意：我们集成 Hibernate3 使用的类是 `org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean`。
3、配置和事务相关
配置 Spring 事务处理，**只有配置了事务处理之后， Spring 才能有效的管理事务**。
（1）配置事务管理器
```
<bean id="txManager" class="org.springframework.orm.hibernate3.HibernateTransactionManager">
    <property name="sessionFactory" ref="sessionFactory"/>
</bean>
```
（2）配置事务通知
```
<!-- 配置 AOP ，Spring 是通过 AOP 来进行事务管理的 -->
<aop:config>
    <!-- 设置 pointCut 表示哪些方法要加入事务处理 -->
    <aop:pointcut id="allMethods" expression="execution(* com.liwei.dao.*.*(..))"/>
    <!-- 通过 advisor 来确定具体要加入事务控制的方法 -->
    <aop:advisor advice-ref="txAdvice" pointcut-ref="allMethods"/>
</aop:config>

<!-- 配置哪些方法要加入事务控制 -->
<tx:advice id="txAdvice" transaction-manager="txManager">
    <tx:attributes>
        <!-- 让所有的方法都加入事务管理 -->
        <tx:method name="*" propagation="REQUIRED"/>
    </tx:attributes>
</tx:advice>
```
## 步骤 3：编写实体类
为实体类添加 Hibernate 的 Annotation 或者 hbm 文件。
并且添加标准化的 JPA 注解。
```
@Table(name = "t_group")
@Entity
public class Group {
    private Integer id;
    private String name;

    @GeneratedValue
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

## 步骤4：编写基于 Hibernate 的 DAO 层

注意：我们这里 Hibernate3 持久化的操作是通过依赖注入了 `SessionFactory` 来完成的。所以我们在每个 DAO 层都要依赖注入 `SessionFactory` ，通过 `SessionFactory` 的 `getCurrentSession()` 方法来完成对实体类的操作。

即 1、在相应的 DAO 中注入相应的 `SessionFactory`（补充说明 `SessionFactory` 有很多种，根据集成的持久化框架和版本所决定）；
2、如果通过 Spring 来管理相应的 `SessionFactory`，不再使用 `factory.openSession()` 开启 session ，而应该是用 facotry.getCurrentSession 来打开 Session ，这个 Session 就会被 Spring 	所管理，此时开发人员不用进行事务控制，也不用关闭 Session ，全部由 Spring 容器来完成。

我们的问题：所有的 DAO 层类都要依赖注入  `SessionFactory` ，然后再获得 Session ，重复代码太多了。
解决方案：编写一个 DAO 层的基类，所有的 DAO 层类都继承这个基类就可以了。

```
@Repository(value="groupHibernateDao")
public class GroupHibernateDao {


    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }


    public void groupAdd(Group group){
        getSession().save(group);
        System.out.println(group.getId());

    }

}
```

## 步骤5：编写测试方法：
```
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:beans.xml"})
public class HibernateSpringTest {

    @Autowired
    private GroupHibernateDao groupHibernateDao;

    @Test
    public void testAdd() {
        Group group = new Group();
        group.setName("人事处");
        groupHibernateDao.groupAdd(group);

    }

}
```
