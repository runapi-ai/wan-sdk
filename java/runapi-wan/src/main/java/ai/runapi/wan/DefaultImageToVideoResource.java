package ai.runapi.wan;

import ai.runapi.core.ClientOptions;
import ai.runapi.core.RequestOptions;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.core.polling.TaskCreateResponse;
import ai.runapi.wan.resources.ImageToVideoResource;
import ai.runapi.wan.types.CompletedImageToVideoResponse;
import ai.runapi.wan.types.ImageToVideoParams;
import ai.runapi.wan.types.ImageToVideoResponse;

final class DefaultImageToVideoResource extends WanResourceBase implements ImageToVideoResource {
  DefaultImageToVideoResource(HttpTransport transport, ClientOptions options) {
    super(transport, options, ImageToVideoResource.ENDPOINT);
  }

  /** Creates an image to video task with default request options. */
  @Override
  public TaskCreateResponse create(ImageToVideoParams params) {
    return create(params, RequestOptions.none());
  }

  /** Creates an image to video task with request-specific options. */
  @Override
  public TaskCreateResponse create(ImageToVideoParams params, RequestOptions options) {
    return createTask(params.action(), params.toMap(), options);
  }

  /** Retrieves an image to video task by ID with default request options. */
  @Override
  public ImageToVideoResponse get(String id) {
    return get(id, RequestOptions.none());
  }

  /** Retrieves an image to video task by ID with request-specific options. */
  @Override
  public ImageToVideoResponse get(String id, RequestOptions options) {
    return getTask(id, options, ImageToVideoResponse.class);
  }

  /** Creates an image to video task and waits for a completed response with default request options. */
  @Override
  public CompletedImageToVideoResponse run(ImageToVideoParams params) {
    return run(params, RequestOptions.none());
  }

  /** Creates an image to video task and waits for a completed response with request-specific options. */
  @Override
  public CompletedImageToVideoResponse run(ImageToVideoParams params, RequestOptions options) {
    return runTask(
        params.action(), params.toMap(), options, ImageToVideoResponse.class, CompletedImageToVideoResponse.class);
  }
}
