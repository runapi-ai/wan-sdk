# Wan Video API Ruby SDK for RunAPI

The wan video api Ruby SDK is the language-specific package for Wan on RunAPI. Use this wan video api package for text-to-video, image-to-video, animation, and video editing flows when your application needs JSON request bodies, task status lookup, and consistent RunAPI errors in Ruby.

This wan video api README is the Ruby package guide inside the public `wan-sdk` repository. For the repository overview, start at `../README.md`; for model details, use https://runapi.ai/models/wan; for API reference, use https://runapi.ai/docs#wan; for SDK docs, use https://runapi.ai/docs#sdk-wan.

## Install

```bash
gem install runapi-wan
```

## Quick start

```ruby
require "runapi-wan"

client = RunApi::Wan::Client.new
task = client.text_to_video.create(
  # Pass the Wan JSON request body from https://runapi.ai/docs#wan.
)
status = client.text_to_video.get(task.id)
```

Use `create` when you want to submit a task and return quickly, `get` when you need the latest task state, and `run` when a script should create and poll until completion. In web request handlers, prefer `create` plus webhook or later `get` polling so a worker is not held open.

RunAPI-generated file URLs are temporary. Download and store generated images, videos, audio, or other files in your own durable storage within 7 days; do not treat returned URLs as long-term assets.

## Language notes

Use Ruby keyword arguments and the `RunApi::Wan` error classes when building video jobs, Rails workers, or scripts. The available resources include text to videos, image to videos, speech to videos, animations, images, and video edits. Keep `RUNAPI_API_KEY` in the environment or your secret manager; never commit API keys or callback secrets.

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
