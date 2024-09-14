package ceui.pixiv.ui.user.following

import ceui.lisa.utils.Params
import ceui.loxia.Client
import ceui.loxia.Illust
import ceui.loxia.IllustResponse
import ceui.loxia.TrendingTag
import ceui.loxia.TrendingTagsResponse
import ceui.loxia.UserPreview
import ceui.loxia.UserPreviewResponse
import ceui.pixiv.ui.common.DataSource
import ceui.pixiv.ui.common.IllustCardHolder
import ceui.pixiv.ui.common.ResponseStore
import ceui.pixiv.ui.user.UserPostHolder
import ceui.pixiv.ui.user.UserPreviewHolder

class FollowingPostsDataSource(
    private val args: FollowingPostFragmentArgs,
    private val responseStore: ResponseStore<IllustResponse> = ResponseStore(
        { "following-user-${args.objectType}-api-${args.restrictType}" },
        1800 * 1000L,
        IllustResponse::class.java,
        { Client.appApi.followUserPosts(args.objectType, args.restrictType ?: Params.TYPE_ALL) }
    )
) : DataSource<Illust, IllustResponse>(
    dataFetcher = { responseStore.retrieveData() },
    itemMapper = { illust -> listOf(UserPostHolder(illust)) },
    filter = { illust -> illust.isAuthurExist() }
)