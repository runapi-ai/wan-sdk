# frozen_string_literal: true

module RunApi
  module Wan
    module Resources
      # Generates videos from text prompts. Supports turbo (2.2) through 2.7
      # with progressive feature upgrades including negative prompts, watermark
      # control, background audio, and R2V multi-reference inputs.
      class TextToVideo
        include RunApi::Core::ResourceHelpers

        ENDPOINT = "/api/v1/wan/text_to_video"
        RESPONSE_CLASS = Types::VideoTaskResponse
        COMPLETED_RESPONSE_CLASS = Types::CompletedVideoTaskResponse

        def initialize(http)
          @http = http
        end

        # Generate a video and wait until complete.
        #
        # @param params [Hash] text-to-video parameters
        # @return [RunApi::Wan::Types::CompletedVideoTaskResponse] completed video generation
        def run(**params)
          task = create(**params)
          poll_until_complete { get(task.id) }
        end

        # Create a text-to-video generation task.
        #
        # @param params [Hash] text-to-video parameters
        # @return [RunApi::Wan::Types::VideoTaskResponse] task creation result with id
        def create(**params)
          params = compact_params(params)
          validate_params!(params)
          request(:post, ENDPOINT, body: params)
        end

        # Get text-to-video status by task ID.
        #
        # @param id [String] task ID
        # @return [RunApi::Wan::Types::VideoTaskResponse] current task status
        def get(id)
          request(:get, "#{ENDPOINT}/#{id}")
        end

        private

        def validate_params!(params)
          raise Core::ValidationError, "model is required" unless param(params, :model)
          raise Core::ValidationError, "prompt is required" unless param(params, :prompt)

          model = param(params, :model)
          unless Types::TEXT_TO_VIDEO_MODELS.include?(model)
            raise Core::ValidationError, "Invalid model: #{model}. Must be one of: #{Types::TEXT_TO_VIDEO_MODELS.join(", ")}"
          end
        end
      end
    end
  end
end
