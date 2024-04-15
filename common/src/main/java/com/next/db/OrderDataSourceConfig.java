package com.next.db;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.next.orderDao", sqlSessionTemplateRef = "")
public class OrderDataSourceConfig {


}
