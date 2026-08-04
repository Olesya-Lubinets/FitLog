package com.example.fitlog

import com.example.fitlog.data.model.DataSource
import com.example.fitlog.data.model.FoodByIDResponse
import com.example.fitlog.data.model.FoodDetailsState
import com.example.fitlog.data.model.FoodFavorite
import com.example.fitlog.data.model.FoodUiState
import com.example.fitlog.data.model.Servings
import com.example.fitlog.data.repository.FoodFavoriteRepository
import com.example.fitlog.data.repository.FoodRepository
import com.example.fitlog.data.repository.NoSuchItemException
import com.example.fitlog.ui.FoodViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FoodViewModelTests {
    lateinit var testFoodRepository: FoodRepository
    lateinit var testFoodFavoriteRepository: FoodFavoriteRepository
    lateinit var testFoodViewModel:FoodViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUP() {
        Dispatchers.setMain(testDispatcher)
        testFoodRepository = mockk<FoodRepository>()
        testFoodFavoriteRepository = mockk<FoodFavoriteRepository>()
        every { testFoodFavoriteRepository.foodFavoriteFlow } returns flowOf(emptyList())
        testFoodViewModel = FoodViewModel(testFoodRepository,testFoodFavoriteRepository)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getSearchedFood sets foodUIState as SuccessState with correct data and source when data comes from API`()= runTest {
        val fakeFoodList = FoodTestData.loadFoodSearchResponsefromMock()
        val fakeFoodListUI = fakeFoodList.foods.food.map { it.toUi(false) }
        coEvery { testFoodFavoriteRepository.getAllOnce() } returns emptyList()
        coEvery { testFoodRepository.getSearchedFood("Apple") }  returns fakeFoodList
        testFoodViewModel.getSearchedFood("Apple")
        assertEquals(FoodUiState.SuccessState(fakeFoodListUI,DataSource.API), testFoodViewModel.foodUiState.value)

    }

    @Test
    fun `getSearchedFood sets foodUIState as SuccessState with correct data and source when API is not responding,data from Favorites`()= runTest {
        val fakeListFavoriteFood = FoodTestData.loadFoodFavoriteListFromMock()
        val fakeListFavoriteFoodUi = fakeListFavoriteFood.map { it.toUi() }
        coEvery { testFoodRepository.getSearchedFood(any()) } throws Exception()
        coEvery { testFoodFavoriteRepository.getAllOnce() } returns fakeListFavoriteFood
        testFoodViewModel.getSearchedFood("Apple")
        assertEquals(FoodUiState.SuccessState(fakeListFavoriteFoodUi,DataSource.FAVORITE),testFoodViewModel.foodUiState.value)
    }

    @Test
    fun `getSearchedFood sets foodUIState as SuccessState with correct data and source when get from API and marks foodFavorite correctly`()= runTest {
        val fakeFoodList = FoodTestData.loadFoodSearchResponsefromMock()
        val fakeFavoriteList = FoodTestData.loadFoodFavoriteListFromMock()
        val listOfFavoritesIds = fakeFavoriteList.map { it.food_id }
        val fakeFoodListUI = fakeFoodList.foods.food.map { it.toUi(it.food_id in listOfFavoritesIds) }
        coEvery { testFoodRepository.getSearchedFood("Apple") }  returns fakeFoodList
        coEvery { testFoodFavoriteRepository.getAllOnce() } returns fakeFavoriteList
        testFoodViewModel.getSearchedFood("Apple")
        assertEquals(FoodUiState.SuccessState(fakeFoodListUI,DataSource.API), testFoodViewModel.foodUiState.value)
    }

    @Test
    fun `getSearchedFood sets foodUIState as Empty when repository returns NoSuchItemException`()= runTest {
        coEvery { testFoodRepository.getSearchedFood(any()) } throws NoSuchItemException("Nothing Found")
        testFoodViewModel.getSearchedFood("Apple")
        assertEquals(FoodUiState.Empty,testFoodViewModel.foodUiState.value)
    }

    @Test
    fun `getSearchFood sets foodUIState as Error when repository returns Exception and Favorites are empy`() = runTest {
        coEvery { testFoodRepository.getSearchedFood(any()) } throws Exception()
        coEvery { testFoodFavoriteRepository.getAllOnce() } returns emptyList<FoodFavorite>()
        testFoodViewModel.getSearchedFood("Apple")
        assertEquals(FoodUiState.ErrorSate("No response from API and empty favorites list"),testFoodViewModel.foodUiState.value)
    }

    @Test
    fun `getFoodByID set foodByIDUIState as Success when result get from API`() = runTest {
        val fakeRepositoryResponse = FoodByIDResponse(FoodTestData.loadMockFoodItemFromFile())
        val expectedSuccessData = fakeRepositoryResponse.food.toUI()
        coEvery { testFoodRepository.getFoodByID(1641) } returns fakeRepositoryResponse
        testFoodViewModel.getFoodByID(1641)
        assertEquals(FoodDetailsState.Success(expectedSuccessData),testFoodViewModel.foodByIDUIState.value)
    }

    @Test
    fun `getFoodByID set foodByIDUIState as Success when result get from Favorites`() = runTest {
        coEvery { testFoodRepository.getFoodByID(5) } throws Exception()
        val fakeFoodFavoriteRepositoryResponse =  FoodTestData.getOneFavoriteFromMock()
        val expectedViewModelData = fakeFoodFavoriteRepositoryResponse.toUi()
        coEvery {  testFoodFavoriteRepository.getById(5)} returns fakeFoodFavoriteRepositoryResponse
        testFoodViewModel.getFoodByID(5)
        assertEquals(FoodDetailsState.Success(expectedViewModelData),testFoodViewModel.foodByIDUIState.value)
    }

    @Test
    fun `getFoodByID sets foodByIDUIState as Error`() = runTest {
        coEvery { testFoodRepository.getFoodByID(any()) } throws Exception()
        coEvery { testFoodFavoriteRepository.getById(any()) } returns null
        testFoodViewModel.getFoodByID(1)
        assertEquals(FoodDetailsState.Error("Error fetching food with ID and it doesn't exist in favorites"),testFoodViewModel.foodByIDUIState.value)
    }

    @Test
    fun `toggleFood inserts new FoodFavorite is food isFavorite is false`() = runTest {
        val foodItem = FoodTestData.loadMockFoodItemFromFile().toUI()
        val testServings = FoodTestData.getOneFavoriteFromMock().servings
        val foodItemFavorite = foodItem.toFavorite(servings = testServings)

        coEvery { testFoodFavoriteRepository.insert(any()) } just Runs
        coEvery { testFoodRepository.getFoodServingsByID(any()) } returns testServings

        testFoodViewModel.toggleFood(foodItem)

        coVerify(exactly = 1) { testFoodFavoriteRepository.insert(foodItemFavorite) }
    }

    @Test
    fun `toggleFood deletes existing FoodFavorite if food isFavorite is true`() = runTest {
        val foodItemToDelete = FoodTestData.getOneFavoriteFromMock().toUi()
        coEvery { testFoodFavoriteRepository.delete(any()) } just Runs
        testFoodViewModel.toggleFood(foodItemToDelete)
        coVerify (exactly = 1){ testFoodFavoriteRepository.delete(foodItemToDelete.food_name) }
    }

    @Test
    fun `toggleFood updates isFavorite correctly when isFavorite=false`() = runTest{
      val fakeFoodItemList = FoodTestData.loadFoodSearchResponsefromMock()
        coEvery { testFoodRepository.getSearchedFood(any()) } returns fakeFoodItemList
        coEvery { testFoodFavoriteRepository.getAllOnce() } returns emptyList()
        testFoodViewModel.getSearchedFood("apple")

        val foodItem = (testFoodViewModel.foodUiState.value  as? FoodUiState.SuccessState)?.data?.first()!!
        val testServings = FoodTestData.getOneFavoriteFromMock().servings


        coEvery { testFoodFavoriteRepository.insert(any()) } just Runs
        coEvery { testFoodRepository.getFoodServingsByID(any()) } returns testServings

        testFoodViewModel.toggleFood(foodItem)
        advanceUntilIdle()
        val updatedListFirstItem = (testFoodViewModel.foodUiState.value  as? FoodUiState.SuccessState)?.data?.first()!!

        assertEquals(!foodItem.isFavorite,updatedListFirstItem.isFavorite)
     }

    @Test
    fun `toggleFood updates isFavorite correctly when isFavorite=true`() = runTest{
        val fakeFoodFavoriteItemList = FoodTestData.loadFoodFavoriteListFromMock()
        coEvery { testFoodRepository.getSearchedFood(any()) } throws Exception()
        coEvery { testFoodFavoriteRepository.getAllOnce() } returns fakeFoodFavoriteItemList
        testFoodViewModel.getSearchedFood("apple")

        val foodItem = (testFoodViewModel.foodUiState.value  as? FoodUiState.SuccessState)?.data?.first()!!

        coEvery { testFoodFavoriteRepository.delete(any()) } just Runs

        testFoodViewModel.toggleFood(foodItem)
        val updatedListFirstItem = (testFoodViewModel.foodUiState.value  as? FoodUiState.SuccessState)?.data?.first()!!

        assertEquals(!foodItem.isFavorite,updatedListFirstItem.isFavorite)
    }

}