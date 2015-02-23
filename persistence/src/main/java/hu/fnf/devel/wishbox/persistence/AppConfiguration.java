package hu.fnf.devel.wishbox.persistence;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by 212417040(hupfhmib@ge.com) on 23/02/15.
 */
@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class AppConfiguration {
    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName( "com.mysql.jdbc.Driver" );
        dataSource.setUrl( "jdbc:mysql://localhost/test" );
        dataSource.setUsername( "dbtest" );
        dataSource.setPassword( "dbpass" );
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase( Database.MYSQL );
        vendorAdapter.setGenerateDdl( true );

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter( vendorAdapter );
        factory.setPackagesToScan( getClass().getPackage().getName() );
        factory.setDataSource( dataSource() );

        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }
}
