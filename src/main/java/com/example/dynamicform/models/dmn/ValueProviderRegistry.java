package com.example.dynamicform.models.dmn;

import com.example.dynamicform.domain.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ValueProviderRegistry {

    private static final ExpressionParser PARSER = new SpelExpressionParser();
    private static final Map<String, Expression> CACHE = new ConcurrentHashMap<>();

    public static String provide(String expr, EvaluationContext ctx) {
        if (expr == null || expr.isBlank()) {
            return null;
        }

        expr = stripQuotes(expr);

        Expression compiled = CACHE.computeIfAbsent(expr, PARSER::parseExpression);

        StandardEvaluationContext spelCtx = new StandardEvaluationContext(ctx);
        spelCtx.setVariable("profile", ctx.getProfile());
        spelCtx.setVariable("cardScheme", ctx.getCardScheme());
        spelCtx.setVariable("zone", ctx.getZone());

        Object value = compiled.getValue(spelCtx);
        return value != null ? value.toString() : null;
    }

    private static String stripQuotes(String s) {
        if (s.length() >= 2 && s.startsWith("\"") && s.endsWith("\"")) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }
}
