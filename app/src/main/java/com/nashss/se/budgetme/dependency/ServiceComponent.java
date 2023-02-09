package com.nashss.se.budgetme.dependency;

import com.nashss.se.budgetme.activity.CreateExpenseActivity;
import com.nashss.se.budgetme.activity.GetExpenseActivity;
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
