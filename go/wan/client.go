// Package wan provides the Wan video and image generation API client.
//
//	client, err := wan.NewClient(option.WithAPIKey("sk-your-api-key"))
//	result, err := client.TextToVideo.Run(ctx, wan.TextToVideoParams{
//	    Model: wan.ModelT2V26, Prompt: "A scenic mountain landscape",
//	})
package wan

import (
	"context"

	"github.com/runapi-ai/core-sdk/go/core"
	"github.com/runapi-ai/core-sdk/go/option"
)

const (
	textToVideoPath      = "/api/v1/wan/text_to_video"
	imageToVideoPath     = "/api/v1/wan/image_to_video"
	videoToVideoPath     = "/api/v1/wan/video_to_video"
	speechToVideoPath    = "/api/v1/wan/speech_to_video"
	animatePath           = "/api/v1/wan/animate"
	textToImagePath      = "/api/v1/wan/text_to_image"
	referenceToVideoPath = "/api/v1/wan/reference_to_video"
	editVideoPath        = "/api/v1/wan/edit_video"
)

// Client is the Wan video and image generation API client.
type Client struct {
	// TextToVideo provides text-to-video generation operations.
	TextToVideo *TextToVideo
	// ImageToVideo provides image-to-video generation operations.
	ImageToVideo *ImageToVideo
	// VideoToVideo provides video-to-video generation operations.
	VideoToVideo *VideoToVideo
	// SpeechToVideo provides speech-driven video generation operations.
	SpeechToVideo *SpeechToVideo
	// Animate provides animation operations.
	Animate *Animate
	// TextToImage provides text-to-image operations.
	TextToImage *TextToImage
	// ReferenceToVideo provides reference-to-video generation operations.
	ReferenceToVideo *ReferenceToVideo
	// EditVideo provides video editing operations.
	EditVideo *EditVideo
}

// NewClient creates a Wan client with the given options.
func NewClient(opts ...option.ClientOption) (*Client, error) {
	resolved, err := option.ResolveClientOptions(opts...)
	if err != nil {
		return nil, err
	}
	httpClient, err := core.NewHTTPClient(resolved)
	if err != nil {
		return nil, err
	}
	return NewClientWithHTTP(httpClient), nil
}

// NewClientWithHTTP creates a Wan client with a pre-configured HTTP transport.
func NewClientWithHTTP(httpClient core.HTTPClient) *Client {
	return &Client{
		TextToVideo:      &TextToVideo{http: httpClient},
		ImageToVideo:     &ImageToVideo{http: httpClient},
		VideoToVideo:     &VideoToVideo{http: httpClient},
		SpeechToVideo:    &SpeechToVideo{http: httpClient},
		Animate:           &Animate{http: httpClient},
		TextToImage:      &TextToImage{http: httpClient},
		ReferenceToVideo: &ReferenceToVideo{http: httpClient},
		EditVideo:        &EditVideo{http: httpClient},
	}
}

// TextToVideo generates videos from text prompts.
type TextToVideo struct{ http core.HTTPClient }

func (r *TextToVideo) Create(ctx context.Context, params TextToVideoParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, textToVideoPath, core.CompactParams(params), requestOptions)
}
func (r *TextToVideo) Get(ctx context.Context, id string, opts ...option.RequestOption) (*VideoTaskResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[VideoTaskResponse](ctx, r.http, core.ResourcePath(textToVideoPath, id), requestOptions)
}
func (r *TextToVideo) Run(ctx context.Context, params TextToVideoParams, opts ...option.RequestOption) (*VideoTaskResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*VideoTaskResponse, error) { return r.Get(ctx, id, opts...) }, pollingOptions)
}

// ImageToVideo generates videos from image inputs.
type ImageToVideo struct{ http core.HTTPClient }

func (r *ImageToVideo) Create(ctx context.Context, params ImageToVideoParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, imageToVideoPath, core.CompactParams(params), requestOptions)
}
func (r *ImageToVideo) Get(ctx context.Context, id string, opts ...option.RequestOption) (*VideoTaskResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[VideoTaskResponse](ctx, r.http, core.ResourcePath(imageToVideoPath, id), requestOptions)
}
func (r *ImageToVideo) Run(ctx context.Context, params ImageToVideoParams, opts ...option.RequestOption) (*VideoTaskResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*VideoTaskResponse, error) { return r.Get(ctx, id, opts...) }, pollingOptions)
}

