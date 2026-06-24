# frozen_string_literal: true

module RunApi
  module Wan
    module Types
      # A single generated video result.
      class Video < RunApi::Core::BaseModel
        optional :url, String
      end

      # A single generated image result.
      class Image < RunApi::Core::BaseModel
        optional :url, String
      end

      # Task result for video generation and editing operations.
      class VideoTaskResponse < RunApi::Core::TaskResponse
        required :id, String
        optional :status, String, enum: -> { RunApi::Core::TaskResponse::Status::ALL }
        optional :videos, [-> { Video }]
        optional :error, String
      end

      # Task result for text-to-image operations.
      class ImageTaskResponse < RunApi::Core::TaskResponse
        required :id, String
        optional :status, String, enum: -> { RunApi::Core::TaskResponse::Status::ALL }
        optional :images, [-> { Image }]
        optional :error, String
      end

      # Narrowed responses returned by `run()` methods once polling observes
      # `status: "completed"`. Result arrays are required so consumers never
      # have to null-check them on a successful task.
      class CompletedVideoTaskResponse < VideoTaskResponse
        required :videos, [-> { Video }]
      end

      class CompletedImageTaskResponse < ImageTaskResponse
        required :images, [-> { Image }]
      end
    end
  end
end
