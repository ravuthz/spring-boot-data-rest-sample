package com.hti.pos.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

/**
 * Created by ravuthz
 * Date : 12/2/2020, 9:44 PM
 * Email : ravuthz@gmail.com, yovannaravuth@gmail.com
 */

@Configuration
@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
public class SwaggerConfig {

    public static final String AUTHORIZATION = "AUTHORIZATION";
    public static final String SCOPE_READ = "read";
    public static final String SCOPE_WRITE = "write";
    public static final String SCOPE_READ_DESC = "Can read only";
    public static final String SCOPE_WRITE_DESC = "Can write only";

    @Value("${security.oauth2.url:}")
    private String oAuthTokenUrl;

    @Value("${swagger.apiInfo.title:}")
    private String title;

    @Value("${swagger.apiInfo.description:}")
    private String description;

    @Value("${swagger.apiInfo.version:}")
    private String version;

    @Value("${swagger.apiInfo.termOfServiceUrl:}")
    private String termOfServiceUrl;

    @Value("${swagger.apiInfo.contactUrl:}")
    private String contactUrl;

    @Value("${swagger.apiInfo.contactName:}")
    private String contactName;

    @Value("${swagger.apiInfo.contactEmail:}")
    private String contactEmail;

    @Value("${swagger.apiInfo.license:}")
    private String license;

    @Value("${swagger.apiInfo.licenseUrl:}")
    private String licenseUrl;

    @Value("${swagger.defaultKey.page:page}")
    private String pageKey;

    private String pageDescription = "Pagination's page number start by index";

    @Value("${swagger.defaultKey.size:size}")
    private String sizeKey;

    private String sizeDescription = "Pagination's items per page";

    @Value("${swagger.defaultKey.sort:sort}")
    private String sortKey;

    private String sortDescription = "Pagination's sort item by field";

    @Value("${swagger.defaultValue.page:0}")
    private String pageValue;

    @Value("${swagger.defaultValue.size:20}")
    private String sizeValue;

    @Value("${swagger.defaultValue.sort:id,desc}")
    private String sortValue;

    @Bean
    public Docket api() {
        List<RequestParameter> parameterList = Arrays.asList(
                parameterBuilder(ParameterType.QUERY, pageKey, pageDescription, pageValue, false),
                parameterBuilder(ParameterType.QUERY, sizeKey, sizeDescription, sizeValue, false),
                parameterBuilder(ParameterType.QUERY, sortKey, sortDescription, sortValue, false)
        );

        List<Response> responseList = List.of(
                responseBuilder(MediaType.APPLICATION_JSON, "200", "OK")
//                responseBuilder(MediaType.ALL, "401", "Unauthorized"),
//                responseBuilder(MediaType.ALL, "403", "Forbidden"),
//                responseBuilder(MediaType.APPLICATION_JSON, "404", "Not Found")
        );

        return new Docket(DocumentationType.OAS_30)
                .select()
//                .apis(RequestHandlerSelectors.withClassAnnotation(Repository.class))
//                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
//                .apis(RequestHandlerSelectors.withClassAnnotation(BasePathAwareController.class))
//                .apis(RequestHandlerSelectors.withClassAnnotation(RepositoryRestController.class))
//                .apis(RequestHandlerSelectors.basePackage("com.hti.pos"))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
//                .paths(PathSelectors.regex("(?!/error.*).*"))
                .build()
//                .globalResponses(HttpMethod.GET, responseList)
//                .globalResponses(HttpMethod.PUT, responseList)
//                .globalResponses(HttpMethod.POST, responseList)
//                .globalResponses(HttpMethod.PATCH, responseList)
//                .globalRequestParameters(parameterList)
//                .securityContexts(Lists.newArrayList(securityContext()))
//                .securitySchemes(Collections.singletonList(securitySchema()))
                .apiInfo(apiInfoBuilder())
                .tags(new Tag("Pet Service", "All apis relating to pets"));
    }

    private ApiInfo apiInfoBuilder() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version)
                .termsOfServiceUrl(termOfServiceUrl)
                .contact(new Contact(contactName, contactUrl, contactEmail))
                .license(license)
                .licenseUrl(licenseUrl)
                .build();
    }

    private RequestParameter parameterBuilder(ParameterType type, String name, String description, String defaultValue, boolean required) {
        return new RequestParameterBuilder()
                .name(name)
                .description(description)
                .in(type)
                .required(required)
                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                .build();
    }

    private Response responseBuilder(MediaType mediaType, String code, String description) {
        return new ResponseBuilder()
                .code(code)
                .description(description)
                .representation(mediaType)
                .apply(r ->
                        r.model(m ->
                                m.referenceModel(ref ->
                                        ref.key(k ->
                                                k.qualifiedModelName(q ->
                                                        q.namespace("org.springframework.boot.autoconfigure.web.servlet.error").name("BasicErrorController")
                                                )
                                        )
                                )
                        )
                )
                .build();
    }

    private List<AuthorizationScope> scopes() {
        List<AuthorizationScope> list = new ArrayList();
        list.add(new AuthorizationScope(SCOPE_READ, SCOPE_READ_DESC));
        list.add(new AuthorizationScope(SCOPE_WRITE, SCOPE_WRITE_DESC));
        return list;
    }

    private List<GrantType> grantTypes() {
        ResourceOwnerPasswordCredentialsGrant resourceOwnerPasswordCredentialsGrant =
                new ResourceOwnerPasswordCredentialsGrant(oAuthTokenUrl);
        List<GrantType> grantTypes = Collections.singletonList(resourceOwnerPasswordCredentialsGrant);
        return grantTypes;
    }

    private OAuth securitySchema() {
        return new OAuth(AUTHORIZATION, scopes(), grantTypes());
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{
                new AuthorizationScope(SCOPE_READ, SCOPE_READ_DESC),
                new AuthorizationScope(SCOPE_WRITE, SCOPE_WRITE_DESC)
        };
        return new ArrayList<>((Collection<? extends SecurityReference>) new SecurityReference(AUTHORIZATION, authorizationScopes));
    }

    private SecurityContext securityContext() {
        return SecurityContext
                .builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

}
