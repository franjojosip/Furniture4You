package com.fjjukic.furniture4you.ui.mock

import com.fjjukic.furniture4you.ui.cart.CartProduct
import com.fjjukic.furniture4you.ui.checkout.CheckoutViewState
import com.fjjukic.furniture4you.ui.checkout.DeliveryOption
import com.fjjukic.furniture4you.ui.checkout.PaymentInfo
import com.fjjukic.furniture4you.ui.checkout.PriceInfo
import com.fjjukic.furniture4you.ui.checkout.ShippingInfo
import com.fjjukic.furniture4you.ui.common.model.Product
import com.fjjukic.furniture4you.ui.home.HomeViewState
import com.fjjukic.furniture4you.ui.home.model.CategoryItem
import com.fjjukic.furniture4you.ui.notification.NotificationModel
import com.fjjukic.furniture4you.ui.notification.NotificationTag
import com.fjjukic.furniture4you.ui.productdetail.ProductDetail
import com.fjjukic.furniture4you.ui.productdetail.ProductDetailViewState
import com.fjjukic.furniture4you.ui.rating.RatingReviewViewState
import com.fjjukic.furniture4you.ui.rating.Review
import ht.ferit.fjjukic.foodlovers.R
import java.util.UUID

object MockRepository {
    fun getHomeState(): HomeViewState {
        return HomeViewState(
            getCategories(),
            getProducts()
        )
    }

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
        return ShippingInfo(
            fullName = "Bruno Fernandes",
            address = "25 rue Robert Latouche, Nice, 06200, Côte D’azur, France",
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

    fun getProductDetailState(): ProductDetailViewState {
        return ProductDetailViewState(getProductDetail())
    }

    fun getProductDetail(): ProductDetail {
        return ProductDetail(
            title = "Minimal Stand",
            description = "Minimal Stand is made of by natural wood. The design that is very simple and minimal. " +
                    "This is truly one of the best furnitures in any family for now. " +
                    "With 3 different colors, you can easily select the best match for your home.",
            imageUrl = "https://jysk.co.uk/dining-room/dining-sets/s361125-marstrand-hvidovre.jpg",
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
                R.drawable.black_simple_lamp
            ),
            Product(
                UUID.randomUUID().toString(),
                "Babylon Marble Table",
                "36.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.babylon_torrentno_marble_table
            ),
            Product(
                UUID.randomUUID().toString(),
                "Coffee Chair",
                "21.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.coffee_chair
            ),
            Product(
                UUID.randomUUID().toString(),
                "Makali Lounge Chair",
                "16.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.makali_lounge_chair
            ),
            Product(
                UUID.randomUUID().toString(),
                "Minimal Stand",
                "18.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.minimal_stand
            ),
            Product(
                UUID.randomUUID().toString(),
                "Office Chair",
                "34.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.office_chair
            ),
            Product(
                UUID.randomUUID().toString(),
                "Orine Table Lamp",
                "17.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.orine_table_lamp
            ),
            Product(
                UUID.randomUUID().toString(),
                "The Day Bed",
                "15.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.the_day_bed
            ),
            Product(
                UUID.randomUUID().toString(),
                "Utility stool",
                "18.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.utility_stool
            ),
            Product(
                UUID.randomUUID().toString(),
                "Kitchen Island",
                "34.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.wide_rolling_kitchen_island
            ),
            Product(
                UUID.randomUUID().toString(),
                "Minimal Desk",
                "50.00",
                "Let your room shine with the addition of this lavish 28-inch abstract table lamp. It has a Linen Drum shade that delivers bright ambient lighting. Adds a vibrant flair to any room with its black ceramic & wood finish. It has a 15-inch wide, ivory linen, drum lampshade that delivers soft lighting.",
                "",
                R.drawable.simple_desk
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

    fun getRatingReviewViewState(): RatingReviewViewState {
        return RatingReviewViewState(
            getProductDetail(),
            getReviews()
        )
    }

    fun getNotifications(): List<NotificationModel> {
        return listOf(
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec.",
                imageResId = R.drawable.minimal_stand,
                tag = NotificationTag(
                    "New",
                    R.color.notification_tag_new
                )
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                imageResId = R.drawable.black_simple_lamp
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                hideImage = true,
                tag = NotificationTag(
                    "HOT!",
                    R.color.notification_tag_hot
                )
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                imageResId = R.drawable.coffee_chair
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                imageResId = R.drawable.cypress_stool
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                imageResId = R.drawable.coffee_chair
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                imageResId = R.drawable.ellington_side_table
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                imageResId = R.drawable.black_simple_lamp
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                imageResId = R.drawable.corchet_pouf_ottoman
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                hideImage = true,
                tag = NotificationTag(
                    "New",
                    R.color.notification_tag_new
                )
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                imageResId = R.drawable.black_simple_lamp
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                imageResId = R.drawable.babylon_torrentno_marble_table
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                imageResId = R.drawable.darien_pouf
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                hideImage = true,
                tag = NotificationTag(
                    "HOT!",
                    R.color.notification_tag_hot
                )
            ),
            NotificationModel(
                title = "Your order #123456789 has been confirmed",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Turpis pretium et in arcu adipiscing nec. Turpis pretium et in arcu adipiscing nec.",
                imageResId = R.drawable.coffee_chair
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
}