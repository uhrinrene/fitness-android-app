package com.project.fitify.common

interface IInstructionLocalSource {
    fun getInstructionsJson(fileName: String): String
}