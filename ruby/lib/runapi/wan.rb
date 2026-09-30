# frozen_string_literal: true

require "runapi/core"
require_relative "wan/types"
require_relative "wan/resources/text_to_video"
require_relative "wan/resources/image_to_video"
require_relative "wan/resources/speech_to_video"
require_relative "wan/resources/animate"
require_relative "wan/resources/text_to_image"
require_relative "wan/resources/edit_video"
require_relative "wan/client"

module RunApi
  module Wan
    AuthenticationError = RunApi::Core::AuthenticationError
    RateLimitError = RunApi::Core::RateLimitError
    InsufficientCreditsError = RunApi::Core::InsufficientCreditsError
    NotFoundError = RunApi::Core::NotFoundError
    ValidationError = RunApi::Core::ValidationError
    TaskFailedError = RunApi::Core::TaskFailedError
    TaskTimeoutError = RunApi::Core::TaskTimeoutError
  end
end
