"""Wan model lists, enums, and response models."""

from __future__ import annotations

from runapi.core import BaseModel, TaskResponse, optional, required


class Video(BaseModel):
    url = optional(str)


class Image(BaseModel):
    url = optional(str)


class VideoTaskResponse(TaskResponse):
    """Wan video task status response."""

    id = required(str)
    status = optional(str, enum=lambda: TaskResponse.Status.ALL)
    videos = optional([lambda: Video])
    error = optional(str)


class ImageTaskResponse(TaskResponse):
    """Wan image task status response."""

    id = required(str)
    status = optional(str, enum=lambda: TaskResponse.Status.ALL)
    images = optional([lambda: Image])
    error = optional(str)


class CompletedVideoTaskResponse(VideoTaskResponse):
    """Narrowed response from ``run()`` once polling observes completion."""

    videos = required([lambda: Video])


class CompletedImageTaskResponse(ImageTaskResponse):
    """Narrowed response from ``run()`` once polling observes completion."""

    images = required([lambda: Image])
