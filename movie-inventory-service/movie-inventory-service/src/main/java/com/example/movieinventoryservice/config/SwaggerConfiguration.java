package com.example.movieinventoryservice.config;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.movieinventoryservice.properties.AppDevelopmentProperties;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.TokenEndpoint;
import springfox.documentation.service.TokenRequestEndpoint;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	public static final String AUTH_SERVER = "http://localhost:8091/oauth";
    public static final String CLIENT_ID = "client";
    public static final String CLIENT_SECRET = "password";

	private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerConfiguration.class);

	@Autowired
	private AppDevelopmentProperties app;

	ApiInfo apiInfo() {
		LOGGER.info(app.toString());
		return new ApiInfoBuilder().title(this.app.getProject()).description(this.app.getDescription())
				.license(this.app.getLicence()).licenseUrl("").termsOfServiceUrl("")
				.version(this.app.getEnvironment() + "-" + this.app.getVersion())
				.contact(new Contact(this.app.getContact(), "", "")).build();
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build().securitySchemes(Arrays.asList(securityScheme()))
        		.securityContexts(Arrays.asList(securityContext()));
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
        		.clientId(CLIENT_ID)
        		.clientSecret(CLIENT_SECRET)
        		.useBasicAuthenticationWithAccessCodeGrant(true)
        		.build();
    }

    private SecurityScheme securityScheme() {
        GrantType grantType = new AuthorizationCodeGrantBuilder()
        		.tokenEndpoint(new TokenEndpoint(AUTH_SERVER + "/token", "oauthtoken"))
        		.tokenRequestEndpoint(
        		  new TokenRequestEndpoint(AUTH_SERVER + "/authorize", CLIENT_ID, CLIENT_SECRET))
        		.build();

        SecurityScheme oauth = new OAuthBuilder().name("spring_oauth")
        		.grantTypes(Arrays.asList(grantType))
        		.scopes(Arrays.asList(scopes()))
        		.build();
        return oauth;
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
        		.securityReferences(
        		  Arrays.asList(new SecurityReference("spring_oauth", scopes())))
        		.forPaths(PathSelectors.regex("/foos.*"))
        		.build();
    }

    private AuthorizationScope[] scopes() {
        AuthorizationScope[] scopes = { 
          new AuthorizationScope("read", "for read operations"), 
          new AuthorizationScope("write", "for write operations"), 
          new AuthorizationScope("foo", "Access foo API") };
        return scopes;
    }
}