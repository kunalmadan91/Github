package com.kunalmadan.github.network

data class Repository (
    val id : Int?,
    val node_id : String?,
    val name : String?,
    val full_name : String?,
    val private : Boolean?,
    val html_url : String?,
    val description : String?,
    val fork : Boolean?,
    val url : String?,
    val forks_url : String?,
    val keys_urls_url : String?,
    val collaborators_url : String?,
    val teams_url : String?,
    val hooks_url : String?,
    val issue_events_url : String?,
    val events_url : String?,
    val assignees_url : String?,
    val branches_url : String?,
    val tags_url : String?,
    val blobs_url : String?,
    val git_tags_url : String?,
    val git_refs_url : String?,
    val trees_url : String?,
    val statuses_url : String?
)
