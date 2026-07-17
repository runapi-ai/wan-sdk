# Wan API Python SDK for RunAPI

The Wan Python SDK is the language-specific package for Wan on RunAPI. Use this package for video generation, animation, and video editing workflows when your application needs request bodies, task status lookup, and consistent RunAPI errors in Python.

This README is the Python package guide inside the public `wan-sdk` repository. For the repository overview, start at `../README.md`; for model details, use https://runapi.ai/models/wan; for API reference, use https://runapi.ai/docs#wan; for SDK docs, use https://runapi.ai/docs#sdk-wan.

## Install

```bash
pip install runapi-wan
```

## Quick start

```python
from runapi.wan import WanClient

client = WanClient()  # reads RUNAPI_API_KEY, or pass api_key="sk-..."

task = client.text_to_video.create(
    model="wan-2.6-text-to-video",
    prompt="A scenic mountain landscape with flowing rivers",
)
status = client.text_to_video.get(task.id)

image = client.text_to_image.create(
    model="wan-2.7-image",
    prompt="A neon city street after rain, cinematic",
)
```

Use `create` to submit a task and return quickly, `get` to fetch the latest task state, and `run` when a script should create and poll until completion:

```python
result = client.text_to_video.run(
    model="wan-2.6-text-to-video",
    prompt="A serene mountain lake at dawn",
)
print(result.videos[0].url)
```

In web request handlers, prefer `create` plus webhook or later `get` polling so a worker is not held open.

RunAPI-generated file URLs are temporary. Download and store generated images, videos, audio, or other files in your own durable storage within 7 days; do not treat returned URLs as long-term assets.

## Language notes

Pass parameters as keyword arguments and catch the `runapi.wan` error classes when building media jobs or scripts. The available resources are `text_to_video`, `image_to_video`, `speech_to_video`, `animate`, `text_to_image`, and `edit_video`. Keep `RUNAPI_API_KEY` in the environment or your secret manager; never commit API keys or callback secrets.

## Links

- Model page: https://runapi.ai/models/wan
- SDK docs: https://runapi.ai/docs#sdk-wan
- Product docs: https://runapi.ai/docs#wan
- Pricing and rate limits: https://runapi.ai/models/wan/2.2-a14b-text-to-video-turbo
- Provider comparison: https://runapi.ai/providers/alibaba
- Full catalog: https://runapi.ai/models
- Repository: https://github.com/runapi-ai/wan-sdk

## License

Licensed under the Apache License, Version 2.0.
