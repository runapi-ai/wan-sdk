import type { HttpClient, PollingOptions, RequestOptions } from '@runapi.ai/core';
import { compactParams } from '@runapi.ai/core';
import { pollUntilComplete } from '@runapi.ai/core/internal';
import type { CompletedVideoTaskResponse, SpeechToVideoParams, VideoTaskResponse } from '../types';

const ENDPOINT = '/api/v1/wan/speech_to_video';

/** Generates lip-synced talking-head videos from a portrait image and speech audio. */
export class SpeechToVideo {
  constructor(private readonly http: HttpClient) {}

  /**
   * Create a speech to video task and wait until complete.
   * @param params Speech to video parameters.
   * @param options Per-request and polling overrides.
   * @returns The completed speech to video response.
   */
  async run(params: SpeechToVideoParams, options?: RequestOptions & PollingOptions): Promise<CompletedVideoTaskResponse> {
    const { id } = await this.create(params, options);
    const response = await pollUntilComplete<VideoTaskResponse>(() => this.get(id, options), {
      maxWaitMs: options?.maxWaitMs,
      pollIntervalMs: options?.pollIntervalMs,
    });
    return response as CompletedVideoTaskResponse;
  }

  /**
   * Create a speech to video task; returns immediately with a task id.
   * @param params Speech to video parameters.
   * @param options Per-request overrides.
   * @returns The task creation result.
   */
  async create(params: SpeechToVideoParams, options?: RequestOptions): Promise<{ id: string }> {
    return this.http.request<{ id: string }>('POST', ENDPOINT, {
      body: compactParams(params),
      ...options,
    });
  }

  /**
   * Fetch the current status of a speech to video task.
   * @param id The task id.
   * @param options Per-request overrides.
   * @returns The current speech to video task status.
   */
  async get(id: string, options?: RequestOptions): Promise<VideoTaskResponse> {
    return this.http.request<VideoTaskResponse>('GET', `${ENDPOINT}/${id}`, options ?? {});
  }
}
