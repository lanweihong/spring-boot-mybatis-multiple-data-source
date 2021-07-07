# Spring Boot 搭配MyBatis及Druid实现多数据源，可自动切换

数据库文件： `test.sql`；

## 添加相关依赖包

编辑 `pom.xml` 文件，添加相关依赖：
```xml
<parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.4.8</version>
        <relativePath/>
    </parent>
    
<properties>
        <mysql.driver.version>8.0.16</mysql.driver.version>
        <mybatis.spring.boot.version>1.3.2</mybatis.spring.boot.version>
        <druid.version>1.1.10</druid.version>
        <tk.mybatis.version>2.1.5</tk.mybatis.version>
        <lombok.version>1.16.18</lombok.version>
    </properties>

    <dependencies>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>${lombok.version}</version>
        </dependency>

        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>${mybatis.spring.boot.version}</version>
        </dependency>

        <!-- mysql driver -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.driver.version}</version>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>${druid.version}</version>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-autoconfigure</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper-spring-boot-starter</artifactId>
            <version>${tk.mybatis.version}</version>
        </dependency>

    </dependencies>
```

## 配置 `application.yml` 文件

在配置文件 `application.yml` 中配置数据源：
```yml
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    username: root
    password: Aa123456.
    druid:
      driver-class-name: com.mysql.cj.jdbc.Driver
      initial-size: 5
      max-active: 50
      max-wait: 60000
      min-idle: 5
      # 配置 book 数据源，可自定义
      book:
        # type: com.alibaba.druid.pool.DruidDataSource
        # driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://localhost:3306/db01?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=Asia/Shanghai
        # username: root
        # password: 1
      # 配置 user 数据源
      user:
        url: jdbc:mysql://localhost:3306/db02?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=Asia/Shanghai
        # 重新配置密码，未配置则默认使用以上配置的
        username: root
        password: 1
```

## 手动配置数据源

### 主数据源配置

创建配置类 `BookDataSourceConfig` ，配置类需要对 `DataSource`、`DataSourceTransactionManager`、`SqlSessionFactory` 、`SqlSessionTemplate` 四个数据项进行配置。当系统中存在多个数据源时，必须有一个数据源为主数据源，使用 `@Primary` 注解修饰。

 `BookDataSourceConfig` 配置类：
```java
@Configuration
// 通过在 `@MapperScan` 注解中配置 `basePackages` 和 `sqlSessionTemplateRef` 来实现自动切换数据源
@MapperScan(basePackages = "com.lanweihong.dao.book",
        sqlSessionFactoryRef = "bookSqlSessionFactory", sqlSessionTemplateRef = "bookSqlSessionTemplate")
public class BookDataSourceConfig {

    public static final String MAPPER_LOCATION = "classpath:mapper/book/*.xml";

    /**
     * 主数据源
     * 说明：@Primary 如果有多个同类的Bean，该Bean优先考虑，多数据源时必须配置一个主数据源，用该注解标志
     * @return
     */
    @Primary
    @Bean("bookDataSource")
    @ConfigurationProperties("spring.datasource.druid.book")
    public DataSource bookDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Primary
    @Bean("bookTransactionManager")
    public DataSourceTransactionManager bookTransactionManager() {
        return new DataSourceTransactionManager(bookDataSource());
    }

    @Primary
    @Bean("bookSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("bookDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        return sqlSessionFactoryBean.getObject();
    }

    @Primary
    @Bean("bookSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("bookDataSource") DataSource dataSource) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory(dataSource));
    }
}
```

Mybatis 是通过配置的扫描包和对应的 `sqlSessionTemplate` 来自动切换数据源，即通过在 `@MapperScan` 注解中配置 `basePackages` 和 `sqlSessionTemplateRef`：
```java
@MapperScan(basePackages = "com.lanweihong.dao.book",
        sqlSessionFactoryRef = "bookSqlSessionFactory", sqlSessionTemplateRef = "bookSqlSessionTemplate")
```

### 配置第二个数据源

编写配置类 `UserDataSourceConfig`：
```java
@Configuration
@MapperScan(basePackages = "com.lanweihong.dao.user", sqlSessionTemplateRef = "userSqlSessionTemplate")
public class UserDataSourceConfig {

    public static final String MAPPER_LOCATION = "classpath:mapper/user/*.xml";

    /**
     * user 数据源
     * @return
     */
    @Bean("userDataSource")
    @ConfigurationProperties("spring.datasource.druid.user")
    public DataSource userDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean("userTransactionManager")
    public DataSourceTransactionManager userTransactionManager() {
        return new DataSourceTransactionManager(userDataSource());
    }

    @Bean("userSqlSessionFactory")
    public SqlSessionFactory userSqlSessionFactory(@Qualifier("userDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean("userSqlSessionTemplate")
    public SqlSessionTemplate userSqlSessionTemplate(@Qualifier("userDataSource") DataSource dataSource) throws Exception {
        return new SqlSessionTemplate(userSqlSessionFactory(dataSource));
    }

}
```

至此，不同数据源的配置已完成，接下来使用了。

## 使用

按平时正常写和使用 `DAO` 和 `Service`，Spring 会根据数据源配置的映射自动切换相应的数据源，不需要在 Service 中指定，直接使用即可。


### 测试

启动应用，在浏览器中访问 `http://127.0.0.1:8015/api/v1/users` 及 `http://127.0.0.1:8015/api/v1/books` 测试；

![测试结果](https://images.lanweihong.com/public/spring-boot-mutil-data-source-test-result-01.png)

![测试结果](https://images.lanweihong.com/public/spring-boot-mutil-data-source-test-result-02.png)

![测试结果](https://images.lanweihong.com/public/spring-boot-mutil-data-source-test-result-03.png)

## 项目结构

项目结构如下图：

![项目结构](https://images.lanweihong.com/public/spring-boot-mutil-data-source-project-catalog.png)