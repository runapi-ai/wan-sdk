import { BaseClient, type ClientOptions } from '@runapi.ai/core';
import { TextToVideo } from './resources/text-to-video';
import { ImageToVideo } from './resources/image-to-video';
import { SpeechToVideo } from './resources/speech-to-video';
import { Animate } from './resources/animate';
import { TextToImage } from './resources/text-to-image';
import { EditVideo } from './resources/edit-video';

/**
 * Wan video and image generation API client.
 *
 * Spans multiple generation families (2.2 through 2.7) with progressive
 * capability upgrades. Includes text-to-video, image-to-video, speech-driven
 * lip-sync, motion-transfer animation, text-to-image, and video editing.
 * Feature availability varies by model variant -- see the param type docs
 * for per-model constraints.
 *
 * @example
 * ```typescript
 * const client = new WanClient({
 *   apiKey: 'your-api-key',
 *   baseUrl: 'https://runapi.ai',
 * });
 *
 * const result = await client.textToVideo.run({
 *   model: 'wan-2.6-text-to-video',
 *   prompt: 'A scenic mountain landscape with flowing rivers',
 * });
 * ```
 */
export class WanClient extends BaseClient {
  /** Generate videos from text prompts. Supports turbo (2.2) through 2.7 with progressive feature upgrades. */
  public readonly textToVideo: TextToVideo;
  /** Generate videos driven by a source image. Flash variants trade quality for speed; 2.7 adds last-frame control and audio. */
  public readonly imageToVideo: ImageToVideo;
  /** Generate lip-synced talking-head videos from a portrait image and speech audio. */
  public readonly speechToVideo: SpeechToVideo;
  /** Transfer motion from a reference video onto a subject image. Two variants: move (preserves subject) and replace (swaps subject). */
  public readonly animate: Animate;
  /** Generate images from text prompts with optional color palette and bounding box constraints. Pro model adds thinking mode. */
  public readonly textToImage: TextToImage;
  /** Modify existing videos guided by a text prompt and optional reference image. */
  public readonly editVideo: EditVideo;

  constructor(options: ClientOptions = {}) {
    super(options);
    this.textToVideo = new TextToVideo(this.http);
    this.imageToVideo = new ImageToVideo(this.http);
    this.speechToVideo = new SpeechToVideo(this.http);
    this.animate = new Animate(this.http);
    this.textToImage = new TextToImage(this.http);
    this.editVideo = new EditVideo(this.http);
  }
}
