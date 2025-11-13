
# Comparison & Recommendation

| Criterion | Analytical Model | Predicates | DMN-like Decision Table |
|---|---|---|---|
| Scenario coverage | ✅ Complex rules via composable `Condition`s; reusable and testable | ✅ Anything expressible in Java; very flexible | ✅ Clear, declarative; supports multi-hit and priorities |
| Extensibility | ✅ Add definitions & conditions (mostly config code) | ⚠️ Add new lambdas; risk of scattering rules | ✅ Add rows to tables; add field keys to registry |
| Implementation complexity | ⚠️ Medium (build a small DSL: `Condition`, `AdditionalDataDefinition`) | ✅ Low (just lambdas) | ⚠️ Medium (table runner + registry) |
| Maintainability | ✅ Good: centralized config per `(tenant, scheme)` | ⚠️ Could become fragmented; discipline required | ✅ Good: rules readable in table form |
| FE/BE contract stability | ✅ Stable `FieldDefinition` output | ✅ Stable `FieldDefinition` output | ✅ Stable `FieldDefinition` output (keys resolved via registry) |
| Multitenancy | ✅ Natural via namespacing config | ✅ Natural via rule lists per tenant/scheme | ✅ Natural via separate decision tables |
| Risk of regressions | ⚠️ Moderate if conditions are duplicated | ⚠️ Higher—code spread across lambdas | ✅ Lower—rows are isolated and evaluated uniformly |

## Recommendation

For long‑term evolution with many tenants and changing business logic, choose the **DMN‑like decision table** approach:

- Business rules stay **declarative and localized** in a table.
- Output to FE remains **stable** through the `FieldRegistry`.
- Easy to audit which rules apply to a given `(tenant, scheme)`.
- New scenarios typically mean **adding a row** (minimal code churn).

