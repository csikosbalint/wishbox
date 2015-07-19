package hu.fnf.devel.wishbox.gateway.security;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChannelInterceptor extends ChannelInterceptorAdapter {
    @Override
    public boolean preReceive( MessageChannel channel ) {
        return super.preReceive( channel );
    }

    @Override
    public void afterReceiveCompletion( Message<?> message, MessageChannel channel, Exception ex ) {
        System.out.println( "message: " + message.toString() );
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
        ConcurrentHashMap map = (ConcurrentHashMap) message.getHeaders().get("simpSessionAttributes");
        for (Object o : map.entrySet()) {
            try {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) o;
                System.out.println(entry.getKey() + "->" + entry.getValue());
            } catch (ClassCastException ignored) {

            }
        }
        return message;
    }
}
