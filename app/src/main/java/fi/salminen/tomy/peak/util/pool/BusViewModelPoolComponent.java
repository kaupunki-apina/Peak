package fi.salminen.tomy.peak.util.pool;

import dagger.Component;
import fi.salminen.tomy.peak.viewmodels.BusViewModel;


@Component(modules = BusViewModelPoolModule.class)
interface BusViewModelPoolComponent {

    BusViewModel busViewModel();
}
