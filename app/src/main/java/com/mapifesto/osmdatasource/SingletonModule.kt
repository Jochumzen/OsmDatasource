package com.mapifesto.osmdatasource

import com.mapifesto.osm_datasource.OsmIntermediary
import com.mapifesto.osm_datasource.OsmIntermediaryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    @Singleton
    @Provides
    fun provideOsmIntermediary(

    ) : OsmIntermediary {
        return OsmIntermediaryImpl()
    }

}