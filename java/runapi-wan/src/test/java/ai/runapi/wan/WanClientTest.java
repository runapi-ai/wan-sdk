package ai.runapi.wan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ai.runapi.core.RequestOptions;
import ai.runapi.core.errors.ValidationException;
import ai.runapi.core.http.HttpRequest;
import ai.runapi.core.http.HttpResponse;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.core.http.JsonRequestBody;
import ai.runapi.core.json.Json;
import ai.runapi.core.polling.TaskCreateResponse;
import ai.runapi.wan.resources.AnimateResource;
import ai.runapi.wan.resources.EditVideoResource;
import ai.runapi.wan.resources.ImageToVideoResource;
import ai.runapi.wan.resources.SpeechToVideoResource;
import ai.runapi.wan.resources.TextToImageResource;
import ai.runapi.wan.resources.TextToVideoResource;
import ai.runapi.wan.types.AnimateModel;
import ai.runapi.wan.types.AnimateParams;
import ai.runapi.wan.types.AspectRatio;
import ai.runapi.wan.types.BoundingBox;
import ai.runapi.wan.types.BoundingBoxGroup;
import ai.runapi.wan.types.ColorPaletteItem;
import ai.runapi.wan.types.CompletedTextToImageResponse;
import ai.runapi.wan.types.EditVideoModel;
import ai.runapi.wan.types.EditVideoParams;
import ai.runapi.wan.types.ImageToVideoModel;
import ai.runapi.wan.types.ImageToVideoParams;
import ai.runapi.wan.types.SpeechToVideoModel;
import ai.runapi.wan.types.SpeechToVideoParams;
import ai.runapi.wan.types.TextToImageModel;
import ai.runapi.wan.types.TextToImageParams;
import ai.runapi.wan.types.TextToImageResponse;
import ai.runapi.wan.types.TextToVideoModel;
import ai.runapi.wan.types.TextToVideoParams;
import ai.runapi.wan.types.TextToVideoResponse;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class WanClientTest {
  @Test
  void builderCreatesClientAndUniversalResources() {
    WanClient client = WanClient.builder().apiKey("sk-test").baseUrl(URI.create("https://runapi.ai")).build();

    assertNotNull(client.textToVideo());
    assertNotNull(client.imageToVideo());
    assertNotNull(client.speechToVideo());
    assertNotNull(client.animate());
    assertNotNull(client.textToImage());
    assertNotNull(client.editVideo());
    assertNotNull(client.files());
    assertNotNull(client.account());
  }

  @Test
  void openValueClassesSerializeAsScalarStrings() throws Exception {
    String modelJson = Json.mapper().writeValueAsString(new TextToVideoModel("wan-2.6-text-to-video"));
    String aspectRatioJson = Json.mapper().writeValueAsString(new AspectRatio("16:9"));

    assertEquals("\"wan-2.6-text-to-video\"", modelJson);
    assertEquals(new TextToVideoModel("wan-2.6-text-to-video"), Json.mapper().readValue(modelJson, TextToVideoModel.class));
    assertEquals("\"16:9\"", aspectRatioJson);
    assertEquals(new AspectRatio("16:9"), Json.mapper().readValue(aspectRatioJson, AspectRatio.class));
  }

  @Test
  void publicResourcesExposeInterfacesOnly() {
    assertTrue(TextToVideoResource.class.isInterface());
    assertTrue(ImageToVideoResource.class.isInterface());
    assertTrue(SpeechToVideoResource.class.isInterface());
    assertTrue(AnimateResource.class.isInterface());
    assertTrue(TextToImageResource.class.isInterface());
    assertTrue(EditVideoResource.class.isInterface());
  }

  @Test
  void customTransportIsNotClosedByClient() {
    CapturingTransport transport = new CapturingTransport();
    WanClient client = WanClient.builder().apiKey("sk-test").transport(transport).build();

    client.close();

    assertFalse(transport.closed);
  }

  @Test
  void textToVideoCreateSendsContractShape() throws Exception {
    CapturingTransport transport = new CapturingTransport("{\"id\":\"task_t2v_123\",\"status\":\"processing\"}");
    WanClient client = WanClient.builder().apiKey("sk-test").transport(transport).build();

    TaskCreateResponse response =
        client.textToVideo()
            .create(
                TextToVideoParams.builder()
                    .model(TextToVideoModel.WAN_2_6_TEXT_TO_VIDEO)
                    .prompt("A scenic mountain landscape")
                    .enableSafetyChecker(true)
                    .multiShots(false)
                    .build(),
                RequestOptions.builder().header("X-Test", "yes").build());

    assertEquals("task_t2v_123", response.getId());
    assertEquals("POST", transport.request.getMethod().name());
    assertEquals(TextToVideoResource.ENDPOINT, transport.request.getPath());
    assertEquals("yes", transport.request.getOptions().getHeaders().get("X-Test"));
    JsonNode body = bodyJson(transport.request);
    assertEquals("wan-2.6-text-to-video", body.get("model").asText());
    assertEquals("A scenic mountain landscape", body.get("prompt").asText());
    assertEquals(true, body.get("enable_safety_checker").asBoolean());
    assertEquals(false, body.get("multi_shots").asBoolean());
    assertFalse(body.has("reference_image"));
  }

  @Test
  void createPreservesCreativeContentButTrimsModel() throws Exception {
    CapturingTransport transport = new CapturingTransport("{\"id\":\"task_trim_1\",\"status\":\"processing\"}");
    WanClient client = WanClient.builder().apiKey("sk-test").transport(transport).build();

    client.textToVideo()
        .create(
            TextToVideoParams.builder()
                .model("  wan-2.6-text-to-video  ")
                .prompt("  spacing matters  ")
                .negativePrompt("  blurry  ")
                .build());

    JsonNode body = bodyJson(transport.request);
    // Protocol identifier: trimmed.
    assertEquals("wan-2.6-text-to-video", body.get("model").asText());
    // Creative content: preserved verbatim, including surrounding whitespace.
    assertEquals("  spacing matters  ", body.get("prompt").asText());
    assertEquals("  blurry  ", body.get("negative_prompt").asText());
  }

  @Test
  void textToVideoR2VUsesPublicFieldNames() throws Exception {
    CapturingTransport transport = new CapturingTransport("{\"id\":\"task_r2v_123\",\"status\":\"processing\"}");
    WanClient client = WanClient.builder().apiKey("sk-test").transport(transport).build();

    client.textToVideo()
        .create(
            TextToVideoParams.builder()
                .model(TextToVideoModel.WAN_2_7_R2V)
                .prompt("A person walking")
                .referenceImageUrls(Arrays.asList("https://cdn.runapi.ai/public/samples/person.jpg"))
                .outputResolution("1080p")
                .build());

    JsonNode body = bodyJson(transport.request);
    assertEquals("https://cdn.runapi.ai/public/samples/person.jpg", body.get("reference_image_urls").get(0).asText());
    assertEquals("1080p", body.get("output_resolution").asText());
    assertFalse(body.has("reference_image"));
  }

  @Test
  void textToVideoR2VRequiresReferenceMediaBeforeRequest() {
    CapturingTransport transport = new CapturingTransport("{\"id\":\"task_r2v_123\",\"status\":\"processing\"}");
    WanClient client = WanClient.builder().apiKey("sk-test").transport(transport).build();

    assertThrows(
        ValidationException.class,
        () ->
            client.textToVideo()
                .create(
                    TextToVideoParams.builder()
                        .model(TextToVideoModel.WAN_2_7_R2V)
                        .prompt("A person walking")
                        .build()));
    assertEquals(null, transport.request);
  }

  @Test
  void rejectsInvalidContractValueBeforeRequest() {
    CapturingTransport transport = new CapturingTransport("{\"id\":\"task_img_123\"}");
    WanClient client = WanClient.builder().apiKey("sk-test").transport(transport).build();

    assertThrows(
        ValidationException.class,
        () ->
            client.textToImage()
                .create(
                    TextToImageParams.builder()
                        .model(TextToImageModel.WAN_2_7_IMAGE)
                        .prompt("A surreal dreamscape")
                        .aspectRatio("bad")
                        .build()));
    assertEquals(null, transport.request);
  }

  @Test
  void imageToVideoCreateSendsExpectedBody() throws Exception {
    CapturingTransport transport = new CapturingTransport("{\"id\":\"task_i2v_123\",\"status\":\"processing\"}");
    WanClient client = WanClient.builder().apiKey("sk-test").transport(transport).build();

    client.imageToVideo()
        .create(
            ImageToVideoParams.builder()
                .model(ImageToVideoModel.WAN_2_6_IMAGE_TO_VIDEO)
                .prompt("Make this image move")
                .firstFrameImageUrl("https://cdn.runapi.ai/public/samples/input.jpg")
                .outputResolution("1080p")
                .build());

    JsonNode body = bodyJson(transport.request);
    assertEquals(ImageToVideoResource.ENDPOINT, transport.request.getPath());
    assertEquals("https://cdn.runapi.ai/public/samples/input.jpg", body.get("first_frame_image_url").asText());
    assertFalse(body.has("image_urls"));
  }

  @Test
  void speechToVideoCreateSendsExpectedBody() throws Exception {
    CapturingTransport transport = new CapturingTransport("{\"id\":\"task_s2v_123\",\"status\":\"processing\"}");
    WanClient client = WanClient.builder().apiKey("sk-test").transport(transport).build();

    client.speechToVideo()
        .create(
            SpeechToVideoParams.builder()
                .model(SpeechToVideoModel.WAN_2_2_A14B_SPEECH_TO_VIDEO_TURBO)
                .prompt("talking portrait")
                .sourceImageUrl("https://cdn.runapi.ai/public/samples/face.jpg")
                .sourceAudioUrl("https://cdn.runapi.ai/public/samples/speech.mp3")
                .outputResolution("720p")
                .build());

    JsonNode body = bodyJson(transport.request);
    assertEquals(SpeechToVideoResource.ENDPOINT, transport.request.getPath());
    assertEquals("https://cdn.runapi.ai/public/samples/face.jpg", body.get("source_image_url").asText());
    assertEquals("https://cdn.runapi.ai/public/samples/speech.mp3", body.get("source_audio_url").asText());
    assertFalse(body.has("image_url"));
    assertFalse(body.has("audio_url"));
  }

  @Test
  void animateCreateSendsExpectedBody() throws Exception {
    CapturingTransport transport = new CapturingTransport("{\"id\":\"task_anim_123\",\"status\":\"processing\"}");
    WanClient client = WanClient.builder().apiKey("sk-test").transport(transport).build();

    client.animate()
        .create(
            AnimateParams.builder()
                .model(AnimateModel.WAN_2_2_ANIMATE_MOVE)
                .sourceImageUrl("https://cdn.runapi.ai/public/samples/character.jpg")
                .referenceVideoUrl("https://cdn.runapi.ai/public/samples/motion.mp4")
                .outputResolution("580p")
                .build());

    JsonNode body = bodyJson(transport.request);
    assertEquals(AnimateResource.ENDPOINT, transport.request.getPath());
    assertEquals("https://cdn.runapi.ai/public/samples/motion.mp4", body.get("reference_video_url").asText());
    assertFalse(body.has("video_url"));
    assertFalse(body.has("image_url"));
  }

  @Test
  void textToImageCreateSendsExpectedBody() throws Exception {
    CapturingTransport transport = new CapturingTransport("{\"id\":\"task_img_123\",\"status\":\"processing\"}");
    WanClient client = WanClient.builder().apiKey("sk-test").transport(transport).build();

    client.textToImage()
        .create(
            TextToImageParams.builder()
                .model(TextToImageModel.WAN_2_7_IMAGE)
                .prompt("A surreal dreamscape")
                .aspectRatio(AspectRatio.ONE_EIGHT)
                .outputResolution("2k")
                .outputCount(2)
                .sourceImageUrls(Arrays.asList("https://cdn.runapi.ai/public/samples/source.jpg"))
                .build());

    JsonNode body = bodyJson(transport.request);
    assertEquals(TextToImageResource.ENDPOINT, transport.request.getPath());
    assertEquals("1:8", body.get("aspect_ratio").asText());
    assertEquals(2, body.get("output_count").asInt());
    assertEquals("https://cdn.runapi.ai/public/samples/source.jpg", body.get("source_image_urls").get(0).asText());
    assertFalse(body.has("input_urls"));
  }

  @Test
  void textToImageParamsDefensivelyCopyListInputs() throws Exception {
    CapturingTransport transport = new CapturingTransport("{\"id\":\"task_img_123\",\"status\":\"processing\"}");
    WanClient client = WanClient.builder().apiKey("sk-test").transport(transport).build();
    List<ColorPaletteItem> palette = new ArrayList<ColorPaletteItem>();
    palette.add(ColorPaletteItem.builder().hex("#ffffff").ratio(0.5).build());
    List<BoundingBoxGroup> bboxes = new ArrayList<BoundingBoxGroup>();
    bboxes.add(BoundingBoxGroup.of(BoundingBox.of(12, 34, 56, 78)));

    TextToImageParams params =
        TextToImageParams.builder()
            .model(TextToImageModel.WAN_2_7_IMAGE)
            .prompt("A surreal dreamscape")
            .colorPalette(palette)
            .bboxList(bboxes)
            .build();
    palette.add(ColorPaletteItem.builder().hex("#000000").ratio(0.5).build());
    bboxes.add(BoundingBoxGroup.of(BoundingBox.of(90, 12, 120, 140)));

    client.textToImage().create(params);

    JsonNode body = bodyJson(transport.request);
    assertEquals(1, body.get("color_palette").size());
    assertEquals("#ffffff", body.get("color_palette").get(0).get("hex").asText());
    assertEquals(1, body.get("bbox_list").size());
    assertEquals(12, body.get("bbox_list").get(0).get(0).get(0).asInt());
    assertEquals(34, body.get("bbox_list").get(0).get(0).get(1).asInt());
    assertEquals(56, body.get("bbox_list").get(0).get(0).get(2).asInt());
    assertEquals(78, body.get("bbox_list").get(0).get(0).get(3).asInt());
  }

  @Test
  void colorPaletteOmitsUnsetOptionalRatio() throws Exception {
    CapturingTransport transport = new CapturingTransport("{\"id\":\"task_img_123\",\"status\":\"processing\"}");
    WanClient client = WanClient.builder().apiKey("sk-test").transport(transport).build();

    client.textToImage()
        .create(
            TextToImageParams.builder()
                .model(TextToImageModel.WAN_2_7_IMAGE)
                .prompt("A surreal dreamscape")
                .colorPalette(Arrays.asList(ColorPaletteItem.builder().hex("#ffffff").build()))
                .build());

    JsonNode body = bodyJson(transport.request);
    JsonNode item = body.get("color_palette").get(0);
    assertEquals("#ffffff", item.get("hex").asText());
    // An unset optional ratio must be omitted, not serialized as "ratio": null.
    assertFalse(item.has("ratio"));
  }

  @Test
  void editVideoCreateSendsExpectedBody() throws Exception {
    CapturingTransport transport = new CapturingTransport("{\"id\":\"task_vedit_123\",\"status\":\"processing\"}");
    WanClient client = WanClient.builder().apiKey("sk-test").transport(transport).build();

    client.editVideo()
        .create(
            EditVideoParams.builder()
                .model(EditVideoModel.WAN_2_7_EDIT_VIDEO)
                .sourceVideoUrl("https://cdn.runapi.ai/public/samples/source.mp4")
                .prompt("Make the sky dramatic")
                .referenceImageUrl("https://cdn.runapi.ai/public/samples/style.png")
                .outputResolution("1080p")
                .build());

    JsonNode body = bodyJson(transport.request);
    assertEquals(EditVideoResource.ENDPOINT, transport.request.getPath());
    assertEquals("https://cdn.runapi.ai/public/samples/source.mp4", body.get("source_video_url").asText());
    assertEquals("https://cdn.runapi.ai/public/samples/style.png", body.get("reference_image_url").asText());
    assertFalse(body.has("reference_image"));
  }

  @Test
  void getDecodesTaskResponseAndExtraFields() {
    CapturingTransport transport =
        new CapturingTransport(
            "{\"id\":\"task_t2v_456\",\"status\":\"completed\",\"videos\":[{\"url\":\"https://file.runapi.ai/result.mp4\"}],\"custom\":\"kept\"}");
    WanClient client = WanClient.builder().apiKey("sk-test").transport(transport).build();

    TextToVideoResponse response = client.textToVideo().get("task_t2v_456");

    assertEquals("GET", transport.request.getMethod().name());
    assertEquals(TextToVideoResource.ENDPOINT + "/task_t2v_456", transport.request.getPath());
    assertInstanceOf(TextToVideoResponse.class, response);
    assertEquals("completed", response.getStatus().value());
    assertEquals("https://file.runapi.ai/result.mp4", response.getVideos().get(0).getUrl());
    assertEquals("kept", response.extraFields().get("custom").asText());
  }

  @Test
  void runPollsUntilCompletedAndAllowsEmptyResultLists() {
    SequenceTransport transport =
        new SequenceTransport(
            "{\"id\":\"task_img_123\",\"status\":\"processing\"}",
            "{\"id\":\"task_img_123\",\"status\":\"processing\"}",
            "{\"id\":\"task_img_123\",\"status\":\"completed\",\"images\":[],\"custom\":\"kept\"}");
    WanClient client = WanClient.builder().apiKey("sk-test").transport(transport).build();

    CompletedTextToImageResponse response =
        client.textToImage()
            .run(
                TextToImageParams.builder()
                    .model(TextToImageModel.WAN_2_7_IMAGE)
                    .prompt("A surreal dreamscape")
                    .build(),
                RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build());

    assertInstanceOf(CompletedTextToImageResponse.class, response);
    assertEquals("completed", response.getStatus().value());
    assertTrue(response.getImages().isEmpty());
    assertEquals("kept", response.extraFields().get("custom").asText());
    assertEquals(3, transport.calls);
  }

  @Test
  void runRejectsCompletedResponseMissingResultField() {
    SequenceTransport transport =
        new SequenceTransport(
            "{\"id\":\"task_t2v_123\",\"status\":\"processing\"}",
            "{\"id\":\"task_t2v_123\",\"status\":\"completed\"}");
    WanClient client = WanClient.builder().apiKey("sk-test").transport(transport).build();

    assertThrows(
        ValidationException.class,
        () ->
            client.textToVideo()
                .run(
                    TextToVideoParams.builder()
                        .model(TextToVideoModel.WAN_2_6_TEXT_TO_VIDEO)
                        .prompt("A scenic mountain landscape")
                        .build(),
                    RequestOptions.builder()
                        .pollingInterval(Duration.ofMillis(1))
                        .pollingMaxWait(Duration.ofSeconds(1))
                        .build()));
  }

  private static JsonNode bodyJson(HttpRequest request) throws Exception {
    JsonRequestBody body = (JsonRequestBody) request.getBody();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    body.writeTo(out);
    return Json.mapper().readTree(out.toByteArray());
  }

  private static final class CapturingTransport implements HttpTransport {
    private final String response;
    private HttpRequest request;
    private boolean closed;

    private CapturingTransport() {
      this("{\"id\":\"task_123\",\"status\":\"processing\"}");
    }

    private CapturingTransport(String response) {
      this.response = response;
    }

    @Override
    public HttpResponse send(HttpRequest request) {
      this.request = request;
      return new HttpResponse(200, response, Collections.<String, java.util.List<String>>emptyMap());
    }

    @Override
    public void close() {
      closed = true;
    }
  }

  private static final class SequenceTransport implements HttpTransport {
    private final String[] responses;
    private int calls;

    private SequenceTransport(String... responses) {
      this.responses = responses;
    }

    @Override
    public HttpResponse send(HttpRequest request) {
      String response = responses[Math.min(calls, responses.length - 1)];
      calls++;
      return new HttpResponse(200, response, Collections.<String, java.util.List<String>>emptyMap());
    }

    @Override
    public void close() {}
  }
}
