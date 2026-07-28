// Package wan provides the Wan video and image generation API client.
package wan

import "github.com/runapi-ai/core-sdk/go/core"

// TextToVideoModel selects the Wan text-to-video engine variant.
// Generations range from 2.2 (turbo, fast at lower resolution) through
// 2.7 (highest quality, supports negative prompts and watermark control).
type TextToVideoModel string

// ImageToVideoModel selects the Wan image-to-video engine variant.
// Flash variants trade visual fidelity for speed; standard/later generations
// offer higher resolution and features like last-frame control and audio.
type ImageToVideoModel string

// EditVideoModel selects the Wan video editing engine variant.
// Edit video transforms an existing video guided by a text prompt.
type EditVideoModel string

// ImageModel selects the Wan image generation engine variant.
type ImageModel string

const (
	// ModelT2V22Turbo is the 2.2-generation turbo model. Fast generation, max 720p output.
	ModelT2V22Turbo TextToVideoModel = "wan-2.2-a14b-text-to-video-turbo"
	// ModelT2V25 is the 2.5-generation model. Supports 720p/1080p and negative prompts.
	ModelT2V25 TextToVideoModel = "wan-2.5-text-to-video"
	// ModelT2V26 is the 2.6-generation model. Supports 720p/1080p output.
	ModelT2V26 TextToVideoModel = "wan-2.6-text-to-video"
	// ModelT2V27 is the 2.7-generation model. Supports 720p/1080p, negative prompts, watermark, and background audio.
	ModelT2V27 TextToVideoModel = "wan-2.7-text-to-video"
	// ModelT2V27R2V is the 2.7 R2V model. Accepts reference images, reference videos,
	// a first-frame image, and reference audio to guide generation alongside the text prompt.
	ModelT2V27R2V TextToVideoModel = "wan-2.7-r2v"

	// ModelI2V22Turbo is the 2.2-generation turbo image-to-video model. Fast, max 720p.
	ModelI2V22Turbo ImageToVideoModel = "wan-2.2-a14b-image-to-video-turbo"
	// ModelI2V25 is the 2.5-generation model. Requires first_frame_image_url and duration_seconds.
	ModelI2V25 ImageToVideoModel = "wan-2.5-image-to-video"
	// ModelI2V26 is the 2.6-generation model. Requires prompt and first_frame_image_url.
	ModelI2V26 ImageToVideoModel = "wan-2.6-image-to-video"
	// ModelI2V26Flash is the 2.6-generation flash model. Faster than standard 2.6, supports audio and multi-shot mode.
	ModelI2V26Flash ImageToVideoModel = "wan-2.6-flash-image-to-video"
	// ModelI2V27 is the 2.7-generation model. Supports last-frame control, video continuation,
	// driving audio, background audio, and watermark control.
	ModelI2V27 ImageToVideoModel = "wan-2.7-image-to-video"

	// ModelEdit26 is the 2.6-generation video editor. Requires prompt and source_video_urls (plural).
	ModelEdit26 EditVideoModel = "wan-2.6-edit-video"
	// ModelEdit26Flash is the 2.6-generation flash video editor. Faster, supports audio and multi-shot mode.
	ModelEdit26Flash EditVideoModel = "wan-2.6-flash-edit-video"
	// ModelEdit27 is the 2.7-generation video editor. Uses source_video_url (singular) and supports aspect ratio control.
	ModelEdit27 EditVideoModel = "wan-2.7-edit-video"

	// ModelImage27 is the 2.7-generation standard image model.
	ModelImage27 ImageModel = "wan-2.7-image"
	// ModelImage27Pro is the 2.7-generation pro image model. Supports thinking_mode for enhanced reasoning.
	ModelImage27Pro ImageModel = "wan-2.7-image-pro"
)

// Video holds a URL to a generated video file.
type Video struct {
	URL string `json:"url"`
}

// Image holds a URL to a generated image file.
type Image struct {
	URL string `json:"url"`
}

// AsyncTaskResponse carries the task ID, lifecycle status, and any error message
// for all Wan async operations. Embed this in endpoint-specific responses.
type AsyncTaskResponse struct {
	core.TaskBillingFacts
	ID     string `json:"id"`
	Status string `json:"status"`
	Error  string `json:"error,omitempty"`
}

