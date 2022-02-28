import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapplication.ui.networks.NetworkManager
import com.example.musicapplication.ui.networks.TrendingTitles
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TrendingTitlesViewModel : ViewModel() {

    private val titleFlow = MutableStateFlow(
        TrendingTitlesState(
            currentState = TrendingTitlesCurrentState.initial,
        )
    )

    fun listen(): Flow<TrendingTitlesState> {
        //Log.d("Flow", titleFlow.asStateFlow().toString())
        return titleFlow.asStateFlow()
    }

    fun getTrendingTitles() {
        viewModelScope.launch {
            titleFlow.emit(
                TrendingTitlesState(
                    currentState = TrendingTitlesCurrentState.loading,
                )
            )

            val res = NetworkManager.getTitles()
            //Log.d("RES", res.titles.toString())

            try {
                if (res.trending.isNotEmpty()) {
                    titleFlow.emit(
                        TrendingTitlesState(
                            currentState = TrendingTitlesCurrentState.success,
                            response = res
                        )
                    )
                } else {
                    titleFlow.emit(
                        TrendingTitlesState(
                            currentState = TrendingTitlesCurrentState.error,
                            response = res
                        )
                    )
                }
            } catch (e: Exception) {
                titleFlow.emit(
                    TrendingTitlesState(
                        currentState = TrendingTitlesCurrentState.error,
                        response = res
                    )
                )
            }
        }
    }
}

data class TrendingTitlesState(
    val currentState: TrendingTitlesCurrentState,
    val response: TrendingTitles? = null
)

enum class TrendingTitlesCurrentState {
    initial, loading, success, error
}