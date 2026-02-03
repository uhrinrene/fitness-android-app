package com.project.fitify.viewmodel.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import com.project.fitify.model.detail.ExerciseActions
import com.project.fitify.viewmodel.detail.uimapping.DetailUiMapper
import com.project.fitify.model.detail.domainmapping.ExerciseDomainModel
import com.project.fitify.common.IInteractor
import com.project.fitify.common.IVideoPlayerHandler
import com.project.fitify.common.mvi.MviViewModel
import com.project.fitify.StatefulUiModel
import com.project.fitify.collectEmits
import com.project.fitify.common.uimapper.ErrorUiMapper
import com.project.fitify.contract.detail.DetailContract.*
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val exerciseInteractor: IInteractor<ExerciseActions, ExerciseDomainModel>,
    private val detailUiMapper: DetailUiMapper,
    private val errorUiMapper: ErrorUiMapper,
    private val playerHandler: IVideoPlayerHandler,
) : MviViewModel<Event, State, Effect>() {

    private val packCode: String? = savedStateHandle[PACK_CODE]
    private val exerciseCode: String? = savedStateHandle[EXERCISE_CODE]

    override fun createInitialState() =
        State(state = StatefulUiModel.Loading())

    val playerInstance: StateFlow<Player?> = playerHandler.instance

    init {
        exerciseCollect()
        setEvent(event = Event.LoadExercise)
    }

    private fun exerciseCollect() = viewModelScope.launch {
        exerciseInteractor.loadData().collectEmits(actionLoading = {
            setState { copy(state = StatefulUiModel.Loading()) }
        }, actionError = { errorDomainModel ->
            setState {
                copy(
                    state = errorUiMapper.provideErrorState(
                        errorDomainModel = errorDomainModel,
                        action = { setEvent(event = Event.LoadExercise) })
                )
            }
        }, actionSuccess = { result ->
            playerHandler.prepare(url = result.videoUrl)
            setState {
                copy(
                    state = detailUiMapper.provideContentState(
                        domainModel = result,
                    )
                )
            }
        })
    }

    override fun handleEvent(event: Event) {
        when(event) {
            Event.LoadExercise -> viewModelScope.launch {
                exerciseInteractor.sendAction(
                    action = ExerciseActions.LoadExercise(
                        packCode = packCode,
                        exerciseCode = exerciseCode
                    )
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        playerHandler.release()
    }

    companion object {
        const val PACK_CODE = "packCode"
        const val EXERCISE_CODE = "exerciseCode"
    }
}