package org.springsource.cloudfoundry.mvc.services.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.cloudfoundry.runtime.env.CloudEnvironment;
import org.cloudfoundry.runtime.env.RedisServiceInfo;
import org.cloudfoundry.runtime.service.keyvalue.RedisServiceCreator;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springsource.cloudfoundry.mvc.services.LoginController;

@Configuration
@Profile("cloud")
public class CloudFoundryDataSourceConfiguration   {

	static Logger log = Logger.getLogger(CloudFoundryDataSourceConfiguration.class);
			
    private CloudEnvironment cloudEnvironment = new CloudEnvironment();

    @Bean
    public DataSource dataSource() throws Exception {
    	
    	 return new EmbeddedDatabaseBuilder()
         .setName("crm")
         .setType(EmbeddedDatabaseType.H2)
         .build();
    	/*
        Collection<RdbmsServiceInfo> mysqlSvc = cloudEnvironment.getServiceInfos(RdbmsServiceInfo.class);
        RdbmsServiceCreator dataSourceCreator = new RdbmsServiceCreator();
        return dataSourceCreator.createService(mysqlSvc.iterator().next());
        
        */
    }

    //@Bean
    public RedisTemplate<String, Object> redisTemplate() throws Exception {
        
    	//DISPALYING PROPERTIES
    	if(cloudEnvironment.isCloudFoundry()){
    		log.debug("-------->IS CLOUD FOUNDRY --------");
    		List<Map<String, Object>> servicelist = cloudEnvironment.getServices();
    		
    		int i = 1;
    		for (Map<String, Object> map : servicelist) {
    			log.debug(String.format("+++++++++++++++++++++ITERATION %s +++++++++++++++++++++",i ) );
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					log.debug(String.format("Key: %s, Value: %s",entry.getKey(), entry.getValue()  ) );
				}
			}
    		
    		Properties prop = cloudEnvironment.getCloudProperties();
    		for(Entry<Object, Object> e : prop.entrySet()){
    			log.debug(String.format("Property:  %s",e ) );
    		}
    	}
    	
    	
    	// This piece of code was throwing an exception...hope this fixes it!
    	RedisTemplate<String, Object> ro = new RedisTemplate<String, Object>();
    	
    	
    	
    	//Trying to obtain a reference to service info from the cloud service itself.
    	Iterator<RedisServiceInfo> it = cloudEnvironment.getServiceInfos(RedisServiceInfo.class).iterator();
    	
    	
    	if(it.hasNext()){
    		log.debug("-------->FOUND REDISERVICE_INFO --------"); 
    		RedisServiceInfo info = it.next();
            RedisServiceCreator creator = new RedisServiceCreator();
            RedisConnectionFactory connectionFactory = creator.createService(info);
            ro.setConnectionFactory(connectionFactory);
    	}else{
    		log.debug("--------REDISERVICE_INFO NOT FOUND!!!--------"); 
    	}
    	return ro;
    }

    

	@Bean
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean( DataSource dataSource  ) throws Exception {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource( dataSource );
        em.setPackagesToScan(LoginController.class.getPackage().getName());
        em.setPersistenceProvider(new HibernatePersistence());
        Map<String, String> p = new HashMap<String, String>();
        p.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, "create");
        p.put(org.hibernate.cfg.Environment.HBM2DDL_IMPORT_FILES, "import_psql.sql");
        p.put(org.hibernate.cfg.Environment.DIALECT, PostgreSQLDialect.class.getName());
        p.put(org.hibernate.cfg.Environment.SHOW_SQL, "true");
        em.setJpaPropertyMap(p);
        return em;
    }


    @Bean
    public CacheManager cacheManager() throws Exception {
    	log.debug("--------INVOKING cacheManager() --------"); 
    	//RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate());
    	
    	 SimpleCacheManager cacheManager = new SimpleCacheManager();
         Cache cache = new ConcurrentMapCache("customers");
         cacheManager.setCaches(Arrays.asList(cache));
    	
        return cacheManager;
    }

}

