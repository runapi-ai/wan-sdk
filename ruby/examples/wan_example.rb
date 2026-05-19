#!/usr/bin/env ruby
# frozen_string_literal: true

require "runapi/wan"

client = RunApi::Wan::Client.new(
  api_key: ENV.fetch("RUNAPI_API_KEY", "runapi_test_token"),
  base_url: ENV.fetch("RUNAPI_BASE_URL", "http://localhost:3000")
)

# 1. Image generation (fastest, ~30s)
puts "=== Image Generation ==="
result = client.text_to_image.run(
  model: "wan-2-7-image",
  prompt: "A serene Japanese garden with cherry blossoms"
)
puts "Status: #{result.status}"
puts "Images: #{result.images&.map(&:url)}"

# 2. Text-to-video
puts "\n=== Text to Video ==="
result = client.text_to_video.run(
  model: "wan-2-6-text-to-video",
  prompt: "Ocean waves crashing on a rocky shore, cinematic",
  resolution: "720p",
  aspect_ratio: "16:9"
)
puts "Status: #{result.status}"
puts "Videos: #{result.videos&.map(&:url)}"

# 3. Manual polling (create + get)
puts "\n=== Manual Polling ==="
task = client.text_to_image.create(
  model: "wan-2-7-image",
  prompt: "A futuristic cityscape at night"
)
puts "Task ID: #{task.id}"

loop do
  status = client.text_to_image.get(task.id)
  puts "Polling... status=#{status.status}"
  break if %w[completed failed].include?(status.status)
  sleep 3
end

# 4. Error handling
puts "\n=== Error Handling ==="
begin
  client.text_to_video.create(model: "invalid-model", prompt: "test")
rescue RunApi::Core::ValidationError => e
  puts "Caught ValidationError: #{e.message}"
end

begin
  client.image_to_video.create(model: "wan-2-6-flash-image-to-video", prompt: "test", audio: true)
rescue RunApi::Core::ValidationError => e
  puts "Caught ValidationError (missing image_urls): #{e.message}"
end
