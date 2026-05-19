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

func TestTextToVideoCreate(t *testing.T) {
	stub := &stubHTTPClient{
		response: json.RawMessage(`{"id":"task_t2v_123","status":"processing"}`),
	}
	client := NewClientWithHTTP(stub)
	resp, err := client.TextToVideo.Create(context.Background(), TextToVideoParams{
		Model:  string(ModelT2V26),
		Prompt: "A scenic mountain landscape",
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
	if body["model"] != "wan-2-6-text-to-video" {
		t.Fatalf("unexpected model: %v", body["model"])
	}
	if resp.ID != "task_t2v_123" {
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
		Model:     string(ModelI2V26),
		Prompt:    "Make this image move",
		ImageURLs: []string{"https://example.com/input.jpg"},
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
}

func TestVideoToVideoCreate(t *testing.T) {
	stub := &stubHTTPClient{
		response: json.RawMessage(`{"id":"task_v2v_123","status":"processing"}`),
	}
	client := NewClientWithHTTP(stub)
	resp, err := client.VideoToVideo.Create(context.Background(), VideoToVideoParams{
		Model:     string(ModelV2V26),
		Prompt:    "Add cinematic color grading",
		VideoURLs: []string{"https://example.com/input.mp4"},
	})
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "POST" || stub.path != videoToVideoPath {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
	if resp.ID != "task_v2v_123" {
		t.Fatalf("unexpected ID: %v", resp.ID)
	}
}

func TestSpeechToVideoCreate(t *testing.T) {
	stub := &stubHTTPClient{
		response: json.RawMessage(`{"id":"task_s2v_123","status":"processing"}`),
	}
	client := NewClientWithHTTP(stub)
	resp, err := client.SpeechToVideo.Create(context.Background(), SpeechToVideoParams{
		Model:    "wan-2-2-a14b-speech-to-video-turbo",
		ImageURL: "https://example.com/face.jpg",
		AudioURL: "https://example.com/speech.mp3",
	})
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "POST" || stub.path != speechToVideoPath {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
	body := stub.body.(map[string]any)
	if body["image_url"] != "https://example.com/face.jpg" {
		t.Fatalf("unexpected image_url: %v", body["image_url"])
	}
	if body["audio_url"] != "https://example.com/speech.mp3" {
		t.Fatalf("unexpected audio_url: %v", body["audio_url"])
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
		Model:    "wan-2-2-animate-move",
		VideoURL: "https://example.com/motion.mp4",
		ImageURL: "https://example.com/character.jpg",
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
}

func TestTextToImageCreate(t *testing.T) {
	stub := &stubHTTPClient{
		response: json.RawMessage(`{"id":"task_img_123","status":"processing"}`),
	}
	client := NewClientWithHTTP(stub)
	resp, err := client.TextToImage.Create(context.Background(), TextToImageParams{
		Model:  string(ModelImage27),
		Prompt: "A surreal dreamscape with floating islands",
	})
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "POST" || stub.path != textToImagePath {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
	if resp.ID != "task_img_123" {
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

func TestReferenceToVideoCreate(t *testing.T) {
	stub := &stubHTTPClient{
		response: json.RawMessage(`{"id":"task_r2v_123","status":"processing"}`),
	}
	client := NewClientWithHTTP(stub)
	resp, err := client.ReferenceToVideo.Create(context.Background(), ReferenceToVideoParams{
		Model:          "wan-2-7-r2v",
		Prompt:         "A person walking in the park",
		ReferenceImage: []string{"https://example.com/person.jpg"},
	})
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "POST" || stub.path != referenceToVideoPath {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
	if resp.ID != "task_r2v_123" {
		t.Fatalf("unexpected ID: %v", resp.ID)
	}
}

func TestEditVideoCreate(t *testing.T) {
	stub := &stubHTTPClient{
		response: json.RawMessage(`{"id":"task_vedit_123","status":"processing"}`),
	}
	client := NewClientWithHTTP(stub)
	resp, err := client.EditVideo.Create(context.Background(), EditVideoParams{
		Model:    "wan-2-7-videoedit",
		VideoURL: "https://example.com/original.mp4",
		Prompt:   "Make the sky more dramatic",
	})
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "POST" || stub.path != editVideoPath {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
	body := stub.body.(map[string]any)
	if body["video_url"] != "https://example.com/original.mp4" {
		t.Fatalf("unexpected video_url: %v", body["video_url"])
	}
	if resp.ID != "task_vedit_123" {
		t.Fatalf("unexpected ID: %v", resp.ID)
	}
}
