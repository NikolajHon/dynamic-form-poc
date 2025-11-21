
package com.example.dynamicform.models.analyticky_model;

import com.example.dynamicform.domain.EvaluationContext;
import com.example.dynamicform.domain.FieldDefinition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
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
