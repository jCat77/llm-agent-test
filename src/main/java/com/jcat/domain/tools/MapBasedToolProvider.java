package com.jcat.domain.tools;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class MapBasedToolProvider implements ToolProvider {

    private final Map<String,ToolMeta> toolMap;

    public MapBasedToolProvider(Map<String, ToolMeta> toolMap) {
        this.toolMap = toolMap;
    }

    @Override
    public ToolMeta get(String id) {
        log.info("Request for tool={}", id);
        ToolMeta toolMeta = toolMap.get(id);
        if (toolMeta == null) {
            log.warn("No tool found for id={}", id);
            throw new RuntimeException("No tool found for id=" + id);
        }
        return toolMeta;
    }
}