func (r AsyncTaskResponse) GetID() string     { return r.ID }
func (r AsyncTaskResponse) GetStatus() string { return r.Status }
func (r AsyncTaskResponse) GetError() string  { return r.Error }

// VideoTaskResponse is the completed result of a video generation task.
// Videos contains one or more output URLs once Status reaches a terminal state.
type VideoTaskResponse struct {
	AsyncTaskResponse
	Videos []Video `json:"videos,omitempty"`
}

// ImageTaskResponse is the completed result of a text-to-image task.
// Images contains one or more output URLs once Status reaches a terminal state.
type ImageTaskResponse struct {
	AsyncTaskResponse
	Images []Image `json:"images,omitempty"`
}

// TextToVideoParams configures text-to-video generation.
// Some fields are model-specific: NegativePrompt works on 2.5 and 2.7;
// Ratio, Watermark, and BackgroundAudioURL are 2.7-only; reference inputs are R2V-only.
type TextToVideoParams struct {
	Model                 string   `json:"model" help:"required; model slug"`
	Prompt                string   `json:"prompt" help:"required; text prompt describing the video"`
	CallbackURL           string   `json:"callback_url,omitempty" help:"optional; webhook URL for async notifications"`
	DurationSeconds       int      `json:"duration_seconds,omitempty" help:"optional; duration in seconds"`
	OutputResolution      string   `json:"output_resolution,omitempty" help:"optional; output resolution"`
	AspectRatio           string   `json:"aspect_ratio,omitempty" help:"optional; output aspect ratio"`
	Ratio                 string   `json:"ratio,omitempty" help:"optional; alternative ratio format (2-7 only)"`
	NegativePrompt        string   `json:"negative_prompt,omitempty" help:"optional; what to avoid in the video (2-5, 2-7 only)"`
	ReferenceImageURLs    []string `json:"reference_image_urls,omitempty" help:"optional; reference image URLs (r2v only)"`
	ReferenceVideoURLs    []string `json:"reference_video_urls,omitempty" help:"optional; reference video URLs (r2v only)"`
	FirstFrameImageURL    string   `json:"first_frame_image_url,omitempty" help:"optional; first frame image URL (r2v only)"`
	ReferenceAudioURL     string   `json:"reference_audio_url,omitempty" help:"optional; reference audio URL (r2v only)"`
	EnablePromptExpansion *bool    `json:"enable_prompt_expansion,omitempty" help:"optional; auto-expand prompt"`
	Seed                  *int     `json:"seed,omitempty" help:"optional; random seed; unsupported by wan-2.6-text-to-video"`
	Acceleration          string   `json:"acceleration,omitempty" help:"optional; acceleration mode"`
	EnableSafetyChecker   *bool    `json:"enable_safety_checker,omitempty" help:"optional; content safety check toggle"`
	Watermark             *bool    `json:"watermark,omitempty" help:"optional; add watermark (2-7 only)"`
	BackgroundAudioURL    string   `json:"background_audio_url,omitempty" help:"optional; background audio URL (2-7 only)"`
}

