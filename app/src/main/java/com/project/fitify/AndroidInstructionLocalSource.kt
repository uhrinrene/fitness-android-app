package com.project.fitify

import android.content.Context

class AndroidInstructionLocalSource(
    private val context: Context
) : IInstructionLocalSource {
    override fun getInstructionsJson(): String {
        return context.assets.open("instructions.json")
            .bufferedReader()
            .use { it.readText() }
    }
}