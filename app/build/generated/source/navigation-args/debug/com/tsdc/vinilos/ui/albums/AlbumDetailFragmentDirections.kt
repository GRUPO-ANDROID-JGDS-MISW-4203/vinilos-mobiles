package com.tsdc.vinilos.ui.albums

import android.os.Bundle
import androidx.navigation.NavDirections
import com.tsdc.vinilos.R
import kotlin.Int

public class AlbumDetailFragmentDirections private constructor() {
  private data class ActionDetailToAddTrack(
    public val albumId: Int = 0,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_detail_to_addTrack

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putInt("albumId", this.albumId)
        return result
      }
  }

  public companion object {
    public fun actionDetailToAddTrack(albumId: Int = 0): NavDirections =
        ActionDetailToAddTrack(albumId)
  }
}
