// Package wan provides the Wan video and image generation API client.
package wan

// TextToVideoModel identifies the Wan text-to-video model variant.
type TextToVideoModel string

// ImageToVideoModel identifies the Wan image-to-video model variant.
type ImageToVideoModel string

// EditVideoModel identifies the Wan video editing model variant.
type EditVideoModel string

// ImageModel identifies the Wan image generation model variant.
type ImageModel string

const (
	ModelT2V22Turbo TextToVideoModel = "wan-2.2-a14b-text-to-video-turbo"
	ModelT2V25      TextToVideoModel = "wan-2.5-text-to-video"
	ModelT2V26      TextToVideoModel = "wan-2.6-text-to-video"
	ModelT2V27      TextToVideoModel = "wan-2.7-text-to-video"
	ModelT2V27R2V   TextToVideoModel = "wan-2.7-r2v"

	ModelI2V22Turbo ImageToVideoModel = "wan-2.2-a14b-image-to-video-turbo"
	ModelI2V25      ImageToVideoModel = "wan-2.5-image-to-video"
	ModelI2V26      ImageToVideoModel = "wan-2.6-image-to-video"
	ModelI2V26Flash ImageToVideoModel = "wan-2.6-flash-image-to-video"
	ModelI2V27      ImageToVideoModel = "wan-2.7-image-to-video"

	ModelEdit26      EditVideoModel = "wan-2.6-edit-video"
	ModelEdit26Flash EditVideoModel = "wan-2.6-flash-edit-video"
	ModelEdit27      EditVideoModel = "wan-2.7-edit-video"

	ModelImage27    ImageModel = "wan-2.7-image"
	ModelImage27Pro ImageModel = "wan-2.7-image-pro"
)

// Video contains a generated video URL.
type Video struct {
	URL string `json:"url"`
}

// Image contains a generated image URL.
type Image struct {
	URL string `json:"url"`
}

// AsyncTaskResponse is the base response for async tasks.
type AsyncTaskResponse struct {
	ID     string `json:"id"`
	Status string `json:"status"`
	Error  string `json:"error,omitempty"`
}

func (r AsyncTaskResponse) GetID() string     { return r.ID }
func (r AsyncTaskResponse) GetStatus() string { return r.Status }
func (r AsyncTaskResponse) GetError() string  { return r.Error }

// VideoTaskResponse is returned when polling a video generation task.
type VideoTaskResponse struct {
	AsyncTaskResponse
	Videos []Video `json:"videos,omitempty"`
}

// ImageTaskResponse is returned when polling an text-to-image task.
type ImageTaskResponse struct {
	AsyncTaskResponse
	Images []Image `json:"images,omitempty"`
}

// TextToVideoParams contains parameters for creating a text-to-video task.
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
	Seed                  *int     `json:"seed,omitempty" help:"optional; random seed for reproducibility"`
	Acceleration          string   `json:"acceleration,omitempty" help:"optional; acceleration mode"`
	EnableSafetyChecker   *bool    `json:"enable_safety_checker,omitempty" help:"optional; content safety check toggle"`
	Watermark             *bool    `json:"watermark,omitempty" help:"optional; add watermark (2-7 only)"`
	BackgroundAudioURL    string   `json:"background_audio_url,omitempty" help:"optional; background audio URL (2-7 only)"`
}

// ImageToVideoParams contains parameters for creating an image-to-video task.
type ImageToVideoParams struct {
	Model                 string   `json:"model" help:"required; model slug"`
	Prompt                string   `json:"prompt,omitempty" help:"optional for 2-2/2-5 (required for 2-6/2-7); text prompt"`
	CallbackURL           string   `json:"callback_url,omitempty" help:"optional; webhook URL for async notifications"`
	FirstFrameImageURL    string   `json:"first_frame_image_url,omitempty" help:"optional; first frame image URL"`
	LastFrameImageURL     string   `json:"last_frame_image_url,omitempty" help:"optional; last frame image URL (2-7 only)"`
	SourceVideoURL        string   `json:"source_video_url,omitempty" help:"optional; source video URL for continuation (2-7 only)"`
	DurationSeconds       int      `json:"duration_seconds,omitempty" help:"optional; duration in seconds"`
	OutputResolution      string   `json:"output_resolution,omitempty" help:"optional; output resolution"`
	AspectRatio           string   `json:"aspect_ratio,omitempty" help:"optional; output aspect ratio"`
	NegativePrompt        string   `json:"negative_prompt,omitempty" help:"optional; what to avoid (2-5, 2-7 only)"`
	EnablePromptExpansion *bool    `json:"enable_prompt_expansion,omitempty" help:"optional; auto-expand prompt"`
	Seed                  *int     `json:"seed,omitempty" help:"optional; random seed"`
	Acceleration          string   `json:"acceleration,omitempty" help:"optional; acceleration mode"`
	EnableSafetyChecker   *bool    `json:"enable_safety_checker,omitempty" help:"optional; content safety check toggle"`
	Watermark             *bool    `json:"watermark,omitempty" help:"optional; add watermark (2-7 only)"`
	Audio                 *bool    `json:"audio,omitempty" help:"optional; generate audio (flash only)"`
	MultiShots            *bool    `json:"multi_shots,omitempty" help:"optional; multi-shot mode (flash only)"`
	DrivingAudioURL       string   `json:"driving_audio_url,omitempty" help:"optional; driving audio URL (2-7 only)"`
	BackgroundAudioURL    string   `json:"background_audio_url,omitempty" help:"optional; background audio URL (2-7 only)"`
	Ratio                 string   `json:"ratio,omitempty" help:"optional; alternative ratio format (2-7 only)"`
}

// SpeechToVideoParams contains parameters for creating a speech-to-video task.
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

// AnimateParams contains parameters for creating an animate task.
type AnimateParams struct {
	Model               string `json:"model" help:"required; model slug"`
	SourceImageURL      string `json:"source_image_url" help:"required; character or subject image URL"`
	ReferenceVideoURL   string `json:"reference_video_url" help:"required; motion reference video URL"`
	CallbackURL         string `json:"callback_url,omitempty" help:"optional; webhook URL for async notifications"`
	OutputResolution    string `json:"output_resolution,omitempty" help:"optional; output resolution"`
	EnableSafetyChecker *bool  `json:"enable_safety_checker,omitempty" help:"optional; content safety check toggle"`
}

// ColorPaletteItem defines a color swatch with hex and ratio.
type ColorPaletteItem struct {
	Hex   string  `json:"hex" help:"required; hex color code e.g. #FF0000"`
	Ratio float64 `json:"ratio,omitempty" help:"optional; proportion 0.0-1.0"`
}

// TextToImageParams contains parameters for creating an text-to-image task.
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

// EditVideoParams contains parameters for creating an edit-video task.
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
