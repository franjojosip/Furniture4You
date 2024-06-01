package com.fjjukic.furniture4you.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import com.fjjukic.furniture4you.ui.common.model.CategoryItem
import com.fjjukic.furniture4you.ui.common.model.Product
import com.fjjukic.furniture4you.ui.common.model.SearchCategory
import com.fjjukic.furniture4you.ui.common.model.SearchCategoryCollection
import com.fjjukic.furniture4you.ui.common.model.SearchSuggestionGroup
import com.fjjukic.furniture4you.ui.common.showFeatureNotAvailable
import com.fjjukic.furniture4you.ui.home.CategoryFilter
import com.fjjukic.furniture4you.ui.theme.NunitoSansTypography
import com.fjjukic.furniture4you.ui.theme.gelatioFamily
import com.fjjukic.furniture4you.ui.theme.nunitoSansFamily
import ht.ferit.fjjukic.foodlovers.R
import kotlin.math.max

@Preview(showBackground = true)
@Composable
fun SearchResultsPreview() {
    SearchResults(
        searchResults = MockRepository.getProducts(),
        categories = MockRepository.getCategories(),
        onProductClick = {},
        onCartClick = {}
    )
}

@Composable
fun SearchResults(
    searchResults: List<Product>,
    categories: List<CategoryItem>,
    onProductClick: (String) -> Unit,
    onCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember {
        mutableIntStateOf(0)
    }
    Column(modifier) {
        CategoryFilter(categories, selectedCategory, { category ->
            selectedCategory = category
        })
        Text(
            text = stringResource(R.string.label_search_count, searchResults.size),
            fontSize = 20.sp,
            fontFamily = gelatioFamily,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = R.color.color_dark_gray),
            modifier = Modifier
                .padding(top = 8.dp, bottom = 12.dp)
                .padding(horizontal = 20.dp)
        )
        LazyColumn {
            items(searchResults) { product ->
                SearchItem(
                    title = product.title,
                    price = product.price,
                    imageResId = product.imageResId,
                    onProductClick = {
                        onProductClick(product.id)
                    },
                    onCartClick = onCartClick,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                )
            }
        }
    }
}

@Composable
private fun SearchItem(
    title: String,
    price: String,
    imageResId: Int,
    onProductClick: () -> Unit,
    onCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onProductClick
            )
    ) {
        Image(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp)),
            painter = painterResource(id = imageResId),
            contentDescription = stringResource(R.string.content_desc_product),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .padding(start = 21.dp)
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontFamily = gelatioFamily,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.color_light_gray),
            )
            Text(
                text = stringResource(id = R.string.title_product_price, price),
                color = colorResource(id = R.color.color_dark_gray),
                style = NunitoSansTypography.titleSmall,
                modifier = Modifier.padding(top = 6.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            CartItem(
                onItemSelect = onCartClick,
                modifier = Modifier
                    .wrapContentSize(),
                itemColor = colorResource(id = R.color.color_medium_gray),
                itemBackground = colorResource(id = R.color.bg_favorite_cart_item),
                itemRadius = 12.dp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SuggestionsPreview() {
    SearchSuggestions(
        suggestions = MockRepository.getSearchSuggestions(),
        onSuggestionSelect = {}
    )
}

@Composable
fun SearchSuggestions(
    suggestions: List<SearchSuggestionGroup>,
    onSuggestionSelect: (String) -> Unit
) {
    LazyColumn {
        suggestions.forEach { suggestionGroup ->
            item {
                SuggestionHeader(suggestionGroup.name)
            }
            items(suggestionGroup.suggestions) { suggestion ->
                Suggestion(
                    suggestion = suggestion,
                    onSuggestionSelect = onSuggestionSelect,
                    modifier = Modifier.fillParentMaxWidth()
                )
            }
            item {
                Spacer(Modifier.height(4.dp))
            }
        }
    }
}

@Composable
private fun SuggestionHeader(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = name,
        fontSize = 18.sp,
        fontFamily = nunitoSansFamily,
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.color_dark_gray),
        modifier = modifier
            .heightIn(min = 56.dp)
            .padding(horizontal = 24.dp, vertical = 4.dp)
            .wrapContentHeight()
    )
}

@Composable
private fun Suggestion(
    suggestion: String,
    onSuggestionSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = suggestion,
        fontSize = 16.sp,
        fontFamily = nunitoSansFamily,
        fontWeight = FontWeight.Medium,
        color = colorResource(id = R.color.color_gray),
        modifier = modifier
            .heightIn(min = 48.dp)
            .clickable { onSuggestionSelect(suggestion) }
            .padding(start = 24.dp)
            .wrapContentSize(Alignment.CenterStart)
    )
}

