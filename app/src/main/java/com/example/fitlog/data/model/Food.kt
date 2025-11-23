package com.example.fitlog.data.model


sealed class Food {
    abstract val food_name: String
    abstract val serving_unit: String
    abstract val serving_qty: Double
    abstract val photo: Photo
    abstract val locale: String
}

data class CommonFood(
    override val food_name: String,
    override val serving_unit: String,
    override val serving_qty: Double,
    override val photo: Photo,
    override val locale: String,

    val tag_name: String,
    val common_type: String?,
    val tag_id: String
) : Food()

data class BrandedFood(
    override val food_name: String,
    override val serving_unit: String,
    override val serving_qty: Double,
    override val photo: Photo,
    override val locale: String,

    val nix_brand_id: String,
    val brand_name_item_name: String,
    val nf_calories: Int,
    val brand_name: String,
    val region: Int,
    val brand_type: Int,
    val nix_item_id: String
) : Food()

data class FoodSearchResponse(
    val common:List<CommonFood>,
    val branded:List<BrandedFood>
)