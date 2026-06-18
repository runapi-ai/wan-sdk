# frozen_string_literal: true

module RunApi
  module Wan
    module Resources
      # Generates images from text prompts with optional color palette and bounding
      # box constraints. Supports batch generation via output_count. Pro model
      # supports thinking_mode for enhanced prompt reasoning.
      class TextToImage
        include RunApi::Core::ResourceHelpers

        ENDPOINT = "/api/v1/wan/text_to_image"
        RESPONSE_CLASS = Types::ImageTaskResponse
        COMPLETED_RESPONSE_CLASS = Types::CompletedImageTaskResponse

        def initialize(http)
          @http = http
        end

        # Generate an image and wait until complete.
        #
        # @param params [Hash] text-to-image parameters
        # @return [RunApi::Wan::Types::CompletedImageTaskResponse] completed image generation
        def run(**params)
          task = create(**params)
          poll_until_complete { get(task.id) }
        end

        # Create a text-to-image generation task.
        #
        # @param params [Hash] text-to-image parameters
        # @return [RunApi::Wan::Types::ImageTaskResponse] task creation result with id
        def create(**params)
          params = compact_params(params)
          validate_params!(params)
          request(:post, ENDPOINT, body: params)
        end

        # Get text-to-image status by task ID.
        #
        # @param id [String] task ID
        # @return [RunApi::Wan::Types::ImageTaskResponse] current task status
        def get(id)
          request(:get, "#{ENDPOINT}/#{id}")
        end

        private

        def validate_params!(params)
          raise Core::ValidationError, "model is required" unless param(params, :model)
          raise Core::ValidationError, "prompt is required" unless param(params, :prompt)

          model = param(params, :model)
          unless Types::TEXT_TO_IMAGE_MODELS.include?(model)
            raise Core::ValidationError, "Invalid model: #{model}. Must be one of: #{Types::TEXT_TO_IMAGE_MODELS.join(", ")}"
          end
        end
      end
    end
  end
end
