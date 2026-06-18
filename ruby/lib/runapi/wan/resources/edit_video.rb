# frozen_string_literal: true

module RunApi
  module Wan
    module Resources
      # Modifies existing videos guided by a text prompt and optional reference image.
      # The 2.6 models use source_video_urls (plural, required) while 2.7 uses
      # source_video_url (singular, required). Flash variants support audio and multi-shot.
      class EditVideo
        include RunApi::Core::ResourceHelpers

        ENDPOINT = "/api/v1/wan/edit_video"
        RESPONSE_CLASS = Types::VideoTaskResponse
        COMPLETED_RESPONSE_CLASS = Types::CompletedVideoTaskResponse

        def initialize(http)
          @http = http
        end

        # Edit a video and wait until complete.
        #
        # @param params [Hash] video editing parameters
        # @return [RunApi::Wan::Types::CompletedVideoTaskResponse] completed video edit
        def run(**params)
          task = create(**params)
          poll_until_complete { get(task.id) }
        end

        # Create a video editing task.
        #
        # @param params [Hash] video editing parameters
        # @return [RunApi::Wan::Types::VideoTaskResponse] task creation result with id
        def create(**params)
          params = compact_params(params)
          validate_params!(params)
          request(:post, ENDPOINT, body: params)
        end

        # Get video editing status by task ID.
        #
        # @param id [String] task ID
        # @return [RunApi::Wan::Types::VideoTaskResponse] current task status
        def get(id)
          request(:get, "#{ENDPOINT}/#{id}")
        end

        private

        def validate_params!(params)
          raise Core::ValidationError, "model is required" unless param(params, :model)

          model = param(params, :model)
          unless Types::EDIT_VIDEO_MODELS.include?(model)
            raise Core::ValidationError, "Invalid model: #{model}. Must be one of: #{Types::EDIT_VIDEO_MODELS.join(", ")}"
          end

          if model.include?("2.6")
            raise Core::ValidationError, "prompt is required" unless param(params, :prompt)
            urls = param(params, :source_video_urls)
            raise Core::ValidationError, "source_video_urls is required" if urls.nil? || urls.empty?
          else
            raise Core::ValidationError, "source_video_url is required" unless param(params, :source_video_url)
          end
        end
      end
    end
  end
end
