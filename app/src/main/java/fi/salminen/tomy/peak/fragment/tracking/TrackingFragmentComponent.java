package fi.salminen.tomy.peak.fragment.tracking;

import dagger.Component;
import fi.salminen.tomy.peak.app.PeakApplicationComponent;
import fi.salminen.tomy.peak.inject.fragment.BaseFragmentComponent;
import fi.salminen.tomy.peak.inject.fragment.FragmentScope;
import fi.salminen.tomy.peak.util.MarkerManager;


@FragmentScope
@Component(dependencies = PeakApplicationComponent.class, modules = TrackingFragmentModule.class)
interface TrackingFragmentComponent extends BaseFragmentComponent {

    MarkerManager getMarkerManager();

    void inject(TrackingFragment fragment);
}
