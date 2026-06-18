"""Wan client."""

from __future__ import annotations

from typing import Any, Optional

from runapi.core import ClientOptions, HttpClient, resolve_api_key

from .resources.animate import Animate
from .resources.edit_video import EditVideo
from .resources.image_to_video import ImageToVideo
from .resources.speech_to_video import SpeechToVideo
from .resources.text_to_image import TextToImage
from .resources.text_to_video import TextToVideo


class WanClient:
    """Wan video and text-to-image client.

    Example::

        client = WanClient(api_key="sk-...")
        result = client.text_to_video.run(
            model="wan-2.6-text-to-video",
            prompt="A scenic mountain landscape with flowing rivers",
        )
    """

    def __init__(self, api_key: Optional[str] = None, **options: Any) -> None:
        resolved_api_key = resolve_api_key(api_key)
        client_options = ClientOptions(api_key=resolved_api_key, **options)
        http = client_options.http_client or HttpClient(client_options)
        self.text_to_video = TextToVideo(http)
        self.image_to_video = ImageToVideo(http)
        self.speech_to_video = SpeechToVideo(http)
        self.animate = Animate(http)
        self.text_to_image = TextToImage(http)
        self.edit_video = EditVideo(http)
