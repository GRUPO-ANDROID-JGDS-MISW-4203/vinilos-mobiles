package com.tsdc.vinilos.ui.collectors

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Int
import kotlin.jvm.JvmStatic

public data class CollectorDetailFragmentArgs(
  public val collectorId: Int = 0,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putInt("collectorId", this.collectorId)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("collectorId", this.collectorId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): CollectorDetailFragmentArgs {
      bundle.setClassLoader(CollectorDetailFragmentArgs::class.java.classLoader)
      val __collectorId : Int
      if (bundle.containsKey("collectorId")) {
        __collectorId = bundle.getInt("collectorId")
      } else {
        __collectorId = 0
      }
      return CollectorDetailFragmentArgs(__collectorId)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle):
        CollectorDetailFragmentArgs {
      val __collectorId : Int?
      if (savedStateHandle.contains("collectorId")) {
        __collectorId = savedStateHandle["collectorId"]
        if (__collectorId == null) {
          throw IllegalArgumentException("Argument \"collectorId\" of type integer does not support null values")
        }
      } else {
        __collectorId = 0
      }
      return CollectorDetailFragmentArgs(__collectorId)
    }
  }
}
