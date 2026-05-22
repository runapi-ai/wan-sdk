// Package wan provides the Wan video and image generation API client.
package wan

// TextToVideoModel identifies the Wan text-to-video model variant.
type TextToVideoModel string

// ImageToVideoModel identifies the Wan image-to-video model variant.
type ImageToVideoModel string

// VideoToVideoModel identifies the Wan video-to-video model variant.
type VideoToVideoModel string

// ImageModel identifies the Wan image generation model variant.
type ImageModel string

const (
	ModelT2V22Turbo TextToVideoModel = "wan-2.2-a14b-text-to-video-turbo"
	ModelT2V25      TextToVideoModel = "wan-2.5-text-to-video"
	ModelT2V26      TextToVideoModel = "wan-2.6-text-to-video"
	ModelT2V27      TextToVideoModel = "wan-2.7-text-to-video"

	ModelI2V22Turbo ImageToVideoModel = "wan-2.2-a14b-image-to-video-turbo"
	ModelI2V25      ImageToVideoModel = "wan-2.5-image-to-video"
	ModelI2V26      ImageToVideoModel = "wan-2.6-image-to-video"
	ModelI2V26Flash ImageToVideoModel = "wan-2.6-flash-image-to-video"
	ModelI2V27      ImageToVideoModel = "wan-2.7-image-to-video"

	ModelV2V26      VideoToVideoModel = "wan-2.6-video-to-video"
	ModelV2V26Flash VideoToVideoModel = "wan-2.6-flash-video-to-video"

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
	Model                 string `json:"model" help:"required; wan-2.2-a14b-text-to-video-turbo, wan-2.5-text-to-video, wan-2.6-text-to-video, or wan-2.7-text-to-video"`
	Prompt                string `json:"prompt" help:"required; text prompt describing the video"`
	CallbackURL           string `json:"callback_url,omitempty" help:"optional; webhook URL for async notifications"`
	Duration              string `json:"duration,omitempty" help:"optional; video duration in seconds. Required for 2-5"`
	Resolution            string `json:"resolution,omitempty" help:"optional; e.g. 720p, 1080p"`
	AspectRatio           string `json:"aspect_ratio,omitempty" help:"optional; 16:9, 9:16, 1:1, 4:3, 3:4"`
	Ratio                 string `json:"ratio,omitempty" help:"optional; alternative ratio format (2-7 only)"`
	NegativePrompt        string `json:"negative_prompt,omitempty" help:"optional; what to avoid in the video (2-5, 2-7 only)"`
	EnablePromptExpansion *bool  `json:"enable_prompt_expansion,omitempty" help:"optional; auto-expand prompt (2-2, 2-5 only)"`
	PromptExtend          *bool  `json:"prompt_extend,omitempty" help:"optional; extend prompt (2-7 only)"`
	Seed                  *int   `json:"seed,omitempty" help:"optional; random seed for reproducibility"`
	Acceleration          string `json:"acceleration,omitempty" help:"optional; generation speed (2-2 only)"`
	NsfwChecker           *bool  `json:"nsfw_checker,omitempty" help:"optional; content safety filter"`
	Watermark             *bool  `json:"watermark,omitempty" help:"optional; add watermark (2-7 only)"`
	AudioURL              string `json:"audio_url,omitempty" help:"optional; background audio URL (2-7 only)"`
}

