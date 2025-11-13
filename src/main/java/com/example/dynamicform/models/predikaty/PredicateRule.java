
package com.example.dynamicform.models.predikaty;

import com.example.dynamicform.domain.EvaluationContext;
import com.example.dynamicform.domain.FieldDefinition;

import java.util.List;
import java.util.Optional;

@FunctionalInterface
public interface PredicateRule {
    Optional<FieldDefinition> apply(EvaluationContext ctx);

    static List<PredicateRule> list(PredicateRule... rules) {
        return List.of(rules);
    }
}
