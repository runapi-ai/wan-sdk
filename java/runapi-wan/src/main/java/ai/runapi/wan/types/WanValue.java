package ai.runapi.wan.types;

import ai.runapi.core.types.RunApiValue;

// Thin package-local alias over the shared core base so wan value classes still
// `extends WanValue` while the logic lives in ai.runapi.core.types.RunApiValue.
// wan is hand-maintained (config/models.yml handwritten: true), so this mirrors
// the generated *Value shim by hand.
abstract class WanValue extends RunApiValue {
  WanValue(String value) {
    super(value);
  }
}