// ImageToVideoParams contains parameters for creating an image-to-video task.
type ImageToVideoParams struct {
	Model                 string   `json:"model" help:"required; wan-2.2-a14b-image-to-video-turbo, wan-2.5-image-to-video, wan-2.6-image-to-video, wan-2.6-flash-image-to-video, or wan-2.7-image-to-video"`
	Prompt                string   `json:"prompt,omitempty" help:"optional for 2-2/2-5 (required for 2-6/2-7); text prompt"`
	CallbackURL           string   `json:"callback_url,omitempty" help:"optional; webhook URL for async notifications"`
	ImageURL              string   `json:"image_url,omitempty" help:"optional; input image URL (2-2, 2-5 only)"`
	ImageURLs             []string `json:"image_urls,omitempty" help:"optional; input image URLs (2-6, 2-7 only)"`
	Duration              string   `json:"duration,omitempty" help:"optional; video duration. Required for 2-5"`
	Resolution            string   `json:"resolution,omitempty" help:"optional; output resolution"`
	AspectRatio           string   `json:"aspect_ratio,omitempty" help:"optional; 16:9, 9:16, 1:1 (2-2, 2-5 only)"`
	NegativePrompt        string   `json:"negative_prompt,omitempty" help:"optional; what to avoid (2-5, 2-7 only)"`
	EnablePromptExpansion *bool    `json:"enable_prompt_expansion,omitempty" help:"optional; auto-expand prompt (2-2, 2-5 only)"`
	PromptExtend          *bool    `json:"prompt_extend,omitempty" help:"optional; extend prompt (2-7 only)"`
	Seed                  *int     `json:"seed,omitempty" help:"optional; random seed"`
	Acceleration          string   `json:"acceleration,omitempty" help:"optional; generation speed (2-2 only)"`
	NsfwChecker           *bool    `json:"nsfw_checker,omitempty" help:"optional; content safety filter"`
	Watermark             *bool    `json:"watermark,omitempty" help:"optional; add watermark (2-7 only)"`
	Audio                 *bool    `json:"audio,omitempty" help:"optional; generate audio (flash only)"`
	MultiShots            *bool    `json:"multi_shots,omitempty" help:"optional; multi-shot mode (flash only)"`
	FirstFrameURL         string   `json:"first_frame_url,omitempty" help:"optional; first frame image URL (2-7 only)"`
	LastFrameURL          string   `json:"last_frame_url,omitempty" help:"optional; last frame image URL (2-7 only)"`
	FirstClipURL          string   `json:"first_clip_url,omitempty" help:"optional; first clip video URL (2-7 only)"`
	DrivingAudioURL       string   `json:"driving_audio_url,omitempty" help:"optional; driving audio URL (2-7 only)"`
	AudioURL              string   `json:"audio_url,omitempty" help:"optional; background audio URL (2-7 only)"`
	Ratio                 string   `json:"ratio,omitempty" help:"optional; alternative ratio format (2-7 only)"`
}

// VideoToVideoParams contains parameters for creating a video-to-video task.
type VideoToVideoParams struct {
	Model       string   `json:"model" help:"required; wan-2.6-video-to-video or wan-2.6-flash-video-to-video"`
	Prompt      string   `json:"prompt" help:"required; text prompt describing the output"`
	VideoURLs   []string `json:"video_urls,omitempty" help:"optional; input video URLs"`
	CallbackURL string   `json:"callback_url,omitempty" help:"optional; webhook URL for async notifications"`
	Duration    string   `json:"duration,omitempty" help:"optional; output duration in seconds"`
	Resolution  string   `json:"resolution,omitempty" help:"optional; output resolution"`
	NsfwChecker *bool    `json:"nsfw_checker,omitempty" help:"optional; content safety filter"`
	Audio       *bool    `json:"audio,omitempty" help:"optional; generate audio (flash only)"`
	MultiShots  *bool    `json:"multi_shots,omitempty" help:"optional; multi-shot mode (flash only)"`
}

// SpeechToVideoParams contains parameters for creating a speech-to-video task.
type SpeechToVideoParams struct {
	Model             string   `json:"model" help:"required; wan-2.2-a14b-speech-to-video-turbo"`
	ImageURL          string   `json:"image_url" help:"required; reference image URL"`
	AudioURL          string   `json:"audio_url" help:"required; driving audio URL"`
	Prompt            string   `json:"prompt,omitempty" help:"optional; additional description prompt"`
	CallbackURL       string   `json:"callback_url,omitempty" help:"optional; webhook URL for async notifications"`
	NumFrames         *int     `json:"num_frames,omitempty" help:"optional; number of output frames"`
	FramesPerSecond   *int     `json:"frames_per_second,omitempty" help:"optional; frames per second"`
	Resolution        string   `json:"resolution,omitempty" help:"optional; output resolution"`
	NegativePrompt    string   `json:"negative_prompt,omitempty" help:"optional; what to avoid"`
	Seed              *int     `json:"seed,omitempty" help:"optional; random seed"`
	NumInferenceSteps *int     `json:"num_inference_steps,omitempty" help:"optional; denoising steps"`
	GuidanceScale     *float64 `json:"guidance_scale,omitempty" help:"optional; classifier-free guidance scale"`
	Shift             *float64 `json:"shift,omitempty" help:"optional; noise shift"`
	NsfwChecker       *bool    `json:"nsfw_checker,omitempty" help:"optional; content safety filter"`
}

// AnimateParams contains parameters for creating an animate task.
type AnimateParams struct {
	Model       string `json:"model" help:"required; wan-2.2-animate-move or wan-2.2-animate-replace"`
	VideoURL    string `json:"video_url" help:"required; input video URL"`
	ImageURL    string `json:"image_url" help:"required; reference image URL"`
	CallbackURL string `json:"callback_url,omitempty" help:"optional; webhook URL for async notifications"`
	Resolution  string `json:"resolution,omitempty" help:"optional; output resolution"`
	NsfwChecker *bool  `json:"nsfw_checker,omitempty" help:"optional; content safety filter"`
}

