package ai.runapi.wan;

import ai.runapi.core.ClientOptions;
import ai.runapi.core.RequestOptions;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.core.polling.TaskCreateResponse;
import ai.runapi.wan.resources.EditVideoResource;
import ai.runapi.wan.types.CompletedEditVideoResponse;
import ai.runapi.wan.types.EditVideoParams;
import ai.runapi.wan.types.EditVideoResponse;

final class DefaultEditVideoResource extends WanResourceBase implements EditVideoResource {
  DefaultEditVideoResource(HttpTransport transport, ClientOptions options) {
    super(transport, options, EditVideoResource.ENDPOINT);
  }

  /** Creates an edit video task with default request options. */
  @Override
  public TaskCreateResponse create(EditVideoParams params) {
    return create(params, RequestOptions.none());
  }

  /** Creates an edit video task with request-specific options. */
  @Override
  public TaskCreateResponse create(EditVideoParams params, RequestOptions options) {
    return createTask(params.action(), params.toMap(), options);
  }

  /** Retrieves an edit video task by ID with default request options. */
  @Override
  public EditVideoResponse get(String id) {
    return get(id, RequestOptions.none());
  }

  /** Retrieves an edit video task by ID with request-specific options. */
  @Override
  public EditVideoResponse get(String id, RequestOptions options) {
    return getTask(id, options, EditVideoResponse.class);
  }

  /** Creates an edit video task and waits for a completed response with default request options. */
  @Override
  public CompletedEditVideoResponse run(EditVideoParams params) {
    return run(params, RequestOptions.none());
  }

  /** Creates an edit video task and waits for a completed response with request-specific options. */
  @Override
  public CompletedEditVideoResponse run(EditVideoParams params, RequestOptions options) {
    return runTask(params.action(), params.toMap(), options, EditVideoResponse.class, CompletedEditVideoResponse.class);
  }
}
