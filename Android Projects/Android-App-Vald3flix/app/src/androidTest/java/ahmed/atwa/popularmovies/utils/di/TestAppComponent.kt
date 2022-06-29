package ahmed.atwa.popularmovies.utils.di

import ahmed.atwa.popularmovies.PopMovApp
import ahmed.atwa.popularmovies.utils.di.builder.ActivityBuilder
import ahmed.atwa.popularmovies.utils.di.component.AppComponent
import ahmed.atwa.popularmovies.utils.di.module.AppModule
import ahmed.atwa.popularmovies.utils.network.NetworkModule
import ahmed.atwa.popularmovies.utils.di.module.RepoModule
import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.DaggerApplication
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Singleton


@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    MockDbModule::class,
    NetworkModule::class,
    RepoModule::class,
    ActivityBuilder::class,
    MockUrlModule::class
]
)
interface TestAppComponent : AppComponent {

    override fun inject(app: PopMovApp)

    override fun inject(instance: DaggerApplication)

    fun getMockWebServer(): MockWebServer

    @Component.Builder
    interface Builder {

        /**
         * [BindsInstance] annotation is used for, if you want to bind particular object or instance
         * of an object through the time of component construction
         */
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): TestAppComponent
    }

}