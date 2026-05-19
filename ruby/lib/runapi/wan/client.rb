# frozen_string_literal: true

module RunApi
  module Wan
    class Client
      attr_reader :text_to_video, :image_to_video, :video_to_video,
        :speech_to_video, :animate, :text_to_image, :reference_to_video, :edit_video

      def initialize(api_key: nil, **options)
        @api_key = Core::Auth.resolve_api_key(api_key)

        client_options = Core::ClientOptions.new(api_key: @api_key, **options)
        http = client_options.http_client || Core::HttpClient.new(client_options)

        @text_to_video = Resources::TextToVideo.new(http)
        @image_to_video = Resources::ImageToVideo.new(http)
        @video_to_video = Resources::VideoToVideo.new(http)
        @speech_to_video = Resources::SpeechToVideo.new(http)
        @animate = Resources::Animate.new(http)
        @text_to_image = Resources::TextToImage.new(http)
        @reference_to_video = Resources::ReferenceToVideo.new(http)
        @edit_video = Resources::EditVideo.new(http)
      end
    end
  end
end
