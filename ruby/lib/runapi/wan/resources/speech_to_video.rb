# frozen_string_literal: true

module RunApi
  module Wan
    module Resources
      # Generates lip-synced talking-head videos from a portrait image and
      # driving speech audio. Both source_image_url and source_audio_url are required.
      class SpeechToVideo
        include RunApi::Core::ResourceHelpers

        ENDPOINT = "/api/v1/wan/speech_to_video"
        RESPONSE_CLASS = Types::VideoTaskResponse
        COMPLETED_RESPONSE_CLASS = Types::CompletedVideoTaskResponse

        def initialize(http)
          @http = http
        end

        # Generate a lip-synced video and wait until complete.
        #
        # @param params [Hash] speech-to-video parameters
        # @return [RunApi::Wan::Types::CompletedVideoTaskResponse] completed video generation
        def run(**params)
          task = create(**params)
          poll_until_complete { get(task.id) }
        end

        # Create a speech-to-video generation task.
        #
        # @param params [Hash] speech-to-video parameters
        # @return [RunApi::Wan::Types::VideoTaskResponse] task creation result with id
        def create(**params)
          params = compact_params(params)
          validate_params!(params)
          request(:post, ENDPOINT, body: params)
        end

        # Get speech-to-video status by task ID.
        #
        # @param id [String] task ID
        # @return [RunApi::Wan::Types::VideoTaskResponse] current task status
        def get(id)
          request(:get, "#{ENDPOINT}/#{id}")
        end

        private

        def validate_params!(params)
          raise Core::ValidationError, "model is required" unless param(params, :model)
          raise Core::ValidationError, "source_image_url is required" unless param(params, :source_image_url)
          raise Core::ValidationError, "source_audio_url is required" unless param(params, :source_audio_url)

          model = param(params, :model)
          unless Types::SPEECH_TO_VIDEO_MODELS.include?(model)
            raise Core::ValidationError, "Invalid model: #{model}. Must be one of: #{Types::SPEECH_TO_VIDEO_MODELS.join(", ")}"
          end
        end
      end
    end
  end
end