// ImageToVideoParams configures image-to-video generation.
// Feature availability varies by generation: 2.2/2.5 treat Prompt as optional while 2.6+ require it;
// LastFrameImageURL, SourceVideoURL, DrivingAudioURL, BackgroundAudioURL, and Watermark are 2.7-only;
// Audio and MultiShots are flash-only.
type ImageToVideoParams struct {
	Model                 string `json:"model" help:"required; model slug"`
	Prompt                string `json:"prompt,omitempty" help:"optional for 2-2/2-5 (required for 2-6/2-7); text prompt"`
	CallbackURL           string `json:"callback_url,omitempty" help:"optional; webhook URL for async notifications"`
	FirstFrameImageURL    string `json:"first_frame_image_url,omitempty" help:"optional; first frame image URL"`
	LastFrameImageURL     string `json:"last_frame_image_url,omitempty" help:"optional; last frame image URL (2-7 only)"`
	SourceVideoURL        string `json:"source_video_url,omitempty" help:"optional; source video URL for continuation (2-7 only)"`
	DurationSeconds       int    `json:"duration_seconds,omitempty" help:"optional; duration in seconds"`
	OutputResolution      string `json:"output_resolution,omitempty" help:"optional; output resolution"`
	AspectRatio           string `json:"aspect_ratio,omitempty" help:"optional; output aspect ratio"`
	NegativePrompt        string `json:"negative_prompt,omitempty" help:"optional; what to avoid (2-5, 2-7 only)"`
	EnablePromptExpansion *bool  `json:"enable_prompt_expansion,omitempty" help:"optional; auto-expand prompt"`
	Seed                  *int   `json:"seed,omitempty" help:"optional; random seed; unsupported by wan-2.6 image-to-video models"`
	Acceleration          string `json:"acceleration,omitempty" help:"optional; acceleration mode"`
	EnableSafetyChecker   *bool  `json:"enable_safety_checker,omitempty" help:"optional; content safety check toggle"`
	Watermark             *bool  `json:"watermark,omitempty" help:"optional; add watermark (2-7 only)"`
	Audio                 *bool  `json:"audio,omitempty" help:"optional; generate audio (flash only)"`
	MultiShots            *bool  `json:"multi_shots,omitempty" help:"optional; multi-shot mode (flash only)"`
	DrivingAudioURL       string `json:"driving_audio_url,omitempty" help:"optional; driving audio URL (2-7 only)"`
	BackgroundAudioURL    string `json:"background_audio_url,omitempty" help:"optional; background audio URL (2-7 only)"`
	Ratio                 string `json:"ratio,omitempty" help:"optional; alternative ratio format (2-7 only)"`
}

// SpeechToVideoParams configures lip-sync video generation.
// Drives a portrait image with speech audio to produce a talking-head video.
// SourceImageURL and SourceAudioURL are both required.
type SpeechToVideoParams struct {
	Model               string   `json:"model" help:"required; model slug"`
	SourceImageURL      string   `json:"source_image_url" help:"required; portrait source image URL"`
	SourceAudioURL      string   `json:"source_audio_url" help:"required; driving speech audio URL"`
	Prompt              string   `json:"prompt,omitempty" help:"optional; additional description prompt"`
	CallbackURL         string   `json:"callback_url,omitempty" help:"optional; webhook URL for async notifications"`
	NumFrames           *int     `json:"num_frames,omitempty" help:"optional; number of output frames"`
	FramesPerSecond     *int     `json:"frames_per_second,omitempty" help:"optional; frames per second"`
	OutputResolution    string   `json:"output_resolution,omitempty" help:"optional; output resolution"`
	NegativePrompt      string   `json:"negative_prompt,omitempty" help:"optional; what to avoid"`
	Seed                *int     `json:"seed,omitempty" help:"optional; random seed"`
	NumInferenceSteps   *int     `json:"num_inference_steps,omitempty" help:"optional; denoising steps"`
	GuidanceScale       *float64 `json:"guidance_scale,omitempty" help:"optional; classifier-free guidance scale"`
	Shift               *float64 `json:"shift,omitempty" help:"optional; noise shift"`
	EnableSafetyChecker *bool    `json:"enable_safety_checker,omitempty" help:"optional; content safety check toggle"`
}

// AnimateParams configures motion-transfer animation.
// Transfers motion from a reference video onto a subject in the source image.
// Use wan-2.2-animate-move to preserve the subject and animate its motion, or
// wan-2.2-animate-replace to replace the subject with the reference video's subject.
type AnimateParams struct {
	Model               string `json:"model" help:"required; model slug"`
	SourceImageURL      string `json:"source_image_url" help:"required; character or subject image URL"`
	ReferenceVideoURL   string `json:"reference_video_url" help:"required; motion reference video URL"`
	CallbackURL         string `json:"callback_url,omitempty" help:"optional; webhook URL for async notifications"`
	OutputResolution    string `json:"output_resolution,omitempty" help:"optional; output resolution"`
	EnableSafetyChecker *bool  `json:"enable_safety_checker,omitempty" help:"optional; content safety check toggle"`
}

