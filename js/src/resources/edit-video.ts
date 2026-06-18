import type { HttpClient, PollingOptions, RequestOptions } from '@runapi.ai/core';
import { compactParams } from '@runapi.ai/core';
import { pollUntilComplete } from '@runapi.ai/core/internal';
import type { CompletedVideoTaskResponse, EditVideoParams, VideoTaskResponse } from '../types';

const ENDPOINT = '/api/v1/wan/edit_video';

/** Modifies existing videos guided by a text prompt and optional reference image. 2.6 models use source_video_urls (plural); 2.7 uses source_video_url (singular). */
export class EditVideo {
  constructor(private readonly http: HttpClient) {}

  /**
   * Create an edit video task and wait until complete.
   * @param params Edit video parameters.
   * @param options Per-request and polling overrides.
   * @returns The completed edit video response.
   */
  async run(params: EditVideoParams, options?: RequestOptions & PollingOptions): Promise<CompletedVideoTaskResponse> {
    const { id } = await this.create(params, options);
    const response = await pollUntilComplete<VideoTaskResponse>(() => this.get(id, options), {
      maxWaitMs: options?.maxWaitMs,
      pollIntervalMs: options?.pollIntervalMs,
    });
    return response as CompletedVideoTaskResponse;
  }

  /**
   * Create an edit video task; returns immediately with a task id.
   * @param params Edit video parameters.
   * @param options Per-request overrides.
   * @returns The task creation result.
   */
  async create(params: EditVideoParams, options?: RequestOptions): Promise<{ id: string }> {
    return this.http.request<{ id: string }>('POST', ENDPOINT, {
      body: compactParams(params),
      ...options,
    });
  }

  /**
   * Fetch the current status of an edit video task.
   * @param id The task id.
   * @param options Per-request overrides.
   * @returns The current edit video task status.
   */
  async get(id: string, options?: RequestOptions): Promise<VideoTaskResponse> {
    return this.http.request<VideoTaskResponse>('GET', `${ENDPOINT}/${id}`, options ?? {});
  }
}
