package com.shishkindenis.locationtracker_child.daggerUtils;

import com.shishkindenis.locationtracker_child.singletons.IdSingleton;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class IdSingletonModule {
    @Provides
    @Singleton
    IdSingleton provideIdSingleton() {
        return new IdSingleton();
    }
}
