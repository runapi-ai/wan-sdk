// Package wan provides the Wan video and image generation API client.
//
//	client, err := wan.NewClient(option.WithAPIKey("sk-your-api-key"))
//	result, err := client.TextToVideo.Run(ctx, wan.TextToVideoParams{
//	    Model: wan.ModelT2V26, Prompt: "A scenic mountain landscape",
//	})
package wan

import (
	"context"

	"github.com/runapi-ai/core-sdk/go/base"
	"github.com/runapi-ai/core-sdk/go/core"
	"github.com/runapi-ai/core-sdk/go/option"
)

const (
	textToVideoPath   = "/api/v1/wan/text_to_video"
	imageToVideoPath  = "/api/v1/wan/image_to_video"
	speechToVideoPath = "/api/v1/wan/speech_to_video"
	animatePath       = "/api/v1/wan/animate"
	textToImagePath   = "/api/v1/wan/text_to_image"
	editVideoPath     = "/api/v1/wan/edit_video"
)

// Client is the Wan video and image generation API client.
type Client struct {
	base.Base
	// TextToVideo provides text-to-video generation operations.
	TextToVideo *TextToVideo
	// ImageToVideo provides image-to-video generation operations.
	ImageToVideo *ImageToVideo
	// SpeechToVideo provides speech-driven video generation operations.
	SpeechToVideo *SpeechToVideo
	// Animate provides animation operations.
	Animate *Animate
	// TextToImage provides text-to-image operations.
	TextToImage *TextToImage
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
		Base:          base.New(httpClient),
		TextToVideo:   &TextToVideo{http: httpClient},
		ImageToVideo:  &ImageToVideo{http: httpClient},
		SpeechToVideo: &SpeechToVideo{http: httpClient},
		Animate:       &Animate{http: httpClient},
		TextToImage:   &TextToImage{http: httpClient},
		EditVideo:     &EditVideo{http: httpClient},
	}
}

// TextToVideo generates videos from text prompts.
type TextToVideo struct{ http core.HTTPClient }

// Create submits a text-to-video generation task and returns immediately with the task ID.
func (r *TextToVideo) Create(ctx context.Context, params TextToVideoParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, textToVideoPath, core.CompactParams(params), requestOptions)
}

// Get retrieves the current status and result of a text-to-video task.
func (r *TextToVideo) Get(ctx context.Context, id string, opts ...option.RequestOption) (*VideoTaskResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[VideoTaskResponse](ctx, r.http, core.ResourcePath(textToVideoPath, id), requestOptions)
}

// Run submits a text-to-video task and polls until it completes or fails.
func (r *TextToVideo) Run(ctx context.Context, params TextToVideoParams, opts ...option.RequestOption) (*VideoTaskResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*VideoTaskResponse, error) { return r.Get(ctx, id, opts...) }, pollingOptions)
}

// ImageToVideo generates videos driven by a source image.
type ImageToVideo struct{ http core.HTTPClient }

// Create submits an image-to-video generation task and returns immediately with the task ID.
func (r *ImageToVideo) Create(ctx context.Context, params ImageToVideoParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, imageToVideoPath, core.CompactParams(params), requestOptions)
}

// Get retrieves the current status and result of an image-to-video task.
func (r *ImageToVideo) Get(ctx context.Context, id string, opts ...option.RequestOption) (*VideoTaskResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[VideoTaskResponse](ctx, r.http, core.ResourcePath(imageToVideoPath, id), requestOptions)
}

// Run submits an image-to-video task and polls until it completes or fails.
func (r *ImageToVideo) Run(ctx context.Context, params ImageToVideoParams, opts ...option.RequestOption) (*VideoTaskResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*VideoTaskResponse, error) { return r.Get(ctx, id, opts...) }, pollingOptions)
}

// SpeechToVideo generates lip-synced talking-head videos from a portrait image and speech audio.
type SpeechToVideo struct{ http core.HTTPClient }

// Create submits a speech-to-video lip-sync task and returns immediately with the task ID.
func (r *SpeechToVideo) Create(ctx context.Context, params SpeechToVideoParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, speechToVideoPath, core.CompactParams(params), requestOptions)
}

// Get retrieves the current status and result of a speech-to-video task.
func (r *SpeechToVideo) Get(ctx context.Context, id string, opts ...option.RequestOption) (*VideoTaskResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[VideoTaskResponse](ctx, r.http, core.ResourcePath(speechToVideoPath, id), requestOptions)
}

// Run submits a speech-to-video task and polls until it completes or fails.
func (r *SpeechToVideo) Run(ctx context.Context, params SpeechToVideoParams, opts ...option.RequestOption) (*VideoTaskResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*VideoTaskResponse, error) { return r.Get(ctx, id, opts...) }, pollingOptions)
}

// Animate transfers motion from a reference video onto a subject in a source image.
// Two model variants are available: animate-move (keeps the original subject, applies motion)
// and animate-replace (swaps the subject with the reference video's subject).
type Animate struct{ http core.HTTPClient }

// Create submits a motion-transfer animation task and returns immediately with the task ID.
func (r *Animate) Create(ctx context.Context, params AnimateParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, animatePath, core.CompactParams(params), requestOptions)
}

// Get retrieves the current status and result of an animation task.
func (r *Animate) Get(ctx context.Context, id string, opts ...option.RequestOption) (*VideoTaskResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[VideoTaskResponse](ctx, r.http, core.ResourcePath(animatePath, id), requestOptions)
}

// Run submits an animation task and polls until it completes or fails.
func (r *Animate) Run(ctx context.Context, params AnimateParams, opts ...option.RequestOption) (*VideoTaskResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*VideoTaskResponse, error) { return r.Get(ctx, id, opts...) }, pollingOptions)
}

// TextToImage generates images from text prompts, with optional color palette
// and bounding box constraints. Supports batch generation via OutputCount.
type TextToImage struct{ http core.HTTPClient }

// Create submits a text-to-image generation task and returns immediately with the task ID.
func (r *TextToImage) Create(ctx context.Context, params TextToImageParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, textToImagePath, core.CompactParams(params), requestOptions)
}

// Get retrieves the current status and result of a text-to-image task.
func (r *TextToImage) Get(ctx context.Context, id string, opts ...option.RequestOption) (*ImageTaskResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[ImageTaskResponse](ctx, r.http, core.ResourcePath(textToImagePath, id), requestOptions)
}

// Run submits a text-to-image task and polls until it completes or fails.
func (r *TextToImage) Run(ctx context.Context, params TextToImageParams, opts ...option.RequestOption) (*ImageTaskResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*ImageTaskResponse, error) { return r.Get(ctx, id, opts...) }, pollingOptions)
}

// EditVideo modifies existing videos guided by a text prompt.
// The prompt describes the desired changes; a reference image can further guide the edit.
type EditVideo struct{ http core.HTTPClient }

// Create submits a video editing task and returns immediately with the task ID.
func (r *EditVideo) Create(ctx context.Context, params EditVideoParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, editVideoPath, core.CompactParams(params), requestOptions)
}

// Get retrieves the current status and result of a video editing task.
func (r *EditVideo) Get(ctx context.Context, id string, opts ...option.RequestOption) (*VideoTaskResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[VideoTaskResponse](ctx, r.http, core.ResourcePath(editVideoPath, id), requestOptions)
}

// Run submits a video editing task and polls until it completes or fails.
func (r *EditVideo) Run(ctx context.Context, params EditVideoParams, opts ...option.RequestOption) (*VideoTaskResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*VideoTaskResponse, error) { return r.Get(ctx, id, opts...) }, pollingOptions)
}
