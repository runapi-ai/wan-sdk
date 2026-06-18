# frozen_string_literal: true

module RunApi
  module Wan
    # Wan video and image generation API client.
    #
    # Spans multiple generation families (2.2 through 2.7) with progressive
    # capability upgrades. Feature availability varies by model variant.
    #
    # @example
    #   client = RunApi::Wan::Client.new(api_key: "your-api-key")
    #   result = client.text_to_video.run(
    #     model: "wan-2.6-text-to-video",
    #     prompt: "A scenic mountain landscape with flowing rivers"
    #   )
    class Client < RunApi::Core::Client
      # @return [Resources::TextToVideo] Generate videos from text prompts. Supports turbo (2.2) through 2.7 with progressive features.
      attr_reader :text_to_video
      # @return [Resources::ImageToVideo] Generate videos driven by a source image. Flash variants trade fidelity for speed.
      attr_reader :image_to_video
      # @return [Resources::SpeechToVideo] Generate lip-synced talking-head videos from a portrait image and speech audio.
      attr_reader :speech_to_video
      # @return [Resources::Animate] Transfer motion from a reference video onto a subject image (move or replace).
      attr_reader :animate
      # @return [Resources::TextToImage] Generate images with optional color palette, bounding box, and thinking mode.
      attr_reader :text_to_image
      # @return [Resources::EditVideo] Modify existing videos guided by text prompts and optional reference images.
      attr_reader :edit_video

      def initialize(api_key: nil, **options)
        super

        @text_to_video = Resources::TextToVideo.new(http)
        @image_to_video = Resources::ImageToVideo.new(http)
        @speech_to_video = Resources::SpeechToVideo.new(http)
        @animate = Resources::Animate.new(http)
        @text_to_image = Resources::TextToImage.new(http)
        @edit_video = Resources::EditVideo.new(http)
      end
    end
  end
end