// ColorPaletteItem constrains image generation to a specific color.
// Hex is a CSS hex code (e.g. "#FF0000"), Ratio is the proportion of the palette (0.0-1.0).
type ColorPaletteItem struct {
	Hex   string  `json:"hex" help:"required; hex color code e.g. #FF0000"`
	Ratio float64 `json:"ratio,omitempty" help:"optional; proportion 0.0-1.0"`
}

// TextToImageParams configures text-to-image generation.
// ThinkingMode enables enhanced prompt reasoning and is only available with the Pro model.
// SourceImageURLs enables image editing mode where the prompt describes changes to apply.
type TextToImageParams struct {
	Model               string             `json:"model" help:"required; model slug"`
	Prompt              string             `json:"prompt" help:"required; text prompt describing the image"`
	CallbackURL         string             `json:"callback_url,omitempty" help:"optional; webhook URL for async notifications"`
	AspectRatio         string             `json:"aspect_ratio,omitempty" help:"optional; output aspect ratio"`
	OutputResolution    string             `json:"output_resolution,omitempty" help:"optional; output resolution"`
	OutputCount         *int               `json:"output_count,omitempty" help:"optional; number of generated images"`
	EnableSequential    *bool              `json:"enable_sequential,omitempty" help:"optional; sequential generation mode"`
	ThinkingMode        *bool              `json:"thinking_mode,omitempty" help:"optional; enhanced reasoning mode (pro only)"`
	Watermark           *bool              `json:"watermark,omitempty" help:"optional; add watermark"`
	Seed                *int               `json:"seed,omitempty" help:"optional; random seed"`
	EnableSafetyChecker *bool              `json:"enable_safety_checker,omitempty" help:"optional; content safety check toggle"`
	SourceImageURLs     []string           `json:"source_image_urls,omitempty" help:"optional; source image URLs for image editing"`
	ColorPalette        []ColorPaletteItem `json:"color_palette,omitempty" help:"optional; color palette constraints"`
	BboxList            []interface{}      `json:"bbox_list,omitempty" help:"optional; bounding box constraints"`
}

// EditVideoParams configures video editing with a text prompt.
// The 2.6 models use SourceVideoURLs (plural, required) while 2.7 uses SourceVideoURL (singular, required).
// Audio and MultiShots are flash-only features.
type EditVideoParams struct {
	Model                 string   `json:"model" help:"required; model slug"`
	SourceVideoURL        string   `json:"source_video_url,omitempty" help:"optional; source video URL (2-7 only)"`
	SourceVideoURLs       []string `json:"source_video_urls,omitempty" help:"optional; source video URLs (2-6 only)"`
	Prompt                string   `json:"prompt,omitempty" help:"optional for 2-7 (required for 2-6); description of the edit"`
	CallbackURL           string   `json:"callback_url,omitempty" help:"optional; webhook URL for async notifications"`
	NegativePrompt        string   `json:"negative_prompt,omitempty" help:"optional; what to avoid"`
	ReferenceImageURL     string   `json:"reference_image_url,omitempty" help:"optional; reference image URL"`
	OutputResolution      string   `json:"output_resolution,omitempty" help:"optional; output resolution"`
	AspectRatio           string   `json:"aspect_ratio,omitempty" help:"optional; output aspect ratio"`
	DurationSeconds       int      `json:"duration_seconds,omitempty" help:"optional; duration in seconds"`
	AudioSetting          string   `json:"audio_setting,omitempty" help:"optional; audio handling setting"`
	EnablePromptExpansion *bool    `json:"enable_prompt_expansion,omitempty" help:"optional; auto-expand prompt"`
	Watermark             *bool    `json:"watermark,omitempty" help:"optional; add watermark"`
	Seed                  *int     `json:"seed,omitempty" help:"optional; random seed"`
	EnableSafetyChecker   *bool    `json:"enable_safety_checker,omitempty" help:"optional; content safety check toggle"`
	Audio                 *bool    `json:"audio,omitempty" help:"optional; generate audio (flash only)"`
	MultiShots            *bool    `json:"multi_shots,omitempty" help:"optional; multi-shot mode (flash only)"`
}
