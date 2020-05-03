package net.codejava.first.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "firstEntityManagerFactory", transactionManagerRef = "firstTransactionManager", basePackages = {
		"net.codejava.first.repo" })
public class PrimaryDBConfig {

	@Primary
	@Bean(name = "firstDataSource")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource firstDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = "firstEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("firstDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("net.codejava.first.entity").persistenceUnit("sales").build();
	}

	@Primary
	@Bean(name = "firstTransactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("firstEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}
