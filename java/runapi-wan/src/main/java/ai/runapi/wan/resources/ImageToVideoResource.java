package ai.runapi.wan.resources;

import ai.runapi.core.RequestOptions;
import ai.runapi.core.polling.TaskCreateResponse;
import ai.runapi.wan.types.CompletedImageToVideoResponse;
import ai.runapi.wan.types.ImageToVideoParams;
import ai.runapi.wan.types.ImageToVideoResponse;

/** Image-to-video generation operations. */
public interface ImageToVideoResource {
  /** API endpoint path for image-to-video tasks. */
  String ENDPOINT = "/api/v1/wan/image_to_video";

  /** Creates an image-to-video task. */
  TaskCreateResponse create(ImageToVideoParams params);

  /** Creates an image-to-video task with per-request options. */
  TaskCreateResponse create(ImageToVideoParams params, RequestOptions options);

  /** Retrieves an image-to-video task by ID. */
  ImageToVideoResponse get(String id);

  /** Retrieves an image-to-video task by ID with per-request options. */
  ImageToVideoResponse get(String id, RequestOptions options);

  /** Creates an image-to-video task and polls until it completes. */
  CompletedImageToVideoResponse run(ImageToVideoParams params);

  /** Creates an image-to-video task with per-request options and polls until it completes. */
  CompletedImageToVideoResponse run(ImageToVideoParams params, RequestOptions options);
}
