import type { HttpClient, PollingOptions, RequestOptions } from '@runapi.ai/core';
import { compactParams } from '@runapi.ai/core';
import { pollUntilComplete } from '@runapi.ai/core/internal';
import type { AnimateParams, CompletedVideoTaskResponse, VideoTaskResponse } from '../types';

const ENDPOINT = '/api/v1/wan/animate';

/** Transfers motion from a reference video onto a subject image. Use animate-move to keep the original subject, or animate-replace to swap it with the reference video's subject. */
export class Animate {
  constructor(private readonly http: HttpClient) {}

  /**
   * Create an animate task and wait until complete.
   * @param params Animate parameters.
   * @param options Per-request and polling overrides.
   * @returns The completed animate response.
   */
  async run(params: AnimateParams, options?: RequestOptions & PollingOptions): Promise<CompletedVideoTaskResponse> {
    const { id } = await this.create(params, options);
    const response = await pollUntilComplete<VideoTaskResponse>(() => this.get(id, options), {
      maxWaitMs: options?.maxWaitMs,
      pollIntervalMs: options?.pollIntervalMs,
    });
    return response as CompletedVideoTaskResponse;
  }

  /**
   * Create an animate task; returns immediately with a task id.
   * @param params Animate parameters.
   * @param options Per-request overrides.
   * @returns The task creation result.
   */
  async create(params: AnimateParams, options?: RequestOptions): Promise<{ id: string }> {
    return this.http.request<{ id: string }>('POST', ENDPOINT, {
      body: compactParams(params),
      ...options,
    });
  }

  /**
   * Fetch the current status of an animate task.
   * @param id The task id.
   * @param options Per-request overrides.
   * @returns The current animate task status.
   */
  async get(id: string, options?: RequestOptions): Promise<VideoTaskResponse> {
    return this.http.request<VideoTaskResponse>('GET', `${ENDPOINT}/${id}`, options ?? {});
  }
}
