package ai.runapi.wan;

import ai.runapi.core.ClientOptions;
import ai.runapi.core.RequestOptions;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.core.polling.TaskCreateResponse;
import ai.runapi.wan.resources.TextToImageResource;
import ai.runapi.wan.types.CompletedTextToImageResponse;
import ai.runapi.wan.types.TextToImageParams;
import ai.runapi.wan.types.TextToImageResponse;

final class DefaultTextToImageResource extends WanResourceBase implements TextToImageResource {
  DefaultTextToImageResource(HttpTransport transport, ClientOptions options) {
    super(transport, options, TextToImageResource.ENDPOINT);
  }

  /** Creates a text to image task with default request options. */
  @Override
  public TaskCreateResponse create(TextToImageParams params) {
    return create(params, RequestOptions.none());
  }

  /** Creates a text to image task with request-specific options. */
  @Override
  public TaskCreateResponse create(TextToImageParams params, RequestOptions options) {
    return createTask(params.action(), params.toMap(), options);
  }

  /** Retrieves a text to image task by ID with default request options. */
  @Override
  public TextToImageResponse get(String id) {
    return get(id, RequestOptions.none());
  }

  /** Retrieves a text to image task by ID with request-specific options. */
  @Override
  public TextToImageResponse get(String id, RequestOptions options) {
    return getTask(id, options, TextToImageResponse.class);
  }

  /** Creates a text to image task and waits for a completed response with default request options. */
  @Override
  public CompletedTextToImageResponse run(TextToImageParams params) {
    return run(params, RequestOptions.none());
  }

  /** Creates a text to image task and waits for a completed response with request-specific options. */
  @Override
  public CompletedTextToImageResponse run(TextToImageParams params, RequestOptions options) {
    return runTask(params.action(), params.toMap(), options, TextToImageResponse.class, CompletedTextToImageResponse.class);
  }
}
