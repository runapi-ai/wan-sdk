# frozen_string_literal: true

module RunApi
  module Wan
    module Resources
      class SpeechToVideo
        include RunApi::Core::ResourceHelpers

        ENDPOINT = "/api/v1/wan/speech_to_video"
        RESPONSE_CLASS = Types::VideoTaskResponse
        COMPLETED_RESPONSE_CLASS = Types::CompletedVideoTaskResponse

        def initialize(http)
          @http = http
        end

        def run(**params)
          task = create(**params)
          poll_until_complete { get(task.id) }
        end

        def create(**params)
          params = compact_params(params)
          validate_params!(params)
          request(:post, ENDPOINT, body: params)
        end

        def get(id)
          request(:get, "#{ENDPOINT}/#{id}")
        end

        private

        def validate_params!(params)
          raise Core::ValidationError, "model is required" unless param(params, :model)
          raise Core::ValidationError, "image_url is required" unless param(params, :image_url)
          raise Core::ValidationError, "audio_url is required" unless param(params, :audio_url)

          model = param(params, :model)
          unless Types::SPEECH_TO_VIDEO_MODELS.include?(model)
            raise Core::ValidationError, "Invalid model: #{model}. Must be one of: #{Types::SPEECH_TO_VIDEO_MODELS.join(", ")}"
          end
        end
      end
    end
  end
end
