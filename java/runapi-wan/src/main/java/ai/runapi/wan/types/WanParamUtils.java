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

  static String requireNonBlank(String value, String name) {
    return ParamSupport.requireNonBlank(value, name);
  }

  static String requireNonBlankTrim(String value, String name) {
    return ParamSupport.requireNonBlankTrim(value, name);
  }

  static Map<String, Object> compact(Map<String, Object> raw) {
    return ParamSupport.compact(raw);
  }

  static List<String> strings(List<String> values) {
    return ParamSupport.strings(values);
  }

  static List<String> requiredStrings(List<String> values, String name) {
    return ParamSupport.requiredStrings(values, name);
  }

  static <T> List<T> list(List<T> values, String name) {
    return ParamSupport.list(values, name);
  }

  static <T> List<T> requiredList(List<T> values, String name) {
    return ParamSupport.requiredList(values, name);
  }

  static List<Map<String, Object>> maps(List<Map<String, Object>> values, String name) {
    return ParamSupport.maps(values, name);
  }

  static Object wireValue(Object value) {
    return ParamSupport.wireValue(value);
  }
}
