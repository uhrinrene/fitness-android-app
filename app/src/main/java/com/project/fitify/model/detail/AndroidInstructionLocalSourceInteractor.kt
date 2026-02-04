package com.project.fitify.model.detail

import android.content.Context
import com.project.fitify.common.IInstructionLocalSource

class AndroidInstructionLocalSourceInteractor(
    private val context: Context
) : IInstructionLocalSource {
    override fun getInstructionsJson(fileName: String): String {
        return context.assets.open("$fileName.json")
            .bufferedReader()
            .use { it.readText() }
    }
}