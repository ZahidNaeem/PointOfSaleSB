package org.zahid.apps.web.pos.configuration;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("oracle.jdbc.driver.OracleDriver");
        dataSourceBuilder.url("jdbc:oracle:thin:@zahid:1521/pdborcl01");
        dataSourceBuilder.username("xxim");
        dataSourceBuilder.password("x");
        return dataSourceBuilder.build();
    }
}