package com.fullmob.jiraboard.di.components;

import com.fullmob.jiraboard.di.modules.TicketsScreenModule;
import com.fullmob.jiraboard.ui.home.tickets.TicketsFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {TicketsScreenModule.class})
public interface TicketsScreenComponent {
    void inject(TicketsFragment fragment);
}
