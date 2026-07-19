# frozen_string_literal: true

require "spec_helper"

RSpec.describe RunApi::Wan::Resources::TextToVideo do
  let(:http) { instance_double(RunApi::Core::HttpClient) }
  let(:resource) { described_class.new(http) }
  let(:endpoint) { "/api/v1/wan/text_to_video" }

  describe "#create" do
    it "POSTs to the correct endpoint with params" do
      params = {model: "wan-2.5-text-to-video", prompt: "a serene ocean", enable_safety_checker: true}
      expect(http).to receive(:request).with(:post, endpoint, body: params)
        .and_return("id" => "task-1")

      result = resource.create(**params)
      expect(result).to be_a(RunApi::Wan::Types::VideoTaskResponse)
      expect(result.id).to eq("task-1")
    end

    it "POSTs r2v params through text-to-video" do
      params = {
        model: "wan-2.7-r2v",
        prompt: "character walking in a city",
        reference_image_urls: ["https://cdn.runapi.ai/public/samples/reference.jpg"],
        output_resolution: "1080p"
      }
      expect(http).to receive(:request).with(:post, endpoint, body: params)
        .and_return("id" => "task-r2v")

      result = resource.create(**params)
      expect(result).to be_a(RunApi::Wan::Types::VideoTaskResponse)
      expect(result.id).to eq("task-r2v")
    end

    it "raises ValidationError when model is missing" do
      expect { resource.create(prompt: "test") }
        .to raise_error(RunApi::Core::ValidationError, /model must be one of/)
    end

    it "raises ValidationError when prompt is missing" do
      expect { resource.create(model: "wan-2.5-text-to-video") }
        .to raise_error(RunApi::Core::ValidationError, /prompt is required/)
    end

    it "raises ValidationError for invalid model" do
      expect { resource.create(model: "invalid", prompt: "test") }
        .to raise_error(RunApi::Core::ValidationError, /model must be one of/)
    end

    it "accepts all valid T2V models" do
      RunApi::Wan::CONTRACT["text-to-video"]["models"].each do |model|
        params = {model: model, prompt: "test"}
        expect(http).to receive(:request).with(:post, endpoint, body: params).and_return("id" => "t1")
        resource.create(**params)
      end
    end
  end

  describe "#get" do
    it "GETs the correct endpoint" do
      expect(http).to receive(:request).with(:get, "#{endpoint}/task-1")
        .and_return("id" => "task-1", "status" => "completed", "videos" => [{"url" => "https://cdn.runapi.ai/public/samples/source.mp4"}])

      result = resource.get("task-1")
      expect(result).to be_a(RunApi::Wan::Types::VideoTaskResponse)
      expect(result.status).to eq("completed")
      expect(result.videos.first.url).to eq("https://cdn.runapi.ai/public/samples/source.mp4")
    end
  end

  describe "#run" do
    it "creates then polls until complete" do
      params = {model: "wan-2.5-text-to-video", prompt: "ocean waves"}
      expect(http).to receive(:request).with(:post, endpoint, body: params).and_return("id" => "task-1")
      expect(http).to receive(:request).with(:get, "#{endpoint}/task-1").and_return("id" => "task-1", "status" => "processing")
      expect(http).to receive(:request).with(:get, "#{endpoint}/task-1")
        .and_return("id" => "task-1", "status" => "completed", "videos" => [{"url" => "https://cdn.runapi.ai/public/samples/source.mp4"}])

      allow(RunApi::Core::Polling).to receive(:sleep)

      result = resource.run(**params)
      expect(result.videos.first.url).to eq("https://cdn.runapi.ai/public/samples/source.mp4")
    end
  end
end

