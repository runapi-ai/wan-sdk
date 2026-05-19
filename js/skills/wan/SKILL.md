---
name: wan
description: Generate videos and images (Wan 2.2 / 2.5 / 2.6 / 2.7 text-to-video, image-to-video, video-to-video, speech-to-video, animate, text-to-image, reference-to-video, edit-video) through RunAPI.ai using the @runapi.ai/wan Node/TypeScript SDK. Use when the user asks to add Wan video or text-to-image, or writes against @runapi.ai/wan. Triggers on "wan", "通义万相", "video generation", "生成视频", "@runapi.ai/wan".
documentation: https://runapi.ai/models/wan
provider_page: https://runapi.ai/providers/alibaba
catalog: https://runapi.ai/models
---

# @runapi.ai/wan — RunAPI.ai Wan video and text-to-image

Build Node / TypeScript integrations that generate Wan video, images, talking-head clips, and edits through RunAPI.ai.

## Setup

Requires **Node 18+** (global `fetch`).

```bash
npm install @runapi.ai/wan
```

Set your API key in the environment:

```dotenv
# .env
RUNAPI_API_KEY=runapi_xxx   # get one at https://runapi.ai/settings/api_keys
```

```ts
import { WanClient } from '@runapi.ai/wan';

// The SDK reads RUNAPI_API_KEY from the environment automatically.
const client = new WanClient();
```

Pass `{ apiKey }` explicitly if you manage secrets differently. `baseUrl` defaults to `https://runapi.ai`; override only for local development.

## Core recipe — text to video

```ts
const result = await client.textToVideo.run({
  model: 'wan-2-6-text-to-video',
  prompt: 'A scenic mountain landscape with flowing rivers',
  aspect_ratio: '16:9',
  resolution: '720p',
  duration: '5',
});

const url = result.videos[0].url;
```

`run()` creates the task, auto-polls, and resolves only when the task completes — `videos[0].url` is guaranteed on the resolved value. On failure it throws `TaskFailedError`; on polling timeout it throws `TaskTimeoutError`. Use `run()` for scripts and short-lived processes. For request handlers, split it:

```ts
const { id } = await client.textToVideo.create({ model: 'wan-2-6-text-to-video', prompt: '...' });
// return 202 immediately; fetch later:
const status = await client.textToVideo.get(id);
if (status.status === 'completed') { /* ... */ }
```

Do not hold a web worker open waiting on `run()`. Split + webhook is the production pattern.

`run()` polls every 2 s for up to 15 min by default. Tune when needed:

```ts
await client.textToVideo.run(params, { maxWaitMs: 30 * 60_000, pollIntervalMs: 5_000 });
```

If `TaskTimeoutError` fires, the task is still running server-side — resume with `<resource>.get(id)` or finish via webhook.

## Resources

`WanClient` exposes eight resources; each has `create / get / run`:

| Resource | Purpose | Example `model` |
|---|---|---|
| `textToVideo` | Text-to-video | `wan-2-7-text-to-video` |
| `imageToVideo` | Animate a still image | `wan-2-7-image-to-video`, `wan-2-6-flash-image-to-video` |
| `videoToVideo` | Transform an existing video | `wan-2-6-video-to-video` |
| `speechToVideo` | Talking-head driven by audio | `wan-2-2-a14b-speech-to-video-turbo` |
| `animate` | Animate-move / animate-replace on a video using an image | `wan-2-2-animate-move`, `wan-2-2-animate-replace` |
| `textToImage` | Text-to-image | `wan-2-7-image`, `wan-2-7-image-pro` |
| `referenceToVideo` | Multi-reference-driven video | `wan-2-7-r2v` |
| `editVideo` | Prompt-driven edits of a source video | `wan-2-7-videoedit` |

### Image-to-video

```ts
await client.imageToVideo.run({
  model: 'wan-2-7-image-to-video',
  image_url: 'https://cdn.example.com/scene.jpg',
  prompt: 'The camera slowly pulls back',
  resolution: '720p',
  duration: '5',
  audio: true,
});
```

### Speech-to-video (talking-head)

```ts
await client.speechToVideo.run({
  model: 'wan-2-2-a14b-speech-to-video-turbo',
  image_url: 'https://cdn.example.com/headshot.jpg',
  audio_url: 'https://cdn.example.com/voiceover.mp3',
  prompt: 'Warm lighting, natural expression',
});
```

### Image generation

Returns `images` (not `videos`):

```ts
const img = await client.textToImage.run({
  model: 'wan-2-7-image-pro',
  prompt: 'A product shot of a vintage camera on a desk',
  aspect_ratio: '1:1',
  resolution: '2K',
  n: 4, // request up to N outputs
});

console.log(img.images[0].url);
```

