package com.lanweihong.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * 配置 user 数据源
 * @author lanweihong 986310747@qq.com
 */
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