RSpec.describe RunApi::Wan::Resources::ImageToVideo do
  let(:http) { instance_double(RunApi::Core::HttpClient) }
  let(:resource) { described_class.new(http) }
  let(:endpoint) { "/api/v1/wan/image_to_video" }

  describe "#create" do
    it "POSTs with model and first_frame_image_url" do
      params = {model: "wan-2.5-image-to-video", first_frame_image_url: "https://cdn.runapi.ai/public/samples/input.png", prompt: "animate", duration_seconds: 5, output_resolution: "1080p"}
      expect(http).to receive(:request).with(:post, endpoint, body: params).and_return("id" => "task-2")

      result = resource.create(**params)
      expect(result).to be_a(RunApi::Wan::Types::VideoTaskResponse)
      expect(result.id).to eq("task-2")
    end

    it "raises ValidationError when model is missing" do
      expect { resource.create(first_frame_image_url: "https://cdn.runapi.ai/public/samples/input.png") }
        .to raise_error(RunApi::Core::ValidationError, /model must be one of/)
    end

    it "raises ValidationError for invalid model" do
      expect { resource.create(model: "bad-model", first_frame_image_url: "https://cdn.runapi.ai/public/samples/input.png") }
        .to raise_error(RunApi::Core::ValidationError, /model must be one of/)
    end

    it "accepts all valid I2V models" do
      contract = RunApi::Wan::CONTRACT["image-to-video"]
      contract["models"].each do |model|
        required = contract["fields_by_model"][model].select { |_, rules| rules["required"] }
        params = required.to_h do |field, rules|
          value = (rules["type"] == "integer") ? 5 : "https://cdn.runapi.ai/public/samples/x"
          [field.to_sym, value]
        end
        params[:model] = model
        expect(http).to receive(:request).with(:post, endpoint, body: params).and_return("id" => "t1")
        resource.create(**params)
      end
    end
  end

  describe "#get" do
    it "GETs the correct endpoint" do
      expect(http).to receive(:request).with(:get, "#{endpoint}/task-2")
        .and_return("id" => "task-2", "status" => "completed")

      result = resource.get("task-2")
      expect(result.status).to eq("completed")
    end
  end
end

RSpec.describe RunApi::Wan::Resources::EditVideo do
  let(:http) { instance_double(RunApi::Core::HttpClient) }
  let(:resource) { described_class.new(http) }
  let(:endpoint) { "/api/v1/wan/edit_video" }

  describe "#create" do
    it "POSTs 2.6 edit params" do
      params = {
        model: "wan-2.6-edit-video",
        prompt: "make it cinematic",
        source_video_urls: ["https://cdn.runapi.ai/public/samples/source.mp4"],
        output_resolution: "1080p"
      }
      expect(http).to receive(:request).with(:post, endpoint, body: params).and_return("id" => "task-edit-26")

      result = resource.create(**params)
      expect(result).to be_a(RunApi::Wan::Types::VideoTaskResponse)
      expect(result.id).to eq("task-edit-26")
    end

    it "POSTs 2.7 edit params" do
      params = {
        model: "wan-2.7-edit-video",
        source_video_url: "https://cdn.runapi.ai/public/samples/source.mp4",
        prompt: "remove background",
        reference_image_url: "https://cdn.runapi.ai/public/samples/style.png"
      }
      expect(http).to receive(:request).with(:post, endpoint, body: params).and_return("id" => "task-edit-27")

      result = resource.create(**params)
      expect(result.id).to eq("task-edit-27")
    end

    it "raises ValidationError when 2.6 source videos are missing" do
      expect { resource.create(model: "wan-2.6-edit-video", prompt: "test") }
        .to raise_error(RunApi::Core::ValidationError, /source_video_urls is required/)
    end

    it "raises ValidationError when 2.7 source video is missing" do
      expect { resource.create(model: "wan-2.7-edit-video") }
        .to raise_error(RunApi::Core::ValidationError, /source_video_url is required/)
    end
  end
end

RSpec.describe RunApi::Wan::Resources::Animate do
  let(:http) { instance_double(RunApi::Core::HttpClient) }
  let(:resource) { described_class.new(http) }
  let(:endpoint) { "/api/v1/wan/animate" }

  describe "#create" do
    it "POSTs source image and reference video params" do
      params = {
        model: "wan-2.2-animate-move",
        source_image_url: "https://cdn.runapi.ai/public/samples/character.png",
        reference_video_url: "https://cdn.runapi.ai/public/samples/motion.mp4"
      }
      expect(http).to receive(:request).with(:post, endpoint, body: params).and_return("id" => "task-animate")

      result = resource.create(**params)
      expect(result).to be_a(RunApi::Wan::Types::VideoTaskResponse)
      expect(result.id).to eq("task-animate")
    end

    it "raises ValidationError when source_image_url is missing" do
      expect { resource.create(model: "wan-2.2-animate-move", reference_video_url: "https://cdn.runapi.ai/public/samples/motion.mp4") }
        .to raise_error(RunApi::Core::ValidationError, /source_image_url is required/)
    end

    it "raises ValidationError when reference_video_url is missing" do
      expect { resource.create(model: "wan-2.2-animate-move", source_image_url: "https://cdn.runapi.ai/public/samples/character.png") }
        .to raise_error(RunApi::Core::ValidationError, /reference_video_url is required/)
    end
  end
end

