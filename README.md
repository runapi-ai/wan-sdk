<p align="center">
  <a href="https://runapi.ai"><img src="https://runapi.ai/icon.svg" height="56" alt="RunAPI"></a>
</p>

<h3 align="center">
  <a href="https://github.com/runapi-ai/wan-sdk">Wan API SDK for RunAPI</a>
</h3>

<p align="center">
  Wan API SDKs for JavaScript, Ruby, and Go on RunAPI.
</p>

<div align="center">

[![npm](https://img.shields.io/npm/v/@runapi.ai/wan)](https://www.npmjs.com/package/@runapi.ai/wan)
[![RubyGems](https://img.shields.io/gem/v/runapi-wan)](https://rubygems.org/gems/runapi-wan)
[![Go Reference](https://pkg.go.dev/badge/github.com/runapi-ai/wan-sdk/go.svg)](https://pkg.go.dev/github.com/runapi-ai/wan-sdk/go)
[![License](https://img.shields.io/github/license/runapi-ai/wan-sdk)](https://github.com/runapi-ai/wan-sdk/blob/main/LICENSE)

</div>
<br/>

The wan video api SDK packages JavaScript, Ruby, and Go clients for Wan on RunAPI. Use this wan video api SDK for text-to-video, image-to-video, video-to-video, animation, and editing workflows that need typed installs, JSON request bodies, task polling, and consistent RunAPI errors across services.

Wan belongs to the Alibaba catalog on RunAPI. The public model page is https://runapi.ai/models/wan; variant pages below carry pricing, rate-limit, and commercial-usage details. The public `wan-sdk` repository groups the JavaScript, Ruby, and Go packages for this model.

## Install

```bash
npm install @runapi.ai/wan
gem install runapi-wan
go get github.com/runapi-ai/wan-sdk/go@latest
```

## What you can build

- Build marketing clips, storyboard previews, creator tools, and agent video pipelines with the wan video api SDK.
- Keep one model-specific repository while installing only the language package your app needs.
- Use `create` for submit-only jobs, `get` for status lookup, and `run` for submit-and-poll scripts.
- Handle authentication, validation, rate limits, insufficient credits, task failures, and polling timeouts through RunAPI SDK errors.

The JavaScript client exposes text to videos, image to videos, video to videos, speech to videos, animations, images, reference to videos, video edits resources, and the Ruby and Go packages mirror the same RunAPI task lifecycle.

## JavaScript quick start

```typescript
import { WanClient } from '@runapi.ai/wan';

const client = new WanClient();

const task = await client.textToVideo.create({
  // Pass the Wan request body documented at https://runapi.ai/docs#wan.
});

const status = await client.textToVideo.get(task.id);
```

For short scripts, use `run` with the same JSON body to create the task and wait for completion. For web request handlers, prefer `create` plus webhook or later `get` polling so the server does not hold a worker open.

## Repository layout

- `js/` publishes `@runapi.ai/wan`.
- `ruby/` publishes `runapi-wan` when RubyGems publishing resumes.
- `go/` publishes `github.com/runapi-ai/wan-sdk/go` and depends on `github.com/runapi-ai/core-sdk/go`.

## Public links

- Model page: https://runapi.ai/models/wan
- SDK docs: https://runapi.ai/docs#sdk-wan
- Product docs: https://runapi.ai/docs#wan
- SDK repository: https://github.com/runapi-ai/wan-sdk
- Skill repository: https://github.com/runapi-ai/wan
- Provider comparison: https://runapi.ai/providers/alibaba
- Full catalog: https://runapi.ai/models

## Pricing and variants

Use the most specific wan video api variant page for pricing, rate limits, and commercial usage:
- [2.2 A14B text to video turbo](https://runapi.ai/models/wan/2.2-a14b-text-to-video-turbo)
- [2.2 A14B image to video turbo](https://runapi.ai/models/wan/2.2-a14b-image-to-video-turbo)
- [2.2 A14B speech to video turbo](https://runapi.ai/models/wan/2.2-a14b-speech-to-video-turbo)
- [2.2 animate move](https://runapi.ai/models/wan/2.2-animate-move)
- [2.2 animate replace](https://runapi.ai/models/wan/2.2-animate-replace)
- [2.5 text to video](https://runapi.ai/models/wan/2.5-text-to-video)
- [2.5 image to video](https://runapi.ai/models/wan/2.5-image-to-video)
- [2.6 text to video](https://runapi.ai/models/wan/2.6-text-to-video)
- [2.6 image to video](https://runapi.ai/models/wan/2.6-image-to-video)
- [2.6 video to video](https://runapi.ai/models/wan/2.6-video-to-video)
- [2.6 flash image to video](https://runapi.ai/models/wan/2.6-flash-image-to-video)
- [2.6 flash video to video](https://runapi.ai/models/wan/2.6-flash-video-to-video)
- [2.7 text to video](https://runapi.ai/models/wan/2.7-text-to-video)
- [2.7 image to video](https://runapi.ai/models/wan/2.7-image-to-video)
- [2.7 image](https://runapi.ai/models/wan/2.7-image)
- [2.7 image pro](https://runapi.ai/models/wan/2.7-image-pro)
- [2.7 reference to video](https://runapi.ai/models/wan/2.7-r2v)
- [2.7 video edit](https://runapi.ai/models/wan/2.7-videoedit)

Default pricing link for the wan video api SDK: https://runapi.ai/models/wan/2.2-a14b-text-to-video-turbo

## FAQ

### Which package should I install for wan video api work?

Install the model package for your language: `@runapi.ai/wan`, `runapi-wan`, or `github.com/runapi-ai/wan-sdk/go`. Install core SDK packages only when you are building shared SDK infrastructure.

### Where should public links point?

Primary wan video api links point to https://runapi.ai/models/wan. Pricing and usage-policy links point to variant pages such as https://runapi.ai/models/wan/2.2-a14b-text-to-video-turbo. Provider comparisons point to https://runapi.ai/providers/alibaba, and broad browsing points to https://runapi.ai/models.

## License

Licensed under the Apache License, Version 2.0.
