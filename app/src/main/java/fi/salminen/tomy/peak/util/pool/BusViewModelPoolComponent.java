package fi.salminen.tomy.peak.util.pool;

import dagger.Component;
import fi.salminen.tomy.peak.app.PeakApplicationComponent;
import fi.salminen.tomy.peak.viewmodels.BusViewModel;


@BusViewModelPoolScope
@Component(dependencies = PeakApplicationComponent.class, modules = BusViewModelPoolModule.class)
interface BusViewModelPoolComponent {

    BusViewModel busViewModel();
}
