package restoran.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@Configuration
public class WebConfig implements WebMvcConfigurer {   
	
	@Autowired
    private MessageSource messageSource;
	
	 @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/ulogovan").setViewName("index");
        registry.addViewController("/registration").setViewName("registration");
        registry.addViewController("/meni").setViewName("meni");
        registry.addViewController("/user/jedinice").setViewName("/konobar/jedinice");
        registry.addViewController("/user/porucijedinicu/{jedinica}").setViewName("/konobar/jedinice");
        registry.addViewController("/user/resetujjedinicu/{jedinica}").setViewName("/konobar/jedinice");
        registry.addViewController("/user/porudzbinekonobara").setViewName("/konobar/porudzbinekonobara");
        registry.addViewController("/user/jedinice").setViewName("/konobar/jedinice");
        registry.addViewController("/admin/users").setViewName("/admin/user");
        registry.addViewController("/admin/blagajne").setViewName("/admin/blagajne-sve");
        
	}
	
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.setValidationMessageSource(messageSource);
        return factory;
    }

    @Bean
	public SpringSecurityDialect securityDialect() {
	    return new SpringSecurityDialect();
	}

}
