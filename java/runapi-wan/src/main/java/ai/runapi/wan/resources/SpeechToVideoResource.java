package ai.runapi.wan.resources;

import ai.runapi.core.RequestOptions;
import ai.runapi.core.polling.TaskCreateResponse;
import ai.runapi.wan.types.CompletedSpeechToVideoResponse;
import ai.runapi.wan.types.SpeechToVideoParams;
import ai.runapi.wan.types.SpeechToVideoResponse;

/** Speech-to-video generation operations. */
public interface SpeechToVideoResource {
  /** API endpoint path for speech-to-video tasks. */
  String ENDPOINT = "/api/v1/wan/speech_to_video";

  /** Creates a speech-to-video task. */
  TaskCreateResponse create(SpeechToVideoParams params);

  /** Creates a speech-to-video task with per-request options. */
  TaskCreateResponse create(SpeechToVideoParams params, RequestOptions options);

  /** Retrieves a speech-to-video task by ID. */
  SpeechToVideoResponse get(String id);

  /** Retrieves a speech-to-video task by ID with per-request options. */
  SpeechToVideoResponse get(String id, RequestOptions options);

  /** Creates a speech-to-video task and polls until it completes. */
  CompletedSpeechToVideoResponse run(SpeechToVideoParams params);

  /** Creates a speech-to-video task with per-request options and polls until it completes. */
  CompletedSpeechToVideoResponse run(SpeechToVideoParams params, RequestOptions options);
}
