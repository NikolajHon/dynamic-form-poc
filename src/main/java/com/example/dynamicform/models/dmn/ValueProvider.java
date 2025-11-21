package com.example.dynamicform.models.dmn;

import com.example.dynamicform.domain.EvaluationContext;

public interface ValueProvider {
    String get(EvaluationContext ctx);
}
