import type { AsyncTaskStatus } from '@runapi.ai/core';

// ——— Model types ———

export type WanTextToVideoModel =
  | 'wan-2-2-a14b-text-to-video-turbo'
  | 'wan-2-5-text-to-video'
  | 'wan-2-6-text-to-video'
  | 'wan-2-7-text-to-video';

export type WanImageToVideoModel =
  | 'wan-2-2-a14b-image-to-video-turbo'
  | 'wan-2-5-image-to-video'
  | 'wan-2-6-image-to-video'
  | 'wan-2-6-flash-image-to-video'
  | 'wan-2-7-image-to-video';

export type WanVideoToVideoModel =
  | 'wan-2-6-video-to-video'
  | 'wan-2-6-flash-video-to-video';

export type WanSpeechToVideoModel = 'wan-2-2-a14b-speech-to-video-turbo';

export type WanAnimateModel = 'wan-2-2-animate-move' | 'wan-2-2-animate-replace';

export type WanTextToImageModel = 'wan-2-7-image' | 'wan-2-7-image-pro';

export type WanReferenceToVideoModel = 'wan-2-7-r2v';

export type WanEditVideoModel = 'wan-2-7-videoedit';

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
  duration?: string;
  resolution?: string;
  aspect_ratio?: string;
  ratio?: string;
  negative_prompt?: string;
  enable_prompt_expansion?: boolean;
  prompt_extend?: boolean;
  seed?: number;
  acceleration?: string;
  nsfw_checker?: boolean;
  watermark?: boolean;
  audio_url?: string;
}

export interface ImageToVideoParams {
  model: WanImageToVideoModel;
  prompt?: string;
  callback_url?: string;
  image_url?: string;
  image_urls?: string[];
  duration?: string;
  resolution?: string;
  aspect_ratio?: string;
  ratio?: string;
  negative_prompt?: string;
  enable_prompt_expansion?: boolean;
  prompt_extend?: boolean;
  seed?: number;
  acceleration?: string;
  nsfw_checker?: boolean;
  watermark?: boolean;
  audio?: boolean;
  multi_shots?: boolean;
  first_frame_url?: string;
  last_frame_url?: string;
  first_clip_url?: string;
  driving_audio_url?: string;
  audio_url?: string;
}

export interface VideoToVideoParams {
  model: WanVideoToVideoModel;
  prompt: string;
  video_urls?: string[];
  callback_url?: string;
  duration?: string;
  resolution?: string;
  nsfw_checker?: boolean;
  audio?: boolean;
  multi_shots?: boolean;
}

export interface SpeechToVideoParams {
  model: WanSpeechToVideoModel;
  image_url: string;
  audio_url: string;
  prompt?: string;
  callback_url?: string;
  num_frames?: number;
  frames_per_second?: number;
  resolution?: string;
  negative_prompt?: string;
  seed?: number;
  num_inference_steps?: number;
  guidance_scale?: number;
  shift?: number;
  nsfw_checker?: boolean;
}

export interface AnimateParams {
  model: WanAnimateModel;
  video_url: string;
  image_url: string;
  callback_url?: string;
  resolution?: string;
  nsfw_checker?: boolean;
}

export interface ColorPaletteItem {
  hex: string;
  ratio?: number;
}

export interface TextToImageParams {
  model: WanTextToImageModel;
  prompt: string;
  callback_url?: string;
  aspect_ratio?: string;
  resolution?: string;
  n?: number;
  enable_sequential?: boolean;
  thinking_mode?: boolean;
  watermark?: boolean;
  seed?: number;
  nsfw_checker?: boolean;
  input_urls?: string[];
  color_palette?: ColorPaletteItem[];
  bbox_list?: unknown[];
}

export interface ReferenceToVideoParams {
  model: WanReferenceToVideoModel;
  prompt: string;
  callback_url?: string;
  negative_prompt?: string;
  first_frame?: string;
  reference_image?: string[];
  reference_video?: string[];
  reference_voice?: string;
  resolution?: string;
  aspect_ratio?: string;
  duration?: string;
  prompt_extend?: boolean;
  watermark?: boolean;
  seed?: number;
  nsfw_checker?: boolean;
}

export interface EditVideoParams {
  model: WanEditVideoModel;
  video_url: string;
  prompt?: string;
  callback_url?: string;
  negative_prompt?: string;
  reference_image?: string;
  resolution?: string;
  aspect_ratio?: string;
  duration?: string;
  audio_setting?: string;
  prompt_extend?: boolean;
  watermark?: boolean;
  seed?: number;
  nsfw_checker?: boolean;
}
