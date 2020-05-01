package net.codejava;

import java.sql.Connection;
import java.sql.SQLException;

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
  entityManagerFactoryRef = "secondaryEntityManagerFactory",
  basePackages = { "net.codejava" }
)
public class SecondaryDBConfig {

	@Autowired
	Environment env;
	 
	  @Bean(name = "secondaryDataSource")
	  @ConfigurationProperties(prefix = "second.datasource")
	  public DataSource dataSource() throws SQLException {
		  DriverManagerDataSource dataSource = new DriverManagerDataSource();
		    dataSource.setDriverClassName(env.getProperty("second.datasource.driverClassName"));
		    dataSource.setUrl(env.getProperty("second.datasource.jdbc-url"));
		    dataSource.setUsername(env.getProperty("second.datasource.username"));
		    dataSource.setPassword(env.getProperty("second.datasource.password"));
	    DataSource dataSourc=  DataSourceBuilder.create().build();
	    try (Connection connection = dataSource.getConnection()) {
	        System.out.println("catalog:" + connection.getCatalog());
	    }
	    return dataSourc;
	  }
	  
	  
	  @Bean(name = "secondaryEntityManagerFactory")
	  public LocalContainerEntityManagerFactoryBean 
	  entityManagerFactory(
	    EntityManagerFactoryBuilder builder,
	    @Qualifier("secondaryDataSource") DataSource dataSource
	  ) {
	    return builder
	      .dataSource(dataSource)
	      .packages("net.codejava")
	      .persistenceUnit("secondary")
	      .build();
	  }
	    
	  
	  @Bean(name = "SecondaryTransactionManager")
	  public PlatformTransactionManager transactionManager(
	    @Qualifier("secondaryEntityManagerFactory") EntityManagerFactory 
	    entityManagerFactory
	  ) {
	    return new JpaTransactionManager(entityManagerFactory);
	  }

}
