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
        def run(options: nil, **params)
          task = create(options: options, **params)
          poll_until_complete { get(task.id, options: options) }
        end

        # Create a text-to-image generation task.
        #
        # @param params [Hash] text-to-image parameters
        # @return [RunApi::Wan::Types::ImageTaskResponse] task creation result with id
        def create(options: nil, **params)
          params = compact_params(params)
          validate_params!(params)
          request(:post, ENDPOINT, body: params, options: options)
        end

        # Get text-to-image status by task ID.
        #
        # @param id [String] task ID
        # @return [RunApi::Wan::Types::ImageTaskResponse] current task status
        def get(id, options: nil)
          request(:get, "#{ENDPOINT}/#{id}", options: options)
        end

        private

        def validate_params!(params)
          validate_contract!(CONTRACT["text-to-image"], params)
          raise Core::ValidationError, "prompt is required" unless param(params, :prompt)
        end
      end
    end
  end
end
