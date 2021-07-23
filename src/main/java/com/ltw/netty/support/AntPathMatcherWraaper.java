package com.ltw.netty.support;

import com.ltw.netty.pojo.PojoEndpointServer;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.springframework.util.AntPathMatcher;

import java.util.LinkedHashMap;
import java.util.Map;


public class AntPathMatcherWraaper extends AntPathMatcher implements WsPathMatcher {

    private String pattern;

    public AntPathMatcherWraaper(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return this.pattern;
    }

    @Override
    public boolean matchAndExtract(QueryStringDecoder decoder, Channel channel) {
        Map<String, String> variables = new LinkedHashMap<>();
        boolean result = doMatch(pattern, decoder.path(), true, variables);
        if (result) {
            channel.attr(PojoEndpointServer.URI_TEMPLATE).set(variables);
            return true;
        }
        return false;
    }
}
