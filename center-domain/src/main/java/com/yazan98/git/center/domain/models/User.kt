package com.yazan98.git.center.domain.models

import java.util.*

/**
 * Created By : Yazan Tarifi
 * Date : 9/18/2019
 * Time : 1:33 PM
 */

data class User(
    var login: String = "",
    var avatar_url: String = "",
    var id: Long,
    var url: String = "",
    var type: String = "",
    var name: String = "",
    var company: String = "",
    var blog: String = "",
    var location: String = "",
    var email: String = "",
    var bio: String = "",
    var public_repos: Long,
    var followers: Long,
    var following: Long,
    var created_at: Date,
    var updated_at: Date,
    var total_private_repos: Long,
    var disk_usage: Long,
    var plan: UserPlan
)

data class UserPlan(
    var name: String = "",
    var space: Long
)
