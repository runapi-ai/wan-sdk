# frozen_string_literal: true

module RunApi
  module Wan
    module Resources
      # Transfers motion from a reference video onto a subject in the source image.
      # Use wan-2.2-animate-move to preserve the subject and animate its motion,
      # or wan-2.2-animate-replace to swap the subject with the reference video's subject.
      class Animate
        include RunApi::Core::ResourceHelpers

        ENDPOINT = "/api/v1/wan/animate"
        RESPONSE_CLASS = Types::VideoTaskResponse
        COMPLETED_RESPONSE_CLASS = Types::CompletedVideoTaskResponse

        def initialize(http)
          @http = http
        end

        # Transfer motion and wait until complete.
        #
        # @param params [Hash] animation parameters
        # @return [RunApi::Wan::Types::CompletedVideoTaskResponse] completed animation result
        def run(**params)
          task = create(**params)
          poll_until_complete { get(task.id) }
        end

        # Create an animation task.
        #
        # @param params [Hash] animation parameters
        # @return [RunApi::Wan::Types::VideoTaskResponse] task creation result with id
        def create(**params)
          params = compact_params(params)
          validate_params!(params)
          request(:post, ENDPOINT, body: params)
        end

        # Get animation status by task ID.
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
          raise Core::ValidationError, "reference_video_url is required" unless param(params, :reference_video_url)

          model = param(params, :model)
          unless Types::ANIMATE_MODELS.include?(model)
            raise Core::ValidationError, "Invalid model: #{model}. Must be one of: #{Types::ANIMATE_MODELS.join(", ")}"
          end
        end
      end
    end
  end
end
