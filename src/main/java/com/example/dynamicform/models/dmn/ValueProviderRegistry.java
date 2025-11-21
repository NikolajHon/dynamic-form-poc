package com.example.dynamicform.models.dmn;

import com.example.dynamicform.domain.EvaluationContext;

import java.util.Map;

public class ValueProviderRegistry {

    private static final Map<String, ValueProvider> PROVIDERS = Map.of(
            "PROFILE.SPOUSE_PHONE", ctx -> ctx.getProfile().spousePhone().orElse(null),
            "CTX.AGE", ctx -> Integer.toString(ctx.getAge()),
            "CTX.CARD_SCHEME", ctx -> ctx.getCardScheme().name()
    );

    public static String provide(String key, EvaluationContext ctx) {
        var provider = PROVIDERS.get(key);
        return provider != null ? provider.get(ctx) : null;
    }
}
