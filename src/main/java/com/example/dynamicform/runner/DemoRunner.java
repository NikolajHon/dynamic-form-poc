package com.example.dynamicform.runner;

import com.example.dynamicform.domain.*;
import com.example.dynamicform.models.analyticky_model.AnalyticalModelEvaluator;
import com.example.dynamicform.models.predikaty.PredicatesEngine;
import com.example.dynamicform.models.dmn.DecisionTableEngine;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class DemoRunner implements CommandLineRunner {

    private final AnalyticalModelEvaluator analytical;
    private final PredicatesEngine predicates;
    private final DecisionTableEngine dmn;

    public DemoRunner(
            AnalyticalModelEvaluator analytical,
            PredicatesEngine predicates,
            DecisionTableEngine dmn
    ) {
        this.analytical = analytical;
        this.predicates = predicates;
        this.dmn = dmn;
    }

    @Override
    public void run(String... args) {

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

        runScenario("SIMPLE (discount TZP)", ctxSimple);
        runScenario("COMPLEX (discount present + age>=65 + zone A/B)", ctxComplex);
        runScenario("CHILD RULE (age < 15 + zone C)", ctxChild);
    }

    private void runScenario(String title, EvaluationContext ctx) {
        System.out.println("==================================================");
        System.out.println("Scenario: " + title);
        System.out.println("Context:  " + ctx);
        System.out.println("--------------------------------------------------");

        print("Approach #1 – Analytical Model", analytical.evaluate(ctx));
        print("Approach #2 – Predicates", predicates.evaluate(ctx));
        print("Approach #3 – DMN-like table", dmn.evaluate(ctx));

        System.out.println();
    }

    private void print(String header, List<FieldDefinition> fields) {
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
