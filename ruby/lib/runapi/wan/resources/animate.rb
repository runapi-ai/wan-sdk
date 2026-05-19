# frozen_string_literal: true

module RunApi
  module Wan
    module Resources
      class Animate
        include RunApi::Core::ResourceHelpers

        ENDPOINT = "/api/v1/wan/animate"
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
          raise Core::ValidationError, "video_url is required" unless param(params, :video_url)
          raise Core::ValidationError, "image_url is required" unless param(params, :image_url)

          model = param(params, :model)
          unless Types::ANIMATE_MODELS.include?(model)
            raise Core::ValidationError, "Invalid model: #{model}. Must be one of: #{Types::ANIMATE_MODELS.join(", ")}"
          end
        end
      end
    end
  end
end
