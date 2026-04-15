package com.tsdc.vinilos.ui.artists

import android.os.Bundle
import androidx.navigation.NavDirections
import com.tsdc.vinilos.R
import kotlin.Int

public class ArtistsFragmentDirections private constructor() {
  private data class ActionArtistsToDetail(
    public val artistId: Int = 0,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_artists_to_detail

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putInt("artistId", this.artistId)
        return result
      }
  }

  public companion object {
    public fun actionArtistsToDetail(artistId: Int = 0): NavDirections =
        ActionArtistsToDetail(artistId)
  }
}
