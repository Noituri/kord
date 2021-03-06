package com.gitlab.kordlib.core.cache.data

import com.gitlab.kordlib.cache.api.data.description
import com.gitlab.kordlib.common.entity.*
import kotlinx.serialization.Serializable

val PresenceData.id get() = "$userId$guildId"

@Serializable
data class PresenceData(
        val userId: Snowflake,
        val guildId: Snowflake,
        val status: PresenceStatus,
        val activities: List<ActivityData>,
        val clientStatus: ClientStatusData,
) {

    companion object {
        val description = description(PresenceData::id)

        fun from(guildId: Snowflake, entity: DiscordPresenceUpdate) = with(entity) {
            PresenceData(
                    user.id,
                    guildId,
                    status,
                    activities.map { ActivityData.from(it) },
                    ClientStatusData.from(clientStatus),
            )
        }
    }

}