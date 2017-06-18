package com.jcsanchez.helloworld;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class Main implements RequestHandler<String, String>{
    @Override
    public String handleRequest(String s, Context context) {
        return "Response: " + s;
    }
}
