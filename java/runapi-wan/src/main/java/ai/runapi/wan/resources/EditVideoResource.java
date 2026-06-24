package ai.runapi.wan.resources;

import ai.runapi.core.RequestOptions;
import ai.runapi.core.polling.TaskCreateResponse;
import ai.runapi.wan.types.CompletedEditVideoResponse;
import ai.runapi.wan.types.EditVideoParams;
import ai.runapi.wan.types.EditVideoResponse;

/** Video editing operations. */
public interface EditVideoResource {
  /** API endpoint path for video editing tasks. */
  String ENDPOINT = "/api/v1/wan/edit_video";

  /** Creates a video editing task. */
  TaskCreateResponse create(EditVideoParams params);

  /** Creates a video editing task with per-request options. */
  TaskCreateResponse create(EditVideoParams params, RequestOptions options);

  /** Retrieves a video editing task by ID. */
  EditVideoResponse get(String id);

  /** Retrieves a video editing task by ID with per-request options. */
  EditVideoResponse get(String id, RequestOptions options);

  /** Creates a video editing task and polls until it completes. */
  CompletedEditVideoResponse run(EditVideoParams params);

  /** Creates a video editing task with per-request options and polls until it completes. */
  CompletedEditVideoResponse run(EditVideoParams params, RequestOptions options);
}
