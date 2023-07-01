package github.sachin2dehury.mediaplayer

import android.content.Context
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object MediaPlayerModule {

    @Provides
    @ViewModelScoped
    fun providePlayer(@ApplicationContext context: Context): Player =
        ExoPlayer.Builder(context).build()

    @Provides
    @ViewModelScoped
    fun provideMetaDataReader(@ApplicationContext context: Context): MetaDataReader =
        MetaDataReaderImpl(context)
}