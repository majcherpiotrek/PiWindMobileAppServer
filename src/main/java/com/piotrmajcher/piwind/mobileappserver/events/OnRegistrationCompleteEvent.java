package com.piotrmajcher.piwind.mobileappserver.events;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.piotrmajcher.piwind.mobileappserver.web.dto.UserTO;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private Locale locale;
    private UserTO user;

    public OnRegistrationCompleteEvent(UserTO user, Locale locale) {
        super(user);

        this.user = user;
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public UserTO getUser() {
        return user;
    }

    public void setUser(UserTO user) {
        this.user = user;
    }
}
