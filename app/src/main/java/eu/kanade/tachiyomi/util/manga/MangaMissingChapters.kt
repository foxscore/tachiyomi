package eu.kanade.tachiyomi.util.manga

import eu.kanade.tachiyomi.ui.manga.chapter.ChapterItem

public fun getMissingChapters(chaptersInput: List<ChapterItem>): List<Int>? {
    val list = mutableListOf<Int>()
    if (chaptersInput.count() == 0) {
        return list
    }

    val chapters = chaptersInput.sortedBy { it.chapter_number }.reversed()
    var currentChapter = 0f
    // Get and evaluate most recent chapter
    var prevChapter = chapters[0].chapter_number

    // Evaluate chapters from most to least recent
    for (i in 1 until chapters.count()) {
        // Get chapter
        currentChapter = chapters[i].chapter_number

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
