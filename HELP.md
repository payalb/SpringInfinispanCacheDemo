

This will make you even more productive and your code less verbose!

Why do I need starters?

Spring Boot Starters make the bootstrapping process much easier and faster. The starter brings you required Maven dependencies as well as some predefined configuration bits.

What do I need to get started?

The starter can operate in two modes: client/server (when you connect to a remote Infinispan Server cluster) and embedded (packaged along with your app). The former is the default. It's also possible to use both those modes at the same time (store some data along with your app and connect to a remote Infinispan Server cluster to perform some other type of operations).

Assuming you have an Infinispan Server running on IP address 192.168.0.17, all you need to do is to use the following dependencies:

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.example</groupId>
    <artifactId>myproject</artifactId>
    <version>1.0.0-SNAPSHOT</version>

    <properties>
        <infinispan.starters.version>1.0.0.Alpha1</infinispan.starters.version>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>org.infinispan</groupId>
            <artifactId>inifinispan-spring-boot-starter</artifactId>
            <version>${infinispan.starters.version}</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>${spring-boot.version}</version>
            </plugin>
        </plugins>
    </build>
</project>
view rawpom.xml hosted with ❤ by GitHub

By default, the starter will try to locate hotrod-client.properties file. The file should contain at least the server list:

infinispan.client.hotrod.server_list=192.168.0.17:11222
view rawhotrod-client.properties hosted with ❤ by GitHub

It is also possible to create RemoteCacheManager's configuration manually:

@Configuration
public class InfinispanCacheConfiguration {

   public static final String IP = "192.168.0.17";

   @Bean
   public InfinispanRemoteConfigurer infinispanRemoteConfigurer() {
      return () -> new ConfigurationBuilder().addServer().host(IP).build();
   }
}
view rawInfinispanCacheConfiguration.java hosted with ❤ by GitHub

That's it! Your app should successfully connect to a remote cluster and you should be able to inject RemoteCacheManager.

Using Infinispan embedded is even simpler than that. All you need to do is to add additional dependency to the classpath:

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.example</groupId>
    <artifactId>myproject</artifactId>
    <version>1.0.0-SNAPSHOT</version>

    <properties>
        <infinispan.starters.version>1.0.0.Alpha1</infinispan.starters.version>
        <version.infinispan>8.2.4.Final</version.infinispan>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.infinispan</groupId>
                <artifactId>infinispan-bom</artifactId>
                <version>${version.infinispan}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-parent</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>org.infinispan</groupId>
            <artifactId>inifinispan-spring-boot-starter</artifactId>
            <version>${infinispan.starters.version}</version>
        </dependency>
        <dependency>
            <groupId>org.infinispan</groupId>
            <artifactId>infinispan-core</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>${spring-boot.version}</version>
            </plugin>
        </plugins>
    </build>
</project>
view rawpom.xml hosted with ❤ by GitHub

The starter will provide you a preconfigured EmbeddedCacheManager. In order to customize the configuration, use the following code snippet:

@Configuration
public class InfinispanCacheConfiguration {

    public static final String TEST_CLUSTER = "TEST_CLUSTER";
    public static final String TEST_CACHE_NAME = "test-simple-cache";
    public static final String TEST_GLOBAL_JMX_DOMAIN = "test.infinispan";

    @Bean
    public InfinispanCacheConfigurer cacheConfigurer() {
        return cacheManager -> {
            final org.infinispan.configuration.cache.Configuration testCache =
                    new ConfigurationBuilder().simpleCache(true)
                                              .eviction().size(1000L).strategy(EvictionStrategy.LRU)
                                              .jmxStatistics().enable()
                                              .build();

            cacheManager.defineConfiguration(TEST_CACHE_NAME, testCache);
        };
    }

    @Bean
    public InfinispanGlobalConfigurer globalConfigurer() {
        return () -> {
            final GlobalConfiguration globalConfiguration = new GlobalConfigurationBuilder()
                    .transport().clusterName(TEST_CLUSTER)
                    .globalJmxStatistics().jmxDomain(TEST_GLOBAL_JMX_DOMAIN).enable()
                    .build();

            return globalConfiguration;
        };
    }
view rawInfinispanCacheConfiguration.java hosted with ❤ by GitHub
Further reading

There are two link I highly recommend you to read. The first is the Spring Boot tutorial and the second is the Github page of the Starters project. 


Jgroup : protocol toolkit for nodes to interact with each other, share data with each other, infinispan comes with this library. Works on xml based config. Default part of infinispan.
Both nodes on same network , so can detect each other. 
discovery protocol:ping (native-s3-ping) protocol: makes use of awk sdk. Uses s3 bucket to keep info of cluster like nodes leaving, joining.
transport protocol: UDP, can make of TCP.

If deploying application to cloud providers, UDP is blocked by default.

In infinispan-code: folder default-configs: can find default config file for jgroup.