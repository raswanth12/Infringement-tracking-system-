package sampleclass;

import okhttp3.Request;

public interface OkHttpClient {

    Object newCall(Request request);

}
