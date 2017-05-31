package hu.bme.aut.mobsoft.lab.mobsoft.network.mock;

import java.io.ByteArrayInputStream;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;

class MockResponseHelper {
	static Response makeResponse(Request request, int code, final String content) {

		return new Response.Builder()
                .protocol(Protocol.HTTP_2)
                .code(code)
                .message("")
				.request(request)
                .body(new ResponseBody() {
			@Override
			public MediaType contentType() {
				return MediaType.parse("application/json");
			}

			@Override
			public long contentLength() {
				return content.getBytes().length;
			}

			@Override
			public BufferedSource source() {
				return Okio.buffer(Okio.source(new ByteArrayInputStream(content.getBytes())));
			}
		}).build();
	}
}
