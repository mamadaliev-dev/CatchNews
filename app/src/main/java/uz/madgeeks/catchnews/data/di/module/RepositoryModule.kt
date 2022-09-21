package uz.madgeeks.catchnews.data.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.madgeeks.catchnews.data.base.BaseRepositoryImpl
import uz.madgeeks.catchnews.data.base.BaseService
import uz.madgeeks.catchnews.domain.BaseRepository

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideBaseRepository(baseService: BaseService): BaseRepository {
        return BaseRepositoryImpl(baseService)
    }

}