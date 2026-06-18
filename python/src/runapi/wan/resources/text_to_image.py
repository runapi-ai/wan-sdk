"""Wan text-to-image resource."""

from __future__ import annotations

from typing import Any, Dict

from runapi.core import Resource, ValidationError

from ..types import (
    TEXT_TO_IMAGE_MODELS,
    CompletedImageTaskResponse,
    ImageTaskResponse,
)


class TextToImage(Resource):
    """Generate images from text prompts with Wan models."""

    ENDPOINT = "/api/v1/wan/text_to_image"

    RESPONSE_CLASS = ImageTaskResponse
    COMPLETED_RESPONSE_CLASS = CompletedImageTaskResponse

    def run(self, **params: Any) -> Any:
        """Generate an image and poll until it completes.

        Args:
            **params: text-to-image parameters (model, ...).

        Returns:
            The completed (narrowed) text-to-image response.
        """
        task = self.create(**params)
        return self._poll_until_complete(lambda: self.get(task.id))

    def create(self, **params: Any) -> Any:
        """Create a text-to-image task and return immediately with an id.

        Args:
            **params: text-to-image parameters (model, ...).

        Returns:
            The task creation result with an id.
        """
        compacted = self._compact_params(params)
        self._validate_params(compacted)
        return self._request("post", self.ENDPOINT, body=compacted)

    def get(self, id: str) -> Any:
        """Fetch the current status of a text-to-image task.

        Args:
            id: The task id returned by ``create``.

        Returns:
            The current task status.
        """
        return self._request("get", f"{self.ENDPOINT}/{id}")

    def _validate_params(self, params: Dict[str, Any]) -> None:
        if not params.get("model"):
            raise ValidationError("model is required")
        if not params.get("prompt"):
            raise ValidationError("prompt is required")

        model = params.get("model")
        if model not in TEXT_TO_IMAGE_MODELS:
            raise ValidationError(f"Invalid model: {model}. Must be one of: {', '.join(TEXT_TO_IMAGE_MODELS)}")
