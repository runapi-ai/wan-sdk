import type { AsyncTaskStatus } from '@runapi.ai/core';

// ——— Model types ———

/**
 * Text-to-video model variants. 2.2 turbo is fastest at lower resolution; 2.5/2.6 add 1080p;
 * 2.7 adds negative prompts, watermark control, and background audio; R2V accepts reference
 * images, videos, first-frame images, and reference audio alongside the text prompt.
 */
export type WanTextToVideoModel =
  | 'wan-2.2-a14b-text-to-video-turbo'
  | 'wan-2.5-text-to-video'
  | 'wan-2.6-text-to-video'
  | 'wan-2.7-text-to-video'
  | 'wan-2.7-r2v';

/**
 * Image-to-video model variants. Flash variants trade fidelity for speed;
 * 2.7 adds last-frame control, video continuation, driving/background audio, and watermark.
 */
export type WanImageToVideoModel =
  | 'wan-2.2-a14b-image-to-video-turbo'
  | 'wan-2.5-image-to-video'
  | 'wan-2.6-image-to-video'
  | 'wan-2.6-flash-image-to-video'
  | 'wan-2.7-image-to-video';

/** Speech-to-video model. Drives a portrait image with speech audio to produce a talking-head video. */
export type WanSpeechToVideoModel = 'wan-2.2-a14b-speech-to-video-turbo';

/** Animation model variants. Move preserves the subject and applies motion; replace swaps the subject with the reference video's subject. */
export type WanAnimateModel = 'wan-2.2-animate-move' | 'wan-2.2-animate-replace';

/** Image generation variants. Pro model supports thinking_mode for enhanced prompt reasoning. */
export type WanTextToImageModel = 'wan-2.7-image' | 'wan-2.7-image-pro';

/** Aspect ratios for image generation, including extreme panoramic ratios (8:1, 1:8). */
export type WanTextToImageAspectRatio = '1:1' | '16:9' | '4:3' | '21:9' | '3:4' | '9:16' | '8:1' | '1:8';

/**
 * Video editing model variants. 2.6 models use source_video_urls (plural);
 * 2.7 uses source_video_url (singular) and adds aspect ratio control.
 * Flash variants support audio generation and multi-shot mode.
 */
export type WanEditVideoModel =
  | 'wan-2.6-edit-video'
  | 'wan-2.6-flash-edit-video'
  | 'wan-2.7-edit-video';

// ——— Response types ———

/** A single generated video result. */
export interface Video {
  /** URL to the generated video file. */
  url: string;
}

/** A single generated image result. */
export interface Image {
  /** URL to the generated image file. */
  url: string;
}

/** Task result for video generation and editing operations. */
export interface VideoTaskResponse {
  id: string;
  status: AsyncTaskStatus;
  /** Output videos, populated once the task completes successfully. */
  videos?: Video[];
  /** Error message when the task has failed. */
  error?: string;
  [key: string]: unknown;
}

