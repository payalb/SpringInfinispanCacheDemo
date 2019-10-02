package com.example.demo;

import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@SpringBootApplication
@EnableCaching
public class SpringInfinispanCacheDemoApplication {
//	  public static final String IP = "192.168.0.17";
	public static void main(String[] args) {
		SpringApplication.run(SpringInfinispanCacheDemoApplication.class, args);
	}

	@Bean
	public View membertemplate() {
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.setPrettyPrint(true);
		return view;
	}
	/*
	 * @Bean public InfinispanRemoteConfigurer infinispanRemoteConfigurer() { return
	 * () -> new ConfigurationBuilder().addServer().host(IP).build(); }
	 */

	@Bean
	@Primary
	public EmbeddedCacheManager getManagerConfig() {
		GlobalConfiguration gc = new GlobalConfigurationBuilder().clusteredDefault().transport()
				.clusterName("cluster-1").addProperty("configurationFile", "jgroups-ec2.xml").build();
		// for non-clustered
		Configuration cfg = new ConfigurationBuilder().clustering().cacheMode(CacheMode.DIST_SYNC).memory().size(150)
				.build();
		return new DefaultCacheManager(gc, cfg);
	}
}
