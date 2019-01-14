package com.blackmorse.guice;

import com.blackmorse.xls.writer.statement.XlsWriterFactory;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class XlsModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().build(XlsWriterFactory.class));
    }
}
