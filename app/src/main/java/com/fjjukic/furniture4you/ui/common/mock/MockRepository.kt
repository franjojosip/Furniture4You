package com.fjjukic.furniture4you.ui.common.mock

import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.checkout.CheckoutViewState
import com.fjjukic.furniture4you.ui.checkout.DeliveryOption
import com.fjjukic.furniture4you.ui.checkout.PaymentInfo
import com.fjjukic.furniture4you.ui.checkout.PriceInfo
import com.fjjukic.furniture4you.ui.checkout.ShippingInfo
import com.fjjukic.furniture4you.ui.common.model.CartProduct
import com.fjjukic.furniture4you.ui.common.model.CategoryItem
import com.fjjukic.furniture4you.ui.common.model.DisplayType
import com.fjjukic.furniture4you.ui.common.model.MyReviewModel
import com.fjjukic.furniture4you.ui.common.model.NotificationModel
import com.fjjukic.furniture4you.ui.common.model.NotificationTag
import com.fjjukic.furniture4you.ui.common.model.Order
import com.fjjukic.furniture4you.ui.common.model.PaymentCard
import com.fjjukic.furniture4you.ui.common.model.Product
import com.fjjukic.furniture4you.ui.common.model.SearchCategory
import com.fjjukic.furniture4you.ui.common.model.SearchCategoryCollection
import com.fjjukic.furniture4you.ui.common.model.SearchSuggestionGroup
import com.fjjukic.furniture4you.ui.components.MenuItem
import com.fjjukic.furniture4you.ui.order.OrderStatus
import com.fjjukic.furniture4you.ui.productdetail.ProductColorOption
import com.fjjukic.furniture4you.ui.productdetail.ProductDetail
import com.fjjukic.furniture4you.ui.rating.Review
import com.fjjukic.furniture4you.ui.search.SearchScreenState
import com.fjjukic.furniture4you.ui.setting.PersonalInformation
import com.fjjukic.furniture4you.ui.setting.SettingsViewState
import java.util.UUID
import kotlin.random.Random

object MockRepository {

    fun getCheckoutViewState(): CheckoutViewState {
        val deliveryOptions = getDeliveryOptions()

        return CheckoutViewState(
            selectedDelivery = deliveryOptions.first(),
            deliveryOptions = deliveryOptions,
            shippingInfo = getShippingInfo(),
            priceInfo = getPriceInfo(),
            paymentInfo = getPaymentInfo()
        )
    }

    fun getShippingInfo(): ShippingInfo {
        return getShippingAddresses().first()
    }

    fun getShippingAddresses(): List<ShippingInfo> {
        val country = getCountries().first()
        val city = getCities().first()
        return listOf(
            ShippingInfo(
                fullName = "Bruno Fernandes",
                address = "25 rue Robert Latouche, Nice, 06200, Côte D’azur, France",
                zipCode = "12345",
                country = country,
                city = city,
                isDefault = true
            ),
            ShippingInfo(
                fullName = "Antonio Vivaldi",
                address = "25 rue Robert Latouche, Nice, 06200, Côte D’azur, France",
                zipCode = "12345",
                country = country,
                city = city,
            ),
            ShippingInfo(
                fullName = "Mark Johnson",
                address = "25 rue Robert Latouche, Nice, 06200, Côte D’azur, France",
                zipCode = "12345",
                country = country,
                city = city,
            )
        )
    }

