# frozen_string_literal: true

module RunApi
  module Wan
    module Resources
      # Generates videos driven by a source image. Flash variants trade fidelity
      # for speed; 2.7 adds last-frame control, video continuation, and audio features.
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
        def run(**params)
          task = create(**params)
          poll_until_complete { get(task.id) }
        end

        # Create an image-to-video generation task.
        #
        # @param params [Hash] image-to-video parameters
        # @return [RunApi::Wan::Types::VideoTaskResponse] task creation result with id
        def create(**params)
          params = compact_params(params)
          validate_params!(params)
          request(:post, ENDPOINT, body: params)
        end

        # Get image-to-video status by task ID.
        #
        # @param id [String] task ID
        # @return [RunApi::Wan::Types::VideoTaskResponse] current task status
        def get(id)
          request(:get, "#{ENDPOINT}/#{id}")
        end

        private

        def validate_params!(params)
          validate_contract!(CONTRACT["image-to-video"], params)
        end
      end
    end
  end
end
