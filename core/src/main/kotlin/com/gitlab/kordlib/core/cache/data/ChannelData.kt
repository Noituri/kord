package com.gitlab.kordlib.core.cache.data

import com.gitlab.kordlib.cache.api.data.description
import com.gitlab.kordlib.common.entity.*
import com.gitlab.kordlib.common.entity.optional.*
import kotlinx.serialization.Serializable

@Serializable
data class ChannelData(
        val id: Snowflake,
        val type: ChannelType,
        val guildId: OptionalSnowflake = OptionalSnowflake.Missing,
        val position: OptionalInt = OptionalInt.Missing,
        val permissionOverwrites: Optional<List<Overwrite>> = Optional.Missing(),
        val name: Optional<String> = Optional.Missing(),
        val topic: Optional<String?> = Optional.Missing(),
        val nsfw: OptionalBoolean = OptionalBoolean.Missing,
        val lastMessageId: OptionalSnowflake? = OptionalSnowflake.Missing,
        val bitrate: OptionalInt = OptionalInt.Missing,
        val userLimit: OptionalInt = OptionalInt.Missing,
        val rateLimitPerUser: OptionalInt = OptionalInt.Missing,
        val recipients: Optional<List<Snowflake>> = Optional.Missing(),
        val icon: Optional<String?> = Optional.Missing(),
        val ownerId: OptionalSnowflake = OptionalSnowflake.Missing,
        val applicationId: OptionalSnowflake = OptionalSnowflake.Missing,
        val parentId: OptionalSnowflake? = OptionalSnowflake.Missing,
        val lastPinTimestamp: Optional<String?> = Optional.Missing(),
) {


    companion object {
        val description = description(ChannelData::id)

        fun from(entity: DiscordChannel) = with(entity) {
            ChannelData(
                    id,
                    type,
                    guildId,
                    position,
                    permissionOverwrites,
                    name,
                    topic,
                    nsfw,
                    lastMessageId,
                    bitrate,
                    userLimit,
                    rateLimitPerUser,
                    recipients.mapList { it.id },
                    icon,
                    ownerId,
                    applicationId,
                    parentId,
                    lastPinTimestamp
            )
        }
    }

}

fun DiscordChannel.toData() = ChannelData.from(this)