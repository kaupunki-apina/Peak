package fi.salminen.tomy.peak.feature.settings;


import dagger.Component;
import fi.salminen.tomy.peak.network.api.JourneysApi;

@Component(modules =  SelectLinesDialogModule.class)
interface SelectLinesDialogComponent {
    JourneysApi getJourneysApi();

    void inject(SelectLinesDialog dialog);
}
