"""Wan animate resource."""

from __future__ import annotations

from typing import Any, Dict

from runapi.core import Resource, ValidationError

from ..types import (
    ANIMATE_MODELS,
    CompletedVideoTaskResponse,
    VideoTaskResponse,
)


class Animate(Resource):
    """Animation operations (animate-move, animate-replace) with Wan models."""

    ENDPOINT = "/api/v1/wan/animate"

    RESPONSE_CLASS = VideoTaskResponse
    COMPLETED_RESPONSE_CLASS = CompletedVideoTaskResponse

    def run(self, **params: Any) -> Any:
        """Transfer motion and poll until it completes.

        Args:
            **params: animation parameters (model, ...).

        Returns:
            The completed (narrowed) animation response.
        """
        task = self.create(**params)
        return self._poll_until_complete(lambda: self.get(task.id))

    def create(self, **params: Any) -> Any:
        """Create a animation task and return immediately with an id.

        Args:
            **params: animation parameters (model, ...).

        Returns:
            The task creation result with an id.
        """
        compacted = self._compact_params(params)
        self._validate_params(compacted)
        return self._request("post", self.ENDPOINT, body=compacted)

    def get(self, id: str) -> Any:
        """Fetch the current status of a animation task.

        Args:
            id: The task id returned by ``create``.

        Returns:
            The current task status.
        """
        return self._request("get", f"{self.ENDPOINT}/{id}")

    def _validate_params(self, params: Dict[str, Any]) -> None:
        if not params.get("model"):
            raise ValidationError("model is required")
        if not params.get("source_image_url"):
            raise ValidationError("source_image_url is required")
        if not params.get("reference_video_url"):
            raise ValidationError("reference_video_url is required")

        model = params.get("model")
        if model not in ANIMATE_MODELS:
            raise ValidationError(f"Invalid model: {model}. Must be one of: {', '.join(ANIMATE_MODELS)}")
