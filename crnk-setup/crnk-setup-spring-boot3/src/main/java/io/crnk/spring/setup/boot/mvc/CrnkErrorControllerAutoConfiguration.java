package io.crnk.spring.setup.boot.mvc;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.webmvc.autoconfigure.error.BasicErrorController;
import org.springframework.boot.webmvc.autoconfigure.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.webmvc.autoconfigure.error.ErrorViewResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.webmvc.error.ErrorAttributes;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

import jakarta.servlet.Servlet;
import java.util.List;


@Configuration
@ConditionalOnProperty(prefix = "crnk.spring.mvc", name = "errorController", havingValue = "true", matchIfMissing = true)
@ConditionalOnWebApplication
@ConditionalOnClass({Servlet.class, DispatcherServlet.class, ErrorMvcAutoConfiguration.class})
// Load before the main ErrorMvcAutoConfiguration so that we override it
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
@EnableConfigurationProperties({CrnkSpringMvcProperties.class, WebProperties.class})
public class CrnkErrorControllerAutoConfiguration {

	private final WebProperties webProperties;

	private final List<ErrorViewResolver> errorViewResolvers;

	public CrnkErrorControllerAutoConfiguration(WebProperties webProperties,
												ObjectProvider<List<ErrorViewResolver>> errorViewResolversProvider) {
		this.webProperties = webProperties;
		this.errorViewResolvers = errorViewResolversProvider.getIfAvailable();
	}

	@Bean
	@ConditionalOnMissingBean(value = ErrorController.class, search = SearchStrategy.CURRENT)
	public BasicErrorController jsonapiErrorController(ErrorAttributes errorAttributes) {
		return new CrnkErrorController(errorAttributes, this.webProperties.getError(), this.errorViewResolvers);
	}
}
