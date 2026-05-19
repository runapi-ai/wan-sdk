import { createHttpClient, type ClientOptions } from '@runapi.ai/core';
import { TextToVideo } from './resources/text-to-video';
import { ImageToVideo } from './resources/image-to-video';
import { VideoToVideo } from './resources/video-to-video';
import { SpeechToVideo } from './resources/speech-to-video';
import { Animate } from './resources/animate';
import { TextToImage } from './resources/text-to-image';
import { ReferenceToVideo } from './resources/reference-to-video';
import { EditVideo } from './resources/edit-video';

/**
 * Wan video and text-to-image API client.
 *
 * @example
 * ```typescript
 * const client = new WanClient({
 *   apiKey: 'your-api-key',
 *   baseUrl: 'https://runapi.ai',
 * });
 *
 * const result = await client.textToVideo.run({
 *   model: 'wan-2-6-text-to-video',
 *   prompt: 'A scenic mountain landscape with flowing rivers',
 * });
 * ```
 */
export class WanClient {
  /** Text-to-video generation operations. */
  public readonly textToVideo: TextToVideo;
  /** Image-to-video generation operations. */
  public readonly imageToVideo: ImageToVideo;
  /** Video-to-video transformation operations. */
  public readonly videoToVideo: VideoToVideo;
  /** Speech-driven talking-head video operations. */
  public readonly speechToVideo: SpeechToVideo;
  /** Animation operations (animate-move, animate-replace). */
  public readonly animate: Animate;
  /** Text-to-image operations. */
  public readonly textToImage: TextToImage;
  /** Reference-to-video generation operations. */
  public readonly referenceToVideo: ReferenceToVideo;
  /** Video editing operations. */
  public readonly editVideo: EditVideo;

  constructor(options: ClientOptions = {}) {
    const http = createHttpClient(options);
    this.textToVideo = new TextToVideo(http);
    this.imageToVideo = new ImageToVideo(http);
    this.videoToVideo = new VideoToVideo(http);
    this.speechToVideo = new SpeechToVideo(http);
    this.animate = new Animate(http);
    this.textToImage = new TextToImage(http);
    this.referenceToVideo = new ReferenceToVideo(http);
    this.editVideo = new EditVideo(http);
  }
}
