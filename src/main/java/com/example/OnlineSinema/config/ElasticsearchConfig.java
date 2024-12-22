//package com.example.OnlineSinema.config;
//
//import com.example.OnlineSinema.domain.Access;
//import com.example.OnlineSinema.domain.User;
//import com.example.OnlineSinema.repository.AccessRepository;
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.repository.support.ElasticsearchRepositoryFactoryBean;
//
//@Configuration
//public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {
//
//    @Override
//    @Bean
//    public ElasticsearchRestTemplate elasticsearchRestTemplate() {
//        return new ElasticsearchRestTemplate(client());
//    }
//
//    @Override
//    public User client() {
//        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
//        return new RestHighLevelClient(builder);
//    }
//
//    @Bean
//    public RepositoryFactoryBean<AccessRepository, Access, Long> accessRepository() {
//        return new ElasticsearchRepositoryFactoryBean<>(AccessRepository.class, Access.class);
//    }
//}