import { beforeEach, describe, expect, it, vi } from 'vitest';
import type { HttpClient } from '@runapi.ai/core';
import { TextToImage } from '../../src/resources/text-to-image';
import { TextToVideo } from '../../src/resources/text-to-video';
import { ImageToVideo } from '../../src/resources/image-to-video';
import { VideoToVideo } from '../../src/resources/video-to-video';
import { Animate } from '../../src/resources/animate';
import { EditVideo } from '../../src/resources/edit-video';
import { ReferenceToVideo } from '../../src/resources/reference-to-video';
import { SpeechToVideo } from '../../src/resources/speech-to-video';

describe('Wan resources', () => {
  const mockHttp: HttpClient = {
    request: vi.fn(),
  };

  beforeEach(() => {
    vi.clearAllMocks();
  });

  it('creates images with flat params', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task-1' });
    const textToImage = new TextToImage(mockHttp);

    await textToImage.create({ model: 'wan-2.7-image', prompt: 'A mountain lake' });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/wan/text_to_image', {
      body: { model: 'wan-2.7-image', prompt: 'A mountain lake' },
    });
  });

  it('gets images by id', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({
      id: 'task-1',
      status: 'completed',
      images: [{ url: 'https://tempfile.runapi.ai/img.jpg' }],
    });
    const textToImage = new TextToImage(mockHttp);

    const result = await textToImage.get('task-1');

    expect(mockHttp.request).toHaveBeenCalledWith('GET', '/api/v1/wan/text_to_image/task-1', {});
    expect(result.images?.[0]?.url).toBe('https://tempfile.runapi.ai/img.jpg');
  });

  it('creates text-to-video with flat params', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task-2' });
    const textToVideo = new TextToVideo(mockHttp);

    await textToVideo.create({ model: 'wan-2.6-text-to-video', prompt: 'Ocean waves' });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/wan/text_to_video', {
      body: { model: 'wan-2.6-text-to-video', prompt: 'Ocean waves' },
    });
  });

  it('gets text-to-video by id', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({
      id: 'task-2',
      status: 'completed',
      videos: [{ url: 'https://cdn-video.runapi.ai/video.mp4' }],
    });
    const textToVideo = new TextToVideo(mockHttp);

    const result = await textToVideo.get('task-2');

    expect(mockHttp.request).toHaveBeenCalledWith('GET', '/api/v1/wan/text_to_video/task-2', {});
    expect(result.videos?.[0]?.url).toBe('https://cdn-video.runapi.ai/video.mp4');
  });

  it('creates image-to-video with image_urls', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task-3' });
    const imageToVideo = new ImageToVideo(mockHttp);

    await imageToVideo.create({
      model: 'wan-2.6-image-to-video',
      prompt: 'Zoom in slowly',
      image_urls: ['https://example.com/image.jpg'],
    });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/wan/image_to_video', {
      body: {
        model: 'wan-2.6-image-to-video',
        prompt: 'Zoom in slowly',
        image_urls: ['https://example.com/image.jpg'],
      },
    });
  });

  it('creates video-to-video with video_url', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task-4' });
    const videoToVideo = new VideoToVideo(mockHttp);

    await videoToVideo.create({
      model: 'wan-2.6-flash-video-to-video',
      prompt: 'Make it cinematic',
      video_url: 'https://example.com/video.mp4',
    });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/wan/video_to_video', {
      body: {
        model: 'wan-2.6-flash-video-to-video',
        prompt: 'Make it cinematic',
        video_url: 'https://example.com/video.mp4',
      },
    });
  });

  it('creates animation with image_urls', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task-5' });
    const animate = new Animate(mockHttp);

    await animate.create({
      model: 'wan-2.2-animate-replace',
      image_urls: ['https://example.com/a.jpg', 'https://example.com/b.jpg'],
    });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/wan/animate', {
      body: {
        model: 'wan-2.2-animate-replace',
        image_urls: ['https://example.com/a.jpg', 'https://example.com/b.jpg'],
      },
    });
  });

  it('creates video-edit with video_url', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task-6' });
    const editVideo = new EditVideo(mockHttp);

    await editVideo.create({
      model: 'wan-2.7-video-edit',
      prompt: 'Remove background',
      video_url: 'https://example.com/clip.mp4',
    });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/wan/edit_video', {
      body: {
        model: 'wan-2.7-video-edit',
        prompt: 'Remove background',
        video_url: 'https://example.com/clip.mp4',
      },
    });
  });

  it('creates reference-to-video with image_urls', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task-7' });
    const referenceToVideo = new ReferenceToVideo(mockHttp);

    await referenceToVideo.create({
      model: 'wan-2.7-reference-to-video',
      prompt: 'Character walking in a city',
      image_urls: ['https://example.com/ref.jpg'],
    });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/wan/reference_to_video', {
      body: {
        model: 'wan-2.7-reference-to-video',
        prompt: 'Character walking in a city',
        image_urls: ['https://example.com/ref.jpg'],
      },
    });
  });

  it('creates speech-to-video with audio_url', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task-8' });
    const speechToVideo = new SpeechToVideo(mockHttp);

    await speechToVideo.create({
      model: 'wan-2.2-a14b-speech-to-video',
      audio_url: 'https://example.com/speech.mp3',
    });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/wan/speech_to_video', {
      body: {
        model: 'wan-2.2-a14b-speech-to-video',
        audio_url: 'https://example.com/speech.mp3',
      },
    });
  });
});