    fun getCountries(): List<MenuItem.Country> {
        return listOf(
            MenuItem.Country(id = "0", name = "United States"),
            MenuItem.Country(id = "1", name = "China"),
            MenuItem.Country(id = "2", name = "India"),
            MenuItem.Country(id = "3", name = "Brazil"),
            MenuItem.Country(id = "4", name = "Russia"),
            MenuItem.Country(id = "5", name = "Japan"),
            MenuItem.Country(id = "6", name = "Germany"),
            MenuItem.Country(id = "7", name = "United Kingdom"),
            MenuItem.Country(id = "8", name = "France"),
            MenuItem.Country(id = "9", name = "Canada"),
            MenuItem.Country(id = "10", name = "Australia"),
            MenuItem.Country(id = "11", name = "South Africa"),
            MenuItem.Country(id = "12", name = "Mexico"),
            MenuItem.Country(id = "13", name = "South Korea"),
            MenuItem.Country(id = "14", name = "Saudi Arabia"),
            MenuItem.Country(id = "15", name = "Turkey"),
            MenuItem.Country(id = "16", name = "Indonesia"),
            MenuItem.Country(id = "17", name = "Nigeria"),
            MenuItem.Country(id = "18", name = "Argentina"),
            MenuItem.Country(id = "19", name = "Egypt")
        ).sortedBy { it.name }
    }

    fun getCities(): List<MenuItem.City> {
        return listOf(
            MenuItem.City(name = "New York", countryId = "0"), // United States
            MenuItem.City(name = "Beijing", countryId = "1"), // China
            MenuItem.City(name = "New Delhi", countryId = "2"), // India
            MenuItem.City(name = "Rio de Janeiro", countryId = "3"), // Brazil
            MenuItem.City(name = "Moscow", countryId = "4"), // Russia
            MenuItem.City(name = "Tokyo", countryId = "5"), // Japan
            MenuItem.City(name = "Berlin", countryId = "6"), // Germany
            MenuItem.City(name = "London", countryId = "7"), // United Kingdom
            MenuItem.City(name = "Paris", countryId = "8"), // France
            MenuItem.City(name = "Toronto", countryId = "9"), // Canada
            MenuItem.City(name = "Sydney", countryId = "10"), // Australia
            MenuItem.City(name = "Johannesburg", countryId = "11"), // South Africa
            MenuItem.City(name = "Mexico City", countryId = "12"), // Mexico
            MenuItem.City(name = "Seoul", countryId = "13"), // South Korea
            MenuItem.City(name = "Riyadh", countryId = "14"), // Saudi Arabia
            MenuItem.City(name = "Istanbul", countryId = "15"), // Turkey
            MenuItem.City(name = "Jakarta", countryId = "16"), // Indonesia
            MenuItem.City(name = "Lagos", countryId = "17"), // Nigeria
            MenuItem.City(name = "Buenos Aires", countryId = "18"), // Argentina
            MenuItem.City(name = "Cairo", countryId = "19") // Egypt
        )
    }

    fun getPriceInfo(): PriceInfo {
        return PriceInfo(
            orderPrice = getCartProducts().sumOf { it.product.price.toDouble() },
            deliveryPrice = 6.00
        )
    }

    fun getPaymentInfo(): PaymentInfo {
        return PaymentInfo(
            cardHolder = "Bruno Fernandes",
            cardNumber = "1234567812343444",
            cvv = "123",
            expDate = "1223"
        )
    }

    fun getProductDetail(): ProductDetail {
        return ProductDetail(
            title = "Minimal Stand",
            description = "Minimal Stand is made of by natural wood. The design that is very simple and minimal. " +
                    "This is truly one of the best furnitures in any family for now. " +
                    "With 3 different colors, you can easily select the best match for your home.",
            imageUrl = "https://www.ikea.com/hr/hr/images/products/lack-stolic-bijela__0702214_pe724347_s5.jpg",
            productOptions = listOf(
                ProductColorOption(
                    0,
                    "https://www.ikea.com/hr/hr/images/products/lack-stolic-bijela__0702214_pe724347_s5.jpg"
                ),
                ProductColorOption(
                    1,
                    "https://www.ikea.com/hr/hr/images/products/lack-stolic-crno-smeda__0836233_pe601379_s5.jpg"
                ),
                ProductColorOption(
                    2,
                    "https://www.ikea.com/hr/hr/images/products/lack-stolic-efekt-bijelo-bajcanog-hrasta__0837100_pe709586_s5.jpg"
                ),
            ),
            price = 49.99,
            reviews = 43
        )
    }

