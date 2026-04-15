package com.tsdc.vinilos.ui.collectors

import android.os.Bundle
import androidx.navigation.NavDirections
import com.tsdc.vinilos.R
import kotlin.Int

public class CollectorsFragmentDirections private constructor() {
  private data class ActionCollectorsToDetail(
    public val collectorId: Int = 0,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_collectors_to_detail

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putInt("collectorId", this.collectorId)
        return result
      }
  }

  public companion object {
    public fun actionCollectorsToDetail(collectorId: Int = 0): NavDirections =
        ActionCollectorsToDetail(collectorId)
  }
}
