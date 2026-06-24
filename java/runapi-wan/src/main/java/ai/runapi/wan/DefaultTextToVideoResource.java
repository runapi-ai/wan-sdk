package ai.runapi.wan;

import ai.runapi.core.ClientOptions;
import ai.runapi.core.RequestOptions;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.core.polling.TaskCreateResponse;
import ai.runapi.wan.resources.TextToVideoResource;
import ai.runapi.wan.types.CompletedTextToVideoResponse;
import ai.runapi.wan.types.TextToVideoParams;
import ai.runapi.wan.types.TextToVideoResponse;

final class DefaultTextToVideoResource extends WanResourceBase implements TextToVideoResource {
  DefaultTextToVideoResource(HttpTransport transport, ClientOptions options) {
    super(transport, options, TextToVideoResource.ENDPOINT);
  }

  /** Creates a text to video task with default request options. */
  @Override
  public TaskCreateResponse create(TextToVideoParams params) {
    return create(params, RequestOptions.none());
  }

  /** Creates a text to video task with request-specific options. */
  @Override
  public TaskCreateResponse create(TextToVideoParams params, RequestOptions options) {
    return createTask(params.action(), params.toMap(), options);
  }

  /** Retrieves a text to video task by ID with default request options. */
  @Override
  public TextToVideoResponse get(String id) {
    return get(id, RequestOptions.none());
  }

  /** Retrieves a text to video task by ID with request-specific options. */
  @Override
  public TextToVideoResponse get(String id, RequestOptions options) {
    return getTask(id, options, TextToVideoResponse.class);
  }

  /** Creates a text to video task and waits for a completed response with default request options. */
  @Override
  public CompletedTextToVideoResponse run(TextToVideoParams params) {
    return run(params, RequestOptions.none());
  }

  /** Creates a text to video task and waits for a completed response with request-specific options. */
  @Override
  public CompletedTextToVideoResponse run(TextToVideoParams params, RequestOptions options) {
    return runTask(params.action(), params.toMap(), options, TextToVideoResponse.class, CompletedTextToVideoResponse.class);
  }
}
