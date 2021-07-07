package com.lanweihong.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * 配置 book 数据源
 * @author lanweihong 986310747@qq.com
 */
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
