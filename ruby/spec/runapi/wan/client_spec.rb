# frozen_string_literal: true

require "spec_helper"

RSpec.describe RunApi::Wan::Client do
  before do
    allow(ConnectionPool).to receive(:new).and_return(instance_double(ConnectionPool))
  end

  after { RunApi.api_key = nil }

  it "accepts api_key as parameter" do
    client = described_class.new(api_key: "param-key")
    expect(client).to be_a(described_class)
  end

  it "falls back to global RunApi.api_key" do
    RunApi.api_key = "global-key"
    client = described_class.new
    expect(client).to be_a(described_class)
  end

  it "raises AuthenticationError without api_key" do
    expect { described_class.new }.to raise_error(RunApi::Core::AuthenticationError, /API key is required/)
  end

  it "exposes all resource accessors" do
    client = described_class.new(api_key: "test-key")
    expect(client.text_to_video).to be_a(RunApi::Wan::Resources::TextToVideo)
    expect(client.image_to_video).to be_a(RunApi::Wan::Resources::ImageToVideo)
    expect(client.speech_to_video).to be_a(RunApi::Wan::Resources::SpeechToVideo)
    expect(client.animate).to be_a(RunApi::Wan::Resources::Animate)
    expect(client.text_to_image).to be_a(RunApi::Wan::Resources::TextToImage)
    expect(client.edit_video).to be_a(RunApi::Wan::Resources::EditVideo)
  end
end
