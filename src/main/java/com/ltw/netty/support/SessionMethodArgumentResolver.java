package com.ltw.netty.support;

import com.ltw.netty.pojo.SocketSession;
import io.netty.channel.Channel;
import org.springframework.core.MethodParameter;

import static com.ltw.netty.pojo.PojoEndpointServer.SESSION_KEY;

public class SessionMethodArgumentResolver implements MethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return SocketSession.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, Channel channel, Object object) throws Exception {
        SocketSession socketSession = channel.attr(SESSION_KEY).get();
        return socketSession;
    }
}
