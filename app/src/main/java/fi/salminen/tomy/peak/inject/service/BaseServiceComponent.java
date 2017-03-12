package fi.salminen.tomy.peak.inject.service;

import android.content.Context;

import dagger.Component;
import fi.salminen.tomy.peak.app.PeakApplicationComponent;


@ServiceScope
@Component(dependencies = PeakApplicationComponent.class, modules = BaseServiceModule.class)
public interface BaseServiceComponent {

    @ForService
    Context getContext();
}
