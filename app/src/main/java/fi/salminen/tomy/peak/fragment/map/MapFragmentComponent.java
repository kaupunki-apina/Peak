package fi.salminen.tomy.peak.fragment.map;

import dagger.Component;
import fi.salminen.tomy.peak.app.PeakApplicationComponent;
import fi.salminen.tomy.peak.inject.fragment.FragmentScope;


@FragmentScope
@Component(dependencies = PeakApplicationComponent.class, modules = MapFragmentModule.class)
interface MapFragmentComponent {

    void inject(MapFragment fragment);
}
