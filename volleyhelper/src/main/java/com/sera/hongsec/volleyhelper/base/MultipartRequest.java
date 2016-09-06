package com.sera.hongsec.volleyhelper.base;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.apache.http.HttpEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 15-9-16.
 */
public class MultipartRequest extends Request<String> {
    private Response.ErrorListener errorListener = null;
    private Response.Listener mListener = null;
    private MultipartRequestParams params = null;
    private HttpEntity httpEntity = null;


    public MultipartRequest(int method, MultipartRequestParams params, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.params = params;
        this.mListener = listener;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            if(params != null) {
                httpEntity = params.getEntity();
                try {
                    httpEntity.writeTo(baos);
                } catch (IOException e) {

                    e.printStackTrace();
                }
                String str = new String(baos.toByteArray());
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        Map<String, String> headers = super.getHeaders();
        if (null == headers || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }
        return headers;
    }

    public String getBodyContentType() {

        String str = httpEntity.getContentType().getValue();
        return httpEntity.getContentType().getValue();
    }


    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }


    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }
}