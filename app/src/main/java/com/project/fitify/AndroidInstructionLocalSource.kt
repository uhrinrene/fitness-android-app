package com.project.fitify

import android.content.Context
import com.project.fitify.common.IInstructionLocalSource

class AndroidInstructionLocalSource(
    private val context: Context
) : IInstructionLocalSource {
    override fun getInstructionsJson(fileName: String): String {
        return context.assets.open("$fileName.json")
            .bufferedReader()
            .use { it.readText() }
    }
}