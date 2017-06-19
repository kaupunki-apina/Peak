package fi.salminen.tomy.peak.inject.fragment;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Scope
@Retention(RUNTIME)
public @interface FragmentScope {
}
