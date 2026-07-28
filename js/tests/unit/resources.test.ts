import { beforeEach, describe, expect, expectTypeOf, it, vi } from 'vitest';
import type { HttpClient } from '@runapi.ai/core';
import type { TaskCreateResponse } from '../../src/types';
import { TextToImage } from '../../src/resources/text-to-image';
import { TextToVideo } from '../../src/resources/text-to-video';
import { ImageToVideo } from '../../src/resources/image-to-video';
import { Animate } from '../../src/resources/animate';
import { EditVideo } from '../../src/resources/edit-video';
import { SpeechToVideo } from '../../src/resources/speech-to-video';

describe('Wan resources', () => {
  const mockHttp: HttpClient = {
    request: vi.fn(),
  };

  beforeEach(() => {
    vi.clearAllMocks();
  });

  it('creates images with flat params', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({
      id: 'task-1',
      billing: { reservation: { amount_cents: 95 }, settlement: null, refund: null },
    });
    const textToImage = new TextToImage(mockHttp);

    const result = await textToImage.create({ model: 'wan-2.7-image', prompt: 'A mountain lake', aspect_ratio: '1:8', output_resolution: '2k', output_count: 2 });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/wan/text_to_image', {
      body: { model: 'wan-2.7-image', prompt: 'A mountain lake', aspect_ratio: '1:8', output_resolution: '2k', output_count: 2 },
    });
    expect(result.billing?.reservation).toEqual({ amount_cents: 95 });
  });

  it('returns billing facts from every create resource while accepting legacy id-only responses', () => {
    const legacyResponse: TaskCreateResponse = { id: 'task-legacy' };

    expect(legacyResponse.billing).toBeUndefined();
    expectTypeOf<ReturnType<Animate['create']>>().toEqualTypeOf<Promise<TaskCreateResponse>>();
    expectTypeOf<ReturnType<EditVideo['create']>>().toEqualTypeOf<Promise<TaskCreateResponse>>();
    expectTypeOf<ReturnType<ImageToVideo['create']>>().toEqualTypeOf<Promise<TaskCreateResponse>>();
    expectTypeOf<ReturnType<SpeechToVideo['create']>>().toEqualTypeOf<Promise<TaskCreateResponse>>();
    expectTypeOf<ReturnType<TextToImage['create']>>().toEqualTypeOf<Promise<TaskCreateResponse>>();
    expectTypeOf<ReturnType<TextToVideo['create']>>().toEqualTypeOf<Promise<TaskCreateResponse>>();
  });

  it('creates images with source_image_urls', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task-source' });
    const textToImage = new TextToImage(mockHttp);

    await textToImage.create({
      model: 'wan-2.7-image',
      prompt: 'Edit this image',
      source_image_urls: ['https://cdn.runapi.ai/public/samples/source.jpg'],
    });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/wan/text_to_image', {
      body: {
        model: 'wan-2.7-image',
        prompt: 'Edit this image',
        source_image_urls: ['https://cdn.runapi.ai/public/samples/source.jpg'],
      },
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

  it('creates r2v through text-to-video with canonical reference fields', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task-r2v' });
    const textToVideo = new TextToVideo(mockHttp);

    await textToVideo.create({
      model: 'wan-2.7-r2v',
      prompt: 'Character walking in a city',
      reference_image_urls: ['https://cdn.runapi.ai/public/samples/reference.jpg'],
      output_resolution: '1080p',
    });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/wan/text_to_video', {
      body: {
        model: 'wan-2.7-r2v',
        prompt: 'Character walking in a city',
        reference_image_urls: ['https://cdn.runapi.ai/public/samples/reference.jpg'],
        output_resolution: '1080p',
      },
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

  it('creates image-to-video with first_frame_image_url', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task-3' });
    const imageToVideo = new ImageToVideo(mockHttp);

    await imageToVideo.create({
      model: 'wan-2.6-image-to-video',
      prompt: 'Zoom in slowly',
      first_frame_image_url: 'https://cdn.runapi.ai/public/samples/result.jpg',
      output_resolution: '1080p',
    });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/wan/image_to_video', {
      body: {
        model: 'wan-2.6-image-to-video',
        prompt: 'Zoom in slowly',
        first_frame_image_url: 'https://cdn.runapi.ai/public/samples/result.jpg',
        output_resolution: '1080p',
      },
    });
  });

  it('creates 2.6 edit-video with source_video_urls', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task-4' });
    const editVideo = new EditVideo(mockHttp);

    await editVideo.create({
      model: 'wan-2.6-flash-edit-video',
      prompt: 'Make it cinematic',
      source_video_urls: ['https://cdn.runapi.ai/public/samples/source.mp4'],
      output_resolution: '1080p',
    });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/wan/edit_video', {
      body: {
        model: 'wan-2.6-flash-edit-video',
        prompt: 'Make it cinematic',
        source_video_urls: ['https://cdn.runapi.ai/public/samples/source.mp4'],
        output_resolution: '1080p',
      },
    });
  });

  it('creates animation with source image and reference video inputs', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task-5' });
    const animate = new Animate(mockHttp);

    await animate.create({
      model: 'wan-2.2-animate-replace',
      reference_video_url: 'https://cdn.runapi.ai/public/samples/source.mp4',
      source_image_url: 'https://cdn.runapi.ai/public/samples/target.jpg',
      output_resolution: '580p',
    });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/wan/animate', {
      body: {
        model: 'wan-2.2-animate-replace',
        reference_video_url: 'https://cdn.runapi.ai/public/samples/source.mp4',
        source_image_url: 'https://cdn.runapi.ai/public/samples/target.jpg',
        output_resolution: '580p',
      },
    });
  });

  it('creates video-edit with source_video_url', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task-6' });
    const editVideo = new EditVideo(mockHttp);

    await editVideo.create({
      model: 'wan-2.7-edit-video',
      prompt: 'Remove background',
      source_video_url: 'https://cdn.runapi.ai/public/samples/source.mp4',
      reference_image_url: 'https://cdn.runapi.ai/public/samples/style.png',
    });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/wan/edit_video', {
      body: {
        model: 'wan-2.7-edit-video',
        prompt: 'Remove background',
        source_video_url: 'https://cdn.runapi.ai/public/samples/source.mp4',
        reference_image_url: 'https://cdn.runapi.ai/public/samples/style.png',
      },
    });
  });

  it('creates speech-to-video with source audio', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task-8' });
    const speechToVideo = new SpeechToVideo(mockHttp);

    await speechToVideo.create({
      model: 'wan-2.2-a14b-speech-to-video-turbo',
      prompt: 'Speak naturally',
      source_image_url: 'https://cdn.runapi.ai/public/samples/face.jpg',
      source_audio_url: 'https://cdn.runapi.ai/public/samples/speech.mp3',
      output_resolution: '720p',
    });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/wan/speech_to_video', {
      body: {
        model: 'wan-2.2-a14b-speech-to-video-turbo',
        prompt: 'Speak naturally',
        source_image_url: 'https://cdn.runapi.ai/public/samples/face.jpg',
        source_audio_url: 'https://cdn.runapi.ai/public/samples/speech.mp3',
        output_resolution: '720p',
      },
    });
  });
});
