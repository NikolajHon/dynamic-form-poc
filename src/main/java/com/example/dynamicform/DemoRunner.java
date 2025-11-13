
package com.example.dynamicform;

import com.example.dynamicform.models.analyticky_model.AnalyticalModelEvaluator;
import com.example.dynamicform.models.predikaty.PredicatesEngine;
import com.example.dynamicform.models.dmn.DecisionTableEngine;
import com.example.dynamicform.domain.*;

import java.util.List;
import java.util.Set;

public class DemoRunner {

    public static void main(String[] args) {
        EvaluationContext ctxSimple = new EvaluationContext(
                "TENANT_A",
                CardScheme.BASIC,
                Set.of(Discount.TZP),
                30,
                Zone.C,
                new Profile(null),
                true
        );

        EvaluationContext ctxComplex = new EvaluationContext(
                "TENANT_A",
                CardScheme.BASIC,
                Set.of(Discount.SENIOR),
                70,
                Zone.A,
                new Profile("+421900123456"),
                true
        );
        EvaluationContext ctxChild = new EvaluationContext(
                "TENANT_A",
                CardScheme.BASIC,
                Set.of(),
                12,
                Zone.C,
                new Profile(null),
                false
        );

        AnalyticalModelEvaluator analytical = new AnalyticalModelEvaluator();
        PredicatesEngine predicates = new PredicatesEngine();
        DecisionTableEngine dmn = new DecisionTableEngine();

        runScenario("SIMPLE (discount TZP)", ctxSimple, analytical, predicates, dmn);
        runScenario("COMPLEX (discount present + age>=65 + zone A/B)", ctxComplex, analytical, predicates, dmn);
        runScenario("CHILD RULE (age < 15 + zone C)", ctxChild, analytical, predicates, dmn);

    }

    private static void runScenario(String title, EvaluationContext ctx,
                                   AnalyticalModelEvaluator analytical,
                                   PredicatesEngine predicates,
                                   DecisionTableEngine dmn) {
        System.out.println("==================================================");
        System.out.println("Scenario: " + title);
        System.out.println("Context:  " + ctx);
        System.out.println("--------------------------------------------------");

        print("Approach #1 – Analytical Model", analytical.evaluate(ctx));
        print("Approach #2 – Predicates", predicates.evaluate(ctx));
        print("Approach #3 – DMN-like table", dmn.evaluate(ctx));

        System.out.println();
    }

    private static void print(String header, List<FieldDefinition> fields) {
        System.out.println(header);
        if (fields.isEmpty()) {
            System.out.println("  (no dynamic fields)");
            return;
        }
        for (var f : fields) {
            System.out.println("  - " + f);
        }
    }
}