/** Task result for text-to-image operations. */
export interface ImageTaskResponse {
  id: string;
  status: AsyncTaskStatus;
  /** Output images, populated once the task completes successfully. */
  images?: Image[];
  /** Error message when the task has failed. */
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

/**
 * Text-to-video parameters. Feature availability varies by model:
 * negative_prompt works on 2.5 and 2.7; ratio/watermark/background_audio_url are 2.7-only;
 * reference inputs (images, videos, first frame, audio) are R2V-only.
 */
export interface TextToVideoParams {
  model: WanTextToVideoModel;
  /** Video description prompt. */
  prompt: string;
  /** Webhook URL for async completion notifications. */
  callback_url?: string;
  /** Video duration in seconds. */
  duration_seconds?: number;
  output_resolution?: string;
  aspect_ratio?: string;
  /** Alternative ratio format (2.7 only). */
  ratio?: string;
  /** What to avoid in the video (2.5 and 2.7 only). */
  negative_prompt?: string;
  /** Reference image URLs for guided generation (R2V only). */
  reference_image_urls?: string[];
  /** Reference video URLs for guided generation (R2V only). */
  reference_video_urls?: string[];
  /** First frame image URL (R2V only). */
  first_frame_image_url?: string;
  /** Reference audio URL (R2V only). */
  reference_audio_url?: string;
  /** Auto-expand prompt with additional detail. */
  enable_prompt_expansion?: boolean;
  /** Random seed for reproducible results. */
  seed?: number;
  acceleration?: string;
  /** Toggle content safety filtering. */
  enable_safety_checker?: boolean;
  /** Add watermark to output (2.7 only). */
  watermark?: boolean;
  /** Background audio URL overlaid on the video (2.7 only). */
  background_audio_url?: string;
}

/**
 * Image-to-video parameters. Prompt is optional for 2.2/2.5 but required for 2.6+.
 * last_frame_image_url, source_video_url, driving/background audio, and watermark are 2.7-only;
 * audio and multi_shots are flash-only.
 */
export interface ImageToVideoParams {
  model: WanImageToVideoModel;
  /** Video description. Optional for 2.2/2.5; required for 2.6 and later. */
  prompt?: string;
  /** Webhook URL for async completion notifications. */
  callback_url?: string;
  /** First frame image URL. */
  first_frame_image_url?: string;
  /** Last frame image URL for endpoint control (2.7 only). */
  last_frame_image_url?: string;
  /** Source video URL for video continuation (2.7 only). */
  source_video_url?: string;
  /** Video duration in seconds. */
  duration_seconds?: number;
  output_resolution?: string;
  aspect_ratio?: string;
  /** Alternative ratio format (2.7 only). */
  ratio?: string;
  /** What to avoid in the video (2.5 and 2.7 only). */
  negative_prompt?: string;
  /** Auto-expand prompt with additional detail. */
  enable_prompt_expansion?: boolean;
  /** Random seed for reproducible results. */
  seed?: number;
  acceleration?: string;
  /** Toggle content safety filtering. */
  enable_safety_checker?: boolean;
  /** Add watermark to output (2.7 only). */
  watermark?: boolean;
  /** Generate synchronized audio (flash only). */
  audio?: boolean;
  /** Enable multi-shot mode (flash only). */
  multi_shots?: boolean;
  /** Driving audio URL for lip-sync guidance (2.7 only). */
  driving_audio_url?: string;
  /** Background audio URL overlaid on the video (2.7 only). */
  background_audio_url?: string;
}

/**
 * Speech-to-video parameters. Generates a lip-synced talking-head video from
 * a portrait image and driving speech audio. Both source_image_url and
 * source_audio_url are required.
 */
export interface SpeechToVideoParams {
  model: WanSpeechToVideoModel;
  /** Portrait source image URL. */
  source_image_url: string;
  /** Driving speech audio URL. */
  source_audio_url: string;
  /** Additional description prompt. */
  prompt?: string;
  /** Webhook URL for async completion notifications. */
  callback_url?: string;
  /** Number of output frames. */
  num_frames?: number;
  /** Output frames per second. */
  frames_per_second?: number;
  output_resolution?: string;
  /** What to avoid in the video. */
  negative_prompt?: string;
  /** Random seed for reproducible results. */
  seed?: number;
  /** Denoising steps. More = higher quality, slower. */
  num_inference_steps?: number;
  /** Classifier-free guidance scale. */
  guidance_scale?: number;
  /** Noise schedule shift factor. */
  shift?: number;
  /** Toggle content safety filtering. */
  enable_safety_checker?: boolean;
}

/**
 * Animation parameters. Transfers motion from a reference video onto the subject
 * in the source image. Use animate-move to preserve the subject and apply
 * motion, or animate-replace to swap the subject with the reference video's subject.
 */
export interface AnimateParams {
  model: WanAnimateModel;
  /** Character or subject image URL. */
  source_image_url: string;
  /** Motion reference video URL. */
  reference_video_url: string;
  /** Webhook URL for async completion notifications. */
  callback_url?: string;
  output_resolution?: string;
  /** Toggle content safety filtering. */
  enable_safety_checker?: boolean;
}

/**
 * Color palette constraint for image generation.
 * Guides the output toward specific colors and proportions.
 */
export interface ColorPaletteItem {
  /** CSS hex color code (e.g. "#FF0000"). */
  hex: string;
  /** Proportion of the palette (0.0-1.0). */
  ratio?: number;
}

/**
 * Text-to-image parameters. Pro model supports thinking_mode for enhanced
 * prompt reasoning. source_image_urls enables image editing mode where the
 * prompt describes changes to apply. Supports batch generation via output_count.
 */
export interface TextToImageParams {
  model: WanTextToImageModel;
  /** Text prompt describing the image. */
  prompt: string;
  /** Webhook URL for async completion notifications. */
  callback_url?: string;
  aspect_ratio?: WanTextToImageAspectRatio;
  output_resolution?: string;
  /** Number of images to generate in a single request. */
  output_count?: number;
  /** Generate images sequentially rather than in parallel. */
  enable_sequential?: boolean;
  /** Enhanced reasoning mode for richer prompt interpretation (pro model only). */
  thinking_mode?: boolean;
  /** Add watermark to output. */
  watermark?: boolean;
  /** Random seed for reproducible results. */
  seed?: number;
  /** Toggle content safety filtering. */
  enable_safety_checker?: boolean;
  /** Source image URLs. When set, enables image editing mode. */
  source_image_urls?: string[];
  /** Color palette constraints to guide the output palette. */
  color_palette?: ColorPaletteItem[];
  /** Bounding box constraints for spatial layout control. */
  bbox_list?: unknown[];
}

/**
 * Video editing parameters. The 2.6 models use source_video_urls (plural, required)
 * while 2.7 uses source_video_url (singular, required). Prompt is required for 2.6,
 * optional for 2.7. Audio and multi_shots are flash-only features.
 */
export interface EditVideoParams {
  model: WanEditVideoModel;
  /** Source video URL (2.7 only, required). */
  source_video_url?: string;
  /** Source video URLs (2.6 only, required). */
  source_video_urls?: string[];
  /** Description of the desired edit. Required for 2.6; optional for 2.7. */
  prompt?: string;
  /** Webhook URL for async completion notifications. */
  callback_url?: string;
  /** What to avoid in the output. */
  negative_prompt?: string;
  /** Reference image URL to further guide the edit. */
  reference_image_url?: string;
  output_resolution?: string;
  aspect_ratio?: string;
  /** Output duration in seconds. */
  duration_seconds?: number;
  /** Audio handling setting. */
  audio_setting?: string;
  /** Auto-expand prompt with additional detail. */
  enable_prompt_expansion?: boolean;
  /** Add watermark to output. */
  watermark?: boolean;
  /** Random seed for reproducible results. */
  seed?: number;
  /** Toggle content safety filtering. */
  enable_safety_checker?: boolean;
  /** Generate synchronized audio (flash only). */
  audio?: boolean;
  /** Enable multi-shot mode (flash only). */
  multi_shots?: boolean;
}
