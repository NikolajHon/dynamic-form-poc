package com.example.dynamicform.models.dmn;

import com.example.dynamicform.domain.EvaluationContext;
import com.example.dynamicform.domain.FieldDefinition;
import org.camunda.bpm.dmn.engine.*;

import java.io.InputStream;
import java.util.*;

public class DecisionTableEngine {

    private final DmnEngine engine;
    private final DmnDecision decision;

    public DecisionTableEngine() {
        this.engine = DmnEngineConfiguration
                .createDefaultDmnEngineConfiguration()
                .buildEngine();

        InputStream dmn = getClass().getResourceAsStream("/dmn/tenant_A_basic.dmn");
        this.decision = engine.parseDecision("DetermineFields", dmn);
    }

    public List<FieldDefinition> evaluate(EvaluationContext ctx) {

        Map<String, Object> vars = new HashMap<>();

        vars.put("discount",
                ctx.getDiscounts().isEmpty()
                        ? ""
                        : ctx.getDiscounts().iterator().next().name());

        vars.put("age", ctx.getAge());
        vars.put("zone", ctx.getZone() != null ? ctx.getZone().name() : "");
        vars.put("spousePhone", ctx.getProfile().spousePhone().orElse(""));
        vars.put("addressVerified", ctx.isAddressVerified());

        System.out.println("DMN INPUT VARS = " + vars);

        DmnDecisionTableResult result = engine.evaluateDecisionTable(decision, vars);

        List<FieldDefinition> out = new ArrayList<>();

        for (DmnDecisionRuleResult rule : result) {
            Object val = rule.get("fields");
            if (val != null) {
                FieldRegistry.ensureKeysExistOrThrow(List.of(val.toString()));
                out.add(FieldRegistry.lookup(val.toString()).orElseThrow().build(ctx));
            }
        }

        return out;
    }
}
