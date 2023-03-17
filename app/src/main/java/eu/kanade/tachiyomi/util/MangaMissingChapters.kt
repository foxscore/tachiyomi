package eu.kanade.tachiyomi.util

import eu.kanade.tachiyomi.ui.manga.ChapterItem
import kotlin.math.ceil

public fun countMissingChapters(chaptersInput: List<ChapterItem>): Int? {
    if (chaptersInput.isEmpty()) {
        return 0
    }

    // If any chapters[i].chapter.chapterNumber is -1, return null
    if (chaptersInput.any { it.chapter.chapterNumber == -1f }) {
        return null
    }

    var count = 0
    val chapters = chaptersInput.sortedBy { it.chapter.chapterNumber }.reversed()
    var currentChapter = 0f

    // If the first chapter is equal to or greater than 2, there are missing chapters
    if (chapters.last().chapter.chapterNumber >= 2) {
        // The chapter number might be, for example, 16.5, (with 16 being the previous one)
        // so we need to round it up
        count = ceil(chapters.last().chapter.chapterNumber.toDouble()).toInt() - 1
    }

    // Get and evaluate most recent chapter
    var prevChapter = chapters[0].chapter.chapterNumber

    // Evaluate chapters from most to least recent
    for (i in 1 until chapters.count()) {
        // Get chapter
        currentChapter = chapters[i].chapter.chapterNumber

        // Evaluate
        if (prevChapter - currentChapter > 1) {
            // Loop, in case there are multiple chapters missing at once
            for (i in prevChapter.toInt() - 1 downTo (currentChapter.toInt() + 1))
                count++
        }

        // Prepare for next loop
        prevChapter = currentChapter
    }

    return count
}
