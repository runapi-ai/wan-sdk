package ai.runapi.wan.resources;

import ai.runapi.core.RequestOptions;
import ai.runapi.core.polling.TaskCreateResponse;
import ai.runapi.wan.types.CompletedTextToImageResponse;
import ai.runapi.wan.types.TextToImageParams;
import ai.runapi.wan.types.TextToImageResponse;

/** Text-to-image generation operations. */
public interface TextToImageResource {
  /** API endpoint path for text-to-image tasks. */
  String ENDPOINT = "/api/v1/wan/text_to_image";

  /** Creates a text-to-image task. */
  TaskCreateResponse create(TextToImageParams params);

  /** Creates a text-to-image task with per-request options. */
  TaskCreateResponse create(TextToImageParams params, RequestOptions options);

  /** Retrieves a text-to-image task by ID. */
  TextToImageResponse get(String id);

  /** Retrieves a text-to-image task by ID with per-request options. */
  TextToImageResponse get(String id, RequestOptions options);

  /** Creates a text-to-image task and polls until it completes. */
  CompletedTextToImageResponse run(TextToImageParams params);

  /** Creates a text-to-image task with per-request options and polls until it completes. */
  CompletedTextToImageResponse run(TextToImageParams params, RequestOptions options);
}
