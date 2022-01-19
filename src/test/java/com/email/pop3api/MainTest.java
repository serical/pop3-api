package com.email.pop3api;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.junit.jupiter.api.Test;

public class MainTest {

    @Test
    public void testPing() {
        final HttpResponse response = HttpRequest.post("localhost:8080/api/v1/email/ping")
                .basicAuth("nima", "asdfa")
                .execute();
        System.out.println(response);
    }
}
