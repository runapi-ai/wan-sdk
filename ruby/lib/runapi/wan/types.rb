# frozen_string_literal: true

module RunApi
  module Wan
    module Types
      # Text-to-video variants: 2.2 turbo (fast, lower res) through 2.7 (highest quality)
      # and R2V (accepts reference images, videos, first-frame, and audio).
      TEXT_TO_VIDEO_MODELS = %w[
        wan-2.2-a14b-text-to-video-turbo
        wan-2.5-text-to-video
        wan-2.6-text-to-video
        wan-2.7-text-to-video
        wan-2.7-r2v
      ].freeze

      # Image-to-video variants. Flash trades fidelity for speed; 2.7 adds last-frame
      # control, video continuation, driving/background audio, and watermark.
      IMAGE_TO_VIDEO_MODELS = %w[
        wan-2.2-a14b-image-to-video-turbo
        wan-2.5-image-to-video
        wan-2.6-image-to-video
        wan-2.6-flash-image-to-video
        wan-2.7-image-to-video
      ].freeze

      # Speech-driven lip-sync model for talking-head video generation.
      SPEECH_TO_VIDEO_MODELS = %w[wan-2.2-a14b-speech-to-video-turbo].freeze
      # Motion transfer: move (preserves subject) and replace (swaps subject).
      ANIMATE_MODELS = %w[wan-2.2-animate-move wan-2.2-animate-replace].freeze
      # Image generation: standard and pro. Pro model supports thinking_mode for enhanced reasoning.
      TEXT_TO_IMAGE_MODELS = %w[wan-2.7-image wan-2.7-image-pro].freeze
      # Video editing: 2.6 uses source_video_urls (plural), 2.7 uses source_video_url (singular).
      # Flash variants support audio generation and multi-shot mode.
      EDIT_VIDEO_MODELS = %w[
        wan-2.6-edit-video
        wan-2.6-flash-edit-video
        wan-2.7-edit-video
      ].freeze

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
