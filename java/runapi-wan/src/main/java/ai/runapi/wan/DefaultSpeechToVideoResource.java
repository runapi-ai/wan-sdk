package ai.runapi.wan;

import ai.runapi.core.ClientOptions;
import ai.runapi.core.RequestOptions;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.core.polling.TaskCreateResponse;
import ai.runapi.wan.resources.SpeechToVideoResource;
import ai.runapi.wan.types.CompletedSpeechToVideoResponse;
import ai.runapi.wan.types.SpeechToVideoParams;
import ai.runapi.wan.types.SpeechToVideoResponse;

final class DefaultSpeechToVideoResource extends WanResourceBase implements SpeechToVideoResource {
  DefaultSpeechToVideoResource(HttpTransport transport, ClientOptions options) {
    super(transport, options, SpeechToVideoResource.ENDPOINT);
  }

  /** Creates a speech to video task with default request options. */
  @Override
  public TaskCreateResponse create(SpeechToVideoParams params) {
    return create(params, RequestOptions.none());
  }

  /** Creates a speech to video task with request-specific options. */
  @Override
  public TaskCreateResponse create(SpeechToVideoParams params, RequestOptions options) {
    return createTask(params.action(), params.toMap(), options);
  }

  /** Retrieves a speech to video task by ID with default request options. */
  @Override
  public SpeechToVideoResponse get(String id) {
    return get(id, RequestOptions.none());
  }

  /** Retrieves a speech to video task by ID with request-specific options. */
  @Override
  public SpeechToVideoResponse get(String id, RequestOptions options) {
    return getTask(id, options, SpeechToVideoResponse.class);
  }

  /** Creates a speech to video task and waits for a completed response with default request options. */
  @Override
  public CompletedSpeechToVideoResponse run(SpeechToVideoParams params) {
    return run(params, RequestOptions.none());
  }

  /** Creates a speech to video task and waits for a completed response with request-specific options. */
  @Override
  public CompletedSpeechToVideoResponse run(SpeechToVideoParams params, RequestOptions options) {
    return runTask(
        params.action(), params.toMap(), options, SpeechToVideoResponse.class, CompletedSpeechToVideoResponse.class);
  }
}
