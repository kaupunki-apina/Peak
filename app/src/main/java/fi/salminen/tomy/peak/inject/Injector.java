package fi.salminen.tomy.peak.inject;


import android.support.annotation.NonNull;

public interface Injector <T>{

    /**
     * Implementing classes corresponding Dagger 2 component.
     *
     * @return Dagger 2 component
     */
    @NonNull T component();

    /**
     * Injects Dagger 2 components.
     */
    void inject();
}
