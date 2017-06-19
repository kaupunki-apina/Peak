package fi.salminen.tomy.peak.inject.fragment;


import android.content.Context;
import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;

@Module
public class BaseFragmentModule {
    private Fragment fragment;

    public BaseFragmentModule(Fragment fragment) {
        this.fragment= fragment;
    }


    @Provides
    @ForFragment
    Context provideContext() {
        return fragment.getContext();
    }
}
