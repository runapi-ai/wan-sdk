package ai.runapi.wan.types;

import ai.runapi.core.types.ParamSupport;
import java.util.List;
import java.util.Map;

// Thin package-local facade delegating to the shared core helper so wan call
// sites (WanParamUtils.compact/list/...) keep working while the logic lives in
// ai.runapi.core.types.ParamSupport. wan is hand-maintained (config/models.yml
// handwritten: true), so this mirrors the generated *ParamUtils shim by hand.
final class WanParamUtils {
  private WanParamUtils() {}

  static Map<String, Object> compact(Map<String, Object> raw) {
    return ParamSupport.compact(raw);
  }

  static List<String> strings(List<String> values) {
    return ParamSupport.strings(values);
  }

  static <T> List<T> list(List<T> values) {
    return ParamSupport.list(values);
  }

  static List<Map<String, Object>> maps(List<Map<String, Object>> values) {
    return ParamSupport.maps(values);
  }

  static Object wireValue(Object value) {
    return ParamSupport.wireValue(value);
  }
}