    fun getCategories(): List<CategoryItem> {
        return listOf(
            CategoryItem(
                title = "Popular",
                imageResId = R.drawable.ic_star
            ),
            CategoryItem(
                title = "Chair",
                imageResId = R.drawable.ic_chair
            ),
            CategoryItem(
                title = "Table",
                imageResId = R.drawable.ic_table
            ),
            CategoryItem(
                title = "Sofa",
                imageResId = R.drawable.ic_sofa
            ),
            CategoryItem(
                title = "Bed",
                imageResId = R.drawable.ic_bed
            ),
            CategoryItem(
                title = "Lamp",
                imageResId = R.drawable.ic_lamp
            )
        )
    }

    fun getSearchScreenState(): SearchScreenState {
        return SearchScreenState(
            products = getProducts(),
            filters = getCategories(),
            searchSuggestions = getSearchSuggestions(),
            searchCategoryCollections = getSearchCategoryCollections(),
            displayType = DisplayType.Categories(categories = getSearchCategoryCollections())
        )
    }

    fun getSearchSuggestions(): List<SearchSuggestionGroup> {
        return listOf(
            SearchSuggestionGroup(
                id = 0L,
                name = "Recent searches",
                suggestions = listOf(
                    "Black Simple",
                    "Makali Lounge"
                )
            ),
            SearchSuggestionGroup(
                id = 1L,
                name = "Popular searches",
                suggestions = listOf(
                    "Minimal Stand",
                    "Gluten Free",
                    "Paleo",
                    "Vegan",
                    "Vegitarian",
                    "Whole30"
                )
            )
        )
    }

    fun getSearchCategoryCollections(): List<SearchCategoryCollection> {
        return listOf(
            SearchCategoryCollection(
                id = 0L,
                name = "Categories",
                categories = listOf(
                    SearchCategory(
                        name = "Table",
                        imageUrl = "https://assets.nickscali.com/media/wysiwyg/pages/category_imgs/Bedside-Table.jpg"
                    ),
                    SearchCategory(
                        name = "Beds",
                        imageUrl = "https://www.familyhandyman.com/wp-content/uploads/2023/09/GettyImages-1393799248.jpg?fit=700%2C467"
                    ),
                    SearchCategory(
                        name = "Dining set",
                        imageUrl = "https://assets.nickscali.com/media/wysiwyg/pages/category_imgs/Dining-Chair.jpg"
                    ),
                    SearchCategory(
                        name = "Accent furniture",
                        imageUrl = "https://lirp.cdn-website.com/30f0bf8a/dms3rep/multi/opt/Shutterstock_2092406098+%281%29-640w.jpg"
                    ),
                    SearchCategory(
                        name = "Industrial furniture",
                        imageUrl = "https://lirp.cdn-website.com/30f0bf8a/dms3rep/multi/opt/shutterstock_2045538812-640w.jpg"
                    )
                )
            ),
            SearchCategoryCollection(
                id = 1L,
                name = "Materials",
                categories = listOf(
                    SearchCategory(
                        name = "Wood",
                        imageUrl = "https://t2.gstatic.com/licensed-image?q=tbn:ANd9GcQAinrw9kPCbeJ27dVKlyh0zk7INFnC_m9HkxUrt39deixLaldvGsnO7-BVaFqQLBt3"
                    ),
                    SearchCategory(
                        name = "Metal",
                        imageUrl = "https://blog.inspireq.com/wp-content/uploads/2021/09/E569-70_LS3_LS3_16-11-15.jpg"
                    ),
                    SearchCategory(
                        name = "Plastic",
                        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/b/b2/Plastic_household_items.jpg"
                    ),
                    SearchCategory(
                        name = "Glass",
                        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/43/Fassade_Wilhelmstrasse_65%2C_Berlin-Mitte%2C_160417%2C_ako.jpg/800px-Fassade_Wilhelmstrasse_65%2C_Berlin-Mitte%2C_160417%2C_ako.jpg"
                    ),
                    SearchCategory(
                        name = "Leather",
                        imageUrl = "https://t3.gstatic.com/licensed-image?q=tbn:ANd9GcRYlo7fDUDiot9vVLlqNeGCHlJXUe12GL_wCbnOj0ZB5BiR7kKcK8Vwd71T12IJCgip"
                    ),
                    SearchCategory(
                        name = "Fabric",
                        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/1/17/Flores_y_colores_hechos_a_mano.jpg"
                    )
                )
            )
        )
    }

