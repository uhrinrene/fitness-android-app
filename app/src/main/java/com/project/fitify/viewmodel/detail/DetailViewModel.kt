package com.project.fitify.viewmodel.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import com.project.fitify.ErrorModel
import com.project.fitify.model.detail.ExerciseActions
import com.project.fitify.viewmodel.detail.uimapping.DetailUiMapper
import com.project.fitify.model.detail.domainmapping.ExerciseDomainModel
import com.project.fitify.common.IInteractor
import com.project.fitify.common.IVideoPlayerHandler
import com.project.fitify.common.mvi.MviViewModel
import com.project.fitify.ResultState
import com.project.fitify.StatefulModel
import com.project.fitify.contract.detail.DetailContract.*
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val exerciseInteractor: IInteractor<ExerciseActions, ExerciseDomainModel>,
    private val detailUiMapper: DetailUiMapper,
    private val playerHandler: IVideoPlayerHandler,
) : MviViewModel<Event, State, Effect>() {

    // TODO packCode a exerciseCode dat jako constantu
    private val packCode: String? = savedStateHandle["packCode"]
    private val exerciseCode: String? = savedStateHandle["exerciseCode"]

    override fun createInitialState() =
        State(state = StatefulModel.Loading())

    val playerInstance: StateFlow<Player?> = playerHandler.instance

    init {
        exerciseCollect()
        viewModelScope.launch {
            exerciseInteractor.sendAction(
                action = ExerciseActions.GetExercise(
                    packCode = packCode,
                    exerciseCode = exerciseCode
                )
            )
        }
    }

    private fun exerciseCollect() = viewModelScope.launch {
        exerciseInteractor.loadData().collect { resultState ->
            when (resultState) {
                is ResultState.Error -> {
                    setState {
                        copy(
                            state = StatefulModel.Error(
                                errorModel = ErrorModel(
                                    title = "error",
                                    button = "button"
                                )
                            )
                        )
                    }
                    Log.d("TAG", "Error" + resultState.errorDomainModel.message)
                }

                ResultState.Loading -> {
                    setState { copy(state = StatefulModel.Loading()) }
                    Log.d("TAG", "Loading")
                }

                is ResultState.Success<ExerciseDomainModel> -> {
                    playerHandler.prepare(url = resultState.value.videoUrl)
                    setState {
                        copy(
                            state = detailUiMapper.provideContentState(
                                domainModel = resultState.value,
                            )
                        )
                    }
                }
            }
        }
    }

    override fun handleEvent(event: Event) {
        // no op
    }

    override fun onCleared() {
        super.onCleared()
        playerHandler.release()
    }
}