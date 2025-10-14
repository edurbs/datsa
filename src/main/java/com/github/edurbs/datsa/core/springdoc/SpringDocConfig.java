package com.github.edurbs.datsa.core.springdoc;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
@SecurityScheme(name = "security_auth",
    type = SecuritySchemeType.OAUTH2,
    flows = @OAuthFlows(authorizationCode = @OAuthFlow(
        authorizationUrl = "${springdoc.oAuthFlow.authorizationUrl}",
        tokenUrl = "${springdoc.oAuthFlow.tokenUrl}",
        scopes = {
            @OAuthScope(name = "READ", description = "read scope"),
            @OAuthScope(name = "WRITE", description = "write scope"),
        }
)))
public class SpringDocConfig {

    private HashMap<String, ApiResponse> responseMap ;

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
            .info(new Info()
                .title("Datsa API")
                .version("v1")
                .description("REST API Datsa")
                .license(new License()
                    .name("Apache 2.0")
                    .url("http://apache.com")
                )
            ).externalDocs(new ExternalDocumentation()
                .description("Datsa docs")
                .url("http://docs.datsa.com.br")
            );
    }

    @Bean
    public OpenApiCustomiser openApiCustomiser(){
        return openApi -> {
            openApi.getPaths()
                .values()
                .forEach(pathItem -> pathItem.readOperationsMap()
                    .forEach((httpMethod, operation) -> {
                        ApiResponses responses = operation.getResponses();
                        switch (httpMethod) {
                            case GET:
                                responses.putAll(getResponse("404"));
                                responses.putAll(getResponse("406"));
                                responses.putAll(getResponse("500"));
                                break;
                            case POST:
                                responses.putAll(getResponse("400"));
                                responses.putAll(getResponse("500"));
                                break;
                            case PUT:
                                responses.putAll(getResponse("400"));
                                responses.putAll(getResponse("404"));
                                responses.putAll(getResponse("500"));
                                break;
                            case DELETE:
                                responses.putAll(getResponse("404"));
                                responses.putAll(getResponse("500"));
                                break;
                            default:
                                responses.putAll(getResponse("500"));
                                break;
                        }

                    })
                );
        };
    }

    private HashMap<String, ApiResponse> getResponse(String code){
        HashMap<String, ApiResponse> responseMap = new HashMap<>();
        responseMap.put(code, getResponseMap().get(code));
        return responseMap;
    }

    private HashMap<String, ApiResponse> getResponseMap(){
        if(responseMap != null){
            return responseMap;
        }
        responseMap = new HashMap<>();
        responseMap.put("400", new ApiResponse().description("Invalid request"));
        responseMap.put("404", new ApiResponse().description("Resource not found"));
        responseMap.put("406", new ApiResponse().description("No acceptable representation found for the requested resource"));
        responseMap.put("500", new ApiResponse().description("Server internal error"));
        return  responseMap;
    }

}
