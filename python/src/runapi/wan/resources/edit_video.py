"""Wan edit-video resource."""

from __future__ import annotations

from typing import Any, Dict

from runapi.core import Resource, ValidationError

from ..types import (
    EDIT_VIDEO_MODELS,
    CompletedVideoTaskResponse,
    VideoTaskResponse,
)


class EditVideo(Resource):
    """Edit videos with Wan models."""

    ENDPOINT = "/api/v1/wan/edit_video"

    RESPONSE_CLASS = VideoTaskResponse
    COMPLETED_RESPONSE_CLASS = CompletedVideoTaskResponse

    def run(self, **params: Any) -> Any:
        """Edit a video and poll until it completes.

        Args:
            **params: video edit parameters (model, ...).

        Returns:
            The completed (narrowed) video edit response.
        """
        task = self.create(**params)
        return self._poll_until_complete(lambda: self.get(task.id))

    def create(self, **params: Any) -> Any:
        """Create a video edit task and return immediately with an id.

        Args:
            **params: video edit parameters (model, ...).

        Returns:
            The task creation result with an id.
        """
        compacted = self._compact_params(params)
        self._validate_params(compacted)
        return self._request("post", self.ENDPOINT, body=compacted)

    def get(self, id: str) -> Any:
        """Fetch the current status of a video edit task.

        Args:
            id: The task id returned by ``create``.

        Returns:
            The current task status.
        """
        return self._request("get", f"{self.ENDPOINT}/{id}")

    def _validate_params(self, params: Dict[str, Any]) -> None:
        if not params.get("model"):
            raise ValidationError("model is required")

        model = params.get("model")
        if model not in EDIT_VIDEO_MODELS:
            raise ValidationError(f"Invalid model: {model}. Must be one of: {', '.join(EDIT_VIDEO_MODELS)}")

        if "2.6" in model:
            if not params.get("prompt"):
                raise ValidationError("prompt is required")
            urls = params.get("source_video_urls")
            if urls is None or len(urls) == 0:
                raise ValidationError("source_video_urls is required")
        else:
            if not params.get("source_video_url"):
                raise ValidationError("source_video_url is required")
