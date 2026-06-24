import type { HttpClient, PollingOptions, RequestOptions, ActionSchema } from '@runapi.ai/core';
import { compactParams, validateParams } from '@runapi.ai/core';
import { pollUntilComplete } from '@runapi.ai/core/internal';
import { contract } from '../contract_gen';
import type { CompletedImageTaskResponse, TextToImageParams, ImageTaskResponse } from '../types';

const ENDPOINT = '/api/v1/wan/text_to_image';

/** Generates images from text prompts with optional color palette and bounding box constraints. Pro model adds thinking_mode for enhanced prompt reasoning. */
export class TextToImage {
  constructor(private readonly http: HttpClient) {}

  /**
   * Create a text to image task and wait until complete.
   * @param params Text to image parameters.
   * @param options Per-request and polling overrides.
   * @returns The completed text to image response.
   */
  async run(params: TextToImageParams, options?: RequestOptions & PollingOptions): Promise<CompletedImageTaskResponse> {
    const { id } = await this.create(params, options);
    const response = await pollUntilComplete<ImageTaskResponse>(() => this.get(id, options), {
      maxWaitMs: options?.maxWaitMs,
      pollIntervalMs: options?.pollIntervalMs,
    });
    return response as CompletedImageTaskResponse;
  }

  /**
   * Create a text to image task; returns immediately with a task id.
   * @param params Text to image parameters.
   * @param options Per-request overrides.
   * @returns The task creation result.
   */
  async create(params: TextToImageParams, options?: RequestOptions): Promise<{ id: string }> {
    const body = compactParams(params);
    validateParams(contract['text-to-image'] as ActionSchema, body as Record<string, unknown>);
    return this.http.request<{ id: string }>('POST', ENDPOINT, {
      body,
      ...options,
    });
  }

  /**
   * Fetch the current status of a text to image task.
   * @param id The task id.
   * @param options Per-request overrides.
   * @returns The current text to image task status.
   */
  async get(id: string, options?: RequestOptions): Promise<ImageTaskResponse> {
    return this.http.request<ImageTaskResponse>('GET', `${ENDPOINT}/${id}`, options ?? {});
  }
}
