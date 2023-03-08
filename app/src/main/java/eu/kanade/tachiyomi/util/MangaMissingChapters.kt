package eu.kanade.tachiyomi.util

import eu.kanade.tachiyomi.ui.manga.ChapterItem

public fun getMissingChapters(chaptersInput: List<ChapterItem>): List<Int>? {
    val list = mutableListOf<Int>()
    if (chaptersInput.isEmpty()) {
        return list
    }

    val chapters = chaptersInput.sortedBy { it.chapter.chapterNumber }.reversed()
    var currentChapter = 0f
    // Get and evaluate most recent chapter
    var prevChapter = chapters[0].chapter.chapterNumber

    // Evaluate chapters from most to least recent
    for (i in 1 until chapters.count()) {
        // Get chapter
        currentChapter = chapters[i].chapter.chapterNumber

        // Evaluate
        if (currentChapter == -1f) {
            return null
        }
        if (prevChapter - currentChapter > 1) {
            // Loop, in case there are multiple chapters missing at once
            for (i in prevChapter.toInt() - 1 downTo (currentChapter.toInt() + 1))
                list.add(i)
        }

        // Prepare for next loop
        prevChapter = currentChapter
    }

    return list
}
