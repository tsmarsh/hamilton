package com.tailoredshapes.jms;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;

import javax.inject.Named;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.function.Predicate;

public class HamiltonModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(MessageListener.class).to(HTTPForwarder.class);
        bind(new TypeLiteral<Predicate<TextMessage>>() {
        }).toInstance((message) -> true);
    }

    @Provides
    @Named("uri")
    String uri(){
        return "";
    }
}
