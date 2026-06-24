package ai.runapi.wan.resources;

import ai.runapi.core.RequestOptions;
import ai.runapi.core.polling.TaskCreateResponse;
import ai.runapi.wan.types.CompletedTextToVideoResponse;
import ai.runapi.wan.types.TextToVideoParams;
import ai.runapi.wan.types.TextToVideoResponse;

/** Text-to-video generation operations. */
public interface TextToVideoResource {
  /** API endpoint path for text-to-video tasks. */
  String ENDPOINT = "/api/v1/wan/text_to_video";

  /** Creates a text-to-video task. */
  TaskCreateResponse create(TextToVideoParams params);

  /** Creates a text-to-video task with per-request options. */
  TaskCreateResponse create(TextToVideoParams params, RequestOptions options);

  /** Retrieves a text-to-video task by ID. */
  TextToVideoResponse get(String id);

  /** Retrieves a text-to-video task by ID with per-request options. */
  TextToVideoResponse get(String id, RequestOptions options);

  /** Creates a text-to-video task and polls until it completes. */
  CompletedTextToVideoResponse run(TextToVideoParams params);

  /** Creates a text-to-video task with per-request options and polls until it completes. */
  CompletedTextToVideoResponse run(TextToVideoParams params, RequestOptions options);
}
