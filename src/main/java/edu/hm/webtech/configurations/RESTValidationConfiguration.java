package edu.hm.webtech.configurations;

import edu.hm.webtech.topic.Topic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Configuration to enable JPA-Validation before saving to the database so that no error 500 is returned but always the correct error 400 with the cause.
 */
@Configuration
public class RESTValidationConfiguration extends RepositoryRestConfigurerAdapter {

    @Bean
    @Primary
    /**
     * Create a validator to use in bean validation - primary to be able to autowire without qualifier
     */
    Validator validator() {
        return new LocalValidatorFactoryBean();
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Topic.class);
    }

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        Validator validator = validator();
        //bean validation always before save and create
        validatingListener.addValidator("beforeCreate", validator);
        validatingListener.addValidator("beforeSave", validator);
    }
}