    fun getCartProducts(): List<CartProduct> {
        return getProducts().take(4).map {
            CartProduct(it)
        }
    }

    fun getCartProduct(): CartProduct {
        return CartProduct(getProducts().first())
    }

    fun getProducts(): List<Product> {
        return listOf(
            Product(
                UUID.randomUUID().toString(),
                "Black Simple Lamp",
                "12.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.img_black_simple_lamp
            ),
            Product(
                UUID.randomUUID().toString(),
                "Babylon Marble Table",
                "36.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.img_babylon_torrentno_marble_table
            ),
            Product(
                UUID.randomUUID().toString(),
                "Coffee Chair",
                "21.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.img_coffee_chair
            ),
            Product(
                UUID.randomUUID().toString(),
                "Makali Lounge Chair",
                "16.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.img_makali_lounge_chair
            ),
            Product(
                UUID.randomUUID().toString(),
                "Minimal Stand",
                "18.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.img_white_minimal_stand
            ),
            Product(
                UUID.randomUUID().toString(),
                "Office Chair",
                "34.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.img_office_chair
            ),
            Product(
                UUID.randomUUID().toString(),
                "Orine Table Lamp",
                "17.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.img_orine_table_lamp
            ),
            Product(
                UUID.randomUUID().toString(),
                "The Day Bed",
                "15.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.img_the_day_bed
            ),
            Product(
                UUID.randomUUID().toString(),
                "Utility stool",
                "18.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.img_utility_stool
            ),
            Product(
                UUID.randomUUID().toString(),
                "Kitchen Island",
                "34.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.img_wide_rolling_kitchen_island
            ),
            Product(
                UUID.randomUUID().toString(),
                "Minimal Desk",
                "50.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.img_simple_desk
            )
        )
    }

    fun getDeliveryOptions(): List<DeliveryOption> {
        return listOf(
            DeliveryOption(
                iconResId = R.drawable.ic_delivery,
                description = "Fast (2–3 days)",
                price = 6.00
            ),
            DeliveryOption(
                iconResId = R.drawable.ic_delivery,
                description = "Normal (4–5 days)",
                price = 4.50
            ),
            DeliveryOption(
                iconResId = R.drawable.ic_delivery,
                description = "Slow (7–10 days)",
                price = 3.00
            )
        )
    }

