
package com.example.dynamicform.models.dmn;

import com.example.dynamicform.domain.EvaluationContext;
import com.example.dynamicform.domain.FieldDefinition;

import java.util.ArrayList;
import java.util.List;

public class DecisionTableEngine {

    public List<FieldDefinition> evaluate(EvaluationContext ctx) {
        List<DecisionRow> rows = DecisionTables.forTenantAndScheme(ctx.getTenant(), ctx.getCardScheme());
        List<FieldDefinition> out = new ArrayList<>();
        for (DecisionRow row : rows) {
            if (row.matches(ctx)) {
                FieldRegistry.ensureKeysExistOrThrow(row.outputs());
                for (String key : row.outputs()) {
                    FieldRegistry.FieldTemplate tmpl = FieldRegistry.lookup(key).orElseThrow();
                    out.add(tmpl.build(ctx));
                }
            }
        }
        return out;
    }
}
