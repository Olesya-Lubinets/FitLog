package com.example.fitlog

import com.example.fitlog.data.db.FoodFavoriteDao
import com.example.fitlog.data.model.FoodFavorite
import com.example.fitlog.data.repository.FoodFavoriteRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class FoodFavoriteRepositoryTest {
    lateinit var testRepository: FoodFavoriteRepository
    lateinit var testDAO:FoodFavoriteDao
    @Before
    fun setup() {
        testDAO = mockk<FoodFavoriteDao>()
        every {testDAO.getAll()} returns flowOf(emptyList())
        testRepository = FoodFavoriteRepository(testDAO)
    }

   @Test
   fun `getAllOnce returns List of FoodFavorites`() = runTest {
       val fakeDaoResponse = FoodTestData.loadFoodFavoriteListFromMock()
       coEvery { testDAO.getAllOnce() } returns fakeDaoResponse
       assertEquals(fakeDaoResponse, testRepository.getAllOnce())
    }

    @Test
    fun `getById with valid Id returns FoodFavorite`() = runTest {
        val fakeDaoResponse = FoodTestData.getOneFavoriteFromMock()
        coEvery { testDAO.getByID(5) } returns fakeDaoResponse
        assertEquals(fakeDaoResponse, testRepository.getById(5))
    }

    @Test
    fun `getById passes correct ID to DAO`() = runTest {
        val fakeDaoResponse = FoodTestData.getOneFavoriteFromMock()
        coEvery { testDAO.getByID(5) } returns fakeDaoResponse
        testRepository.getById(5)
        coVerify (exactly = 1 ){ testDAO.getByID(5) }
    }

    @Test
    fun `getById with unknown Id returns null`() = runTest {
        coEvery { testDAO.getByID(any()) } returns null
        assertNull(testRepository.getById(9999))
    }

    @Test
    fun `insert calls DAO with correct FoodFavorite argument`() = runTest {
        val fakeFoodFavoriteItem = FoodTestData.getOneFavoriteFromMock()
        coEvery { testDAO.insert(fakeFoodFavoriteItem) } just Runs
        testRepository.insert(fakeFoodFavoriteItem)
        coVerify ( exactly = 1 ) {testDAO.insert(fakeFoodFavoriteItem)}
    }

    @Test
    fun `delete calls DAO with correct FoodFavorite argument`() = runTest {
        val fakeFoodFavoriteItem = FoodTestData.getOneFavoriteFromMock()
        val fakeFoodFavoriteItemName = fakeFoodFavoriteItem.food_name
        coEvery { testDAO.delete(fakeFoodFavoriteItemName) } just Runs
        testRepository.delete(fakeFoodFavoriteItemName)
        coVerify (exactly = 1){ testDAO.delete(fakeFoodFavoriteItemName) }
    }

}