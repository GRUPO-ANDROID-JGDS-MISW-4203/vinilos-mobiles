package com.tsdc.vinilos.ui.artists

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Int
import kotlin.jvm.JvmStatic

public data class ArtistDetailFragmentArgs(
  public val artistId: Int = 0,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putInt("artistId", this.artistId)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("artistId", this.artistId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): ArtistDetailFragmentArgs {
      bundle.setClassLoader(ArtistDetailFragmentArgs::class.java.classLoader)
      val __artistId : Int
      if (bundle.containsKey("artistId")) {
        __artistId = bundle.getInt("artistId")
      } else {
        __artistId = 0
      }
      return ArtistDetailFragmentArgs(__artistId)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): ArtistDetailFragmentArgs {
      val __artistId : Int?
      if (savedStateHandle.contains("artistId")) {
        __artistId = savedStateHandle["artistId"]
        if (__artistId == null) {
          throw IllegalArgumentException("Argument \"artistId\" of type integer does not support null values")
        }
      } else {
        __artistId = 0
      }
      return ArtistDetailFragmentArgs(__artistId)
    }
  }
}
