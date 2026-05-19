# frozen_string_literal: true

module RunApi
  module Wan
    module Types
      TEXT_TO_VIDEO_MODELS = %w[
        wan-2-2-a14b-text-to-video-turbo
        wan-2-5-text-to-video
        wan-2-6-text-to-video
        wan-2-7-text-to-video
      ].freeze

      IMAGE_TO_VIDEO_MODELS = %w[
        wan-2-2-a14b-image-to-video-turbo
        wan-2-5-image-to-video
        wan-2-6-image-to-video
        wan-2-6-flash-image-to-video
        wan-2-7-image-to-video
      ].freeze

      VIDEO_TO_VIDEO_MODELS = %w[
        wan-2-6-video-to-video
        wan-2-6-flash-video-to-video
      ].freeze

      SPEECH_TO_VIDEO_MODELS = %w[wan-2-2-a14b-speech-to-video-turbo].freeze
      ANIMATE_MODELS = %w[wan-2-2-animate-move wan-2-2-animate-replace].freeze
      TEXT_TO_IMAGE_MODELS = %w[wan-2-7-image wan-2-7-image-pro].freeze
      REFERENCE_TO_VIDEO_MODELS = %w[wan-2-7-r2v].freeze
      EDIT_VIDEO_MODELS = %w[wan-2-7-videoedit].freeze

      class Video < RunApi::Core::BaseModel
        optional :url, String
      end

      class Image < RunApi::Core::BaseModel
        optional :url, String
      end

      class VideoTaskResponse < RunApi::Core::TaskResponse
        required :id, String
        optional :status, String, enum: -> { RunApi::Core::TaskResponse::Status::ALL }
        optional :videos, [ -> { Video } ]
        optional :error, String
      end

      class ImageTaskResponse < RunApi::Core::TaskResponse
        required :id, String
        optional :status, String, enum: -> { RunApi::Core::TaskResponse::Status::ALL }
        optional :images, [ -> { Image } ]
        optional :error, String
      end

      # Narrowed responses returned by `run()` methods once polling observes
      # `status: "completed"`. Result arrays are required so consumers never
      # have to null-check them on a successful task.
      class CompletedVideoTaskResponse < VideoTaskResponse
        required :videos, [ -> { Video } ]
      end

      class CompletedImageTaskResponse < ImageTaskResponse
        required :images, [ -> { Image } ]
      end
    end
  end
end
