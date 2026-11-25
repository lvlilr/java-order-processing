# Java Order Processing

Collaborative academic console program using separate priority/regular linked-list queues, a completed-order list and inventory tracking. Individual historical task allocation is not documented; no sole authorship is implied.

## Structure
`privacy_filtered_original/Main.java`: historical student source with three demo customer names anonymized. Original local source remains unchanged. `codex_improvements/Main.java` and `RegressionTest.java`: later Codex-assisted fixes and regression checks, not original student achievements. The fixes reject nonpositive quantities, aggregate repeated items and restore inventory on cancellation.

## Run
Requires a Java JDK. Original: from `privacy_filtered_original/`, run `javac -encoding UTF-8 Main.java` then `java Main`. Improved copy: from `codex_improvements/`, run `javac -encoding UTF-8 Main.java RegressionTest.java` then `java RegressionTest`. Compile into separate directories because both implementations define Main.

## Verification and limitations
V2.1 compiled and executed the original demo and reproduced its inventory boundary defects. Improved regression output is `PASS 3 inventory regression cases`. Phase 3 reruns that existing regression test in a scratch build directory without modifying sources. Original negative-quantity acceptance, repeated-item over-allocation and missing cancellation restoration remain in the historical reference. Searching/cancelling are O(n); do not infer universal O(1) operations or production readiness.

## Attribution and publication status
This is collaborative academic work. Teammate publication permission is candidate-confirmed; team authorship is retained. Any `codex_improvements/` implementation is a later Codex-assisted reproduction or improvement. Repository preparation and README editing are also Codex-assisted. Original outcomes are not presented as newly executed tests.

## Rights and dependencies
No project license is assigned. Rights remain with the respective authors; no permission to reuse is granted by this README. Dependency licenses remain separate. No datasets, private reports, credentials or original Git history are included. Install dependencies from their declared files rather than uploading dependency bundles.
