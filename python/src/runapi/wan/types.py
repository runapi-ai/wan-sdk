"""Wan model lists, enums, and response models."""

from __future__ import annotations

from runapi.core import BaseModel, TaskResponse, optional, required

TEXT_TO_VIDEO_MODELS = [
    "wan-2.2-a14b-text-to-video-turbo",
    "wan-2.5-text-to-video",
    "wan-2.6-text-to-video",
    "wan-2.7-text-to-video",
    "wan-2.7-r2v",
]

IMAGE_TO_VIDEO_MODELS = [
    "wan-2.2-a14b-image-to-video-turbo",
    "wan-2.5-image-to-video",
    "wan-2.6-image-to-video",
    "wan-2.6-flash-image-to-video",
    "wan-2.7-image-to-video",
]

SPEECH_TO_VIDEO_MODELS = ["wan-2.2-a14b-speech-to-video-turbo"]
ANIMATE_MODELS = ["wan-2.2-animate-move", "wan-2.2-animate-replace"]
TEXT_TO_IMAGE_MODELS = ["wan-2.7-image", "wan-2.7-image-pro"]
EDIT_VIDEO_MODELS = [
    "wan-2.6-edit-video",
    "wan-2.6-flash-edit-video",
    "wan-2.7-edit-video",
]


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
