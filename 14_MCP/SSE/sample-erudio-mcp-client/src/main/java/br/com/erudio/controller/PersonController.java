package br.com.erudio.controller;

import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final static Logger LOG = LoggerFactory.getLogger(PersonController.class);
    private final ChatClient chatClient;

    private final List<McpSyncClient> mcpSyncClients;

    public PersonController(ChatClient.Builder chatClientBuilder,
                            ToolCallbackProvider tools,
                            List<McpSyncClient> mcpSyncClients) {
        this.chatClient = chatClientBuilder
                .defaultTools(tools)
                .defaultAdvisors(
                        // new PromptChatMemoryAdvisor(chatMemory),
                        new SimpleLoggerAdvisor())
                .build();
        this.mcpSyncClients = mcpSyncClients;
    }

    @GetMapping("/nationality/{nationality}")
    String findByNationality(@PathVariable String nationality) {

        PromptTemplate pt = new PromptTemplate("""
                Find persons with {nationality} nationality.
                """);
        Prompt p = pt.create(Map.of("nationality", nationality));
        return this.chatClient.prompt(p)
                .call()
                .content();
    }

    @GetMapping("/count-by-nationality/{nationality}")
    String countByNationality(@PathVariable String nationality) {
        PromptTemplate pt = new PromptTemplate("""
                How many persons come from {nationality}?
                Use the 'getPersonsByNationality' tool to get the number of persons from {nationality}.
                """);
        Prompt p = pt.create(Map.of("nationality", nationality));
        return this.chatClient.prompt(p)
                .call()
                .content();
    }

    @GetMapping("/count-by-nationality-from-client/{nationality}")
    String countByNationalityFromClient(@PathVariable String nationality) {
        return this.chatClient.prompt(loadPromptByName("persons-by-nationality", nationality))
                .call()
                .content();
    }

    Prompt loadPromptByName(String name, String nationality) {
        McpSchema.GetPromptRequest r = new McpSchema.GetPromptRequest(name, Map.of("nationality", nationality));
        var client = mcpSyncClients.stream()
                .filter(c -> c.getServerInfo().name().equals("person-erudio-mcp-service"))
                .findFirst();
        if (client.isPresent()) {
            var content = (McpSchema.TextContent) client.get().getPrompt(r).messages().getFirst().content();
            PromptTemplate pt = new PromptTemplate(content.text());
            Prompt p = pt.create(Map.of("nationality", nationality));
            LOG.info("Prompt: {}", p);
            return p;
        } else return null;
    }
}
