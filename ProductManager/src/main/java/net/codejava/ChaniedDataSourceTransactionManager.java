package net.codejava;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
@Configuration
public class ChaniedDataSourceTransactionManager{

	/*
	 * @Autowired DataSource secondaryDataSource;
	 * 
	 * @Autowired DataSource primaryDataSource;
	 * 
	 * DataSourceTransactionManager tm1; DataSourceTransactionManager tm2; public
	 * MyDataSourceTransactionManager(DataSource ds1,DataSource ds2){ tm1 = new
	 * DataSourceTransactionManager(ds1); tm2 =new
	 * DataSourceTransactionManager(ds2); }
	 */
	 @Bean(name = "chainedTransactionManager")
	    public ChainedTransactionManager transactionManager(
	    		@Qualifier("transactionManager") PlatformTransactionManager ds1,
	            @Qualifier("SecondaryTransactionManager") PlatformTransactionManager ds2) {
	         return new ChainedTransactionManager(ds1, ds2);
	    }
}
