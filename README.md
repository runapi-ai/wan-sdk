<p align="center">
  <a href="https://runapi.ai"><img src="https://runapi.ai/icon.svg" height="56" alt="RunAPI"></a>
</p>

<h3 align="center">
  <a href="https://github.com/runapi-ai/wan-sdk">Wan API SDK for RunAPI</a>
</h3>

<p align="center">
  Wan API SDKs for JavaScript, Python, Ruby, Go, Java, and PHP on RunAPI.
</p>

<div align="center">

[![npm](https://img.shields.io/npm/v/@runapi.ai/wan)](https://www.npmjs.com/package/@runapi.ai/wan)
[![PyPI](https://img.shields.io/pypi/v/runapi-wan)](https://pypi.org/project/runapi-wan/)
[![RubyGems](https://img.shields.io/gem/v/runapi-wan)](https://rubygems.org/gems/runapi-wan)
[![Go Reference](https://pkg.go.dev/badge/github.com/runapi-ai/wan-sdk/go.svg)](https://pkg.go.dev/github.com/runapi-ai/wan-sdk/go)
[![Maven Central](https://img.shields.io/maven-central/v/ai.runapi/runapi-wan)](https://central.sonatype.com/artifact/ai.runapi/runapi-wan)
[![License](https://img.shields.io/github/license/runapi-ai/wan-sdk)](https://github.com/runapi-ai/wan-sdk/blob/main/LICENSE)

</div>
<br/>

The Wan API SDK packages JavaScript, Python, Ruby, Go, Java, and PHP clients for Wan on RunAPI. Use it for text-to-video, image-to-video, speech-to-video, animation, text-to-image, and edit-video workflows when your app needs typed request builders, predictable task polling, file upload helpers, account helpers, and consistent RunAPI errors.

Wan is listed in the RunAPI model catalog at https://runapi.ai/models/wan. Variant pages below carry pricing, rate-limit, and commercial-usage details. The public `wan-sdk` repository groups the non-PHP language packages, examples, CI, and release tags for this model. The PHP package is released from a split Composer repository.

## Install

```bash
npm install @runapi.ai/wan
pip install runapi-wan
gem install runapi-wan
go get github.com/runapi-ai/wan-sdk/go@latest
```

Gradle:

```kotlin
dependencies {
  implementation("ai.runapi:runapi-wan:0.1.2")
}
```

Maven:

```xml
<dependency>
  <groupId>ai.runapi</groupId>
  <artifactId>runapi-wan</artifactId>
  <version>0.1.2</version>
</dependency>
```

Use the Java BOM when installing multiple RunAPI Java modules:

```kotlin
dependencies {
  implementation(platform("ai.runapi:runapi-bom:0.2.8"))
  implementation("ai.runapi:runapi-wan")
}
```

The PHP package is published from the split Composer repository as `runapi-ai/wan`; see https://github.com/runapi-ai/wan-php for PHP install and examples.

## What you can build

- Build apps, agent workflows, batch jobs, and production services around Wan requests.
- Install only the language package your app needs while keeping one model-specific repository for docs and releases.
- Use `create` for submit-only jobs, `get` for status lookup, and `run` for submit-and-poll scripts.
- Upload local files, URL files, or base64 files through shared RunAPI file helpers.
- Handle validation, authentication, rate limits, insufficient credits, task failures, and polling timeouts through RunAPI SDK errors.

## Java quick start

```java
import ai.runapi.wan.WanClient;
import ai.runapi.wan.types.TextToVideoParams;
import ai.runapi.wan.types.CompletedTextToVideoResponse;
import ai.runapi.wan.types.TextToVideoModel;

WanClient client = WanClient.builder()
    .apiKey(System.getenv("RUNAPI_API_KEY"))
    .build();

CompletedTextToVideoResponse result = client.textToVideo().run(
    TextToVideoParams.builder()
        .model(TextToVideoModel.WAN_2_6_TEXT_TO_VIDEO)
        .prompt("A scenic mountain landscape with flowing rivers")
        .outputResolution("1080p")
        .aspectRatio("16:9")
        .durationSeconds(5)
        .build()
);
```

Java packages target Java 8 bytecode and are tested on Java 8, 11, 17, and 21. Each model artifact depends on `ai.runapi:runapi-core`, so application code normally installs only `ai.runapi:runapi-wan`.

## Task lifecycle

Most media endpoints are asynchronous. `create()` submits a task and returns its id, `get(id)` fetches the latest task state, and `run(params)` creates the task and polls until it reaches a terminal state. In web request handlers, prefer `create()` plus webhook or later `get()` polling so the server does not hold a worker open.

## Repository layout

- `js/` publishes `@runapi.ai/wan`.
- `python/` publishes `runapi-wan`.
- `ruby/` publishes `runapi-wan`.
- `go/` publishes `github.com/runapi-ai/wan-sdk/go`.
- `java/` publishes `ai.runapi:runapi-wan` and uses `ai.runapi:runapi-core`.

## Public links

- Model page: https://runapi.ai/models/wan
- SDK docs: https://runapi.ai/docs/resources/sdks
- Product docs: https://runapi.ai/docs/api/wan/text-to-video
- SDK repository: https://github.com/runapi-ai/wan-sdk
- PHP package repository: https://github.com/runapi-ai/wan-php
- Skill repository: https://github.com/runapi-ai/wan
- Provider comparison: https://runapi.ai/providers/alibaba
- Full catalog: https://runapi.ai/models

## Pricing and variants

Use the most specific Wan variant page for pricing, rate limits, and commercial usage:
- [2.2 A14B text to video turbo](https://runapi.ai/models/wan/2.2-a14b-text-to-video-turbo)
- [2.2 A14B image to video turbo](https://runapi.ai/models/wan/2.2-a14b-image-to-video-turbo)
- [2.2 A14B speech to video turbo](https://runapi.ai/models/wan/2.2-a14b-speech-to-video-turbo)
- [2.2 animate move](https://runapi.ai/models/wan/2.2-animate-move)
- [2.2 animate replace](https://runapi.ai/models/wan/2.2-animate-replace)
- [2.5 text to video](https://runapi.ai/models/wan/2.5-text-to-video)
- [2.5 image to video](https://runapi.ai/models/wan/2.5-image-to-video)
- [2.6 text to video](https://runapi.ai/models/wan/2.6-text-to-video)
- [2.6 image to video](https://runapi.ai/models/wan/2.6-image-to-video)
- [2.6 edit video](https://runapi.ai/models/wan/2.6-edit-video)
- [2.6 flash image to video](https://runapi.ai/models/wan/2.6-flash-image-to-video)
- [2.6 flash edit video](https://runapi.ai/models/wan/2.6-flash-edit-video)
- [2.7 text to video](https://runapi.ai/models/wan/2.7-text-to-video)
- [2.7 image to video](https://runapi.ai/models/wan/2.7-image-to-video)
- [2.7 image](https://runapi.ai/models/wan/2.7-image)
- [2.7 image pro](https://runapi.ai/models/wan/2.7-image-pro)
- [2.7 R2V text to video](https://runapi.ai/models/wan/2.7-r2v)
- [2.7 video edit](https://runapi.ai/models/wan/2.7-edit-video)

Default pricing link for the Wan SDK: https://runapi.ai/models/wan/2.2-a14b-text-to-video-turbo

## File storage

RunAPI-generated file URLs are temporary. Download and store generated images, videos, audio, or other files in your own durable storage within 7 days; do not treat returned URLs as long-term assets.

## FAQ

### Which package should I install for Wan work?

Install the model package for your language: `@runapi.ai/wan` on npm, `runapi-wan` on PyPI, `runapi-wan` on RubyGems, `github.com/runapi-ai/wan-sdk/go`, `ai.runapi:runapi-wan` on Maven Central, or `runapi-ai/wan` on Packagist. Install core SDK packages only when you are building shared SDK infrastructure.

### Where should public links point?

Primary Wan links point to https://runapi.ai/models/wan. Pricing and usage-policy links point to variant pages such as https://runapi.ai/models/wan/2.2-a14b-text-to-video-turbo. Provider comparisons point to https://runapi.ai/providers/alibaba, and broad browsing points to https://runapi.ai/models.

## License

Licensed under the Apache License, Version 2.0.
