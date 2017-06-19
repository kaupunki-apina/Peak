package fi.salminen.tomy.peak.inject.fragment;

import android.content.Context;

import dagger.Component;


@Component(modules = BaseFragmentModule.class)
public interface BaseFragmentComponent {

    @ForFragment
    Context getContext();
}
