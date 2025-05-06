package br.com.erudio;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.stereotype.Component;

@Component
public class MeuToolCallback implements ToolCallback {

    @Override
    public ToolDefinition getToolDefinition() {
        return ToolDefinition.builder().description("Tool executada com sucesso!").build();
    }

    @Override
    public String call(String toolInput) {
        return "meu_tool";
    }
}