    fun getNotifications(): List<NotificationModel> {
        return listOf(
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec.",
                imageResId = R.drawable.img_white_minimal_stand,
                tag = NotificationTag(
                    "New",
                    R.color.color_tag_new
                )
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                imageResId = R.drawable.img_black_simple_lamp
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                hideImage = true,
                tag = NotificationTag(
                    "HOT!",
                    R.color.color_tag_hot
                )
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                imageResId = R.drawable.img_coffee_chair
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                imageResId = R.drawable.img_cypress_stool
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                imageResId = R.drawable.img_coffee_chair
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                imageResId = R.drawable.img_ellington_side_table
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                imageResId = R.drawable.img_black_simple_lamp
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                imageResId = R.drawable.img_corchet_pouf_ottoman
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                hideImage = true,
                tag = NotificationTag(
                    "New",
                    R.color.color_tag_new
                )
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                imageResId = R.drawable.img_black_simple_lamp
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                imageResId = R.drawable.img_babylon_torrentno_marble_table
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                imageResId = R.drawable.img_darien_pouf
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                hideImage = true,
                tag = NotificationTag(
                    "HOT!",
                    R.color.color_tag_hot
                )
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                imageResId = R.drawable.img_coffee_chair
            ),
        )
    }

    fun getReviews(): List<Review> {
        return listOf(
            Review(
                name = "Bruno Fernandes",
                description = "Nice Furniture with good delivery. The delivery time is very fast. Then products look like exactly the picture in the app. Besides, color is also the same and quality is very good despite very cheap price",
                date = "20/03/2023",
                imageResId = R.drawable.img_person_one
            ),
            Review(
                name = "Tracy Jay",
                description = "Nice Furniture with good delivery. The delivery time is very fast. Then products look like exactly the picture in the app. Besides, color is also the same and quality is very good despite very cheap price",
                date = "20/03/2023",
                imageResId = R.drawable.img_person_two
            ),
            Review(
                name = "Martina Herrera",
                description = "Nice Furniture with good delivery. The delivery time is very fast. Then products look like exactly the picture in the app. Besides, color is also the same and quality is very good despite very cheap price",
                date = "20/03/2023",
                imageResId = R.drawable.img_person_three
            )
        )
    }

    fun getMyReviews(): List<MyReviewModel> {
        return getProducts().map {
            MyReviewModel(
                title = it.title,
                price = it.price,
                date = "25/03/2024",
                description = "Nice Furniture with good delivery. The delivery time is very fast. Then products look like exactly the picture in the app. Besides, color is also the same and quality is very good despite very cheap price",
                imageResId = it.imageResId
            )
        }
    }

    fun getSettingsViewState(): SettingsViewState {
        return SettingsViewState(
            personalInformation = PersonalInformation(
                name = "Bruno Fernandes",
                email = "bruno2304@gmail.com",
            ),
            password = "bruno12345",
            salesState = true,
            newArrivalsState = false,
            deliveryStatusChangeState = false
        )
    }

    fun getPersonalInformation(): PersonalInformation {
        return PersonalInformation(
            name = "Bruno Fernandes",
            email = "bruno2304@gmail.com",
        )
    }

    fun getPaymentCards(): List<PaymentCard> {
        return listOf(
            PaymentCard(
                cardNumber = "1234123412343947",
                cardHolder = "Jimmy Cutler",
                cvv = "154",
                expDate = "0327",
                vendorLogoResId = R.drawable.ic_mastercard_tinted,
                isDefault = true
            ),
            PaymentCard(
                cardNumber = "1234123412343191",
                cardHolder = "Ben Parker",
                cvv = "184",
                expDate = "0723",
                vendorLogoResId = R.drawable.ic_visa,
            ),
        )
    }

    fun getMockCard(): PaymentCard {
        return PaymentCard(
            cardNumber = "123412341234XXXX",
            cardHolder = "XXXXXX",
            cvv = "XXX",
            expDate = "XXXX",
            vendorLogoResId = R.drawable.ic_mastercard_tinted,
            isDefault = true
        )
    }

    private fun generateRandom10DigitString(): String {
        val digits = (1..9).map { Random.nextInt(0, 10) }
        return digits.joinToString("")
    }

    fun getOrders(): List<Order> {
        return List(10) {
            Order(
                number = generateRandom10DigitString(),
                date = "20/03/2020",
                quantity = 3,
                amount = 150.00,
                status = OrderStatus.Delivered
            )
        }
            .plus(
                List(10) {
                    Order(
                        number = "238562312",
                        date = "20/03/2020",
                        quantity = 3,
                        amount = 150.00,
                        status = OrderStatus.Canceled
                    )
                })
            .plus(
                List(10) {
                    Order(
                        number = "238562312",
                        date = "20/03/2020",
                        quantity = 3,
                        amount = 150.00,
                        status = OrderStatus.Processing
                    )
                }
            )
    }
}