
package com.example.dynamicform.approach1;

import com.example.dynamicform.domain.DataType;
import com.example.dynamicform.domain.EvaluationContext;
import com.example.dynamicform.domain.FieldDefinition;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class AdditionalDataDefinition {
    private final String code;
    private final String label;
    private final DataType dataType;
    private final boolean mandatory;
    private final Predicate<EvaluationContext> condition;
    private final Function<EvaluationContext, Optional<String>> prefill;
    private final String source;

    public AdditionalDataDefinition(String code, String label, DataType dataType, boolean mandatory,
                                    Predicate<EvaluationContext> condition,
                                    Function<EvaluationContext, Optional<String>> prefill,
                                    String source) {
        this.code = code;
        this.label = label;
        this.dataType = dataType;
        this.mandatory = mandatory;
        this.condition = condition;
        this.prefill = prefill;
        this.source = source;
    }

    public boolean matches(EvaluationContext ctx) {
        return condition.test(ctx);
    }

    public FieldDefinition toField(EvaluationContext ctx) {
        return new FieldDefinition(code, label, dataType, mandatory, prefill.apply(ctx), source);
    }
}
