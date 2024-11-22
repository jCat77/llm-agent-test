package com.jcat.domain.tools;

import java.lang.reflect.Method;

public record ToolMeta(Tool tool, Method method) {

    public static ToolMeta create(Tool tool, Method method) {
        return new ToolMeta(tool, method);
    }
}
