
package com.example.dynamicform.models.predikaty;

import com.example.dynamicform.domain.EvaluationContext;
import com.example.dynamicform.domain.FieldDefinition;

import java.util.ArrayList;
import java.util.List;

public class PredicatesEngine {

    public List<FieldDefinition> evaluate(EvaluationContext ctx) {
        List<PredicateRule> rules = PredicateRulesRegistry.forTenantAndScheme(ctx.getTenant(), ctx.getCardScheme());
        List<FieldDefinition> out = new ArrayList<>();
        for (PredicateRule rule : rules) {
            rule.apply(ctx).ifPresent(out::add);
        }
        return out;
    }
}
