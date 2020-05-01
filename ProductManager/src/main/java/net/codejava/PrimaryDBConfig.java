package net.codejava;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  entityManagerFactoryRef = "primaryEntityManagerFactory",
  basePackages = { "net.codejava" }
)
public class PrimaryDBConfig {

	@Autowired
	Environment env;
	 @Primary
	  @Bean(name = "primaryDataSource")
	  @ConfigurationProperties(prefix = "spring.datasource")
	  public DataSource dataSource() throws SQLException {
		  DriverManagerDataSource dataSource = new DriverManagerDataSource();
		    dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
		    dataSource.setUrl(env.getProperty("spring.datasource.jdbc-url"));
		    dataSource.setUsername(env.getProperty("spring.datasource.username"));
		    dataSource.setPassword(env.getProperty("spring.datasource.password"));
	    DataSource dataSourc=  DataSourceBuilder.create().build();
	    try (Connection connection = dataSource.getConnection()) {
	        System.out.println("catalog:" + connection.getCatalog());
	    }
	    return dataSourc;
	  }
	  
	  @Primary
	  @Bean(name = "primaryEntityManagerFactory")
	  public LocalContainerEntityManagerFactoryBean 
	  primaryEntityManagerFactory(
	    EntityManagerFactoryBuilder builder,
	    @Qualifier("primaryDataSource") DataSource dataSource
	  ) {
	    return builder
	      .dataSource(dataSource)
	      .packages("net.codejava")
	      .persistenceUnit("primary")
	      .build();
	  }
	    
	  @Primary
	  @Bean(name = "transactionManager")
	  public PlatformTransactionManager transactionManager(
	    @Qualifier("primaryEntityManagerFactory") EntityManagerFactory 
	    primaryEntityManagerFactory
	  ) {
	    return new JpaTransactionManager(primaryEntityManagerFactory);
	  }

}
