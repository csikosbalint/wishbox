package hu.fnf.devel.wishbox.gateway.security;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

public class MessageInterceptor extends ChannelInterceptorAdapter {
    @Override
    public boolean preReceive( MessageChannel channel ) {
        return super.preReceive( channel );
    }

    @Override
    public void afterReceiveCompletion( Message<?> message, MessageChannel channel, Exception ex ) {
        System.out.println( "message: " + message.toString() );
    }
}
