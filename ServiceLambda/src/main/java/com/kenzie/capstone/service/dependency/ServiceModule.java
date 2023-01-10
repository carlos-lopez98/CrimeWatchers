package com.kenzie.capstone.service.dependency;

import com.kenzie.capstone.service.LambdaCrimeService;
import com.kenzie.capstone.service.dao.CrimeDao;
import com.kenzie.capstone.service.dao.ExampleDao;

import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Module(
    includes = DaoModule.class
)
public class ServiceModule {

    @Singleton
    @Provides
    @Inject
    public LambdaService provideLambdaService(@Named("ExampleDao") ExampleDao exampleDao) {
        return new LambdaService(exampleDao);
    }

    /**
     * This class is injecting lambdaCrimeService with a Singleton Instance of CrimeDao
     * @param crimeDao
     * @return
     */

    @Singleton
    @Provides
    @Inject
    public LambdaCrimeService provideLambdaCrimeService(@Named("LambdaCrimeService") CrimeDao crimeDao) {
        return new LambdaCrimeService(crimeDao);
    }
}

