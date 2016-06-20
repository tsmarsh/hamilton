package com.tailoredshapes.jms;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        Injector injector = Guice.createInjector(new HamiltonModule());
    }
}
