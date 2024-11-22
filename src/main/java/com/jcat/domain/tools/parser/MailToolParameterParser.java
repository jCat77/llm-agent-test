package com.jcat.domain.tools.parser;

import com.jcat.domain.tools.MailTools;

public class MailToolParameterParser implements ParameterParser<MailTools.Params> {

    @Override
    public MailTools.Params parse(String params) {
        return new MailTools.Params();
    }
}