@Preview(showBackground = true)
@Composable
fun NoResultsPreview() {
    NoResults(query = stringResource(id = R.string.placeholder_name))
}

@Composable
fun NoResults(
    query: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize()
            .padding(24.dp)
    ) {
        Image(
            painterResource(R.drawable.img_no_result),
            contentDescription = null
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(
                id = R.string.title_no_result,
                query
            ),
            fontSize = 16.sp,
            fontFamily = gelatioFamily,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = R.color.color_dark_gray),
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = stringResource(id = R.string.label_try_different_search),
            fontSize = 16.sp,
            fontFamily = gelatioFamily,
            fontWeight = FontWeight.Thin,
            color = colorResource(id = R.color.color_gray),
        )
    }
}


@Composable
fun SearchCategories(
    categories: List<SearchCategoryCollection>
) {
    LazyColumn {
        itemsIndexed(categories) { index, collection ->
            SearchCategoryCollection(collection, index)
        }
    }
    Spacer(Modifier.height(8.dp))
}

@Composable
private fun SearchCategoryCollection(
    collection: SearchCategoryCollection,
    index: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = collection.name,
            fontSize = 18.sp,
            fontFamily = nunitoSansFamily,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.color_dark_gray),
            modifier = Modifier
                .heightIn(min = 56.dp)
                .padding(horizontal = 24.dp, vertical = 4.dp)
                .wrapContentHeight()
        )
        VerticalGrid(Modifier.padding(horizontal = 16.dp)) {
            val gradient = when (index % 2) {
                0 -> listOf(
                    colorResource(id = R.color.color_gradient1_start),
                    colorResource(id = R.color.color_gradient1_end),
                )

                else -> listOf(
                    colorResource(id = R.color.color_gradient2_start),
                    colorResource(id = R.color.color_gradient2_end),
                )
            }
            collection.categories.forEach { category ->
                SearchCategoryItem(
                    category = category,
                    gradient = gradient,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        Spacer(Modifier.height(4.dp))
    }
}

private val MinImageSize = 134.dp
private val CategoryShape = RoundedCornerShape(10.dp)
private const val CategoryTextProportion = 0.55f

@Composable
private fun SearchCategoryItem(
    category: SearchCategory,
    gradient: List<Color>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Layout(
        modifier = modifier
            .aspectRatio(1.45f)
            .shadow(elevation = 3.dp, shape = CategoryShape)
            .clip(CategoryShape)
            .background(Brush.horizontalGradient(gradient))
            .clickable {
                showFeatureNotAvailable(context)
            },
        content = {
            Text(
                text = category.name,
                fontSize = 16.sp,
                fontFamily = gelatioFamily,
                color = colorResource(id = R.color.color_dark_gray),
                modifier = Modifier
                    .padding(4.dp)
                    .padding(start = 8.dp)
            )
            Surface(
                shape = CircleShape
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(category.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.img_no_result),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }
        }
    ) { measurables, constraints ->
        // Text given a set proportion of width (which is determined by the aspect ratio)
        val textWidth = (constraints.maxWidth * CategoryTextProportion).toInt()
        val textPlaceable = measurables[0].measure(Constraints.fixedWidth(textWidth))

        // Image is sized to the larger of height of item, or a minimum value
        // i.e. may appear larger than item (but clipped to the item bounds)
        val imageSize = max(MinImageSize.roundToPx(), constraints.maxHeight)
        val imagePlaceable = measurables[1].measure(Constraints.fixed(imageSize, imageSize))
        layout(
            width = constraints.maxWidth,
            height = constraints.minHeight
        ) {
            textPlaceable.placeRelative(
                x = 0,
                y = (constraints.maxHeight - textPlaceable.height) / 2 // centered
            )
            imagePlaceable.placeRelative(
                // image is placed to end of text i.e. will overflow to the end (but be clipped)
                x = textWidth,
                y = (constraints.maxHeight - imagePlaceable.height) / 2 // centered
            )
        }
    }
}

@Preview
@Composable
private fun SearchCategoryPreview() {
    SearchCategoryItem(
        category = SearchCategory(
            name = stringResource(id = R.string.placeholder_name),
            imageUrl = ""
        ),
        gradient = listOf(
            colorResource(id = R.color.color_gradient1_start),
            colorResource(id = R.color.color_gradient1_end),
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchCategoriesPreview() {
    SearchCategories(MockRepository.getSearchCategoryCollections())
}