// ColorPaletteItem defines a color swatch with hex and ratio.
type ColorPaletteItem struct {
	Hex   string  `json:"hex" help:"required; hex color code e.g. #FF0000"`
	Ratio float64 `json:"ratio,omitempty" help:"optional; proportion 0.0-1.0"`
}

// TextToImageParams contains parameters for creating an text-to-image task.
type TextToImageParams struct {
	Model            string             `json:"model" help:"required; wan-2.7-image or wan-2.7-image-pro"`
	Prompt           string             `json:"prompt" help:"required; text prompt describing the image"`
	CallbackURL      string             `json:"callback_url,omitempty" help:"optional; webhook URL for async notifications"`
	AspectRatio      string             `json:"aspect_ratio,omitempty" help:"optional; 1:1, 16:9, 9:16, 4:3, 3:4, 3:2, 2:3"`
	Resolution       string             `json:"resolution,omitempty" help:"optional; output resolution"`
	N                *int               `json:"n,omitempty" help:"optional; number of images to generate"`
	EnableSequential *bool              `json:"enable_sequential,omitempty" help:"optional; sequential generation mode"`
	ThinkingMode     *bool              `json:"thinking_mode,omitempty" help:"optional; enhanced reasoning mode (pro only)"`
	Watermark        *bool              `json:"watermark,omitempty" help:"optional; add watermark"`
	Seed             *int               `json:"seed,omitempty" help:"optional; random seed"`
	NsfwChecker      *bool              `json:"nsfw_checker,omitempty" help:"optional; content safety filter"`
	InputURLs        []string           `json:"input_urls,omitempty" help:"optional; reference image URLs"`
	ColorPalette     []ColorPaletteItem `json:"color_palette,omitempty" help:"optional; color palette constraints"`
	BboxList         []interface{}      `json:"bbox_list,omitempty" help:"optional; bounding box constraints"`
}

// ReferenceToVideoParams contains parameters for creating a reference-to-video task.
type ReferenceToVideoParams struct {
	Model          string   `json:"model" help:"required; wan-2.7-r2v"`
	Prompt         string   `json:"prompt" help:"required; text prompt describing the video"`
	CallbackURL    string   `json:"callback_url,omitempty" help:"optional; webhook URL for async notifications"`
	NegativePrompt string   `json:"negative_prompt,omitempty" help:"optional; what to avoid"`
	FirstFrame     string   `json:"first_frame,omitempty" help:"optional; first frame image URL"`
	ReferenceImage []string `json:"reference_image,omitempty" help:"optional; reference image URLs"`
	ReferenceVideo []string `json:"reference_video,omitempty" help:"optional; reference video URLs"`
	ReferenceVoice string   `json:"reference_voice,omitempty" help:"optional; reference voice audio URL"`
	Resolution     string   `json:"resolution,omitempty" help:"optional; output resolution"`
	AspectRatio    string   `json:"aspect_ratio,omitempty" help:"optional; 16:9, 9:16, 1:1"`
	Duration       string   `json:"duration,omitempty" help:"optional; video duration in seconds"`
	PromptExtend   *bool    `json:"prompt_extend,omitempty" help:"optional; extend prompt"`
	Watermark      *bool    `json:"watermark,omitempty" help:"optional; add watermark"`
	Seed           *int     `json:"seed,omitempty" help:"optional; random seed"`
	NsfwChecker    *bool    `json:"nsfw_checker,omitempty" help:"optional; content safety filter"`
}

// EditVideoParams contains parameters for creating a edit-video task.
type EditVideoParams struct {
	Model          string `json:"model" help:"required; wan-2.7-videoedit"`
	VideoURL       string `json:"video_url" help:"required; input video URL to edit"`
	Prompt         string `json:"prompt,omitempty" help:"optional; description of the edit"`
	CallbackURL    string `json:"callback_url,omitempty" help:"optional; webhook URL for async notifications"`
	NegativePrompt string `json:"negative_prompt,omitempty" help:"optional; what to avoid"`
	ReferenceImage string `json:"reference_image,omitempty" help:"optional; reference image URL"`
	Resolution     string `json:"resolution,omitempty" help:"optional; output resolution"`
	AspectRatio    string `json:"aspect_ratio,omitempty" help:"optional; output aspect ratio"`
	Duration       string `json:"duration,omitempty" help:"optional; output duration in seconds"`
	AudioSetting   string `json:"audio_setting,omitempty" help:"optional; audio handling setting"`
	PromptExtend   *bool  `json:"prompt_extend,omitempty" help:"optional; extend prompt"`
	Watermark      *bool  `json:"watermark,omitempty" help:"optional; add watermark"`
	Seed           *int   `json:"seed,omitempty" help:"optional; random seed"`
	NsfwChecker    *bool  `json:"nsfw_checker,omitempty" help:"optional; content safety filter"`
}
