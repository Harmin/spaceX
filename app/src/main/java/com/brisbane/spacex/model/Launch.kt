package com.brisbane.spacex.model

data class Launch(
    val flight_id: String,
    val flight_number: Int,
    val mission_name: String,
    val launch_year: String,
    val launch_date_utc: String,
    val launch_success: Boolean,
    val rocket: Rocket?,
    val launchSite: LaunchSite,
    val links: Links,
    val details: String)

data class Rocket(
    val rocket_id: String,
    val rocket_name: String?)

data class LaunchSite(
    val site_id: String,
    val site_name: String,
    val site_name_long: String?)

data class Links(
    val wikipedia: String?,
    val flickr_images: List<String>)