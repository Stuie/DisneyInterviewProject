package com.example.marvel.data

import com.example.marvel.data.dto.ComicDTO
import com.example.marvel.data.dto.ComicDataDTO
import com.example.marvel.data.dto.ComicsDataWrapperDTO
import com.example.marvel.data.dto.ThumbnailDTO
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

class ComicRepositoryTest {
    private val mockService = mockk<ComicService>()

    private val subject = ComicRepository(mockService)

    private val comicIdSlot = slot<Int>()

    @BeforeEach
    fun setup() {
        coEvery { mockService.getComic(capture(comicIdSlot)) }.returns(Response.success(testComicsDataWrapperDTO))
    }

    @Test
    fun testGetComic_makesNetworkRequest_returnsListOfComics() = runTest {
        val comicId = 1234
        val response = subject.getComic(comicId).first()

        assertEquals(comicTitle, response?.title)

        coVerify(exactly = 1) { mockService.getComic(any()) }

        assertEquals(comicId, comicIdSlot.captured)
    }

    /**
     * Only one comic should be returned when fetching a comic by its ID, I believe. Verify that if two comics are
     * returned, the first result is what is used to create the [Comic].
     */
    @Test
    fun testGetComic_usesFirstResult_whenMultipleComicsReturned() = runTest {
        // Override the setup for this test
        coEvery { mockService.getComic(any<Int>()) }.returns(Response.success(testSecondComicsDataWrapperDTO))

        val response = subject.getComic(4321).first()

        assertEquals(comicTitle, response?.title)

        coVerify(exactly = 1) { mockService.getComic(any()) }
    }
}

private const val comicTitle = "Comic Title"
private const val secondComicTitle = "Second Comic Title"


private val testThumbnailDTO = ThumbnailDTO(
    path = "https://example.com/image",
    extension = "jpg",
)

private val testComicDTO = ComicDTO(
    id = 1234,
    digitalId = 5432109,
    title = comicTitle,
    issueNumber = 10.0,
    description = "Comic description",
    thumbnail = testThumbnailDTO
)

private val testComicData = ComicDataDTO(
    offset = 0,
    limit = 1,
    total = 1,
    count = 1,
    results = listOf(testComicDTO),
)

private val testComicsDataWrapperDTO = ComicsDataWrapperDTO(
    code = 200,
    status = "Ok",
    copyright = "This is the copyright text",
    attributionText = "This is the attribution text",
    data = testComicData,
)

private val testSecondComicDTO = ComicDTO(
    id = 5678,
    digitalId = 8675309,
    title = secondComicTitle,
    issueNumber = 11.0,
    description = "Comic description",
    thumbnail = testThumbnailDTO
)

private val testSecondComicData = ComicDataDTO(
    offset = 0,
    limit = 2,
    total = 2,
    count = 2,
    results = listOf(testComicDTO, testSecondComicDTO),
)

private val testSecondComicsDataWrapperDTO = ComicsDataWrapperDTO(
    code = 200,
    status = "Ok",
    copyright = "This is the copyright text",
    attributionText = "This is the attribution text",
    data = testSecondComicData,
)
