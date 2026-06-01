import type { AsyncTaskStatus } from '@runapi.ai/core';

// ——— Model types ———

export type WanTextToVideoModel =
  | 'wan-2.2-a14b-text-to-video-turbo'
  | 'wan-2.5-text-to-video'
  | 'wan-2.6-text-to-video'
  | 'wan-2.7-text-to-video'
  | 'wan-2.7-r2v';

export type WanImageToVideoModel =
  | 'wan-2.2-a14b-image-to-video-turbo'
  | 'wan-2.5-image-to-video'
  | 'wan-2.6-image-to-video'
  | 'wan-2.6-flash-image-to-video'
  | 'wan-2.7-image-to-video';

export type WanSpeechToVideoModel = 'wan-2.2-a14b-speech-to-video-turbo';

export type WanAnimateModel = 'wan-2.2-animate-move' | 'wan-2.2-animate-replace';

export type WanTextToImageModel = 'wan-2.7-image' | 'wan-2.7-image-pro';

export type WanTextToImageAspectRatio = '1:1' | '16:9' | '4:3' | '21:9' | '3:4' | '9:16' | '8:1' | '1:8';

export type WanEditVideoModel =
  | 'wan-2.6-edit-video'
  | 'wan-2.6-flash-edit-video'
  | 'wan-2.7-edit-video';

// ——— Response types ———

export interface Video {
  url: string;
}

export interface Image {
  url: string;
}

export interface VideoTaskResponse {
  id: string;
  status: AsyncTaskStatus;
  videos?: Video[];
  error?: string;
  [key: string]: unknown;
}

export interface ImageTaskResponse {
  id: string;
  status: AsyncTaskStatus;
  images?: Image[];
  error?: string;
  [key: string]: unknown;
}

/**
 * Resolved responses returned by the `run()` methods after polling sees
 * `status: 'completed'`. Narrows the base response so result arrays
 * (`videos` / `images`) are guaranteed non-optional in user code.
 */
export type CompletedVideoTaskResponse = VideoTaskResponse & {
  status: 'completed';
  videos: Video[];
};

export type CompletedImageTaskResponse = ImageTaskResponse & {
  status: 'completed';
  images: Image[];
};

// ——— Request param types ———

export interface TextToVideoParams {
  model: WanTextToVideoModel;
  prompt: string;
  callback_url?: string;
  duration_seconds?: number;
  output_resolution?: string;
  aspect_ratio?: string;
  ratio?: string;
  negative_prompt?: string;
  reference_image_urls?: string[];
  reference_video_urls?: string[];
  first_frame_image_url?: string;
  reference_audio_url?: string;
  enable_prompt_expansion?: boolean;
  seed?: number;
  acceleration?: string;
  enable_safety_checker?: boolean;
  watermark?: boolean;
  background_audio_url?: string;
}

export interface ImageToVideoParams {
  model: WanImageToVideoModel;
  prompt?: string;
  callback_url?: string;
  first_frame_image_url?: string;
  last_frame_image_url?: string;
  source_video_url?: string;
  duration_seconds?: number;
  output_resolution?: string;
  aspect_ratio?: string;
  ratio?: string;
  negative_prompt?: string;
  enable_prompt_expansion?: boolean;
  seed?: number;
  acceleration?: string;
  enable_safety_checker?: boolean;
  watermark?: boolean;
  audio?: boolean;
  multi_shots?: boolean;
  driving_audio_url?: string;
  background_audio_url?: string;
}

export interface SpeechToVideoParams {
  model: WanSpeechToVideoModel;
  source_image_url: string;
  source_audio_url: string;
  prompt?: string;
  callback_url?: string;
  num_frames?: number;
  frames_per_second?: number;
  output_resolution?: string;
  negative_prompt?: string;
  seed?: number;
  num_inference_steps?: number;
  guidance_scale?: number;
  shift?: number;
  enable_safety_checker?: boolean;
}

export interface AnimateParams {
  model: WanAnimateModel;
  source_image_url: string;
  reference_video_url: string;
  callback_url?: string;
  output_resolution?: string;
  enable_safety_checker?: boolean;
}

export interface ColorPaletteItem {
  hex: string;
  ratio?: number;
}

export interface TextToImageParams {
  model: WanTextToImageModel;
  prompt: string;
  callback_url?: string;
  aspect_ratio?: WanTextToImageAspectRatio;
  output_resolution?: string;
  output_count?: number;
  enable_sequential?: boolean;
  thinking_mode?: boolean;
  watermark?: boolean;
  seed?: number;
  enable_safety_checker?: boolean;
  source_image_urls?: string[];
  color_palette?: ColorPaletteItem[];
  bbox_list?: unknown[];
}

export interface EditVideoParams {
  model: WanEditVideoModel;
  source_video_url?: string;
  source_video_urls?: string[];
  prompt?: string;
  callback_url?: string;
  negative_prompt?: string;
  reference_image_url?: string;
  output_resolution?: string;
  aspect_ratio?: string;
  duration_seconds?: number;
  audio_setting?: string;
  enable_prompt_expansion?: boolean;
  watermark?: boolean;
  seed?: number;
  enable_safety_checker?: boolean;
  audio?: boolean;
  multi_shots?: boolean;
}
