package ai.runapi.wan.resources;

import ai.runapi.core.RequestOptions;
import ai.runapi.core.polling.TaskCreateResponse;
import ai.runapi.wan.types.AnimateParams;
import ai.runapi.wan.types.AnimateResponse;
import ai.runapi.wan.types.CompletedAnimateResponse;

/** Animation operations. */
public interface AnimateResource {
  /** API endpoint path for animation tasks. */
  String ENDPOINT = "/api/v1/wan/animate";

  /** Creates an animation task. */
  TaskCreateResponse create(AnimateParams params);

  /** Creates an animation task with per-request options. */
  TaskCreateResponse create(AnimateParams params, RequestOptions options);

  /** Retrieves an animation task by ID. */
  AnimateResponse get(String id);

  /** Retrieves an animation task by ID with per-request options. */
  AnimateResponse get(String id, RequestOptions options);

  /** Creates an animation task and polls until it completes. */
  CompletedAnimateResponse run(AnimateParams params);

  /** Creates an animation task with per-request options and polls until it completes. */
  CompletedAnimateResponse run(AnimateParams params, RequestOptions options);
}
