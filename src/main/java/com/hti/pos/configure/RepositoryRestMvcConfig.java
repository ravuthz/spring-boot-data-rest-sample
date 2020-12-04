package com.hti.pos.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by ravuthz
 * Date : 12/2/2020, 8:45 PM
 * Email : ravuthz@gmail.com, yovannaravuth@gmail.com
 */

@Configuration
public class RepositoryRestMvcConfig implements RepositoryRestConfigurer {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.setReturnBodyOnCreate(Boolean.TRUE);
        config.setReturnBodyOnUpdate(Boolean.TRUE);
        config.setDefaultMediaType(MediaType.APPLICATION_JSON);
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
//        config.exposeIdsFor(User.class, Role.class, Permission.class);
        config.exposeIdsFor(entities.stream().map(e -> e.getJavaType()).collect(Collectors.toList()).toArray(new Class[0]));
    }

}
