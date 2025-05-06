package br.com.erudio.config;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class ToolConfig {

    @Bean
    @Primary
    public ToolCallbackProvider toolCallbackProvider(
            @Qualifier("mcpToolCallbacks") ToolCallbackProvider mcpToolCallbacks) {
        return mcpToolCallbacks;
    }

    /*@Bean
    public ToolCallbackProvider toolCallbackProvider() {
        return MethodToolCallbackProvider.builder()
                .toolObjects(Collections.emptyList())
                .build();
    }*/
}
