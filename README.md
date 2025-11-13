
# Dynamic Form PoC (pure Java, no Spring/JPA)

This PoC demonstrates **three approaches** to dynamic form fields for a card application use case.  
Everything is **in-memory** and written in **pure Java 17**, with a simple `main` runner that shows results for **two scenarios**

- **Simple:** selecting discount `TZP` ⇒ add field **„Číslo TZP preukazu“** (mandatory).
- **Complex:** any discount present **AND** age ≥ 65 **AND** zone ∈ {A, B} ⇒ add field **„Telefónne číslo manželky“**, pre-filled from profile if available.

## Approaches implemented

1. **Analytical Model** – configuration-driven model (`AdditionalData` + `Condition`) evaluated in code.
2. **Predicates** – a list of predicates bound to `(tenant, cardScheme)` producing field definitions.
3. **DMN-like Decision Table** – declarative rules returning *field keys*; those keys are resolved to FE definitions via a registry.

Each approach supports **multitenancy** and **per-card-scheme rules**.

## How to run

Pre-requisites: Java 17+ and Maven.

```bash
cd dynamic-form-poc
mvn -q -DskipTests package
mvn -q exec:java
```

You should see the output for both scenarios and for all three approaches.

## Project layout

- `com.example.dynamicform.domain` – core domain (context, enums, output definition).
- `com.example.dynamicform.approach1` – Analytical Model (config + evaluator).
- `com.example.dynamicform.approach2` – Predicates (per-tenant rules as lambdas).
- `com.example.dynamicform.approach3` – DMN-like decision table and field registry.
- `com.example.dynamicform.DemoRunner` – main class that runs both scenarios.

## Adding a new discount/condition in each approach

### 1) Analytical Model
- Define a new `Condition` (compose from the provided building blocks in `Conditions`).
- Register a new `AdditionalDataDefinition` with its `conditions` in `AnalyticalModelConfig` for a given `(tenant, scheme)`.
- No engine changes are required; only configuration.

### 2) Predicates
- Add a new lambda `PredicateRule` to `PredicateRulesRegistry` for a given `(tenant, scheme)`.
- The lambda receives `EvaluationContext` and returns `Optional<FieldDefinition>` (or multiple via helpers).

### 3) DMN table
- Add a new `DecisionRow` to the `DecisionTables` for a given `(tenant, scheme)`.
- The row returns one or more **field keys** (e.g. `TZP_CARD_ID`, `SPOUSE_PHONE`).  
- Ensure the key is present in `FieldRegistry` to map to FE definition (label, type, mandatory policy, prefill strategy).

## Deliverables

- Runnable Maven module with demo runner.
- This README.
- `COMPARISON.md` – comparison matrix + recommendation.

---

**Out of scope**: databases, Spring/JPA, UI/REST, security, performance.
