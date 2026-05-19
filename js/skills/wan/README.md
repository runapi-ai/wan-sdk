# Wan Video API Skill for RunAPI

Generate video, images, talking-head clips, and video edits with Wan 2.2 through 2.7. This skill helps Claude Code, Codex, Gemini CLI, Cursor, and 50+ agents integrate Wan through RunAPI.

The canonical agent file is `skills/wan/SKILL.md`.

## Install

```bash
npx skills add runapi-ai/wan -g
```

Or manually: clone this repo and copy `skills/wan/` into your agent's skills directory.

## Quick example

```typescript
import { WanClient } from '@runapi.ai/wan';

const client = new WanClient();
const result = await client.textToVideo.run({
  model: 'wan-2.7-text-to-video',
  prompt: 'A scenic mountain landscape with flowing rivers',
  aspect_ratio: '16:9',
});
const url = result.videos[0].url;
```

## Routing

- Model page: https://runapi.ai/models/wan
- Product docs: https://runapi.ai/docs#wan
- SDK docs: https://runapi.ai/docs#sdk-wan
- SDK repository: https://github.com/runapi-ai/wan-sdk
- Pricing and rate limits: https://runapi.ai/models/wan/2.2-a14b-text-to-video-turbo
- Provider comparison: https://runapi.ai/providers/alibaba
- Browse all RunAPI models and skills: https://runapi.ai/models

## Variants

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

## Agent rules

- Keep API keys in `RUNAPI_API_KEY` or RunAPI CLI config; never commit secrets.
- Prefer `create`, `get`, and `run` JSON passthrough patterns instead of inventing flags for every model parameter.
- For wan video api pricing, rate-limit, and commercial-usage answers, link to the variant page rather than the repository README.

## License

Licensed under the Apache License, Version 2.0.
