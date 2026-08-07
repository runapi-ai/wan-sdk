# frozen_string_literal: true

module RunApi
  module Wan
    module Resources
      # Generates videos driven by a source image. Flash variants trade fidelity
      # for speed; 2.7 adds last-frame control, video continuation, and audio features.
      # On WAN 2.6, multi_shots controls whether the generated video uses multiple
      # shots with transitions instead of one continuous shot.
      class ImageToVideo
        include RunApi::Core::ResourceHelpers

        ENDPOINT = "/api/v1/wan/image_to_video"
        RESPONSE_CLASS = Types::VideoTaskResponse
        COMPLETED_RESPONSE_CLASS = Types::CompletedVideoTaskResponse

        def initialize(http)
          @http = http
        end

        # Generate a video from an image and wait until complete.
        #
        # @param params [Hash] image-to-video parameters
        # @return [RunApi::Wan::Types::CompletedVideoTaskResponse] completed video generation
        def run(options: nil, **params)
          task = create(options: options, **params)
          poll_until_complete { get(task.id, options: options) }
        end

        # Create an image-to-video generation task.
        #
        # @param params [Hash] image-to-video parameters
        # @return [RunApi::Wan::Types::VideoTaskResponse] task creation result with id
        def create(options: nil, **params)
          params = compact_params(params)
          validate_params!(params)
          request(:post, ENDPOINT, body: params, options: options)
        end

        # Get image-to-video status by task ID.
        #
        # @param id [String] task ID
        # @return [RunApi::Wan::Types::VideoTaskResponse] current task status
        def get(id, options: nil)
          request(:get, "#{ENDPOINT}/#{id}", options: options)
        end

        private

        def validate_params!(params)
          validate_contract!(CONTRACT["image-to-video"], params)
        end
      end
    end
  end
end
