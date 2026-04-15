package com.tsdc.vinilos.ui.albums

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Int
import kotlin.jvm.JvmStatic

public data class AlbumDetailFragmentArgs(
  public val albumId: Int = 0,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putInt("albumId", this.albumId)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("albumId", this.albumId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): AlbumDetailFragmentArgs {
      bundle.setClassLoader(AlbumDetailFragmentArgs::class.java.classLoader)
      val __albumId : Int
      if (bundle.containsKey("albumId")) {
        __albumId = bundle.getInt("albumId")
      } else {
        __albumId = 0
      }
      return AlbumDetailFragmentArgs(__albumId)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): AlbumDetailFragmentArgs {
      val __albumId : Int?
      if (savedStateHandle.contains("albumId")) {
        __albumId = savedStateHandle["albumId"]
        if (__albumId == null) {
          throw IllegalArgumentException("Argument \"albumId\" of type integer does not support null values")
        }
      } else {
        __albumId = 0
      }
      return AlbumDetailFragmentArgs(__albumId)
    }
  }
}
