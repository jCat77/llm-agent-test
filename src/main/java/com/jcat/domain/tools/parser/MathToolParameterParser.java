package com.jcat.domain.tools.parser;

import com.jcat.domain.tools.MathTools;

public class MathToolParameterParser implements ParameterParser<MathTools.Params> {

    @Override
    public MathTools.Params parse(String params) {
        return new MathTools.Params();
    }

}