RSpec.describe RunApi::Wan::Resources::SpeechToVideo do
  let(:http) { instance_double(RunApi::Core::HttpClient) }
  let(:resource) { described_class.new(http) }
  let(:endpoint) { "/api/v1/wan/speech_to_video" }

  describe "#create" do
    it "POSTs source image and source audio params" do
      params = {
        model: "wan-2.2-a14b-speech-to-video-turbo",
        prompt: "speak naturally",
        source_image_url: "https://cdn.runapi.ai/public/samples/portrait.png",
        source_audio_url: "https://cdn.runapi.ai/public/samples/speech.mp3"
      }
      expect(http).to receive(:request).with(:post, endpoint, body: params).and_return("id" => "task-speech")

      result = resource.create(**params)
      expect(result).to be_a(RunApi::Wan::Types::VideoTaskResponse)
      expect(result.id).to eq("task-speech")
    end

    it "raises ValidationError when source_image_url is missing" do
      expect {
        resource.create(
          model: "wan-2.2-a14b-speech-to-video-turbo",
          prompt: "speak naturally",
          source_audio_url: "https://cdn.runapi.ai/public/samples/speech.mp3"
        )
      }.to raise_error(RunApi::Core::ValidationError, /source_image_url is required/)
    end

    it "raises ValidationError when source_audio_url is missing" do
      expect {
        resource.create(
          model: "wan-2.2-a14b-speech-to-video-turbo",
          prompt: "speak naturally",
          source_image_url: "https://cdn.runapi.ai/public/samples/portrait.png"
        )
      }.to raise_error(RunApi::Core::ValidationError, /source_audio_url is required/)
    end
  end
end

RSpec.describe RunApi::Wan::Resources::TextToImage do
  let(:http) { instance_double(RunApi::Core::HttpClient) }
  let(:resource) { described_class.new(http) }
  let(:endpoint) { "/api/v1/wan/text_to_image" }

  describe "#create" do
    it "POSTs to the image endpoint" do
      params = {model: "wan-2.7-image", prompt: "a mountain at sunset", aspect_ratio: "1:8", output_resolution: "2k", output_count: 2}
      expect(http).to receive(:request).with(:post, endpoint, body: params).and_return("id" => "task-3")

      result = resource.create(**params)
      expect(result).to be_a(RunApi::Wan::Types::ImageTaskResponse)
      expect(result.id).to eq("task-3")
    end

    it "POSTs source_image_urls to the image endpoint" do
      params = {model: "wan-2.7-image", prompt: "edit this image", source_image_urls: ["https://cdn.runapi.ai/public/samples/source.jpg"]}
      expect(http).to receive(:request).with(:post, endpoint, body: params).and_return("id" => "task-source")

      result = resource.create(**params)
      expect(result.id).to eq("task-source")
    end

    it "raises ValidationError when model is missing" do
      expect { resource.create(prompt: "test") }
        .to raise_error(RunApi::Core::ValidationError, /model must be one of/)
    end

    it "raises ValidationError when prompt is missing" do
      expect { resource.create(model: "wan-2.7-image") }
        .to raise_error(RunApi::Core::ValidationError, /prompt is required/)
    end

    it "raises ValidationError for invalid model" do
      expect { resource.create(model: "invalid", prompt: "test") }
        .to raise_error(RunApi::Core::ValidationError, /model must be one of/)
    end
  end

  describe "#get" do
    it "GETs and returns ImageTaskResponse" do
      expect(http).to receive(:request).with(:get, "#{endpoint}/task-3")
        .and_return("id" => "task-3", "status" => "completed", "images" => [{"url" => "https://cdn.runapi.ai/public/samples/input.png"}])

      result = resource.get("task-3")
      expect(result).to be_a(RunApi::Wan::Types::ImageTaskResponse)
      expect(result.images.first.url).to eq("https://cdn.runapi.ai/public/samples/input.png")
    end
  end

  describe "#run" do
    it "creates then polls until complete" do
      params = {model: "wan-2.7-image", prompt: "mountain"}
      expect(http).to receive(:request).with(:post, endpoint, body: params).and_return("id" => "task-3")
      expect(http).to receive(:request).with(:get, "#{endpoint}/task-3")
        .and_return("id" => "task-3", "status" => "completed", "images" => [{"url" => "https://cdn.runapi.ai/public/samples/input.png"}])

      allow(RunApi::Core::Polling).to receive(:sleep)

      result = resource.run(**params)
      expect(result.images.first.url).to eq("https://cdn.runapi.ai/public/samples/input.png")
    end
  end
end
