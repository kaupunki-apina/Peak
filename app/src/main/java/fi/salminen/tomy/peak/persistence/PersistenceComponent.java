package fi.salminen.tomy.peak.persistence;


import dagger.Component;

@PrefsScope
@Component(modules = PersistenceModule.class)
public interface PersistenceComponent {

    PeakPrefs getPeakPrefs();
}
