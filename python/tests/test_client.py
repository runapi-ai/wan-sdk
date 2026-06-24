import pytest

from runapi.core import config
from runapi.core.errors import AuthenticationError, ValidationError
from runapi.wan import WanClient
from runapi.wan.resources.animate import Animate
from runapi.wan.resources.edit_video import EditVideo
from runapi.wan.resources.image_to_video import ImageToVideo
from runapi.wan.resources.speech_to_video import SpeechToVideo
from runapi.wan.resources.text_to_image import TextToImage
from runapi.wan.resources.text_to_video import TextToVideo
from runapi.wan.types import (
    CompletedVideoTaskResponse,
    VideoTaskResponse,
)


class FakeHttp:
    def __init__(self, *responses):
        self._responses = list(responses)
        self.calls = []

    def request(self, method, path, body=None, options=None):
        self.calls.append((method, path, body))
        if self._responses:
            return self._responses.pop(0)
        return {"id": "task_1", "status": "pending"}


@pytest.fixture(autouse=True)
def reset_config(monkeypatch):
    monkeypatch.delenv("RUNAPI_API_KEY", raising=False)
    monkeypatch.setattr(config, "api_key", None)
    yield


# --- auth -----------------------------------------------------------------


def test_accepts_api_key_parameter():
    assert isinstance(WanClient(api_key="k", http_client=FakeHttp()), WanClient)


def test_falls_back_to_global(monkeypatch):
    monkeypatch.setattr(config, "api_key", "global-key")
    assert isinstance(WanClient(http_client=FakeHttp()), WanClient)


def test_falls_back_to_env(monkeypatch):
    monkeypatch.setenv("RUNAPI_API_KEY", "env-key")
    assert isinstance(WanClient(http_client=FakeHttp()), WanClient)


def test_raises_without_api_key():
    with pytest.raises(AuthenticationError, match="API key is required"):
        WanClient()


# --- injection / accessors ------------------------------------------------


def test_uses_injected_http_client():
    fake = FakeHttp()
    client = WanClient(api_key="k", http_client=fake)
    assert client.text_to_video._http is fake
    assert client.image_to_video._http is fake
    assert client.speech_to_video._http is fake
    assert client.animate._http is fake
    assert client.text_to_image._http is fake
    assert client.edit_video._http is fake


def test_exposes_resource_accessors():
    client = WanClient(api_key="k", http_client=FakeHttp())
    assert isinstance(client.text_to_video, TextToVideo)
    assert isinstance(client.image_to_video, ImageToVideo)
    assert isinstance(client.speech_to_video, SpeechToVideo)
    assert isinstance(client.animate, Animate)
    assert isinstance(client.text_to_image, TextToImage)
    assert isinstance(client.edit_video, EditVideo)


# --- request shapes -------------------------------------------------------


def test_create_posts_compacted_body():
    fake = FakeHttp({"id": "t1", "status": "pending"})
    client = WanClient(api_key="k", http_client=fake)
    result = client.text_to_video.create(
        model="wan-2.6-text-to-video", prompt="hello world", seed=None
    )
    assert fake.calls == [
        ("post", "/api/v1/wan/text_to_video", {"model": "wan-2.6-text-to-video", "prompt": "hello world"}),
    ]
    assert isinstance(result, VideoTaskResponse)


def test_get_fetches_by_id():
    fake = FakeHttp({"id": "t1", "status": "processing"})
    client = WanClient(api_key="k", http_client=fake)
    client.text_to_video.get("t1")
    assert fake.calls == [("get", "/api/v1/wan/text_to_video/t1", None)]


def test_image_to_video_posts_to_endpoint():
    fake = FakeHttp({"id": "t1", "status": "pending"})
    client = WanClient(api_key="k", http_client=fake)
    client.image_to_video.create(
        model="wan-2.6-image-to-video",
        first_frame_image_url="https://x/a.png",
        prompt="make it move",
    )
    assert fake.calls == [
        (
            "post",
            "/api/v1/wan/image_to_video",
            {"model": "wan-2.6-image-to-video", "first_frame_image_url": "https://x/a.png", "prompt": "make it move"},
        ),
    ]


def test_text_to_image_uses_image_endpoint():
    fake = FakeHttp({"id": "t1", "status": "pending"})
    client = WanClient(api_key="k", http_client=fake)
    client.text_to_image.create(model="wan-2.7-image", prompt="a cat")
    assert fake.calls == [
        ("post", "/api/v1/wan/text_to_image", {"model": "wan-2.7-image", "prompt": "a cat"}),
    ]


def test_run_narrows_completed_type():
    fake = FakeHttp(
        {"id": "t1", "status": "pending"},
        {"id": "t1", "status": "completed", "videos": [{"url": "https://x/y.mp4"}]},
    )
    client = WanClient(api_key="k", http_client=fake)
    result = client.text_to_video.run(model="wan-2.6-text-to-video", prompt="a serene lake")
    assert isinstance(result, CompletedVideoTaskResponse)
    assert result.videos[0].url == "https://x/y.mp4"


# --- validation -----------------------------------------------------------


def test_rejects_unknown_model():
    client = WanClient(api_key="k", http_client=FakeHttp())
    with pytest.raises(ValidationError, match="model must be one of"):
        client.text_to_video.create(model="nope", prompt="hi there")


def test_requires_prompt():
    client = WanClient(api_key="k", http_client=FakeHttp())
    with pytest.raises(ValidationError, match="prompt is required"):
        client.text_to_video.create(model="wan-2.6-text-to-video")


def test_animate_requires_source_and_reference():
    client = WanClient(api_key="k", http_client=FakeHttp())
    with pytest.raises(ValidationError, match="reference_video_url is required"):
        client.animate.create(model="wan-2.2-animate-move")
    with pytest.raises(ValidationError, match="source_image_url is required"):
        client.animate.create(model="wan-2.2-animate-move", reference_video_url="https://x/v.mp4")


def test_speech_to_video_requires_image_and_audio():
    client = WanClient(api_key="k", http_client=FakeHttp())
    with pytest.raises(ValidationError, match="source_audio_url is required"):
        client.speech_to_video.create(
            model="wan-2.2-a14b-speech-to-video-turbo", prompt="say hi"
        )
    with pytest.raises(ValidationError, match="source_image_url is required"):
        client.speech_to_video.create(
            model="wan-2.2-a14b-speech-to-video-turbo",
            prompt="say hi",
            source_audio_url="https://x/a.mp3",
        )


def test_edit_video_2_6_requires_prompt_and_source_urls():
    client = WanClient(api_key="k", http_client=FakeHttp())
    with pytest.raises(ValidationError, match="prompt is required"):
        client.edit_video.create(model="wan-2.6-edit-video")
    with pytest.raises(ValidationError, match="source_video_urls is required"):
        client.edit_video.create(model="wan-2.6-edit-video", prompt="trim it")


def test_edit_video_non_2_6_requires_single_source_url():
    client = WanClient(api_key="k", http_client=FakeHttp())
    with pytest.raises(ValidationError, match="source_video_url is required"):
        client.edit_video.create(model="wan-2.7-edit-video")


def test_edit_video_non_2_6_accepts_single_source_url():
    fake = FakeHttp({"id": "t1", "status": "pending"})
    client = WanClient(api_key="k", http_client=fake)
    client.edit_video.create(model="wan-2.7-edit-video", source_video_url="https://x/v.mp4")
    assert fake.calls == [
        ("post", "/api/v1/wan/edit_video", {"model": "wan-2.7-edit-video", "source_video_url": "https://x/v.mp4"}),
    ]
