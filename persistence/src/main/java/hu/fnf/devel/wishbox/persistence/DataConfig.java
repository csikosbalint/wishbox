package hu.fnf.devel.wishbox.persistence;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by 212417040(hupfhmib@ge.com) on 24/02/15.
 */
@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class DataConfig {
//    @Bean
//    public DataSource dataSource() {
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName( "com.mysql.jdbc.Driver" );
//        dataSource.setUrl( "jdbc:mysql://localhost/test" );
//        dataSource.setUsername( "dbtest" );
//        dataSource.setPassword( "dbpass" );
//        return dataSource;
//    }
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setDatabase( Database.MYSQL );
//        vendorAdapter.setGenerateDdl( true );
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setJpaVendorAdapter( vendorAdapter );
//        factory.setPackagesToScan( getClass().getPackage().getName() );
//        factory.setDataSource( dataSource() );
//        return factory;
//    }
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        return new JpaTransactionManager();
//    }
}
