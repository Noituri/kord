package com.gitlab.kordlib.core.entity

import com.gitlab.kordlib.common.entity.Snowflake
import com.gitlab.kordlib.core.Kord
import com.gitlab.kordlib.core.behavior.GuildBehavior
import com.gitlab.kordlib.core.behavior.UserBehavior
import com.gitlab.kordlib.core.cache.data.ApplicationInfoData
import com.gitlab.kordlib.core.cache.data.TeamData
import com.gitlab.kordlib.core.supplier.EntitySupplier
import com.gitlab.kordlib.core.supplier.EntitySupplyStrategy
import com.gitlab.kordlib.rest.Image
import java.util.*

/**
 * The details of a [Discord OAuth2](https://discord.com/developers/docs/topics/oauth2) application.
 */
class ApplicationInfo(
        val data: ApplicationInfoData,
        override val kord: Kord,
        override val supplier: EntitySupplier = kord.defaultSupplier,
) : Entity, Strategizable {

    override val id: Snowflake
        get() = data.id

    val name: String get() = data.name

    val description: String? get() = data.description

    val isPublic: Boolean get() = data.botPublic

    val requireCodeGrant: Boolean get() = data.botRequireCodeGrant

    /**
     * The rpc origins of this application, empty if disabled.
     */
    val rpcOrigins: List<String> get() = data.rpcOrigins.orEmpty()

    val ownerId: Snowflake get() = data.ownerId

    val owner: UserBehavior get() = UserBehavior(ownerId, kord)

    val summary: String get() = data.summary

    val verifyKey: String get() = data.verifyKey

    val teamId: Snowflake? get() = data.team?.id

    val team: Team? get() = data.team?.let { Team(TeamData.from(it), kord) }

    val guildId: Snowflake? get() = data.guildId.value

    val guild: GuildBehavior? get() = guildId?.let { GuildBehavior(it, kord) }

    val primarySkuId: Snowflake? get() = data.primarySkuId.value

    val slug: String? get() = data.slug.value

    val coverImageHash: String? get() = data.coverImage.value

    suspend fun getOwner(): User = supplier.getUser(ownerId)

    suspend fun getOwnerOrNull(): User? = supplier.getUserOrNull(ownerId)

    suspend fun getGuildOrNull(): Guild? {
        return supplier.getGuildOrNull(guildId ?: return null)
    }

    /**
     * Returns a new [ApplicationInfo] with the given [strategy].
     */
    override fun withStrategy(strategy: EntitySupplyStrategy<*>): ApplicationInfo =
            ApplicationInfo(data, kord, strategy.supply(kord))

    override fun hashCode(): Int = Objects.hash(id)

    override fun equals(other: Any?): Boolean = when (other) {
        is ApplicationInfo -> other.id == id
        else -> false
    }

    override fun toString(): String {
        return "ApplicationInfo(data=$data, kord=$kord, supplier=$supplier)"
    }

}