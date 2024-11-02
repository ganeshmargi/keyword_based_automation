import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.repository", 
                       entityManagerFactoryRef = "entityManagerFactory",
                       transactionManagerRef = "transactionManager")
public class MultiDataSourceConfig {

    @Autowired
    private JpaProperties jpaProperties;

    @Bean(name = "dataSources")
    @ConfigurationProperties("spring.datasource")
    public Map<String, DataSourceProperties> dataSourceProperties() {
        return new HashMap<>();
    }

    @Bean
    public Map<String, DataSource> dataSources() {
        Map<String, DataSource> dataSources = new HashMap<>();
        dataSourceProperties().forEach((key, properties) -> {
            DataSource dataSource = properties.initializeDataSourceBuilder().build();
            dataSources.put(key, dataSource);
        });
        return dataSources;
    }

    @Bean
    public Map<String, LocalContainerEntityManagerFactoryBean> entityManagerFactories() {
        Map<String, LocalContainerEntityManagerFactoryBean> factories = new HashMap<>();
        dataSources().forEach((key, dataSource) -> {
            factories.put(key, createEntityManagerFactory(key, dataSource));
        });
        return factories;
    }

    private LocalContainerEntityManagerFactoryBean createEntityManagerFactory(String key, DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setPackagesToScan("com.example." + key.toLowerCase() + "entity"); // Adjust package names as needed
        factory.setJpaProperties(jpaProperties.getProperties());
        factory.setPersistenceUnitName(key);
        factory.afterPropertiesSet(); // Initialize the factory
        return factory;
    }

    @Bean
    public Map<String, PlatformTransactionManager> transactionManagers() {
        Map<String, PlatformTransactionManager> transactionManagers = new HashMap<>();
        entityManagerFactories().forEach((key, factory) -> {
            PlatformTransactionManager transactionManager = new JpaTransactionManager(factory.getObject());
            transactionManagers.put(key, transactionManager);
        });
        return transactionManagers;
    }
}
