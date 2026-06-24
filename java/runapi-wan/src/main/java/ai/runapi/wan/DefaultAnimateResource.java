package ai.runapi.wan;

import ai.runapi.core.ClientOptions;
import ai.runapi.core.RequestOptions;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.core.polling.TaskCreateResponse;
import ai.runapi.wan.resources.AnimateResource;
import ai.runapi.wan.types.AnimateParams;
import ai.runapi.wan.types.AnimateResponse;
import ai.runapi.wan.types.CompletedAnimateResponse;

final class DefaultAnimateResource extends WanResourceBase implements AnimateResource {
  DefaultAnimateResource(HttpTransport transport, ClientOptions options) {
    super(transport, options, AnimateResource.ENDPOINT);
  }

  /** Creates an animate task with default request options. */
  @Override
  public TaskCreateResponse create(AnimateParams params) {
    return create(params, RequestOptions.none());
  }

  /** Creates an animate task with request-specific options. */
  @Override
  public TaskCreateResponse create(AnimateParams params, RequestOptions options) {
    return createTask(params.action(), params.toMap(), options);
  }

  /** Retrieves an animate task by ID with default request options. */
  @Override
  public AnimateResponse get(String id) {
    return get(id, RequestOptions.none());
  }

  /** Retrieves an animate task by ID with request-specific options. */
  @Override
  public AnimateResponse get(String id, RequestOptions options) {
    return getTask(id, options, AnimateResponse.class);
  }

  /** Creates an animate task and waits for a completed response with default request options. */
  @Override
  public CompletedAnimateResponse run(AnimateParams params) {
    return run(params, RequestOptions.none());
  }

  /** Creates an animate task and waits for a completed response with request-specific options. */
  @Override
  public CompletedAnimateResponse run(AnimateParams params, RequestOptions options) {
    return runTask(params.action(), params.toMap(), options, AnimateResponse.class, CompletedAnimateResponse.class);
  }
}
