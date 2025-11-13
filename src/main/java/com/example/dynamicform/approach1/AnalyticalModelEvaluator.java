
package com.example.dynamicform.approach1;

import com.example.dynamicform.domain.EvaluationContext;
import com.example.dynamicform.domain.FieldDefinition;

import java.util.ArrayList;
import java.util.List;

public class AnalyticalModelEvaluator {

    public List<FieldDefinition> evaluate(EvaluationContext ctx) {
        List<AdditionalDataDefinition> defs = AnalyticalModelConfig.forTenantAndScheme(ctx.getTenant(), ctx.getCardScheme());
        List<FieldDefinition> out = new ArrayList<>();
        for (AdditionalDataDefinition def : defs) {
            if (def.matches(ctx)) {
                out.add(def.toField(ctx));
            }
        }
        return out;
    }
}
