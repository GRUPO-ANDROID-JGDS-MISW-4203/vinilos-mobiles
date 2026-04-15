package com.tsdc.vinilos.ui.albums

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.tsdc.vinilos.R
import kotlin.Int

public class AlbumsFragmentDirections private constructor() {
  private data class ActionAlbumsToDetail(
    public val albumId: Int = 0,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_albums_to_detail

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putInt("albumId", this.albumId)
        return result
      }
  }

  public companion object {
    public fun actionAlbumsToDetail(albumId: Int = 0): NavDirections = ActionAlbumsToDetail(albumId)

    public fun actionAlbumsToCreateAlbum(): NavDirections =
        ActionOnlyNavDirections(R.id.action_albums_to_createAlbum)
  }
}