### Reference-to-video

Drive with any combination of first frame, reference images, reference videos, and reference voice:

```ts
await client.referenceToVideo.run({
  model: 'wan-2-7-r2v',
  prompt: 'A consistent character walking through a neon-lit city',
  first_frame: 'https://cdn.example.com/start.jpg',
  reference_image: ['https://cdn.example.com/char.png'],
  reference_voice: 'https://cdn.example.com/voice.mp3',
  aspect_ratio: '16:9',
  duration: '10',
});
```

### Video edits

```ts
await client.editVideo.run({
  model: 'wan-2-7-videoedit',
  video_url: 'https://cdn.example.com/source.mp4',
  prompt: 'Change the time of day to sunset',
  reference_image: 'https://cdn.example.com/mood.jpg',
});
```

## Models

All model values live in the `WanTextToVideoModel`, `WanImageToVideoModel`, etc. type unions — see `dist/types.d.ts`. Exact credit costs per model are shown at https://runapi.ai/pricing and in the dashboard — do not hardcode prices in application code.

## Callbacks (webhooks)

Pass `callback_url` on `create()` (or any `run()` call) and RunAPI will POST the final payload to you:

```ts
await client.textToVideo.create({
  model: 'wan-2-6-text-to-video',
  prompt: '...',
  callback_url: 'https://your.app/webhooks/runapi/wan',
});
```

Payload shape (video resources):

```ts
{ id: string; status: 'completed' | 'failed'; videos?: { url: string }[]; error?: string }
```

For `textToImage`, the payload carries `images: { url: string }[]` instead.

**Always verify the signature before trusting the body.** RunAPI signs every callback with your account's Callback Secret (rotate at `/accounts/callback_secret`). Headers:

- `X-Callback-Id` — UUID, store to make handler idempotent
- `X-Callback-Timestamp` — unix seconds, reject if `|now - ts| > 300`
- `X-Callback-Signature` — base64 HMAC-SHA256 over `` `${id}.${ts}.${rawBody}` `` using the base64-decoded secret

```ts
import crypto from 'node:crypto';

function verify(raw: string, id: string, ts: string, sig: string, secret: string) {
  const key = Buffer.from(secret, 'base64');
  const mac = crypto.createHmac('sha256', key)
    .update(`${id}.${ts}.${raw}`)
    .digest('base64');
  return crypto.timingSafeEqual(Buffer.from(mac), Buffer.from(sig));
}
```

Reply `2xx` within 10s; any non-2xx triggers retries.

## Errors

All errors are re-exported from `@runapi.ai/core`. Always `instanceof` — never string-match messages.

| Error | Status | Action |
|---|---|---|
| `AuthenticationError` | 401 | abort; surface "reconnect your API key" |
| `InsufficientCreditsError` | 402 | prompt user to top up at runapi.ai/billing |
| `ValidationError` | 400 / 422 | fix params; do not retry |
| `RateLimitError` | 429 | sleep `err.retryAfterMs`, then retry |
| `ServiceUnavailableError` | 503 / 455 | retry with backoff; transient service issue |
| `TaskFailedError` | — | show `err.details` to user; do not auto-retry |
| `TaskTimeoutError` | — | re-poll with `<resource>.get(id)` |

```ts
import { InsufficientCreditsError, TaskFailedError } from '@runapi.ai/wan';

try {
  await client.textToVideo.run({ model: 'wan-2-6-text-to-video', prompt: '...' });
} catch (err) {
  if (err instanceof InsufficientCreditsError) { /* surface top-up CTA */ }
  else if (err instanceof TaskFailedError)       { /* show err.details */ }
  else throw err;
}
```

## Gotchas

- `model` is required on every call; every resource has its own allowed model list.
- `imageToVideo` accepts either `image_url` (single) or `image_urls` (multi-frame, e.g. first/last); do not send both.
- `duration` is a string (e.g. `'5'`, `'10'`), matching the wire format.
- `animate` needs both `video_url` (driving motion) and `image_url` (subject).
- `textToImage.run()` returns `images` — index `result.images[0].url`. Every other resource returns `videos`.
- `resolution` values differ by resource (e.g. `'720p'` for video, `'1K' / '2K'` for images). Follow the types.
- `callback_url` must be reachable from the public internet. `localhost` / `127.0.0.1` URLs will never fire — use a tunnel (cloudflared, ngrok, tailscale funnel) when developing locally.

## Dig deeper

Package README (full API surface, all params): `node_modules/@runapi.ai/wan/README.md`. Types: `@runapi.ai/wan/dist/types.d.ts`. Product docs: https://runapi.ai/docs.
