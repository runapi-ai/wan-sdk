import type { HttpClient, PollingOptions, RequestOptions } from '@runapi.ai/core';
import { compactParams } from '@runapi.ai/core';
import { pollUntilComplete } from '@runapi.ai/core/internal';
import type { CompletedImageTaskResponse, TextToImageParams, ImageTaskResponse } from '../types';

const ENDPOINT = '/api/v1/wan/text_to_image';

export class TextToImage {
  constructor(private readonly http: HttpClient) {}

  async run(params: TextToImageParams, options?: RequestOptions & PollingOptions): Promise<CompletedImageTaskResponse> {
    const { id } = await this.create(params, options);
    const response = await pollUntilComplete<ImageTaskResponse>(() => this.get(id, options), {
      maxWaitMs: options?.maxWaitMs,
      pollIntervalMs: options?.pollIntervalMs,
    });
    return response as CompletedImageTaskResponse;
  }

  async create(params: TextToImageParams, options?: RequestOptions): Promise<{ id: string }> {
    return this.http.request<{ id: string }>('POST', ENDPOINT, {
      body: compactParams(params),
      ...options,
    });
  }

  async get(id: string, options?: RequestOptions): Promise<ImageTaskResponse> {
    return this.http.request<ImageTaskResponse>('GET', `${ENDPOINT}/${id}`, options ?? {});
  }
}
