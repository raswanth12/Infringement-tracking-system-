import java.io.IOException;
import java.util.List;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import sampleclass.OkHttpClient;

public class OpenAiApiClient {
    private static final String API_KEY = "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";
    private static final String API_URL = "https://api.openai.com/v1/engines/davinci-codex/completions";

    /**
     * @param prompt
     * @return
     * @throws IOException
     */ 
    public static String isAiGenerated(String prompt) throws IOException {
        final OkHttpClient client = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<CompletionRequest> requestAdapter = moshi.adapter(CompletionRequest.class);
        JsonAdapter<CompletionResponse> responseAdapter = moshi.adapter(CompletionResponse.class);

        CompletionRequest completionRequest = new CompletionRequest(prompt);
        RequestBody requestBody = RequestBody.create(requestAdapter.toJson(completionRequest), MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(API_URL)
                .header("Authorization", "Bearer " + API_KEY)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            CompletionResponse completionResponse = responseAdapter.fromJson(response.body().source());
            return completionResponse.choices.get(0).text;
        }
    }

    static class CompletionRequest {
        String prompt;
        int max_tokens;

        CompletionRequest(String prompt) {
            this.prompt = prompt;
            this.max_tokens = 5;
        }
    }

    static class CompletionResponse {
        List<Choice> choices;

        static class Choice {
            String text;
        }
    }
}
