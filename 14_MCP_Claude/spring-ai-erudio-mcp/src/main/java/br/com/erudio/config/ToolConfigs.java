package br.com.erudio.config;

import br.com.erudio.services.CourseService;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ToolConfigs {

    @Bean
    public List<ToolCallback> erudioTools(CourseService courseService) {
        return List.of(ToolCallbacks.from(courseService));
    }
}