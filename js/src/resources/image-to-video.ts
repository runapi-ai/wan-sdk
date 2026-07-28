import type { HttpClient, PollingOptions, RequestOptions, ActionSchema } from '@runapi.ai/core';
import { compactParams, validateParams } from '@runapi.ai/core';
import { pollUntilComplete } from '@runapi.ai/core/internal';
import { contract } from '../contract_gen';
import type { CompletedVideoTaskResponse, ImageToVideoParams, TaskCreateResponse, VideoTaskResponse } from '../types';

const ENDPOINT = '/api/v1/wan/image_to_video';

/** Generates videos driven by a source image. Flash variants trade fidelity for speed; 2.7 adds last-frame control, video continuation, and audio features. */
export class ImageToVideo {
  constructor(private readonly http: HttpClient) {}

  /**
   * Create an image to video task and wait until complete.
   * @param params Image to video parameters.
   * @param options Per-request and polling overrides.
   * @returns The completed image to video response.
   */
  async run(params: ImageToVideoParams, options?: RequestOptions & PollingOptions): Promise<CompletedVideoTaskResponse> {
    const { id } = await this.create(params, options);
    const response = await pollUntilComplete<VideoTaskResponse>(() => this.get(id, options), {
      maxWaitMs: options?.maxWaitMs,
      pollIntervalMs: options?.pollIntervalMs,
    });
    return response as CompletedVideoTaskResponse;
  }

  /**
   * Create an image to video task; returns immediately with a task id.
   * @param params Image to video parameters.
   * @param options Per-request overrides.
   * @returns The task creation result.
   */
  async create(params: ImageToVideoParams, options?: RequestOptions): Promise<TaskCreateResponse> {
    const body = compactParams(params);
    validateParams(contract['image-to-video'] as ActionSchema, body as Record<string, unknown>);
    return this.http.request<TaskCreateResponse>('POST', ENDPOINT, {
      body,
      ...options,
    });
  }

  /**
   * Fetch the current status of an image to video task.
   * @param id The task id.
   * @param options Per-request overrides.
   * @returns The current image to video task status.
   */
  async get(id: string, options?: RequestOptions): Promise<VideoTaskResponse> {
    return this.http.request<VideoTaskResponse>('GET', `${ENDPOINT}/${id}`, options ?? {});
  }
}
