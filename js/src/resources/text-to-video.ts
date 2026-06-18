import type { HttpClient, PollingOptions, RequestOptions } from '@runapi.ai/core';
import { compactParams } from '@runapi.ai/core';
import { pollUntilComplete } from '@runapi.ai/core/internal';
import type { CompletedVideoTaskResponse, TextToVideoParams, VideoTaskResponse } from '../types';

const ENDPOINT = '/api/v1/wan/text_to_video';

/** Generates videos from text prompts. Supports turbo (2.2) through 2.7 with progressive feature upgrades including negative prompts, watermark control, and R2V multi-reference inputs. */
export class TextToVideo {
  constructor(private readonly http: HttpClient) {}

  /**
   * Create a text to video task and wait until complete.
   * @param params Text to video parameters.
   * @param options Per-request and polling overrides.
   * @returns The completed text to video response.
   */
  async run(params: TextToVideoParams, options?: RequestOptions & PollingOptions): Promise<CompletedVideoTaskResponse> {
    const { id } = await this.create(params, options);
    const response = await pollUntilComplete<VideoTaskResponse>(() => this.get(id, options), {
      maxWaitMs: options?.maxWaitMs,
      pollIntervalMs: options?.pollIntervalMs,
    });
    return response as CompletedVideoTaskResponse;
  }

  /**
   * Create a text to video task; returns immediately with a task id.
   * @param params Text to video parameters.
   * @param options Per-request overrides.
   * @returns The task creation result.
   */
  async create(params: TextToVideoParams, options?: RequestOptions): Promise<{ id: string }> {
    return this.http.request<{ id: string }>('POST', ENDPOINT, {
      body: compactParams(params),
      ...options,
    });
  }

  /**
   * Fetch the current status of a text to video task.
   * @param id The task id.
   * @param options Per-request overrides.
   * @returns The current text to video task status.
   */
  async get(id: string, options?: RequestOptions): Promise<VideoTaskResponse> {
    return this.http.request<VideoTaskResponse>('GET', `${ENDPOINT}/${id}`, options ?? {});
  }
}
