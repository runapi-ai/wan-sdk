package wan

import (
	"context"
	"encoding/json"
	"testing"

	"github.com/runapi-ai/core-sdk/go/core"
)

type stubHTTPClient struct {
	method   string
	path     string
	body     any
	response json.RawMessage
}

func (s *stubHTTPClient) Request(_ context.Context, method, path string, opts *core.HTTPRequestOptions) (json.RawMessage, error) {
	s.method = method
	s.path = path
	if opts != nil {
		s.body = opts.Body
	}
	return s.response, nil
}

func intPtr(v int) *int { return &v }

func TestTextToVideoCreate(t *testing.T) {
	stub := &stubHTTPClient{
		response: json.RawMessage(`{"id":"task_t2v_123","status":"processing"}`),
	}
	client := NewClientWithHTTP(stub)
	enableSafetyChecker := true
	resp, err := client.TextToVideo.Create(context.Background(), TextToVideoParams{
		Model:               string(ModelT2V26),
		Prompt:              "A scenic mountain landscape",
		EnableSafetyChecker: &enableSafetyChecker,
	})
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "POST" || stub.path != textToVideoPath {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
	body := stub.body.(map[string]any)
	if body["prompt"] != "A scenic mountain landscape" {
		t.Fatalf("unexpected prompt: %v", body["prompt"])
	}
	if body["model"] != "wan-2.6-text-to-video" {
		t.Fatalf("unexpected model: %v", body["model"])
	}
	if body["enable_safety_checker"] != true {
		t.Fatalf("unexpected enable_safety_checker: %v", body["enable_safety_checker"])
	}
	if resp.ID != "task_t2v_123" {
		t.Fatalf("unexpected ID: %v", resp.ID)
	}
}

func TestTextToVideoCreateR2V(t *testing.T) {
	stub := &stubHTTPClient{
		response: json.RawMessage(`{"id":"task_r2v_123","status":"processing"}`),
	}
	client := NewClientWithHTTP(stub)
	resp, err := client.TextToVideo.Create(context.Background(), TextToVideoParams{
		Model:              string(ModelT2V27R2V),
		Prompt:             "A person walking in the park",
		ReferenceImageURLs: []string{"https://cdn.runapi.ai/public/samples/person.jpg"},
		OutputResolution:   "1080p",
	})
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "POST" || stub.path != textToVideoPath {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
	body := stub.body.(map[string]any)
	if body["reference_image_urls"].([]any)[0] != "https://cdn.runapi.ai/public/samples/person.jpg" {
		t.Fatalf("unexpected reference_image_urls: %v", body["reference_image_urls"])
	}
	if body["output_resolution"] != "1080p" {
		t.Fatalf("unexpected output_resolution: %v", body["output_resolution"])
	}
	if _, ok := body["reference_image"]; ok {
		t.Fatalf("unexpected provider reference_image field: %v", body)
	}
	if resp.ID != "task_r2v_123" {
		t.Fatalf("unexpected ID: %v", resp.ID)
	}
}

func TestTextToVideoGet(t *testing.T) {
	stub := &stubHTTPClient{
		response: json.RawMessage(`{"id":"task_t2v_456","status":"completed","videos":[{"url":"https://file.runapi.ai/result.mp4"}]}`),
	}
	client := NewClientWithHTTP(stub)
	resp, err := client.TextToVideo.Get(context.Background(), "task_t2v_456")
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "GET" || stub.path != textToVideoPath+"/task_t2v_456" {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
	if resp.Status != "completed" {
		t.Fatalf("unexpected status: %v", resp.Status)
	}
	if len(resp.Videos) != 1 || resp.Videos[0].URL != "https://file.runapi.ai/result.mp4" {
		t.Fatalf("unexpected videos: %v", resp.Videos)
	}
}

func TestImageToVideoCreate(t *testing.T) {
	stub := &stubHTTPClient{
		response: json.RawMessage(`{"id":"task_i2v_123","status":"processing"}`),
	}
	client := NewClientWithHTTP(stub)
	resp, err := client.ImageToVideo.Create(context.Background(), ImageToVideoParams{
		Model:              string(ModelI2V26),
		Prompt:             "Make this image move",
		FirstFrameImageURL: "https://cdn.runapi.ai/public/samples/input.jpg",
		OutputResolution:   "1080p",
	})
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "POST" || stub.path != imageToVideoPath {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
	if resp.ID != "task_i2v_123" {
		t.Fatalf("unexpected ID: %v", resp.ID)
	}
	body := stub.body.(map[string]any)
	if body["first_frame_image_url"] != "https://cdn.runapi.ai/public/samples/input.jpg" {
		t.Fatalf("unexpected first_frame_image_url: %v", body["first_frame_image_url"])
	}
	if _, ok := body["image_urls"]; ok {
		t.Fatalf("unexpected provider image_urls field: %v", body)
	}
	if body["output_resolution"] != "1080p" {
		t.Fatalf("unexpected output_resolution: %v", body["output_resolution"])
	}
}

func TestEditVideoCreateWan26(t *testing.T) {
	stub := &stubHTTPClient{
		response: json.RawMessage(`{"id":"task_edit_26_123","status":"processing"}`),
	}
	client := NewClientWithHTTP(stub)
	resp, err := client.EditVideo.Create(context.Background(), EditVideoParams{
		Model:            string(ModelEdit26),
		Prompt:           "Add cinematic color grading",
		SourceVideoURLs:  []string{"https://cdn.runapi.ai/public/samples/source.mp4"},
		OutputResolution: "1080p",
		DurationSeconds:  5,
	})
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "POST" || stub.path != editVideoPath {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
	body := stub.body.(map[string]any)
	if body["model"] != "wan-2.6-edit-video" {
		t.Fatalf("unexpected model: %v", body["model"])
	}
	if body["source_video_urls"].([]any)[0] != "https://cdn.runapi.ai/public/samples/source.mp4" {
		t.Fatalf("unexpected source_video_urls: %v", body["source_video_urls"])
	}
	if body["output_resolution"] != "1080p" {
		t.Fatalf("unexpected output_resolution: %v", body["output_resolution"])
	}
	if resp.ID != "task_edit_26_123" {
		t.Fatalf("unexpected ID: %v", resp.ID)
	}
}

func TestSpeechToVideoCreate(t *testing.T) {
	stub := &stubHTTPClient{
		response: json.RawMessage(`{"id":"task_s2v_123","status":"processing"}`),
	}
	client := NewClientWithHTTP(stub)
	resp, err := client.SpeechToVideo.Create(context.Background(), SpeechToVideoParams{
		Model:            "wan-2.2-a14b-speech-to-video-turbo",
		SourceImageURL:   "https://cdn.runapi.ai/public/samples/face.jpg",
		SourceAudioURL:   "https://cdn.runapi.ai/public/samples/speech.mp3",
		OutputResolution: "720p",
	})
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "POST" || stub.path != speechToVideoPath {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
	body := stub.body.(map[string]any)
	if body["source_image_url"] != "https://cdn.runapi.ai/public/samples/face.jpg" {
		t.Fatalf("unexpected source_image_url: %v", body["source_image_url"])
	}
	if body["source_audio_url"] != "https://cdn.runapi.ai/public/samples/speech.mp3" {
		t.Fatalf("unexpected source_audio_url: %v", body["source_audio_url"])
	}
	if _, ok := body["image_url"]; ok {
		t.Fatalf("unexpected provider image_url field: %v", body)
	}
	if _, ok := body["audio_url"]; ok {
		t.Fatalf("unexpected provider audio_url field: %v", body)
	}
	if body["output_resolution"] != "720p" {
		t.Fatalf("unexpected output_resolution: %v", body["output_resolution"])
	}
	if resp.ID != "task_s2v_123" {
		t.Fatalf("unexpected ID: %v", resp.ID)
	}
}

func TestAnimateCreate(t *testing.T) {
	stub := &stubHTTPClient{
		response: json.RawMessage(`{"id":"task_anim_123","status":"processing"}`),
	}
	client := NewClientWithHTTP(stub)
	resp, err := client.Animate.Create(context.Background(), AnimateParams{
		Model:             "wan-2.2-animate-move",
		ReferenceVideoURL: "https://cdn.runapi.ai/public/samples/motion.mp4",
		SourceImageURL:    "https://cdn.runapi.ai/public/samples/character.jpg",
		OutputResolution:  "580p",
	})
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "POST" || stub.path != animatePath {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
	if resp.ID != "task_anim_123" {
		t.Fatalf("unexpected ID: %v", resp.ID)
	}
	body := stub.body.(map[string]any)
	if body["reference_video_url"] != "https://cdn.runapi.ai/public/samples/motion.mp4" {
		t.Fatalf("unexpected reference_video_url: %v", body["reference_video_url"])
	}
	if body["source_image_url"] != "https://cdn.runapi.ai/public/samples/character.jpg" {
		t.Fatalf("unexpected source_image_url: %v", body["source_image_url"])
	}
	if _, ok := body["video_url"]; ok {
		t.Fatalf("unexpected provider video_url field: %v", body)
	}
	if _, ok := body["image_url"]; ok {
		t.Fatalf("unexpected provider image_url field: %v", body)
	}
	if body["output_resolution"] != "580p" {
		t.Fatalf("unexpected output_resolution: %v", body["output_resolution"])
	}
}

func TestTextToImageCreate(t *testing.T) {
	stub := &stubHTTPClient{
		response: json.RawMessage(`{"id":"task_img_123","status":"processing"}`),
	}
	client := NewClientWithHTTP(stub)
	resp, err := client.TextToImage.Create(context.Background(), TextToImageParams{
		Model:            string(ModelImage27),
		Prompt:           "A surreal dreamscape with floating islands",
		AspectRatio:      "1:8",
		OutputResolution: "2k",
		OutputCount:      intPtr(2),
	})
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "POST" || stub.path != textToImagePath {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
	body := stub.body.(map[string]any)
	if body["aspect_ratio"] != "1:8" {
		t.Fatalf("unexpected aspect_ratio: %v", body["aspect_ratio"])
	}
	if body["output_count"] != float64(2) {
		t.Fatalf("unexpected output_count: %v", body["output_count"])
	}
	if body["output_resolution"] != "2k" {
		t.Fatalf("unexpected output_resolution: %v", body["output_resolution"])
	}
	if resp.ID != "task_img_123" {
		t.Fatalf("unexpected ID: %v", resp.ID)
	}
}

func TestTextToImageCreateWithSourceImages(t *testing.T) {
	stub := &stubHTTPClient{
		response: json.RawMessage(`{"id":"task_img_source_123","status":"processing"}`),
	}
	client := NewClientWithHTTP(stub)
	resp, err := client.TextToImage.Create(context.Background(), TextToImageParams{
		Model:           string(ModelImage27),
		Prompt:          "Edit this image",
		SourceImageURLs: []string{"https://cdn.runapi.ai/public/samples/source.jpg"},
	})
	if err != nil {
		t.Fatal(err)
	}
	body := stub.body.(map[string]any)
	if body["source_image_urls"].([]any)[0] != "https://cdn.runapi.ai/public/samples/source.jpg" {
		t.Fatalf("unexpected source_image_urls: %v", body["source_image_urls"])
	}
	if _, ok := body["input_urls"]; ok {
		t.Fatalf("unexpected provider input_urls field: %v", body)
	}
	if resp.ID != "task_img_source_123" {
		t.Fatalf("unexpected ID: %v", resp.ID)
	}
}

func TestTextToImageGet(t *testing.T) {
	stub := &stubHTTPClient{
		response: json.RawMessage(`{"id":"task_img_456","status":"completed","images":[{"url":"https://file.runapi.ai/result.jpg"}]}`),
	}
	client := NewClientWithHTTP(stub)
	resp, err := client.TextToImage.Get(context.Background(), "task_img_456")
	if err != nil {
		t.Fatal(err)
	}
	if len(resp.Images) != 1 || resp.Images[0].URL != "https://file.runapi.ai/result.jpg" {
		t.Fatalf("unexpected images: %v", resp.Images)
	}
}

func TestEditVideoCreate(t *testing.T) {
	stub := &stubHTTPClient{
		response: json.RawMessage(`{"id":"task_vedit_123","status":"processing"}`),
	}
	client := NewClientWithHTTP(stub)
	resp, err := client.EditVideo.Create(context.Background(), EditVideoParams{
		Model:             string(ModelEdit27),
		SourceVideoURL:    "https://cdn.runapi.ai/public/samples/source.mp4",
		Prompt:            "Make the sky more dramatic",
		ReferenceImageURL: "https://cdn.runapi.ai/public/samples/style.png",
		OutputResolution:  "1080p",
	})
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "POST" || stub.path != editVideoPath {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
	body := stub.body.(map[string]any)
	if body["source_video_url"] != "https://cdn.runapi.ai/public/samples/source.mp4" {
		t.Fatalf("unexpected source_video_url: %v", body["source_video_url"])
	}
	if body["reference_image_url"] != "https://cdn.runapi.ai/public/samples/style.png" {
		t.Fatalf("unexpected reference_image_url: %v", body["reference_image_url"])
	}
	if _, ok := body["reference_image"]; ok {
		t.Fatalf("unexpected provider reference_image field: %v", body)
	}
	if body["output_resolution"] != "1080p" {
		t.Fatalf("unexpected output_resolution: %v", body["output_resolution"])
	}
	if resp.ID != "task_vedit_123" {
		t.Fatalf("unexpected ID: %v", resp.ID)
	}
}
