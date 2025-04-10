package com.example.animeapi.data.modeldata

class AnimeResponse(val pagination: Pagination, val data: List<AnimeItem>)
data class Pagination(
    val last_visible_page: Int,
    val has_next_page: Boolean,
    val current_page: Int,
    val items: Items
)

data class Items(val count: Int, val total: Int, val per_page: Int)
data class AnimeItem(
    val mal_id: Int,
    val url: String,
    val images: Images,
    val trailer: Trailer,
    val approved: Boolean,
    val titles: List<Title>,
    val title: String,
    val title_english: String?,
    val title_japanese: String?,
    val title_synonyms: List<String>,
    val type: String,
    val source: String,
    val episodes: Int?,
    val status: String,
    val airing: Boolean,
    val aired: Aired,
    val duration: String,
    val rating: String,
    val score: Double?,
    val scored_by: Int,
    val rank: Int,
    val popularity: Int,
    val members: Int,
    val favorites: Int,
    val synopsis: String?,
    val season: String?,
    val year: Int?,
    val broadcast: Broadcast,
    val producers: List<SubItem>,
    val licensors: List<SubItem>,
    val studios: List<SubItem>,
    val genres: List<SubItem>,
    val explicit_genres: List<SubItem>,
    val themes: List<SubItem>,
    val demographics: List<SubItem>
)

data class Images(val jpg: ImageInfo, val webp: ImageInfo)
data class ImageInfo(
    val image_url: String,
    val small_image_url: String,
    val large_image_url: String
)

data class Trailer(
    val youtube_id: String?,
    val url: String?,
    val embed_url: String?,
    val images: TrailerImages?
)

data class TrailerImages(
    val image_url: String?,
    val small_image_url: String?,
    val medium_image_url: String?,
    val large_image_url: String?,
    val maximum_image_url: String?
)

data class Title(val type: String, val title: String)
data class Aired(val from: String?, val to: String?, val prop: Prop, val string: String)
data class Prop(val from: DatePart, val to: DatePart)
data class DatePart(val day: Int?, val month: Int?, val year: Int?)
data class Broadcast(
    val day: String?,
    val time: String?,
    val timezone: String?,
    val string: String?
)

data class SubItem(val mal_id: Int, val type: String, val name: String, val url: String)