// VideoToVideo transforms videos using text prompts.
type VideoToVideo struct{ http core.HTTPClient }

func (r *VideoToVideo) Create(ctx context.Context, params VideoToVideoParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, videoToVideoPath, core.CompactParams(params), requestOptions)
}
func (r *VideoToVideo) Get(ctx context.Context, id string, opts ...option.RequestOption) (*VideoTaskResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[VideoTaskResponse](ctx, r.http, core.ResourcePath(videoToVideoPath, id), requestOptions)
}
func (r *VideoToVideo) Run(ctx context.Context, params VideoToVideoParams, opts ...option.RequestOption) (*VideoTaskResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*VideoTaskResponse, error) { return r.Get(ctx, id, opts...) }, pollingOptions)
}

// SpeechToVideo generates talking-head videos from audio.
type SpeechToVideo struct{ http core.HTTPClient }

func (r *SpeechToVideo) Create(ctx context.Context, params SpeechToVideoParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, speechToVideoPath, core.CompactParams(params), requestOptions)
}
func (r *SpeechToVideo) Get(ctx context.Context, id string, opts ...option.RequestOption) (*VideoTaskResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[VideoTaskResponse](ctx, r.http, core.ResourcePath(speechToVideoPath, id), requestOptions)
}
func (r *SpeechToVideo) Run(ctx context.Context, params SpeechToVideoParams, opts ...option.RequestOption) (*VideoTaskResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*VideoTaskResponse, error) { return r.Get(ctx, id, opts...) }, pollingOptions)
}

// Animate generates animated videos from video + image pairs.
type Animate struct{ http core.HTTPClient }

func (r *Animate) Create(ctx context.Context, params AnimateParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, animatePath, core.CompactParams(params), requestOptions)
}
func (r *Animate) Get(ctx context.Context, id string, opts ...option.RequestOption) (*VideoTaskResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[VideoTaskResponse](ctx, r.http, core.ResourcePath(animatePath, id), requestOptions)
}
func (r *Animate) Run(ctx context.Context, params AnimateParams, opts ...option.RequestOption) (*VideoTaskResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*VideoTaskResponse, error) { return r.Get(ctx, id, opts...) }, pollingOptions)
}

// TextToImage generates images from text prompts.
type TextToImage struct{ http core.HTTPClient }

func (r *TextToImage) Create(ctx context.Context, params TextToImageParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, textToImagePath, core.CompactParams(params), requestOptions)
}
func (r *TextToImage) Get(ctx context.Context, id string, opts ...option.RequestOption) (*ImageTaskResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[ImageTaskResponse](ctx, r.http, core.ResourcePath(textToImagePath, id), requestOptions)
}
func (r *TextToImage) Run(ctx context.Context, params TextToImageParams, opts ...option.RequestOption) (*ImageTaskResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*ImageTaskResponse, error) { return r.Get(ctx, id, opts...) }, pollingOptions)
}

// ReferenceToVideo generates videos using reference images/videos.
type ReferenceToVideo struct{ http core.HTTPClient }

func (r *ReferenceToVideo) Create(ctx context.Context, params ReferenceToVideoParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, referenceToVideoPath, core.CompactParams(params), requestOptions)
}
func (r *ReferenceToVideo) Get(ctx context.Context, id string, opts ...option.RequestOption) (*VideoTaskResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[VideoTaskResponse](ctx, r.http, core.ResourcePath(referenceToVideoPath, id), requestOptions)
}
func (r *ReferenceToVideo) Run(ctx context.Context, params ReferenceToVideoParams, opts ...option.RequestOption) (*VideoTaskResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*VideoTaskResponse, error) { return r.Get(ctx, id, opts...) }, pollingOptions)
}

// EditVideo edits existing videos using text prompts.
type EditVideo struct{ http core.HTTPClient }

func (r *EditVideo) Create(ctx context.Context, params EditVideoParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, editVideoPath, core.CompactParams(params), requestOptions)
}
func (r *EditVideo) Get(ctx context.Context, id string, opts ...option.RequestOption) (*VideoTaskResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[VideoTaskResponse](ctx, r.http, core.ResourcePath(editVideoPath, id), requestOptions)
}
func (r *EditVideo) Run(ctx context.Context, params EditVideoParams, opts ...option.RequestOption) (*VideoTaskResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*VideoTaskResponse, error) { return r.Get(ctx, id, opts...) }, pollingOptions)
}
