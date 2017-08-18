package io.cultr.file;

import org.springframework.boot.actuate.system.ApplicationPidFileWriter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAsync
@EnableCircuitBreaker
@EnableHystrix
@EnableHystrixDashboard
@EnableAspectJAutoProxy
@EnableSwagger2
public class FileApplication extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(FileApplication.class).listeners(new ApplicationPidFileWriter()).run(args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FileApplication.class);
	}
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("io.cultr.file")).paths(PathSelectors.any()).build()
				.apiInfo(apiInfo())/* .pathMapping("/v1/") */;
	}

	/*@Bean
	@Order(value = 1)
	public AmazonS3Properties amazonProperties() {
		return new AmazonS3Properties();
	}
	
	@Bean
	@Order(value = 2)
	public AmazonS3Configuration amazonS3Configuration() {
		return new AmazonS3Configuration();
	}*/
	
	@SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo("Cultr File Service", "Cultr File Service", "1.0", "", "support-api@cultr.io",
				"Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0");
		return apiInfo;
	}
}
