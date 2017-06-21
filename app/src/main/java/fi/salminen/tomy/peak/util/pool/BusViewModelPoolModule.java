package fi.salminen.tomy.peak.util.pool;


import dagger.Module;
import dagger.Provides;
import fi.salminen.tomy.peak.util.icons.IconFactory;
import fi.salminen.tomy.peak.util.icons.MarkerIconModule;
import fi.salminen.tomy.peak.viewmodels.BusViewModel;

@Module(includes = MarkerIconModule.class)
public class BusViewModelPoolModule {

    @Provides
    BusViewModel provideBusViewModel(IconFactory iconFactory) {
        return new BusViewModel(iconFactory);
    }
}
