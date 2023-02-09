package CapstoneProject.app.dependency;

import CapstoneProject.app.activity.CreateExpenseActivity;
import CapstoneProject.app.activity.GetExpenseActivity;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Dagger component for providing dependency injection in the Music Playlist Service.
 */
@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {

    /**
     * Provides the relevant activity.
     * @return GetExpenseActivity
     */
    GetExpenseActivity provideGetExpenseActivity();

    CreateExpenseActivity provideCreateExpenseActivity();

}
