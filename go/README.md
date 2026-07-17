# Wan API Go SDK for RunAPI

The Wan Go SDK is the language-specific package for Wan on RunAPI. Use this package for video generation, animation, and video editing workflows when your application needs request bodies, task status lookup, and consistent RunAPI errors in Go.

This README is the Go package guide inside the public `wan-sdk` repository. For the repository overview, start at `../README.md`; for model details, use https://runapi.ai/models/wan; for API reference, use https://runapi.ai/docs#wan; for SDK docs, use https://runapi.ai/docs#sdk-wan.

## Install

```bash
go get github.com/runapi-ai/wan-sdk/go@latest
```

## Quick start

```go
import (
  "context"

  "github.com/runapi-ai/wan-sdk/go/wan"
)

client, err := wan.NewClient()
task, err := client.TextToVideo.Create(context.Background(), wan.TextToVideoParams{
  // Pass the Wan JSON request body from https://runapi.ai/docs#wan.
})
status, err := client.TextToVideo.Get(context.Background(), task.ID)
```

Use `create` when you want to submit a task and return quickly, `get` when you need the latest task state, and `run` when a script should create and poll until completion. In web request handlers, prefer `create` plus webhook or later `get` polling so a worker is not held open.

RunAPI-generated file URLs are temporary. Download and store generated images, videos, audio, or other files in your own durable storage within 7 days; do not treat returned URLs as long-term assets.

## Language notes

Use the public Go module with `github.com/runapi-ai/core-sdk/go` options when building video services, CLIs, or workers. The available resources are `TextToVideo`, `ImageToVideo`, `SpeechToVideo`, `Animate`, `TextToImage`, and `EditVideo`. Keep `RUNAPI_API_KEY` in the environment or your secret manager; never commit API keys or callback secrets.

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
