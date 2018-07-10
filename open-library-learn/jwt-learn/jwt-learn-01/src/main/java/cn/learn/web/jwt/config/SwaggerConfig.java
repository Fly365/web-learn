package cn.learn.web.jwt.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * jwt-learn-cn.learn.web.jwt.config
 *
 * @author : WXF
 * @date : 2018年-07月-10日
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbi1bUk9MRV9BRE1JTiwgQVVUSF9XUklURV0iLCJleHAiOjE1Mjk3Mzg3MzN9.TnPfcYc_ssozPpmnIwf5GvPjp3H4tppf0nXaihgzsMfcoWrhwzEITicvhFNdInFbbDXWNppPD3Bts1ni5GuTRQ";

    public Docket api(){
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("Authorization").description("令牌").defaultValue(TOKEN).modelRef(new ModelRef("string"))
                .parameterType("header").required(false);
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(tokenPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.learn.web.jwt.controller"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .globalOperationParameters(parameters)
                .apiInfo(initApiInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, Lists.newArrayList(
                        new ResponseMessageBuilder().code(500).message("500 msg")
                                .responseModel(new ModelRef("Error")).build(),
                        new ResponseMessageBuilder().code(403).message("Forbidden").build()));
    }


    private ApiInfo initApiInfo() {
        return new ApiInfoBuilder().title("JWT Learn")
                .description("JWT Learn")
                .version("0.0.1").build();
    }




}
