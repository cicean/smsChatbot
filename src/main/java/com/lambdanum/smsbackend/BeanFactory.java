package com.lambdanum.smsbackend;


import com.lambdanum.smsbackend.filesystem.ResourceRepository;
import opennlp.tools.stemmer.snowball.SnowballStemmer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import java.io.IOException;

@Configuration
public class BeanFactory {

    private EntityManager em;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public SessionFactory sessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @Bean
    public EntityManager entityManager() {
        if (em == null) {
            em = sessionFactory().createEntityManager();
        }
        return em;
    }

    @Bean
    public Tokenizer tokenizer(ResourceRepository resourceRepository) {
        try {
            TokenizerModel model = new TokenizerModel(resourceRepository.getResourceFileInputStream("en-token.bin"));
            Tokenizer tokenizer = new TokenizerME(model);
            return tokenizer;
        } catch (IOException e) {
            throw new RuntimeException("Could not instantiate tokenizer");
        }
    }

    @Bean
    public SnowballStemmer snowballStemmer() {
        return new SnowballStemmer(SnowballStemmer.ALGORITHM.ENGLISH);
    }


}
