package com.example.fitlog

import com.example.fitlog.data.api.foodAPI.FoodApiService
import com.example.fitlog.data.model.Food
import com.example.fitlog.data.model.FoodByIDResponse
import com.example.fitlog.data.model.FoodSearchResponse
import com.example.fitlog.data.model.FoodsContainer
import com.example.fitlog.data.repository.FoodRepository
import com.example.fitlog.data.repository.NoSuchItemException
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import kotlin.test.assertFailsWith


class FoodRepositoryTests {

    lateinit var testFoodRepository: FoodRepository
    lateinit var mockFoodApi:FoodApiService

    @Before
    fun setupRepository() {
        mockFoodApi = mockk<FoodApiService>()
        testFoodRepository = FoodRepository(mockFoodApi)
    }

    @Test
    fun `getSearchedFood returns FoodSearchResponse when API response`()= runTest() {
        val query = "Soup"
        val fakeResponse = FoodTestData.loadFoodSearchResponsefromMock()
        coEvery { mockFoodApi.findFood(query) } returns fakeResponse

        val result = testFoodRepository.getSearchedFood(query)
        assertEquals(fakeResponse,result)
    }

    @Test
    fun `getSearchedFood throws NoSuchItemException when API respondse with empty list`() = runTest {

        val fakeAPIResponse = FoodSearchResponse(FoodsContainer(emptyList<Food>(),0,0,0))
        coEvery { mockFoodApi.findFood(any()) } returns fakeAPIResponse
        assertFailsWith<NoSuchItemException> { testFoodRepository.getSearchedFood("anything")}
    }

    @Test
    fun `getSearchedFood throws Exception when API throws Exception`() = runTest {
        coEvery { mockFoodApi.findFood(any()) } throws Exception()
        assertFailsWith<Exception> { testFoodRepository.getSearchedFood("anything")}
    }

    @Test
    fun `getFoodByID returns FoodByIDResponse`() = runTest {
        val fakeFoodItem = FoodTestData.loadMockFoodItemFromFile()
        val fakeAPIResponse = FoodByIDResponse(fakeFoodItem)
        coEvery { mockFoodApi.getByID(1641) } returns fakeAPIResponse
        val result = testFoodRepository.getFoodByID(1641).food
        assertEquals(fakeAPIResponse.food,result)
    }

    @Test
    fun `getFoodByID throws Exception if id is not valid`() = runTest {
        coEvery { mockFoodApi.getByID(99999) } throws Exception()
        assertFailsWith<Exception> { testFoodRepository.getFoodByID(99999)}
    }

    @Test
    fun `getFoodServingsByID returns Servings when API response`() = runTest {
        val fakeFoodItem = FoodTestData.loadMockFoodItemFromFile()
        val fakeAPIResponse = FoodByIDResponse(fakeFoodItem)
        coEvery { mockFoodApi.getByID(1641)} returns fakeAPIResponse
        val result = testFoodRepository.getFoodServingsByID(1641)
        assertEquals(fakeFoodItem.servings,result)
    }
}