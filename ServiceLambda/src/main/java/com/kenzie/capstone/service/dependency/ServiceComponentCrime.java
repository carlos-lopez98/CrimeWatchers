package com.kenzie.capstone.service.dependency;

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DaoModule.class, ServiceModule.class})
public interface ServiceComponentCrime {
    LambdaCrimeService provideLambdaCrimeService();
}
