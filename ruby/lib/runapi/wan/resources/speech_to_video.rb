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
        def run(options: nil, **params)
          task = create(options: options, **params)
          poll_until_complete { get(task.id, options: options) }
        end

        # Create a speech-to-video generation task.
        #
        # @param params [Hash] speech-to-video parameters
        # @return [RunApi::Wan::Types::VideoTaskResponse] task creation result with id
        def create(options: nil, **params)
          params = compact_params(params)
          validate_params!(params)
          request(:post, ENDPOINT, body: params, options: options)
        end

        # Get speech-to-video status by task ID.
        #
        # @param id [String] task ID
        # @return [RunApi::Wan::Types::VideoTaskResponse] current task status
        def get(id, options: nil)
          request(:get, "#{ENDPOINT}/#{id}", options: options)
        end

        private

        def validate_params!(params)
          validate_contract!(CONTRACT["speech-to-video"], params)
        end
      end
    end
  end
end
